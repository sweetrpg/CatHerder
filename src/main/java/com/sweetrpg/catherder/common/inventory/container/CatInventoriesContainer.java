package com.sweetrpg.catherder.common.inventory.container;

import com.sweetrpg.catherder.CatHerder;
import com.sweetrpg.catherder.common.registry.ModContainerTypes;
import com.sweetrpg.catherder.common.inventory.PackCatItemHandler;
import com.sweetrpg.catherder.common.inventory.container.slot.CatInventorySlot;
import com.sweetrpg.catherder.common.registry.ModTalents;
import com.sweetrpg.catherder.common.talent.PackCatTalent;
import com.sweetrpg.catherder.common.entity.CatEntity;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ProPercivalalb
 */
public class CatInventoriesContainer extends AbstractContainerMenu {

    private Level world;
    private Player player;
    private DataSlot position;
    private SimpleContainerData trackableArray;
    private final List<CatInventorySlot> catSlots = new ArrayList<>();
    private int possibleSlots = 0;

    //Server method
    public CatInventoriesContainer(int windowId, Inventory playerInventory, SimpleContainerData trackableArray) {
        super(ModContainerTypes.CAT_INVENTORIES.get(), windowId);
        this.world = playerInventory.player.level;
        this.player = playerInventory.player;
        this.position = DataSlot.standalone();
        checkContainerDataCount(trackableArray, 1);
        this.addDataSlot(this.position);
        this.trackableArray = trackableArray;

        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }

        this.addCatSlots();
    }

    public void addCatSlots() {
        final int TOTAL_COLUMNS = 9;

        int page = this.position.get();
        int drawingColumn = 0;

        for (int i = 0; i < this.trackableArray.getCount(); i++) {
            int entityId = this.trackableArray.get(i);
            Entity entity = this.world.getEntity(entityId);

            if (entity instanceof CatEntity) {
                CatEntity cat = (CatEntity) entity;

                PackCatItemHandler packInventory = cat.getTalent(ModTalents.PACK_CAT)
                                                      .map((inst) -> inst.cast(PackCatTalent.class).inventory()).orElse(null);
                if (packInventory == null) {
                    continue;
                }

                int level = Mth.clamp(cat.getCatLevel(ModTalents.PACK_CAT), 0, 5); // Number of rows for this cat
                int numCols = Mth.clamp(level, 0, Math.max(0, TOTAL_COLUMNS)); // Number of rows to draw

                for (int row = 0; row < 3; row++) {
                    for (int col = 0; col < numCols; col++) {
                        CatInventorySlot slot = new CatInventorySlot(cat, this.player, packInventory, drawingColumn + col, row, col, col * 3 + row, 8 + 18 * (drawingColumn + col - page), 18 * row + 18);
                        this.addCatSlot(slot);
                        int adjustedColumn = slot.getOverallColumn() - page;
                        if (adjustedColumn - page < 0 || adjustedColumn - page >= 9) {
                            slot.setEnabled(false);
                        }
                    }
                }

                this.possibleSlots += level;
                drawingColumn += numCols;
            }
        }

    }

    @Override
    public void setData(int id, int data) {
        super.setData(id, data);

        if (id == 0) {
            for (int i = 0; i < this.catSlots.size(); i++) {
                CatInventorySlot slot = this.catSlots.get(i);
                CatInventorySlot newSlot = new CatInventorySlot(slot, 8 + 18 * (slot.getOverallColumn() - data));
                this.replaceCatSlot(i, newSlot);
                int adjustedColumn = slot.getOverallColumn() - data;
                if (adjustedColumn < 0 || adjustedColumn >= 9) {
                    newSlot.setEnabled(false);
                }
            }
        }

    }

    private void addCatSlot(CatInventorySlot slotIn) {
        this.addSlot(slotIn);
        this.catSlots.add(slotIn);
    }

    private void replaceCatSlot(int i, CatInventorySlot slotIn) {
        this.catSlots.set(i, slotIn);
        // Work around to set Slot#slotNumber (MCP name) which is Slot#index in official
        // mappings. Needed because SlotItemHandler#index shadows the latter.
        Slot s = slotIn;
        this.slots.set(s.index, slotIn);
    }

    public int getTotalNumColumns() {
        return this.possibleSlots;
    }

    public int getPage() {
        return this.position.get();
    }

    public void setPage(int page) {
        this.position.set(page);
    }

    public List<CatInventorySlot> getSlots() {
        return this.catSlots;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(i);

        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            int startIndex = this.slots.size() - this.catSlots.size() + this.position.get() * 3;
            int endIndex = Math.min(startIndex + 9 * 3, this.slots.size());

            if (i >= this.slots.size() - this.catSlots.size() && i < this.slots.size()) {
                if (!moveItemStackTo(itemstack1, 0, this.slots.size() - this.catSlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            }
            else if (!moveItemStackTo(itemstack1, this.slots.size() - this.catSlots.size(), this.slots.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }
        }

        return itemstack;
    }
}
