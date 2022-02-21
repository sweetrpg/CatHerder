package com.sweetrpg.catherder.common.entity.ai;


import com.sweetrpg.catherder.api.feature.EnumMode;
import com.sweetrpg.catherder.common.entity.CatEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;

public class SkittishModeGoal<T extends LivingEntity> extends AvoidEntityGoal {

    private final CatEntity cat;

    public SkittishModeGoal(CatEntity catIn) {
        super(catIn, Entity.class, (entity) -> {
            return true;
        }, 16, 2, 2, (entity) -> {
            return true;
        });
        this.cat = catIn;
    }

    @Override
    public boolean canUse() {
        return this.cat.isMode(EnumMode.SKITTISH) && super.canUse();
    }
}
