package object;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class OBJ_Table extends Entity {
    GamePanel gp;

    public OBJ_Table(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_solid;
        name = "Table";
        down1 = setup("/object/table", gp.tileSize, gp.tileSize);
        collision = true;

    }
}