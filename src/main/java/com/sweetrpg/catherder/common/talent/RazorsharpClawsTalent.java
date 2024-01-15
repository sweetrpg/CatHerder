package com.sweetrpg.catherder.common.talent;

import com.sweetrpg.catherder.api.feature.DataKey;
import com.sweetrpg.catherder.api.registry.Talent;
import com.sweetrpg.catherder.api.registry.TalentInstance;

public class RazorsharpClawsTalent extends TalentInstance {

    public static DataKey<Integer> COOLDOWN = DataKey.make();

    public RazorsharpClawsTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

}
