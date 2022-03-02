package com.sweetrpg.catherder.common.addon.biomesoplenty;

import com.google.common.collect.Lists;
import com.sweetrpg.catherder.api.impl.CasingMaterial;
import com.sweetrpg.catherder.api.registry.ICasingMaterial;
import com.sweetrpg.catherder.common.addon.Addon;
import com.sweetrpg.catherder.common.util.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Collection;
import java.util.function.Supplier;

public class BiomesOPlentyAddon implements Addon {

    public static final String MOD_ID = "biomesoplenty";

    public static final String[] BLOCKS = {
            "cherry_planks", "umbran_planks",
            "fir_planks", "dead_planks", "magic_planks", "palm_planks", "redwood_planks",
            "willow_planks", "hellbark_planks", "jacaranda_planks", "mahogany_planks"
    };

    public final void registerCasings(final RegistryEvent.Register<ICasingMaterial> event) {
        if(!this.shouldLoad()) {return;}

        IForgeRegistry<ICasingMaterial> casingRegistry = event.getRegistry();

        for(String block : BLOCKS) {
            ResourceLocation rl = Util.getResource(MOD_ID, block);
            Supplier<Block> blockGet = () -> ForgeRegistries.BLOCKS.getValue(rl);

            casingRegistry.register(new CasingMaterial(blockGet).setRegistryName(rl));
        }
    }

    @Override
    public void init() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addGenericListener(ICasingMaterial.class, this::registerCasings);
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
