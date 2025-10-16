package Main;

import java.awt.*;

public class EventHandler {

    GamePanel qp;
    Rectangle eventRect;
    int eventRectDefaultX, eventRectDefaultY;

    public EventHandler(GamePanel qp) {
        this.qp = qp;

        eventRect = new Rectangle();
        eventRect.x = 23;
        eventRect.y = 23;
        eventRect.width = 2;
        eventRect.height = 2;
        eventRectDefaultX = eventRect.x;
        eventRectDefaultY = eventRect.y;
    }

    public void checkEvent(){
        if(hit(27,16,"right") == true){damagePit(qp.dialogueState);}
        if(hit(27,16,"right") == true){teleport(qp.dialogueState);}
        if(hit(23,12,"up") == true) {healingPool(qp.dialogueState);}
    }

    public boolean hit(int eventCol, int eventRow, String reqDirection){
        boolean hit = false;

        qp.player.solidArea.x = qp.player.worldX + qp.player.solidArea.x;
        qp.player.solidArea.y = qp.player.worldY + qp.player.solidArea.y;
        eventRect.x = eventCol * qp.tileSize + eventRect.x;
        eventRect.y = eventRow * qp.tileSize + eventRect.y;

        if(qp.player.solidArea.intersects(eventRect)){
            if(qp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")){
                hit = true;
            }
        }

        qp.player.solidArea.x = qp.player.solidAreaDefaultX;
        qp.player.solidArea.y = qp.player.solidAreaDefaultY;
        eventRect.x = eventRectDefaultX;
        eventRect.y = eventRectDefaultY;

        return hit;
    }
    public void teleport(int gameState){

        qp.gameState = gameState;
        qp.ui.currentDialogue = "Teleport";
        qp.player.worldX = qp.tileSize * 37;
        qp.player.worldY = qp.tileSize * 10;
    }

    public void damagePit(int gameState){
        qp.gameState = gameState;
        qp.ui.currentDialogue = "You fall into a pit";
        qp.player.life -= 1;
    }

    public void healingPool(int gameState){

        if(qp.keyHandler.EnterPressed == true) {
            qp.gameState = gameState;
            qp.ui.currentDialogue = "You drink the water /nYour life has been recovered";
            qp.player.life = qp.player.maxLife;
        }
    }
}
