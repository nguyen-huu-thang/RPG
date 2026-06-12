package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Component;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.ObjectStreamClass;
import java.util.ArrayList;
import java.util.spi.CurrencyNameProvider;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandle;
import main.UtilityTool;
import object.OBJ_Coin;
import object.OBJ_Key;
import object.OBJ_SQ;
import object.OBJ_Throwsword;
import object.OBJ_Weapon;

public class Player extends Entity {

    KeyHandle keyH;

    public final int screenX;
    public final int screenY;
    public boolean attackCancel = false;
    public boolean lightUpdated = false;

    public Player(GamePanel gp, KeyHandle keyH) {
        super(gp);

        this.keyH = keyH;

        screenX = gp.screenWidth / 2;
        screenY = gp.screenHeight / 2;
        solidArea = new Rectangle();

        solidArea.x = 16;
        solidArea.y = 16;
        solidArea.width = 16;
        solidArea.height = 24;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        hitArea.width = 36;
        hitArea.height = 36;
        setDefaultValue();
        getPlayerImage();
        getPlayerAttackImage();
        setItem();
    }

    public void getPlayerImage() {

        // up1 = setup("/char/boy_up_1", gp.tileSize, gp.tileSize);
        // up2 = setup("/char/boy_up_2", gp.tileSize, gp.tileSize);
        // down1 = setup("/char/boy_down_1", gp.tileSize, gp.tileSize);
        // down2 = setup("/char/boy_down_2", gp.tileSize, gp.tileSize);
        // left1 = setup("/char/boy_left_1", gp.tileSize, gp.tileSize);
        // left2 = setup("/char/boy_left_2", gp.tileSize, gp.tileSize);
        // right1 = setup("/char/boy_right_1", gp.tileSize, gp.tileSize);
        // right2 = setup("/char/boy_right_2", gp.tileSize, gp.tileSize);

        up1 = setup("/char/illya/illya.top.1", gp.tileSize, gp.tileSize);
        up2 = setup("/char/illya/illya.top.3", gp.tileSize, gp.tileSize);
        down1 = setup("/char/illya/illya.bot.1", gp.tileSize, gp.tileSize);
        down2 = setup("/char/illya/illya.bot.3", gp.tileSize, gp.tileSize);
        left1 = setup("/char/illya/illya.left.1", gp.tileSize, gp.tileSize);
        left2 = setup("/char/illya/illya.left.3", gp.tileSize, gp.tileSize);
        right1 = setup("/char/illya/illya.right.1", gp.tileSize, gp.tileSize);
        right2 = setup("/char/illya/illya.right.3", gp.tileSize, gp.tileSize);
        stand1 = setup("/char/illya/illya.top.2", gp.tileSize, gp.tileSize);
        stand2 = setup("/char/illya/illya.bot.2", gp.tileSize, gp.tileSize);
        stand3 = setup("/char/illya/illya.left.2", gp.tileSize, gp.tileSize);
        stand4 = setup("/char/illya/illya.right.2", gp.tileSize, gp.tileSize);
    }

    public void getPlayerAttackImage() {
        if (currentWeapon.type == type_sword) {
            attackup1 = setup("/char/boy_up_1_attack", gp.tileSize, gp.tileSize * 2);
            attackup2 = setup("/char/boy_up_2_attack", gp.tileSize, gp.tileSize * 2);
            attackdown1 = setup("/char/boy_down_1_attack", gp.tileSize, gp.tileSize * 2);
            attackdown2 = setup("/char/boy_down_2_attack", gp.tileSize, gp.tileSize * 2);
            attackleft1 = setup("/char/boy_left_1_attack", gp.tileSize * 2, gp.tileSize);
            attackleft2 = setup("/char/boy_left_2_attack", gp.tileSize * 2, gp.tileSize);
            attackright1 = setup("/char/boy_right_1_attack", gp.tileSize * 2, gp.tileSize);
            attackright2 = setup("/char/boy_right_2_attack", gp.tileSize * 2, gp.tileSize);
        }
        if (currentWeapon.type == type_lancer) {
            attackup1 = setup("/char/boy_up_1_attack2", gp.tileSize, gp.tileSize * 2);
            attackup2 = setup("/char/boy_up_2_attack2", gp.tileSize, gp.tileSize * 2);
            attackdown1 = setup("/char/boy_down_1_attack2", gp.tileSize, gp.tileSize * 2);
            attackdown2 = setup("/char/boy_down_2_attack2", gp.tileSize, gp.tileSize * 2);
            attackleft1 = setup("/char/boy_left_1_attack2", gp.tileSize * 2, gp.tileSize);
            attackleft2 = setup("/char/boy_left_2_attack2", gp.tileSize * 2, gp.tileSize);
            attackright1 = setup("/char/boy_right_1_attack2", gp.tileSize * 2, gp.tileSize);
            attackright2 = setup("/char/boy_right_2_attack2", gp.tileSize * 2, gp.tileSize);
        }

    }

    public void setDefaultValue() {
        worldX = gp.tileSize * 26 - (gp.tileSize / 2);
        worldY = gp.tileSize * 33 - (gp.tileSize / 2);
        defaultSpeed = 4;
        speed = defaultSpeed;

        direction = "down";

        // Player status
        Level = 1;
        maxHP = 6;
        HP = maxHP;
        maxMP = 4;
        MP = maxMP;
        Strength = 1;
        Dexterity = 1;
        exp = 0;
        nextLevelexp = 10;
        coins = 100;

        // Weapon

        currentWeapon = new OBJ_Weapon(gp);
        attack = getAttack();
        defense = getDefense();
        projectile = new OBJ_SQ(gp);

    }

    public void setDefaultPosition() {
        worldX = gp.tileSize * 26 - (gp.tileSize / 2);
        worldY = gp.tileSize * 33 - (gp.tileSize / 2);
        speed = 4;
        direction = "down";
    }

    public void restore() {
        HP = maxHP;
        MP = maxMP;
        invincible = false;
    }

    // Set item initiation
    public void setItem() {
        inventory.clear();
        inventory.add(currentWeapon);
    }

    public int getAttack() {
        hitArea = currentWeapon.hitArea;
        return attack = Strength * currentWeapon.attackValue;
    }

    public int getDefense() {
        return defense = (int) (defense + 2 * Dexterity) * currentWeapon.defenseValue;
    }

    public void update() {

        if (attacking) {
            attacking();

        }

        else if (keyH.up == true || keyH.right == true ||
                keyH.left == true || keyH.down == true || keyH.enter == true) {
            if (keyH.up == true) {
                direction = "up";

            }
            if (keyH.down == true) {
                direction = "down";

            }
            if (keyH.left == true) {
                direction = "left";

            }
            if (keyH.right == true) {
                direction = "right";

            }

            collisionOn = false;
            gp.cc.checkTile(this);
            int objIndex = gp.cc.checkObject(this, true);
            pickupObject(objIndex);

            // Check collision
            // Check monster collision
            int monsterIndex = gp.cc.checkEntity(this, gp.mons);
            contactMonster(monsterIndex);

            // Check npc collision
            int npcIndex = gp.cc.checkEntity(this, gp.npc);
            interactNPC(npcIndex);
            // Chech event
            gp.eh.checkEvent();
            if (gp.gameState == gp.playState) {
                gp.player.attackCancel = false;
            }

            // IF COLLISION IS FALSE PLAYER CAN MOVE
            if (collisionOn == false && !keyH.enter) {
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
            }

            if (keyH.enter == true && attackCancel == false) {
                gp.playSE(6);
                attacking = true;
                spriteCounter = 0;
            }

            gp.key.enter = false;

            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
                standNum = 0;
            }

        } else {
            standCounter++;
            if (standCounter == 10) {
                standNum = 1;
                standCounter = 0;
            }
        }
        if (gp.key.shot == true && projectile.alive == false
                && shotAvailableCounter == 60 && projectile.hasmana(this) == true) {

            projectile.set(worldX, worldY, direction, true, this);
            // Subtract mana
            projectile.costmana(this);

            // Add it to tile list
            // gp.projectileList.add(projectile);
            for (int i = 0; i < gp.projectile[1].length; i++) {
                if (gp.projectile[gp.currentMap][i] == null) {
                    gp.projectile[gp.currentMap][i] = projectile;
                    break;
                }
            }

            shotAvailableCounter = 0;
            gp.playSE(10);
        }
        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter == 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if (shotAvailableCounter < 60) {
            shotAvailableCounter++;

        }
        if (HP >= maxHP) {
            HP = maxHP;
        }
        if (MP >= maxMP) {
            MP = maxMP;
        }
        if (HP <= 0) {
            gp.gameState = gp.gameoverState;
            gp.ui.commandNum = -1;
            gp.stopMusic();
            gp.playSE(8);
        }
    }

    public void contactMonster(int i) {
        if (i != 999) {
            if (invincible == false && gp.mons[gp.currentMap][i].dying == false) {
                gp.playSE(5);
                int damage = gp.mons[gp.currentMap][i].attack - defense;
                if (damage < 0) {
                    damage = 0;
                }
                HP -= damage;
                invincible = true;
            }

        }
    }

    public void damageMonster(int i, int attack, int knockBackPower) {
        if (i != 999) {
            if (gp.mons[gp.currentMap][i].invincible == false) {

                gp.playSE(5);
                if (knockBackPower > 0) {
                    knockBack(gp.mons[gp.currentMap][i], knockBackPower);
                }

                int damage = attack - gp.mons[gp.currentMap][i].defense;
                if (damage < 0) {
                    damage = 0;
                }
                gp.mons[gp.currentMap][i].HP -= damage;
                gp.ui.showMessage(damage + " damage!");
                gp.mons[gp.currentMap][i].invincible = true;
                gp.mons[gp.currentMap][i].damagedReaction();

                if (gp.mons[gp.currentMap][i].HP <= 0) {
                    gp.mons[gp.currentMap][i].dying = true;
                    gp.ui.showMessage("Kill the " + gp.mons[gp.currentMap][i].name + "!");
                    gp.ui.showMessage("Exp + " + gp.mons[gp.currentMap][i].exp + "!");
                    exp += gp.mons[gp.currentMap][i].exp;
                    checkLevelUp();
                }
            }
        }
    }

    public void checkLevelUp() {
        if (exp >= nextLevelexp) {
            Level++;
            exp = 0;
            nextLevelexp *= 2;
            maxHP += 2;
            Strength++;
            Dexterity++;
            attack = getAttack();
            defense = getDefense();
            gp.playSE(7);
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "You are level " + Level + " now !";

        }
    }

    public void attacking() {
        spriteCounter++;
        if (spriteCounter <= 5) {
            spriteNum = 1;
        }
        if (spriteCounter > 5 && spriteCounter <= 25) {

            spriteNum = 2;

            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // Adjust player foe attacking area
            switch (direction) {
                case "up":
                    worldY -= hitArea.height;
                    break;
                case "down":
                    worldY += hitArea.height;
                    break;
                case "left":
                    worldX -= hitArea.width;
                    break;
                case "right":
                    worldX += hitArea.width;
                    break;
            }
            // Attack area become solid
            solidArea.width = hitArea.width;
            solidArea.height = hitArea.height;
            // Check collision with update
            int monsterIndex = gp.cc.checkEntity(this, gp.mons);
            damageMonster(monsterIndex, attack, currentWeapon.knockBackPower);
            int projectileIndex = gp.cc.checkEntity(this, gp.projectile);
            damageProjectile(projectileIndex);

            // After checking collision,restore value
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

        }
        if (spriteCounter > 30) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }

    }

    // Them hieu ung day lui
    public void knockBack(Entity entity, int knockBackPower) {
        entity.direction = direction;
        entity.speed += knockBackPower;
        entity.knockBack = true;

    }

    public void pickupObject(int i) {
        if (i != 999) {
            // Pick up item

            if (gp.obj[gp.currentMap][i].type == type_pickupOnly) {
                gp.obj[gp.currentMap][i].use(this);
                gp.obj[gp.currentMap][i] = null;
            } else if (gp.obj[gp.currentMap][i].type == type_solid) {
                if (keyH.enter == true) {
                    attackCancel = true;
                    gp.obj[gp.currentMap][i].interact();
                }
            } else {

                // Inventory pick up
                String text;

                if (canObtain(gp.obj[gp.currentMap][i]) == true) {
                    gp.playSE(3);
                    text = "Get a " + gp.obj[gp.currentMap][i].name + "!";
                } else {
                    text = "You cannot carry anymore!";
                }
                gp.ui.showMessage(text);
                gp.obj[gp.currentMap][i] = null;
            }

        }
    }

    public void interactNPC(int i) {
        if (gp.key.enter == true) {
            if (i != 999) {
                gp.gameState = gp.dialogueState;
                gp.npc[gp.currentMap][i].speak();
                attackCancel = true;

            }
        }

    }

    public void damageProjectile(int i) {
        if (i != 999) {
            Entity projectile = gp.projectile[gp.currentMap][i];
            projectile.alive = false;

        }
    }

    public void selectItem() {
        int itemIndex = gp.ui.getItemIndex(gp.ui.playerslotCol, gp.ui.playerslotRow);
        if (itemIndex < inventory.size()) {

            Entity selectedItem = inventory.get(itemIndex);
            if (selectedItem.type == type_sword || selectedItem.type == type_lancer) {
                currentWeapon = selectedItem;
                attack = getAttack();
                getPlayerAttackImage();
            }
            if (selectedItem.type == type_comsumable) {
                if (selectedItem.use(this) == true) {
                    if (selectedItem.amount > 1) {
                        selectedItem.amount--;
                    } else {
                        inventory.remove(itemIndex);
                    }

                }

            }
            if (selectedItem.type == type_light) {
                if (currentLight == selectedItem) {
                    currentLight = null;
                } else {
                    currentLight = selectedItem;
                }
                lightUpdated = true;
            }
        }
    }

    public int searchItem(String itemName) {
        int itemIndex = 999;
        for (int i = 0; i < gp.player.inventory.size(); i++) {
            if (inventory.get(i).name.equals(itemName)) {
                itemIndex = i;
                break;
            }
        }
        return itemIndex;
    }

    public boolean canObtain(Entity item) {
        boolean canObtain = false;
        // Check item stackable
        if (item.stackable == true) {

            int index = searchItem(item.name);

            if (index != 999) {
                inventory.get(index).amount++;
                canObtain = true;
            }
            // New item
            else {
                if (inventory.size() != maxInventorySize) {
                    inventory.add(item);
                    canObtain = true;
                }
            }
        }
        // Not stackable
        else {
            if (inventory.size() != maxInventorySize) {
                inventory.add(item);
                canObtain = true;
            }
        }
        return canObtain;
    }

    public void draw(Graphics2D g2) {
        int tempScreenX = screenX, tempScreenY = screenY;

        BufferedImage image = null;
        switch (direction) {
            case "up":
                if (!attacking) {
                    if (spriteNum == 1) {
                        image = up1;
                    }
                    if (spriteNum == 2) {
                        image = up2;
                    }
                    if (standNum == 1) {
                        image = stand1;
                    }
                } else {
                    tempScreenY = screenY - gp.tileSize;
                    if (spriteNum == 1) {
                        image = attackup1;
                    }
                    if (spriteNum == 2) {
                        image = attackup2;
                    }
                }

                break;
            case "down":
                if (!attacking) {
                    if (spriteNum == 1) {
                        image = down1;
                    }
                    if (spriteNum == 2) {
                        image = down2;
                    }
                    if (standNum == 1) {
                        image = stand2;
                    }
                } else {
                    if (spriteNum == 1) {
                        image = attackdown1;
                    }
                    if (spriteNum == 2) {
                        image = attackdown2;
                    }
                }
                break;
            case "left":
                if (!attacking) {
                    if (spriteNum == 1) {
                        image = left1;
                    }
                    if (spriteNum == 2) {
                        image = left2;
                    }
                    if (standNum == 1) {
                        image = stand3;
                    }
                } else {
                    tempScreenX = screenX - gp.tileSize;
                    if (spriteNum == 1) {
                        image = attackleft1;
                    }
                    if (spriteNum == 2) {
                        image = attackleft2;
                    }
                }

                break;
            case "right":
                if (!attacking) {
                    if (spriteNum == 1) {
                        image = right1;
                    }
                    if (spriteNum == 2) {
                        image = right2;
                    }
                    if (standNum == 1) {
                        image = stand4;
                    }
                } else {
                    if (spriteNum == 1) {
                        image = attackright1;
                    }
                    if (spriteNum == 2) {
                        image = attackright2;
                    }
                }
                break;
        }

        if (invincible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
        }
        if (screenX > worldX) {
            tempScreenX = worldX;

        }
        if (screenY > worldY) {
            tempScreenY = worldY;
        }
        int right = gp.screenWidth - screenX;
        if (right > gp.worldWidth - worldX) {
            tempScreenX = gp.screenWidth - (gp.worldWidth - worldX);
        }
        int bot = gp.screenHeight - screenY;

        if (bot > gp.worldHeight - worldY) {
            tempScreenY = gp.screenHeight - (gp.worldHeight - worldY);
        }

        g2.drawImage(image, tempScreenX, tempScreenY, null);
        // Reset Image
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

}