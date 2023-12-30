package com.sweetrpg.catherder.common;

import net.minecraft.world.food.FoodProperties;

public class FoodValues {

    public static final FoodProperties CHEESE = (new FoodProperties.Builder())
                                                        .nutrition(3)
                                                        .saturationMod(0.5f)
                                                        .fast().build();

    public static final FoodProperties KIBBLE = (new FoodProperties.Builder())
                                                        .nutrition(2)
                                                        .saturationMod(0.1f)
                                                        .fast().build();

    public static final FoodProperties CANNED_FOOD = (new FoodProperties.Builder())
                                                        .nutrition(4)
                                                        .saturationMod(0.1f)
                                                        .fast().build();

}
