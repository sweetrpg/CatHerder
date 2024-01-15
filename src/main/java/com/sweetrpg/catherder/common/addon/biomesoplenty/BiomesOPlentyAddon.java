package com.sweetrpg.catherder.common.addon.biomesoplenty;

import com.google.common.collect.Lists;
import com.sweetrpg.catherder.api.CatHerderAPI;
import com.sweetrpg.catherder.api.impl.ColorMaterial;
import com.sweetrpg.catherder.api.impl.StructureMaterial;
import com.sweetrpg.catherder.api.registry.IStructureMaterial;
import com.sweetrpg.catherder.common.addon.Addon;
import com.sweetrpg.catherder.common.util.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collection;
import java.util.function.Supplier;

public class BiomesOPlentyAddon implements Addon {

    public static final String MOD_ID = "biomesoplenty";

    public static final String[] BLOCKS = {
            "cherry_planks", "umbran_planks",
            "fir_planks", "dead_planks", "magic_planks", "palm_planks", "redwood_planks",
            "willow_planks", "hellbark_planks", "jacaranda_planks", "mahogany_planks"
    };
    public static final DeferredRegister<IStructureMaterial> STRUCTURES = DeferredRegister.create(CatHerderAPI.RegistryKeys.STRUCTURE_REGISTRY, MOD_ID);

    @Override
    public void init() {
        if(!this.shouldLoad()) {
            return;
        }

        for(String block : BLOCKS) {
            ResourceLocation rl = Util.getResource(MOD_ID, block);
            Supplier<Block> blockGet = () -> ForgeRegistries.BLOCKS.getValue(rl);

            STRUCTURES.register(rl.getPath(), () -> new StructureMaterial(blockGet.get().builtInRegistryHolder()));
        }
    }

    @Override
    public Collection<String> getMods() {
        return Lists.newArrayList(MOD_ID);
    }

    @Override
    public String getName() {
        return "BiomesOPlenty Addon";
    }
}
