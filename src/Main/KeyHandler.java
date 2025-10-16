package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel qp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, EnterPressed;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public KeyHandler(GamePanel qp) {
        this.qp = qp;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();
        //title state
        if(qp.gameState == qp.titleState){
            if(qp.ui.titleScreenState == 0){
                if(code == KeyEvent.VK_W){
                    qp.ui.commandNum--;
                    if(qp.ui.commandNum < 0){
                        qp.ui.commandNum = 3;
                    }
                }
                if(code == KeyEvent.VK_S){
                    qp.ui.commandNum++;
                    if(qp.ui.commandNum > 3){
                        qp.ui.commandNum = 0;
                    }
                }
                if(code == KeyEvent.VK_ENTER){
                    if(qp.ui.commandNum == 0){
                        qp.ui.titleScreenState= 1;
                    }
                    if(qp.ui.commandNum == 1){
                        //add later
                    }
                    if(qp.ui.commandNum == 2){
                        qp.ui.titleScreenState = 2;
                    }
                    if(qp.ui.commandNum == 3){
                        System.exit(0);
                    }
                }
            }
            else if(qp.ui.titleScreenState == 1){
                if(code == KeyEvent.VK_W){
                    qp.ui.commandNum--;
                    if(qp.ui.commandNum < 0){
                        qp.ui.commandNum = 3;
                    }
                }
                if(code == KeyEvent.VK_S){
                    qp.ui.commandNum++;
                    if(qp.ui.commandNum > 3){
                        qp.ui.commandNum = 0;
                    }
                }
                if(code == KeyEvent.VK_ENTER){
                    if(qp.ui.commandNum == 0){
                        System.out.println("Do some fighter specific stuff!");
                        qp.gameState = qp.playState;
                        qp.playMusic(0);
                    }
                    if(qp.ui.commandNum == 1){
                        System.out.println("Do some Thief specific stuff!");
                    }
                    if(qp.ui.commandNum == 2){
                        System.out.println("Do some sorcerer specific stuff!");
                    }
                    if(qp.ui.commandNum == 3){
                        qp.ui.titleScreenState = 0;
                    }
                }
            }
            else if(qp.ui.titleScreenState == 2){
                if(code == KeyEvent.VK_W){
                    qp.ui.commandNum--;
                    if(qp.ui.commandNum < 0){
                        qp.ui.commandNum = 3;
                    }
                }
                if(code == KeyEvent.VK_S){
                    qp.ui.commandNum++;
                    if(qp.ui.commandNum > 3){
                        qp.ui.commandNum = 0;
                    }
                }
                if(code == KeyEvent.VK_ENTER){
                    if(qp.ui.commandNum == 0){
                        //qp.playMusic(0);
                    }
                    if(qp.ui.commandNum == 1){

                    }
                    if(qp.ui.commandNum == 2){
                        qp.ui.titleScreenState = 3;
                    }
                    if(qp.ui.commandNum == 3){
                        qp.ui.titleScreenState = 0;
                    }
                }
            }
            else if(qp.ui.titleScreenState == 3){
                if(code == KeyEvent.VK_W){
                    qp.ui.commandNum--;
                    if(qp.ui.commandNum < 0){
                        qp.ui.commandNum = 5;
                    }
                }
                if(code == KeyEvent.VK_S){
                    qp.ui.commandNum++;
                    if(qp.ui.commandNum > 5){
                        qp.ui.commandNum = 0;
                    }
                }
                if(code == KeyEvent.VK_ENTER){
                    if(qp.ui.commandNum == 0){
                        //qp.playMusic(0);
                    }
                    if(qp.ui.commandNum == 1){

                    }
                    if(qp.ui.commandNum == 2){

                    }
                    if(qp.ui.commandNum == 3){

                    }
                    if(qp.ui.commandNum == 4){

                    }
                    if(qp.ui.commandNum == 5){
                        qp.ui.titleScreenState = 2;
                    }
                }
            }
        }

        //play state
        if(qp.gameState == qp.playState)
        {
            if(code == KeyEvent.VK_W){
                upPressed = true;
            }
            if(code == KeyEvent.VK_S){
                downPressed = true;
            }
            if(code == KeyEvent.VK_A){
                leftPressed = true;
            }
            if(code == KeyEvent.VK_D){
                rightPressed = true;
            }
            if(code == KeyEvent.VK_P){
                qp.gameState = qp.pauseState;
            }
            if(code == KeyEvent.VK_ENTER){
                EnterPressed = true;
            }
        }

        //pause state
        else if(qp.gameState == qp.pauseState){
            if(code == KeyEvent.VK_P){
                qp.gameState = qp.playState;
            }
        }

        //dialogue state
        else if(qp.gameState == qp.dialogueState){
            if(code == KeyEvent.VK_ENTER){
                qp.gameState = qp.playState;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W){
            upPressed = false;
        }
        if(code == KeyEvent.VK_S){
            downPressed = false;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = false;
        }
    }
}
