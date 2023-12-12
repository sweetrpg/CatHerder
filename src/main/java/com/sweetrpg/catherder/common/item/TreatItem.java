package com.sweetrpg.catherder.common.item;

import com.sweetrpg.catherder.CatHerder;
import com.sweetrpg.catherder.api.feature.CatLevel;
import com.sweetrpg.catherder.api.inferface.AbstractCatEntity;
import com.sweetrpg.catherder.api.inferface.ICatItem;
import com.sweetrpg.catherder.common.entity.CatEntity;
import com.sweetrpg.catherder.common.lib.Constants;
import com.sweetrpg.catherder.common.registry.ModEntityTypes;
import com.sweetrpg.catherder.common.registry.ModItems;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class TreatItem extends Item implements ICatItem {

    private final int maxLevel;
    private final CatLevel.Type type;

    public TreatItem(int maxLevel, CatLevel.Type typeIn, Properties properties) {
        super(properties);
        this.maxLevel = maxLevel;
        this.type = typeIn;
    }

//    @Mod.EventBusSubscriber(modid = CatHerderAPI.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
//    public static class CatTrainEvent {
//
//    }

    @Override
    public InteractionResult processInteract(AbstractCatEntity catIn, Level worldIn, Player playerIn, InteractionHand handIn) {
        if(!catIn.isTame() || !catIn.canInteract(playerIn)) {
            return InteractionResult.FAIL;
        }

        CatLevel catLevel = catIn.getCatLevel();

        if(catIn.getAge() < 0) {
            if(!worldIn.isClientSide) {
                worldIn.broadcastEntityEvent(catIn, Constants.EntityState.CAT_SMOKE);
                playerIn.sendMessage(new TranslatableComponent("treat.catherder." + this.type.getName() + ".too_young"), catIn.getUUID());
            }

            return InteractionResult.CONSUME;
        }
        else if(!catLevel.canIncrease(this.type)) {
            if(!worldIn.isClientSide) {
                worldIn.broadcastEntityEvent(catIn, Constants.EntityState.CAT_SMOKE);
                playerIn.sendMessage(new TranslatableComponent("treat.catherder." + this.type.getName() + ".low_level"), catIn.getUUID());
            }

            return InteractionResult.CONSUME;
        }
        else if(catLevel.getLevel(this.type) < this.maxLevel) {
            if(!playerIn.level.isClientSide) {
                if(!playerIn.getAbilities().instabuild) {
                    playerIn.getItemInHand(handIn).shrink(1);
                }

                catIn.increaseLevel(this.type);
                catIn.setHealth(catIn.getMaxHealth());
                catIn.setOrderedToSit(true);
                worldIn.broadcastEntityEvent(catIn, Constants.EntityState.CAT_HEARTS);
                playerIn.sendMessage(new TranslatableComponent("treat.catherder." + this.type.getName() + ".level_up"), catIn.getUUID());
            }

            return InteractionResult.SUCCESS;
        }
        else {
            if(!worldIn.isClientSide) {
                worldIn.broadcastEntityEvent(catIn, Constants.EntityState.CAT_SMOKE);
                playerIn.sendMessage(new TranslatableComponent("treat.catherder." + this.type.getName() + ".max_level"), catIn.getUUID());
            }

            return InteractionResult.CONSUME;
        }
    }
}
