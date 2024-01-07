package com.sweetrpg.catherder.common.registry;

import com.sweetrpg.catherder.common.item.IDyeableArmorItem;
import com.sweetrpg.catherder.common.util.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;
import net.minecraftforge.event.CreativeModeTabEvent;

public class ModItemGroups {

    public static CreativeModeTab GENERAL;
// TODO   public static CreativeModeTab CAT_TREE;
// TODO   public static CreativeModeTab PET_DOOR;

//    public static final CreativeModeTab GENERAL = new CustomItemGroup("catherder", () -> new ItemStack(ModItems.TRAINING_TREAT.get()));
//    public static final CreativeModeTab CAT_TREE = new CustomItemGroup("catherder.cattree", CattreeUtil::createRandomBed);

    public static void creativeModeTabRegisterEvent(final CreativeModeTabEvent.Register event) {
        GENERAL = event.registerCreativeModeTab(Util.getResource("main"), (builder) -> builder.title(Component.translatable("itemGroup.catherder")).icon(() -> new ItemStack(ModItems.TRAINING_TREAT.get())));
    }

    public static void creativeModeTabBuildEvent(final CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == GENERAL) {
            // Adds items in the order of definition in DoggyItems. This matches order
            // from previous version of MC prior to 1.19.3.
            for (var item : ModItems.ITEMS.getEntries()) {
                if (item.get() instanceof IDyeableArmorItem dyeableItem) {
                    ItemStack stack = new ItemStack(item.get());

                    dyeableItem.setColor(stack, dyeableItem.getDefaultColor(stack));
                    event.accept(stack);
                    continue;
                }

                event.accept(item);
            }
//
//            // Add the block items, missing the dog bed out as that has it's own tab.
//            for (var blockItem : ModBlocks.ITEMS.getEntries()) {
//                if (blockItem.getId().getPath() == "dog_bed") {
//                    continue;
//                }
//
//                event.accept(blockItem);
//            }
        }

//        if (event.getTab() == DOG_BED) {
//            for (IBeddingMaterial beddingId : DoggyTalentsAPI.BEDDING_MATERIAL.get().getValues()) {
//                for (ICasingMaterial casingId : DoggyTalentsAPI.CASING_MATERIAL.get().getValues()) {
//                    event.accept(DogBedUtil.createItemStack(casingId, beddingId), CreativeModeTab.TabVisibility.PARENT_TAB_ONLY);
//                }
//            }
//        }
    }
//    public static class CustomItemGroup extends CreativeModeTab {
//
//        private Supplier<ItemStack> icon;
//
//        public CustomItemGroup(String label, Supplier<ItemStack> iconIn) {
//            super(label);
//            this.icon = iconIn;
//        }
//
//        @Override
//        public ItemStack makeIcon() {
//            return this.icon.get();
//        }
//    }
}
