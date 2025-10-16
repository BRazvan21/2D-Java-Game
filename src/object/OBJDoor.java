package object;

import Main.GamePanel;

import javax.imageio.ImageIO;

import java.io.IOException;

public class OBJDoor extends SuperObject{

    GamePanel qp;

    public OBJDoor(GamePanel qp) {

        this.qp = qp;
        name = "Door";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/door.png"));
            uTool.scaleImage(image, qp.tileSize, qp.tileSize);
        }catch(IOException e){
            e.printStackTrace();
        }
        collision = true;
    }
}
