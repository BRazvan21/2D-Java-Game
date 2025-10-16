package Main;

import entity.NPC_OldMan;

public class AssetSetter {

    GamePanel qp;

    public AssetSetter(GamePanel qp) {
        this.qp = qp;
    }

    public void setObject() {

    }

    public void setNPC(){

        qp.npc[0] = new NPC_OldMan(qp);
        qp.npc[0].worldX = qp.tileSize*21;
        qp.npc[0].worldY = qp.tileSize*21;
    }
}
