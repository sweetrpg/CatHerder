package com.sweetrpg.catherder.common.block;

import com.sweetrpg.catherder.common.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheeseWheelBlock extends Block {

    public static final IntegerProperty SERVINGS = IntegerProperty.create("servings", 0, 8);
    protected static final VoxelShape PLATE_SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 2.0D, 15.0D);
//    protected static final List<VoxelShape> SHAPES = Arrays.asList(
//            PLATE_SHAPE,
//            Shapes.joinUnoptimized(PLATE_SHAPE, Block.box(2.0D, 2.0D, 2.0D, 14.0D, 3.0D, 14.0D), BooleanOp.OR),
//            Shapes.joinUnoptimized(PLATE_SHAPE, Block.box(2.0D, 3.0D, 2.0D, 14.0D, 4.0D, 14.0D), BooleanOp.OR),
//            Shapes.joinUnoptimized(PLATE_SHAPE, Block.box(2.0D, 4.0D, 2.0D, 14.0D, 5.0D, 14.0D), BooleanOp.OR),
//            Shapes.joinUnoptimized(PLATE_SHAPE, Block.box(2.0D, 5.0D, 2.0D, 14.0D, 6.0D, 14.0D), BooleanOp.OR),
//            Shapes.joinUnoptimized(PLATE_SHAPE, Block.box(2.0D, 6.0D, 2.0D, 14.0D, 7.0D, 14.0D), BooleanOp.OR),
//            Shapes.joinUnoptimized(PLATE_SHAPE, Block.box(2.0D, 7.0D, 2.0D, 14.0D, 8.0D, 14.0D), BooleanOp.OR),
//            Shapes.joinUnoptimized(PLATE_SHAPE, Block.box(2.0D, 8.0D, 2.0D, 14.0D, 9.0D, 14.0D), BooleanOp.OR),
//            Shapes.joinUnoptimized(PLATE_SHAPE, Block.box(2.0D, 9.0D, 2.0D, 14.0D, 10.0D, 14.0D), BooleanOp.OR)
//    );
    protected static final VoxelShape PIE_SHAPE = Shapes.joinUnoptimized(PLATE_SHAPE, Block.box(2.0D, 2.0D, 2.0D, 14.0D, 8.0D, 14.0D), BooleanOp.OR);
    //    public final Supplier<Item> servingItem;
    public final boolean hasLeftovers;
    /**
     * This block provides up to 8 servings of food to players who interact with it.
     * If a leftover item is specified, the block lingers at 0 servings, and is destroyed on right-click.
     *
     * @param properties   Block properties.
     * @param hasLeftovers Whether the block remains when out of servings. If false, the block vanishes once it runs out.
     */
    public CheeseWheelBlock(Properties properties, boolean hasLeftovers) {
        super(properties);
//        this.servingItem = servingItem;
        this.hasLeftovers = hasLeftovers;
        this.registerDefaultState(this.stateDefinition.any()
                                          .setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH)
                                          .setValue(SERVINGS, 8));
    }

    @Override
    public boolean hasDynamicShape() {
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return state.getValue(SERVINGS) == 0 ? PLATE_SHAPE : PIE_SHAPE;
    }

    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        ItemStack itemStack = player.getItemInHand(handIn);
        Item item = itemStack.getItem();
        if(itemStack.is(Items.SHEARS)) {
            worldIn.playSound(null, pos, SoundEvents.ITEM_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
//                pLevel.setBlockAndUpdate(pos, CandleCakeBlock.byCandle(block));
            worldIn.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
//                player.awardStat(Stats.ITEM_USED.get(item));
            player.getInventory().add(new ItemStack(ModItems.CHEESE_WEDGE.get(), state.getValue(SERVINGS)));
            player.getInventory().add(new ItemStack(Items.BOWL));
            worldIn.removeBlock(pos, false);
            return InteractionResult.SUCCESS;
        }

        if(worldIn.isClientSide) {
            if(this.takeServing(worldIn, pos, state, player, handIn).consumesAction()) {
                return InteractionResult.SUCCESS;
            }
        }

        return this.takeServing(worldIn, pos, state, player, handIn);
    }

    private InteractionResult takeServing(LevelAccessor worldIn, BlockPos pos, BlockState state, Player player, InteractionHand handIn) {
        int servings = state.getValue(SERVINGS);

        if(servings == 0) {
            worldIn.playSound(null, pos, SoundEvents.WOOD_BREAK, SoundSource.PLAYERS, 0.8F, 0.8F);
            worldIn.destroyBlock(pos, true);
            return InteractionResult.SUCCESS;
        }

//        ItemStack serving = this.getServingItem();
//        ItemStack heldStack = player.getItemInHand(handIn);

        if(servings > 0) {
//            if (heldStack.sameItem(serving.getContainerItem())) {
            worldIn.setBlock(pos, state.setValue(SERVINGS, servings - 1), Block.UPDATE_ALL);
//                if (!player.getAbilities().instabuild) {
//                    heldStack.shrink(1);
//                }
            player.getInventory().add(new ItemStack(ModItems.CHEESE_WEDGE.get()));
//                if (!player.getInventory().add(serving)) {
//                    player.drop(serving, false);
//                }
            if(worldIn.getBlockState(pos).getValue(SERVINGS) == 0 && !this.hasLeftovers) {
                worldIn.removeBlock(pos, false);
                worldIn.playSound(null, pos, SoundEvents.WOOD_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
            else {
                worldIn.playSound(null, pos, SoundEvents.GENERIC_EAT, SoundSource.BLOCKS, 1.0F, 1.0F);
            }

            return InteractionResult.SUCCESS;
//            }
//            else {
//                player.displayClientMessage(TextUtils.getTranslation("block", "cheese_wheel.use_container", serving.getContainerItem().getHoverName()), true);
//            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(BlockStateProperties.HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
//                .setValue(SERVINGS, context.);
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        return facing == Direction.DOWN && !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.below()).getMaterial().isSolid();
    }

    @SuppressWarnings("deprecation")
    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level worldIn, BlockPos pos) {
        return blockState.getValue(SERVINGS);
    }

    public int getMaxServings() {
        return 8;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isPathfindable(BlockState state, BlockGetter worldIn, BlockPos pos, PathComputationType type) {
        return false;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BlockStateProperties.HORIZONTAL_FACING, SERVINGS);
    }
}
