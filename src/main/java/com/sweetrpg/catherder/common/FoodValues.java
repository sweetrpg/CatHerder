package com.sweetrpg.catherder.common;

import net.minecraft.world.food.FoodProperties;

public class FoodValues {

    public static final FoodProperties CHEESE = (new FoodProperties.Builder())
                                                        .nutrition(3)
                                                        .saturationMod(0.5f)
                                                        .fast().build();

}
