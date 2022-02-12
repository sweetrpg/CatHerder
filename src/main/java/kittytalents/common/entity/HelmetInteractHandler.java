package kittytalents.common.entity;

import com.google.common.collect.ImmutableMap;
import kittytalents.KittyAccessories;
import kittytalents.api.registry.Accessory;
import kittytalents.api.registry.AccessoryInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.IRegistryDelegate;

import java.util.Map;

public class HelmetInteractHandler implements kittytalents.api.inferface.ICatItem {

    private static final Map<IRegistryDelegate<? extends Item>, RegistryObject<? extends Accessory>> MAPPING = new ImmutableMap.Builder<IRegistryDelegate<? extends Item>, RegistryObject<? extends Accessory>>()
        .put(Items.IRON_HELMET.delegate,      KittyAccessories.IRON_HELMET)
        .put(Items.DIAMOND_HELMET.delegate,   KittyAccessories.DIAMOND_HELMET)
        .put(Items.GOLDEN_HELMET.delegate,    KittyAccessories.GOLDEN_HELMET)
        .put(Items.CHAINMAIL_HELMET.delegate, KittyAccessories.CHAINMAIL_HELMET)
        .put(Items.TURTLE_HELMET.delegate,    KittyAccessories.TURTLE_HELMET)
        .put(Items.NETHERITE_HELMET.delegate, KittyAccessories.NETHERITE_HELMET)
        .put(Items.IRON_BOOTS.delegate,     KittyAccessories.IRON_BOOTS)
        .put(Items.DIAMOND_BOOTS.delegate,     KittyAccessories.DIAMOND_BOOTS)
        .put(Items.GOLDEN_BOOTS.delegate,     KittyAccessories.GOLDEN_BOOTS)
        .put(Items.CHAINMAIL_BOOTS.delegate,     KittyAccessories.CHAINMAIL_BOOTS)
        .put(Items.NETHERITE_BOOTS.delegate,     KittyAccessories.NETHERITE_BOOTS)
        .put(Items.IRON_CHESTPLATE.delegate,  KittyAccessories.IRON_BODY_PIECE)
        .put(Items.DIAMOND_CHESTPLATE.delegate, KittyAccessories.DIAMOND_BODY_PIECE)
        .put(Items.GOLDEN_CHESTPLATE.delegate, KittyAccessories.GOLDEN_BODY_PIECE)
        .put(Items.CHAINMAIL_CHESTPLATE.delegate, KittyAccessories.CHAINMAIL_BODY_PIECE)
        .put(Items.NETHERITE_CHESTPLATE.delegate, KittyAccessories.NETHERITE_BODY_PIECE)
        .put(Items.LEATHER_HELMET.delegate,   KittyAccessories.LEATHER_HELMET)
        .put(Items.LEATHER_BOOTS.delegate,   KittyAccessories.LEATHER_BOOTS)
        .put(Items.LEATHER_CHESTPLATE.delegate,   KittyAccessories.LEATHER_BODY_PIECE)
       .build();

    @Override
    public InteractionResult processInteract(kittytalents.api.inferface.AbstractCatEntity dogIn, Level worldIn, Player playerIn, InteractionHand handIn) {
        if (dogIn.isTame() && dogIn.canInteract(playerIn)) {
            ItemStack stack = playerIn.getItemInHand(handIn);

            if (!stack.isEmpty()) {
                RegistryObject<? extends Accessory> associatedAccessory = MAPPING.get(stack.getItem().delegate);

                if (associatedAccessory != null) {
                    AccessoryInstance inst = associatedAccessory.get().createFromStack(stack.copy().split(1));

                    if (dogIn.addAccessory(inst)) {
                        dogIn.consumeItemFromStack(playerIn, stack);
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        }

        return InteractionResult.PASS;
    }

}
