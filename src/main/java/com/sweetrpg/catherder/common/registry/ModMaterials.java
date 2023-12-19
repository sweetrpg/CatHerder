package com.sweetrpg.catherder.common.registry;

import com.sweetrpg.catherder.api.impl.DyeMaterial;
import com.sweetrpg.catherder.api.impl.ColorMaterial;
import com.sweetrpg.catherder.api.impl.StructureMaterial;
import com.sweetrpg.catherder.api.registry.IDyeMaterial;
import com.sweetrpg.catherder.api.registry.IColorMaterial;
import com.sweetrpg.catherder.api.registry.IStructureMaterial;
import com.sweetrpg.catherder.common.lib.Constants;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModMaterials {
    // material registries
    public static final DeferredRegister<IStructureMaterial> STRUCTURES = DeferredRegister.create(ModRegistries.Keys.STRUCTURE_REGISTRY, Constants.VANILLA_ID);
    public static final DeferredRegister<IColorMaterial> COLORS = DeferredRegister.create(ModRegistries.Keys.COLOR_REGISTRY, Constants.VANILLA_ID);
    public static final DeferredRegister<IDyeMaterial> DYES = DeferredRegister.create(ModRegistries.Keys.DYE_REGISTRY, Constants.VANILLA_ID);

    // structures
    public static final RegistryObject<IStructureMaterial> OAK_PLANKS = registerStructure(Blocks.OAK_PLANKS.delegate);
    public static final RegistryObject<IStructureMaterial> SPRUCE_PLANKS = registerStructure(Blocks.SPRUCE_PLANKS.delegate);
    public static final RegistryObject<IStructureMaterial> BIRCH_PLANKS = registerStructure(Blocks.BIRCH_PLANKS.delegate);
    public static final RegistryObject<IStructureMaterial> JUNGLE_PLANKS = registerStructure(Blocks.JUNGLE_PLANKS.delegate);
    public static final RegistryObject<IStructureMaterial> ACACIA_PLANKS = registerStructure(Blocks.ACACIA_PLANKS.delegate);
    public static final RegistryObject<IStructureMaterial> DARK_OAK_PLANKS = registerStructure(Blocks.DARK_OAK_PLANKS.delegate);
    public static final RegistryObject<IStructureMaterial> CRIMSON_PLANKS = registerStructure(Blocks.CRIMSON_PLANKS.delegate);
    public static final RegistryObject<IStructureMaterial> WARPED_PLANKS = registerStructure(Blocks.WARPED_PLANKS.delegate);

    // colors
    public static final RegistryObject<IColorMaterial> WHITE_WOOL = registerColor(Blocks.WHITE_WOOL.delegate);
    public static final RegistryObject<IColorMaterial> ORANGE_WOOL = registerColor(Blocks.ORANGE_WOOL.delegate);
    public static final RegistryObject<IColorMaterial> MAGENTA_WOOL = registerColor(Blocks.MAGENTA_WOOL.delegate);
    public static final RegistryObject<IColorMaterial> LIGHT_BLUE_WOOL = registerColor(Blocks.LIGHT_BLUE_WOOL.delegate);
    public static final RegistryObject<IColorMaterial> YELLOW_WOOL = registerColor(Blocks.YELLOW_WOOL.delegate);
    public static final RegistryObject<IColorMaterial> LIME_WOOL = registerColor(Blocks.LIME_WOOL.delegate);
    public static final RegistryObject<IColorMaterial> PINK_WOOL = registerColor(Blocks.PINK_WOOL.delegate);
    public static final RegistryObject<IColorMaterial> GRAY_WOOL = registerColor(Blocks.GRAY_WOOL.delegate);
    public static final RegistryObject<IColorMaterial> LIGHT_GRAY_WOOL = registerColor(Blocks.LIGHT_GRAY_WOOL.delegate);
    public static final RegistryObject<IColorMaterial> CYAN_WOOL = registerColor(Blocks.CYAN_WOOL.delegate);
    public static final RegistryObject<IColorMaterial> PURPLE_WOOL = registerColor(Blocks.PURPLE_WOOL.delegate);
    public static final RegistryObject<IColorMaterial> BLUE_WOOL = registerColor(Blocks.BLUE_WOOL.delegate);
    public static final RegistryObject<IColorMaterial> BROWN_WOOL = registerColor(Blocks.BROWN_WOOL.delegate);
    public static final RegistryObject<IColorMaterial> GREEN_WOOL = registerColor(Blocks.GREEN_WOOL.delegate);
    public static final RegistryObject<IColorMaterial> RED_WOOL = registerColor(Blocks.RED_WOOL.delegate);
    public static final RegistryObject<IColorMaterial> BLACK_WOOL = registerColor(Blocks.BLACK_WOOL.delegate);
    
    // dyes
    public static final RegistryObject<IDyeMaterial> WHITE_DYE = registerDye(Items.WHITE_DYE.delegate);
    public static final RegistryObject<IDyeMaterial> ORANGE_DYE = registerDye(Items.ORANGE_DYE.delegate);
    public static final RegistryObject<IDyeMaterial> MAGENTA_DYE = registerDye(Items.MAGENTA_DYE.delegate);
    public static final RegistryObject<IDyeMaterial> LIGHT_BLUE_DYE = registerDye(Items.LIGHT_BLUE_DYE.delegate);
    public static final RegistryObject<IDyeMaterial> YELLOW_DYE = registerDye(Items.YELLOW_DYE.delegate);
    public static final RegistryObject<IDyeMaterial> LIME_DYE = registerDye(Items.LIME_DYE.delegate);
    public static final RegistryObject<IDyeMaterial> PINK_DYE = registerDye(Items.PINK_DYE.delegate);
    public static final RegistryObject<IDyeMaterial> GRAY_DYE = registerDye(Items.GRAY_DYE.delegate);
    public static final RegistryObject<IDyeMaterial> LIGHT_GRAY_DYE = registerDye(Items.LIGHT_GRAY_DYE.delegate);
    public static final RegistryObject<IDyeMaterial> CYAN_DYE = registerDye(Items.CYAN_DYE.delegate);
    public static final RegistryObject<IDyeMaterial> PURPLE_DYE = registerDye(Items.PURPLE_DYE.delegate);
    public static final RegistryObject<IDyeMaterial> BLUE_DYE = registerDye(Items.BLUE_DYE.delegate);
    public static final RegistryObject<IDyeMaterial> BROWN_DYE = registerDye(Items.BROWN_DYE.delegate);
    public static final RegistryObject<IDyeMaterial> GREEN_DYE = registerDye(Items.GREEN_DYE.delegate);
    public static final RegistryObject<IDyeMaterial> RED_DYE = registerDye(Items.RED_DYE.delegate);
    public static final RegistryObject<IDyeMaterial> BLACK_DYE = registerDye(Items.BLACK_DYE.delegate);

    private static RegistryObject<IStructureMaterial> registerStructure(final Supplier<Block> sup) {
        return STRUCTURES.register(sup.get().getRegistryName().getPath(), () -> new StructureMaterial(sup));
    }

    private static RegistryObject<IColorMaterial> registerColor(final Supplier<Block> sup) {
        return COLORS.register(sup.get().getRegistryName().getPath(), () -> new ColorMaterial(sup));
    }

    private static RegistryObject<IDyeMaterial> registerDye(final Supplier<Item> sup) {
        return DYES.register(sup.get().getRegistryName().getPath(), () -> new DyeMaterial(sup));
    }
}
