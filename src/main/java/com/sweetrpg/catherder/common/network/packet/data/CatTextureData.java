package com.sweetrpg.catherder.common.network.packet.data;

public class CatTextureData extends CatData {

    public String hash;

    public CatTextureData(int entityId, String hash) {
        super(entityId);
        this.hash = hash;
    }

}
