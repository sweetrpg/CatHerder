package kittytalents.common.entity.ai;

import kittytalents.api.feature.EnumMode;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Optional;
import java.util.Random;

public class DogWanderGoal extends Goal {

    protected final kittytalents.common.entity.CatEntity cat;

    protected final double speed;
    protected int executionChance;

    public DogWanderGoal(kittytalents.common.entity.CatEntity dogIn, double speedIn) {
        this.cat = dogIn;
        this.speed = speedIn;
        this.executionChance = 60;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (!this.cat.isTame() || this.cat.isVehicle()) {
            return false;
        }

        if (!this.cat.isMode(EnumMode.WANDERING)) {
            return false;
        }

        Optional<BlockPos> bowlPos = this.cat.getBowlPos();

        if (!bowlPos.isPresent()) {
            return false;
        }

        return bowlPos.get().distSqr(this.cat.blockPosition()) < 400.0D;
    }

    @Override
    public void tick() {
        if (this.cat.getNoActionTime() >= 100) {
            return;
        } else if (this.cat.getRandom().nextInt(this.executionChance) != 0) {
            return;
        } if (this.cat.isPathFinding()) {
            return;
        }

        Vec3 pos = this.getPosition();
        this.cat.getNavigation().moveTo(pos.x, pos.y, pos.z, this.speed);
    }

    @Nullable
    protected Vec3 getPosition() {
        PathNavigation pathNavigate = this.cat.getNavigation();
        Random random = this.cat.getRandom();

        int xzRange = 5;
        int yRange = 3;

        float bestWeight = Float.MIN_VALUE;
        Optional<BlockPos> bowlPos = this.cat.getBowlPos();
        BlockPos bestPos = bowlPos.get();

        for (int attempt = 0; attempt < 5; ++attempt) {
            int l = random.nextInt(2 * xzRange + 1) - xzRange;
            int i1 = random.nextInt(2 * yRange + 1) - yRange;
            int j1 = random.nextInt(2 * xzRange + 1) - xzRange;

            BlockPos testPos = bowlPos.get().offset(l, i1, j1);

            if (pathNavigate.isStableDestination(testPos)) {
                float weight = this.cat.getWalkTargetValue(testPos);

                if (weight > bestWeight) {
                    bestWeight = weight;
                    bestPos = testPos;
                }
            }
        }

        return new Vec3(bestPos.getX(), bestPos.getY(), bestPos.getZ());
    }
}
