package com.sweetrpg.catherder.common.event;

import com.sweetrpg.catherder.api.inferface.AbstractCatEntity;
import com.sweetrpg.catherder.api.inferface.ICatFoodHandler;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class MeatFoodHandler implements ICatFoodHandler {

    @Override
    public boolean isFood(ItemStack stackIn) {
        return stackIn.isEdible() && stackIn.getItem().getFoodProperties().isMeat() && stackIn.getItem() != Items.ROTTEN_FLESH;
    }

    @Override
    public boolean canConsume(AbstractCatEntity catIn, ItemStack stackIn, Entity entityIn) {
        return this.isFood(stackIn);
    }

    @Override
    public InteractionResult consume(AbstractCatEntity catIn, ItemStack stackIn, Entity entityIn) {

        if(catIn.getCatHunger() < catIn.getMaxHunger()) {
            if(!catIn.level.isClientSide) {
                int heal = stackIn.getItem().getFoodProperties().getNutrition() * 5;

                catIn.setCatHunger(catIn.getCatHunger() + heal);
                catIn.consumeItemFromStack(entityIn, stackIn);
            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;

    }

}
