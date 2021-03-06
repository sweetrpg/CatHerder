package com.sweetrpg.catherder.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sweetrpg.catherder.api.registry.Accessory;
import com.sweetrpg.catherder.api.registry.AccessoryType;
import com.sweetrpg.catherder.api.registry.IBeddingMaterial;
import com.sweetrpg.catherder.api.registry.ICasingMaterial;
import com.sweetrpg.catherder.api.registry.Talent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.function.Supplier;

/**
 * @author ProPercivalalb
 */
public class CatHerderAPI {

    public static Supplier<IForgeRegistry<Talent>> TALENTS;
    public static Supplier<IForgeRegistry<Accessory>> ACCESSORIES;
    public static Supplier<IForgeRegistry<AccessoryType>> ACCESSORY_TYPE;
    public static Supplier<IForgeRegistry<IBeddingMaterial>> BEDDING_MATERIAL;
    public static Supplier<IForgeRegistry<ICasingMaterial>> CASING_MATERIAL;

    public static final Logger LOGGER = LogManager.getLogger("catherder");
}
