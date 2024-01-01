package com.sweetrpg.catherder.common.network.packet.data;

import com.sweetrpg.catherder.api.feature.Mode;

public class CatModeData extends CatData {

    public Mode mode;

    public CatModeData(int entityId, Mode modeIn) {
        super(entityId);
        this.mode = modeIn;
    }
}
