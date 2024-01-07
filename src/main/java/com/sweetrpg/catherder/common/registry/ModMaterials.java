package com.sweetrpg.catherder.common.registry;

import com.sweetrpg.catherder.api.CatHerderAPI;
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
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModMaterials {
    // material registries
    public static final DeferredRegister<IStructureMaterial> STRUCTURES = DeferredRegister.create(CatHerderAPI.RegistryKeys.STRUCTURE_REGISTRY, Constants.VANILLA_ID);
    public static final DeferredRegister<IColorMaterial> COLORS = DeferredRegister.create(CatHerderAPI.RegistryKeys.COLOR_REGISTRY, Constants.VANILLA_ID);
    public static final DeferredRegister<IDyeMaterial> DYES = DeferredRegister.create(CatHerderAPI.RegistryKeys.DYE_REGISTRY, Constants.VANILLA_ID);

    // structures
    public static final RegistryObject<IStructureMaterial> OAK_PLANKS = registerStructure(() -> Blocks.OAK_PLANKS);
    public static final RegistryObject<IStructureMaterial> SPRUCE_PLANKS = registerStructure(() -> Blocks.SPRUCE_PLANKS);
    public static final RegistryObject<IStructureMaterial> BIRCH_PLANKS = registerStructure(() -> Blocks.BIRCH_PLANKS);
    public static final RegistryObject<IStructureMaterial> JUNGLE_PLANKS = registerStructure(() -> Blocks.JUNGLE_PLANKS);
    public static final RegistryObject<IStructureMaterial> ACACIA_PLANKS = registerStructure(() -> Blocks.ACACIA_PLANKS);
    public static final RegistryObject<IStructureMaterial> DARK_OAK_PLANKS = registerStructure(() -> Blocks.DARK_OAK_PLANKS);
    public static final RegistryObject<IStructureMaterial> CRIMSON_PLANKS = registerStructure(() -> Blocks.CRIMSON_PLANKS);
    public static final RegistryObject<IStructureMaterial> WARPED_PLANKS = registerStructure(() -> Blocks.WARPED_PLANKS);

    // colors
    public static final RegistryObject<IColorMaterial> WHITE_WOOL = registerColor(() -> Blocks.WHITE_WOOL);
    public static final RegistryObject<IColorMaterial> ORANGE_WOOL = registerColor(() -> Blocks.ORANGE_WOOL);
    public static final RegistryObject<IColorMaterial> MAGENTA_WOOL = registerColor(() -> Blocks.MAGENTA_WOOL);
    public static final RegistryObject<IColorMaterial> LIGHT_BLUE_WOOL = registerColor(() -> Blocks.LIGHT_BLUE_WOOL);
    public static final RegistryObject<IColorMaterial> YELLOW_WOOL = registerColor(() -> Blocks.YELLOW_WOOL);
    public static final RegistryObject<IColorMaterial> LIME_WOOL = registerColor(() -> Blocks.LIME_WOOL);
    public static final RegistryObject<IColorMaterial> PINK_WOOL = registerColor(() -> Blocks.PINK_WOOL);
    public static final RegistryObject<IColorMaterial> GRAY_WOOL = registerColor(() -> Blocks.GRAY_WOOL);
    public static final RegistryObject<IColorMaterial> LIGHT_GRAY_WOOL = registerColor(() -> Blocks.LIGHT_GRAY_WOOL);
    public static final RegistryObject<IColorMaterial> CYAN_WOOL = registerColor(() -> Blocks.CYAN_WOOL);
    public static final RegistryObject<IColorMaterial> PURPLE_WOOL = registerColor(() -> Blocks.PURPLE_WOOL);
    public static final RegistryObject<IColorMaterial> BLUE_WOOL = registerColor(() -> Blocks.BLUE_WOOL);
    public static final RegistryObject<IColorMaterial> BROWN_WOOL = registerColor(() -> Blocks.BROWN_WOOL);
    public static final RegistryObject<IColorMaterial> GREEN_WOOL = registerColor(() -> Blocks.GREEN_WOOL);
    public static final RegistryObject<IColorMaterial> RED_WOOL = registerColor(() -> Blocks.RED_WOOL);
    public static final RegistryObject<IColorMaterial> BLACK_WOOL = registerColor(() -> Blocks.BLACK_WOOL);
    
    // dyes
    public static final RegistryObject<IDyeMaterial> WHITE_DYE = registerDye(() -> Items.WHITE_DYE);
    public static final RegistryObject<IDyeMaterial> ORANGE_DYE = registerDye(() -> Items.ORANGE_DYE);
    public static final RegistryObject<IDyeMaterial> MAGENTA_DYE = registerDye(() -> Items.MAGENTA_DYE);
    public static final RegistryObject<IDyeMaterial> LIGHT_BLUE_DYE = registerDye(() -> Items.LIGHT_BLUE_DYE);
    public static final RegistryObject<IDyeMaterial> YELLOW_DYE = registerDye(() -> Items.YELLOW_DYE);
    public static final RegistryObject<IDyeMaterial> LIME_DYE = registerDye(() -> Items.LIME_DYE);
    public static final RegistryObject<IDyeMaterial> PINK_DYE = registerDye(() -> Items.PINK_DYE);
    public static final RegistryObject<IDyeMaterial> GRAY_DYE = registerDye(() -> Items.GRAY_DYE);
    public static final RegistryObject<IDyeMaterial> LIGHT_GRAY_DYE = registerDye(() -> Items.LIGHT_GRAY_DYE);
    public static final RegistryObject<IDyeMaterial> CYAN_DYE = registerDye(() -> Items.CYAN_DYE);
    public static final RegistryObject<IDyeMaterial> PURPLE_DYE = registerDye(() -> Items.PURPLE_DYE);
    public static final RegistryObject<IDyeMaterial> BLUE_DYE = registerDye(() -> Items.BLUE_DYE);
    public static final RegistryObject<IDyeMaterial> BROWN_DYE = registerDye(() -> Items.BROWN_DYE);
    public static final RegistryObject<IDyeMaterial> GREEN_DYE = registerDye(() -> Items.GREEN_DYE);
    public static final RegistryObject<IDyeMaterial> RED_DYE = registerDye(() -> Items.RED_DYE);
    public static final RegistryObject<IDyeMaterial> BLACK_DYE = registerDye(() -> Items.BLACK_DYE);

    private static RegistryObject<IStructureMaterial> registerStructure(final Supplier<Block> sup) {
        return STRUCTURES.register(ForgeRegistries.BLOCKS.getKey(sup.get()).getPath(), () -> new StructureMaterial(sup.get().builtInRegistryHolder()));
    }

    private static RegistryObject<IColorMaterial> registerColor(final Supplier<Block> sup) {
        return COLORS.register(ForgeRegistries.BLOCKS.getKey(sup.get()).getPath(), () -> new ColorMaterial(sup.get().builtInRegistryHolder()));
    }

    private static RegistryObject<IDyeMaterial> registerDye(final Supplier<Item> sup) {
        return DYES.register(ForgeRegistries.ITEMS.getKey(sup.get()).getPath(), () -> new DyeMaterial(sup.get().builtInRegistryHolder()));
    }
}
