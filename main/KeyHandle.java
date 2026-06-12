package main;

import java.awt.event.KeyListener;
import java.security.Key;
import java.awt.event.KeyEvent;

public class KeyHandle implements KeyListener {
    public boolean up, down, left, right, enter, shot;
    boolean checkDrawTime = false;
    GamePanel gp;

    public KeyHandle(GamePanel gp) {
        this.gp = gp;

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        // Title State
        if (gp.gameState == gp.titleState) {
            titleStateScreen(code);
        }
        // Play State
        else if (gp.gameState == gp.playState) {
            playStateScreen(code);
            // Pause
        } else if (gp.gameState == gp.pauseState) {
            pauseStateScreen(code);
            // Dialogue State
        } else if (gp.gameState == gp.dialogueState) {
            dialogueStateScreen(code);
        }
        // Character State
        else if (gp.gameState == gp.characterState) {
            characterStateScreen(code);

        } else if (gp.gameState == gp.optionState) {
            optionStateScreen(code);
        } else if (gp.gameState == gp.gameoverState) {
            gameOverStateScreen(code);
        } else if (gp.gameState == gp.tradeState) {
            tradeStateScreen(code);
        }

    }

    public void tradeStateScreen(int code) {
        if (code == KeyEvent.VK_V || code == KeyEvent.VK_ENTER) {
            enter = true;

        }
        if (gp.ui.subState == 0) {
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2;

                }
                gp.playSE(3);
            }
            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;

                }
                gp.playSE(3);
            }
        }
        if (gp.ui.subState == 1) {
            npcInventory(code);
            if (code == KeyEvent.VK_ESCAPE) {
                gp.ui.subState = 0;
            }
        }
        if (gp.ui.subState == 2) {
            playerInventory(code);
            if (code == KeyEvent.VK_ESCAPE) {
                gp.ui.subState = 0;
            }

        }
    }

    public void titleStateScreen(int code) {

        if (gp.ui.titleScreenState == 0) {

            if (code == KeyEvent.VK_W) {

                if (gp.ui.commandNum > 0) {
                    gp.ui.commandNum--;
                }

            }
            if (code == KeyEvent.VK_S) {
                if (gp.ui.commandNum < 3) {
                    gp.ui.commandNum++;
                }
            }
            if (code == KeyEvent.VK_V || code == KeyEvent.VK_ENTER) {

                if (gp.ui.commandNum == 0) {
                    gp.ui.titleScreenState = 1;
                }
                if (gp.ui.commandNum == 1) {
                    // add later
                }
                if (gp.ui.commandNum == 2) {
                    gp.gameState = gp.optionState;
                }
                if (gp.ui.commandNum == 3) {
                    System.exit(0);
                }
            }
        } else if (gp.ui.titleScreenState == 1) {

            if (code == KeyEvent.VK_W) {

                if (gp.ui.commandNum > 0) {
                    gp.ui.commandNum--;
                }

            }
            if (code == KeyEvent.VK_S) {
                if (gp.ui.commandNum < 3) {
                    gp.ui.commandNum++;
                }
            }
            if (code == KeyEvent.VK_V || code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 0) {
                    gp.gameMode = gp.easy;
                    gp.gameState = gp.playState;

                }
                if (gp.ui.commandNum == 1) {
                    gp.gameMode = gp.hard;
                    gp.gameState = gp.playState;

                }
                if (gp.ui.commandNum == 2) {
                    gp.gameMode = gp.evil;
                    gp.gameState = gp.playState;

                }
                if (gp.ui.commandNum == 3) {
                    gp.ui.titleScreenState = 0;
                }
            }
        }
    }

    public void playStateScreen(int code) {
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            up = true;
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            left = true;
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            down = true;
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            right = true;
        }
        // Debug
        if (code == KeyEvent.VK_T) {
            if (checkDrawTime == false) {
                checkDrawTime = true;
            } else
                checkDrawTime = false;
        }
        if (code == KeyEvent.VK_R) {
            int map = gp.currentMap;

            ;
            switch (map) {
                case 0:
                    gp.tileM.loadMap("/maps/world2.txt", 0);
                    break;
                case 1:
                    gp.tileM.loadMap("/maps/map1.txt", 1);
                    break;
                case 2:
                    gp.tileM.loadMap("/maps/map01.txt", 2);
                    break;
                case 3:
                    gp.tileM.loadMap("/maps/map3.txt", 3);
                    break;
                case 4:
                    gp.tileM.loadMap("/maps/map5.txt", 4);
                    break;
                case 5:
                    gp.tileM.loadMap("/maps/map_06.txt", 5);
                    break;

            }
        }

        // Pause State
        if (code == KeyEvent.VK_P) {
            gp.gameState = gp.pauseState;
        }
        if (code == KeyEvent.VK_V || code == KeyEvent.VK_ENTER) {
            enter = true;
        }
        if (code == KeyEvent.VK_X) {
            gp.gameState = gp.characterState;
        }
        if (code == KeyEvent.VK_F) {
            shot = true;
        }
        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.optionState;
        }
    }

    public void optionStateScreen(int code) {
        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.playState;
        }
        if (code == KeyEvent.VK_ENTER) {
            enter = true;
        }
        int maxcommandNum = 0;
        switch (gp.ui.subState) {
            case 0:
                maxcommandNum = 4;
                break;
            case 2:
                maxcommandNum = 1;
                break;

        }

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = maxcommandNum;
            }
            gp.playSE(3);
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > maxcommandNum) {
                gp.ui.commandNum = 0;

            }
            gp.playSE(3);
        }
        if (code == KeyEvent.VK_A) {
            if (gp.ui.subState == 0) {
                if (gp.ui.commandNum == 0 && gp.music.volumnScale > 0) {
                    gp.music.volumnScale--;
                    gp.music.checkVolumn();
                    gp.playSE(4);
                }

                if (gp.ui.commandNum == 1 && gp.se.volumnScale > 0) {
                    gp.se.volumnScale--;
                    gp.playSE(4);
                }
            }
        }
        if (code == KeyEvent.VK_D) {
            if (gp.ui.subState == 0) {
                if (gp.ui.commandNum == 0 && gp.music.volumnScale < 10) {
                    gp.music.volumnScale++;
                    gp.music.checkVolumn();
                    gp.playSE(4);
                }

                if (gp.ui.commandNum == 1 && gp.music.volumnScale < 10) {
                    gp.se.volumnScale++;
                    gp.playSE(4);
                }
            }
        }

    }

    public void pauseStateScreen(int code) {
        if (code == KeyEvent.VK_P) {

            gp.gameState = gp.playState;

        }
    }

    public void characterStateScreen(int code) {

        if (code == KeyEvent.VK_X) {
            gp.gameState = gp.playState;
        }

        if (code == KeyEvent.VK_V) {
            gp.player.selectItem();
        }
        playerInventory(code);

    }

    public void playerInventory(int code) {
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            if (gp.ui.playerslotRow != 0) {
                gp.ui.playerslotRow--;
                gp.playSE(8);
            }

        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            if (gp.ui.playerslotRow != 4) {
                gp.ui.playerslotRow++;
                gp.playSE(8);
            }

        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            if (gp.ui.playerslotCol != 0) {
                gp.ui.playerslotCol--;
                gp.playSE(8);
            }

        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            if (gp.ui.playerslotCol != 3) {
                gp.ui.playerslotCol++;
                gp.playSE(8);
            }

        }
    }

    public void npcInventory(int code) {
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            if (gp.ui.npcslotRow != 0) {
                gp.ui.npcslotRow--;
                gp.playSE(8);
            }

        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            if (gp.ui.npcslotRow != 4) {
                gp.ui.npcslotRow++;
                gp.playSE(8);
            }

        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            if (gp.ui.npcslotCol != 0) {
                gp.ui.npcslotCol--;
                gp.playSE(8);
            }

        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            if (gp.ui.npcslotCol != 3) {
                gp.ui.npcslotCol++;
                gp.playSE(8);
            }

        }
    }

    public void dialogueStateScreen(int code) {
        if (code == KeyEvent.VK_V) {
            gp.gameState = gp.playState;
        }
    }

    public void gameOverStateScreen(int code) {
        if (code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 1;
            }
            gp.playSE(12);
        }
        if (code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 1) {
                gp.ui.commandNum = 0;
            }
            gp.playSE(12);
        }
        if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_V) {
            if (gp.ui.commandNum == 0) {
                gp.gameState = gp.playState;
                gp.retry();
                gp.playMusic(11);

            } else if (gp.ui.commandNum == 1) {
                gp.ui.titleScreenState = 0;
                gp.gameState = gp.titleState;

                gp.restart();
            }
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            up = false;

        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            left = false;
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            down = false;
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            right = false;

        }
        if (code == KeyEvent.VK_F || code == KeyEvent.VK_ENTER) {
            shot = false;

        }

    }
}
