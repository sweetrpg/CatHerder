package kittytalents;

import kittytalents.common.util.Util;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;

public class KittyTags {

    public static Tag.Named<Item> BEG_ITEMS_TAMED = tag("beg_items_tamed");
    public static Tag.Named<Item> BEG_ITEMS_UNTAMED = tag("beg_items_untamed");
    public static Tag.Named<Item> BREEDING_ITEMS = tag("breeding_items");
    public static Tag.Named<Item> PACK_KITTY_BLACKLIST = tag("pack_kitty_blacklist");
    public static Tag.Named<Item> TREATS = tag("treats");

    private static Tag.Named<Item> tag(String name) {
        return ItemTags.bind(Util.getResourcePath(name));
    }
}
