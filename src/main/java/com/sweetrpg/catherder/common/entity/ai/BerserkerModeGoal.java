package com.sweetrpg.catherder.common.entity.ai;

import com.sweetrpg.catherder.api.feature.EnumMode;
import com.sweetrpg.catherder.common.entity.CatEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;

public class BerserkerModeGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {

    private final CatEntity cat;

    public BerserkerModeGoal(CatEntity catIn, Class<T> targetClassIn, boolean checkSight) {
        super(catIn, targetClassIn, checkSight, false);
        this.cat = catIn;
    }

    @Override
    public boolean canUse() {
        return this.cat.isMode(EnumMode.BERSERKER) && super.canUse();
    }
}
