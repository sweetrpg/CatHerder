package com.sweetrpg.catherder.common.entity.ai;

import com.sweetrpg.catherder.api.feature.EnumMode;
import com.sweetrpg.catherder.api.inferface.IThrowableItem;
import com.sweetrpg.catherder.common.entity.CatEntity;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

public class FetchGoal extends MoveToClosestItemGoal {

    public static Predicate<ItemStack> BONE_PREDICATE = (item) -> item.getItem() instanceof IThrowableItem;

    public FetchGoal(CatEntity catIn, double speedIn, float range) {
        super(catIn, speedIn, range, 2, BONE_PREDICATE);
    }

    @Override
    public boolean canUse() {
        if (this.cat.isInSittingPose()) {
            return false;
        } else if (this.cat.hasToy()) {
            return false;
        }

        return this.cat.getMode() == EnumMode.DOCILE && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        if (this.cat.isInSittingPose()) {
            return false;
        } else if (this.cat.hasToy()) {
            return false;
        }

        return this.cat.getMode() == EnumMode.DOCILE && super.canContinueToUse();
    }

    @Override
    public void tick() {
        super.tick();


    }

    @Override
    public void stop() {
        // Cat doesn't have bone and is close enough to target
        if (!this.cat.hasToy() && this.cat.distanceTo(this.target) < this.minDist * this.minDist) {
            if (this.target.isAlive() && !this.target.hasPickUpDelay()) {

                this.cat.setToyVariant(this.target.getItem());

                this.target.discard();
            }
        }

        super.stop();
    }
}
