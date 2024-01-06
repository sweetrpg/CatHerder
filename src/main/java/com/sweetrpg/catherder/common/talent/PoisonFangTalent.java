package com.sweetrpg.catherder.common.talent;

import com.sweetrpg.catherder.api.inferface.AbstractCatEntity;
import com.sweetrpg.catherder.api.registry.Talent;
import com.sweetrpg.catherder.api.registry.TalentInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class PoisonFangTalent extends TalentInstance {

    public PoisonFangTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public InteractionResult processInteract(AbstractCatEntity catIn, Level worldIn, Player playerIn, InteractionHand handIn) {
        if(catIn.isTame()) {
            if(this.level() < 5) {
                return InteractionResult.PASS;
            }

            ItemStack stack = playerIn.getItemInHand(handIn);

            if(stack.getItem() == Items.SPIDER_EYE) {

                if(playerIn.getEffect(MobEffects.POISON) == null || catIn.getCatHunger() < 30) {
                    return InteractionResult.FAIL;
                }

                if(!worldIn.isClientSide) {
                    playerIn.removeAllEffects();
                    catIn.setCatHunger(catIn.getCatHunger() - 30);
                    catIn.consumeItemFromStack(playerIn, stack);
                }

                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public InteractionResult isPotionApplicable(AbstractCatEntity catIn, MobEffectInstance effectIn) {
        if(this.level() >= 3) {
            if(effectIn.getEffect() == MobEffects.POISON) {
                return InteractionResult.FAIL;
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public InteractionResult attackEntityAsMob(AbstractCatEntity cat, Entity entity) {
        if(entity instanceof LivingEntity) {
            if(this.level() > 0) {
                ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.POISON, this.level() * 20, 0));
                return InteractionResult.PASS;
            }
        }

        return InteractionResult.PASS;
    }
}
