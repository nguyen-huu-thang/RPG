package object;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity {
    GamePanel gp;

    public OBJ_Key(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_comsumable;
        name = "Key";
        down1 = setup("/object/key", gp.tileSize, gp.tileSize);
        description = "It opens a door.";
        stackable = true;
    }

    public boolean use(Entity entity) {
        gp.gameState = gp.dialogueState;
        int objIndex = getDetected(entity, gp.obj, "Door");

        if (objIndex != 999) {
            gp.ui.currentDialogue = "You used a key to\nopen the door!";
            gp.playSE(1);
            gp.obj[gp.currentMap][objIndex] = null;
            return true;
        } else {
            gp.ui.currentDialogue = "Hmm?\nWhat are you doing?";
            return false;
        }

    }

}
