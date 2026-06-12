package object;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class OBJ_Mana extends Entity {
    GamePanel gp;

    public OBJ_Mana(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_pickupOnly;
        value = 1;
        name = "Mana";
        down1 = setup("/object/mana", gp.tileSize, gp.tileSize);
        image = setup("/object/mana", gp.tileSize, gp.tileSize);
        image2 = setup("/object/no_mana", gp.tileSize, gp.tileSize);
    }

    public boolean use(Entity entity) {
        gp.playSE(9);
        gp.ui.showMessage("MP + " + value);
        entity.MP += value;
        return true;
    }

}
