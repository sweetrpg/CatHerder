package com.sweetrpg.catherder.api;

import com.sweetrpg.catherder.api.registry.*;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.registries.IForgeRegistry;

import java.util.function.Supplier;

/**
 * @author Paulyhedral, ProPercivalalb
 */
public class CatHerderAPI {

    public static final String MOD_ID = "catherder";
    public static final String MOD_NAME = "Cat Herder";

    public static Supplier<IForgeRegistry<Talent>> TALENTS;
    public static Supplier<IForgeRegistry<Accessory>> ACCESSORIES;
    public static Supplier<IForgeRegistry<AccessoryType>> ACCESSORY_TYPE;
    public static Supplier<IForgeRegistry<IStructureMaterial>> STRUCTURE_MATERIAL;
    public static Supplier<IForgeRegistry<IColorMaterial>> COLOR_MATERIAL;
    public static Supplier<IForgeRegistry<IDyeMaterial>> DYE_MATERIAL;

    public static final Logger LOGGER = LogManager.getLogger("catherder");

    public class RegistryKeys {
        public static final ResourceLocation TALENTS_REGISTRY = new ResourceLocation(MOD_ID, "talents");
        public static final ResourceLocation ACCESSORY_REGISTRY = new ResourceLocation(MOD_ID, "accessories");
        public static final ResourceLocation ACCESSORY_TYPE_REGISTRY = new ResourceLocation(MOD_ID, "accessory_type");
        public static final ResourceLocation STRUCTURE_REGISTRY = new ResourceLocation(MOD_ID, "structures");
        public static final ResourceLocation COLOR_REGISTRY = new ResourceLocation(MOD_ID, "colors");
        public static final ResourceLocation DYE_REGISTRY = new ResourceLocation(MOD_ID, "dyes");
    }

}
