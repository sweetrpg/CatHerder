package com.sweetrpg.catherder.common.item;

import com.sweetrpg.catherder.api.feature.DataKey;
import com.sweetrpg.catherder.api.inferface.AbstractCatEntity;
import com.sweetrpg.catherder.api.inferface.ICatItem;
import com.sweetrpg.catherder.api.registry.AccessoryInstance;
import com.sweetrpg.catherder.common.block.entity.CatTreeBlockEntity;
import com.sweetrpg.catherder.common.entity.CatEntity;
import com.sweetrpg.catherder.common.lib.Constants;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.List;
import java.util.UUID;

public class CatShearsItem extends Item implements ICatItem {

    private static DataKey<Integer> COOLDOWN = DataKey.make();

    public CatShearsItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        BlockPos pos = context.getClickedPos();
        BlockEntity blockEntity = context.getLevel().getBlockEntity(pos);
        if(blockEntity instanceof CatTreeBlockEntity catTreeEntity) {
            catTreeEntity.setOwner((UUID) null);
            catTreeEntity.setOwner((CatEntity) null);

            return InteractionResult.SUCCESS;
        }

        return super.onItemUseFirst(stack, context);
    }

    @Override
    public InteractionResult processInteract(AbstractCatEntity catIn, Level worldIn, Player playerIn, InteractionHand handIn) {
        if(catIn.isOwnedBy(playerIn)) {
            List<AccessoryInstance> accessories = catIn.getAccessories();
            if(accessories.isEmpty()) {
                if(!catIn.isTame()) {
                    return InteractionResult.CONSUME;
                }

                if(!worldIn.isClientSide) {
                    int cooldownLeft = catIn.getDataOrDefault(COOLDOWN, catIn.tickCount) - catIn.tickCount;

                    if(cooldownLeft <= 0) {
                        worldIn.broadcastEntityEvent(catIn, Constants.EntityState.CAT_SMOKE);
                        catIn.untame();
                    }
                }

                return InteractionResult.SUCCESS;
            }

            if(!worldIn.isClientSide) {
                for(AccessoryInstance inst : accessories) {
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
