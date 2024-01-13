package com.sweetrpg.catherder.common.entity.ai;

//import com.sweetrpg.catherder.common.block.tileentity.CattreeTileEntity;

import com.sweetrpg.catherder.common.block.entity.CatTreeBlockEntity;
import com.sweetrpg.catherder.common.entity.CatEntity;
import com.sweetrpg.catherder.common.lib.Constants;
import com.sweetrpg.catherder.common.util.WorldUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class MoveToBlockGoal extends Goal {

    protected final CatEntity cat;

    public MoveToBlockGoal(CatEntity catIn) {
        this.cat = catIn;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return this.cat.getTargetBlock() != null && !this.cat.isOrderedToSit();
    }

    @Override
    public boolean canContinueToUse() {
        return this.cat.isPathFinding() && !this.cat.getTargetBlock().closerToCenterThan(this.cat.position(), 0.5);
    }

    @Override
    public void stop() {
        BlockPos target = this.cat.getTargetBlock();

        CatTreeBlockEntity catTreeEntity = WorldUtil.getBlockEntity(cat.level, target, CatTreeBlockEntity.class);

        if(catTreeEntity != null) {
            // Double-check the bed still has no owner
            if(catTreeEntity.getOwnerUUID() == null) {
                catTreeEntity.setOwner(this.cat);
                this.cat.setCatTreePos(this.cat.level.dimension(), target);
            }
        }

        this.cat.setTargetBlock(null);
        this.cat.setOrderedToSit(true);

        this.cat.level.broadcastEntityEvent(this.cat, Constants.EntityState.CAT_HEARTS);
    }

    @Override
    public void start() {
        BlockPos target = this.cat.getTargetBlock();
        this.cat.getNavigation().moveTo((target.getX()) + 0.5D, target.getY() + 1, (target.getZ()) + 0.5D, 1.0D);
    }

}
