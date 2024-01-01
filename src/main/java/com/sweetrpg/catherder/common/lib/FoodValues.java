package com.sweetrpg.catherder.common.lib;

import net.minecraft.world.food.FoodProperties;

public class FoodValues {

    public static final FoodProperties CHEESE = (new FoodProperties.Builder())
            .nutrition(3)
            .saturationMod(0.5f)
            .alwaysEat()
            .fast().build();

}
