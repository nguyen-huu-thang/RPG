package object;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class OBJ_Door extends Entity {
    GamePanel gp;

    public OBJ_Door(GamePanel gp) {

        super(gp);
        this.gp = gp;
        type = type_solid;
        solidArea.x = 3;
        solidArea.y = 12;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        name = "Door";
        down1 = setup("/object/door", gp.tileSize, gp.tileSize);
        collision = true;

    }

    public void interact() {
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "You need a key.";

    }

}
