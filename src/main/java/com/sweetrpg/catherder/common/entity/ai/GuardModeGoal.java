package com.sweetrpg.catherder.common.entity.ai;

import com.sweetrpg.catherder.api.feature.EnumMode;
import com.sweetrpg.catherder.common.entity.CatEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;

public class GuardModeGoal extends NearestAttackableTargetGoal<Monster> {

    private final CatEntity cat;
    private LivingEntity owner;

    public GuardModeGoal(CatEntity catIn, boolean checkSight) {
        super(catIn, Monster.class, 0, checkSight, false, null);
        this.cat = catIn;
    }

    @Override
    public boolean canUse() {
        LivingEntity owner = this.cat.getOwner();
        if (owner == null) {
            return false;
        }

        if (!this.cat.isMode(EnumMode.GUARD)) {
            return false;
        }

        this.owner = owner;

        if (super.canUse()) {
            this.owner = owner;
            return true;
        }

        return false;
    }

    @Override
    protected double getFollowDistance() {
        return 6D;
    }

    @Override
    protected void findTarget() {
       this.target = this.cat.level().getNearestEntity(this.targetType, this.targetConditions, this.cat, this.owner.getX(), this.owner.getEyeY(), this.owner.getZ(), this.getTargetSearchArea(this.getFollowDistance()));
    }
}
