package com.sweetrpg.catherder.common.entity.ai;

import com.sweetrpg.catherder.api.feature.Mode;
import com.sweetrpg.catherder.api.inferface.IThrowableItem;
import com.sweetrpg.catherder.common.entity.CatEntity;
import com.sweetrpg.catherder.common.util.EntityUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

import java.util.EnumSet;

public class CatFollowOwnerGoal extends Goal {

    private final CatEntity cat;
    private final PathNavigation navigator;
    private final Level world;
    private final double followSpeed;
    private final float stopDist; // If closer than stopDist stop moving towards owner
    private final float startDist; // If further than startDist moving towards owner

    private LivingEntity owner;
    private int timeToRecalcPath;
    private float oldWaterCost;

    public CatFollowOwnerGoal(CatEntity catIn, double speedIn, float startMoveDist, float stopMoveDist) {
        this.cat = catIn;
        this.world = catIn.level();
        this.followSpeed = speedIn;
        this.navigator = catIn.getNavigation();
        this.startDist = startMoveDist;
        this.stopDist = stopMoveDist;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity owner = this.cat.getOwner();
        if(owner == null) {
            return false;
        }
//        else if (this.cat.getMode() == Mode.PATROL) {
//            return false;
//        }
        else if(owner.isSpectator()) {
            return false;
        }
        else if(this.cat.isInSittingPose()) {
            return false;
        }
        else if(!this.cat.hasToy() && this.cat.distanceToSqr(owner) < this.getMinStartDistanceSq()) {
            return false;
        }
        else {
            this.owner = owner;
            return true;
        }
    }

    @Override
    public boolean canContinueToUse() {
        if(this.navigator.isDone()) {
            return false;
        }
        else if(this.cat.isInSittingPose()) {
            return false;
        }
        else {
            return this.cat.distanceToSqr(this.owner) > this.stopDist * this.stopDist;
        }
    }

    @Override
    public void start() {
        this.timeToRecalcPath = 0;
        this.oldWaterCost = this.cat.getPathfindingMalus(BlockPathTypes.WATER);
        this.cat.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
    }

    @Override
    public void stop() {
        if(this.cat.hasToy()) {
            double distanceToOwner = this.owner.distanceToSqr(this.cat);
            if(distanceToOwner <= this.stopDist * this.stopDist) {
                IThrowableItem throwableItem = this.cat.getThrowableItem();
                ItemStack fetchItem = throwableItem != null ? throwableItem.getReturnStack(this.cat.getToyVariant()) : this.cat.getToyVariant();

                this.cat.spawnAtLocation(fetchItem, 0.0F);
                this.cat.setToyVariant(ItemStack.EMPTY);
            }
        }

        this.owner = null;
        this.navigator.stop();
        this.cat.setPathfindingMalus(BlockPathTypes.WATER, this.oldWaterCost);
    }

    @Override
    public void tick() {
        this.cat.getLookControl().setLookAt(this.owner, 10.0F, this.cat.getMaxHeadXRot());
        if(--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = 10;
            if(!this.cat.isLeashed() && !this.cat.isPassenger()) { // Is not leashed and is not a passenger
                if(this.cat.distanceToSqr(this.owner) >= 144.0D) { // Further than 12 blocks away teleport (12 units == one block?)
                    EntityUtil.tryToTeleportNearEntity(this.cat, this.navigator, this.owner, 4);
                }
                else {
                    this.navigator.moveTo(this.owner, this.followSpeed);
                }
            }
        }
    }

    public float getMinStartDistanceSq() {
        if(this.cat.isMode(Mode.GUARD)) {
            return 4F;
        }

        return this.startDist * this.startDist;
    }
}
