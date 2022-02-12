package kittytalents;

import kittytalents.common.util.DogBedUtil;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class KittyItemGroups {

    public static final CreativeModeTab GENERAL = new CustomItemGroup("kittytalents", () -> new ItemStack(KittyItems.TRAINING_TREAT.get()));
    public static final CreativeModeTab CAT_BED = new CustomItemGroup("kittytalents.dogbed", DogBedUtil::createRandomBed);

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
