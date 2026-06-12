package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Weapon extends Entity {

    public OBJ_Weapon(GamePanel gp) {
        super(gp);
        type = type_sword;
        name = "Sword";
        down1 = setup("/object/sword2", gp.tileSize, gp.tileSize);
        attackValue = 4;
        defenseValue = 1;
        hitArea.width = 36;
        hitArea.height = 36;
        price = 100;
        knockBackPower = 3;
        description = "An old Sword";

    }
}