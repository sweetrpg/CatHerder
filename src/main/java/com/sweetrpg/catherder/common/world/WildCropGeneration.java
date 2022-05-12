package com.sweetrpg.catherder.common.world;

import com.sweetrpg.catherder.common.config.ConfigHandler;
import com.sweetrpg.catherder.common.registry.ModBlocks;
import com.sweetrpg.catherder.common.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

import java.util.Arrays;

public class WildCropGeneration {

    public static final BlockPos BLOCK_BELOW = new BlockPos(0, -1, 0);

    public static ConfiguredFeature<RandomPatchConfiguration, ?> FEATURE_PATCH_WILD_CATNIP;
    public static PlacedFeature PATCH_WILD_CATNIP;

    public static RandomPatchConfiguration getWildCropConfiguration(Block block, int tries, int xzSpread, int ySpread, BlockPredicate plantedOn) {
        return new RandomPatchConfiguration(tries, xzSpread, ySpread,
                                            () -> Feature.SIMPLE_BLOCK.configured(new SimpleBlockConfiguration(BlockStateProvider.simple(block)))
                                                            .filtered(BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, plantedOn)));
    }

    public static void registerWildCatnipGeneration() {
        FEATURE_PATCH_WILD_CATNIP = Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,
                                                      Util.modLoc("patch_wild_catnip"),
                                                      Feature.RANDOM_PATCH.configured(getWildCropConfiguration(ModBlocks.WILD_CATNIP.get(), 64, ConfigHandler.SERVER.WILD_CATNIP_SPREAD.get(), 3,
                                                                                                               BlockPredicate.matchesBlocks(Arrays.asList(Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.PODZOL), BLOCK_BELOW))));

        PATCH_WILD_CATNIP = Registry.register(BuiltinRegistries.PLACED_FEATURE,
                                              Util.modLoc("patch_wild_catnip"),
                                              FEATURE_PATCH_WILD_CATNIP.placed(RarityFilter.onAverageOnceEvery(ConfigHandler.SERVER.CHANCE_WILD_CATNIP.get()),
                                                                               InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
    }

}
