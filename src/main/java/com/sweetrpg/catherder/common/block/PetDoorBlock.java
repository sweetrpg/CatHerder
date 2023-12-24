package com.sweetrpg.catherder.common.block;

import com.sweetrpg.catherder.CatHerder;
import com.sweetrpg.catherder.api.CatHerderAPI;
import com.sweetrpg.catherder.api.registry.IStructureMaterial;
import com.sweetrpg.catherder.common.block.entity.PetDoorBlockEntity;
import com.sweetrpg.catherder.common.util.PetDoorUtil;
import com.sweetrpg.catherder.common.util.WorldUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
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
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PetDoorBlock extends BaseEntityBlock {

    protected static final VoxelShape UNLOCKED_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
    protected static final VoxelShape LOCKED_SHAPE_NORTH_SOUTH = Block.box(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
    protected static final VoxelShape LOCKED_SHAPE_EAST_WEST = Block.box(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);

    public PetDoorBlock() {
        super(Block.Properties.of(Material.WOOD).strength(1.0F, 5.0F).sound(SoundType.WOOD));
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH)
                .setValue(BlockStateProperties.WATERLOGGED, false));
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return getOcclusionShape(state, level, pos);
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
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext selectionContext) {
        if(state.getValue(BlockStateProperties.LOCKED)) {
            return switch(state.getValue(BlockStateProperties.HORIZONTAL_FACING)) {
                case UP, DOWN, NORTH, SOUTH -> LOCKED_SHAPE_NORTH_SOUTH;
                case WEST, EAST -> LOCKED_SHAPE_EAST_WEST;
            };
        }

        return UNLOCKED_SHAPE;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState blockState) {
        return new PetDoorBlockEntity(pos, blockState);
    }

    @javax.annotation.Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return null;
    }

    @SuppressWarnings("deprecation")
    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        return Block.canSupportCenter(worldIn, pos.below(), Direction.UP);
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @javax.annotation.Nullable LivingEntity placer, ItemStack stack) {
        state = state.setValue(BlockStateProperties.HORIZONTAL_FACING, placer.getDirection().getOpposite());

        PetDoorBlockEntity petDoorBlockEntity = WorldUtil.getBlockEntity(worldIn, pos, PetDoorBlockEntity.class);

        if(petDoorBlockEntity != null) {
            PetDoorUtil.setDoorVariant(petDoorBlockEntity, stack);

            petDoorBlockEntity.setPlacer(placer);
//            CompoundTag tag = stack.getTagElement("catherder");
//            if(tag != null) {
//                Component name = NBTUtil.getTextComponent(tag, "name");
//                UUID ownerId = NBTUtil.getUniqueId(tag, "ownerId");
//                petDoorBlockEntity.setBedName(name);
//                petDoorBlockEntity.setOwner(ownerId);
//            }
        }

        worldIn.setBlock(pos, state, Block.UPDATE_CLIENTS);
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if(stateIn.getValue(BlockStateProperties.WATERLOGGED)) {
            worldIn.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }

        return facing == Direction.DOWN && !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @SuppressWarnings("deprecation")
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(BlockStateProperties.HORIZONTAL_FACING, rot.rotate(state.getValue(BlockStateProperties.HORIZONTAL_FACING)));
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(BlockStateProperties.HORIZONTAL_FACING)));
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
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @javax.annotation.Nullable BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);

        IStructureMaterial structureMaterial = PetDoorUtil.getStructureMaterial(stack);

        tooltip.add(structureMaterial != null
                ? structureMaterial.getTooltip()
                : new TranslatableComponent("petdoor.structure.null").withStyle(ChatFormatting.RED));

        if (structureMaterial == null) {
            tooltip.add(new TranslatableComponent("petdoor.explain.missing").withStyle(ChatFormatting.ITALIC));
        }

//        CompoundTag tag = stack.getTagElement("catherder");
//        if (tag != null) {
//            UUID ownerId = NBTUtil.getUniqueId(tag, "ownerId");
//            Component name = NBTUtil.getTextComponent(tag, "name");
//            Component ownerName = NBTUtil.getTextComponent(tag, "ownerName");
//
//            if (name != null) {
//                tooltip.add(new TextComponent("Bed Name: ").withStyle(ChatFormatting.WHITE).append(name));
//            }
//
//            if (ownerName != null) {
//                tooltip.add(new TextComponent("Name: ").withStyle(ChatFormatting.DARK_AQUA).append(ownerName));
//
//            }
//
//            if (ownerId != null && (flagIn.isAdvanced() || Screen.hasShiftDown())) {
//                tooltip.add(new TextComponent("UUID: ").withStyle(ChatFormatting.AQUA).append(new TextComponent(ownerId.toString())));
//            }
//        }
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        for (IStructureMaterial structureId : CatHerderAPI.STRUCTURE_MATERIAL.get().getValues()) {
            items.add(PetDoorUtil.createItemStack(structureId));
        }
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        PetDoorBlockEntity petDoorBlockEntity = WorldUtil.getBlockEntity(world, pos, PetDoorBlockEntity.class);

        if (petDoorBlockEntity != null) {
            return PetDoorUtil.createItemStack(petDoorBlockEntity.getStructure());
        }

        CatHerder.LOGGER.debug("Unable to pick block on pet door.");
        return ItemStack.EMPTY;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context)
                       .setValue(BlockStateProperties.LOCKED, false)
                       .setValue(BlockStateProperties.HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.WATERLOGGED, BlockStateProperties.LOCKED);
    }
}
