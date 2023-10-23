package com.sweetrpg.catherder.common.addon.botania;

import com.google.common.collect.Lists;
import com.sweetrpg.catherder.api.CatHerderAPI;
import com.sweetrpg.catherder.api.impl.CasingMaterial;
import com.sweetrpg.catherder.api.registry.ICasingMaterial;
import com.sweetrpg.catherder.common.addon.Addon;
import com.sweetrpg.catherder.common.util.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Collection;
import java.util.function.Supplier;

public class BotaniaAddon implements Addon {

    public static final String MOD_ID = "botania";

    public static final String[] BLOCKS = {"livingwood_planks", "mossy_livingwood_planks",
            "dreamwood_planks", "mossy_dreamwood_planks", "shimmerwood_planks"};
    public static final DeferredRegister<ICasingMaterial> CASINGS = DeferredRegister.create(CatHerderAPI.RegistryKeys.CASING_REGISTRY, MOD_ID);

    @Override
    public void init() {
        if (!this.shouldLoad()) { return; }

        for (String block : BLOCKS) {
            ResourceLocation rl = Util.getResource(MOD_ID, block);
            Supplier<Block> blockGet = () -> ForgeRegistries.BLOCKS.getValue(rl);

            CASINGS.register(rl.getPath(), () -> new CasingMaterial(blockGet.get().builtInRegistryHolder()));
        }

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.register(CASINGS);
    }
    @Override
    public Collection<String> getMods() {
        return Lists.newArrayList(MOD_ID);
    }

    @Override
    public String getName() {
        return "Botania Addon";
    }
}
