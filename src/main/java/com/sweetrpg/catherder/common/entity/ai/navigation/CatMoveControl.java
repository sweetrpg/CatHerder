package com.sweetrpg.catherder.common.entity.ai.navigation;

import com.sweetrpg.catherder.common.entity.CatEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CatMoveControl extends MoveControl {

    private CatEntity cat;

    public CatMoveControl(CatEntity cat) {
        super(cat);
        this.cat = cat;
    }

    @Override
    public void tick() {
        double dy = 0;
        if(this.hasWanted()) {
            dy = Math.abs(this.getWantedY() - this.cat.getY());
        }

        if(this.operation == MoveControl.Operation.MOVE_TO && dy > 0.75) {
            final float SNEAK_SPEED_1 = 0.35f;
            final float SNEAK_SPEED_2 = 0.25f;

            this.operation = MoveControl.Operation.WAIT;
            double d0 = this.wantedX - this.mob.getX();
            double d1 = this.wantedZ - this.mob.getZ();
            double d2 = this.wantedY - this.mob.getY();
            double d3 = d0 * d0 + d2 * d2 + d1 * d1;
            if(d3 < (double) 2.5000003E-7F) {
                this.mob.setZza(0.0F);
                return;
            }

            float f9 = (float) (Mth.atan2(d1, d0) * (double) (180F / (float) Math.PI)) - 90.0F;
            this.mob.setYRot(this.rotlerp(this.mob.getYRot(), f9, 90.0F));
            float speed_cap = dy > 1.75 ? SNEAK_SPEED_2 : SNEAK_SPEED_1;
            float speed = Math.min(speed_cap,
                    (float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
            this.mob.setSpeed(speed);
            BlockPos blockpos = this.mob.blockPosition();
            BlockState blockstate = this.mob.level.getBlockState(blockpos);
            VoxelShape voxelshape = blockstate.getCollisionShape(this.mob.level, blockpos);
            if(d2 > (double) this.mob.getStepHeight() && d0 * d0 + d1 * d1 < (double) Math.max(1.0F, this.mob.getBbWidth()) || !voxelshape.isEmpty() && this.mob.getY() < voxelshape.max(Direction.Axis.Y) + (double) blockpos.getY() && !blockstate.is(BlockTags.DOORS) && !blockstate.is(BlockTags.FENCES)) {
                this.mob.getJumpControl().jump();
                this.operation = MoveControl.Operation.JUMPING;
            }

            return;
        }

        super.tick();
    }

}
