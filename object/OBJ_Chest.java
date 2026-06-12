package object;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class OBJ_Chest extends Entity {
    GamePanel gp;
    Entity loot;
    boolean opened = false;

    public OBJ_Chest(GamePanel gp, Entity loot) {
        super(gp);
        this.gp = gp;
        this.loot = loot;
        type = type_solid;

        name = "Chest";
        image1 = setup("/object/chest/chest1", gp.tileSize, gp.tileSize);
        image2 = setup("/object/chest/chest1_open", gp.tileSize, gp.tileSize);
        image3 = setup("/object/chest/chest2", gp.tileSize, gp.tileSize);
        image4 = setup("/object/chest/chest2_open", gp.tileSize, gp.tileSize);
        down1 = image1;
        down2 = image3;
        collision = true;

        solidArea.x = 4;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public void interact() {
        gp.gameState = gp.dialogueState;

        if (opened == false) {
            gp.playSE(14);

            StringBuilder sb = new StringBuilder();
            sb.append("You open the chest and find a " + loot.name + "!");
            if (gp.player.canObtain(loot) == false) {
                sb.append("\n...But you cannot carry anymore.");
            } else {
                sb.append("\nYou got the " + loot.name + "!");
                down1 = image2;
                opened = true;
            }
            gp.ui.currentDialogue = sb.toString();

        } else {
            gp.ui.currentDialogue = "It's empty.";
        }
    }
}
