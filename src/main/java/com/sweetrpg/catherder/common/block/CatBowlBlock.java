package com.sweetrpg.catherder.common.block;

import com.sweetrpg.catherder.common.registry.ModItems;
import com.sweetrpg.catherder.common.registry.ModTileEntityTypes;
import com.sweetrpg.catherder.common.Screens;
import com.sweetrpg.catherder.common.block.tileentity.CatBowlBlockEntity;
import com.sweetrpg.catherder.common.util.InventoryUtil;
import com.sweetrpg.catherder.common.util.WorldUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.EmptyHandler;

import javax.annotation.Nullable;

public class CatBowlBlock extends BaseEntityBlock {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 4.0D, 16.0D, 5.0D, 12.0D);

    public CatBowlBlock() {
        super(Block.Properties.of(Material.METAL).strength(5.0F, 5.0F).sound(SoundType.METAL));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState blockState) {
        return new CatBowlBlockEntity(pos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, ModTileEntityTypes.CAT_BOWL.get(), CatBowlBlockEntity::tick);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext selectionContext) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        return Block.canSupportCenter(worldIn, pos.below(), Direction.UP);
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        CatBowlBlockEntity foodBowlTileEntity = WorldUtil.getTileEntity(worldIn, pos, CatBowlBlockEntity.class);

        if (foodBowlTileEntity != null) {
            foodBowlTileEntity.setPlacer(placer);
        }

        worldIn.setBlock(pos, state, Block.UPDATE_CLIENTS);
    }

    @Override
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof ItemEntity) {
            CatBowlBlockEntity foodBowl = WorldUtil.getTileEntity(worldIn, pos, CatBowlBlockEntity.class);

            if (foodBowl != null) {
                ItemEntity entityItem = (ItemEntity) entityIn;

                IItemHandler bowlInventory = foodBowl.getInventory();
                ItemStack remaining = InventoryUtil.addItem(bowlInventory, entityItem.getItem());
                if (!remaining.isEmpty()) {
                    entityItem.setItem(remaining);
                }
                else {
                    entityItem.discard();
                    worldIn.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.NEUTRAL, 0.25F, ((worldIn.random.nextFloat() - worldIn.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                }
            }
        }
    }

    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            CatBowlBlockEntity foodBowl = WorldUtil.getTileEntity(worldIn, pos, CatBowlBlockEntity.class);
            if (foodBowl != null) {
                IItemHandler bowlInventory = foodBowl.getInventory();
                for (int i = 0; i < bowlInventory.getSlots(); ++i) {
                    Containers.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), bowlInventory.getStackInSlot(i));
                }
                worldIn.updateNeighbourForOutputSignal(pos, this);
            }

            super.onRemove(state, worldIn, pos, newState, isMoving);
        }
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level worldIn, BlockPos pos) {
        CatBowlBlockEntity foodBowl = WorldUtil.getTileEntity(worldIn, pos, CatBowlBlockEntity.class);

        if (foodBowl != null) {
            IItemHandler bowlInventory = foodBowl.getInventory();
            return InventoryUtil.calcRedstoneFromInventory(bowlInventory);
        }

        return 0;
    }

    @Override
    public InteractionResult use(BlockState blockStateIn, Level worldIn, BlockPos posIn, Player playerIn, InteractionHand handIn, BlockHitResult result) {
        if (worldIn.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        else {
            CatBowlBlockEntity foodBowl = WorldUtil.getTileEntity(worldIn, posIn, CatBowlBlockEntity.class);

            if (foodBowl != null) {
                ItemStack stack = playerIn.getItemInHand(handIn);

                if (!stack.isEmpty() && stack.getItem() == ModItems.TREAT_BAG.get()) {
                    IItemHandler bagInventory = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(EmptyHandler.INSTANCE);
                    IItemHandler bowlInventory = foodBowl.getInventory();

                    InventoryUtil.transferStacks((IItemHandlerModifiable) bagInventory, bowlInventory);
                }
                else if (playerIn instanceof ServerPlayer && !(playerIn instanceof FakePlayer)) {
                    ServerPlayer serverPlayer = (ServerPlayer)playerIn;

                    Screens.openFoodBowlScreen(serverPlayer, foodBowl);
                }
            }

            return InteractionResult.SUCCESS;
        }
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.getValue(WATERLOGGED)) {
            worldIn.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }

        return facing == Direction.DOWN && !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState FluidState = context.getLevel().getFluidState(context.getClickedPos());

        return this.defaultBlockState().setValue(WATERLOGGED, Boolean.valueOf(FluidState.getType() == Fluids.WATER));
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }
}