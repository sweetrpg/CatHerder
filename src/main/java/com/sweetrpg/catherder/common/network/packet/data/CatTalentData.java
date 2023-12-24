package com.sweetrpg.catherder.common.network.packet.data;

import com.sweetrpg.catherder.api.registry.Talent;

public class CatTalentData extends CatData {

    public final Talent talent;
    public final int adjustment;

    public CatTalentData(int entityId, Talent talent, int adjustment) {
        super(entityId);
        this.talent = talent;
        this.adjustment = adjustment;
    }
}
