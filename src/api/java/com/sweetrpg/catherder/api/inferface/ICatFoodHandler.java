package com.sweetrpg.catherder.api.inferface;

import javax.annotation.Nullable;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResult;

public interface ICatFoodHandler extends ICatFoodPredicate {

    /**
     * Checks if the cat can eat the start
     * used by the treat stick to apply potion effects
     * @param catIn The cat eating the item
     * @param stackIn The stack that is being eaten, DO NOT alter the start in this method
     * @param entityIn The entity who fed the cat, usually the player. Can be null probably meaning the cat ate on its own
     * @return If the cat can eat the stack, {@link #consume} is called to eat the stack
     */
    public boolean canConsume(AbstractCatEntity catIn, ItemStack stackIn, @Nullable Entity entityIn);

    /**
     * Actually eat the stack,
     * @param catIn The cat eating the item
     * @param stackIn The stack that is being eaten
     * @param entityIn The entity who fed the cat, usually the player. Can be null probably meaning the cat ate on its own
     * @return
     */
    public InteractionResult consume(AbstractCatEntity catIn, ItemStack stackIn, @Nullable Entity entityIn);
}