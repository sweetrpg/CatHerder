//package com.sweetrpg.catherder.common.event;
//
//import com.google.common.collect.ImmutableMap;
//import com.sweetrpg.catherder.common.registry.ModAccessories;
//import com.sweetrpg.catherder.api.registry.Accessory;
//import com.sweetrpg.catherder.api.registry.AccessoryInstance;
//import com.sweetrpg.catherder.api.inferface.AbstractCatEntity;
//import com.sweetrpg.catherder.api.inferface.ICatItem;
//import net.minecraft.world.InteractionHand;
//import net.minecraft.world.InteractionResult;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.Items;
//import net.minecraft.world.level.Level;
//import net.minecraftforge.registries.RegistryObject;
//import net.minecraftforge.registries.IRegistryDelegate;
//
//import java.util.Map;
//
//public class HelmetInteractHandler implements ICatItem {
//
//    private static final Map<IRegistryDelegate<? extends Item>, RegistryObject<? extends Accessory>> MAPPING = new ImmutableMap.Builder<IRegistryDelegate<? extends Item>, RegistryObject<? extends Accessory>>()
////        .put(Items.IRON_HELMET.delegate, CatAccessories.IRON_HELMET)
////        .put(Items.DIAMOND_HELMET.delegate, CatAccessories.DIAMOND_HELMET)
////        .put(Items.GOLDEN_HELMET.delegate, CatAccessories.GOLDEN_HELMET)
////        .put(Items.CHAINMAIL_HELMET.delegate, CatAccessories.CHAINMAIL_HELMET)
////        .put(Items.TURTLE_HELMET.delegate, CatAccessories.TURTLE_HELMET)
////        .put(Items.NETHERITE_HELMET.delegate, CatAccessories.NETHERITE_HELMET)
////        .put(Items.IRON_BOOTS.delegate, CatAccessories.IRON_BOOTS)
////        .put(Items.DIAMOND_BOOTS.delegate, CatAccessories.DIAMOND_BOOTS)
////        .put(Items.GOLDEN_BOOTS.delegate, CatAccessories.GOLDEN_BOOTS)
////        .put(Items.CHAINMAIL_BOOTS.delegate, CatAccessories.CHAINMAIL_BOOTS)
////        .put(Items.NETHERITE_BOOTS.delegate, CatAccessories.NETHERITE_BOOTS)
////        .put(Items.IRON_CHESTPLATE.delegate, CatAccessories.IRON_BODY_PIECE)
////        .put(Items.DIAMOND_CHESTPLATE.delegate, CatAccessories.DIAMOND_BODY_PIECE)
////        .put(Items.GOLDEN_CHESTPLATE.delegate, CatAccessories.GOLDEN_BODY_PIECE)
////        .put(Items.CHAINMAIL_CHESTPLATE.delegate, CatAccessories.CHAINMAIL_BODY_PIECE)
////        .put(Items.NETHERITE_CHESTPLATE.delegate, CatAccessories.NETHERITE_BODY_PIECE)
////        .put(Items.LEATHER_HELMET.delegate, CatAccessories.LEATHER_HELMET)
////        .put(Items.LEATHER_BOOTS.delegate, ModAccessories.LEATHER_BOOTS)
////        .put(Items.LEATHER_CHESTPLATE.delegate, CatAccessories.LEATHER_BODY_PIECE)
//       .build();
//
//    @Override
//    public InteractionResult processInteract(AbstractCatEntity catIn, Level worldIn, Player playerIn, InteractionHand handIn) {
//        if (catIn.isTame() && catIn.canInteract(playerIn)) {
//            ItemStack stack = playerIn.getItemInHand(handIn);
//
//            if (!stack.isEmpty()) {
//                RegistryObject<? extends Accessory> associatedAccessory = MAPPING.get(stack.getItem().delegate);
//
//                if (associatedAccessory != null) {
//                    AccessoryInstance inst = associatedAccessory.get().createFromStack(stack.copy().split(1));
//
//                    if (catIn.addAccessory(inst)) {
//                        catIn.consumeItemFromStack(playerIn, stack);
//                        return InteractionResult.SUCCESS;
//                    }
//                }
//            }
//        }
//
//        return InteractionResult.PASS;
//    }
//
//}
