package entity;

import java.util.Random;
import java.awt.Rectangle;
import java.lang.Math;
import main.GamePanel;

public class NPC_Oldman extends Entity {
    public NPC_Oldman(GamePanel gp) {
        super(gp);
        type = 1;

        direction = "down";
        speed = 1;

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 30;
        solidArea.height = 30;
        getImage();
        setDialogue();
    }

    public void getImage() {

        up1 = setup("/npc/oldman_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/npc/oldman_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/npc/oldman_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/npc/oldman_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/npc/oldman_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/npc/oldman_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/npc/oldman_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/npc/oldman_right_2", gp.tileSize, gp.tileSize);

    }

    public void setAction() {
        if (onPath == true) {
            // int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
            // int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;

            // searchPath(goalCol, goalRow);
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

    public void speak() {

        super.speak();
        onPath = true;
    }

    public void setDialogue() {
        dialogue[0] = "Hello!";
        dialogue[1] = "What?";
        dialogue[2] = "I'm old now.";
        dialogue[3] = "Cannot be a knight anymore.";
        dialogue[4] = "So sad.";

    }

}