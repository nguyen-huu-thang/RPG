package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import javax.swing.GrayFilter;

import java.awt.AlphaComposite;
import java.awt.Color;

import main.GamePanel;
import main.UtilityTool;

public class Entity {
    public BufferedImage image, image1, image2, image3, image4;
    public BufferedImage attackup1, attackup2, attackdown1, attackdown2,
            attackleft1, attackleft2, attackright1, attackright2;
    public String name;
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int inventorySize = 20;
    public Rectangle hitArea = new Rectangle(0, 0, 0, 0);
    public boolean collision = false;
    public int speed;
    GamePanel gp;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2, stand1, stand2, stand3, stand4;
    public Rectangle solidArea = new Rectangle(0, 0, 40, 40);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public String dialogue[] = new String[20];
    public int type = 0;// 0:player 1:npc 2:monster

    // State
    public int worldX, worldY;
    public String direction = "down";
    int dialogueIndex = 0;
    public int spriteNum = 1;
    public int standNum = 1;
    public boolean invincible = false;
    public boolean collisionOn = false;
    public boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    boolean hpBar = false;
    public boolean onPath = false;
    public boolean knockBack = false;

    // Counter
    public int standCounter = 0;
    public int spriteCounter = 0;
    public int invincibleCounter = 0;
    public int interval = 0;
    int dyingCounter = 0;
    int hpBarCounter = 0;
    public int shotAvailableCounter = 0;
    public int knockBackCounter = 0;

    // Character stat
    public final int maxInventorySize = 20;
    public int defaultSpeed;
    public int maxHP;
    public int HP;
    public int maxMP;
    public int MP;
    public int Level;
    public int Strength;
    public int Dexterity;
    public int Intelligence;
    public int Vitality;
    public int attack;
    public int defense;
    public int exp;
    public int coins;
    public int nextLevelexp;
    public Entity currentWeapon;
    public Entity currentLight;
    public Projectile projectile;
    // Item
    public int value;
    public int defenseValue;
    public int attackValue;
    public String description = "";
    public int useCost;
    public int price;
    public int knockBackPower = 0;
    public boolean stackable = false;
    public int amount = 1;
    public int lightRadius;

    // Type

    public final int type_sword = 3;
    public final int type_lancer = 4;
    public final int type_comsumable = 5;
    public final int type_pickupOnly = 6;
    public final int type_solid = 7;
    public final int type_light = 8;
    // public final int type_obstacle = 8;

    public Entity(GamePanel gp) {
        this.gp = gp;

    }

    public void interact() {

    }

    public void setAction() {

    }

    public void damagedReaction() {

    }

    public void speak() {
        gp.ui.currentDialogue = dialogue[dialogueIndex];
        dialogueIndex++;
        if (dialogue[dialogueIndex] == null) {
            dialogueIndex = 0;
        }

        switch (gp.player.direction) {
            case "up":
                direction = "down";
                break;
            case "left":
                direction = "right";
                break;
            case "down":
                direction = "up";
                break;
            case "right":
                direction = "left";
                break;
        }
    }

    public void update() {

        if (knockBack == true) {
            checkCollision();
            if (collisionOn == true) {
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;

            } else {
                switch (gp.player.direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
                knockBackCounter++;
                if (knockBackCounter == 4) {
                    knockBackCounter = 0;
                    knockBack = false;
                    speed = defaultSpeed;

                }
            }

        } else {
            setAction();
            checkCollision();

            if (collisionOn == false) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }

        }

        spriteCounter++;
        if (spriteCounter > 12) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 30) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if (shotAvailableCounter < 60) {
            shotAvailableCounter++;

        }
    }

    public void damagePlayer(int attack) {
        if (gp.player.invincible == false) {
            gp.playSE(5);
            int damage = attack - gp.player.defense;
            if (damage < 0) {
                damage = 0;
            }
            gp.player.HP -= damage;
            gp.player.invincible = true;
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        // STOP MOVING CAMERA
        if (gp.player.worldX < gp.player.screenX) {
            screenX = worldX;
        }
        if (gp.player.worldY < gp.player.screenY) {
            screenY = worldY;
        }
        int right = gp.screenWidth - gp.player.screenX;
        if (right > gp.worldWidth - gp.player.worldX) {
            screenX = gp.screenWidth - (gp.worldWidth - worldX);
        }
        int bot = gp.screenHeight - gp.player.screenY;
        if (bot > gp.worldHeight - gp.player.worldY) {
            screenY = gp.screenHeight - (gp.worldHeight - worldY);
        }

        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }

                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }

                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }

                break;
        }
        // Monster HP bar
        if (this.type == 2 && hpBar == true) {
            double oneScale = (double) gp.tileSize / maxHP;
            double HPBarValue = oneScale * HP;

            g2.setColor(new Color(35, 35, 35));
            g2.fillRect(screenX - 1, screenY - 16, gp.tileSize + 2, 12);

            g2.setColor(new Color(225, 0, 30));
            g2.fillRect(screenX, screenY - 15, (int) HPBarValue, 10);
            hpBarCounter++;

            if (hpBarCounter > 600) {
                hpBarCounter = 0;
                hpBar = false;
            }
        }

        if (invincible) {
            hpBar = true;
            hpBarCounter = 0;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }
        if (dying == true) {
            dyingAnimation(g2);
        }
        if (worldX + gp.tileSize >= gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize <= gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize >= gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize <= gp.player.worldY + gp.player.screenY) {
            g2.drawImage(image, screenX, screenY, null);
        } else if (gp.player.worldX < gp.player.screenX ||
                gp.player.worldY < gp.player.screenY ||
                right > gp.worldWidth - gp.player.worldX ||
                bot > gp.worldHeight - gp.player.worldY) {
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        // if ((gp.player.worldX < gp.player.screenX ||
        // gp.player.worldY < gp.player.screenY ||
        // right > gp.worldWidth - gp.player.worldX ||
        // bot > gp.worldHeight - gp.player.worldY)) {
        // g2.drawImage(image, screenX, screenY, null);
        // }
    }

    public BufferedImage setup(String imageName, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imageName + ".png"));
            image = uTool.scaleImage(image, width, height);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return image;

    }

    public void dyingAnimation(Graphics2D g2) {

        dyingCounter++;

        if (dyingCounter <= 10) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > 10 && dyingCounter <= 20) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > 20 && dyingCounter <= 30) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > 30 && dyingCounter <= 40) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > 40 && dyingCounter <= 50) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > 50 && dyingCounter <= 60) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > 60 && dyingCounter <= 70) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > 70 && dyingCounter <= 80) {
            changeAlpha(g2, 1f);
        }

        if (dyingCounter > 80) {
            alive = false;
        }
    }

    public boolean use(Entity entity) {
        return false;
    }

    public void changeAlpha(Graphics2D g2, float alpha) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
    }

    public void checkDrop() {
    }

    public int getLeftX(GamePanel gp) {
        return worldX + solidArea.x;
    }

    public int getRightX(GamePanel gp) {
        return worldX + solidArea.x + solidArea.width;
    }

    public int getTopY(GamePanel gp) {
        return worldY + solidArea.y;
    }

    public int getBotY(GamePanel gp) {
        return worldY + solidArea.y + solidArea.height;
    }

    public int getCol() {
        return (worldX + solidArea.x) / gp.tileSize;
    }

    public int getRow() {
        return (worldY + solidArea.y) / gp.tileSize;
    }

    public void dropItem(Entity drop) {
        for (int i = 0; i < gp.obj[1].length; i++) {
            if (gp.obj[gp.currentMap][i] == null) {
                gp.obj[gp.currentMap][i] = drop;
                gp.obj[gp.currentMap][i].worldX = worldX;// Where dead mons drop
                gp.obj[gp.currentMap][i].worldY = worldY;
                break;
            }
        }

    }

    public void checkCollision() {
        collisionOn = false;
        gp.cc.checkTile(this);
        gp.cc.checkObject(this, false);
        gp.cc.checkEntity(this, gp.mons);
        boolean contactPlayer = gp.cc.checkPlayer(this);
        if (this.type == 2 && contactPlayer) {
            damagePlayer(attack);
        }
    }

    public void searchPath(int goalCol, int goalRow) {

        int startCol = (worldX + solidArea.x) / gp.tileSize;
        int startRow = (worldY + solidArea.y) / gp.tileSize;

        gp.pf.setNode(startCol, startRow, goalCol, goalRow);

        if (gp.pf.search() == true) {
            // next worldx and y
            int nextX = gp.pf.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pf.pathList.get(0).row * gp.tileSize;

            // Entity solid position
            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBotY = worldY + solidArea.y + solidArea.height;

            if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "up";
            } else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "down";
            } else if (enTopY >= nextY && enBotY < nextY + gp.tileSize) {
                // left or right
                if (enLeftX > nextX) {
                    direction = "left";
                }
                if (enLeftX < nextX) {
                    direction = "right";
                }
            } else if (enTopY > nextY && enLeftX > nextX) {
                // up or left
                direction = "up";
                checkCollision();
                if (collisionOn == true) {
                    direction = "left";
                }
            } else if (enTopY > nextY && enLeftX < nextX) {
                // up or right
                direction = "up";
                checkCollision();
                if (collisionOn == true) {
                    direction = "right";
                }
            } else if (enTopY < nextY && enLeftX > nextX) {
                // Down or left
                direction = "down";
                checkCollision();
                if (collisionOn == true) {
                    direction = "left";
                }
            } else if (enTopY < nextY && enLeftX < nextX) {
                // Down or right
                direction = "down";
                checkCollision();
                if (collisionOn == true) {
                    direction = "right";
                }

            }

            // int nextCol = gp.pf.pathList.get(0).col;
            // int nextRow = gp.pf.pathList.get(0).row;
            // if (nextCol == goalCol && nextRow == goalRow) {
            // onPath = false;
            // }
        }
    }

    public int getDetected(Entity user, Entity target[][], String targetName) {
        int index = 999;
        // Check surrounding area
        int nextWorldX = user.getLeftX(gp);
        int nextWorldY = user.getTopY(gp);

        switch (user.direction) {
            case "up":
                nextWorldY = user.getTopY(gp) - 1;
                break;
            case "down":
                nextWorldY = user.getBotY(gp) + 1;
                break;
            case "left":
                nextWorldX = user.getLeftX(gp) - 1;
                break;
            case "right":
                nextWorldX = user.getRightX(gp) + 1;
                break;

        }
        int col = nextWorldX / gp.tileSize;
        int row = nextWorldY / gp.tileSize;

        for (int i = 0; i < target[1].length; i++) {
            if (target[gp.currentMap][i] != null) {
                if (target[gp.currentMap][i].getCol() == col &&
                        target[gp.currentMap][i].getRow() == row &&
                        target[gp.currentMap][i].name.equals(targetName)) {
                    index = i;
                    break;

                }
            }
        }
        return index;

    }
}
