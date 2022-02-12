package kittytalents.common.addon.botania;

import com.google.common.collect.Lists;
import kittytalents.api.impl.CasingMaterial;
import kittytalents.api.registry.ICasingMaterial;
import kittytalents.common.addon.Addon;
import kittytalents.common.util.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Collection;
import java.util.function.Supplier;

public class BotaniaAddon implements Addon {

    public static final String MOD_ID = "botania";

    public static final String[] BLOCKS = {"livingwood_planks", "mossy_livingwood_planks",
            "dreamwood_planks", "mossy_dreamwood_planks", "shimmerwood_planks"};

    public final void registerCasings(final RegistryEvent.Register<ICasingMaterial> event) {
        if (!this.shouldLoad()) { return; }
        IForgeRegistry<ICasingMaterial> casingRegistry = event.getRegistry();

        for (String block : BLOCKS) {
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
        return "Botania Addon";
    }
}
