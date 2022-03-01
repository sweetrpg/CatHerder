package com.sweetrpg.catherder.common.entity.ai;

import com.sweetrpg.catherder.common.entity.CatEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Optional;
import java.util.Random;

public class CatnipGoal extends Goal {

    protected final CatEntity cat;

    protected final float speed;
    protected float oldSpeed;
    protected int executionChance;

    public CatnipGoal(CatEntity catIn, float speedIn) {
        this.cat = catIn;
        this.speed = speedIn;
        this.executionChance = 5;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return this.cat.isTame() && !this.cat.isVehicle();

//        if (!this.cat.isMode(EnumMode.WANDERING)) {
//            return false;
//        }

//        Optional<BlockPos> bowlPos = this.cat.getBowlPos();
//
//        if (!bowlPos.isPresent()) {
//            return false;
//        }
// bowlPos.get().distSqr(this.cat.blockPosition()) < 400.0D;
    }

    @Override
    public void start() {
        this.oldSpeed = this.cat.getSpeed();
        this.cat.setSpeed(this.speed);
        this.cat.setSprinting(true);
    }

    @Override
    public void tick() {
//        if(this.cat.getNoActionTime() >= 20) {
//            return;
//        }
        /*else*/ if(this.cat.getRandom().nextInt(this.executionChance) != 0) {
            return;
        }

        if(this.cat.isPathFinding()) {
            return;
        }

        Vec3 pos = this.getPosition();
        this.cat.getNavigation().moveTo(pos.x, pos.y, pos.z, this.speed);
    }

    @Override
    public void stop() {
        this.cat.setSpeed(this.oldSpeed);
        this.cat.setSprinting(false);
    }

    @Nullable
    protected Vec3 getPosition() {
        PathNavigation pathNavigate = this.cat.getNavigation();
        Random random = this.cat.getRandom();

        int xzRange = 5;
        int yRange = 3;

        float bestWeight = Float.MIN_VALUE;
//        Optional<BlockPos> bowlPos = this.cat.getBowlPos();
//        BlockPos bestPos = bowlPos.get();
        BlockPos bestPos = this.cat.blockPosition();

        for(int attempt = 0; attempt < 5; ++attempt) {
            int l = random.nextInt(2 * xzRange + 1) - xzRange;
            int i1 = random.nextInt(2 * yRange + 1) - yRange;
            int j1 = random.nextInt(2 * xzRange + 1) - xzRange;

            BlockPos testPos = bestPos.offset(l, i1, j1);

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
