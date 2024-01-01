package com.sweetrpg.catherder.common.entity.ai;


import com.sweetrpg.catherder.CatHerder;
import com.sweetrpg.catherder.api.feature.Mode;
import com.sweetrpg.catherder.common.config.ConfigHandler;
import com.sweetrpg.catherder.common.entity.CatEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.animal.Animal;

import java.util.Random;

public class SkittishModeGoal<T extends LivingEntity> extends AvoidEntityGoal {

    private final CatEntity cat;

    public SkittishModeGoal(CatEntity catIn) {
        super(catIn, Entity.class, (entity) -> {
//            CatHerder.LOGGER.debug("(to avoid) entity = {}", entity);
            return true;
        }, 16, 2, 2, (entity) -> {
//            CatHerder.LOGGER.debug("(on avoid) entity = {}", entity);
            return true;
        });
        this.cat = catIn;
    }

    @Override
    public boolean canUse() {
        if(!this.cat.isMode(Mode.SKITTISH)) {
            return false;
        }

        boolean canUse = super.canUse();
        if(!canUse) {
            return false;
        }

        Random random = this.cat.getRandom();

        // less likely to avoid owner
        if(this.cat.getOwner() == this.toAvoid) {
            return (random.nextInt(0, 100) < ConfigHandler.CLIENT.SKITTISH_OWNER.get());
        }

        // somewhat less likely to avoid other animals
        if(this.toAvoid instanceof Animal) {
            return (random.nextInt(0, 100) < ConfigHandler.CLIENT.SKITTISH_ANIMALS.get());
        }

        // more likely to avoid everything else
        return (random.nextInt(0, 100) < ConfigHandler.CLIENT.SKITTISH_OTHERS.get());
    }

}
