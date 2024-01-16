package com.sweetrpg.catherder.common.block.entity;

import com.sweetrpg.catherder.common.entity.CatEntity;
import com.sweetrpg.catherder.common.registry.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.UUID;

public class LitterboxBlockEntity extends PlacedBlockEntity {

//    private final ItemStackHandler inventory = new ItemStackHandler(5) {
//        @Override
//        protected void onContentsChanged(int slot) {
//            // When contents change mark needs save to disc
//            LitterboxBlockEntity.this.setChanged();
//        }
//
//        @Override
//        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
//            return FoodHandler.isFood(stack).isPresent();
//        }
//    };
//    private final LazyOptional<ItemStackHandler> itemStackHandler = LazyOptional.of(() -> this.inventory);

    public int timeoutCounter;

    public LitterboxBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntityTypes.LITTERBOX.get(), pos, blockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState blockState, BlockEntity blockEntity) {
        if(!(blockEntity instanceof LitterboxBlockEntity box)) {
            return;
        }

        // Only run update code every 5 ticks (0.25s)
        if(++box.timeoutCounter < 5) { return; }

        List<CatEntity> catList = box.level.getEntitiesOfClass(CatEntity.class, new AABB(pos).inflate(15, 15, 15));

        for(CatEntity cat : catList) {
            // TODO: make litterbox remember who placed and only their cats can attach to it
            UUID placerId = box.getPlacerId();
            if(placerId != null && placerId.equals(cat.getOwnerUUID()) /* && !cat.getLitterboxPos().isPresent() */) {
                cat.setLitterboxPos(box.worldPosition);
            }
        }

        box.timeoutCounter = 0;
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
//        this.inventory.deserializeNBT(compound);
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
//        compound.merge(this.inventory.serializeNBT());
    }

//    public ItemStackHandler getInventory() {
//        return this.inventory;
//    }

//    @Nonnull
//    @Override
//    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
//        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
//            return (LazyOptional<T>) this.itemStackHandler;
//        }
//        return super.getCapability(cap, side);
//    }
}
