package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.time.Year;
import java.util.ArrayList;
import java.util.concurrent.Flow.Subscription;

import javax.imageio.ImageIO;

import entity.Entity;
import object.OBJ_Coin;
import object.OBJ_Heart;
import object.OBJ_Mana;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font incon;
    UtilityTool uTool = new UtilityTool();
    BufferedImage background;
    public boolean messageOn;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public boolean end = false;
    public String currentDialogue = "";
    public int commandNum = 0;
    public int titleScreenState = 0;// 0:menu 1:Difficulty
    BufferedImage heart_full, heart_half, heart_empty, coin, fullmana, nomana;
    public int playerslotCol = 0;
    public int playerslotRow = 0;
    public int npcslotCol = 0;
    public int npcslotRow = 0;
    public int subState = 0;
    public int counter;

    public Entity npc;

    public UI(GamePanel gp) {

        this.gp = gp;
        InputStream is = getClass().getResourceAsStream("/font/Inconsolata.ttf");
        try {

            incon = Font.createFont(Font.TRUETYPE_FONT, is);

        } catch (Exception e) {
            e.printStackTrace();
            ;

        }
        // OBJ_Key key = new OBJ_Key(gp);
        // keyImage = key.image;
        // Create HUD Object
        Entity heart = new OBJ_Heart(gp);
        Entity mana = new OBJ_Mana(gp);
        Entity browncoin = new OBJ_Coin(gp);
        heart_full = heart.image;
        heart_half = heart.image1;
        heart_empty = heart.image2;
        fullmana = mana.image;
        nomana = mana.image2;
        coin = browncoin.down1;

    }

    public void showMessage(String text) {
        message.add(text);
        messageCounter.add(0);
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(incon);
        g2.setColor(Color.white);
        // Title State
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
        if (gp.gameState == gp.playState) {
            drawMessage();
            drawPlayerLife();
        }
        // Pause state
        if (gp.gameState == gp.pauseState) {
            drawPlayerLife();
            drawPauseScreen();

        }
        if (gp.gameState == gp.characterState) {
            drawCharacterScreen();
            drawInventory(gp.player, true);

        }
        // Dialogue state
        if (gp.gameState == gp.dialogueState) {
            drawDialogueScreen();
        }
        // Option Screen
        if (gp.gameState == gp.optionState) {
            drawOptionScreen();
        }
        if (gp.gameState == gp.gameoverState) {
            drawGameOver();
        }
        // Transition State
        if (gp.gameState == gp.transitionState) {
            drawTransition();
        }
        // Trade State
        if (gp.gameState == gp.tradeState) {
            drawTradeScreen();
        }
        // Sleep
        if (gp.gameState == gp.sleepState) {
            drawSleepScreen();
        }

    }

    public void drawTradeScreen() {
        // State
        switch (subState) {
            case 0:
                trade_select();
                break;
            case 1:
                trade_buy();
                break;
            case 2:
                trade_sell();
                break;
        }
        gp.key.enter = false;
    }

    public void trade_select() {
        drawDialogueScreen();

        // Draw window
        int x = gp.tileSize * 15;
        int y = gp.tileSize * 7;
        int width = gp.tileSize * 4;
        int height = (int) (gp.tileSize * 4);
        drawSubWindow(x, y, width, height);

        // Draw title
        x += gp.tileSize;
        y += gp.tileSize;
        g2.drawString("Buy", x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - 25, y);
            if (gp.key.enter == true) {
                subState = 1;
            }
        }
        y += gp.tileSize;

        g2.drawString("Sell", x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - 25, y);
            if (gp.key.enter == true) {
                subState = 2;
            }
        }
        y += gp.tileSize;

        g2.drawString("Leave", x, y);
        if (commandNum == 2) {
            g2.drawString(">", x - 25, y);
            if (gp.key.enter == true) {
                commandNum = 0;
                gp.gameState = gp.dialogueState;
                currentDialogue = "Come again, hehe.";
            }
        }
        y += gp.tileSize;

    }

    public void trade_buy() {
        // Draw player inventory
        drawInventory(gp.player, false);
        // Draw npc inventory
        drawInventory(npc, true);

        // Draw hint window
        int x = gp.tileSize * 2;
        int y = gp.tileSize * 10;
        int width = gp.tileSize * 5;
        int height = gp.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("[ESC] Back", x + 25, y + 60);

        // Draw player coin
        x = gp.tileSize * 12;
        y = gp.tileSize * 10;
        width = gp.tileSize * 5;
        height = gp.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("Your Coins: " + gp.player.coins, x + 25, y + 60);

        // Draw price

        int itemIndex = getItemIndex(npcslotCol, npcslotRow);
        if (itemIndex < npc.inventory.size()) {
            x = (int) (gp.tileSize * 8);
            y = (int) (gp.tileSize);
            width = (int) (gp.tileSize * 2.5);
            height = gp.tileSize;
            drawSubWindow(x, y, width, height);
            g2.drawImage(coin, x + 10, y + 8, 32, 32, null);

            int price = npc.inventory.get(itemIndex).price;
            String text = "" + price;
            x = getXforWindowRightText(text, gp.tileSize * 10);
            g2.drawString(text, x, y + 32);

            // Buy item
            if (gp.key.enter == true) {
                if (npc.inventory.get(itemIndex).price > gp.player.coins) {
                    subState = 0;
                    gp.gameState = gp.dialogueState;
                    currentDialogue = "Oh my.\nYou don't have enough\nmoney, darling.";
                    drawDialogueScreen();
                } else {
                    if (gp.player.canObtain(npc.inventory.get(itemIndex)) == true) {
                        gp.player.coins -= npc.inventory.get(itemIndex).price;
                        gp.playSE(13);
                    } else {
                        subState = 0;
                        gp.gameState = gp.dialogueState;
                        currentDialogue = "Oh my my.\nYou bag is full,darling.";
                    }
                }
                // if (gp.player.inventory.size() == gp.player.maxInventorySize) {
                // subState = 0;
                // gp.gameState = gp.dialogueState;
                // currentDialogue = "Oh my my.\nYou bag is full,darling.";

                // } else {
                // gp.player.coins -= npc.inventory.get(itemIndex).price;
                // gp.player.inventory.add(npc.inventory.get(itemIndex));
                // gp.playSE(13);
                // }

            }

        }
    }

    public void trade_sell() {
        // Draw player Inventory
        drawInventory(gp.player, true);

        int x;
        int y;
        int width;
        int height;

        x = gp.tileSize * 2;
        y = gp.tileSize * 10;
        width = gp.tileSize * 5;
        height = gp.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("[ESC] Back", x + 25, y + 60);

        // Draw player coin
        x = gp.tileSize * 12;
        y = gp.tileSize * 10;
        width = gp.tileSize * 5;
        height = gp.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("Your Coins: " + gp.player.coins, x + 25, y + 60);

        // Draw price

        int itemIndex = getItemIndex(playerslotCol, playerslotRow);
        if (itemIndex < gp.player.inventory.size()) {
            // Draw window
            x = (int) (gp.tileSize * 8);
            y = (int) (gp.tileSize);
            width = (int) (gp.tileSize * 2.5);
            height = gp.tileSize;
            drawSubWindow(x, y, width, height);
            g2.drawImage(coin, x + 10, y + 8, 32, 32, null);

            int price = gp.player.inventory.get(itemIndex).price / 2;
            String text = "" + price;
            x = getXforWindowRightText(text, gp.tileSize * 10);
            g2.drawString(text, x, y + 32);

            // Sell item
            if (gp.key.enter == true) {
                if (gp.player.inventory.get(itemIndex) == gp.player.currentWeapon) {
                    commandNum = 0;
                    subState = 0;
                    gp.gameState = gp.dialogueState;
                    currentDialogue = "You cannot sell an equipped item, darling.";

                } else {
                    if (gp.player.inventory.get(itemIndex).amount > 1) {
                        gp.player.inventory.get(itemIndex).amount--;
                        gp.playSE(13);
                    } else {
                        gp.player.inventory.remove(itemIndex);
                        gp.playSE(13);
                    }

                    gp.player.coins += price;
                }
            }
        }

    }

    public void drawTransition() {
        counter++;
        g2.setColor(new Color(0, 0, 0, counter * 5));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        if (counter == 50) {
            counter = 0;
            gp.gameState = gp.playState;
            gp.currentMap = gp.eh.tempMap;
            gp.player.worldX = gp.tileSize * gp.eh.tempCol;
            gp.player.worldY = gp.tileSize * gp.eh.tempRow;
            gp.eh.previousEventX = gp.player.worldX;
            gp.eh.previousEventY = gp.player.worldY;
        }
    }

    // Draw player life
    public void drawPlayerLife() {
        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;
        int i = 0;
        // Draw max heart;
        while (i < (gp.player.maxHP / 2)) {
            g2.drawImage(heart_empty, x, y, null);
            i++;
            x += gp.tileSize;
        }
        // reset
        x = gp.tileSize / 2;
        y = gp.tileSize / 2;
        i = 0;
        // Draw more content
        while (i < gp.player.HP) {
            g2.drawImage(heart_half, x, y, null);
            i++;
            if (i < gp.player.HP) {
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += gp.tileSize;
        }
        // Draw mana
        x = gp.tileSize / 2;
        y = gp.tileSize * 2;
        i = 0;
        while (i < gp.player.maxMP) {
            g2.drawImage(nomana, x, y, null);
            i++;
            x += 55;
        }
        x = gp.tileSize / 2;
        y = gp.tileSize * 2;
        i = 0;
        while (i < gp.player.MP) {
            g2.drawImage(fullmana, x, y, null);
            i++;
            x += 55;
        }

    }

    public void drawInventory(Entity entity, boolean cursor) {
        int frameX = 0;
        int frameY = 0;
        int frameWidth = 0;
        int frameHeight = 0;
        int slotCol = 0;
        int slotRow = 0;

        if (entity == gp.player) {
            frameX = gp.tileSize * 12;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize * 5;
            frameHeight = gp.tileSize * 6;
            slotCol = playerslotCol;
            slotRow = playerslotRow;
        } else {
            frameX = gp.tileSize * 2;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize * 5;
            frameHeight = gp.tileSize * 6;
            slotCol = npcslotCol;
            slotRow = npcslotRow;
        }
        // Frame
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // Slot
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        // Draw item
        for (int i = 0; i < entity.inventory.size(); i++) {

            // Equip
            if (entity.inventory.get(i) == entity.currentWeapon ||
                    entity.inventory.get(i) == entity.currentLight) {
                g2.setColor(new Color(240, 20, 30));
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
            }

            g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);
            // Draw amount

            if (entity.inventory.get(i).amount > 1 && entity == gp.player) {

                g2.setFont(g2.getFont().deriveFont(20F));
                int amountX;
                int amountY;

                String s = "" + entity.inventory.get(i).amount;
                amountX = getXforWindowRightText(s, slotX + 44);
                amountY = slotY + gp.tileSize;
                // Shadow

                g2.setColor(new Color(60, 60, 60));
                g2.drawString(s, amountX, amountY);
                // Text
                g2.setColor(Color.white);
                g2.drawString(s, amountX - 2, amountY - 2);
            }
            slotX += gp.tileSize;

            if (i == 3 || i == 6 || i == 9) {
                slotX = slotXstart;
                slotY += gp.tileSize;
            }
        }

        // Cursor
        if (cursor == true) {
            int cursorX = slotXstart + (gp.tileSize * slotCol);
            int cursorY = slotYstart + (gp.tileSize * slotRow);
            int cursoWidth = gp.tileSize;
            int cursorHeight = gp.tileSize;
            // Draw cursor
            g2.setColor(Color.white);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(cursorX, cursorY, cursoWidth, cursorHeight, 10, 10);
            // Description

            int dframeX = frameX;
            int dframeY = frameY + frameHeight;
            int dframeWidth = frameWidth;
            int dframeHeight = gp.tileSize * 3;

            int textX = dframeX + 20;
            int textY = dframeY + gp.tileSize;
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20F));
            int itemIndex = getItemIndex(slotCol, slotRow);
            if (itemIndex < entity.inventory.size()) {

                drawSubWindow(dframeX, dframeY, dframeWidth, dframeHeight);
                for (String line : entity.inventory.get(itemIndex).description.split("\n")) {
                    g2.drawString(entity.inventory.get(itemIndex).description, textX, textY);
                    textY += 28;
                }
            }

        }
    }

    public int getItemIndex(int slotCol, int slotRow) {
        int itemIndex = slotCol + (slotRow * 4);
        return itemIndex;
    }

    public void drawMessage() {
        int messageX = gp.tileSize;
        int messageY = gp.tileSize * 4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));

        for (int i = 0; i < message.size(); i++) {
            if (message.get(i) != null) {

                g2.setColor(Color.black);
                g2.drawString(message.get(i), messageX + 2, messageY + 2);
                g2.setColor(Color.white);
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);
                messageY += 50;

                if (messageCounter.get(i) > 120) {
                    message.remove(i);
                    messageCounter.remove(i);

                }
            }
        }

    }

    public void drawTitleScreen() {
        // Title background
        if (titleScreenState == 0) {
            try {
                background = ImageIO.read(getClass().getResourceAsStream("/tiles/background.png"));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            background = uTool.scaleImage(background, gp.screenWidth, gp.screenHeight);
            g2.drawImage(background, 0, 0, null);
            // g2.setColor(new Color(70, 120, 200));
            // g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            // Title name

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 90F));
            String text = "2D Adventure";
            int x = getXforCenteredText(text);
            int y = gp.tileSize * 4;
            // Shadow color
            g2.setColor(Color.black);
            g2.drawString(text, x + 5, y + 5);

            // Main color
            g2.setColor(Color.white);
            g2.drawString(text, x, y);

            // // Print player
            // x = gp.screenWidth / 2;
            // y += gp.tileSize * 2;
            // g2.drawImage(gp.player.down1, x - (gp.tileSize / 2), y, gp.tileSize * 2,
            // gp.tileSize * 2, null);

            // Title menu
            g2.setColor(Color.black);
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
            text = "NEW GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize * 3;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x - gp.tileSize / 2, y);
            }

            text = "LOAD GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x - gp.tileSize / 2, y);
            }

            text = "OPTION";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 2) {
                g2.drawString(">", x - gp.tileSize / 2, y);
            }

            text = "QUIT";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 3) {
                g2.drawString(">", x - gp.tileSize / 2, y);
            }
        } else if (titleScreenState == 1) {
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(42F));

            String text = "Select difficulty.";
            int x = getXforCenteredText(text);
            int y = gp.tileSize * 3;
            g2.drawString(text, x, y);

            text = "EASY";
            x = getXforCenteredText(text);
            y += gp.tileSize * 3;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "HARD";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "EVIL";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 2) {
                g2.drawString(">", x - gp.tileSize, y);
            }
            text = "Back";
            x = getXforCenteredText(text);
            y += gp.tileSize * 2;
            g2.drawString(text, x, y);
            if (commandNum == 3) {
                g2.drawString(">", x - gp.tileSize, y);
            }

        }

    }

    public void drawCharacterScreen() {
        // Create sub window
        final int frameX = gp.tileSize * 2, frameY = gp.tileSize,
                frameWidth = gp.tileSize * 5, frameHeight = gp.tileSize * 10;
        {
            drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        }
        // Text
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(24F));

        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 35;

        // Name
        g2.drawString("Level", textX, textY);
        textY += lineHeight * 2;
        g2.drawString("HP", textX, textY);
        textY += lineHeight;
        g2.drawString("MP", textX, textY);
        textY += lineHeight;
        g2.drawString("Strength", textX, textY);
        textY += lineHeight;
        g2.drawString("Dexterity", textX, textY);
        textY += lineHeight;
        g2.drawString("Attack", textX, textY);
        textY += lineHeight;
        g2.drawString("Defense", textX, textY);
        textY += lineHeight;
        g2.drawString("Exp", textX, textY);
        textY += lineHeight;
        g2.drawString("Next Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Coins", textX, textY);
        textY += lineHeight;
        g2.drawString("Weapon", textX, textY);
        textY += lineHeight;

        // Value
        int tailX = (frameX + frameWidth) - 55;
        textY = (frameY + gp.tileSize);
        String value;
        value = String.valueOf(gp.player.Level);
        textX = getXforWindowRightText(value, tailX);
        g2.drawString(value, tailX, textY);
        textY += lineHeight * 2;

        value = String.valueOf(gp.player.HP + "/" + gp.player.maxHP);
        textX = getXforWindowRightText(value, tailX);
        g2.drawString(value, tailX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.MP + "/" + gp.player.maxMP);
        textX = getXforWindowRightText(value, tailX);
        g2.drawString(value, tailX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.Strength);
        textX = getXforWindowRightText(value, tailX);
        g2.drawString(value, tailX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.Dexterity);
        textX = getXforWindowRightText(value, tailX);
        g2.drawString(value, tailX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.attack);
        textX = getXforWindowRightText(value, tailX);
        g2.drawString(value, tailX, textY);

        textY += lineHeight;
        value = String.valueOf(gp.player.defense);
        textX = getXforWindowRightText(value, tailX);
        g2.drawString(value, tailX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.exp);
        textX = getXforWindowRightText(value, tailX);
        g2.drawString(value, tailX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.nextLevelexp);
        textX = getXforWindowRightText(value, tailX);
        g2.drawString(value, tailX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.coins);
        textX = getXforWindowRightText(value, tailX);
        g2.drawString(value, tailX, textY);
        textY += lineHeight;

        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize * 2, textY, null);
        textY += gp.tileSize;
    }

    public void drawPauseScreen() {
        String text = "PAUSED";

        int x = getXforCenteredText(text);
        int y = gp.screenHeight / 2;

        g2.drawString(text, x, y);

    }

    public void drawOptionScreen() {
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));

        // sub window

        int frameX = gp.tileSize * 6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 8;
        int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (subState) {
            case 0:
                option_top(frameX, frameY);
                break;
            case 1:
                option_control(frameX, frameY);
                break;
            case 2:
                option_confirm(frameX, frameY);
                break;
            case 3:
                break;
        }

        gp.key.enter = false;
    }

    public void drawSleepScreen() {
        counter++;
        if (counter < 120) {
            gp.eManager.lightning.filterAlpha += 0.001f;
            if (gp.eManager.lightning.filterAlpha > 1f) {
                gp.eManager.lightning.filterAlpha = 1f;
            }
        }
        if (counter >= 120) {
            gp.eManager.lightning.filterAlpha--;
            if (gp.eManager.lightning.filterAlpha < 0f) {
                gp.eManager.lightning.filterAlpha = 0;
                counter = 0;
                gp.eManager.lightning.dayState = gp.eManager.lightning.day;
                gp.gameState = gp.playState;

            }
        }
    }

    public void option_top(int frameX, int frameY) {
        int textX;
        int textY;

        // Title
        String text = "Option";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        // BGM
        textY += gp.tileSize * 2;
        textX = frameX + gp.tileSize;
        g2.drawString("Music", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 18, textY);
        }
        // SE
        textY += gp.tileSize;
        g2.drawString("SE", textX, textY);
        if (commandNum == 1) {
            g2.drawString(">", textX - 18, textY);
        }
        textY += gp.tileSize;
        // Control
        g2.drawString("Control", textX, textY);
        if (commandNum == 2) {
            g2.drawString(">", textX - 18, textY);
            if (gp.key.enter == true) {
                subState = 1;
                commandNum = 0;
            }
        }

        // End game
        textY += gp.tileSize;
        g2.drawString("End Game", textX, textY);
        if (commandNum == 3) {
            g2.drawString(">", textX - 18, textY);
            if (gp.key.enter == true) {
                subState = 2;
                commandNum = 0;
            }
        }

        textY += gp.tileSize * 2;
        g2.drawString("Back", textX, textY);
        if (commandNum == 4) {
            g2.drawString(">", textX - 18, textY);
            if (gp.key.enter == true) {
                gp.gameState = gp.playState;
                commandNum = 0;
            }
        }

        // Music volumn
        textX = frameX + gp.tileSize * 5;
        textY = frameY + gp.tileSize * 2 + 24;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, 120, 24);// 120/10 = 12
        int volumnWidth = 12 * gp.music.volumnScale;
        g2.fillRect(textX, textY, volumnWidth, 24);
        // SE
        textY += gp.tileSize;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, 120, 24);
        volumnWidth = 12 * gp.se.volumnScale;
        g2.fillRect(textX, textY, volumnWidth, 24);

        gp.config.save();
    }

    public void option_control(int frameX, int frameY) {
        int textX;
        int textY;

        // Control
        String text = "Control";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString("Move", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Conform/Attack", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Shoot", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Character", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Pause", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Option", textX, textY);
        textY += gp.tileSize;

        textX = frameX + gp.tileSize * 6;
        textY = frameY + gp.tileSize * 2;

        g2.drawString("WASD", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Z", textX, textY);
        textY += gp.tileSize;
        g2.drawString("F", textX, textY);
        textY += gp.tileSize;
        g2.drawString("X", textX, textY);
        textY += gp.tileSize;
        g2.drawString("P", textX, textY);
        textY += gp.tileSize;
        g2.drawString("ESC", textX, textY);
        textY += gp.tileSize;
        // Back
        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize * 9;
        g2.drawString("Back", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.key.enter == true) {
                subState = 0;
                commandNum = 2;
            }
        }

    }

    public void option_confirm(int frameX, int frameY) {
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 3;

        currentDialogue = "Quit the Game and \nreturn to the \ntitle screen?";

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += gp.tileSize;
        }

        // Yes
        String text = "YES";
        textX = getXforCenteredText(text);
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 28, textY);
            if (gp.key.enter == true) {
                subState = 0;
                titleScreenState = 0;
                gp.gameState = gp.titleState;

            }

        }
        // No
        text = "NO";
        textX = getXforCenteredText(text);
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if (commandNum == 1) {
            g2.drawString(">", textX - 25, textY);
            if (gp.key.enter == true) {
                subState = 0;
                commandNum = 3;
            }

        }

    }

    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth / 2 - length / 2;
    }

    public int getXforWindowRightText(String text, int tailX) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
        return x;
    }

    public void drawDialogueScreen() {
        // Create Dialogue window
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - gp.tileSize * 4;
        int height = gp.tileSize * 5;
        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 40F));
        x += gp.tileSize + 5;
        y += gp.tileSize + 5;

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 50;
        }

    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 200);

        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 30, 30);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 30, 30);
    }

    public void drawGameOver() {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 100F));

        text = "YOU DIED";
        g2.setColor(Color.black);
        x = getXforCenteredText(text);
        y = gp.tileSize * 4;
        g2.drawString(text, x, y);
        // Main
        g2.setColor(Color.white);
        g2.drawString(text, x - 4, y - 4);

        // Retry
        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Retry";
        x = getXforCenteredText(text);
        y += gp.tileSize * 4;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - 40, y);
        }
        // Back to title screen
        g2.setFont(g2.getFont().deriveFont(50f));
        text = "QUIT";
        x = getXforCenteredText(text);
        y += 55;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - 40, y);
        }
    }
}
