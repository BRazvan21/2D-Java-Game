package entity;

import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class entity {

    GamePanel qp;
    public int worldX, worldY;
    public int speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public int actionLockCounter = 0;
    String dialogues[] = new String[20];
    int dialogueIndex = 0;

    //Character Status
    public int maxLife;
    public int life;

    public entity(GamePanel qp)
    {
        this.qp = qp;
    }

    public void setAction(){}
    public void speak(){

        if(dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        qp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch(qp.player.direction)
        {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "right":
                direction = "left";
                break;
            case "left":
                direction = "right";
                break;
        }
    }
    public void update(){

        setAction();

        collisionOn = false;
        qp.cChecker.checkTile(this);
        qp.cChecker.checkObject(this, false);
        qp.cChecker.checkPlayer(this);

        if(collisionOn == false){

            switch(direction){
                case "up": worldY -= speed;break;
                case "down": worldY += speed;break;
                case "left": worldX -= speed;break;
                case "right": worldX += speed;break;
            }
    }

        spriteCounter++;
        if(spriteCounter > 12) {
            if(spriteNum == 1) {
                spriteNum = 2;
            }
            else if(spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }
    public void draw(Graphics2D g2){

        BufferedImage image = null;
        int screenX = worldX - qp.player.worldX + qp.player.screenX;
        int screenY = worldY - qp.player.worldY + qp.player.screenY;

        if(worldX + qp.tileSize > qp.player.worldX - qp.player.screenX &&
                worldX - qp.tileSize < qp.player.worldX + qp.player.screenX &&
                worldY + qp.tileSize > qp.player.worldY - qp.player.screenY &&
                worldY - qp.tileSize < qp.player.worldY + qp.player.screenY) {

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

            g2.drawImage(image, screenX, screenY, qp.tileSize, qp.tileSize, null);
        }
    }

    public BufferedImage setup(String imagePath){

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try{
            image = ImageIO.read(getClass().getResourceAsStream( imagePath +".png"));
            image = uTool.scaleImage(image, qp.tileSize, qp.tileSize);

        }catch(IOException e){
            e.printStackTrace();
        }
        return image;
    }
}
