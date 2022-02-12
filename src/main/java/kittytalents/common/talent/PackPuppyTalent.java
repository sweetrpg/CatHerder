package kittytalents.common.talent;

import kittytalents.KittyTags;
import kittytalents.KittyTalents;
import kittytalents.api.registry.Talent;
import kittytalents.api.registry.TalentInstance;
import kittytalents.common.config.ConfigHandler;
import kittytalents.common.inventory.PackPuppyItemHandler;
import kittytalents.common.util.InventoryUtil;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
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

public class PackPuppyTalent extends TalentInstance {

    public static Capability<PackPuppyItemHandler> PACK_PUPPY_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});;

    private PackPuppyItemHandler packPuppyHandler;
    private LazyOptional<?> lazyPackPuppyHandler;

    public static Predicate<ItemEntity> SHOULD_PICKUP_ENTITY_ITEM = (entity) -> {
        return entity.isAlive() && !entity.hasPickUpDelay() && !KittyTags.PACK_PUPPY_BLACKLIST.contains(entity.getItem().getItem());// && !EntityAIFetch.BONE_PREDICATE.test(entity.getItem());
    };

    public PackPuppyTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
        PackPuppyItemHandler handler = new PackPuppyItemHandler();
        this.packPuppyHandler = handler;
        this.lazyPackPuppyHandler = LazyOptional.of(() -> handler);
    }

    public PackPuppyItemHandler inventory() {
        return this.packPuppyHandler;
    }

    @Override
    public void tick(kittytalents.api.inferface.AbstractCatEntity dogIn) {
        if (dogIn.isAlive() && !dogIn.level.isClientSide && this.level() >= 5) {
            List<ItemEntity> list = dogIn.level.getEntitiesOfClass(ItemEntity.class, dogIn.getBoundingBox().inflate(2.5D, 1D, 2.5D), SHOULD_PICKUP_ENTITY_ITEM);

            if (!list.isEmpty()) {
                for (ItemEntity entityItem : list) {
                    ItemStack remaining = InventoryUtil.addItem(this.packPuppyHandler, entityItem.getItem());

                    if (!remaining.isEmpty()) {
                        entityItem.setItem(remaining);
                    } else {
                        entityItem.discard();
                        dogIn.playSound(SoundEvents.ITEM_PICKUP, 0.25F, ((dogIn.level.random.nextFloat() - dogIn.level.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                    }
                }
            }
        }
    }

    @Override
    public InteractionResult processInteract(kittytalents.api.inferface.AbstractCatEntity dogIn, Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);

        if (dogIn.isTame() && this.level() > 0) { // Cat requirements
            if (playerIn.isShiftKeyDown() && stack.isEmpty()) { // Player requirements

                if (dogIn.canInteract(playerIn)) {

                    if (!playerIn.level.isClientSide) {
                        playerIn.displayClientMessage(new TranslatableComponent("talent.kittytalents.pack_puppy.version_migration"), false);
                    }
                    return InteractionResult.SUCCESS;
                }
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public void set(kittytalents.api.inferface.AbstractCatEntity cat, int preLevel) {
        // No need to drop anything if cat didn't have pack puppy
        if (preLevel > 0 && this.level == 0) {
            this.dropInventory(cat);
        }
    }

    @Override
    public void dropInventory(kittytalents.api.inferface.AbstractCatEntity dogIn) {
        //TODO either drop inventory or save to respawn data, currently does both
        // No need to drop anything if cat didn't have pack puppy
        for (int i = 0; i < this.packPuppyHandler.getSlots(); ++i) {
            Containers.dropItemStack(dogIn.level, dogIn.getX(), dogIn.getY(), dogIn.getZ(), this.packPuppyHandler.getStackInSlot(i));
            this.packPuppyHandler.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    @Override
    public void writeToNBT(kittytalents.api.inferface.AbstractCatEntity dogIn, CompoundTag compound) {
        super.writeToNBT(dogIn, compound);
        compound.merge(this.packPuppyHandler.serializeNBT());
    }

    @Override
    public void readFromNBT(kittytalents.api.inferface.AbstractCatEntity dogIn, CompoundTag compound) {
        super.readFromNBT(dogIn, compound);
        this.packPuppyHandler.deserializeNBT(compound);
    }

    // Left in for backwards compatibility for versions <= 2.0.0.5
    @Override
    public void onRead(kittytalents.api.inferface.AbstractCatEntity dogIn, CompoundTag compound) {
        this.packPuppyHandler.deserializeNBT(compound);
    }

    @Override
    public <T> LazyOptional<T> getCapability(kittytalents.api.inferface.AbstractCatEntity dogIn, Capability<T> cap, Direction side) {
        if (cap == PackPuppyTalent.PACK_PUPPY_CAPABILITY) {
            return (LazyOptional<T>) this.lazyPackPuppyHandler;
        }
        return null;
    }

    @Override
    public void invalidateCapabilities(kittytalents.api.inferface.AbstractCatEntity dogIn) {
        this.lazyPackPuppyHandler.invalidate();
    }

    @Override
    public boolean hasRenderer() {
        return ConfigHandler.CLIENT.RENDER_CHEST.get();
    }

    public static boolean hasInventory(kittytalents.api.inferface.AbstractCatEntity dogIn) {
        return dogIn.isAlive() && dogIn.getTalent(KittyTalents.PACK_PUPPY).isPresent();
    }
}
