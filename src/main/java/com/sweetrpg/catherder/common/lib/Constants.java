package com.sweetrpg.catherder.common.lib;

import com.sweetrpg.catherder.api.CatHerderAPI;
import com.sweetrpg.catherder.common.util.Util;
import net.minecraft.resources.ResourceLocation;

public class Constants {

    public static final String VANILLA_ID = "minecraft";
    public static final String VANILLA_NAME = "Minecraft";

    // Network
    public static final ResourceLocation CHANNEL_NAME = Util.getResource("channel");
    public static final String PROTOCOL_VERSION = Integer.toString(3);

    // Storage
    public static final String STORAGE_CAT_RESPAWN = CatHerderAPI.MOD_ID + "DeadCats";
    public static final String STORAGE_CAT_LOCATION = "cat_locations";

    // Language
    public static final String LOCALE_EN_US = "en_us";
    public static final String LOCALE_EN_GB = "en_gb";
    public static final String LOCALE_DE_DE = "de_de";
    public static final String LOCALE_KO_KR = "ko_kr";
    public static final String LOCALE_RU_RU = "ru_ru";
    public static final String LOCALE_VI_VN = "vi_vn";
    public static final String LOCALE_ZH_CN = "zh_cn";
    public static final String LOCALE_ZH_TW = "zh_tw";

    // Translation keys
    public static final String TRANSLATION_KEY_BLOCK_CAT_TREE = "block.catherder.cat_tree";
    public static final String TRANSLATION_KEY_BLOCK_CAT_TREE_SET_OWNER_HELP = "block.catherder.cat_tree.set_owner_help";
    public static final String TRANSLATION_KEY_BLOCK_CAT_TREE_OWNER = "block.catherder.cat_tree.owner";
    public static final String TRANSLATION_KEY_BLOCK_CAT_BOWL = "block.catherder.cat_bowl";
    public static final String TRANSLATION_KEY_BLOCK_CAT_BATH = "block.catherder.cat_bath";
    public static final String TRANSLATION_KEY_BLOCK_LITTERBOX = "block.catherder.litter_box";
    public static final String TRANSLATION_KEY_BLOCK_WILD_CATNIP = "block.catherder.wild_catnip";
    public static final String TRANSLATION_KEY_BLOCK_CATNIP = "block.catherder.catnip";
    public static final String TRANSLATION_KEY_BLOCK_CARDBOARD_BOX = "block.catherder.cardboard_box";
    public static final String TRANSLATION_KEY_BLOCK_CHEESE_WHEEL = "block.catherder.cheese_wheel";
    public static final String TRANSLATION_KEY_BLOCK_CHEESE_WHEEL_USE = "block.catherder.cheese_wheel.use_container";
    public static final String TRANSLATION_KEY_BLOCK_MOUSE_TRAP = "block.catherder.mouse_trap";
    public static final String TRANSLATION_KEY_BLOCK_PET_DOOR = "block.catherder.pet_door";
    public static final String TRANSLATION_KEY_ITEM_CAT_GUT = "item.catherder.cat_gut";
    public static final String TRANSLATION_KEY_ITEM_CHEESE_WEDGE = "item.catherder.cheese_wedge";
    public static final String TRANSLATION_KEY_ITEM_MOUSE_TRAP = "item.catherder.mouse_trap";
    public static final String TRANSLATION_KEY_ITEM_TRAINING_TREAT = "item.catherder.training_treat";
    public static final String TRANSLATION_KEY_ITEM_SUPER_TREAT = "item.catherder.super_treat";
    public static final String TRANSLATION_KEY_ITEM_MASTER_TREAT = "item.catherder.master_treat";
    public static final String TRANSLATION_KEY_ITEM_WILD_TREAT = "item.catherder.wild_treat";
    public static final String TRANSLATION_KEY_ITEM_BREEDING_TREAT = "item.catherder.breeding_treat";
    public static final String TRANSLATION_KEY_ITEM_CAT_CHARM = "item.catherder.cat_charm";
    public static final String TRANSLATION_KEY_ITEM_COLLAR_SHEARS = "item.catherder.collar_shears";
    public static final String TRANSLATION_KEY_ITEM_COMMAND_EMBLEM = "item.catherder.command_emblem";
    public static final String TRANSLATION_KEY_ITEM_RADIO_COLLAR = "item.catherder.radio_collar";
    public static final String TRANSLATION_KEY_ITEM_WOOL_COLLAR = "item.catherder.wool_collar";
    public static final String TRANSLATION_KEY_ITEM_WOOL_COLLAR_TOOLTIP = "item.catherder.wool_collar.tooltip";
    public static final String TRANSLATION_KEY_ITEM_CREATIVE_COLLAR = "item.catherder.creative_collar";
    public static final String TRANSLATION_KEY_ITEM_CREATIVE_COLLAR_TOOLTIP = "item.catherder.creative_collar.tooltip";
    public static final String TRANSLATION_KEY_ITEM_SPOTTED_COLLAR = "item.catherder.spotted_collar";
    public static final String TRANSLATION_KEY_ITEM_MULTICOLORED_COLLAR = "item.catherder.multicolored_collar";
    public static final String TRANSLATION_KEY_ITEM_RADAR = "item.catherder.radar";
    public static final String TRANSLATION_KEY_ITEM_CREATIVE_RADAR = "item.catherder.creative_radar";
    public static final String TRANSLATION_KEY_ITEM_CREATIVE_RADAR_TOOLTIP = "item.catherder.creative_radar.tooltip";
    public static final String TRANSLATION_KEY_ITEM_TREAT_BAG = "item.catherder.treat_bag";
    public static final String TRANSLATION_KEY_ITEM_CAT_TOY = "item.catherder.cat_toy";
    public static final String TRANSLATION_KEY_ITEM_CAPE = "item.catherder.cape";
    public static final String TRANSLATION_KEY_ITEM_SUNGLASSES = "item.catherder.sunglasses";
    public static final String TRANSLATION_KEY_ITEM_CAPE_COLORED = "item.catherder.cape_coloured";
    public static final String TRANSLATION_KEY_ITEM_CAPE_COLORED_TOOLTIP = "item.catherder.cape_coloured.tooltip";
    public static final String TRANSLATION_KEY_ITEM_LEATHER_JACKET = "item.catherder.leather_jacket";
    public static final String TRANSLATION_KEY_ITEM_LITTER_SCOOP = "item.catherder.litter_scoop";
    public static final String TRANSLATION_KEY_ITEM_SMALL_CATSIZER = "item.catherder.small_catsizer";
    public static final String TRANSLATION_KEY_ITEM_BIG_CATSIZER = "item.catherder.big_catsizer";
    public static final String TRANSLATION_KEY_ITEM_OWNER_CHANGE = "item.catherder.owner_change";
    public static final String TRANSLATION_KEY_ITEM_OWNER_CHANGE_TOOLTIP = "item.catherder.owner_change.tooltip";
    public static final String TRANSLATION_KEY_ITEM_GUARD_SUIT = "item.catherder.guard_suit";
    public static final String TRANSLATION_KEY_ITEM_CATNIP_SEEDS = "item.catherder.catnip_seeds";
    public static final String TRANSLATION_KEY_ITEM_WILD_CATNIP = "item.catherder.wild_catnip";
    public static final String TRANSLATION_KEY_ITEM_CATNIP = "item.catherder.catnip";
    public static final String TRANSLATION_KEY_ITEM_YARN = "item.catherder.yarn";
    public static final String TRANSLATION_KEY_ITEM_CARDBOARD = "item.catherder.cardboard";
    public static final String TRANSLATION_KEY_ITEM_TREATBAG_CONTENTS = "item.catherder.treat_bag.contents";
    public static final String TRANSLATION_KEY_CONTAINER_PACK_CAT = "container.catherder.pack_cat";
    public static final String TRANSLATION_KEY_CONTAINER_CAT_BOWL = "container.catherder.cat_bowl";
    public static final String TRANSLATION_KEY_CONTAINER_LITTERBOX = "container.catherder.litter_box";
    public static final String TRANSLATION_KEY_CONTAINER_TREAT_BAG = "container.catherder.treat_bag";
    public static final String TRANSLATION_KEY_CONTAINER_INVENTORIES_LINK = "container.catherder.cat_inventories.link";
    public static final String TRANSLATION_KEY_CONTAINER_INVENTORIES = "container.catherder.cat_inventories";
    public static final String TRANSLATION_KEY_ITEMGROUP_MAIN = "itemGroup.catherder";
    public static final String TRANSLATION_KEY_ITEMGROUP_CATTREE = "itemGroup.catherder.cattree";
    public static final String TRANSLATION_KEY_WILD_TREAT_LOW_LEVEL = "treat.catherder.wild_treat.low_level";
    public static final String TRANSLATION_KEY_WILD_TREAT_LEVEL_UP = "treat.catherder.wild_treat.level_up";
    public static final String TRANSLATION_KEY_WILD_TREAT_TOO_YOUNG = "treat.catherder.wild_treat.too_young";
    public static final String TRANSLATION_KEY_WILD_TREAT_MAX_LEVEL = "treat.catherder.wild_treat.max_level";
    public static final String TRANSLATION_KEY_NORMAL_TREAT_LEVEL_UP = "treat.catherder.normal_treat.level_up";
    public static final String TRANSLATION_KEY_NORMAL_TREAT_TOO_YOUNG = "treat.catherder.normal_treat.too_young";
    public static final String TRANSLATION_KEY_NORMAL_TREAT_MAX_LEVEL = "treat.catherder.normal_treat.max_level";
    public static final String TRANSLATION_KEY_TALENT_TOMCAT_NAME = "talent.catherder.tomcat";
    public static final String TRANSLATION_KEY_TALENT_TOMCAT_DESCRIPTION = "talent.catherder.tomcat.description";
    public static final String TRANSLATION_KEY_TALENT_POUNCE_NAME = "talent.catherder.pounce";
    public static final String TRANSLATION_KEY_TALENT_POUNCE_DESCRIPTION = "talent.catherder.pounce.description";
    public static final String TRANSLATION_KEY_TALENT_SUPERJUMP_NAME = "talent.catherder.super_jump";
    public static final String TRANSLATION_KEY_TALENT_SUPERJUMP_DESCRIPTION = "talent.catherder.super_jump.description";
    public static final String TRANSLATION_KEY_TALENT_CATLIKEREFLEXES_NAME = "talent.catherder.catlike_reflexes";
    public static final String TRANSLATION_KEY_TALENT_CATLIKEREFLEXES_DESCRIPTION = "talent.catherder.catlike_reflexes.description";
    public static final String TRANSLATION_KEY_TALENT_BIRD_CATCHER_NAME = "talent.catherder.bird_catcher";
    public static final String TRANSLATION_KEY_TALENT_BIRD_CATCHER_DESCRIPTION = "talent.catherder.bird_catcher.description";
    public static final String TRANSLATION_KEY_TALENT_HELLBEAST_NAME = "talent.catherder.hell_beast";
    public static final String TRANSLATION_KEY_TALENT_HELLBEAST_DESCRIPTION = "talent.catherder.hell_beast.description";
    public static final String TRANSLATION_KEY_TALENT_MOUNT_NAME = "talent.catherder.mount";
    public static final String TRANSLATION_KEY_TALENT_MOUNT_DESCRIPTION = "talent.catherder.mount.description";
    public static final String TRANSLATION_KEY_TALENT_MOUNT_EXHAUSTED = "talent.catherder.mount.exhausted";
    public static final String TRANSLATION_KEY_TALENT_PACKCAT_NAME = "talent.catherder.pack_cat";
    public static final String TRANSLATION_KEY_TALENT_PACKCAT_DESCRIPTION = "talent.catherder.pack_cat.description";
    public static final String TRANSLATION_KEY_TALENT_QUICKHEALER_NAME = "talent.catherder.quick_healer";
    public static final String TRANSLATION_KEY_TALENT_QUICKHEALER_DESCRIPTION = "talent.catherder.quick_healer.description";
    public static final String TRANSLATION_KEY_TALENT_CREEPERSWEEPER_NAME = "talent.catherder.creeper_sweeper";
    public static final String TRANSLATION_KEY_TALENT_CREEPERSWEEPER_DESCRIPTION = "talent.catherder.creeper_sweeper.description";
    public static final String TRANSLATION_KEY_TALENT_CHEETAHSPEED_NAME = "talent.catherder.cheetah_speed";
    public static final String TRANSLATION_KEY_TALENT_CHEETAHSPEED_DESCRIPTION = "talent.catherder.cheetah_speed.description";
    public static final String TRANSLATION_KEY_TALENT_FISHERCAT_NAME = "talent.catherder.fisher_cat";
    public static final String TRANSLATION_KEY_TALENT_FISHERCAT_DESCRIPTION = "talent.catherder.fisher_cat.description";
    public static final String TRANSLATION_KEY_TALENT_HAPPYEATER_NAME = "talent.catherder.happy_eater";
    public static final String TRANSLATION_KEY_TALENT_HAPPYEATER_DESCRIPTION = "talent.catherder.happy_eater.description";
    public static final String TRANSLATION_KEY_TALENT_BEDFINDER_NAME = "talent.catherder.bed_finder";
    public static final String TRANSLATION_KEY_TALENT_BEDFINDER_DESCRIPTION = "talent.catherder.bed_finder.description";
    public static final String TRANSLATION_KEY_TALENT_PESTFIGHTER_NAME = "talent.catherder.pest_fighter";
    public static final String TRANSLATION_KEY_TALENT_PESTFIGHTER_DESCRIPTION = "talent.catherder.pest_fighter.description";
    public static final String TRANSLATION_KEY_TALENT_POISONFANG_NAME = "talent.catherder.poison_fang";
    public static final String TRANSLATION_KEY_TALENT_POISONFANG_DESCRIPTION = "talent.catherder.poison_fang.description";
    public static final String TRANSLATION_KEY_TALENT_RESCUECAT_NAME = "talent.catherder.rescue_cat";
    public static final String TRANSLATION_KEY_TALENT_RESCUECAT_DESCRIPTION = "talent.catherder.rescue_cat.description";
    public static final String TRANSLATION_KEY_TALENT_RAZORSHARPCLAWS_NAME = "talent.catherder.razor_sharp_claws";
    public static final String TRANSLATION_KEY_TALENT_RAZORSHARPCLAWS_DESCRIPTION = "talent.catherder.razor_sharp_claws.description";
    public static final String TRANSLATION_KEY_TALENT_RAZORSHARPCLAWS_COOLDOWN = "talent.catherder.razor_sharp_claws.cooldown";
    public static final String TRANSLATION_KEY_TALENT_RAZORSHARPCLAWS_LEVEL = "talent.catherder.razor_sharp_claws.level";
    public static final String TRANSLATION_KEY_TALENT_RAZORSHARPCLAWS_MISS = "talent.catherder.razor_sharp_claws.miss";
    public static final String TRANSLATION_KEY_TALENT_NERMAL_NAME = "talent.catherder.nermal";
    public static final String TRANSLATION_KEY_TALENT_NERMAL_DESCRIPTION = "talent.catherder.nermal.description";
    public static final String TRANSLATION_KEY_TALENT_NERMAL_MSG_1_LINE_1 = "talent.catherder.nermal.msg.1.line.1";
    public static final String TRANSLATION_KEY_TALENT_NERMAL_MSG_1_LINE_2 = "talent.catherder.nermal.msg.1.line.2";
    public static final String TRANSLATION_KEY_TALENT_NERMAL_MSG_2_LINE_1 = "talent.catherder.nermal.msg.2.line.1";
    public static final String TRANSLATION_KEY_TALENT_NERMAL_MSG_2_LINE_2 = "talent.catherder.nermal.msg.2.line.2";
    public static final String TRANSLATION_KEY_TALENT_NERMAL_MSG_2_LINE_3 = "talent.catherder.nermal.msg.2.line.3";
    public static final String TRANSLATION_KEY_TALENT_NERMAL_MSG_3_LINE_1 = "talent.catherder.nermal.msg.3.line.1";
    public static final String TRANSLATION_KEY_TALENT_NERMAL_MSG_3_LINE_2 = "talent.catherder.nermal.msg.3.line.2";
    public static final String TRANSLATION_KEY_TALENT_NERMAL_MSG_3_LINE_3 = "talent.catherder.nermal.msg.3.line.3";
    public static final String TRANSLATION_KEY_TALENT_NERMAL_MSG_4_LINE_1 = "talent.catherder.nermal.msg.4.line.1";
    public static final String TRANSLATION_KEY_TALENT_NERMAL_MSG_4_LINE_2 = "talent.catherder.nermal.msg.4.line.2";
    public static final String TRANSLATION_KEY_TALENT_NERMAL_MSG_4_LINE_3 = "talent.catherder.nermal.msg.4.line.3";
    public static final String TRANSLATION_KEY_TALENT_NERMAL_MSG_5_LINE_1 = "talent.catherder.nermal.msg.5.line.1";
    public static final String TRANSLATION_KEY_TALENT_NERMAL_MSG_5_LINE_2 = "talent.catherder.nermal.msg.5.line.2";
    public static final String TRANSLATION_KEY_TALENT_NERMAL_MSG_5_LINE_3 = "talent.catherder.nermal.msg.5.line.3";
    public static final String TRANSLATION_KEY_TALENT_NERMAL_MSG_6_LINE_1 = "talent.catherder.nermal.msg.6.line.1";
    public static final String TRANSLATION_KEY_TALENT_NERMAL_MSG_6_LINE_2 = "talent.catherder.nermal.msg.6.line.2";
    public static final String TRANSLATION_KEY_TALENT_NERMAL_MSG_6_LINE_3 = "talent.catherder.nermal.msg.6.line.3";
    public static final String TRANSLATION_KEY_TALENT_BEDFINDER_MOUNT = "talent.catherder.bed_finder.mount";
    public static final String TRANSLATION_KEY_CAT_MODE_DOMESTIC = "cat.mode.domestic";
    public static final String TRANSLATION_KEY_CAT_MODE_DOMESTIC_INDICATOR = "cat.mode.domestic.indicator";
    public static final String TRANSLATION_KEY_CAT_MODE_DOMESTIC_DESCRIPTION = "cat.mode.domestic.description";
    public static final String TRANSLATION_KEY_CAT_MODE_DOMESTIC_NO_BOWL = "cat.mode.domestic.no_bowl";
    public static final String TRANSLATION_KEY_CAT_MODE_DOMESTIC_NO_BOX = "cat.mode.domestic.no_box";
    public static final String TRANSLATION_KEY_CAT_MODE_DOMESTIC_NO_TREE = "cat.mode.domestic.no_tree";
    public static final String TRANSLATION_KEY_CAT_MODE_DOMESTIC_BOWL_DISTANCE = "cat.mode.domestic.bowl_distance";
    public static final String TRANSLATION_KEY_CAT_MODE_DOMESTIC_BOX_DISTANCE = "cat.mode.domestic.box_distance";
    public static final String TRANSLATION_KEY_CAT_MODE_DOMESTIC_TREE_DISTANCE = "cat.mode.domestic.tree_distance";
    public static final String TRANSLATION_KEY_CAT_MODE_DOMESTIC_BOWL_TOO_FAR = "cat.mode.domestic.bowl_too_far";
    public static final String TRANSLATION_KEY_CAT_MODE_DOMESTIC_BOX_TOO_FAR = "cat.mode.domestic.box_too_far";
    public static final String TRANSLATION_KEY_CAT_MODE_DOMESTIC_TREE_TOO_FAR = "cat.mode.domestic.tree.too_far";
    public static final String TRANSLATION_KEY_CAT_MODE_TACTICAL = "cat.mode.tactical";
    public static final String TRANSLATION_KEY_CAT_MODE_TACTICAL_INDICATOR = "cat.mode.tactical.indicator";
    public static final String TRANSLATION_KEY_CAT_MODE_TACTICAL_DESCRIPTION = "cat.mode.tactical.description";
    public static final String TRANSLATION_KEY_CAT_MODE_SKITTISH = "cat.mode.skittish";
    public static final String TRANSLATION_KEY_CAT_MODE_SKITTISH_INDICATOR = "cat.mode.skittish.indicator";
    public static final String TRANSLATION_KEY_CAT_MODE_SKITTISH_DESCRIPTION = "cat.mode.skittish.description";
    public static final String TRANSLATION_KEY_CAT_MODE_ATTACK = "cat.mode.attack";
    public static final String TRANSLATION_KEY_CAT_MODE_ATTACK_INDICATOR = "cat.mode.attack.indicator";
    public static final String TRANSLATION_KEY_CAT_MODE_ATTACK_DESCRIPTION = "cat.mode.attack.description";
    public static final String TRANSLATION_KEY_CAT_MODE_DOCILE = "cat.mode.docile";
    public static final String TRANSLATION_KEY_CAT_MODE_DOCILE_INDICATOR = "cat.mode.docile.indicator";
    public static final String TRANSLATION_KEY_CAT_MODE_DOCILE_DESCRIPTION = "cat.mode.docile.description";
    public static final String TRANSLATION_KEY_CAT_MODE_WANDERING = "cat.mode.wandering";
    public static final String TRANSLATION_KEY_CAT_MODE_WANDERING_INDICATOR = "cat.mode.wandering.indicator";
    public static final String TRANSLATION_KEY_CAT_MODE_WANDERING_DESCRIPTION = "cat.mode.wandering.description";
    public static final String TRANSLATION_KEY_CAT_MODE_GUARD = "cat.mode.guard";
    public static final String TRANSLATION_KEY_CAT_MODE_GUARD_INDICATOR = "cat.mode.guard.indicator";
    public static final String TRANSLATION_KEY_CAT_MODE_GUARD_DESCRIPTION = "cat.mode.guard.description";
    public static final String TRANSLATION_KEY_CAT_MODE_INCAPACITATED_INDICATOR = "cat.mode.incapacitated.indicator";
    public static final String TRANSLATION_KEY_CAT_MODE_INCAPACITATED_HELP = "cat.mode.incapacitated.help";
    public static final String TRANSLATION_KEY_CAT_GENDER_FEMALE = "cat.gender.female";
    public static final String TRANSLATION_KEY_CAT_GENDER_FEMALE_INDICATOR = "cat.gender.female.indicator";
    public static final String TRANSLATION_KEY_CAT_GENDER_FEMALE_PRONOUN = "cat.gender.female.pronoun";
    public static final String TRANSLATION_KEY_CAT_GENDER_FEMALE_SUBJECT = "cat.gender.female.subject";
    public static final String TRANSLATION_KEY_CAT_GENDER_FEMALE_TITLE = "cat.gender.female.title";
    public static final String TRANSLATION_KEY_CAT_GENDER_MALE = "cat.gender.male";
    public static final String TRANSLATION_KEY_CAT_GENDER_MALE_INDICATOR = "cat.gender.male.indicator";
    public static final String TRANSLATION_KEY_CAT_GENDER_MALE_PRONOUN = "cat.gender.male.pronoun";
    public static final String TRANSLATION_KEY_CAT_GENDER_MALE_SUBJECT = "cat.gender.male.subject";
    public static final String TRANSLATION_KEY_CAT_GENDER_MALE_TITLE = "cat.gender.male.title";
    public static final String TRANSLATION_KEY_ENTITY_CAT = "entity.catherder.cat";
    public static final String TRANSLATION_KEY_ENTITY_CAT_UNKNOWN_OWNER = "entity.catherder.cat.unknown_owner";
    public static final String TRANSLATION_KEY_ENTITY_CAT_UNTAMED = "entity.catherder.cat.untamed";
    public static final String TRANSLATION_KEY_COMMAND_COME = "catcommand.come";
    public static final String TRANSLATION_KEY_COMMAND_STAY = "catcommand.stay";
    public static final String TRANSLATION_KEY_COMMAND_OKAY = "catcommand.ok";
    public static final String TRANSLATION_KEY_GUI_PREVIOUS_PAGE = "catgui.prevpage";
    public static final String TRANSLATION_KEY_GUI_NEXT_PAGE = "catgui.nextpage";
    public static final String TRANSLATION_KEY_GUI_NEW_NAME = "catgui.newname";
    public static final String TRANSLATION_KEY_GUI_LEVEL = "catgui.level";
    public static final String TRANSLATION_KEY_GUI_LEVEL_WILD = "catgui.level.wild";
    public static final String TRANSLATION_KEY_GUI_POINTS_LEFT = "catgui.pointsleft";
//    public static final String TRANSLATION_KEY_GUI_TEXTURE_INDEX = "catgui.textureindex";
    public static final String TRANSLATION_KEY_GUI_OBEY_OTHERS = "catgui.obeyothers";
    public static final String TRANSLATION_KEY_GUI_FRIENDLY_FIRE = "catgui.friendlyfire";
    public static final String TRANSLATION_KEY_GUI_HEALTH = "catgui.health";
    public static final String TRANSLATION_KEY_GUI_SPEED = "catgui.speed";
    public static final String TRANSLATION_KEY_GUI_OWNER = "catgui.owner";
    public static final String TRANSLATION_KEY_GUI_GENDER = "catgui.gender";
    public static final String TRANSLATION_KEY_GUI_AGE = "catgui.age";
    public static final String TRANSLATION_KEY_GUI_ARMOR = "catgui.armor";
    public static final String TRANSLATION_KEY_GUI_OWNER_YOU = "catgui.owner.you";
    public static final String TRANSLATION_KEY_GUI_BABY = "catgui.age.baby";
    public static final String TRANSLATION_KEY_GUI_ADULT = "catgui.age.adult";
    public static final String TRANSLATION_KEY_RADAR_NORTH = "catradar.north";
    public static final String TRANSLATION_KEY_RADAR_NORTHEAST = "catradar.north.east";
    public static final String TRANSLATION_KEY_RADAR_NORTHWEST = "catradar.north.west";
    public static final String TRANSLATION_KEY_RADAR_SOUTH = "catradar.south";
    public static final String TRANSLATION_KEY_RADAR_SOUTHEAST = "catradar.south.east";
    public static final String TRANSLATION_KEY_RADAR_SOUTHWEST = "catradar.south.west";
    public static final String TRANSLATION_KEY_RADAR_EAST = "catradar.east";
    public static final String TRANSLATION_KEY_RADAR_WEST = "catradar.west";
    public static final String TRANSLATION_KEY_RADAR_NOT_IN_DIM = "catradar.notindim";
    public static final String TRANSLATION_KEY_RADAR_ERROR_NO_RADIO = "catradar.errornoradio";
    public static final String TRANSLATION_KEY_RADAR_ERROR_NULL = "catradar.errornull";
    public static final String TRANSLATION_KEY_RADAR_ERROR_OWN = "catradar.errorown";
    public static final String TRANSLATION_KEY_GUI_CATINFO_TITLE = "catherder.screen.cat.title";
    public static final String TRANSLATION_KEY_GUI_CATINFO_ENTER_NAME = "catInfo.enterName";
    public static final String TRANSLATION_KEY_GUI_CONFIG_TITLE = "title.catherder.config";
    public static final String TRANSLATION_KEY_GUI_CONFIG_CAT_SETTINGS = "modgui.catherder.config.catsettings";
    public static final String TRANSLATION_KEY_GUI_CONFIG_CAT_SETTINGS_TOOLTIP = "modgui.catherder.config.catsettings.tooltip";
    public static final String TRANSLATION_KEY_GUI_CONFIG_GENERAL = "modgui.catherder.config.general";
    public static final String TRANSLATION_KEY_GUI_CONFIG_GENERAL_TOOLTIP = "modgui.catherder.config.general.tooltip";
    public static final String TRANSLATION_KEY_GUI_CONFIG_TALENTS = "modgui.catherder.config.talents";
    public static final String TRANSLATION_KEY_GUI_CONFIG_TALENTS_TOOLTIP = "modgui.catherder.config.talents.tooltip";
    public static final String TRANSLATION_KEY_ARG_UUID_FORMAT_INVALID = "argument.catherder.uuid.format.invalid";
    public static final String TRANSLATION_KEY_ARG_UUID_SECTION_INVALID = "argument.catherder.uuid.section.invalid";
    public static final String TRANSLATION_KEY_COMMANDS_RESPAWN_UUID_SUCCESS = "commands.catrespawn.uuid.success";
    public static final String TRANSLATION_KEY_COMMANDS_RESPAWN_UUID_FAILURE = "commands.catrespawn.uuid.failure";
    public static final String TRANSLATION_KEY_COMMAND_RESPAWN_IMPRECISE = "command.catrespawn.imprecise";
    public static final String TRANSLATION_KEY_CATTREE_COLOR_NULL = "cattree.color.null";
    public static final String TRANSLATION_KEY_CATTREE_COLOR_MISSING = "cattree.color.missing";
    public static final String TRANSLATION_KEY_CATTREE_EXPLAIN_MISSING = "cattree.explain.missing";
    public static final String TRANSLATION_KEY_PETDOOR_STRUCTURE_NULL = "petdoor.structure.null";
    public static final String TRANSLATION_KEY_PETDOOR_STRUCTURE_MISSING = "petdoor.structure.missing";
    public static final String TRANSLATION_KEY_PETDOOR_EXPLAIN_MISSING = "petdoor.explain.missing";
    public static final String TRANSLATION_KEY_COLOR_WHITE = "color.minecraft.white_wool";
    public static final String TRANSLATION_KEY_COLOR_BLACK = "color.minecraft.black_wool";
    public static final String TRANSLATION_KEY_COLOR_GREY = "color.minecraft.gray_wool";
    public static final String TRANSLATION_KEY_COLOR_LIGHT_GREY = "color.minecraft.light_gray_wool";
    public static final String TRANSLATION_KEY_COLOR_PURPLE = "color.minecraft.purple_wool";
    public static final String TRANSLATION_KEY_COLOR_PINK = "color.minecraft.pink_wool";
    public static final String TRANSLATION_KEY_COLOR_BROWN = "color.minecraft.brown_wool";
    public static final String TRANSLATION_KEY_COLOR_ORANGE = "color.minecraft.orange_wool";
    public static final String TRANSLATION_KEY_COLOR_YELLOW = "color.minecraft.yellow_wool";
    public static final String TRANSLATION_KEY_COLOR_GREEN = "color.minecraft.green_wool";
    public static final String TRANSLATION_KEY_COLOR_LIME = "color.minecraft.lime_wool";
    public static final String TRANSLATION_KEY_COLOR_BLUE = "color.minecraft.blue_wool";
    public static final String TRANSLATION_KEY_COLOR_LIGHT_BLUE = "color.minecraft.light_blue_wool";
    public static final String TRANSLATION_KEY_COLOR_MAGENTA = "color.minecraft.magenta_wool";
    public static final String TRANSLATION_KEY_COLOR_RED = "color.minecraft.red_wool";
    public static final String TRANSLATION_KEY_COLOR_CYAN = "color.minecraft.cyan_wool";
    public static final String TRANSLATION_KEY_STRUCTURE_OAK = "structure.minecraft.oak_planks";
    public static final String TRANSLATION_KEY_STRUCTURE_DARK_OAK = "structure.minecraft.dark_oak_planks";
    public static final String TRANSLATION_KEY_STRUCTURE_BIRCH = "structure.minecraft.birch_planks";
    public static final String TRANSLATION_KEY_STRUCTURE_SPRUCE = "structure.minecraft.spruce_planks";
    public static final String TRANSLATION_KEY_STRUCTURE_ACACIA = "structure.minecraft.acacia_planks";
    public static final String TRANSLATION_KEY_STRUCTURE_JUNGLE = "structure.minecraft.jungle_planks";
    public static final String TRANSLATION_KEY_STRUCTURE_CRIMSON = "structure.minecraft.crimson_planks";
    public static final String TRANSLATION_KEY_STRUCTURE_WARPED = "structure.minecraft.warped_planks";
    public static final String TRANSLATION_KEY_CONFIG_CHANCE_WILD_CATNIP = "catherder.config.cat.chance_wild_catnip";
    public static final String TRANSLATION_KEY_CONFIG_WILD_CATNIP_SPREAD = "catherder.config.cat.wild_catnip_spread";
    public static final String TRANSLATION_KEY_CONFIG_ENABLE_LITTERBOX = "catherder.config.cat.enable_litterbox";
    public static final String TRANSLATION_KEY_CONFIG_DISABLE_HUNGER = "catherder.config.cat.disable_hunger";
    public static final String TRANSLATION_KEY_CONFIG_ENABLE_STARTING_ITEMS = "catherder.config.enable_starting_items";
    public static final String TRANSLATION_KEY_CONFIG_ENABLE_GENDER = "catherder.config.enable_gender";
    public static final String TRANSLATION_KEY_CONFIG_ENABLE_KITTEN_PARENT_LEVELS = "catherder.config.enable_kitten_get_parent_levels";
    public static final String TRANSLATION_KEY_CONFIG_TIME_TO_MATURE = "catherder.config.cat.time_to_mature";
    public static final String TRANSLATION_KEY_CONFIG_EAT_FOOD_ON_FLOOR = "catherder.config.eat_food_on_floor";
    // Advancements
    public static final String TRANSLATION_KEY_ADVANCEMENT_TRAIN_CAT_TITLE = "advancements.catherder.main.train_cat.title";
    public static final String TRANSLATION_KEY_ADVANCEMENT_TRAIN_CAT_DESCRIPTION = "advancements.catherder.main.train_cat.description";
    public static final String TRANSLATION_KEY_ADVANCEMENT_PLACE_LITTERBOX_TITLE = "advancements.catherder.main.place_litterbox.title";
    public static final String TRANSLATION_KEY_ADVANCEMENT_PLACE_LITTERBOX_DESCRIPTION = "advancements.catherder.main.place_litterbox.description";
    public static final String TRANSLATION_KEY_ADVANCEMENT_CLEAN_LITTERBOX_TITLE = "advancements.catherder.main.clean_litterbox.title";
    public static final String TRANSLATION_KEY_ADVANCEMENT_CLEAN_LITTERBOX_DESCRIPTION = "advancements.catherder.main.clean_litterbox.description";
    public static final String TRANSLATION_KEY_ADVANCEMENT_PLACE_CARDBOARD_BOX_TITLE = "advancements.catherder.main.place_cardboard_box.title";
    public static final String TRANSLATION_KEY_ADVANCEMENT_PLACE_CARDBOARD_BOX_DESCRIPTION = "advancements.catherder.main.place_cardboard_box.description";
    public static final String TRANSLATION_KEY_ADVANCEMENT_PLACE_MOUSETRAP_TITLE = "advancements.catherder.main.place_mousetrap.title";
    public static final String TRANSLATION_KEY_ADVANCEMENT_PLACE_MOUSETRAP_DESCRIPTION = "advancements.catherder.main.place_mousetrap.description";
    public static final String TRANSLATION_KEY_ADVANCEMENT_CRAFT_PET_DOOR_TITLE = "advancements.catherder.main.craft_pet_door.title";
    public static final String TRANSLATION_KEY_ADVANCEMENT_CRAFT_PET_DOOR_DESCRIPTION = "advancements.catherder.main.craft_pet_door.description";
    public static final String TRANSLATION_KEY_ADVANCEMENT_GIVE_CATNIP_TITLE = "advancements.catherder.main.give_catnip.title";
    public static final String TRANSLATION_KEY_ADVANCEMENT_GIVE_CATNIP_DESCRIPTION = "advancements.catherder.main.give_catnip.description";
    public static final String TRANSLATION_KEY_ADVANCEMENT_FIND_CATNIP_TITLE = "advancements.catherder.main.find_catnip.title";
    public static final String TRANSLATION_KEY_ADVANCEMENT_FIND_CATNIP_DESCRIPTION = "advancements.catherder.main.find_catnip.description";
    public static final String TRANSLATION_KEY_ADVANCEMENT_CRAFT_CHEESEWHEEL_TITLE = "advancements.catherder.main.craft_cheesewheel.title";
    public static final String TRANSLATION_KEY_ADVANCEMENT_CRAFT_CHEESEWHEEL_DESCRIPTION = "advancements.catherder.main.craft_cheesewheel.description";
    public static final String TRANSLATION_KEY_ADVANCEMENT_CHANGE_OWNER_TITLE = "advancements.catherder.main.change_owner.title";
    public static final String TRANSLATION_KEY_ADVANCEMENT_CHANGE_OWNER_DESCRIPTION = "advancements.catherder.main.change_owner.description";
    public static final String TRANSLATION_KEY_ADVANCEMENT_MAX_LEVEL_TITLE = "advancements.catherder.main.max_level.title";
    public static final String TRANSLATION_KEY_ADVANCEMENT_MAX_LEVEL_DESCRIPTION = "advancements.catherder.main.max_level.description";
    public static final String TRANSLATION_KEY_ADVANCEMENT_CELESTIAL_EXPLORATION_TITLE = "advancements.catherder.main.ce.title";
    public static final String TRANSLATION_KEY_ADVANCEMENT_CELESTIAL_EXPLORATION_DESCRIPTION = "advancements.catherder.main.ce.description";
    public static final String TRANSLATION_KEY_ADVANCEMENT_RADIO_COLLAR_TITLE = "advancements.catherder.main.radio_collar.title";
    public static final String TRANSLATION_KEY_ADVANCEMENT_RADIO_COLLAR_DESCRIPTION = "advancements.catherder.main.radio_collar.description";
    public static final String TRANSLATION_KEY_ADVANCEMENT_CAT_TREE_TITLE = "advancements.catherder.main.cat_tree.title";
    public static final String TRANSLATION_KEY_ADVANCEMENT_CAT_TREE_DESCRIPTION = "advancements.catherder.main.cat_tree.description";
    public static final String TRANSLATION_KEY_ADVANCEMENT_SUNGLASSES_TITLE = "advancements.catherder.main.sunglasses.title";
    public static final String TRANSLATION_KEY_ADVANCEMENT_SUNGLASSES_DESCRIPTION = "advancements.catherder.main.sunglasses.description";
    public static final String TRANSLATION_KEY_ADVANCEMENT_THROW_TOY_TITLE = "advancements.catherder.main.toy.title";
    public static final String TRANSLATION_KEY_ADVANCEMENT_THROW_TOY_DESCRIPTION = "advancements.catherder.main.toy.description";
    public static final String TRANSLATION_KEY_ADVANCEMENT_NERMAL_TITLE = "advancements.catherder.main.nermal.title";
    public static final String TRANSLATION_KEY_ADVANCEMENT_NERMAL_DESCRIPTION = "advancements.catherder.main.nermal.description";
    public static final String TRANSLATION_KEY_ADVANCEMENT_LASAGNA_TITLE = "advancements.catherder.main.lasagna.title";
    public static final String TRANSLATION_KEY_ADVANCEMENT_LASAGNA_DESCRIPTION = "advancements.catherder.main.lasagna.description";

    public static class EntityState {
        public static final byte DEATH = 3;
        public static final byte CAT_SMOKE = 6;
        public static final byte CAT_HEARTS = 7;
        public static final byte CAT_START_SHAKING = 8;
        public static final byte GUARDIAN_SOUND = 21;
        public static final byte TOTEM_OF_UNDYING = 35;
        public static final byte SLIDING_DOWN_HONEY = 53;
        public static final byte CAT_INTERRUPT_SHAKING = 56;
    }
}
