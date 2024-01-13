package com.sweetrpg.catherder.common.entity.ai;

import com.sweetrpg.catherder.api.feature.Mode;
import com.sweetrpg.catherder.common.entity.CatEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Optional;
import java.util.Random;

public class CatWanderGoal extends Goal {

    protected final CatEntity cat;

    protected final double speed;
    protected int executionChance;

    private final double NUM_BLOCKS_AWAY = 15;
    private final double BLOCK_SIZE = 12;
    private final double MAX_DISTANCE = NUM_BLOCKS_AWAY * BLOCK_SIZE;

    public CatWanderGoal(CatEntity catIn, double speedIn) {
        this.cat = catIn;
        this.speed = speedIn;
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

        if(this.cat.isMode(Mode.DOMESTIC)) {
            Optional<Tuple<BlockPos, Double>> closestDist = closestDomesticItem();
            if(closestDist.isEmpty()) {
                return false;
            }

            return (closestDist.get().getB() < MAX_DISTANCE);
        }

        if(!this.cat.isMode(Mode.WANDERING)) {
            if(this.cat.getOwner() == null) {
                return false;
            }

            BlockPos ownerPos = this.cat.getOwner().blockPosition();
            BlockPos catPos = this.cat.blockPosition();

            // if the owner is more than 40-ish blocks away
            if(ownerPos.distSqr(catPos) > 400D) {
                return false;
            }
        }

        return true;
    }

    private Optional<Tuple<BlockPos, Double>> closestDomesticItem() {
        Optional<BlockPos> bowlPos = this.cat.getBowlPos();
        Optional<BlockPos> litterboxPos = this.cat.getLitterboxPos();
        Optional<BlockPos> treePos = this.cat.getCatTreePos();

        if(bowlPos.isEmpty() ||
                litterboxPos.isEmpty() ||
                treePos.isEmpty()) {
            return Optional.empty();
        }

        double closestDist = Double.MAX_VALUE;
        BlockPos closestPos = null;

        double bowlDist = bowlPos.get().distSqr(this.cat.blockPosition());
        if(bowlDist < closestDist) {
            closestDist = bowlDist;
            closestPos = bowlPos.get();
        }
        double litterboxDist = litterboxPos.get().distSqr(this.cat.blockPosition());
        if(litterboxDist < closestDist) {
            closestDist = litterboxDist;
            closestPos = litterboxPos.get();
        }
        double treeDist = treePos.get().distSqr(this.cat.blockPosition());
        if(treeDist < closestDist) {
            closestDist = treeDist;
            closestPos = treePos.get();
        }

        return Optional.of(new Tuple<>(closestPos, closestDist));
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
        if(pos != null) {
            this.cat.getNavigation().moveTo(pos.x, pos.y, pos.z, this.speed);
        }
    }

    @Nullable
    protected Vec3 getPosition() {
        PathNavigation pathNavigate = this.cat.getNavigation();
        RandomSource random = this.cat.getRandom();

        int xzRange = 5;
        int yRange = 3;

        float bestWeight = Float.MIN_VALUE;

        if(this.cat.isMode(Mode.DOMESTIC)) {
            Optional<Tuple<BlockPos, Double>> closestDist = closestDomesticItem();
            if(closestDist.isEmpty()) {
                return null;
            }

            BlockPos bestPos = closestDist.get().getA().offset(random.nextInt(xzRange), 0, random.nextInt(xzRange));
            for(int attempt = 0; attempt < 5; ++attempt) {
                int dx = random.nextInt(2 * xzRange + 1) - xzRange;
                int dy = random.nextInt(2 * yRange + 1) - yRange;
                int dz = random.nextInt(2 * xzRange + 1) - xzRange;

                BlockPos testPos = bestPos.offset(dx, dy, dz);

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
