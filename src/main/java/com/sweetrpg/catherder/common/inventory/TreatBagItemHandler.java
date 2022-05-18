package com.sweetrpg.catherder.common.inventory;

import javax.annotation.Nonnull;

import com.sweetrpg.catherder.common.registry.ModTags;
import com.sweetrpg.catherder.api.feature.FoodHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class TreatBagItemHandler extends ItemStackHandler {

    private ItemStack bag;

    public TreatBagItemHandler(ItemStack bag) {
        super(5);
        this.bag = bag;

        CompoundTag inventoryNBT = bag.getTagElement("inventory");
        if (inventoryNBT != null) {
            this.deserializeNBT(inventoryNBT);
        }
    }

    @Override
    protected void onContentsChanged(int slot) {
        this.bag.getOrCreateTagElement("inventory").merge(this.serializeNBT());
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return stack.is(ModTags.TREATS) || FoodHandler.isFood(stack).isPresent();
    }
}
