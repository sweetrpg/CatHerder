package kittytalents.common.item;

import kittytalents.api.feature.DataKey;
import kittytalents.api.registry.AccessoryInstance;
import kittytalents.common.lib.Constants;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class CatShearsItem extends Item implements kittytalents.api.inferface.ICatItem {

    private static DataKey<Integer> COOLDOWN = DataKey.make();

    public CatShearsItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult processInteract(kittytalents.api.inferface.AbstractCatEntity dogIn, Level worldIn, Player playerIn, InteractionHand handIn) {
        if (dogIn.isOwnedBy(playerIn)) {
            List<AccessoryInstance> accessories = dogIn.getAccessories();
            if (accessories.isEmpty()) {
                if (!dogIn.isTame()) {
                    return InteractionResult.CONSUME;
                }

                if (!worldIn.isClientSide) {
                    int cooldownLeft = dogIn.getDataOrDefault(COOLDOWN, dogIn.tickCount) - dogIn.tickCount;

                    if (cooldownLeft <= 0) {
                        worldIn.broadcastEntityEvent(dogIn, Constants.EntityState.WOLF_SMOKE);
                        dogIn.untame();
                    }
                }

                return InteractionResult.SUCCESS;
            }

            if (!worldIn.isClientSide) {
                for (AccessoryInstance inst : accessories) {
                    ItemStack returnItem = inst.getReturnItem();
                    dogIn.spawnAtLocation(returnItem, 1);
                }

                dogIn.removeAccessories();
                dogIn.setData(COOLDOWN, dogIn.tickCount + 40);

                return InteractionResult.SUCCESS;
            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
    }

}
