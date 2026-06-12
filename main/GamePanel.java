package main;

import javax.swing.JPanel;
import javax.swing.plaf.TextUI;
import AI.Pathfinder;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.awt.Graphics;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import entity.Entity;
import entity.Player;
import enviroment.Environment;
import enviroment.Lightning;
import object.Objects;
import tile.TileManager;

//Game Screen
public class GamePanel extends JPanel implements Runnable {
    // SETTINGS SCREEN
    public final int originalSize = 16; // 16x16 size
    public final int scale = 3; // multiply base the monitor;

    // SYSTEM
    public int currentMap = 0;
    public final int maxMap = 10;
    public final int tileSize = originalSize * scale;
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 15;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    // WORLD MAP SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = maxWorldCol * tileSize;
    public final int worldHeight = maxWorldRow * tileSize;

    public Pathfinder pf = new Pathfinder(this);
    public KeyHandle key = new KeyHandle(this);
    public AssetSetter as = new AssetSetter(this);
    public UI ui = new UI(this);
    public Sounds music = new Sounds();
    public Sounds se = new Sounds();
    Thread gameThread;
    public collisionCheck cc = new collisionCheck(this);
    public EventHandle eh = new EventHandle(this);
    Config config = new Config(this);
    Environment eManager = new Environment(this);
    public final int easy = 1;
    public final int hard = 2;
    public final int evil = 3;
    public int gameMode = easy;

    // Player and object
    public Player player = new Player(this, key);
    public Entity obj[][] = new Entity[maxMap][20];
    public Entity npc[][] = new Entity[maxMap][10];

    public Entity mons[][] = new Entity[maxMap][20];
    ArrayList<Entity> entityList = new ArrayList<>();
    public Entity projectile[][] = new Entity[maxMap][20];
    // public ArrayList<Entity> projectileList = new ArrayList<>();
    // Game State

    public final int playState = 1;
    public final int pauseState = 0;
    public int gameState = playState;
    public final int dialogueState = 2;
    public final int titleState = 3;
    public final int characterState = 4;
    public final int optionState = 5;
    public final int gameoverState = 6;
    public final int transitionState = 7;
    public final int tradeState = 8;
    public final int sleepState = 9;

    // Player position;

    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 6;

    // FPS
    int FPS = 60;
    //

    public TileManager tileM = new TileManager(this);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(key);
        this.setFocusable(true);
    }

    public void setupGame() {
        gameState = titleState;
        as.setObject();
        as.setMonster();

        eManager.setup();
        as.setNPC();
        playMusic(11);

    }

    public void retry() {
        currentMap = 0;
        se.stop();
        player.setDefaultPosition();
        player.restore();
        as.setNPC();
        as.setMonster();

    }

    public void restart() {
        player.setDefaultValue();
        player.setItem();
        as.setObject();
        as.setMonster();
        as.setNPC();

    }

    public void startGamethread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override

    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        while (gameThread != null) {
            // 1 : Update character position
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {

        if (gameState == playState) {
            // Player
            player.update();
            // NPC
            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    npc[currentMap][i].update();
                }
            }
            // Monster
            for (int i = 0; i < mons[1].length; i++) {
                if (mons[currentMap][i] != null) {
                    if (mons[currentMap][i].alive == true && mons[currentMap][i].dying == false) {
                        mons[currentMap][i].update();
                    }
                    if (mons[currentMap][i].alive == false) {
                        mons[currentMap][i].checkDrop();
                        mons[currentMap][i] = null;
                    }

                }
            }
            // Projectile
            for (int i = 0; i < projectile[1].length; i++) {
                if (projectile[currentMap][i] != null) {
                    if (projectile[currentMap][i].alive == true) {
                        projectile[currentMap][i].update();
                    } else if (projectile[currentMap][i].alive == false)
                        projectile[currentMap][i] = null;
                }
            }
            eManager.update();
        }

        if (gameState == pauseState) {

        }
        // nothing

    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        // Title Screen
        if (gameState == titleState) {
            ui.draw(g2);
        } // Other
        else {
            tileM.draw(g2);

            // Object draw
            entityList.add(player);

            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    entityList.add(npc[currentMap][i]);

                }
            }
            // Object
            for (int i = 0; i < obj[1].length; i++) {
                if (obj[currentMap][i] != null) {
                    entityList.add(obj[currentMap][i]);

                }
            }
            // Monster
            for (int i = 0; i < mons[1].length; i++) {
                if (mons[currentMap][i] != null) {
                    entityList.add(mons[currentMap][i]);
                }
            }
            // Projectile
            for (int i = 0; i < projectile[1].length; i++) {
                if (projectile[currentMap][i] != null) {
                    entityList.add(projectile[currentMap][i]);

                }
            }

            // Soft order

            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {

                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }

            });

            // Draw Entity

            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);

            }
            // EMPTY ENTITY LIST
            entityList.clear();
            // Enviroment
            eManager.draw(g2);

            // Ui draw
            ui.draw(g2);
            // Draw NPC

            // Debug
            if (key.checkDrawTime == true) {

                g2.setFont(new Font("Arial", Font.PLAIN, 20));
                g2.setColor(Color.white);
                int x = 10;
                int y = 600;

                g2.drawString("WorldX" + player.worldX, x, y);
                y += 20;
                g2.drawString("WorldY" + player.worldY, x, y);
                y += 20;
                g2.drawString("ColX:" + (player.worldX + player.solidArea.x) / tileSize, x, y);
                y += 20;
                g2.drawString("RowY:" + (player.worldY + player.solidArea.y) / tileSize, x, y);
                y += 20;
            }

        }
        // Draw tile
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();

    }

    public void stopMusic() {
        music.stop();
        se.stop();
    }

    public void playSE(int i) {
        se.setFile(i);
        se.play();

    }
}