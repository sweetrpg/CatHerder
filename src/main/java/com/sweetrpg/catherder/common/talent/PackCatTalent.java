package com.sweetrpg.catherder.common.talent;

import com.sweetrpg.catherder.api.inferface.AbstractCatEntity;
import com.sweetrpg.catherder.api.registry.Talent;
import com.sweetrpg.catherder.api.registry.TalentInstance;
import com.sweetrpg.catherder.common.config.ConfigHandler;
import com.sweetrpg.catherder.common.inventory.PackCatItemHandler;
import com.sweetrpg.catherder.common.registry.ModTags;
import com.sweetrpg.catherder.common.registry.ModTalents;
import com.sweetrpg.catherder.common.util.InventoryUtil;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;

import java.util.List;
import java.util.function.Predicate;

public class PackCatTalent extends TalentInstance {

    public static Capability<PackCatItemHandler> PACK_CAT_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });
    ;

    private PackCatItemHandler packCatItemHandler;
    private LazyOptional<?> lazyPackCatHandler;

    public static Predicate<ItemEntity> SHOULD_PICKUP_ENTITY_ITEM = (entity) -> {
        return entity.isAlive() && !entity.hasPickUpDelay() && !entity.getItem().is(ModTags.PACK_CAT_BLACKLIST);// && !EntityAIFetch.BONE_PREDICATE.test(entity.getItem());
    };

    public PackCatTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
        PackCatItemHandler handler = new PackCatItemHandler();
        this.packCatItemHandler = handler;
        this.lazyPackCatHandler = LazyOptional.of(() -> handler);
    }

    public PackCatItemHandler inventory() {
        return this.packCatItemHandler;
    }

    @Override
    public void tick(AbstractCatEntity catIn) {
        if(catIn.isAlive() && !catIn.level.isClientSide && this.level() >= 5) {
            List<ItemEntity> list = catIn.level.getEntitiesOfClass(ItemEntity.class, catIn.getBoundingBox().inflate(2.5D, 1D, 2.5D), SHOULD_PICKUP_ENTITY_ITEM);

            if(!list.isEmpty()) {
                for(ItemEntity entityItem : list) {
                    ItemStack remaining = InventoryUtil.addItem(this.packCatItemHandler, entityItem.getItem());

                    if(!remaining.isEmpty()) {
                        entityItem.setItem(remaining);
                    }
                    else {
                        entityItem.discard();
                        catIn.playSound(SoundEvents.ITEM_PICKUP, 0.25F, ((catIn.level.random.nextFloat() - catIn.level.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                    }
                }
            }
        }
    }

    @Override
    public InteractionResult processInteract(AbstractCatEntity catIn, Level worldIn, Player playerIn, InteractionHand handIn) {
//        ItemStack stack = playerIn.getItemInHand(handIn);
//
//        if (catIn.isTame() && this.level() > 0) { // Cat requirements
//            if (playerIn.isShiftKeyDown() && stack.isEmpty()) { // Player requirements
//
//                if (catIn.canInteract(playerIn)) {
//
//                    if (!playerIn.level.isClientSide) {
//                        playerIn.displayClientMessage(new TranslatableComponent("talent.catherder.pack_cat.version_migration"), false);
//                    }
//                    return InteractionResult.SUCCESS;
//                }
//            }
//        }

        return InteractionResult.PASS;
    }

    @Override
    public void set(AbstractCatEntity cat, int preLevel) {
        // No need to drop anything if cat didn't have pack cat
        if(preLevel > 0 && this.level == 0) {
            this.dropInventory(cat);
        }
    }

    @Override
    public void dropInventory(AbstractCatEntity catIn) {
        //TODO either drop inventory or save to respawn data, currently does both
        // No need to drop anything if cat didn't have pack cat
        for(int i = 0; i < this.packCatItemHandler.getSlots(); ++i) {
            Containers.dropItemStack(catIn.level, catIn.getX(), catIn.getY(), catIn.getZ(), this.packCatItemHandler.getStackInSlot(i));
            this.packCatItemHandler.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    @Override
    public void writeToNBT(AbstractCatEntity catIn, CompoundTag compound) {
        super.writeToNBT(catIn, compound);
        compound.merge(this.packCatItemHandler.serializeNBT());
    }

    @Override
    public void readFromNBT(AbstractCatEntity catIn, CompoundTag compound) {
        super.readFromNBT(catIn, compound);
        this.packCatItemHandler.deserializeNBT(compound);
    }

    // Left in for backwards compatibility for versions <= 2.0.0.5
    @Override
    public void onRead(AbstractCatEntity catIn, CompoundTag compound) {
        this.packCatItemHandler.deserializeNBT(compound);
    }

    @Override
    public <T> LazyOptional<T> getCapability(AbstractCatEntity catIn, Capability<T> cap, Direction side) {
        if(cap == PackCatTalent.PACK_CAT_CAPABILITY) {
            return (LazyOptional<T>) this.lazyPackCatHandler;
        }
        return null;
    }

    @Override
    public void invalidateCapabilities(AbstractCatEntity catIn) {
        this.lazyPackCatHandler.invalidate();
    }

    @Override
    public boolean hasRenderer() {
        return ConfigHandler.CLIENT.RENDER_CHEST.get();
    }

    public static boolean hasInventory(AbstractCatEntity catIn) {
        return catIn.isAlive() && catIn.getTalent(ModTalents.PACK_CAT).isPresent();
    }
}
