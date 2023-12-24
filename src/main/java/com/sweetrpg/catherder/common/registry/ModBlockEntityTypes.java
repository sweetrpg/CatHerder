package com.sweetrpg.catherder.common.registry;

import com.sweetrpg.catherder.api.CatHerderAPI;
import com.sweetrpg.catherder.common.block.entity.CatTreeBlockEntity;
import com.sweetrpg.catherder.common.block.entity.CatBowlBlockEntity;
import com.sweetrpg.catherder.common.block.entity.LitterboxBlockEntity;
import com.sweetrpg.catherder.common.block.entity.PetDoorBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlockEntityTypes {

    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.Keys.BLOCK_ENTITY_TYPES, CatHerderAPI.MOD_ID);

    public static final RegistryObject<BlockEntityType<CatTreeBlockEntity>> CAT_TREE = register("cat_tree", CatTreeBlockEntity::new, ModBlocks.CAT_TREE);
    public static final RegistryObject<BlockEntityType<PetDoorBlockEntity>> PET_DOOR = register("pet_door", PetDoorBlockEntity::new, ModBlocks.PET_DOOR);

    public static final RegistryObject<BlockEntityType<CatBowlBlockEntity>> CAT_BOWL = register("cat_bowl", CatBowlBlockEntity::new, ModBlocks.CAT_BOWL);
    public static final RegistryObject<BlockEntityType<LitterboxBlockEntity>> LITTERBOX = register("litter_box", LitterboxBlockEntity::new, ModBlocks.LITTERBOX);

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(final String name, final BlockEntityType.BlockEntitySupplier<T> sup, Supplier<? extends Block> validBlock) {
        return register(name, () -> BlockEntityType.Builder.of(sup, validBlock.get()).build(null));
    }

    private static <T extends BlockEntityType<?>> RegistryObject<T> register(final String name, final Supplier<T> sup) {
        return TILE_ENTITIES.register(name, sup);
    }

}
