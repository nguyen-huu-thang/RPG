package main;

import entity.NPC_Merchant;
import entity.NPC_Oldman;
import monsters.Mons1;
import object.OBJ_Chest;
import object.OBJ_Coin;
import object.OBJ_Door;
import object.OBJ_Heart;
import object.OBJ_House;
import object.OBJ_Key;
import object.OBJ_Lancer;
import object.OBJ_Lantern;
import object.OBJ_Mana;
import object.OBJ_Table;
import object.OBJ_Tent;
import object.OBJ_hp;
import java.awt.Graphics2D;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        int mapNum = 0;
        // Map 0

        int i = 0;
        gp.obj[mapNum][i] = new OBJ_Coin(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 20;
        gp.obj[mapNum][i].worldY = gp.tileSize * 22;
        i++;
        gp.obj[mapNum][i] = new OBJ_Coin(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 20;
        gp.obj[mapNum][i].worldY = gp.tileSize * 20;
        i++;
        gp.obj[mapNum][i] = new OBJ_hp(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 30;
        gp.obj[mapNum][i].worldY = gp.tileSize * 28;
        i++;
        gp.obj[mapNum][i] = new OBJ_Key(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 33;
        gp.obj[mapNum][i].worldY = gp.tileSize * 28;
        i++;
        gp.obj[mapNum][i] = new OBJ_Door(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 44;
        gp.obj[mapNum][i].worldY = gp.tileSize * 32;
        i++;
        gp.obj[mapNum][i] = new OBJ_Chest(gp, new OBJ_Tent(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize * 3;
        gp.obj[mapNum][i].worldY = gp.tileSize * 1;
        i++;
        gp.obj[mapNum][i] = new OBJ_hp(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 12;
        gp.obj[mapNum][i].worldY = gp.tileSize * 32;
        i++;
        gp.obj[mapNum][i] = new OBJ_hp(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 31;
        gp.obj[mapNum][i].worldY = gp.tileSize * 12;

        i++;
        gp.obj[mapNum][i] = new OBJ_Lantern(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 15;
        gp.obj[mapNum][i].worldY = gp.tileSize * 12;
        // Map 1

        // Map 2
        mapNum = 2;
        i++;
        gp.obj[mapNum][i] = new OBJ_Table(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 18;
        gp.obj[mapNum][i].worldY = gp.tileSize * 15;

    }

    public void setNPC() {
        int mapNum = 0;
        int i = 0;
        gp.npc[mapNum][i] = new NPC_Oldman(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 23;
        gp.npc[mapNum][i].worldY = gp.tileSize * 22;
        // Map 1:
        mapNum = 1;
        i = 0;
        gp.npc[mapNum][i] = new NPC_Merchant(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 25;
        gp.npc[mapNum][i].worldY = gp.tileSize * 15;
        i++;
    }

    public void setMonster() {
        int mapNum = 0;
        // Map 0
        int i = 0;
        gp.mons[mapNum][i] = new Mons1(gp);
        gp.mons[mapNum][i].worldX = gp.tileSize * 24;
        gp.mons[mapNum][i].worldY = gp.tileSize * 26;
        i++;
        gp.mons[mapNum][i] = new Mons1(gp);
        gp.mons[mapNum][i].worldX = gp.tileSize * 29;
        gp.mons[mapNum][i].worldY = gp.tileSize * 9;
        i++;
        gp.mons[mapNum][i] = new Mons1(gp);
        gp.mons[mapNum][i].worldX = gp.tileSize * 14;
        gp.mons[mapNum][i].worldY = gp.tileSize * 16;
        i++;
        gp.mons[mapNum][i] = new Mons1(gp);
        gp.mons[mapNum][i].worldX = gp.tileSize * 34;
        gp.mons[mapNum][i].worldY = gp.tileSize * 21;
        i++;

        // Map 1
        mapNum = 1;
        i = 0;
        gp.mons[mapNum][i] = new Mons1(gp);
        gp.mons[mapNum][i].worldX = gp.tileSize * 19;
        gp.mons[mapNum][i].worldY = gp.tileSize * 4;
        i++;
        gp.mons[mapNum][i] = new Mons1(gp);
        gp.mons[mapNum][i].worldX = gp.tileSize * 13;
        gp.mons[mapNum][i].worldY = gp.tileSize * 15;
        i++;

        // Map 2

        // Map 3

        // Map 4

        // Map 5

    }

}
