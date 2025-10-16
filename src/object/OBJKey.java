package object;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJKey extends SuperObject{

    GamePanel qp;

    public OBJKey(GamePanel qp) {

        this.qp = qp;
        name = "Key";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));
            uTool.scaleImage(image, qp.tileSize, qp.tileSize);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
