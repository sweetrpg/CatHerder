package kittytalents.common.item;

import kittytalents.api.registry.Accessory;
import kittytalents.api.registry.AccessoryInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class AccessoryItem extends Item implements kittytalents.api.inferface.ICatItem {

    public Supplier<? extends Accessory> type;

    public AccessoryItem(Supplier<? extends Accessory> type, Properties properties) {
        super(properties);
        this.type = type;
    }

    @Override
    public InteractionResult processInteract(kittytalents.api.inferface.AbstractCatEntity catIn, Level worldIn, Player playerIn, InteractionHand handIn) {
        if (catIn.canInteract(playerIn) && catIn.addAccessory(this.createInstance(catIn, playerIn.getItemInHand(handIn), playerIn))) {
            catIn.consumeItemFromStack(playerIn, playerIn.getItemInHand(handIn));
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    public AccessoryInstance createInstance(kittytalents.api.inferface.AbstractCatEntity catIn, ItemStack stack, Player playerIn) {
        return this.type.get().getDefault();
    }
}
