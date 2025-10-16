package object;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJChest extends SuperObject{

    GamePanel qp;

    public OBJChest(GamePanel qp) {

        this.qp = qp;
        name = "Chest";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/chest.png"));
            uTool.scaleImage(image, qp.tileSize, qp.tileSize);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
