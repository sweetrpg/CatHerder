package com.sweetrpg.catherder.common.entity.accessory;

import com.sweetrpg.catherder.CatHerder;
import com.sweetrpg.catherder.api.inferface.AbstractCatEntity;
import com.sweetrpg.catherder.api.inferface.IColoredObject;
import com.sweetrpg.catherder.api.registry.Accessory;
import com.sweetrpg.catherder.api.registry.AccessoryInstance;
import com.sweetrpg.catherder.api.registry.AccessoryType;
import com.sweetrpg.catherder.api.inferface.ICatAlteration;
import com.sweetrpg.catherder.common.util.ColorCache;
import com.sweetrpg.catherder.common.util.Util;
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
            CatHerder.LOGGER.info("Unable to set set dyable accessory color.");
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

    public class DyeableAccessoryInstance extends AccessoryInstance implements ICatAlteration, IColoredObject {

        private ColorCache color;

        public DyeableAccessoryInstance(int colorIn) {
            this(ColorCache.make(colorIn));
        }

        public DyeableAccessoryInstance(ColorCache colorIn) {
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
        public InteractionResult processInteract(AbstractCatEntity catIn, Level worldIn, Player playerIn, InteractionHand handIn) {
            ItemStack stack = playerIn.getItemInHand(handIn);

            DyeColor dyeColor = DyeColor.getColor(stack);
            if (dyeColor != null) {
                int colorNew = Util.colorDye(this.color.get(), dyeColor);

                // No change
                if (this.color.is(colorNew)) {
                    return InteractionResult.FAIL;
                }

                this.color = ColorCache.make(colorNew);
                catIn.consumeItemFromStack(playerIn, stack);
                // Make sure to sync change with client
                catIn.markAccessoriesDirty();
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        }
    }

}
