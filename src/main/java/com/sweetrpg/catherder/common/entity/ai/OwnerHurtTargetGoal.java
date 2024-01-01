package com.sweetrpg.catherder.common.entity.ai;

import com.sweetrpg.catherder.api.feature.Mode;
import com.sweetrpg.catherder.common.entity.CatEntity;

public class OwnerHurtTargetGoal extends net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal {

    private CatEntity cat;

    public OwnerHurtTargetGoal(CatEntity catIn) {
        super(catIn);
        this.cat = catIn;
    }

    @Override
    public boolean canUse() {
         return this.cat.isMode(Mode.ATTACK, Mode.TACTICAL) && super.canUse();
    }
}
