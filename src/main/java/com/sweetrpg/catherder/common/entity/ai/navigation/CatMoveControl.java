package com.sweetrpg.catherder.common.entity.ai.navigation;

import com.sweetrpg.catherder.CatHerder;
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
        CatHerder.LOGGER.debug("#tick");

        double dy = 0;
        if(this.hasWanted()) {
            CatHerder.LOGGER.debug("Cat {} wants to move", this.cat);
            dy = Math.abs(this.getWantedY() - this.cat.getY());
            CatHerder.LOGGER.debug("dy = {}", dy);
        }

        CatHerder.LOGGER.debug("Check if move operation is MOVE_TO and dy > 0.75");
        if(this.operation == MoveControl.Operation.MOVE_TO && dy > 0.75) {
            final float SNEAK_SPEED_1 = 0.35f;
            final float SNEAK_SPEED_2 = 0.25f;

            CatHerder.LOGGER.debug("Set operation to WAIT");
            this.operation = MoveControl.Operation.WAIT;
            double d0 = this.wantedX - this.mob.getX();
            double d1 = this.wantedZ - this.mob.getZ();
            double d2 = this.wantedY - this.mob.getY();
            double d3 = d0 * d0 + d2 * d2 + d1 * d1;
            CatHerder.LOGGER.debug("d0 = {}, d1 = {}, d2 = {}, d3 = {}", d0, d1, d2, d3);
            if(d3 < (double) 2.5000003E-7F) {
                CatHerder.LOGGER.debug("d3 < 2.5000003E-7F; set mob Zza to 0 and return");
                this.mob.setZza(0.0F);
                return;
            }

            float f9 = (float) (Mth.atan2(d1, d0) * (double) (180F / (float) Math.PI)) - 90.0F;
            CatHerder.LOGGER.debug("f9 = {}", f9);
            this.mob.setYRot(this.rotlerp(this.mob.getYRot(), f9, 90.0F));
            float speed_cap = dy > 1.75 ? SNEAK_SPEED_2 : SNEAK_SPEED_1;
            CatHerder.LOGGER.debug("speed_cap = {}", speed_cap);
            float speed = Math.min(speed_cap,
                    (float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
            CatHerder.LOGGER.debug("speed = {}", speed);
            this.mob.setSpeed(speed);
            BlockPos blockpos = this.mob.blockPosition();
            CatHerder.LOGGER.debug("blockpos = {}", blockpos);
            BlockState blockstate = this.mob.level.getBlockState(blockpos);
            CatHerder.LOGGER.debug("blockstate = {}", blockstate);
            VoxelShape voxelshape = blockstate.getCollisionShape(this.mob.level, blockpos);
            CatHerder.LOGGER.debug("Check if mob needs to jump");
            if(d2 > (double) this.mob.getStepHeight() && d0 * d0 + d1 * d1 < (double) Math.max(1.0F, this.mob.getBbWidth()) || !voxelshape.isEmpty() && this.mob.getY() < voxelshape.max(Direction.Axis.Y) + (double) blockpos.getY() && !blockstate.is(BlockTags.DOORS) && !blockstate.is(BlockTags.FENCES)) {
                CatHerder.LOGGER.debug("Set move control to JUMPING");
                this.mob.getJumpControl().jump();
                this.operation = MoveControl.Operation.JUMPING;
            }

            CatHerder.LOGGER.debug("return");
            return;
        }

        CatHerder.LOGGER.debug("Call super.tick()");
        super.tick();
    }

}
