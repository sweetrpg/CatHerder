package com.sweetrpg.catherder.common.item;

import com.sweetrpg.catherder.common.entity.CatEntity;
import com.sweetrpg.catherder.common.registry.ModEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.Objects;

public class CatCharmItem extends Item {

    public CatCharmItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        if(world.isClientSide || !(world instanceof ServerLevel)) {
            return InteractionResult.SUCCESS;
        }
        else {
            Player player = context.getPlayer();
            ItemStack itemstack = context.getItemInHand();
            BlockPos blockpos = context.getClickedPos();
            Direction enumfacing = context.getClickedFace();
            BlockState iblockstate = world.getBlockState(blockpos);

            BlockPos blockpos1;
            if(iblockstate.getCollisionShape(world, blockpos).isEmpty()) {
                blockpos1 = blockpos;
            }
            else {
                blockpos1 = blockpos.relative(enumfacing);
            }

            Entity entity = ModEntityTypes.CAT.get()
                                              .spawn((ServerLevel) world, itemstack, context.getPlayer(), blockpos1, MobSpawnType.SPAWN_EGG, !Objects.equals(blockpos, blockpos1) && enumfacing == Direction.UP, false);
            if(entity instanceof CatEntity) {
                CatEntity cat = (CatEntity) entity;
                if(player != null) {
                    cat.setTame(true);
                    cat.setOwnerUUID(player.getUUID());
                }
                itemstack.shrink(1);
            }

            return InteractionResult.SUCCESS;
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if(worldIn.isClientSide || !(worldIn instanceof ServerLevel)) {
            return new InteractionResultHolder<>(InteractionResult.PASS, itemstack);
        }
        else {
            HitResult raytraceresult = Item.getPlayerPOVHitResult(worldIn, playerIn, ClipContext.Fluid.SOURCE_ONLY);
            if(raytraceresult != null && raytraceresult.getType() == HitResult.Type.BLOCK) {
                BlockPos blockpos = ((BlockHitResult) raytraceresult).getBlockPos();
                if(!(worldIn.getBlockState(blockpos).getBlock() instanceof LiquidBlock)) {
                    return new InteractionResultHolder<>(InteractionResult.PASS, itemstack);
                }
                else if(worldIn.mayInteract(playerIn, blockpos) && playerIn.mayUseItemAt(blockpos, ((BlockHitResult) raytraceresult).getDirection(), itemstack)) {
                    Entity entity = ModEntityTypes.CAT.get().spawn((ServerLevel) worldIn, itemstack, playerIn, blockpos, MobSpawnType.SPAWN_EGG, false, false);
                    if(entity instanceof CatEntity) {
                        CatEntity cat = (CatEntity) entity;
                        cat.setTame(true);
                        cat.setOwnerUUID(playerIn.getUUID());
                        itemstack.shrink(1);

                        playerIn.awardStat(Stats.ITEM_USED.get(this));
                        return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
                    }
                    else {
                        return new InteractionResultHolder<>(InteractionResult.PASS, itemstack);
                    }
                }
                else {
                    return new InteractionResultHolder<>(InteractionResult.FAIL, itemstack);
                }
            }
            else {
                return new InteractionResultHolder<>(InteractionResult.PASS, itemstack);
            }
        }
    }
}
