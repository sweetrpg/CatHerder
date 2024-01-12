package com.sweetrpg.catherder.common.world;

//import com.sweetrpg.catherder.common.config.ConfigHandler;
//import com.sweetrpg.catherder.common.registry.ModBlocks;
//import com.sweetrpg.catherder.common.util.Util;
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.Registry;
//import net.minecraft.data.worldgen.placement.PlacementUtils;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
//import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
//import net.minecraft.world.level.levelgen.feature.Feature;
//import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
//import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
//import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
//import net.minecraft.world.level.levelgen.placement.BiomeFilter;
//import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
//import net.minecraft.world.level.levelgen.placement.PlacedFeature;
//import net.minecraft.world.level.levelgen.placement.RarityFilter;
//
//import java.util.Arrays;
//import java.util.List;
//
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.Holder;
//import net.minecraft.core.Registry;
//import net.minecraft.data.worldgen.features.FeatureUtils;
//import net.minecraft.data.worldgen.placement.PlacementUtils;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.tags.BlockTags;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
//import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
//import net.minecraft.world.level.levelgen.feature.Feature;
//import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
//import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
//import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
//import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
//import net.minecraft.world.level.levelgen.placement.*;

import com.sweetrpg.catherder.api.CatHerderAPI;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class WildCropGeneration {

    public static ResourceKey<ConfiguredFeature<?, ?>> FEATURE_PATCH_WILD_CATNIP = ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(CatHerderAPI.MOD_ID, "patch_wild_catnip"));

    public static ResourceKey<PlacedFeature> PATCH_WILD_CATNIP = ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(CatHerderAPI.MOD_ID, "patch_wild_catnip"));

//    public static final BlockPos BLOCK_BELOW = new BlockPos(0, -1, 0);
//
//    public static Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> FEATURE_PATCH_WILD_CATNIP;
//    public static Holder<PlacedFeature> PATCH_WILD_CATNIP;
//
//    public static RandomPatchConfiguration getWildCropConfiguration(Block block, int tries, int xzSpread, int ySpread, BlockPredicate plantedOn) {
//        return new RandomPatchConfiguration(tries, xzSpread, ySpread, PlacementUtils.filtered(
//                                            Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(block)),
//                                                            BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, plantedOn)));
//    }
//
//    static Holder<PlacedFeature> registerPlacement(ResourceLocation id, Holder<? extends ConfiguredFeature<?, ?>> feature, PlacementModifier... modifiers) {
//        return BuiltinRegistries.register(BuiltinRegistries.PLACED_FEATURE, id, new PlacedFeature(Holder.hackyErase(feature), List.of(modifiers)));
//    }
//
//    protected static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> register(ResourceLocation id, F feature, FC featureConfig) {
//        return register(BuiltinRegistries.CONFIGURED_FEATURE, id, new ConfiguredFeature<>(feature, featureConfig));
//    }
//
//    private static <V extends T, T> Holder<V> register(Registry<T> registry, ResourceLocation id, V value) {
//        return (Holder<V>) BuiltinRegistries.<T>register(registry, id, value);
//    }
//
//    public static void registerWildCatnipGeneration() {
//        FEATURE_PATCH_WILD_CATNIP = register(Util.modLoc("patch_wild_catnip"),
//                                               Feature.RANDOM_PATCH,
//                                             getWildCropConfiguration(ModBlocks.WILD_CATNIP.get(),
//                                                                      64, ConfigHandler.SERVER.WILD_CATNIP_SPREAD.get(), 3,
//                                                                                                               BlockPredicate.matchesBlocks(Arrays.asList(Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.PODZOL), BLOCK_BELOW)));
//
////        FEATURE_PATCH_WILD_CATNIP = Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,
////                                                      Util.modLoc("patch_wild_catnip"),
////                                                      Feature.RANDOM_PATCH.configured(getWildCropConfiguration(ModBlocks.WILD_CATNIP.get(), 64, ConfigHandler.SERVER.WILD_CATNIP_SPREAD.get(), 3,
////                                                                                                               BlockPredicate.matchesBlocks(Arrays.asList(Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.PODZOL), BLOCK_BELOW))));
//
//        PATCH_WILD_CATNIP = registerPlacement(Util.modLoc("patch_wild_catnip"),
//                                                FEATURE_PATCH_WILD_CATNIP,
//                                                RarityFilter.onAverageOnceEvery(ConfigHandler.SERVER.CHANCE_WILD_CATNIP.get()),
//                                              InSquarePlacement.spread(),
//                                              PlacementUtils.HEIGHTMAP,
//                                              BiomeFilter.biome());
//
////        PATCH_WILD_CATNIP = Registry.register(BuiltinRegistries.PLACED_FEATURE,
////                                              Util.modLoc("patch_wild_catnip"),
////                                              FEATURE_PATCH_WILD_CATNIP.placed(RarityFilter.onAverageOnceEvery(ConfigHandler.SERVER.CHANCE_WILD_CATNIP.get()),
////                                                                               InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
//    }

    public static void load() {
    }

}
