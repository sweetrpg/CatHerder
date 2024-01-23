package com.sweetrpg.catherder.common.entity.ai;

import com.sweetrpg.catherder.api.feature.Mode;
import com.sweetrpg.catherder.common.entity.CatEntity;
import com.sweetrpg.catherder.common.util.EntityUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

public class GuardModeGoal extends Goal {

    private final CatEntity cat;
    private LivingEntity owner;
    private int timeToRecalcPath;
    private float oldWaterCost;

    public GuardModeGoal(CatEntity catIn, boolean checkSight) {
//        super(catIn, Monster.class, 0, checkSight, true, null);
        this.cat = catIn;
    }

    @Override
    public boolean canUse() {
        LivingEntity owner = this.cat.getOwner();
        if(owner == null) {
            return false;
        }

        if(!this.cat.isMode(Mode.GUARD)) {
            return false;
        }

        this.owner = owner;
        return true;

//        return super.canUse();
    }

    @Override
    public void start() {
        this.timeToRecalcPath = 0;
        this.oldWaterCost = this.cat.getPathfindingMalus(BlockPathTypes.WATER);
        this.cat.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
    }

    @Override
    public void tick() {
        this.cat.getLookControl().setLookAt(this.owner, 10.0F, this.cat.getMaxHeadXRot());
        if(--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = 10;
            if(!this.cat.isLeashed() && !this.cat.isPassenger()) { // Is not leashed and is not a passenger
                var distance = this.cat.distanceToSqr(this.owner);
                if(distance >= 400.0D) { // Further than ? blocks away teleport (12 units == one block?)
                    EntityUtil.tryToTeleportNearEntity(this.cat, this.cat.getNavigation(), this.owner, 4);
                }
                else {
                    this.cat.getNavigation().moveTo(this.owner, 1);
                }
            }
        }
    }

//    @Override
//    protected double getFollowDistance() {
//        return 6D;
//    }

//    @Override
//    protected void findTarget() {
//        this.target = this.cat.level.getNearestEntity(this.targetType, this.targetConditions, this.cat, this.owner.getX(), this.owner.getEyeY(), this.owner.getZ(), this.getTargetSearchArea(this.getFollowDistance()));
//    }
}
