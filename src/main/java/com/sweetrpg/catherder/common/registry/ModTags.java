package com.sweetrpg.catherder.common.registry;

import com.sweetrpg.catherder.common.util.Util;
import net.minecraft.tags.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {

    public static TagKey<Item> BEG_ITEMS_TAMED = modItemTag("beg_items_tamed");
    public static TagKey<Item> BEG_ITEMS_UNTAMED = modItemTag("beg_items_untamed");
    public static TagKey<Item> BREEDING_ITEMS = modItemTag("breeding_items");
    public static TagKey<Item> PACK_CAT_BLACKLIST = modItemTag("pack_cat_blacklist");
    public static TagKey<Item> TREATS = modItemTag("treats");
    public static TagKey<Item> TOYS = modItemTag("toys");
    public static TagKey<Item> MEAT = modItemTag("meat");
    public static final TagKey<Block> WILD_CROPS = modBlockTag("wild_crops");
    public static final TagKey<Item> CAT_TREES = modItemTag("cat_trees");
    public static final TagKey<Block> PET_DOORS = modBlockTag("pet_doors");
//    public static Tag.Named<EntityType<?>> PESTS = modEntityTag("pests");

    private static TagKey<Item> modItemTag(String name) {
        return ItemTags.create(Util.getResource(name));
    }

    private static TagKey<Block> modBlockTag(String name) {
        return BlockTags.create(Util.getResource(name));
    }

//    private static TagKey<EntityType<?>> tag(String path) {
//        return EntityTypeTags.bind(Util.getResourcePath(path));
//    }
}
