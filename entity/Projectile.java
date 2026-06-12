package entity;

import main.GamePanel;

public class Projectile extends Entity {
    Entity user;

    public Projectile(GamePanel gp) {
        super(gp);

    }

    public void set(int worldX, int worldY, String direction, boolean alive, Entity user) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        this.HP = maxHP;
    }

    public void update() {

        if (user == gp.player) {
            int monsterIndex = gp.cc.checkEntity(this, gp.mons);
            if (monsterIndex != 999) {
                gp.player.damageMonster(monsterIndex, attack, knockBackPower);
                alive = false;

            }
        } else {
            boolean contactPlayer = gp.cc.checkPlayer(this);
            if (gp.player.invincible == false && contactPlayer == true) {
                damagePlayer(attack);
                alive = false;
            }
        }

        switch (direction) {
            case "up":
                worldY -= speed;
                break;
            case "down":
                worldY += speed;
                break;
            case "left":
                worldX -= speed;
                break;
            case "right":
                worldX += speed;
                break;
        }
        HP--;
        if (HP <= 0) {
            alive = false;
        }
        spriteCounter++;
        if (spriteCounter > 12) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public boolean hasmana(Entity user) {
        boolean hasmana = false;
        return hasmana;
    }

    public void costmana(Entity user) {
    }
}
