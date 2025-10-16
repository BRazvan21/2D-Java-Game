package Main;

import entity.entity;

public class CollisionChecker {

    GamePanel qp;

    public CollisionChecker(GamePanel qp) {
        this.qp = qp;
    }

    public void checkTile(entity entity) {

        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / qp.tileSize;
        int entityRightCol = entityRightWorldX / qp.tileSize;
        int entityTopRow = entityTopWorldY / qp.tileSize;
        int entityBottomRow = entityBottomWorldY / qp.tileSize;

        int tileNum1, tileNum2;

        switch(entity.direction)
        {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed)/qp.tileSize;
                tileNum1 = qp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = qp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if(qp.tileM.tile[tileNum1].collision == true || qp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed)/qp.tileSize;
                tileNum1 = qp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = qp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if(qp.tileM.tile[tileNum1].collision == true || qp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed)/qp.tileSize;
                tileNum1 = qp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = qp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if(qp.tileM.tile[tileNum1].collision == true || qp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed)/qp.tileSize;
                tileNum1 = qp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = qp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if(qp.tileM.tile[tileNum1].collision == true || qp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
        }
    }
    public int checkObject(entity entity, boolean player){

        int index = 999;

        for(int i = 0; i < qp.obj.length; i++){

            if(qp.obj[i] != null){

                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                qp.obj[i].solidArea.x = qp.obj[i].worldX + qp.obj[i].solidArea.x;
                qp.obj[i].solidArea.y = qp.obj[i].worldY + qp.obj[i].solidArea.y;

                switch(entity.direction){
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        if(entity.solidArea.intersects(qp.obj[i].solidArea)){
                            if(qp.obj[i].collision == true) {
                                entity.collisionOn = true;
                            }
                            if(player == true) {
                                index = i;
                            }
                        }
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        if(entity.solidArea.intersects(qp.obj[i].solidArea)){
                            if(qp.obj[i].collision == true) {
                                entity.collisionOn = true;
                            }
                            if(player == true) {
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if(entity.solidArea.intersects(qp.obj[i].solidArea)){
                            if(qp.obj[i].collision == true) {
                                entity.collisionOn = true;
                            }
                            if(player == true) {
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        if(entity.solidArea.intersects(qp.obj[i].solidArea)){
                            if(qp.obj[i].collision == true) {
                                entity.collisionOn = true;
                            }
                            if(player == true) {
                                index = i;
                            }
                        }
                        break;
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                qp.obj[i].solidArea.x = qp.obj[i].solidAreaDefaultX;
                qp.obj[i].solidArea.y = qp.obj[i].solidAreaDefaultY;
            }
        }
        return index;
    }
    public int checkEntity(entity Entity, entity[] target){
        int index = 999;

        for(int i = 0; i < target.length; i++){

            if(target[i] != null){

                Entity.solidArea.x = Entity.worldX + Entity.solidArea.x;
                Entity.solidArea.y = Entity.worldY + Entity.solidArea.y;

                target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
                target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;

                switch(Entity.direction){
                    case "up":
                        Entity.solidArea.y -= Entity.speed;
                        if(Entity.solidArea.intersects(target[i].solidArea)){
                            Entity.collisionOn = true;
                                index = i;
                        }
                        break;
                    case "down":
                        Entity.solidArea.y += Entity.speed;
                        if(Entity.solidArea.intersects(target[i].solidArea)){
                                Entity.collisionOn = true;
                                index = i;
                        }
                        break;
                    case "left":
                        Entity.solidArea.x -= Entity.speed;
                        if(Entity.solidArea.intersects(target[i].solidArea)){
                                Entity.collisionOn = true;
                                index = i;
                        }
                        break;
                    case "right":
                        Entity.solidArea.x += Entity.speed;
                        if(Entity.solidArea.intersects(target[i].solidArea)){
                                Entity.collisionOn = true;
                                index = i;
                        }
                        break;
                }
                Entity.solidArea.x = Entity.solidAreaDefaultX;
                Entity.solidArea.y = Entity.solidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
            }
        }
        return index;
    }
    public void checkPlayer(entity Entity){

            Entity.solidArea.x = Entity.worldX + Entity.solidArea.x;
            Entity.solidArea.y = Entity.worldY + Entity.solidArea.y;

                qp.player.solidArea.x = qp.player.worldX + qp.player.solidArea.x;
                qp.player.solidArea.y = qp.player.worldY + qp.player.solidArea.y;

                switch(Entity.direction){
                    case "up":
                        Entity.solidArea.y -= Entity.speed;
                        if(Entity.solidArea.intersects(qp.player.solidArea)){
                            Entity.collisionOn = true;
                        }
                        break;
                    case "down":
                        Entity.solidArea.y += Entity.speed;
                        if(Entity.solidArea.intersects(qp.player.solidArea)){
                            Entity.collisionOn = true;
                        }
                        break;
                    case "left":
                        Entity.solidArea.x -= Entity.speed;
                        if(Entity.solidArea.intersects(qp.player.solidArea)){
                            Entity.collisionOn = true;
                        }
                        break;
                    case "right":
                        Entity.solidArea.x += Entity.speed;
                        if(Entity.solidArea.intersects(qp.player.solidArea)){
                            Entity.collisionOn = true;
                        }
                        break;
                }
                Entity.solidArea.x = Entity.solidAreaDefaultX;
                Entity.solidArea.y = Entity.solidAreaDefaultY;
                qp.player.solidArea.x = qp.player.solidAreaDefaultX;
                qp.player.solidArea.y = qp.player.solidAreaDefaultY;
    }
}
