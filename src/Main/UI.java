package Main;

import object.OBJ_Heart;
import object.SuperObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UI {
    GamePanel qp;
    Graphics2D g2;
    Font maruMonica, purisaB;
    BufferedImage heart_full, heart_half, heart_blank;
    BufferedImage image, image2, image3, image4, image5;
    private int imgX, imgY;
    public static String[] ImageURL;

    UtilityTool uTool = new UtilityTool();
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean GameFinished = false;
    public String currentDialogue = "";
    public int commandNum = 0;
    public int titleScreenState = 0; //0: first screen 1:second screen
    public static List<TrackInfo> trackInfos;
    
    public UI(GamePanel qp){
        this.qp = qp;

        try {
            InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
            purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //CREATE HUD OBJECT
        SuperObject heart = new OBJ_Heart(qp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
    }

    public void showMessage(String text){

        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2){
        this.g2 = g2;

        g2.setFont(maruMonica);
        g2.setColor(Color.white);

        //title state
        if(qp.gameState == qp.titleState){
            drawTitleScreen();
        }
        //play state
        if(qp.gameState == qp.playState){
            drawPlayerLife();
        }
        //pause state
        if(qp.gameState == qp.pauseState){
            drawPlayerLife();
            drawPauseScreen();
        }
        //dialogue state
        if(qp.gameState == qp.dialogueState){
            drawPlayerLife();
            drawDialogueScreen();
        }
    }
    public void drawPlayerLife(){

        //qp.player.life = 3;

        int x = qp.tileSize/2;
        int y = qp.tileSize/2;
        int i = 0;
        //DRAW BLANK HEART
        while(i < qp.player.maxLife/2){
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += qp.tileSize;
        }
        x = qp.tileSize/2;
        y = qp.tileSize/2;
        i = 0;

        //DRAW CURRENT LIFE
        while(i < qp.player.life){
            g2.drawImage(heart_half, x, y, null);
            i++;
            if(i < qp.player.life){
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += qp.tileSize;
        }

    }

    public void drawTitleScreen() {
        //Title name
        if(titleScreenState == 0){
            g2.setColor(new Color(70, 120, 80));
            g2.fillRect(0,0,qp.screenWidth, qp.screenHeight);

            g2.setFont(g2.getFont().deriveFont(Font.BOLD,96F));
            String text = "My 2D Game";
            int x = getXforCenteredText(text);
            int y = qp.tileSize*3;
            //Shadows
            g2.setColor(Color.black);
            g2.drawString(text, x+5, y+5);
            //Main Color
            g2.setColor(Color.white);
            g2.drawString(text, x, y);

            //BLUE BOY IMAGE
            x = qp.screenWidth/2 - (qp.tileSize*2)/2;
            y += qp.tileSize*2;
            g2.drawImage(qp.player.down1,x,y, qp.tileSize*2, qp.tileSize*2, null);

            //MENU
            g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));

            text = "NEW GAME";
            x = getXforCenteredText(text);
            y += qp.tileSize*3.5;
            g2.drawString(text, x, y);
            if(commandNum == 0){
                g2.drawString(">", x-qp.tileSize, y);
            }

            text = "LOAD GAME";
            x = getXforCenteredText(text);
            y += qp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 1){
                g2.drawString(">", x-qp.tileSize, y);
            }

            text = "OPTIONS";
            x = getXforCenteredText(text);
            y += qp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 2){
                g2.drawString(">", x-qp.tileSize, y);
            }

            text = "QUIT";
            x = getXforCenteredText(text);
            y += qp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 3){
                g2.drawString(">", x-qp.tileSize, y);
            }
        }
        else if(titleScreenState == 1){
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(42F));

            String text = "Select your class";
            int x = getXforCenteredText(text);
            int y = qp.tileSize*3;
            g2.drawString(text, x, y);

            text = "Fighter";
            x = getXforCenteredText(text);
            y += qp.tileSize*3;
            g2.drawString(text, x, y);
            if(commandNum == 0){
                g2.drawString(">", x-qp.tileSize, y);
            }

            text = "Thief";
            x = getXforCenteredText(text);
            y += qp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 1){
                g2.drawString(">", x-qp.tileSize, y);
            }

            text = "Sorcerer";
            x = getXforCenteredText(text);
            y += qp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 2){
                g2.drawString(">", x-qp.tileSize, y);
            }

            text = "BACK";
            x = getXforCenteredText(text);
            y += qp.tileSize*2;
            g2.drawString(text, x, y);
            if(commandNum == 3){
                g2.drawString(">", x-qp.tileSize, y);
            }
        }
        else if(titleScreenState == 2){
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(42F));

            String text = "Options";
            int x = getXforCenteredText(text);
            int y = qp.tileSize*3;
            g2.drawString(text, x, y);

            text = "Sound vol";
            x = getXforCenteredText(text);
            y += qp.tileSize*3;
            g2.drawString(text, x, y);
            if(commandNum == 0){
                g2.drawString(">", x-qp.tileSize, y);
            }

            text = "Music vol";
            x = getXforCenteredText(text);
            y += qp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 1){
                g2.drawString(">", x-qp.tileSize, y);
            }

            text = "Spotify Playlist";
            x = getXforCenteredText(text);
            y += qp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 2){
                g2.drawString(">", x-qp.tileSize, y);
            }

            text = "BACK";
            x = getXforCenteredText(text);
            y += qp.tileSize*2;
            g2.drawString(text, x, y);
            if(commandNum == 3){
                g2.drawString(">", x-qp.tileSize, y);
            }
        }
        else if (titleScreenState == 3) {
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(42F));
            String text = "Spotify Playlist";
            int x = getXforCenteredText(text);
            int y = qp.tileSize + qp.tileSize / 2;
            g2.drawString(text, x, y);

            text = "Melodia 1";
            x = getXforCenteredText(text);
            y += qp.tileSize*2;
            g2.drawString(text, x - 3*qp.tileSize, y);
            if(commandNum == 0){
                g2.drawString(">", x-4*qp.tileSize, y);
            }

            text = "Melodia 2";
            x = getXforCenteredText(text);
            y += qp.tileSize + qp.tileSize / 2;
            g2.drawString(text, x - 3*qp.tileSize, y);
            if(commandNum == 1){
                g2.drawString(">", x-4*qp.tileSize, y);
            }

            text = "Melodia 3";
            x = getXforCenteredText(text);
            y += qp.tileSize + qp.tileSize / 2;
            g2.drawString(text, x - 3*qp.tileSize, y);
            if(commandNum == 2){
                g2.drawString(">", x-4*qp.tileSize, y);
            }
            text = "Melodia 4";
            x = getXforCenteredText(text);
            y += qp.tileSize + qp.tileSize / 2;
            g2.drawString(text, x - 3*qp.tileSize, y);
            if(commandNum == 3){
                g2.drawString(">", x-4*qp.tileSize, y);
            }
            text = "Melodia 5";
            x = getXforCenteredText(text);
            y += qp.tileSize + qp.tileSize / 2;
            g2.drawString(text, x - 3*qp.tileSize, y);
            if(commandNum == 4){
                g2.drawString(">", x-4*qp.tileSize, y);
            }
            text = "BACK";
            x = getXforCenteredText(text);
            y += qp.tileSize*2;
            g2.drawString(text, x, y);
            if(commandNum == 5){
                g2.drawString(">", x-qp.tileSize, y);
            }
        }
    }
    public void drawPauseScreen(){

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = qp.screenHeight/2;

        g2.drawString(text,x,y);
    }

    public void drawDialogueScreen(){

        int x = qp.tileSize*2;
        int y = qp.tileSize/2;
        int width = qp.screenWidth - (qp.tileSize*4);
        int height = qp.tileSize*4;

        drawSubWindow(x,y,width,height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,32F));
        x += qp.tileSize;
        y += qp.tileSize;

        for(String line : currentDialogue.split("/n")){
            g2.drawString(line,x,y);
            y += 40;
        }
    }
    public void drawSubWindow(int x, int y, int width, int height){

        Color c = new Color(0,0,0,210);
        g2.setColor(c);
        g2.fillRoundRect(x,y,width,height,35,35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5,y+5,width-10,height-10,25,25);
    }

    public int getXforCenteredText(String text){

        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = qp.screenWidth/2 - length/2;
        return x;
    }
}
