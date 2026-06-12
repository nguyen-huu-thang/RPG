package object;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class OBJ_hp extends Entity {

    GamePanel gp;

    public OBJ_hp(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_comsumable;
        name = "Potion";
        value = 5;
        down1 = setup("/object/hp", gp.tileSize, gp.tileSize);
        description = "Heal 5 HP.";
        price = 25;
        stackable = true;
    }

    public boolean use(Entity entity) {

        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "You drink the potion.\nHeal " + value + " HP.";
        entity.HP += value;
        gp.playSE(9);
        return true;
    }
}
