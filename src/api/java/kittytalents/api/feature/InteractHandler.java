package kittytalents.api.feature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;

public class InteractHandler {

    private static final List<kittytalents.api.inferface.ICatItem> HANDLERS = Collections.synchronizedList(new ArrayList<>(4));

    public static void registerHandler(kittytalents.api.inferface.ICatItem handler) {
        HANDLERS.add(handler);
    }

    public static InteractionResult getMatch(@Nullable kittytalents.api.inferface.AbstractCatEntity catIn, ItemStack stackIn, Player playerIn, InteractionHand handIn) {
        if (stackIn.getItem() instanceof kittytalents.api.inferface.ICatItem) {
            kittytalents.api.inferface.ICatItem item = (kittytalents.api.inferface.ICatItem) stackIn.getItem();
            InteractionResult result = item.processInteract(catIn, catIn.level, playerIn, handIn);
            if (result != InteractionResult.PASS) {
                return result;
            }
        }

        for (kittytalents.api.inferface.ICatItem handler : HANDLERS) {
            InteractionResult result = handler.processInteract(catIn, catIn.level, playerIn, handIn);
            if (result != InteractionResult.PASS) {
                return result;
            }
        }

        return InteractionResult.PASS;
    }
}
