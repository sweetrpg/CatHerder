package com.sweetrpg.catherder.common.item;

import com.sweetrpg.catherder.api.feature.DataKey;
import com.sweetrpg.catherder.api.inferface.AbstractCatEntity;
import com.sweetrpg.catherder.api.inferface.ICatItem;
import com.sweetrpg.catherder.common.entity.CatEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class CatnipItem extends Item implements ICatItem {

    private static final DataKey<Integer> COOLDOWN = DataKey.make();

    public CatnipItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult processInteract(AbstractCatEntity catIn, Level worldIn, Player playerIn, InteractionHand handIn) {
        if(catIn.canInteract(playerIn)) {
            if(!worldIn.isClientSide) {
                if(catIn instanceof CatEntity cat) {
                    InteractionResult result = cat.consumeCatnip(playerIn, handIn);
                    // check if cat took the catnip, otherwise, don't consume it
                    if(result == InteractionResult.SUCCESS) {
                        catIn.consumeItemFromStack(playerIn, playerIn.getItemInHand(handIn));
                    }
                    return result;
                }

//                int cooldownLeft = catIn.getDataOrDefault(COOLDOWN, catIn.tickCount) - catIn.tickCount;
//
//                if(cooldownLeft <= 0) {
//                    worldIn.broadcastEntityEvent(catIn, Constants.EntityState.CAT_SMOKE);
//                }
//                else {
//                    catIn.setSpeed(2);
//                    catIn.setSprinting(true);
//                    catIn.setData(COOLDOWN, catIn.tickCount + 40);
//
//                    catIn.consumeItemFromStack(playerIn, playerIn.getItemInHand(handIn));
//
//                    catIn.level.broadcastEntityEvent(catIn, Constants.EntityState.CAT_HEARTS);
//                }
            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
    }
}
