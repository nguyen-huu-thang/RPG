package object;

import javax.imageio.ImageIO;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class OBJ_Throwsword extends Projectile {
    GamePanel gp;

    public OBJ_Throwsword(GamePanel gp) {

        super(gp);
        this.gp = gp;

        name = "Knife";
        speed = 12;
        maxHP = 120;
        HP = maxHP;
        attack = 5;
        defense = 0;
        useCost = 1;
        knockBackPower = 0;
        alive = false;
        getImage();
    }

    public void getImage() {
        up1 = setup("/object/Projectiles/sword2_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/object/Projectiles/sword2_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/object/Projectiles/sword2_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/object/Projectiles/sword2_down_1", gp.tileSize, gp.tileSize);
        left1 = setup("/object/Projectiles/sword2_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/object/Projectiles/sword2_left_1", gp.tileSize, gp.tileSize);
        right1 = setup("/object/Projectiles/sword2_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/object/Projectiles/sword2_right_1", gp.tileSize, gp.tileSize);
    }

    // public boolean hasmana(Entity user) {
    // boolean hasmana = false;
    // if (user.MP >= useCost) {
    // hasmana = true;
    // }
    // return hasmana;
    // }

    // public void costmana(Entity user) {
    // user.MP -= useCost;
    // }
}
