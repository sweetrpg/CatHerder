package com.sweetrpg.catherder.common;

import com.sweetrpg.catherder.api.feature.FoodHandler;
import com.sweetrpg.catherder.common.command.CatRespawnCommand;
import com.sweetrpg.catherder.common.config.ConfigHandler;
import com.sweetrpg.catherder.common.entity.CatEntity;
import com.sweetrpg.catherder.common.event.FishFoodHandler;
import com.sweetrpg.catherder.common.network.PacketHandler;
import com.sweetrpg.catherder.common.registry.ModItems;
import com.sweetrpg.catherder.common.talent.HappyEaterTalent;
import com.sweetrpg.catherder.common.world.gen.WildCropGeneration;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class CommonSetup {
    public static void init(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            PacketHandler.init();
            //TODO CriteriaTriggers.register(criterion)

//            FoodHandler.registerHandler(new MeatFoodHandler());
            FoodHandler.registerHandler(new FishFoodHandler());
            FoodHandler.registerDynPredicate(HappyEaterTalent.INNER_DYN_PRED);

//            InteractHandler.registerHandler(new HelmetInteractHandler());
            ConfigHandler.initTalentConfig();
            CatRespawnCommand.registerSerilizers();
            CatEntity.initDataParameters();

            WildCropGeneration.registerWildCatnipGeneration();

            registerCompostables();
//            registerDispenserBehaviors();
//            registerAnimalFeeds();
        });
    }

//    public static void registerDispenserBehaviors() {
//
//    }

    public static void registerCompostables() {
        ComposterBlock.COMPOSTABLES.put(ModItems.CATNIP.get(), 0.65F);
        ComposterBlock.COMPOSTABLES.put(ModItems.WILD_CATNIP.get(), 0.65F);
        ComposterBlock.COMPOSTABLES.put(ModItems.CATNIP_SEEDS.get(), 1.0F);
    }

//    public static void registerAnimalFeeds() {
//
//    }
}
