package com.sweetrpg.catherder.common.item;

import com.sweetrpg.catherder.api.inferface.AbstractCatEntity;
import com.sweetrpg.catherder.api.inferface.ICatFoodHandler;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CatToyItem extends Item implements ICatFoodHandler {

    public CatToyItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFood(ItemStack stackIn) {
        return stackIn.getItem() == this;
    }

    @Override
    public boolean canConsume(AbstractCatEntity catIn, ItemStack stackIn, Entity entityIn) {
        return true;
    }

    @Override
    public InteractionResult consume(AbstractCatEntity catIn, ItemStack stackIn, Entity entityIn) {
        if (!catIn.level.isClientSide) {
            catIn.addEffect(new MobEffectInstance(MobEffects.GLOWING, 100, 1, false, true));
            catIn.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 6, false, true));
            catIn.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 2, false, true));
            catIn.consumeItemFromStack(entityIn, stackIn);
        }

        return InteractionResult.SUCCESS;
    }

}
