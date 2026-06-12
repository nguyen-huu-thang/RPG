package monsters;

import java.awt.Rectangle;
import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin;
import object.OBJ_Heart;
import object.OBJ_Lancer;
import object.OBJ_Mana;
import object.OBJ_SQ;
import object.OBJ_Throwsword;
import object.OBJ_hp;

public class Mons1 extends Entity {
    GamePanel gp;

    public Mons1(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = 2;
        name = "Monster";

        defaultSpeed = 2;
        maxHP = 5;
        HP = maxHP;
        attack = 4;
        defense = 2;
        exp = 5;
        speed = defaultSpeed;

        projectile = new OBJ_Throwsword(gp);

        solidArea.x = 8;
        solidArea.y = 12;
        solidArea.width = 30;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setImage();
    }

    public void setImage() {

        up1 = setup("/monsters/mons1_up1", gp.tileSize, gp.tileSize);
        up2 = setup("/monsters/mons1_up2", gp.tileSize, gp.tileSize);
        down1 = setup("/monsters/mons1_down1", gp.tileSize, gp.tileSize);
        down2 = setup("/monsters/mons1_down2", gp.tileSize, gp.tileSize);
        left1 = setup("/monsters/mons1_left1", gp.tileSize, gp.tileSize);
        left2 = setup("/monsters/mons1_left2", gp.tileSize, gp.tileSize);
        right1 = setup("/monsters/mons1_right1", gp.tileSize, gp.tileSize);
        right2 = setup("/monsters/mons1_right2", gp.tileSize, gp.tileSize);

    }

    public void update() {
        super.update();

        int xDistence = Math.abs(worldX - gp.player.worldX);
        int yDistence = Math.abs(worldY - gp.player.worldY);
        int tileDistance = (xDistence + yDistence) / gp.tileSize;
        if (onPath == false && tileDistance < 3) {

            onPath = true;

        }
        // if (onPath == true && tileDistance > 20) {
        // onPath = false;
        // }
    }

    public void setAction() {

        if (onPath == true) {
            int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;

            searchPath(goalCol, goalRow);

            int i = new Random().nextInt(200) + 1;
            if (i < 190 && projectile.alive == false && shotAvailableCounter == 60) {

                projectile.set(worldX, worldY, direction, true, this);

                for (int ii = 0; ii < gp.projectile[1].length; ii++) {
                    if (gp.projectile[gp.currentMap][ii] == null) {
                        gp.projectile[gp.currentMap][ii] = projectile;
                        break;
                    }
                }
                shotAvailableCounter = 0;
            }
        } else {

            interval++;

            if (interval == 120) {

                Random random = new Random();
                int i = random.nextInt(100) + 1;// Random number

                if (i <= 25) {
                    direction = "up";

                }
                if (i > 25 && i <= 50) {
                    direction = "down";

                }
                if (i > 50 && i <= 75) {
                    direction = "left";

                }
                if (i > 75 && i <= 100) {
                    direction = "right";

                }
                interval = 0;
            }

        }

    }

    public void damagedReaction() {
        interval = 0;

        if (gp.gameMode == gp.hard || gp.gameMode == gp.evil) {
            onPath = true;
        } else if (gp.gameMode == gp.easy) {
            direction = gp.player.direction;
        }

    }

    public void checkDrop() {
        // cast a dice
        int i = new Random().nextInt(100) + 1;
        // Set a drop
        if (i < 25) {
            dropItem(new OBJ_hp(gp));
        }
        if (i >= 25 && i < 50) {
            dropItem(new OBJ_Coin(gp));
        }
        if (i >= 50 && i < 75) {
            dropItem(new OBJ_Mana(gp));
        }
        if (i >= 75 && i < 99) {
            dropItem(new OBJ_Heart(gp));
        }
        if (i >= 99) {
            dropItem(new OBJ_Lancer(gp));
        }

    }
}
