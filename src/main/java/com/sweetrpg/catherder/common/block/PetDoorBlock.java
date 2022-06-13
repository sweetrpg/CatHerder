package com.sweetrpg.catherder.common.block;

import com.sweetrpg.catherder.common.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class PetDoorBlock extends Block {

    protected static final VoxelShape UNLOCKED_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
    protected static final VoxelShape LOCKED_SHAPE_NORTH_SOUTH = Block.box(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
    protected static final VoxelShape LOCKED_SHAPE_EAST_WEST = Block.box(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);

    public PetDoorBlock() {
        super(Block.Properties.of(Material.WOOD).strength(1.0F, 5.0F).sound(SoundType.WOOD));
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pPos) {
        return switch(state.getValue(BlockStateProperties.HORIZONTAL_FACING)) {
            case UP, DOWN, NORTH, SOUTH -> LOCKED_SHAPE_NORTH_SOUTH;
            case WEST, EAST -> LOCKED_SHAPE_EAST_WEST;
        };
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return getOcclusionShape(state, level, pos);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext selectionContext) {
        if(state.getValue(BlockStateProperties.LOCKED)) {
            return switch(state.getValue(BlockStateProperties.HORIZONTAL_FACING)) {
                case UP, DOWN, NORTH, SOUTH -> LOCKED_SHAPE_NORTH_SOUTH;
                case WEST, EAST -> LOCKED_SHAPE_EAST_WEST;
            };
        }

        return UNLOCKED_SHAPE;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context)
                       .setValue(BlockStateProperties.LOCKED, false)
                       .setValue(BlockStateProperties.HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
    }

    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {

        boolean locked = state.getValue(BlockStateProperties.LOCKED);

        level.playSound(null, pos, SoundEvents.CHEST_LOCKED, SoundSource.BLOCKS, 1.0F, 1.0F);
        level.setBlock(pos, state.setValue(BlockStateProperties.LOCKED, !locked), Block.UPDATE_ALL);
        state.setValue(BlockStateProperties.LOCKED, !locked);

        return InteractionResult.SUCCESS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.LOCKED);
    }
}
