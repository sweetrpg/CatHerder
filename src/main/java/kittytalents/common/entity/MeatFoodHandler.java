package kittytalents.common.entity;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class MeatFoodHandler implements kittytalents.api.inferface.ICatFoodHandler {

    @Override
    public boolean isFood(ItemStack stackIn) {
        return stackIn.isEdible() && stackIn.getItem().getFoodProperties().isMeat() && stackIn.getItem() != Items.ROTTEN_FLESH;
    }

    @Override
    public boolean canConsume(kittytalents.api.inferface.AbstractCatEntity dogIn, ItemStack stackIn, Entity entityIn) {
        return this.isFood(stackIn);
    }

    @Override
    public InteractionResult consume(kittytalents.api.inferface.AbstractCatEntity dogIn, ItemStack stackIn, Entity entityIn) {

        if (dogIn.getDogHunger() < dogIn.getMaxHunger()) {
            if (!dogIn.level.isClientSide) {
                int heal = stackIn.getItem().getFoodProperties().getNutrition() * 5;

                dogIn.setDogHunger(dogIn.getDogHunger() + heal);
                dogIn.consumeItemFromStack(entityIn, stackIn);
            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;

    }

}
