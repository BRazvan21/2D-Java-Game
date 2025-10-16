package object;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Heart extends SuperObject {

    GamePanel qp;

    public OBJ_Heart(GamePanel qp) {

        this.qp = qp;
        name = "Heart";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/heart_full.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/objects/heart_half.png"));
            image3 = ImageIO.read(getClass().getResourceAsStream("/objects/heart_blank.png"));
            image = uTool.scaleImage(image, qp.tileSize, qp.tileSize);
            image2 = uTool.scaleImage(image2, qp.tileSize, qp.tileSize);
            image3 = uTool.scaleImage(image3, qp.tileSize, qp.tileSize);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
