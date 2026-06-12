package entity;

import java.awt.Rectangle;

import main.GamePanel;
import object.OBJ_Lancer;
import object.OBJ_Mana;
import object.OBJ_SQ;
import object.OBJ_Weapon;
import object.OBJ_hp;

public class NPC_Merchant extends Entity {

    public NPC_Merchant(GamePanel gp) {

        super(gp);
        type = 1;
        direction = "down";
        speed = 0;
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;
        getImage();
        setDialogue();
        setItem();
    }

    public void getImage() {

        up1 = setup("/npc/morgan_left", gp.tileSize, gp.tileSize);
        up2 = setup("/npc/morgan_left", gp.tileSize, gp.tileSize);
        down1 = setup("/npc/morgan_left", gp.tileSize, gp.tileSize);
        down2 = setup("/npc/morgan_left", gp.tileSize, gp.tileSize);
        left1 = setup("/npc/morgan_left", gp.tileSize, gp.tileSize);
        left2 = setup("/npc/morgan_left", gp.tileSize, gp.tileSize);
        right1 = setup("/npc/morgan_right", gp.tileSize, gp.tileSize);
        right2 = setup("/npc/morgan_right", gp.tileSize, gp.tileSize);

    }

    public void speak() {
        super.speak();
        gp.gameState = gp.tradeState;
        gp.ui.npc = this;

    }

    public void setDialogue() {
        dialogue[0] = "He he, so you found me.\nOr you want some good stuff ?";
        dialogue[1] = "Go ahead.";
        dialogue[2] = "Don't be shy.";
        dialogue[3] = "Goodluck for gacha.";
        dialogue[4] = "No SQs...";

    }

    public void setItem() {
        inventory.add(new OBJ_hp(gp));
        inventory.add(new OBJ_Lancer(gp));
        inventory.add(new OBJ_Mana(gp));
        inventory.add(new OBJ_Weapon(gp));
    }
}
