package kittytalents.common.item;

import kittytalents.common.lib.Constants;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class ChangeOwnerItem extends Item implements kittytalents.api.inferface.ICatItem {

    public ChangeOwnerItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult processInteract(kittytalents.api.inferface.AbstractCatEntity dogIn, Level worldIn, Player playerIn, InteractionHand handIn) {
        if (!dogIn.isOwnedBy(playerIn)) {

            if (!worldIn.isClientSide) {
                dogIn.tame(playerIn);
                dogIn.getNavigation().stop();
                dogIn.setTarget((LivingEntity) null);
                dogIn.setOrderedToSit(true);
                worldIn.broadcastEntityEvent(dogIn, Constants.EntityState.WOLF_HEARTS);

                //TODO playerIn.sendMessage(new TranslationTextComponent(""));
            }

            return InteractionResult.SUCCESS;
        }

        //TODO playerIn.sendMessage(new TranslationTextComponent(""));
        return InteractionResult.FAIL;
    }
}
