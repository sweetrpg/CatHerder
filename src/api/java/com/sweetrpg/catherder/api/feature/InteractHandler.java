package com.sweetrpg.catherder.api.feature;

import com.sweetrpg.catherder.api.inferface.AbstractCatEntity;
import com.sweetrpg.catherder.api.inferface.ICatItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InteractHandler {

    private static final List<ICatItem> HANDLERS = Collections.synchronizedList(new ArrayList<>(4));

    public static void registerHandler(ICatItem handler) {
        HANDLERS.add(handler);
    }

    public static InteractionResult getMatch(@Nullable AbstractCatEntity catIn, ItemStack stackIn, Player playerIn, InteractionHand handIn) {
        if(stackIn.getItem() instanceof ICatItem) {
            ICatItem item = (ICatItem) stackIn.getItem();
            InteractionResult result = item.processInteract(catIn, catIn.level(), playerIn, handIn);
            if(result != InteractionResult.PASS) {
                return result;
            }
        }

        for(ICatItem handler : HANDLERS) {
            InteractionResult result = handler.processInteract(catIn, catIn.level(), playerIn, handIn);
            if(result != InteractionResult.PASS) {
                return result;
            }
        }

        return InteractionResult.PASS;
    }
}
