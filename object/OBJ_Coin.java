package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Coin extends Entity {
    GamePanel gp;

    public OBJ_Coin(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_pickupOnly;
        name = "QP";
        value = 1;
        down1 = setup("/object/qp", gp.tileSize, gp.tileSize);

    }

    public boolean use(Entity entity) {
        gp.playSE(3);
        gp.ui.showMessage("Coin + " + value);
        gp.player.coins += value;
        return true;
    }
}