package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Tent extends Entity {
    GamePanel gp;

    public OBJ_Tent(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_comsumable;
        name = "Tent";
        down1 = setup("/object/tent", gp.tileSize, gp.tileSize);
        price = 300;
        stackable = true;
        description = " You can sleep inside.";

    }

    public boolean use(Entity entity) {
        gp.gameState = gp.sleepState;
        gp.playSE(15);
        gp.player.HP = gp.player.maxHP;
        gp.player.MP = gp.player.maxMP;
        return true;
    }
}
