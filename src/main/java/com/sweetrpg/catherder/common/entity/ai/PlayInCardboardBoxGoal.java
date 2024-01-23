package com.sweetrpg.catherder.common.entity.ai;

import com.sweetrpg.catherder.common.entity.CatEntity;
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
import java.util.Random;

public class PlayInCardboardBoxGoal<T extends LivingEntity> extends MoveToBlockGoal {
    private final CatEntity cat;
    private int usingCounter = 0;

    public static final int MAX_CARDBOARDBOX_USE_COUNT = 40;
    public static final int CARDBOARDBOX_USE_DELAY = 10000;

    public PlayInCardboardBoxGoal(CatEntity cat, float speedModifier, int searchRange) {
        super(cat, speedModifier, searchRange, 6);
        this.cat = cat;
        this.verticalSearchStart = -2;
        this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean canUse() {
        return this.cat.isTame() && !this.cat.isOrderedToSit() && !this.cat.isLying() && !this.cat.isHungry() && super.canUse();
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
        this.cat.setInSittingPose(false);
        this.cat.setLyingDown(false);
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        super.tick();

//        if(this.cat.getLitterboxCooldown() > 0) {
//            return;
//        }
//
        if(this.isReachedTarget()) {
            if(this.cat.isInSittingPose()) {
                this.cat.level.playSound(null, this.cat, SoundEvents.DEEPSLATE_BREAK, SoundSource.AMBIENT, 1, 1);

            }
            else if(this.cat.isLyingDown()) {
//                if(this.usingLitterboxCounter % 10 == 0) {
//                    this.cat.level.broadcastEntityEvent(this.cat, Constants.EntityState.CAT_SMOKE);
//                }
//                if(this.usingLitterboxCounter % 5 == 0) {
//                    this.cat.level.playSound(null, this.cat, SoundEvents.AXE_STRIP, SoundSource.AMBIENT, 1, 1);
//                }
//                this.usingLitterboxCounter++;
                this.cat.level.playSound(null, this.cat, SoundEvents.CAT_PURREOW, SoundSource.AMBIENT, 2, 1);
            }
            else {
                int what = new Random().nextInt(100);
                // 50% to lie down
                if(what > 50) {
                    this.cat.setLyingDown(true);
//                this.cat.setInSittingPose(true);
//                this.cat.setSprinting(true);
                }
                // 30% chance to sit down
                else if(what > 20) {
                    this.cat.setInSittingPose(true);
                }
                // 20% chance to just make some noise
                else {
                    this.cat.level.playSound(null, this.cat, SoundEvents.DEEPSLATE_BREAK, SoundSource.AMBIENT, 1, 1);
                    this.usingCounter = MAX_CARDBOARDBOX_USE_COUNT;
                }
            }

            this.usingCounter++;

            if(this.usingCounter > MAX_CARDBOARDBOX_USE_COUNT) {
                this.cat.setInSittingPose(false);
                this.cat.setLyingDown(false);
                this.stop();
            }
        }
        else {
//            this.cat.setInSittingPose(false);
        }
    }

//    @Override
//    public double acceptedDistance() {
//        return 1.35D;
//    }

    /**
     * Return true to set given position as destination
     */
    protected boolean isValidTarget(LevelReader level, BlockPos pos) {
        return level.isEmptyBlock(pos.above()) && level.getBlockState(pos).is(ModBlocks.CARDBOARD_BOX.get());
    }
}
