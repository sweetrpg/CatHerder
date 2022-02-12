package kittytalents.common.talent;

import kittytalents.api.registry.Talent;
import kittytalents.api.registry.TalentInstance;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class HappyEaterTalent extends TalentInstance implements kittytalents.api.inferface.ICatFoodHandler {

    public static final kittytalents.api.inferface.ICatFoodPredicate INNER_DYN_PRED = (stackIn) -> {
        Item item = stackIn.getItem();
        return item == Items.ROTTEN_FLESH || (item.isEdible() && ItemTags.FISHES.contains(item));
    };

    public HappyEaterTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public InteractionResultHolder<Float> setDogHunger(kittytalents.api.inferface.AbstractCatEntity dogIn, float hunger, float diff) {
        hunger += diff / 10 * this.level();
        return InteractionResultHolder.success(hunger);
    }

    @Override
    public boolean isFood(ItemStack stackIn) {
        return HappyEaterTalent.INNER_DYN_PRED.isFood(stackIn);
    }

    @Override
    public boolean canConsume(kittytalents.api.inferface.AbstractCatEntity dogIn, ItemStack stackIn, Entity entityIn) {
        if (this.level() >= 3) {

            Item item = stackIn.getItem();

            if (item == Items.ROTTEN_FLESH) {
                return true;
            }

            if (this.level() >= 5 && item.isEdible() && ItemTags.FISHES.contains(item)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public InteractionResult consume(kittytalents.api.inferface.AbstractCatEntity dogIn, ItemStack stackIn, Entity entityIn) {
        if (this.level() >= 3) {

            Item item = stackIn.getItem();

            if (item == Items.ROTTEN_FLESH) {
                dogIn.addHunger(30);
                dogIn.consumeItemFromStack(entityIn, stackIn);
                return InteractionResult.SUCCESS;
            }

            if (this.level() >= 5 && item.isEdible() && ItemTags.FISHES.contains(item)) {
                dogIn.addHunger(item.getFoodProperties().getNutrition() * 5);
                dogIn.consumeItemFromStack(entityIn, stackIn);
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.FAIL;
    }
}
