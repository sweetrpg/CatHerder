package com.sweetrpg.catherder.common.registry;

import com.mojang.serialization.Codec;
import com.sweetrpg.catherder.api.CatHerderAPI;
import com.sweetrpg.catherder.common.world.filter.BiomeTagFilter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModPlacementModifiers
{
	public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_MODIFIERS = DeferredRegister.create(BuiltInRegistries.PLACEMENT_MODIFIER_TYPE.key(), CatHerderAPI.MOD_ID);

	public static final RegistryObject<PlacementModifierType<BiomeTagFilter>> BIOME_TAG = PLACEMENT_MODIFIERS.register("biome_tag", () -> typeConvert(BiomeTagFilter.CODEC));

	private static <P extends PlacementModifier> PlacementModifierType<P> typeConvert(Codec<P> codec) {
		return () -> codec;
	}
}
