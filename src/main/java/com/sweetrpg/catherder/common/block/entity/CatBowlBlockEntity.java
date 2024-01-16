package com.sweetrpg.catherder.common.block.entity;

import com.sweetrpg.catherder.api.feature.FoodHandler;
import com.sweetrpg.catherder.common.entity.CatEntity;
import com.sweetrpg.catherder.common.inventory.container.FoodBowlContainer;
import com.sweetrpg.catherder.common.lib.Constants;
import com.sweetrpg.catherder.common.registry.ModBlockEntityTypes;
import com.sweetrpg.catherder.common.util.InventoryUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class CatBowlBlockEntity extends PlacedBlockEntity implements MenuProvider {

    private final ItemStackHandler inventory = new ItemStackHandler(5) {
        @Override
        protected void onContentsChanged(int slot) {
            // When contents change mark needs save to disc
            CatBowlBlockEntity.this.setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return FoodHandler.isFood(stack).isPresent();
        }
    };
    private final LazyOptional<ItemStackHandler> itemStackHandler = LazyOptional.of(() -> this.inventory);

    public int timeoutCounter;

    public CatBowlBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntityTypes.CAT_BOWL.get(), pos, blockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState blockState, BlockEntity blockEntity) {
        if(!(blockEntity instanceof CatBowlBlockEntity bowl)) {
            return;
        }

        // Only run update code every 5 ticks (0.25s)
        if(++bowl.timeoutCounter < 5) { return; }

        List<CatEntity> catList = bowl.level.getEntitiesOfClass(CatEntity.class, new AABB(pos).inflate(15, 15, 15));

        for(CatEntity cat : catList) {
            //TODO make cat bowl remember who placed and only their cats can attach to the bowl
            UUID placerId = bowl.getPlacerId();
            if(placerId != null && placerId.equals(cat.getOwnerUUID()) /*&& !cat.getBowlPos().isPresent()*/) {
                cat.setBowlPos(bowl.worldPosition);
            }

            if(cat.isHungry()) {
                InventoryUtil.feedCatFrom(cat, null, bowl.inventory);
            }
        }

        bowl.timeoutCounter = 0;
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        this.inventory.deserializeNBT(compound);
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        compound.merge(this.inventory.serializeNBT());
    }

    public ItemStackHandler getInventory() {
        return this.inventory;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return (LazyOptional<T>) this.itemStackHandler;
        }
        return super.getCapability(cap, side);
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent(Constants.TRANSLATION_KEY_CONTAINER_CAT_BOWL);
    }

    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerIn) {
        return new FoodBowlContainer(windowId, this.level, this.worldPosition, playerInventory, playerIn);
    }
}
