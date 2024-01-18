package com.sweetrpg.catherder.common;

import com.sweetrpg.catherder.CatHerder;
import com.sweetrpg.catherder.api.feature.FoodHandler;
import com.sweetrpg.catherder.common.config.ConfigHandler;
import com.sweetrpg.catherder.common.entity.CatEntity;
import com.sweetrpg.catherder.common.event.FishFoodHandler;
import com.sweetrpg.catherder.common.network.PacketHandler;
import com.sweetrpg.catherder.common.registry.ModItems;
import com.sweetrpg.catherder.common.talent.HappyEaterTalent;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class CommonSetup {
    public static void init(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            CatHerder.LOGGER.info("CommonSetup.init starting");

            PacketHandler.init();
            //TODO CriteriaTriggers.register(criterion)

//            FoodHandler.registerHandler(new MeatFoodHandler());
            FoodHandler.registerHandler(new FishFoodHandler());
            FoodHandler.registerDynPredicate(HappyEaterTalent.INNER_DYN_PRED);

//            InteractHandler.registerHandler(new HelmetInteractHandler());
            ConfigHandler.initTalentConfig();
//            CatRespawnCommand.registerSerilizers(FMLJavaModLoadingContext.get().getModEventBus());
            CatEntity.initDataParameters();

//            WildCropGeneration.load();

            registerCompostables();
//            registerDispenserBehaviors();
//            registerAnimalFeeds();

            CatHerder.LOGGER.info("CommonSetup.init complete");
        });
    }

//    public static void registerDispenserBehaviors() {
//
//    }

    public static void registerCompostables() {
        CatHerder.LOGGER.info("Registering compostables...");

        ComposterBlock.COMPOSTABLES.put(ModItems.CATNIP.get(), 0.65F);
        ComposterBlock.COMPOSTABLES.put(ModItems.WILD_CATNIP.get(), 0.65F);
        ComposterBlock.COMPOSTABLES.put(ModItems.CATNIP_SEEDS.get(), 1.0F);
    }

//    public static void registerAnimalFeeds() {
//
//    }
}
