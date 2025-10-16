package object;

import Main.GamePanel;
import Main.UtilityTool;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {

    public BufferedImage image, image2, image3;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    UtilityTool uTool = new UtilityTool();

    public void draw(Graphics2D g2, GamePanel qp){

        int screenX = worldX - qp.player.worldX + qp.player.screenX;
        int screenY = worldY - qp.player.worldY + qp.player.screenY;

        if(worldX + qp.tileSize > qp.player.worldX - qp.player.screenX &&
           worldX - qp.tileSize < qp.player.worldX + qp.player.screenX &&
           worldY + qp.tileSize > qp.player.worldY - qp.player.screenY &&
           worldY - qp.tileSize < qp.player.worldY + qp.player.screenY) {

            g2.drawImage(image, screenX, screenY, qp.tileSize, qp.tileSize, null);
        }
    }
}
