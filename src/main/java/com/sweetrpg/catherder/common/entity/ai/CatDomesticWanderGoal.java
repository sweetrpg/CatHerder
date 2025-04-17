package com.sweetrpg.catherder.common.entity.ai;

import com.sweetrpg.catherder.CatHerder;
import com.sweetrpg.catherder.api.feature.Mode;
import com.sweetrpg.catherder.common.config.ConfigHandler;
import com.sweetrpg.catherder.common.entity.CatEntity;
import com.sweetrpg.catherder.common.util.MathUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.phys.Vec3;
import org.antlr.v4.runtime.misc.Triple;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Optional;
import java.util.Random;

public class CatDomesticWanderGoal extends Goal {

    protected final CatEntity cat;

    protected final double speed;
    protected int executionChance;

    private final double NUM_BLOCKS_AWAY = 15;
    private final double BLOCK_SIZE = 12;
    private final double MAX_DISTANCE = NUM_BLOCKS_AWAY * BLOCK_SIZE;

    public CatDomesticWanderGoal(CatEntity catIn, double speedIn) {
        this.cat = catIn;
        this.speed = speedIn;
        this.executionChance = 60;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if(!this.cat.isTame() || this.cat.isVehicle()) {
            return false;
        }

        if(this.cat.isInSittingPose() || this.cat.isLying() || this.cat.isLyingDown()) {
            return false;
        }

        if(!this.cat.isMode(Mode.DOMESTIC)) {
            return false;
        }

        // The cat should wander an area centered on the center-point of the three items: litter box, food bowl, cat tree
        // If any one of the items is missing, do not wander.
        // If the distance between any pair of the items is further than a given distance, do not wander.
        // Calculate center of the three items. That point is the center of the wandering area.
        // The maximum wander distance becomes the radius of the wander circle, which is the distance of the furthest item from the center.
        // The cat will not go further than that distance.
        final var itemPositions = getItemPositions();
        if(itemPositions.a.isEmpty() || itemPositions.b.isEmpty() || itemPositions.c.isEmpty()) {
            CatHerder.LOGGER.debug("A cat item is missing for domestic mode wander.");
            return false;
        }
        final var blockDistance = ConfigHandler.CLIENT.MAX_ITEM_DISTANCE.get() * 12;
        if(itemPositions.a.get().distSqr(itemPositions.b.get()) > blockDistance) {
            CatHerder.LOGGER.debug("The distance between {}'s litter box and food bowl is greater than {}", this.cat, blockDistance);
            return false;
        }
        if(itemPositions.a.get().distSqr(itemPositions.c.get()) > blockDistance) {
            CatHerder.LOGGER.debug("The distance between {}'s litter box and cat tree is greater than {}", this.cat, blockDistance);
            return false;
        }
        if(itemPositions.b.get().distSqr(itemPositions.c.get()) > blockDistance) {
            CatHerder.LOGGER.debug("The distance between {}'s food bowl and cat tree is greater than {}", this.cat, blockDistance);
            return false;
        }
        BlockPos itemCenter = MathUtil.calculateCenter(itemPositions.a.get(), itemPositions.b.get(), itemPositions.c.get());
        double maxWanderDistance = Math.pow(MathUtil.furthestDistance(itemCenter, itemPositions.a.get(), itemPositions.b.get(), itemPositions.c.get()) * 1.5, 2);

        if(this.cat.blockPosition().distSqr(itemCenter) > maxWanderDistance) {
            CatHerder.LOGGER.debug("{} is more than {} from the center point.", this.cat, maxWanderDistance);
            return false;
        }

        return true;
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
        Random random = this.cat.getRandom();

        int xzRange = 5;
        int yRange = 3;

        float bestWeight = Float.MIN_VALUE;

        final var itemPositions = getItemPositions();
        if(itemPositions.a.isEmpty() || itemPositions.b.isEmpty() || itemPositions.c.isEmpty()) {
            CatHerder.LOGGER.debug("A cat item is missing for domestic mode wander.");
            return this.cat.position();
        }

        BlockPos bestPos = this.cat.blockPosition().offset(random.nextDouble(xzRange), 0, random.nextDouble(xzRange));
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

    /**
     * 0: litter box
     * 1: food bowl
     * 2: cat tree
     *
     * @return tuple of block positions
     */
    private Triple<Optional<BlockPos>, Optional<BlockPos>, Optional<BlockPos>> getItemPositions() {
        Optional<BlockPos> bowlPos = this.cat.getBowlPos();
        Optional<BlockPos> litterboxPos = this.cat.getLitterboxPos();
        Optional<BlockPos> treePos = this.cat.getCatTreePos();

        return new Triple<>(litterboxPos, bowlPos, treePos);
    }
}
