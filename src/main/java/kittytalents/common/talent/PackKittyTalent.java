package kittytalents.common.talent;

import kittytalents.KittyTags;
import kittytalents.KittyTalents;
import kittytalents.api.registry.Talent;
import kittytalents.api.registry.TalentInstance;
import kittytalents.common.config.ConfigHandler;
import kittytalents.common.inventory.PackKittyItemHandler;
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

public class PackKittyTalent extends TalentInstance {

    public static Capability<PackKittyItemHandler> PACK_PUPPY_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});;

    private PackKittyItemHandler packPuppyHandler;
    private LazyOptional<?> lazyPackPuppyHandler;

    public static Predicate<ItemEntity> SHOULD_PICKUP_ENTITY_ITEM = (entity) -> {
        return entity.isAlive() && !entity.hasPickUpDelay() && !KittyTags.PACK_KITTY_BLACKLIST.contains(entity.getItem().getItem());// && !EntityAIFetch.BONE_PREDICATE.test(entity.getItem());
    };

    public PackKittyTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
        PackKittyItemHandler handler = new PackKittyItemHandler();
        this.packPuppyHandler = handler;
        this.lazyPackPuppyHandler = LazyOptional.of(() -> handler);
    }

    public PackKittyItemHandler inventory() {
        return this.packPuppyHandler;
    }

    @Override
    public void tick(kittytalents.api.inferface.AbstractCatEntity catIn) {
        if (catIn.isAlive() && !catIn.level.isClientSide && this.level() >= 5) {
            List<ItemEntity> list = catIn.level.getEntitiesOfClass(ItemEntity.class, catIn.getBoundingBox().inflate(2.5D, 1D, 2.5D), SHOULD_PICKUP_ENTITY_ITEM);

            if (!list.isEmpty()) {
                for (ItemEntity entityItem : list) {
                    ItemStack remaining = InventoryUtil.addItem(this.packPuppyHandler, entityItem.getItem());

                    if (!remaining.isEmpty()) {
                        entityItem.setItem(remaining);
                    } else {
                        entityItem.discard();
                        catIn.playSound(SoundEvents.ITEM_PICKUP, 0.25F, ((catIn.level.random.nextFloat() - catIn.level.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                    }
                }
            }
        }
    }

    @Override
    public InteractionResult processInteract(kittytalents.api.inferface.AbstractCatEntity catIn, Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);

        if (catIn.isTame() && this.level() > 0) { // Cat requirements
            if (playerIn.isShiftKeyDown() && stack.isEmpty()) { // Player requirements

                if (catIn.canInteract(playerIn)) {

                    if (!playerIn.level.isClientSide) {
                        playerIn.displayClientMessage(new TranslatableComponent("talent.kittytalents.pack_kitty.version_migration"), false);
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
    public void dropInventory(kittytalents.api.inferface.AbstractCatEntity catIn) {
        //TODO either drop inventory or save to respawn data, currently does both
        // No need to drop anything if cat didn't have pack puppy
        for (int i = 0; i < this.packPuppyHandler.getSlots(); ++i) {
            Containers.dropItemStack(catIn.level, catIn.getX(), catIn.getY(), catIn.getZ(), this.packPuppyHandler.getStackInSlot(i));
            this.packPuppyHandler.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    @Override
    public void writeToNBT(kittytalents.api.inferface.AbstractCatEntity catIn, CompoundTag compound) {
        super.writeToNBT(catIn, compound);
        compound.merge(this.packPuppyHandler.serializeNBT());
    }

    @Override
    public void readFromNBT(kittytalents.api.inferface.AbstractCatEntity catIn, CompoundTag compound) {
        super.readFromNBT(catIn, compound);
        this.packPuppyHandler.deserializeNBT(compound);
    }

    // Left in for backwards compatibility for versions <= 2.0.0.5
    @Override
    public void onRead(kittytalents.api.inferface.AbstractCatEntity catIn, CompoundTag compound) {
        this.packPuppyHandler.deserializeNBT(compound);
    }

    @Override
    public <T> LazyOptional<T> getCapability(kittytalents.api.inferface.AbstractCatEntity catIn, Capability<T> cap, Direction side) {
        if (cap == PackKittyTalent.PACK_PUPPY_CAPABILITY) {
            return (LazyOptional<T>) this.lazyPackPuppyHandler;
        }
        return null;
    }

    @Override
    public void invalidateCapabilities(kittytalents.api.inferface.AbstractCatEntity catIn) {
        this.lazyPackPuppyHandler.invalidate();
    }

    @Override
    public boolean hasRenderer() {
        return ConfigHandler.CLIENT.RENDER_CHEST.get();
    }

    public static boolean hasInventory(kittytalents.api.inferface.AbstractCatEntity catIn) {
        return catIn.isAlive() && catIn.getTalent(KittyTalents.PACK_KITTY).isPresent();
    }
}
