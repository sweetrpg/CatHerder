package com.sweetrpg.catherder.common.config;

import com.sweetrpg.catherder.CatHerder;
import com.sweetrpg.catherder.api.CatHerderAPI;
import com.sweetrpg.catherder.api.registry.Talent;
import com.sweetrpg.catherder.common.lib.Constants;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.registries.IRegistryDelegate;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

public class ConfigHandler {

    public static ClientConfig CLIENT;
    public static ServerConfig SERVER;
    public static TalentConfig TALENT;
    private static ForgeConfigSpec CONFIG_SERVER_SPEC;
    private static ForgeConfigSpec CONFIG_CLIENT_SPEC;
    private static ForgeConfigSpec CONFIG_TALENT_SPEC;

    public static final boolean ALWAYS_SHOW_CAT_NAME = true;
    public static final float DEFAULT_MAX_HUNGER = 120F;
    public static final boolean SEND_SKIN = false;
    public static final boolean DISPLAY_OTHER_CAT_SKINS = false;
    public static final boolean WHISTLE_SOUNDS = true;

    public static void init(IEventBus modEventBus) {
        Pair<ServerConfig, ForgeConfigSpec> commonPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
        CONFIG_SERVER_SPEC = commonPair.getRight();
        SERVER = commonPair.getLeft();
        Pair<ClientConfig, ForgeConfigSpec> clientPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
        CONFIG_CLIENT_SPEC = clientPair.getRight();
        CLIENT = clientPair.getLeft();
        CatHerder.LOGGER.debug("Register configs");

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, CONFIG_SERVER_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CONFIG_CLIENT_SPEC);
    }

    public static void initTalentConfig() {
        Pair<TalentConfig, ForgeConfigSpec> talentPair = new ForgeConfigSpec.Builder().configure(TalentConfig::new);
        CONFIG_TALENT_SPEC = talentPair.getRight();
        TALENT = talentPair.getLeft();

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, CONFIG_TALENT_SPEC, "catherder-talents.toml");
    }

    public static class ClientConfig {

        public ForgeConfigSpec.BooleanValue DIRE_PARTICLES;
        public ForgeConfigSpec.BooleanValue RENDER_CHEST;
        public ForgeConfigSpec.BooleanValue USE_CH_TEXTURES;
        public ForgeConfigSpec.BooleanValue RENDER_ARMOUR;
        public ForgeConfigSpec.BooleanValue RENDER_SADDLE;
        public ForgeConfigSpec.BooleanValue RENDER_WINGS;
        public ForgeConfigSpec.BooleanValue RENDER_BLOOD;
        public ForgeConfigSpec.IntValue SKITTISH_OWNER;
        public ForgeConfigSpec.IntValue SKITTISH_ANIMALS;
        public ForgeConfigSpec.IntValue SKITTISH_OTHERS;
        public ForgeConfigSpec.IntValue SKITTISH_TWITCHINESS;
        public ForgeConfigSpec.IntValue MAX_ITEM_DISTANCE;
        public ForgeConfigSpec.IntValue MAX_WANDER_DISTANCE;

        public ClientConfig(ForgeConfigSpec.Builder builder) {
            {
                builder.push("General");

                SKITTISH_OWNER = builder.comment("Sets the likelihood (in percent) that a skittish cat will avoid its owner.").translation("catherder.config.client.skittish_owner").defineInRange("skittish_owner", 25, 1, 100);
                SKITTISH_ANIMALS = builder.comment("Sets the likelihood (in percent) that a skittish cat will avoid other animals.").translation("catherder.config.client.skittish_animals").defineInRange("skittish_animals", 40, 1, 100);
                SKITTISH_OTHERS = builder.comment("Sets the likelihood (in percent) that a skittish cat will avoid any other creature.").translation("catherder.config.client.skittish_others").defineInRange("skittish_others", 75, 1, 100);
                SKITTISH_TWITCHINESS = builder.comment("Sets how sensitive the check for fleeing from an entity is.").translation("catherder.config.client.skittish_twitchiness").defineInRange("skittish_twitchiness", 750, 1, 1000);

                builder.pop();
            }

            {
                builder.push("Wandering");

                MAX_ITEM_DISTANCE = builder.comment("Sets the maximum distance domestic items may be away from each other to be considered part of the cat's wandering area.").translation("catherder.config.client.domestic_item_max_distance").defineInRange("domestic_item_max_distance", 40, 20, 100);
                MAX_WANDER_DISTANCE = builder.comment("Sets the maximum distance a cat in wander mode will wander from its owner.").translation("catherder.config.client.wander_max_distance").defineInRange("wander_max_distance", 200, 20, 1000);

                builder.pop();
            }

            {
                builder.push("Cat Render");

                DIRE_PARTICLES = builder.comment("Enables the particle effect on Wild Level 30 cats.").translation("catherder.config.client.enable_wild_particles").define("enable_wild_particles", true);
                RENDER_CHEST = builder.comment("When enabled, cats with points in pack cat will have chests on their side.").translation("catherder.config.client.render_chest").define("render_chest", true);
                USE_CH_TEXTURES = builder.comment("If disabled will use the default minecraft skin for all cat textures.").translation("catherder.config.client.enable_dt_textures").define("enable_ch_textures", true);
                RENDER_ARMOUR = builder.comment("When enabled, cats with points in guard cat will have armour.").translation("catherder.config.client.render_armour").define("render_armour", false);
                RENDER_SADDLE = builder.comment("When enabled, cats with points in mount will have a saddle on.").translation("catherder.config.client.render_saddle").define("render_saddle", true);
                RENDER_WINGS = builder.comment("When enabled, cats will have wings when at level 5 pillow paw.").translation("catherder.config.client.render_wings").define("render_wings", false);
                RENDER_BLOOD = builder.comment("When enabled, cats will show blood texture while incapacitated.").translation("catherder.config.client.render_incapacitated_overlay").define("render_incapacitated_overlay", true);

                builder.pop();
            }
        }
    }

    public static class ServerConfig {

        public ForgeConfigSpec.BooleanValue LITTERBOX;
        public ForgeConfigSpec.IntValue CHANCE_WILD_CATNIP;
        public ForgeConfigSpec.IntValue WILD_CATNIP_SPREAD;
        public ForgeConfigSpec.BooleanValue DISABLE_HUNGER;
        public ForgeConfigSpec.BooleanValue STARTING_ITEMS;
        public ForgeConfigSpec.BooleanValue CAT_GENDER;
        public ForgeConfigSpec.BooleanValue KITTENS_GET_PARENT_LEVELS;
        public ForgeConfigSpec.IntValue TIME_TO_MATURE;
        public ForgeConfigSpec.BooleanValue CAT_WHINE_WHEN_HUNGER_LOW;
        public ForgeConfigSpec.BooleanValue EAT_FOOD_ON_FLOOR;

        public Map<String, ForgeConfigSpec.BooleanValue> DISABLED_TALENTS;

        public ServerConfig(ForgeConfigSpec.Builder builder) {
            {
                builder.push("General");

                //DEBUG_MODE = builder
                //        .comment("Enables debugging mode, which would output values for the sake of finding issues in the mod.")
                //        .define("debugMode", false);
                CHANCE_WILD_CATNIP = builder.comment("Chance that catnip appears in the wild").translation(Constants.TRANSLATION_KEY_CONFIG_CHANCE_WILD_CATNIP).defineInRange("chance_wild_catnip", 50, 1, 100);
                WILD_CATNIP_SPREAD = builder.comment("Horizontal spread of patches of catnip").translation(Constants.TRANSLATION_KEY_CONFIG_WILD_CATNIP_SPREAD).defineInRange("wild_catnip_spread", 6, 1, 20);

                builder.pop();
            }

            {
                builder.push("Cat Constants");

                LITTERBOX = builder.comment("Enables litterbox maintenance").translation(Constants.TRANSLATION_KEY_CONFIG_ENABLE_LITTERBOX).define("enable_litterbox", true);
                DISABLE_HUNGER = builder.comment("Disable hunger mode for the cat").translation(Constants.TRANSLATION_KEY_CONFIG_DISABLE_HUNGER).define("disable_hunger", false);
                STARTING_ITEMS = builder.comment("When enabled you will spawn with a guide, Cat Charm and Command Emblem.").translation(Constants.TRANSLATION_KEY_CONFIG_ENABLE_STARTING_ITEMS).define("enable_starting_items", false);
                CAT_GENDER = builder.comment("When enabled, cats will be randomly assigned genders and will only mate and produce children with the opposite gender.").translation(Constants.TRANSLATION_KEY_CONFIG_ENABLE_GENDER).define("enable_gender", true);
                KITTENS_GET_PARENT_LEVELS = builder.comment("When enabled, kittens get some levels from parents. When disabled, kittens start at 0 points.").translation(Constants.TRANSLATION_KEY_CONFIG_ENABLE_KITTEN_PARENT_LEVELS).define("enable_kitten_get_parent_levels", false);
                TIME_TO_MATURE = builder.comment("The time in ticks it takes for a baby cat to become an adult, default 48000 (2 Minecraft days) and minimum 0").translation(Constants.TRANSLATION_KEY_CONFIG_TIME_TO_MATURE).defineInRange("time_to_mature", 48000, 0, Integer.MAX_VALUE);
                EAT_FOOD_ON_FLOOR = builder.comment("When enabled cats will path and eat editable items in the world.").translation(Constants.TRANSLATION_KEY_CONFIG_EAT_FOOD_ON_FLOOR).define("eat_food_on_floor", true);

                builder.pop();
            }
        }
    }

    public static class TalentConfig {
        public Map<IRegistryDelegate<Talent>, ForgeConfigSpec.BooleanValue> DISABLED_TALENTS;

        public TalentConfig(ForgeConfigSpec.Builder builder) {
            builder.comment("Here you can disable talents.").push("Talents");

            DISABLED_TALENTS = new HashMap<>();

            CatHerderAPI.TALENTS.get().forEach((loc) ->
                DISABLED_TALENTS.put(loc.delegate, builder.define(loc.getRegistryName().toString(), true))
            );
            builder.pop();
        }

        public boolean getFlag(Talent talent) {
            ForgeConfigSpec.BooleanValue booleanValue = this.DISABLED_TALENTS.get(talent.delegate);
            if (booleanValue == null) {
                return true;
            }

            return booleanValue.get();
        }
    }
}
