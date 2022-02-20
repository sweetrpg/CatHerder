package com.sweetrpg.catherder;

import com.sweetrpg.catherder.api.registry.Talent;
import com.sweetrpg.catherder.api.registry.TalentInstance;
import com.sweetrpg.catherder.client.ClientSetup;
import com.sweetrpg.catherder.data.*;
import com.sweetrpg.catherder.client.entity.render.world.BedFinderRenderer;
import com.sweetrpg.catherder.client.event.ClientEventHandler;
import com.sweetrpg.catherder.common.Capabilities;
import com.sweetrpg.catherder.common.CommonSetup;
import com.sweetrpg.catherder.common.addon.AddonManager;
import com.sweetrpg.catherder.common.command.CatRespawnCommand;
import com.sweetrpg.catherder.common.config.ConfigHandler;
import com.sweetrpg.catherder.common.event.EventHandler;
import com.sweetrpg.catherder.common.lib.Constants;
import com.sweetrpg.catherder.common.registry.*;
import com.sweetrpg.catherder.common.talent.*;
//import com.sweetrpg.catherder.common.util.BackwardsComp;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * @author Paulyhedral,ProPercivalalb
 */
@Mod(Constants.MOD_ID)
public class CatHerder {

    public static final DeferredRegister<Talent> TALENTS = DeferredRegister.create(Talent.class, Constants.MOD_ID);

    public static final RegistryObject<Talent> BED_FINDER = registerInst("bed_finder", BedFinderTalent::new);
    public static final RegistryObject<Talent> TOMCAT = registerInst("tomcat", TomcatTalent::new);
    public static final RegistryObject<Talent> CREEPER_SWEEPER = registerInst("creeper_sweeper", CreeperSweeperTalent::new);
    public static final RegistryObject<Talent> CHEETAH_SPEED = registerInst("cheetah_speed", CheetahSpeedTalent::new);
    public static final RegistryObject<Talent> FISHER_CAT = registerInst("fisher_cat", FisherCatTalent::new);
    public static final RegistryObject<Talent> CATLIKE_REFLEXES = registerInst("catlike_reflexes", CatlikeReflexesTalent::new);
    public static final RegistryObject<Talent> HAPPY_EATER = registerInst("happy_eater", HappyEaterTalent::new);
    public static final RegistryObject<Talent> HELL_BEAST = registerInst("hell_beast", HellBeastTalent::new);
    public static final RegistryObject<Talent> BIRDCATCHER = registerInst("birdcatcher", null);
    public static final RegistryObject<Talent> PACK_CAT = registerInst("pack_cat", PackCatTalent::new);
    public static final RegistryObject<Talent> PEST_FIGHTER = registerInst("pest_fighter", PestFighterTalent::new);
    //    public static final RegistryObject<Talent> PILLOW_PAW = registerInst("pillow_paw", PillowPawTalent::new);
    public static final RegistryObject<Talent> POISON_FANG = registerInst("poison_fang", PoisonFangTalent::new);
    public static final RegistryObject<Talent> NERMAL = registerInst("nermal", NermalTalent::new);
    public static final RegistryObject<Talent> QUICK_HEALER = registerInst("quick_healer", QuickHealerTalent::new);
    //public static final RegistryObject<Talent> RANGED_ATTACKER = registerInst("ranged_attacker", RangedAttacker::new);
    public static final RegistryObject<Talent> RESCUE_CAT = registerInst("rescue_cat", RescueCatTalent::new);
    public static final RegistryObject<Talent> ROARING_GALE = registerInst("roaring_gale", RoaringGaleTalent::new);
    //    public static final RegistryObject<Talent> SHEPHERD_CAT = registerInst("shepherd_cat", ShepherdCatTalent::new);
//    public static final RegistryObject<Talent> SWIMMER_CAT = registerInst("swimmer_cat", SwimmerCatTalent::new);
    public static final RegistryObject<Talent> MOUNT = registerInst("mount", MountTalent::new);
    public static final RegistryObject<Talent> CAT_TORCH = registerInst("cat_torch", CatTorchTalent::new);

    private static <T extends Talent> RegistryObject<Talent> registerInst(final String name, final BiFunction<Talent, Integer, TalentInstance> sup) {
        return register(name, () -> new Talent(sup));
    }

    private static <T extends Talent> RegistryObject<T> register(final String name, final Supplier<T> sup) {
        return TALENTS.register(name, sup);
    }

    public static final Logger LOGGER = LogManager.getLogger(Constants.MOD_ID);

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
        ModTileEntityTypes.TILE_ENTITIES.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModEntityTypes.ENTITIES.register(modEventBus);
        ModContainerTypes.CONTAINERS.register(modEventBus);
        ModSerializers.SERIALIZERS.register(modEventBus);
        ModSounds.SOUNDS.register(modEventBus);
        ModRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
        CatHerder.TALENTS.register(modEventBus);
        ModAccessories.ACCESSORIES.register(modEventBus);
        ModAccessoryTypes.ACCESSORY_TYPES.register(modEventBus);
        CatBedMaterials.BEDDINGS.register(modEventBus);
        CatBedMaterials.CASINGS.register(modEventBus);
        ModAttributes.ATTRIBUTES.register(modEventBus);

        modEventBus.addListener(ModRegistries::newRegistry);
        modEventBus.addListener(ModEntityTypes::addEntityAttributes);
        modEventBus.addListener(Capabilities::registerCaps);

        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;
        forgeEventBus.addListener(this::serverStarting);
        forgeEventBus.addListener(this::registerCommands);

        forgeEventBus.register(new EventHandler());
//        forgeEventBus.register(new BackwardsComp());

        // Client Events
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
            modEventBus.addListener(this::clientSetup);
            modEventBus.addListener(ModBlocks::registerBlockColours);
            modEventBus.addListener(ModItems::registerItemColours);
            modEventBus.addListener(ClientEventHandler::onModelBakeEvent);
            modEventBus.addListener(ClientSetup::setupTileEntityRenderers);
            modEventBus.addListener(ClientSetup::setupEntityRenderers);
            modEventBus.addListener(ClientSetup::addClientReloadListeners);
            forgeEventBus.register(new ClientEventHandler());
            forgeEventBus.addListener(BedFinderRenderer::onWorldRenderLast);
        });

        ConfigHandler.init(modEventBus);

        AddonManager.init();
    }

//    public void commonSetup(final FMLCommonSetupEvent event) {
//    }

    public void serverStarting(final ServerStartingEvent event) {

    }

    public void registerCommands(final RegisterCommandsEvent event) {
        CatRespawnCommand.register(event.getDispatcher());
    }

    @OnlyIn(Dist.CLIENT)
    public void clientSetup(final FMLClientSetupEvent event) {
        ClientSetup.setupScreenManagers(event);

        ClientSetup.setupCollarRenderers(event);
    }

    protected void interModProcess(final InterModProcessEvent event) {
//        BackwardsComp.init();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        AddonManager.init();
    }

    private void gatherData(final GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();

        if (event.includeClient()) {
            CHBlockstateProvider blockstates = new CHBlockstateProvider(gen, event.getExistingFileHelper());
            gen.addProvider(blockstates);
            gen.addProvider(new CHItemModelProvider(gen, blockstates.getExistingHelper()));
            gen.addProvider(new CHLangProvider(gen, Constants.LOCALE_EN_US));
            gen.addProvider(new CHLangProvider(gen, Constants.LOCALE_EN_GB));
            gen.addProvider(new CHLangProvider(gen, Constants.LOCALE_DE_DE));
            gen.addProvider(new CHLangProvider(gen, Constants.LOCALE_KO_KR));
            gen.addProvider(new CHLangProvider(gen, Constants.LOCALE_RU_RU));
            gen.addProvider(new CHLangProvider(gen, Constants.LOCALE_VI_VN));
            gen.addProvider(new CHLangProvider(gen, Constants.LOCALE_ZH_CN));
            gen.addProvider(new CHLangProvider(gen, Constants.LOCALE_ZH_TW));
        }

        if (event.includeServer()) {
            // gen.addProvider(new DTBlockTagsProvider(gen));
            gen.addProvider(new CHAdvancementProvider(gen));
            CHBlockTagsProvider blockTagProvider = new CHBlockTagsProvider(gen, event.getExistingFileHelper());
            gen.addProvider(blockTagProvider);
            gen.addProvider(new CHItemTagsProvider(gen, blockTagProvider, event.getExistingFileHelper()));
            gen.addProvider(new CHRecipeProvider(gen));
            gen.addProvider(new CHLootTableProvider(gen));
        }
    }
}
