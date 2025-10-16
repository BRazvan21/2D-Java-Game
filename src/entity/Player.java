package entity;

import Main.GamePanel;
import Main.KeyHandler;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends entity {

    KeyHandler keyH;
    public final int screenX;
    public final int screenY;

    public Player(GamePanel qp, KeyHandler keyH) {

        super(qp);
        this.keyH = keyH;

        screenX = qp.screenWidth / 2 - (qp.tileSize / 2);
        screenY = qp.screenHeight / 2 - (qp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues() {

        worldX = qp.tileSize * 23;
        worldY = qp.tileSize * 21;
        speed = 4;
        direction = "down";

        //PLAYER STATUS
        maxLife = 6;
        life = maxLife;
    }
    public void getPlayerImage()
    {
        up1 = setup("/Player/boy_up_1");
        up2 = setup("/Player/boy_up_2");
        down1 = setup("/Player/boy_down_1");
        down2 = setup("/Player/boy_down_2");
        left1 = setup("/Player/boy_left_1");
        left2 = setup("/Player/boy_left_2");
        right1 = setup("/Player/boy_right_1");
        right2 = setup("/Player/boy_right_2");
    }

    public void update() {

        if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {

            if(keyH.upPressed == true){
                direction = "up";
            }
            else if(keyH.downPressed == true){
                direction = "down";
            }
            else if(keyH.leftPressed == true){
                direction = "left";
            }
            else if(keyH.rightPressed == true){
                direction = "right";
            }

            collisionOn = false;
            qp.cChecker.checkTile(this);

            int objIndex = qp.cChecker.checkObject(this,true);
            pickUpObject(objIndex);

            //check npc collision
            int npcIndex = qp.cChecker.checkEntity(this,qp.npc);
            interactNPC(npcIndex);
            //CHECK EVENT
            qp.eHandler.checkEvent();

            qp.keyHandler.EnterPressed = false;

            if(collisionOn == false) {

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

            spriteCounter++;
            if(spriteCounter > 12)
            {
                if(spriteNum == 1)
                {
                    spriteNum = 2;
                }
                else if(spriteNum == 2)
                {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }
    public void pickUpObject(int i){

        if(i != 999) {

        }
    }

    public void interactNPC(int i){
        if(i != 999) {

            if (qp.keyHandler.EnterPressed == true) {
                qp.gameState = qp.dialogueState;
                qp.npc[i].speak();
            }
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch(direction){
            case "up":
                if(spriteNum == 1)
                {
                    image = up1;
                }
                if(spriteNum == 2)
                {
                    image = up2;
                }
                break;
            case "down":
                if(spriteNum == 1)
                {
                    image = down1;
                }
                if(spriteNum == 2)
                {
                    image = down2;
                }
                break;
            case "left":
                if(spriteNum == 1)
                {
                    image = left1;
                }
                if(spriteNum == 2)
                {
                    image = left2;
                }
                break;
            case "right":
                if(spriteNum == 1)
                {
                    image = right1;
                }
                if(spriteNum == 2)
                {
                    image = right2;
                }
                break;
        }
        g2.drawImage(image, screenX, screenY, null);
    }
}
