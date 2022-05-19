package com.sweetrpg.catherder.common.block;

import com.sweetrpg.catherder.CatHerder;
import com.sweetrpg.catherder.common.block.tileentity.CatTreeBlockEntity;
import com.sweetrpg.catherder.common.entity.CatEntity;
import com.sweetrpg.catherder.common.registry.ModEntityTypes;
import com.sweetrpg.catherder.common.storage.CatRespawnData;
import com.sweetrpg.catherder.common.storage.CatRespawnStorage;
import com.sweetrpg.catherder.common.util.EntityUtil;
import com.sweetrpg.catherder.common.util.NBTUtil;
import com.sweetrpg.catherder.common.util.WorldUtil;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class CatTreeBlock extends BaseEntityBlock {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 29.0D, 16.0D);
    protected static final VoxelShape SHAPE_OCCLUSION = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 29.0D, 16.0D);
    protected static final VoxelShape SHAPE_COLLISION = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 29.0D, 16.0D);

    public CatTreeBlock() {
        super(Block.Properties.of(Material.WOOD).strength(1.0F, 5.0F).sound(SoundType.WOOD));
        this.registerDefaultState(this.stateDefinition.any().setValue(BlockStateProperties.FACING, Direction.NORTH).setValue(WATERLOGGED, false));
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOcclusionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return SHAPE_OCCLUSION;
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext selectionContext) {
        return SHAPE_COLLISION;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState blockState) {
        return new CatTreeBlockEntity(pos, blockState);
    }

    @Nullable
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
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        state = state.setValue(BlockStateProperties.FACING, placer.getDirection().getOpposite());

        CatTreeBlockEntity catTreeTileEntity = WorldUtil.getTileEntity(worldIn, pos, CatTreeBlockEntity.class);

        if(catTreeTileEntity != null) {
//            CatTreeUtil.setBedVariant(catTreeTileEntity, stack);

            catTreeTileEntity.setPlacer(placer);
            CompoundTag tag = stack.getTagElement("catherder");
            if(tag != null) {
                Component name = NBTUtil.getTextComponent(tag, "name");
                UUID ownerId = NBTUtil.getUniqueId(tag, "ownerId");
                catTreeTileEntity.setBedName(name);
                catTreeTileEntity.setOwner(ownerId);
            }
        }

        worldIn.setBlock(pos, state, Block.UPDATE_CLIENTS);
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if(stateIn.getValue(WATERLOGGED)) {
            worldIn.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }

        return facing == Direction.DOWN && !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @SuppressWarnings("deprecation")
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(BlockStateProperties.FACING, rot.rotate(state.getValue(BlockStateProperties.FACING)));
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(BlockStateProperties.FACING)));
    }

    @SuppressWarnings("deprecation")
    @Override
    @Deprecated
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if(worldIn.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        else {
            CatTreeBlockEntity catTreeEntity = WorldUtil.getTileEntity(worldIn, pos, CatTreeBlockEntity.class);

            if(catTreeEntity != null) {
                ItemStack stack = player.getItemInHand(handIn);
                if(stack.getItem() == Items.NAME_TAG && stack.hasCustomHoverName()) {
                    catTreeEntity.setBedName(stack.getHoverName());

                    if (!player.getAbilities().instabuild) {
                        stack.shrink(1);
                    }

                    worldIn.sendBlockUpdated(pos, state, state, Block.UPDATE_ALL);
                    return InteractionResult.SUCCESS;
                }
                else if(player.isShiftKeyDown() && catTreeEntity.getOwnerUUID() == null) {
                    List<CatEntity> cats = worldIn.getEntities(ModEntityTypes.CAT.get(), new AABB(pos).inflate(10D), (cat) -> cat.isAlive() && cat.isOwnedBy(player));
                    Collections.sort(cats, new EntityUtil.Sorter(new Vec3(pos.getX(), pos.getY(), pos.getZ())));

                    CatEntity closestStanding = null;
                    CatEntity closestSitting = null;
                    for(CatEntity cat : cats) {
                        if(closestSitting != null && closestSitting != null) {
                            break;
                        }

                        if(closestSitting == null && cat.isInSittingPose()) {
                            closestSitting = cat;
                        }
                        else if(closestStanding == null && !cat.isInSittingPose()) {
                            closestStanding = cat;
                        }
                    }

                    CatEntity closests = closestStanding != null ? closestStanding : closestSitting;
                    if(closests != null) {
                        closests.setTargetBlock(pos);
                    }
                }
                else if(catTreeEntity.getOwnerUUID() != null) {
                    CatRespawnData storage = CatRespawnStorage.get(worldIn).remove(catTreeEntity.getOwnerUUID());

                    if(storage != null) {
                        CatEntity cat = storage.respawn((ServerLevel) worldIn, player, pos.above());

                        catTreeEntity.setOwner(cat);
                        cat.setBedPos(cat.level.dimension(), pos);
                        return InteractionResult.SUCCESS;
                    }
                    else {
                        Component name = catTreeEntity.getOwnerName();
                        player.sendMessage(new TranslatableComponent("block.catherder.cat_tree.owner", name != null ? name : "someone"), Util.NIL_UUID);
                        return InteractionResult.FAIL;
                    }
                }
                else {
                    player.sendMessage(new TranslatableComponent("block.catherder.cat_tree.set_owner_help"), Util.NIL_UUID);
                    return InteractionResult.SUCCESS;
                }
            }

            return InteractionResult.SUCCESS;
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);

//        Pair<ICasingMaterial, IBeddingMaterial> materials = CattreeUtil.getMaterials(stack);
//
//        tooltip.add(materials.getLeft() != null
//                ? materials.getLeft().getTooltip()
//                : new TranslatableComponent("cattree.casing.null").withStyle(ChatFormatting.RED));
//        tooltip.add(materials.getRight() != null
//                ? materials.getRight().getTooltip()
//                : new TranslatableComponent("cattree.bedding.null").withStyle(ChatFormatting.RED));
//
//        if (materials.getLeft() == null && materials.getRight() == null) {
//            tooltip.add(new TranslatableComponent("cattree.explain.missing").withStyle(ChatFormatting.ITALIC));
//        }
//
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

//    @Override
//    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
//        for (IBeddingMaterial beddingId : CatHerderAPI.BEDDING_MATERIAL.getValues()) {
//            for (ICasingMaterial casingId : CatHerderAPI.CASING_MATERIAL.getValues()) {
////                items.add(CattreeUtil.createItemStack(casingId, beddingId));
//            }
//        }
//    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
//        CattreeTileEntity cattreeTileEntity = WorldUtil.getTileEntity(world, pos, CattreeTileEntity.class);
//
//        if (cattreeTileEntity != null) {
//            return CattreeUtil.createItemStack(cattreeTileEntity.getCasing(), cattreeTileEntity.getBedding());
//        }

        CatHerder.LOGGER.debug("Unable to pick block on cat tree.");
        return ItemStack.EMPTY;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(BlockStateProperties.FACING, context.getNearestLookingDirection().getOpposite());
    }
}
