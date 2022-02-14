package com.sweetrpg.catherder.common.entity.ai;

import com.sweetrpg.catherder.CatHerder;
import com.sweetrpg.catherder.api.feature.EnumMode;
import com.sweetrpg.catherder.common.item.PatrolItem;
import com.sweetrpg.catherder.common.entity.CatEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;

import java.util.EnumSet;
import java.util.List;

public class PatrolAreaGoal extends Goal {

    public final CatEntity cat;
    private final PathNavigation navigator;
    public int index;
    private int timeToRecalcPath;

    public PatrolAreaGoal(CatEntity catIn) {
        this.cat = catIn;
        this.navigator = catIn.getNavigation();
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return this.cat.getMode() == EnumMode.PATROL && this.cat.getTarget() == null && !this.cat.getData(PatrolItem.POS).isEmpty();
    }

    @Override
    public boolean canContinueToUse() {
        return this.cat.getMode() == EnumMode.PATROL && this.cat.getTarget() == null && !this.cat.getData(PatrolItem.POS).isEmpty();
    }

    @Override
    public void start() {
        this.timeToRecalcPath = 0;
        this.index = 0;
    }

    @Override
    public void stop() {
        this.cat.getNavigation().stop();
    }

    @Override
    public void tick() {
        if (!this.cat.isInSittingPose()) {
            if (--this.timeToRecalcPath <= 0) {
                this.timeToRecalcPath = 10;

                List<BlockPos> patrolPos = this.cat.getData(PatrolItem.POS);

                this.index = Mth.clamp(this.index, 0, patrolPos.size() - 1);
                BlockPos pos = patrolPos.get(this.index);

                CatHerder.LOGGER.info("Update" + this.index);

                if (this.cat.blockPosition().closerThan(pos, 2D) || !this.navigator.moveTo(pos.getX(), pos.getY(), pos.getZ(), 0.8D)) {
                    ++this.index;
                    this.index %= patrolPos.size();
                }
            }
        }
    }

}
