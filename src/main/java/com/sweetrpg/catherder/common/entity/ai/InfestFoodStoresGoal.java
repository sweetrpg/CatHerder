package com.sweetrpg.catherder.common.entity.ai;

//import com.sweetrpg.catherder.common.entity.RodentEntity;
//import net.minecraft.core.BlockPos;
//import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.LevelReader;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.level.block.CarrotBlock;
//import net.minecraft.world.level.block.state.BlockState;
//
//public class InfestFoodStoresGoal extends MoveToBlockGoal {
//    private final RodentEntity rodent;
//    private boolean wantsToInfest;
//    private boolean canRaid;
//
//    public InfestFoodStoresGoal(RodentEntity rodent) {
//        super(rodent, (double)0.7F, 16);
//        this.rodent = rodent;
//    }
//
//    /**
//     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
//     * method as well.
//     */
//    public boolean canUse() {
//        if (this.nextStartTick <= 0) {
//            if (!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.rodent.level, this.rodent)) {
//                return false;
//            }
//
//            this.canRaid = false;
//            this.wantsToInfest = this.rodent.wantsMoreFood();
//            this.wantsToInfest = true;
//        }
//
//        return super.canUse();
//    }
//
//    /**
//     * Returns whether an in-progress EntityAIBase should continue executing
//     */
//    public boolean canContinueToUse() {
//        return this.canRaid && super.canContinueToUse();
//    }
//
//    /**
//     * Keep ticking a continuous task that has already been started
//     */
//    public void tick() {
//        super.tick();
//      /*  this.rabbit.getLookControl().setLookAt((double)this.blockPos.getX() + 0.5D, (double)(this.blockPos.getY() + 1), (double)this.blockPos.getZ() + 0.5D, 10.0F, (float)this.rabbit.getMaxHeadXRot());
//        if (this.isReachedTarget()) {
//            Level level = this.rabbit.level;
//            BlockPos blockpos = this.blockPos.above();
//            BlockState blockstate = level.getBlockState(blockpos);
//            Block block = blockstate.getBlock();
//            if (this.canRaid && block instanceof CarrotBlock) {
//                int i = blockstate.getValue(CarrotBlock.AGE);
//                if (i == 0) {
//                    level.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 2);
//                    level.destroyBlock(blockpos, true, this.rabbit);
//                } else {
//                    level.setBlock(blockpos, blockstate.setValue(CarrotBlock.AGE, Integer.valueOf(i - 1)), 2);
//                    level.levelEvent(2001, blockpos, Block.getId(blockstate));
//                }
//
//                this.rabbit.moreCarrotTicks = 40;
//            }
//
//            this.canRaid = false;
//            this.nextStartTick = 10;
//        }
//*/
//    }
//
//    /**
//     * Return true to set given position as destination
//     */
//    protected boolean isValidTarget(LevelReader pLevel, BlockPos pPos) {
//     /*   BlockState blockstate = pLevel.getBlockState(pPos);
//        if (blockstate.is(Blocks.FARMLAND) && this.wantsToInfest && !this.canInfest) {
//            blockstate = pLevel.getBlockState(pPos.above());
//            if (blockstate.getBlock() instanceof CarrotBlock && ((CarrotBlock)blockstate.getBlock()).isMaxAge(blockstate)) {
//                this.canInfest = true;
//                return true;
//            }
//        }
//*/
//        return false;
//    }
//}
