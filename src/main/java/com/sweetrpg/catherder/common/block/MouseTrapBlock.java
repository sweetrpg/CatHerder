package com.sweetrpg.catherder.common.block;

import com.sweetrpg.catherder.common.registry.ModItems;
import com.sweetrpg.catherder.common.util.WorldUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class MouseTrapBlock extends Block {

    public static final BooleanProperty SPRUNG = BlockStateProperties.TRIGGERED;
    protected static final VoxelShape SHAPE_NORTH_SOUTH = Block.box(1.0D, 0.0D, 0.0D, 15.0D, 2.0D, 16.0D);
    protected static final VoxelShape SHAPE_EAST_WEST = Block.box(0.0D, 0.0D, 1.0D, 16.0D, 2.0D, 15.0D);

    public MouseTrapBlock() {
        super(Block.Properties.of(Material.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD));
    }

    public boolean hasDynamicShape() {
        return true;
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter pLevel, BlockPos pPos) {
        return switch (state.getValue(BlockStateProperties.FACING)) {
            case UP, DOWN, NORTH, SOUTH -> SHAPE_NORTH_SOUTH;
            case WEST, EAST -> SHAPE_EAST_WEST;
        };
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(BlockStateProperties.FACING)) {
            case UP, DOWN, NORTH, SOUTH -> SHAPE_NORTH_SOUTH;
            case WEST, EAST -> SHAPE_EAST_WEST;
        };
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext selectionContext) {
        return switch (state.getValue(BlockStateProperties.FACING)) {
            case UP, DOWN, NORTH, SOUTH -> SHAPE_NORTH_SOUTH;
            case WEST, EAST -> SHAPE_EAST_WEST;
        };
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context)
                    .setValue(SPRUNG, true)
                .setValue(BlockStateProperties.FACING, context.getNearestLookingDirection().getOpposite());
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);

        if (stack.isEmpty()) {
            return InteractionResult.SUCCESS;
        }
        else {
            if(stack.getItem() == ModItems.CHEESE_WEDGE.get()) {
                if(!level.isClientSide) {
                    if(!player.getAbilities().instabuild) {
                        stack.shrink(1);
                    }

                    level.playSound(null, pos, SoundEvents.CHEST_LOCKED, SoundSource.BLOCKS, 1.0F, 1.0F);
                    level.setBlock(pos, state.setValue(SPRUNG, false), Block.UPDATE_ALL);
//                    state.setValue(SPRUNG, false);
                }
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.FACING, SPRUNG);
    }
}
