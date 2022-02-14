package com.sweetrpg.catherder.common.block;

import com.sweetrpg.catherder.CatEntityTypes;
import com.sweetrpg.catherder.CatHerder;
import com.sweetrpg.catherder.api.CatHerderAPI;
import com.sweetrpg.catherder.api.registry.IBeddingMaterial;
import com.sweetrpg.catherder.api.registry.ICasingMaterial;
import com.sweetrpg.catherder.common.block.tileentity.CatBedTileEntity;
import com.sweetrpg.catherder.common.storage.CatRespawnData;
import com.sweetrpg.catherder.common.storage.CatRespawnStorage;
import com.sweetrpg.catherder.common.util.CatBedUtil;
import com.sweetrpg.catherder.common.util.EntityUtil;
import com.sweetrpg.catherder.common.util.NBTUtil;
import com.sweetrpg.catherder.common.util.WorldUtil;
import com.sweetrpg.catherder.common.entity.CatEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
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
import net.minecraft.world.level.block.state.properties.DirectionProperty;
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
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class CatBedBlock extends BaseEntityBlock {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D);
    protected static final VoxelShape SHAPE_COLLISION = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D);

    public CatBedBlock() {
        super(Block.Properties.of(Material.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext selectionContext) {
        return SHAPE_COLLISION;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState blockState) {
        return new CatBedTileEntity(pos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return null;
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        return Block.canSupportCenter(worldIn, pos.below(), Direction.UP);
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        state = state.setValue(FACING, placer.getDirection().getOpposite());

        CatBedTileEntity catBedTileEntity = WorldUtil.getTileEntity(worldIn, pos, CatBedTileEntity.class);

        if (catBedTileEntity != null) {
            CatBedUtil.setBedVariant(catBedTileEntity, stack);

            catBedTileEntity.setPlacer(placer);
            CompoundTag tag = stack.getTagElement("catherder");
            if (tag != null) {
                Component name = NBTUtil.getTextComponent(tag, "name");
                UUID ownerId = NBTUtil.getUniqueId(tag, "ownerId");
                catBedTileEntity.setBedName(name);
                catBedTileEntity.setOwner(ownerId);
            }
        }

        worldIn.setBlock(pos, state, Block.UPDATE_CLIENTS);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.getValue(WATERLOGGED)) {
            worldIn.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }
        return facing == Direction.DOWN && !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    @Override
    @Deprecated
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (worldIn.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            CatBedTileEntity catBedTileEntity = WorldUtil.getTileEntity(worldIn, pos, CatBedTileEntity.class);

            if (catBedTileEntity != null) {

                ItemStack stack = player.getItemInHand(handIn);
                if (stack.getItem() == Items.NAME_TAG && stack.hasCustomHoverName()) {
                    catBedTileEntity.setBedName(stack.getHoverName());

                    if (!player.getAbilities().instabuild) {
                        stack.shrink(1);
                    }

                    worldIn.sendBlockUpdated(pos, state, state, Block.UPDATE_ALL);
                    return InteractionResult.SUCCESS;
                } else if (player.isShiftKeyDown() && catBedTileEntity.getOwnerUUID() == null) {
                    List<CatEntity> dogs = worldIn.getEntities(CatEntityTypes.CAT.get(), new AABB(pos).inflate(10D), (cat) -> cat.isAlive() && cat.isOwnedBy(player));
                    Collections.sort(dogs, new EntityUtil.Sorter(new Vec3(pos.getX(), pos.getY(), pos.getZ())));

                    CatEntity closestStanding = null;
                    CatEntity closestSitting = null;
                    for (CatEntity cat : dogs) {
                        if (closestSitting != null && closestSitting != null) {
                            break;
                        }

                        if (closestSitting == null && cat.isInSittingPose()) {
                            closestSitting = cat;
                        } else if (closestStanding == null && !cat.isInSittingPose()) {
                            closestStanding = cat;
                        }
                    }

                    CatEntity closests = closestStanding != null ? closestStanding : closestSitting;
                    if (closests != null) {
                        closests.setTargetBlock(pos);
                    }
                } else if (catBedTileEntity.getOwnerUUID() != null) {
                    CatRespawnData storage = CatRespawnStorage.get(worldIn).remove(catBedTileEntity.getOwnerUUID());

                    if (storage != null) {
                        CatEntity cat = storage.respawn((ServerLevel) worldIn, player, pos.above());

                        catBedTileEntity.setOwner(cat);
                        cat.setBedPos(cat.level.dimension(), pos);
                        return InteractionResult.SUCCESS;
                    } else {
                        Component name = catBedTileEntity.getOwnerName();
                        player.sendMessage(new TranslatableComponent("block.catherder.cat_bed.owner", name != null ? name : "someone"), Util.NIL_UUID);
                        return InteractionResult.FAIL;
                    }
                } else {
                    player.sendMessage(new TranslatableComponent("block.catherder.cat_bed.set_owner_help"), Util.NIL_UUID);
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

        Pair<ICasingMaterial, IBeddingMaterial> materials = CatBedUtil.getMaterials(stack);

        tooltip.add(materials.getLeft() != null
                ? materials.getLeft().getTooltip()
                : new TranslatableComponent("catbed.casing.null").withStyle(ChatFormatting.RED));
        tooltip.add(materials.getRight() != null
                ? materials.getRight().getTooltip()
                : new TranslatableComponent("catbed.bedding.null").withStyle(ChatFormatting.RED));

        if (materials.getLeft() == null && materials.getRight() == null) {
            tooltip.add(new TranslatableComponent("catbed.explain.missing").withStyle(ChatFormatting.ITALIC));
        }

        CompoundTag tag = stack.getTagElement("catherder");
        if (tag != null) {
            UUID ownerId = NBTUtil.getUniqueId(tag, "ownerId");
            Component name = NBTUtil.getTextComponent(tag, "name");
            Component ownerName = NBTUtil.getTextComponent(tag, "ownerName");

            if (name != null) {
                tooltip.add(new TextComponent("Bed Name: ").withStyle(ChatFormatting.WHITE).append(name));
            }

            if (ownerName != null) {
                tooltip.add(new TextComponent("Name: ").withStyle(ChatFormatting.DARK_AQUA).append(ownerName));

            }

            if (ownerId != null && (flagIn.isAdvanced() || Screen.hasShiftDown())) {
                tooltip.add(new TextComponent("UUID: ").withStyle(ChatFormatting.AQUA).append(new TextComponent(ownerId.toString())));
            }
        }
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        for (IBeddingMaterial beddingId : CatHerderAPI.BEDDING_MATERIAL.getValues()) {
            for (ICasingMaterial casingId : CatHerderAPI.CASING_MATERIAL.getValues()) {
                items.add(CatBedUtil.createItemStack(casingId, beddingId));
            }
        }
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        CatBedTileEntity catBedTileEntity = WorldUtil.getTileEntity(world, pos, CatBedTileEntity.class);

        if (catBedTileEntity != null) {
            return CatBedUtil.createItemStack(catBedTileEntity.getCasing(), catBedTileEntity.getBedding());
        }

        CatHerder.LOGGER.debug("Unable to pick block on cat bed.");
        return ItemStack.EMPTY;
    }
}
