package com.sweetrpg.catherder.common.entity.ai;

import com.sweetrpg.catherder.common.util.EntityUtil;
import com.sweetrpg.catherder.common.entity.CatEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class MoveToClosestItemGoal extends Goal {

    protected final CatEntity cat;
    protected final Predicate<ItemEntity> predicate;
    protected final Comparator<Entity> sorter;
    protected final double followSpeed;
    protected final PathNavigation catNavigator;
    protected final float minDist;

    protected ItemEntity target;
    private int timeToRecalcPath;
    private float maxDist;
    private float oldWaterCost;
    private double oldRangeSense;

    public MoveToClosestItemGoal(CatEntity catIn, double speedIn, float maxDist, float stopDist, @Nullable Predicate<ItemStack> targetSelector) {
        this.cat = catIn;
        this.catNavigator = catIn.getNavigation();
        this.followSpeed = speedIn;
        this.maxDist = maxDist;
        this.minDist = stopDist;
        this.predicate = (entity) -> {
            if (entity.isInvisible()) {
                return false;
            } else if (targetSelector != null && !targetSelector.test(entity.getItem())) {
                return false;
            } else {
                return entity.distanceTo(this.cat) <= EntityUtil.getFollowRange(this.cat);
            }
        };
        this.sorter = new EntityUtil.Sorter(catIn);
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        double d0 = EntityUtil.getFollowRange(this.cat);
        List<ItemEntity> list = this.cat.level.getEntitiesOfClass(ItemEntity.class, this.cat.getBoundingBox().inflate(d0, 4.0D, d0), this.predicate);
        if (list.isEmpty()) {
            return false;
        } else {
            Collections.sort(list, this.sorter);
            this.target = list.get(0);
            return true;
        }
    }

    @Override
    public boolean canContinueToUse() {
        ItemEntity target = this.target;
        if (target == null || !target.isAlive()) {
            return false;
        } else {
            double d0 = EntityUtil.getFollowRange(this.cat);
            double dist = this.cat.distanceToSqr(target);
            if (dist > d0 * d0 || dist < this.minDist * this.minDist) {
                return false;
            } else {
                return true;
            }
        }
    }

    @Override
    public void start() {
        this.timeToRecalcPath = 0;
        this.oldWaterCost = this.cat.getPathfindingMalus(BlockPathTypes.WATER);
        this.cat.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.oldRangeSense = this.cat.getAttribute(Attributes.FOLLOW_RANGE).getValue();
        this.cat.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(this.maxDist);
    }

    @Override
    public void tick() {
        if (!this.cat.isInSittingPose()) {
            if (--this.timeToRecalcPath <= 0) {
                this.timeToRecalcPath = 10;

                if (!this.catNavigator.moveTo(this.target, this.followSpeed)) {
                    this.cat.getLookControl().setLookAt(this.target, 10.0F, this.cat.getMaxHeadXRot());
                }
            }
        }
    }

    @Override
    public void stop() {
        this.target = null;
        this.catNavigator.stop();
        this.cat.setPathfindingMalus(BlockPathTypes.WATER, this.oldWaterCost);
        this.cat.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(this.oldRangeSense);
    }
}
