package com.sweetrpg.catherder.common.registry;

import com.sweetrpg.catherder.common.util.Util;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {

    public static Tag.Named<Item> BEG_ITEMS_TAMED = modItemTag("beg_items_tamed");
    public static Tag.Named<Item> BEG_ITEMS_UNTAMED = modItemTag("beg_items_untamed");
    public static Tag.Named<Item> BREEDING_ITEMS = modItemTag("breeding_items");
    public static Tag.Named<Item> PACK_CAT_BLACKLIST = modItemTag("pack_cat_blacklist");
    public static Tag.Named<Item> TREATS = modItemTag("treats");
    public static final Tag.Named<Block> WILD_CROPS = modBlockTag("wild_crops");

    private static Tag.Named<Item> modItemTag(String path) {
        return ItemTags.bind(Util.getResourcePath(path));
    }

    private static Tag.Named<Block> modBlockTag(String path) {
        return BlockTags.bind(Util.getResourcePath(path));
    }

    private static Tag.Named<EntityType<?>> modEntityTag(String path) {
        return EntityTypeTags.bind(Util.getResourcePath(path));
    }
}
