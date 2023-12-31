package com.sweetrpg.catherder.common.entity.ai;

import com.sweetrpg.catherder.api.feature.Mode;
import com.sweetrpg.catherder.common.entity.CatEntity;

public class OwnerHurtByTargetGoal extends net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal {

    private CatEntity cat;

    public OwnerHurtByTargetGoal(CatEntity catIn) {
        super(catIn);
        this.cat = catIn;
    }

    @Override
    public boolean canUse() {
         return this.cat.isMode(Mode.ATTACK) && super.canUse();
    }
}
