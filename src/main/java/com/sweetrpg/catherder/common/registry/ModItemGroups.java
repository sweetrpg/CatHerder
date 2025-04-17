package com.sweetrpg.catherder.common.registry;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class ModItemGroups {

    public static final CreativeModeTab GENERAL = new CustomItemGroup("catherder", () -> new ItemStack(ModItems.TRAINING_TREAT.get()));
//    public static final CreativeModeTab CAT_TREE = new CustomItemGroup("catherder.cattree", CattreeUtil::createRandomBed);

    public static class CustomItemGroup extends CreativeModeTab {

        private Supplier<ItemStack> icon;

        public CustomItemGroup(String label, Supplier<ItemStack> iconIn) {
            super(label);
            this.icon = iconIn;
        }

        @Override
        public ItemStack makeIcon() {
            return this.icon.get();
        }
    }
}
