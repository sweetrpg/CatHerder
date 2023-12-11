package com.sweetrpg.catherder.api.inferface;

import net.minecraft.world.item.ItemStack;

@FunctionalInterface
public interface ICatFoodPredicate {

    /**
     * Determines if the stack could ever be food for a cat, i.e
     * the stack could be fed to the cat under certain conditions
     * Used to check if the stack can go in cat bowl or treat bag
     * @param stackIn The stack
     * @return If the start could ever be fed to a cat
     */
    public boolean isFood(ItemStack stackIn);
}