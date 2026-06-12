package object;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity {
    GamePanel gp;

    public OBJ_Heart(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = "Heart";
        type = type_pickupOnly;
        value = 2;
        down1 = setup("/object/heart", gp.tileSize, gp.tileSize);
        image = setup("/object/heart", gp.tileSize, gp.tileSize);
        image1 = setup("/object/heart_half", gp.tileSize, gp.tileSize);
        image2 = setup("/object/heart_empty", gp.tileSize, gp.tileSize);

    }

    public boolean use(Entity entity) {
        gp.playSE(9);
        gp.ui.showMessage("HP + " + value);
        entity.HP += value;
        return true;
    }

}
