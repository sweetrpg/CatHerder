package com.sweetrpg.catherder.common.lib;

import com.sweetrpg.catherder.common.util.Util;
import net.minecraft.resources.ResourceLocation;

public class Resources {

    public static final ResourceLocation GUI_CAT_BOWL = getGui("cat_bowl");
    public static final ResourceLocation GUI_PACK_CAT = getGui("pack_cat");
    public static final ResourceLocation GUI_TREAT_BAG = getGui("treat_bag");
    public static final ResourceLocation GUI_RADAR = getGui("radar");

    public static final ResourceLocation CAT_INVENTORY = getGui("cat_inventory");
    public static final ResourceLocation INVENTORY_BUTTON = getGui("cat_button");
    public static final ResourceLocation SMALL_WIDGETS = getGui("small_widgets");

    // Vanilla cat
    public static final ResourceLocation ENTITY_VANILLA_CAT = Util.getResource(Constants.VANILLA_ID, "textures/entity/cat/all_black.png");
//    public static final ResourceLocation ENTITY_VANILLA_CAT_WILD = Util.getResource(Constants.VANILLA_ID, "textures/entity/cat/cat.png");
//    public static final ResourceLocation ENTITY_VANILLA_CAT_COLLAR = Util.getResource(Constants.VANILLA_ID, "textures/entity/cat/cat_collar.png");

    public static final ResourceLocation COLLAR_DEFAULT = getEntity("cat", "cat_collar");
    public static final ResourceLocation COLLAR_GOLDEN = getEntity("cat", "cat_collar_0");
    public static final ResourceLocation COLLAR_SPOTTED = getEntity("cat", "cat_collar_1");
    public static final ResourceLocation COLLAR_MULTICOLORED = getEntity("cat", "cat_collar_2");
    public static final ResourceLocation CLOTHING_LEATHER_JACKET = getEntity("cat", "cat_leather_jacket");
    public static final ResourceLocation GUARD_SUIT = getEntity("cat", "cat_guard_suit");
    public static final ResourceLocation GLASSES_SUNGLASSES = getEntity("cat", "cat_sunglasses");
    public static final ResourceLocation BOW_TIE = getEntity("cat", "cat_bowtie");
    public static final ResourceLocation CAPE = getEntity("cat", "cat_cape");
    public static final ResourceLocation DYEABLE_CAPE = getEntity("cat", "cat_cape1");
    public static final ResourceLocation RADIO_BAND = getEntity("cat", "cat_radio_collar");
    public static final ResourceLocation TALENT_RESCUE = getEntity("cat/talents", "rescue");
    public static final ResourceLocation TALENT_CHEST = getEntity("cat", "cat_chest");
    public static final ResourceLocation IRON_HELMET = getEntity("cat", "armor/iron_helmet");
    public static final ResourceLocation DIAMOND_HELMET = getEntity("cat", "armor/diamond_helmet");
    public static final ResourceLocation GOLDEN_HELMET = getEntity("cat", "armor/golden_helmet");
    public static final ResourceLocation CHAINMAIL_HELMET = getEntity("cat", "armor/chainmail_helmet");
    public static final ResourceLocation LEATHER_HELMET = getEntity("cat", "armor/leather_helmet");
    public static final ResourceLocation TURTLE_HELMET = getEntity("cat", "armor/turtle_helmet");
    public static final ResourceLocation NETHERITE_HELMET = getEntity("cat", "armor/netherite_helmet");

    public static final ResourceLocation IRON_BOOTS = getEntity("cat", "armor/iron_boots");
    public static final ResourceLocation DIAMOND_BOOTS = getEntity("cat", "armor/diamond_boots");
    public static final ResourceLocation GOLDEN_BOOTS = getEntity("cat", "armor/golden_boots");
    public static final ResourceLocation CHAINMAIL_BOOTS = getEntity("cat", "armor/chainmail_boots");
    public static final ResourceLocation LEATHER_BOOTS = getEntity("cat", "armor/leather_boots");
    public static final ResourceLocation NETHERITE_BOOTS = getEntity("cat", "armor/netherite_boots");

    public static final ResourceLocation IRON_BODY_PIECE = getEntity("cat", "armor/iron_body");
    public static final ResourceLocation DIAMOND_BODY_PIECE = getEntity("cat", "armor/diamond_body");
    public static final ResourceLocation GOLDEN_BODY_PIECE = getEntity("cat", "armor/golden_body");
    public static final ResourceLocation CHAINMAIL_BODY_PIECE = getEntity("cat", "armor/chainmail_body");
    public static final ResourceLocation LEATHER_BODY_PIECE = getEntity("cat", "armor/leather_body");
    public static final ResourceLocation NETHERITE_BODY_PIECE = getEntity("cat", "armor/netherite_body");

    public static ResourceLocation getEntity(String type, String textureFileName) {
        return Util.getResource("textures/entity/" + type + "/" + textureFileName + ".png");
    }

    public static ResourceLocation getGui(String textureFileName) {
        return Util.getResource("textures/gui/" + textureFileName + ".png");
    }
}
