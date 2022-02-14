package com.sweetrpg.catherder.common.item;

import com.sweetrpg.catherder.api.feature.DataKey;
import com.sweetrpg.catherder.api.registry.AccessoryInstance;
import com.sweetrpg.catherder.api.inferface.AbstractCatEntity;
import com.sweetrpg.catherder.api.inferface.ICatItem;
import com.sweetrpg.catherder.common.lib.Constants;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class CatShearsItem extends Item implements ICatItem {

    private static DataKey<Integer> COOLDOWN = DataKey.make();

    public CatShearsItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult processInteract(AbstractCatEntity catIn, Level worldIn, Player playerIn, InteractionHand handIn) {
        if (catIn.isOwnedBy(playerIn)) {
            List<AccessoryInstance> accessories = catIn.getAccessories();
            if (accessories.isEmpty()) {
                if (!catIn.isTame()) {
                    return InteractionResult.CONSUME;
                }

                if (!worldIn.isClientSide) {
                    int cooldownLeft = catIn.getDataOrDefault(COOLDOWN, catIn.tickCount) - catIn.tickCount;

                    if (cooldownLeft <= 0) {
                        worldIn.broadcastEntityEvent(catIn, Constants.EntityState.CAT_SMOKE);
                        catIn.untame();
                    }
                }

                return InteractionResult.SUCCESS;
            }

            if (!worldIn.isClientSide) {
                for (AccessoryInstance inst : accessories) {
                    ItemStack returnItem = inst.getReturnItem();
                    catIn.spawnAtLocation(returnItem, 1);
                }

                catIn.removeAccessories();
                catIn.setData(COOLDOWN, catIn.tickCount + 40);

                return InteractionResult.SUCCESS;
            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
    }

}
