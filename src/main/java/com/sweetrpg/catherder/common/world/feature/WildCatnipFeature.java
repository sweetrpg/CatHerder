//package com.sweetrpg.catherder.common.world.feature;
//
//import com.mojang.serialization.Codec;
//import com.sweetrpg.catherder.common.block.WildCatnipBlock;
//import com.sweetrpg.catherder.common.registry.ModBlocks;
//import net.minecraft.core.BlockPos;
//import net.minecraft.util.RandomSource;
//import net.minecraft.world.level.WorldGenLevel;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.level.block.DoublePlantBlock;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
//import net.minecraft.world.level.levelgen.Heightmap;
//import net.minecraft.world.level.levelgen.feature.Feature;
//import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
//import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
//
//public class WildCatnipFeature extends Feature<RandomPatchConfiguration>
//{
//	public WildCatnipFeature(Codec<RandomPatchConfiguration> configFactoryIn) {
//		super(configFactoryIn);
//	}
//
//	@Override
//	public boolean place(FeaturePlaceContext<RandomPatchConfiguration> context) {
//		WorldGenLevel level = context.level();
//		BlockPos origin = context.origin();
//		RandomPatchConfiguration config = context.config();
//		RandomSource rand = context.random();
//
//		BlockPos blockpos = level.getHeightmapPos(Heightmap.Types.OCEAN_FLOOR_WG, origin);
//
//		int i = 0;
//		BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();
//
//		for (int j = 0; j < config.tries(); ++j) {
//			blockpos$mutable.set(blockpos).move(
//					rand.nextInt(config.xzSpread() + 1) - rand.nextInt(config.xzSpread() + 1),
//					rand.nextInt(config.ySpread() + 1) - rand.nextInt(config.ySpread() + 1),
//					rand.nextInt(config.xzSpread() + 1) - rand.nextInt(config.xzSpread() + 1));
//
//			if (level.getBlockState(blockpos$mutable).getBlock() == Blocks.WATER && level.getBlockState(blockpos$mutable.above()).getBlock() == Blocks.AIR) {
//				BlockState bottomRiceState = ModBlocks.WILD_CATNIP.get().defaultBlockState(); // .setValue(WildCatnipBlock.HALF, DoubleBlockHalf.LOWER);
//				if (bottomRiceState.canSurvive(level, blockpos$mutable)) {
//					DoublePlantBlock.placeAt(level, bottomRiceState, blockpos$mutable, 2);
//					++i;
//				}
//			}
//		}
//
//		return i > 0;
//	}
//}
