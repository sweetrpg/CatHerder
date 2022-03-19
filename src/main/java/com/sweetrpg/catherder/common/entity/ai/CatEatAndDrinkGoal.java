package com.sweetrpg.catherder.common.entity.ai;

import com.sweetrpg.catherder.common.entity.CatEntity;
import com.sweetrpg.catherder.common.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;

import java.util.EnumSet;

public class CatEatAndDrinkGoal<T extends LivingEntity> extends MoveToBlockGoal {
    private final CatEntity cat;

    public CatEatAndDrinkGoal(CatEntity cat, int searchRange) {
        super(cat, 0, searchRange, 6);
        this.cat = cat;
        this.verticalSearchStart = -2;
        this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean canUse() {
        return this.cat.isTame() && !this.cat.isOrderedToSit() && !this.cat.isLying() && super.canUse();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void start() {
        super.start();
        this.cat.setInSittingPose(false);
    }

    protected int nextStartTick(PathfinderMob pCreature) {
        return 40;
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void stop() {
        super.stop();
// TODO        this.cat.setLying(false);
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        super.tick();

        if(!this.isReachedTarget()) {
        }
        else {
        }
    }

    /**
     * Return true to set given position as destination
     */
    protected boolean isValidTarget(LevelReader level, BlockPos pos) {
        return (level.isEmptyBlock(pos.relative(Direction.Axis.X, 1)) ||
                        level.isEmptyBlock(pos.relative(Direction.Axis.Z, 1))) &&
                       level.getBlockState(pos).is(ModBlocks.CAT_BOWL.get());
    }
}


