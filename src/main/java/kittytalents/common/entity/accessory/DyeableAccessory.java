package kittytalents.common.entity.accessory;

import kittytalents.KittyTalents2;
import kittytalents.api.inferface.IColoredObject;
import kittytalents.api.registry.Accessory;
import kittytalents.api.registry.AccessoryInstance;
import kittytalents.api.registry.AccessoryType;
import kittytalents.common.util.ColourCache;
import kittytalents.common.util.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class DyeableAccessory extends Accessory {

    public DyeableAccessory(Supplier<? extends AccessoryType> typeIn, Supplier<? extends ItemLike> itemIn) {
        super(typeIn, itemIn);
    }

    @Override
    public AccessoryInstance createInstance(FriendlyByteBuf buf) {
        return this.create(buf.readInt());
    }

    @Override
    public void write(AccessoryInstance instance, FriendlyByteBuf buf) {
        DyeableAccessoryInstance exact = instance.cast(DyeableAccessoryInstance.class);
        buf.writeInt(exact.getColorInteger());
    }

    @Override
    public void write(AccessoryInstance instance, CompoundTag compound) {
        DyeableAccessoryInstance exact = instance.cast(DyeableAccessoryInstance.class);
        compound.putInt("color", exact.getColorInteger());
    }

    @Override
    public AccessoryInstance read(CompoundTag compound) {
        return this.create(compound.getInt("color"));
    }

    @Override
    public AccessoryInstance getDefault() {
        return this.create(0);
    }

    @Override
    public ItemStack getReturnItem(AccessoryInstance instance) {
        DyeableAccessoryInstance exact = instance.cast(DyeableAccessoryInstance.class);

        ItemStack returnStack = super.getReturnItem(instance);
        if (returnStack.getItem() instanceof DyeableLeatherItem) {
            ((DyeableLeatherItem) returnStack.getItem()).setColor(returnStack, exact.getColorInteger());
        } else {
            KittyTalents2.LOGGER.info("Unable to set set dyable accessory color.");
        }

        return returnStack;
    }

    public AccessoryInstance create(int color) {
        return new DyeableAccessoryInstance(color);
    }

    @Override
    public AccessoryInstance createFromStack(ItemStack stackIn) {
        Item item = stackIn.getItem();
        if (item instanceof DyeableLeatherItem) {
            return this.create(((DyeableLeatherItem) item).getColor(stackIn));
        }

        return this.getDefault();
    }

    public class DyeableAccessoryInstance extends AccessoryInstance implements kittytalents.api.inferface.ICatAlteration, IColoredObject {

        private ColourCache color;

        public DyeableAccessoryInstance(int colorIn) {
            this(ColourCache.make(colorIn));
        }

        public DyeableAccessoryInstance(ColourCache colorIn) {
            super(DyeableAccessory.this);
            this.color = colorIn;
        }

        @Override
        public float[] getColor() {
            return this.color.getFloatArray();
        }

        public int getColorInteger() {
            return this.color.get();
        }

        @Override
        public AccessoryInstance copy() {
            return new DyeableAccessoryInstance(this.color);
        }

        @Override
        public InteractionResult processInteract(kittytalents.api.inferface.AbstractCatEntity catIn, Level worldIn, Player playerIn, InteractionHand handIn) {
            ItemStack stack = playerIn.getItemInHand(handIn);

            DyeColor dyeColor = DyeColor.getColor(stack);
            if (dyeColor != null) {
                int colorNew = Util.colorDye(this.color.get(), dyeColor);

                // No change
                if (this.color.is(colorNew)) {
                    return InteractionResult.FAIL;
                }

                this.color = ColourCache.make(colorNew);
                catIn.consumeItemFromStack(playerIn, stack);
                // Make sure to sync change with client
                catIn.markAccessoriesDirty();
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        }
    }

}
