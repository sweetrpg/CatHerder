package kittytalents.api.inferface;

import net.minecraft.world.item.ItemStack;

public interface IThrowableItem {

    /**
     * The stack the cat drops upon return
     * @param stack The stack the cat fetched
     * @param type The type fetched stack
     */
    public ItemStack getReturnStack(ItemStack stack);

    /**
     * The stack the cat renders in mouth
     * @param stack The stack the cat fetched
     */
    public ItemStack getRenderStack(ItemStack stack);
}
