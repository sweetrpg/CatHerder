package com.sweetrpg.catherder.common.network.packet.data;

public class CatNameData extends CatData {

    public final String name;

    public CatNameData(int entityId, String name) {
        super(entityId);
        this.name = name;
    }
}
