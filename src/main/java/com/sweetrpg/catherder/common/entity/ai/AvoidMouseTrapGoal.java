package com.sweetrpg.catherder.common.entity.ai;

import net.minecraft.world.entity.ai.goal.Goal;

public class AvoidMouseTrapGoal extends Goal {
    @Override
    public boolean canUse() {
        return false;
    }
}
