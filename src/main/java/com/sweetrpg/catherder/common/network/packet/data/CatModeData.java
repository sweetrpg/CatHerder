package com.sweetrpg.catherder.common.network.packet.data;

import com.sweetrpg.catherder.api.feature.EnumMode;

public class CatModeData extends CatData {

    public EnumMode mode;

    public CatModeData(int entityId, EnumMode modeIn) {
        super(entityId);
        this.mode = modeIn;
    }
}
