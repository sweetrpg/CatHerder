package com.sweetrpg.catherder.common.entity.ai;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;

/**
 * Custom path navigation that overrides canUpdatePath() so that the cat
 * can follow paths even when its onGround flag is briefly false (e.g. on
 * slopes or uneven terrain). In vanilla 1.19 the GroundPathNavigation
 * skips followThePath() when the entity is not on the ground, causing
 * the cat to freeze mid-path.
 */
public class CatPathNavigation extends GroundPathNavigation {

    public CatPathNavigation(Mob mob, Level level) {
        super(mob, level);
    }

    @Override
    protected boolean canUpdatePath() {
        return this.mob.isOnGround() || this.mob.isInWaterRainOrBubble() || this.mob.isPassenger() || this.mob.fallDistance < 0.5F;
    }
}
