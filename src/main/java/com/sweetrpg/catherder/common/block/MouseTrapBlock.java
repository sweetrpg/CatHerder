package com.sweetrpg.catherder.common.block;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;

public class MouseTrapBlock extends Block {

    public static final BooleanProperty SPRUNG = BlockStateProperties.TRIGGERED;

    public MouseTrapBlock() {
        super(Block.Properties.of(Material.METAL).strength(3.0F, 5.0F).sound(SoundType.METAL));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return super.getStateForPlacement(pContext)
                    .setValue(SPRUNG, false);
    }
}
