package com.sweetrpg.catherder.common.entity.ai;

import com.sweetrpg.catherder.common.entity.CatEntity;
import com.sweetrpg.catherder.common.lib.Constants;
import com.sweetrpg.catherder.common.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;

import java.util.EnumSet;

public class UseLitterboxGoal<T extends LivingEntity> extends MoveToBlockGoal {
    private final CatEntity cat;
    private int usingLitterboxCounter = 0;

    public static final int MAX_LITTERBOX_USE_COUNT = 40;
    public static final int LITTERBOX_USE_DELAY = 10000;

    public UseLitterboxGoal(CatEntity cat, int searchRange) {
        super(cat, 1, searchRange, 6);
        this.cat = cat;
        this.verticalSearchStart = -2;
        this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean canUse() {
        if(this.cat.getLitterboxCooldown() > 0) {
            return false;
        }

        return this.cat.isTame() && !this.cat.isOrderedToSit() && !this.cat.isLying() && super.canUse();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void start() {
        super.start();
        this.cat.setLitterboxCooldown(0);
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
        this.cat.setInSittingPose(false);
        this.cat.setSprinting(false);
        this.usingLitterboxCounter = 0;
        this.cat.setLitterboxCooldown(LITTERBOX_USE_DELAY);
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        super.tick();

        if(this.cat.getLitterboxCooldown() > 0) {
            return;
        }

        if(this.isReachedTarget()) {
            if(this.cat.isInSittingPose()) {
                if(this.usingLitterboxCounter % 10 == 0) {
                    this.cat.level.broadcastEntityEvent(this.cat, Constants.EntityState.CAT_SMOKE);
                }
                if(this.usingLitterboxCounter % 5 == 0) {
                    this.cat.level.playSound(null, this.cat, SoundEvents.AXE_STRIP, SoundSource.AMBIENT, 1, 1);
                }
                this.usingLitterboxCounter++;
            }
            else {
                // TODO this.cat.setLying(true);
                this.cat.setInSittingPose(true);
                this.cat.setSprinting(true);
                this.usingLitterboxCounter = 0;
            }

            if(this.usingLitterboxCounter > MAX_LITTERBOX_USE_COUNT) {
                this.cat.level.broadcastEntityEvent(this.cat, Constants.EntityState.CAT_HEARTS);
                this.cat.setInSittingPose(false);
                this.cat.setSprinting(false);
                this.cat.setLitterboxCooldown(LITTERBOX_USE_DELAY);
                this.stop();
            }
        }
        else {
//            this.cat.setInSittingPose(false);
        }
    }

    @Override
    public double acceptedDistance() {
        return 1.35D;
    }

    /**
     * Return true to set given position as destination
     */
    protected boolean isValidTarget(LevelReader level, BlockPos pos) {
        return level.isEmptyBlock(pos.above()) && level.getBlockState(pos).is(ModBlocks.LITTER_BOX.get());
    }
}

