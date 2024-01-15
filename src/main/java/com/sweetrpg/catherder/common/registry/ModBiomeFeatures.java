package com.sweetrpg.catherder.common.registry;

import com.sweetrpg.catherder.api.CatHerderAPI;
import com.sweetrpg.catherder.common.world.configuration.WildCropConfiguration;
//import com.sweetrpg.catherder.common.world.feature.WildCatnipFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBiomeFeatures
{
	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, CatHerderAPI.MOD_ID);

//	public static final RegistryObject<Feature<RandomPatchConfiguration>> WILD_CATNIP = FEATURES.register("wild_catnip", () -> new WildCatnipFeature(RandomPatchConfiguration.CODEC));
//	public static final RegistryObject<Feature<WildCropConfiguration>> WILD_CROP = FEATURES.register("wild_crop", () -> new WildCropFeature(WildCropConfiguration.CODEC));
}
