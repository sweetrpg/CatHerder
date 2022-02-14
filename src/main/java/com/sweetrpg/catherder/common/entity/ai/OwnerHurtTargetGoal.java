package com.sweetrpg.catherder.common.entity.ai;

import com.sweetrpg.catherder.api.feature.EnumMode;
import com.sweetrpg.catherder.common.entity.CatEntity;

public class OwnerHurtTargetGoal extends net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal {

    private CatEntity cat;

    public OwnerHurtTargetGoal(CatEntity catIn) {
        super(catIn);
        this.cat = catIn;
    }

    @Override
    public boolean canUse() {
         return this.cat.isMode(EnumMode.AGGRESIVE, EnumMode.BERSERKER, EnumMode.TACTICAL) && super.canUse();
    }
}
