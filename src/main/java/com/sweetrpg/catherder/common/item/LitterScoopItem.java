package com.sweetrpg.catherder.common.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

public class LitterScoopItem extends Item {

    public LitterScoopItem(Properties properties) {
        super(properties);
    }

//    @Override
//    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
//
//        // check if litterbox maintenance is enabled
//        if(!ConfigHandler.SERVER.LITTERBOX.get()) {
//            return InteractionResult.FAIL;
//        }
//
//        // check if we're using it on the litterbox
//        BlockPos pos = context.getClickedPos();
//        BlockState blockState = context.getLevel().getBlockState(pos);
//        if(blockState.is(ModBlocks.LITTERBOX.get())) {
//            ((LitterboxBlockEntity)context.getLevel().getBlockEntity(pos)).useLitterbox(blockState);
//        }
////        if(stack.is(ModBlocks.LITTERBOX.get().asItem())) {
////
////            if(stack.getItem() instanceof ModBlocks.LITTERBOX.get) {
////litterbox.clean();
////
////                context.getLevel().playSound(null, context.getClickedPos(), SoundEvents.ROOTED_DIRT_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
////            }
////        }
//
//        return InteractionResult.FAIL;
//    }


    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {

        return amount;
    }
}
