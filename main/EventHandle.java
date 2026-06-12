package main;

import java.util.Currency;

import entity.Entity;

public class EventHandle {

    GamePanel gp;
    EventReact eventReact[][][];
    int previousEventX, previousEventY;
    boolean canTouchEvent = true;

    int tempMap, tempCol, tempRow;

    public EventHandle(GamePanel gp) {
        this.gp = gp;

        eventReact = new EventReact[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        int map = 0;
        int col = 0;
        int row = 0;
        while (map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow) {
            eventReact[map][col][row] = new EventReact();
            eventReact[map][col][row].x = 23;
            eventReact[map][col][row].y = 23;
            eventReact[map][col][row].width = 10;
            eventReact[map][col][row].height = 10;
            eventReact[map][col][row].eventReactDefaultX = eventReact[map][col][row].x;
            eventReact[map][col][row].eventReactDefaultY = eventReact[map][col][row].y;

            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
                if (row == gp.maxWorldRow) {
                    row = 0;
                    map++;
                }
            }
        }

    }

    public void checkEvent() {

        // Check if the player is more than 1 tile away from last event.
        int xDistence = Math.abs(gp.player.worldX - previousEventX);
        int yDistence = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistence, yDistence);
        if (distance > gp.tileSize) {
            canTouchEvent = true;
        }
        if (canTouchEvent == true) {
            if (hit(0, 17, 11, "any")) {
                damagePit(0, 17, 11, gp.dialogueState);
            }

            else if (hit(0, 4, 6, "any")) {
                healingPool(0, 4, 6, gp.dialogueState);

            } else if (hit(0, 3, 3, "any")) {
                teleport(0, 3, 3, gp.dialogueState);
            }

            else if (hit(0, 21, 12, "any") == true) {
                goin(2, 11, 17);
            } else if (hit(2, 11, 17, "any") == true) {
                goin(0, 21, 12);
            } else if (hit(0, 26, 6, "any") == true) {
                goin(1, 10, 17);
            } else if (hit(1, 10, 17, "any") == true) {
                goin(0, 26, 6);
            } else if (hit(1, 25, 11, "any") == true) {
                goin(3, 0, 31);
            } else if (hit(3, 0, 31, "any") == true) {
                goin(1, 25, 11);
            } else if (hit(4, 0, 14, "any") == true) {
                goin(3, 49, 24);
            } else if (hit(3, 49, 24, "any") == true) {
                goin(4, 0, 14);
            } else if (hit(4, 0, 28, "any") == true) {
                goin(3, 49, 40);
            } else if (hit(3, 49, 40, "any") == true) {
                goin(4, 0, 28);
            } else if (hit(5, 0, 26, "any") == true) {
                goin(4, 49, 29);
            } else if (hit(4, 49, 29, "any") == true) {
                goin(5, 0, 26);
            }
        }

    }

    public boolean hit(int map, int col, int row, String reqDirection) {

        boolean hit = false;
        if (map == gp.currentMap) {
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
            eventReact[map][col][row].x = col * gp.tileSize + eventReact[map][col][row].x;
            eventReact[map][col][row].y = row * gp.tileSize + eventReact[map][col][row].y;

            if (gp.player.solidArea.intersects(eventReact[map][col][row])
                    && eventReact[map][col][row].eventDone == false) {
                if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                    hit = true;

                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;

                }
            }
            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            eventReact[map][col][row].x = eventReact[map][col][row].eventReactDefaultX;
            eventReact[map][col][row].y = eventReact[map][col][row].eventReactDefaultY;

        }

        return hit;
    }

    public void damagePit(int map, int col, int row, int gameState) {

        gp.gameState = gameState;
        gp.ui.currentDialogue = "You fell to a pit!\n -1 HP.";
        gp.player.HP--;
        gp.playSE(4);
        eventReact[map][col][row].eventDone = true;
        canTouchEvent = false;
    }

    public void healingPool(int map, int col, int row, int gameState) {
        if (gp.key.enter) {
            gp.player.attackCancel = true;
            gp.gameState = gameState;
            gp.ui.currentDialogue = "You drink the water.\nHeal full HP,MP";
            gp.as.setMonster();
            gp.player.HP = gp.player.maxHP;
            gp.player.MP = gp.player.maxMP;
            eventReact[map][col][row].eventDone = false;
        }

    }

    public void teleport(int map, int col, int row, int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "Teleport!";
        gp.playSE(6);
        gp.player.worldX = gp.tileSize * 32;
        gp.player.worldY = gp.tileSize * 32;
    }

    public void goin(int map, int col, int row) {
        gp.gameState = gp.transitionState;
        tempMap = map;
        tempCol = col;
        tempRow = row;

        // gp.currentMap = map;
        // gp.player.worldX = gp.tileSize * col;
        // gp.player.worldY = gp.tileSize * row;
        // previousEventX = gp.player.worldX;
        // previousEventY = gp.player.worldY;
        canTouchEvent = false;
        gp.playSE(1);

    }

    public void speak(Entity entity) {
        if (gp.key.enter == true) {
            gp.gameState = gp.dialogueState;
            gp.player.attackCancel = true;
            entity.speak();
        }
    }

}
