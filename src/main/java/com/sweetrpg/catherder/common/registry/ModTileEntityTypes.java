package com.sweetrpg.catherder.common.registry;

import com.sweetrpg.catherder.common.block.tileentity.CatTreeBlockEntity;
import com.sweetrpg.catherder.common.block.tileentity.CatBowlBlockEntity;
import com.sweetrpg.catherder.common.lib.Constants;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModTileEntityTypes {

    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Constants.MOD_ID);

    public static final RegistryObject<BlockEntityType<CatTreeBlockEntity>> CAT_TREE = register("cat_tree", CatTreeBlockEntity::new, ModBlocks.CAT_TREE);
    public static final RegistryObject<BlockEntityType<CatBowlBlockEntity>> CAT_BOWL = register("cat_bowl", CatBowlBlockEntity::new, ModBlocks.CAT_BOWL);

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(final String name, final BlockEntityType.BlockEntitySupplier<T> sup, Supplier<? extends Block> validBlock) {
        return register(name, () -> BlockEntityType.Builder.of(sup, validBlock.get()).build(null));
    }

    private static <T extends BlockEntityType<?>> RegistryObject<T> register(final String name, final Supplier<T> sup) {
        return TILE_ENTITIES.register(name, sup);
    }

}
