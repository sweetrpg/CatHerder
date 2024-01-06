package com.sweetrpg.catherder.common.inventory.container;

import com.sweetrpg.catherder.CatHerder;
import com.sweetrpg.catherder.common.registry.ModContainerTypes;
import com.sweetrpg.catherder.api.inferface.AbstractCatEntity;
import com.sweetrpg.catherder.common.registry.ModTalents;
import com.sweetrpg.catherder.common.talent.PackCatTalent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

/**
 * @author ProPercivalalb
 */
public class PackCatContainer extends AbstractContainerMenu {

    private AbstractCatEntity cat;
    private ItemStackHandler packInventory;
    private int level;

    public PackCatContainer(int windowId, Inventory playerInventory, AbstractCatEntity catIn) {
        super(ModContainerTypes.PACK_CAT.get(), windowId);
        this.cat = catIn;
        this.level = Mth.clamp(catIn.getCatLevel(ModTalents.PACK_CAT), 0, 5);
        this.packInventory = catIn.getCapability(PackCatTalent.PACK_CAT_CAPABILITY).orElseThrow(() -> new RuntimeException("Item handler not present."));

        for (int j = 0; j < 3; j++) {
            for (int i1 = 0; i1 < this.level; i1++) {
                this.addSlot(new SlotItemHandler(this.packInventory, i1 * 3 + j, 79 + 18 * i1, 1 + 18 * j + 24));
            }
        }

        int var3;
        int var4;

        for (var3 = 0; var3 < 3; ++var3) {
            for (var4 = 0; var4 < 9; ++var4) {
                this.addSlot(new Slot(playerInventory, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
            }
        }

        for (var3 = 0; var3 < 9; ++var3) {
            this.addSlot(new Slot(playerInventory, var3, 8 + var3 * 18, 142));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(i);
        int packcatLevel = Mth.clamp(this.level, 0, 5);

        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            if (i < 3 * packcatLevel) {
                if (!this.moveItemStackTo(itemstack1, 3 * packcatLevel, this.slots.size(), true))
                    return ItemStack.EMPTY;
            }
            else if (!this.moveItemStackTo(itemstack1, 0, 3 * packcatLevel, false))
                return ItemStack.EMPTY;

            if (itemstack1.isEmpty())
                slot.set(ItemStack.EMPTY);
            else
                slot.setChanged();

            if (itemstack1.getCount() == itemstack.getCount())
                return ItemStack.EMPTY;
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.cat.distanceToSqr(player) < 144D;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
    }

    public AbstractCatEntity getCat() {
        return this.cat;
    }

    public int getCatLevel() {
        return this.level;
    }
}
