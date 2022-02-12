package kittytalents.common.entity.ai;

import kittytalents.common.block.tileentity.DogBedTileEntity;
import kittytalents.common.lib.Constants;
import kittytalents.common.util.WorldUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class MoveToBlockGoal extends Goal {

    protected final kittytalents.common.entity.CatEntity cat;

    public MoveToBlockGoal(kittytalents.common.entity.CatEntity dogIn) {
        this.cat = dogIn;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return this.cat.getTargetBlock() != null && !this.cat.isOrderedToSit();
    }

    @Override
    public boolean canContinueToUse() {
        return this.cat.isPathFinding() && !this.cat.getTargetBlock().closerThan(this.cat.position(), 0.5);
    }

    @Override
    public void stop() {
        BlockPos target = this.cat.getTargetBlock();

        DogBedTileEntity dogBedTileEntity = WorldUtil.getTileEntity(cat.level, target, DogBedTileEntity.class);

        if (dogBedTileEntity != null) {
            // Double check the bed still has no owner
            if (dogBedTileEntity.getOwnerUUID() == null) {
                dogBedTileEntity.setOwner(this.cat);
                this.cat.setBedPos(this.cat.level.dimension(), target);
            }
        }

        this.cat.setTargetBlock(null);
        this.cat.setOrderedToSit(true);

        this.cat.level.broadcastEntityEvent(this.cat, Constants.EntityState.WOLF_HEARTS);
    }

    @Override
    public void start() {
        BlockPos target = this.cat.getTargetBlock();
        this.cat.getNavigation().moveTo((target.getX()) + 0.5D, target.getY() + 1, (target.getZ()) + 0.5D, 1.0D);
    }

}
