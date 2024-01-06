package com.sweetrpg.catherder.common.addon.autumnity;

import com.google.common.collect.Lists;
import com.sweetrpg.catherder.api.impl.StructureMaterial;
import com.sweetrpg.catherder.api.registry.IStructureMaterial;
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

public class AutumnityAddon implements Addon {

    public static final String MOD_ID = "autumnity";

    public static final String[] BLOCKS = {"maple_planks"};

    public final void registerStructures(final RegistryEvent.Register<IStructureMaterial> event) {
        if (!this.shouldLoad()) { return; }
        IForgeRegistry<IStructureMaterial> structureRegistry = event.getRegistry();

        for (String block : BLOCKS) {
            ResourceLocation rl = Util.getResource(MOD_ID, block);
            Supplier<Block> blockGet = () -> ForgeRegistries.BLOCKS.getValue(rl);

            structureRegistry.register(new StructureMaterial(blockGet).setRegistryName(rl));
        }
    }

    @Override
    public void init() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addGenericListener(IStructureMaterial.class, this::registerStructures);
    }

    @Override
    public Collection<String> getMods() {
        return Lists.newArrayList(MOD_ID);
    }

    @Override
    public String getName() {
        return "Autumnity Addon";
    }
}
