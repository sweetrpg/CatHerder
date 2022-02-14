package com.sweetrpg.catherder.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sweetrpg.catherder.api.registry.Accessory;
import com.sweetrpg.catherder.api.registry.AccessoryType;
import com.sweetrpg.catherder.api.registry.IBeddingMaterial;
import com.sweetrpg.catherder.api.registry.ICasingMaterial;
import com.sweetrpg.catherder.api.registry.Talent;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * @author ProPercivalalb
 */
public class CatHerderAPI {

    public static IForgeRegistry<Talent> TALENTS;
    public static IForgeRegistry<Accessory> ACCESSORIES;
    public static IForgeRegistry<AccessoryType> ACCESSORY_TYPE;
    public static IForgeRegistry<IBeddingMaterial> BEDDING_MATERIAL;
    public static IForgeRegistry<ICasingMaterial> CASING_MATERIAL;

    public static final Logger LOGGER = LogManager.getLogger("catherder");
}
