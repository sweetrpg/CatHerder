package com.sweetrpg.catherder.common.talent;

import com.sweetrpg.catherder.api.inferface.AbstractCatEntity;
import com.sweetrpg.catherder.api.inferface.ICatFoodHandler;
import com.sweetrpg.catherder.api.inferface.ICatFoodPredicate;
import com.sweetrpg.catherder.api.registry.Talent;
import com.sweetrpg.catherder.api.registry.TalentInstance;
import com.sweetrpg.catherder.common.registry.ModTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class HappyEaterTalent extends TalentInstance implements ICatFoodHandler {

    public static final ICatFoodPredicate INNER_DYN_PRED = (stackIn) -> {
        Item item = stackIn.getItem();
        return item == Items.ROTTEN_FLESH || (item.isEdible() && (stackIn.is(ItemTags.FISHES) || stackIn.is(ModTags.MEAT)));
    };

    public HappyEaterTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public InteractionResultHolder<Float> setCatHunger(AbstractCatEntity catIn, float hunger, float diff) {
        hunger += diff / 10 * this.level();
        return InteractionResultHolder.success(hunger);
    }

    @Override
    public boolean isFood(ItemStack stackIn) {
        return HappyEaterTalent.INNER_DYN_PRED.isFood(stackIn);
    }

    @Override
    public boolean canConsume(AbstractCatEntity catIn, ItemStack stackIn, Entity entityIn) {
        Item item = stackIn.getItem();

        if(item.isEdible()) {
            if(stackIn.is(ItemTags.FISHES)) {
                return true;
            }

            if(this.level() >= 3) {
                if(stackIn.is(ModTags.MEAT)) {
                    return true;
                }

                if(this.level() >= 5) {
                    if(item == Items.ROTTEN_FLESH) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public InteractionResult consume(AbstractCatEntity catIn, ItemStack stackIn, Entity entityIn) {
        Item item = stackIn.getItem();

        if(item.isEdible()) {
            if(stackIn.is(ItemTags.FISHES)) {
                catIn.addHunger(item.getFoodProperties().getNutrition() * 7);
                catIn.consumeItemFromStack(entityIn, stackIn);
                return InteractionResult.SUCCESS;
            }

            if(this.level() >= 3) {
                if(stackIn.is(ModTags.MEAT)) {
                    catIn.addHunger(item.getFoodProperties().getNutrition() * 7);
                    catIn.consumeItemFromStack(entityIn, stackIn);
                    return InteractionResult.SUCCESS;
                }

                if(this.level() >= 5) {
                    if(item == Items.ROTTEN_FLESH) {
                        catIn.addHunger(30);
                        catIn.consumeItemFromStack(entityIn, stackIn);
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        }

        return InteractionResult.FAIL;
    }
}
