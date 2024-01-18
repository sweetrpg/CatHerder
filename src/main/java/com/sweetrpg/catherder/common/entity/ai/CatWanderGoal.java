package com.sweetrpg.catherder.common.entity.ai;

import com.sweetrpg.catherder.CatHerder;
import com.sweetrpg.catherder.api.feature.Mode;
import com.sweetrpg.catherder.common.entity.CatEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Random;

public class CatWanderGoal extends Goal {

    protected final CatEntity cat;

    protected final double speed;
    protected final float maximumDistance;
    protected int executionChance;

    private final double NUM_BLOCKS_AWAY = 15;
    private final double BLOCK_SIZE = 12;
    private final double MAX_DISTANCE = NUM_BLOCKS_AWAY * BLOCK_SIZE;

    public CatWanderGoal(CatEntity catIn, double speedIn, float maxiumDistance) {
        this.cat = catIn;
        this.speed = speedIn;
        this.maximumDistance = maxiumDistance;
        this.executionChance = 60;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
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
        return (ownerPos.distSqr(catPos) < (this.maximumDistance * this.maximumDistance));
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
            if(this.cat.getNavigation().moveTo(pos.x, pos.y, pos.z, this.speed)) {
                CatHerder.LOGGER.debug("Cat {} is moving to {}", this.cat, pos);
            }
            else {
                CatHerder.LOGGER.warn("Cat {} will not move to {}", this.cat, pos);
            }
//        }
    }

//    @Nullable
    protected Vec3 getPosition() {
        PathNavigation pathNavigate = this.cat.getNavigation();
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

            if(pathNavigate.isStableDestination(testPos)) {
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
