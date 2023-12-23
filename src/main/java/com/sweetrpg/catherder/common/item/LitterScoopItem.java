package com.sweetrpg.catherder.common.item;

import com.sweetrpg.catherder.api.feature.CatLevel;
import com.sweetrpg.catherder.api.inferface.AbstractCatEntity;
import com.sweetrpg.catherder.api.inferface.ICatItem;
import com.sweetrpg.catherder.common.block.LitterboxBlock;
import com.sweetrpg.catherder.common.config.ConfigHandler;
import com.sweetrpg.catherder.common.lib.Constants;
import com.sweetrpg.catherder.common.registry.ModBlocks;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class LitterScoopItem extends Item {


    public LitterScoopItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {

        // check if litterbox maintenance is enabled
        if(!ConfigHandler.SERVER.LITTERBOX.get()) {
            return InteractionResult.FAIL;
        }

        // check if we're using it on the litterbox
        if(stack.is(ModBlocks.LITTERBOX.get().asItem())) {

            if(stack.getItem() instanceof LitterboxBlock litterbox) {
litterbox.clean();

                context.getLevel().playSound(null, context.getClickedPos(), SoundEvents.ROOTED_DIRT_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
        }

        return InteractionResult.FAIL;
    }
}
