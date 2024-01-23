package com.sweetrpg.catherder;

import com.sweetrpg.catherder.api.CatHerderAPI;
import com.sweetrpg.catherder.client.ClientSetup;
import com.sweetrpg.catherder.client.entity.render.world.BedFinderRenderer;
import com.sweetrpg.catherder.client.event.ClientEventHandler;
import com.sweetrpg.catherder.common.lib.Capabilities;
import com.sweetrpg.catherder.common.CommonSetup;
import com.sweetrpg.catherder.common.addon.AddonManager;
import com.sweetrpg.catherder.common.config.ConfigHandler;
import com.sweetrpg.catherder.common.event.EventHandler;
import com.sweetrpg.catherder.common.lib.Constants;
import com.sweetrpg.catherder.common.registry.*;
import com.sweetrpg.catherder.common.util.BackwardsComp;
import com.sweetrpg.catherder.common.world.WildCropGeneration;
import com.sweetrpg.catherder.data.*;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Paulyhedral, ProPercivalalb
 */
@Mod(CatHerderAPI.MOD_ID)
public class CatHerder {

    public static final Logger LOGGER = LogManager.getLogger(CatHerderAPI.MOD_ID);

    public static final SimpleChannel HANDLER = NetworkRegistry.ChannelBuilder.named(Constants.CHANNEL_NAME)
            .clientAcceptedVersions(Constants.PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(Constants.PROTOCOL_VERSION::equals)
            .networkProtocolVersion(Constants.PROTOCOL_VERSION::toString)
            .simpleChannel();

    public CatHerder() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Mod lifecycle
        modEventBus.addListener(this::gatherData);
        modEventBus.addListener(CommonSetup::init);
        modEventBus.addListener(this::interModProcess);

        // Registries
        ModBlocks.BLOCKS.register(modEventBus);
        ModBlockEntityTypes.BLOCK_ENTITIES.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModEntityTypes.ENTITIES.register(modEventBus);
        ModContainerTypes.CONTAINERS.register(modEventBus);
        ModSerializers.SERIALIZERS.register(modEventBus);
        ModSounds.SOUNDS.register(modEventBus);
        ModRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
        ModTalents.TALENTS.register(modEventBus);
        ModAccessories.ACCESSORIES.register(modEventBus);
        ModAccessoryTypes.ACCESSORY_TYPES.register(modEventBus);
        ModMaterials.STRUCTURES.register(modEventBus);
        ModMaterials.COLORS.register(modEventBus);
        ModMaterials.DYES.register(modEventBus);
        ModAttributes.ATTRIBUTES.register(modEventBus);

        modEventBus.addListener(ModItemGroups::onCreativeTabRegister);

        ModPlacementModifiers.PLACEMENT_MODIFIERS.register(modEventBus);
        ModBiomeModifiers.BIOME_MODIFIER_SERIALIZERS.register(modEventBus);
        ModLootFunctions.LOOT_FUNCTIONS.register(modEventBus);
        ModLootModifiers.LOOT_MODIFIERS.register(modEventBus);

        WildCropGeneration.load();

        modEventBus.addListener(ModRegistries::newRegistry);
        modEventBus.addListener(ModEntityTypes::addEntityAttributes);
        modEventBus.addListener(Capabilities::registerCaps);

        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;
        forgeEventBus.addListener(this::serverStarting);
        forgeEventBus.addListener(this::registerCommands);

        forgeEventBus.register(new EventHandler());
        forgeEventBus.register(new BackwardsComp());

        // Client Events
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
            modEventBus.addListener(this::clientSetup);
            modEventBus.addListener(ModBlocks::registerBlockColours);
            modEventBus.addListener(ModItems::registerItemColours);
            modEventBus.addListener(ClientEventHandler::registerModelForBaking);
            modEventBus.addListener(ClientEventHandler::modifyBakedModels);
            modEventBus.addListener(ClientSetup::setupTileEntityRenderers);
            modEventBus.addListener(ClientSetup::setupEntityRenderers);
            modEventBus.addListener(ClientSetup::addClientReloadListeners);
            modEventBus.addListener(ClientSetup::registerOverlays);

            forgeEventBus.register(new ClientEventHandler());
            forgeEventBus.addListener(BedFinderRenderer::onWorldRenderLast);
        });

        ConfigHandler.init(modEventBus);

        AddonManager.init();
    }

//    public void commonSetup(final FMLCommonSetupEvent event) {
//    }

    public void serverStarting(final ServerStartingEvent event) {
        LOGGER.debug("Server starting");
    }

    public void registerCommands(final RegisterCommandsEvent event) {
        LOGGER.debug("Register commands");
//        CatRespawnCommand.register(event.getDispatcher());
    }

    @OnlyIn(Dist.CLIENT)
    public void clientSetup(final FMLClientSetupEvent event) {
        LOGGER.debug("Client startup");

        ClientSetup.setupScreenManagers(event);

        ClientSetup.setupCollarRenderers(event);
    }

    protected void interModProcess(final InterModProcessEvent event) {
        LOGGER.debug("event {}", event);

                BackwardsComp.init();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

//        AddonManager.init();
    }

    private void gatherData(final GatherDataEvent event) {
        LOGGER.debug("Gather data: {}", event);

        DataGenerator gen = event.getGenerator();
        var packOutput = gen.getPackOutput();
        var lookup = event.getLookupProvider();
        var fileHelper = event.getExistingFileHelper();

        if(event.includeClient()) {
            CHBlockstateProvider blockstates = new CHBlockstateProvider(packOutput, event.getExistingFileHelper());
            gen.addProvider(true, blockstates);
            gen.addProvider(true, new CHItemModelProvider(packOutput, blockstates.getExistingHelper()));
            gen.addProvider(true, new CHLangProvider(packOutput, Constants.LOCALE_EN_US));
            gen.addProvider(true, new CHLangProvider(packOutput, Constants.LOCALE_EN_GB));
            gen.addProvider(true, new CHLangProvider(packOutput, Constants.LOCALE_DE_DE));
            gen.addProvider(true, new CHLangProvider(packOutput, Constants.LOCALE_KO_KR));
            gen.addProvider(true, new CHLangProvider(packOutput, Constants.LOCALE_RU_RU));
            gen.addProvider(true, new CHLangProvider(packOutput, Constants.LOCALE_VI_VN));
            gen.addProvider(true, new CHLangProvider(packOutput, Constants.LOCALE_ZH_CN));
            gen.addProvider(true, new CHLangProvider(packOutput, Constants.LOCALE_ZH_TW));
        }

        if(event.includeServer()) {
            // gen.addProvider(new DTBlockTagsProvider(gen));
            gen.addProvider(true, new CHAdvancements(packOutput, lookup, fileHelper));
            CHBlockTagsProvider blockTagProvider = new CHBlockTagsProvider(packOutput, lookup, fileHelper);
            gen.addProvider(true, blockTagProvider);
            gen.addProvider(true, new CHItemTagsProvider(packOutput, lookup, blockTagProvider, event.getExistingFileHelper()));
            gen.addProvider(true, new CHRecipeProvider(packOutput));
            gen.addProvider(true, new CHLootTableProvider(packOutput));
        }
    }
}
