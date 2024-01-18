package com.sweetrpg.catherder.common.entity.ai;

import com.sweetrpg.catherder.CatHerder;
import com.sweetrpg.catherder.api.feature.Mode;
import com.sweetrpg.catherder.api.inferface.IThrowableItem;
import com.sweetrpg.catherder.common.config.ConfigHandler;
import com.sweetrpg.catherder.common.entity.CatEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Random;

public class CatWanderGoal extends Goal {

    protected final CatEntity cat;
    private final PathNavigation navigator;
    protected final double speed;
    protected int executionChance;
    private float oldWaterCost;

    private final double NUM_BLOCKS_AWAY = 15;
    private final double BLOCK_SIZE = 12;
    private final double MAX_DISTANCE = NUM_BLOCKS_AWAY * BLOCK_SIZE;

    public CatWanderGoal(CatEntity catIn, double speedIn) {
        this.cat = catIn;
        this.speed = speedIn;
        this.navigator = catIn.getNavigation();
        this.executionChance = 60;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if(!this.cat.isTame() || this.cat.isVehicle()) {
            return false;
        }

        if(this.cat.isInSittingPose() || this.cat.isLying() || this.cat.isLyingDown()) {
            return false;
        }

        if(!this.cat.isMode(Mode.WANDERING)) {
            return false;
        }

        if(this.cat.getOwner() == null) {
            return false;
        }

        BlockPos ownerPos = this.cat.getOwner().blockPosition();
        BlockPos catPos = this.cat.blockPosition();

        // if the owner is more than 40-ish blocks away
        var maximumDistance = ConfigHandler.CLIENT.MAX_WANDER_DISTANCE.get();
        return (ownerPos.distSqr(catPos) < (maximumDistance * maximumDistance));
    }

    @Override
    public boolean canContinueToUse() {
        if(this.navigator.isDone()) {
            return false;
        }
        else if(this.cat.isInSittingPose()) {
            return false;
        }

        return true;
    }

    @Override
    public void start() {
        this.oldWaterCost = this.cat.getPathfindingMalus(BlockPathTypes.WATER);
        this.cat.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
    }

    @Override
    public void stop() {
        this.navigator.stop();
        this.cat.setPathfindingMalus(BlockPathTypes.WATER, this.oldWaterCost);
    }

    @Override
    public void tick() {
        if(this.cat.getNoActionTime() >= 100) {
            return;
        }
        else if(this.cat.getRandom().nextInt(this.executionChance) != 0) {
            return;
        }

        if(this.cat.isPathFinding()) {
            return;
        }

        Vec3 pos = this.getPosition();
//        if(pos != null) {
        this.cat.setOnGround(true);
            if(this.navigator.moveTo(pos.x, pos.y, pos.z, this.speed)) {
                CatHerder.LOGGER.debug("Cat {} is moving to {}", this.cat, pos);
            }
            else {
                CatHerder.LOGGER.warn("Cat {} will not move to {}", this.cat, pos);
            }
//        }
    }

//    @Nullable
    protected Vec3 getPosition() {
        RandomSource random = this.cat.getRandom();

        int xzRange = 5;
        int yRange = 3;

        float bestWeight = Float.MIN_VALUE;

        BlockPos bestPos = this.cat.blockPosition();
        for(int attempt = 0; attempt < 5; ++attempt) {
            int x = random.nextInt(2 * xzRange + 1) - xzRange;
            int y = random.nextInt(2 * yRange + 1) - yRange;
            int z = random.nextInt(2 * xzRange + 1) - xzRange;

            BlockPos testPos = bestPos.offset(x, y, z);

            if(this.navigator.isStableDestination(testPos)) {
                float weight = this.cat.getWalkTargetValue(testPos);

                if(weight > bestWeight) {
                    bestWeight = weight;
                    bestPos = testPos;
                }
            }
        }

        return new Vec3(bestPos.getX(), bestPos.getY(), bestPos.getZ());
    }

}
