package com.sweetrpg.catherder.common.item;

import com.sweetrpg.catherder.api.inferface.AbstractCatEntity;
import com.sweetrpg.catherder.api.inferface.ICatItem;
import com.sweetrpg.catherder.common.lib.Constants;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class ChangeOwnerItem extends Item implements ICatItem {

    public ChangeOwnerItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult processInteract(AbstractCatEntity catIn, Level worldIn, Player playerIn, InteractionHand handIn) {
        if (!catIn.isOwnedBy(playerIn)) {

            if (!worldIn.isClientSide) {
                catIn.tame(playerIn);
                catIn.getNavigation().stop();
                catIn.setTarget((LivingEntity) null);
                catIn.setOrderedToSit(true);
                worldIn.broadcastEntityEvent(catIn, Constants.EntityState.CAT_HEARTS);

                //TODO playerIn.sendMessage(new TranslationTextComponent(""));
            }

            return InteractionResult.SUCCESS;
        }

        //TODO playerIn.sendMessage(new TranslationTextComponent(""));
        return InteractionResult.FAIL;
    }
}
