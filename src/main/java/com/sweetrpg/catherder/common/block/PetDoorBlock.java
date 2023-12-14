package com.sweetrpg.catherder.common.block;

import com.sweetrpg.catherder.api.CatHerderAPI;
import com.sweetrpg.catherder.api.registry.IColorMaterial;
import com.sweetrpg.catherder.api.registry.IStructureMaterial;
import com.sweetrpg.catherder.common.registry.ModItems;
import com.sweetrpg.catherder.common.util.CatTreeUtil;
import com.sweetrpg.catherder.common.util.NBTUtil;
import com.sweetrpg.catherder.common.util.PetDoorUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class PetDoorBlock extends Block {

    protected static final VoxelShape UNLOCKED_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
    protected static final VoxelShape LOCKED_SHAPE_NORTH_SOUTH = Block.box(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
    protected static final VoxelShape LOCKED_SHAPE_EAST_WEST = Block.box(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);

//    public enum Type {
//        OAK,
//        DARK_OAK,
//        BIRCH,
//        ACACIA,
//        SPRUCE,
//        JUNGLE,
//        CRIMSON,
//        WARPED,
//        // TODO: other wood types (BOP, etc.)
//    }

//    private Type type;

    public PetDoorBlock() {
        super(Block.Properties.of(Material.WOOD).strength(1.0F, 5.0F).sound(SoundType.WOOD));
//        this.type = type;
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
//        for (IBeddingMaterial beddingId : CatHerderAPI.BEDDING_MATERIAL.getValues()) {
        for (IStructureMaterial structureId : CatHerderAPI.STRUCTURE_MATERIAL.get().getValues()) {
            items.add(PetDoorUtil.createItemStack(structureId));
        }
//        }
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
