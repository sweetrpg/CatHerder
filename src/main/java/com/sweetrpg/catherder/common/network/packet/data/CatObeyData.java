package com.sweetrpg.catherder.common.network.packet.data;

public class CatObeyData extends CatData {

    public final boolean obeyOthers;

    public CatObeyData(int entityId, boolean obeyOthers) {
        super(entityId);
        this.obeyOthers = obeyOthers;
    }
}
