package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Lancer extends Entity {

    public OBJ_Lancer(GamePanel gp) {
        super(gp);
        type = type_lancer;
        name = "Lance";
        down1 = setup("/object/lancer1", gp.tileSize, gp.tileSize);
        attackValue = 3;
        defenseValue = 3;
        knockBackPower = 10;
        hitArea.width = 48;
        hitArea.height = 48;
        description = "A candy lance";
        price = 100;

    }
}