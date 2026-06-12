package object;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class OBJ_House extends Entity {
    GamePanel gp;

    public OBJ_House(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = 2;
        name = "House";

        down1 = setup("/object/house1", gp.tileSize, gp.tileSize);
        collision = true;
    }
}