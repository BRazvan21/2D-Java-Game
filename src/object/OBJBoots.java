package object;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJBoots extends SuperObject{

    GamePanel qp;

    public OBJBoots(GamePanel qp) {

        this.qp = qp;
        name = "Boots";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/boots.png"));
            uTool.scaleImage(image, qp.tileSize, qp.tileSize);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
