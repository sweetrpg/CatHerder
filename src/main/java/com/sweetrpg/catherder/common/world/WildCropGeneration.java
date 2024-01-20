package com.sweetrpg.catherder.common.world;

import com.sweetrpg.catherder.api.CatHerderAPI;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class WildCropGeneration {

    public static ResourceKey<ConfiguredFeature<?, ?>> FEATURE_PATCH_WILD_CATNIP = ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(CatHerderAPI.MOD_ID, "patch_wild_catnip"));

    public static ResourceKey<PlacedFeature> PATCH_WILD_CATNIP = ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(CatHerderAPI.MOD_ID, "patch_wild_catnip"));

    public static void load() {
    }

}
