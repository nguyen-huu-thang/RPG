package object;

import javax.imageio.ImageIO;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class OBJ_SQ extends Projectile {
    GamePanel gp;

    public OBJ_SQ(GamePanel gp) {

        super(gp);
        this.gp = gp;
        name = "Rock";
        speed = 5;
        maxHP = 60;
        HP = maxHP;
        attack = 4;
        defense = 0;
        useCost = 1;
        alive = false;
        getImage();
    }

    public void getImage() {
        up1 = setup("/object/Projectiles/sq", gp.tileSize, gp.tileSize);
        up2 = setup("/object/Projectiles/sq", gp.tileSize, gp.tileSize);
        down1 = setup("/object/Projectiles/sq", gp.tileSize, gp.tileSize);
        down2 = setup("/object/Projectiles/sq", gp.tileSize, gp.tileSize);
        left1 = setup("/object/Projectiles/sq", gp.tileSize, gp.tileSize);
        left2 = setup("/object/Projectiles/sq", gp.tileSize, gp.tileSize);
        right1 = setup("/object/Projectiles/sq", gp.tileSize, gp.tileSize);
        right2 = setup("/object/Projectiles/sq", gp.tileSize, gp.tileSize);
    }

    public boolean hasmana(Entity user) {
        boolean hasmana = false;
        if (user.MP >= useCost) {
            hasmana = true;
        }
        return hasmana;
    }

    public void costmana(Entity user) {
        user.MP -= useCost;
    }
}