package com.sweetrpg.catherder.common.block;

import com.sweetrpg.catherder.common.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class MouseTrapBlock extends Block {

    public static final BooleanProperty SPRUNG = BlockStateProperties.TRIGGERED;

    public MouseTrapBlock() {
        super(Block.Properties.of(Material.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context)
                    .setValue(SPRUNG, true);
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
                        state.setValue(SPRUNG, false);
                    }
                }
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SPRUNG);
    }
}
