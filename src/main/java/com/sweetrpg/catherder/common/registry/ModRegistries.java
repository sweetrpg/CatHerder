package com.sweetrpg.catherder.common.registry;

import com.sweetrpg.catherder.api.CatHerderAPI;
import com.sweetrpg.catherder.api.impl.MissingDyeMaterial;
import com.sweetrpg.catherder.api.impl.MissingStructureMaterial;
import com.sweetrpg.catherder.api.impl.MissingColorMaterial;
import com.sweetrpg.catherder.api.registry.*;
import com.sweetrpg.catherder.common.util.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;

public class ModRegistries {

    protected class Keys {
        public static final ResourceLocation TALENTS_REGISTRY = Util.getResource("talents");
        public static final ResourceLocation ACCESSORY_REGISTRY = Util.getResource("accessories");
        public static final ResourceLocation ACCESSORY_TYPE_REGISTRY = Util.getResource("accessory_type");
        public static final ResourceLocation STRUCTURE_REGISTRY = Util.getResource("structures");
        public static final ResourceLocation COLOR_REGISTRY = Util.getResource("colors");
        public static final ResourceLocation DYE_REGISTRY = Util.getResource("dyes");
    }

    public static void newRegistry(NewRegistryEvent event) {
        CatHerderAPI.TALENTS = event.create(makeRegistry(Keys.TALENTS_REGISTRY, Talent.class));
        CatHerderAPI.ACCESSORIES = event.create(makeRegistry(Keys.ACCESSORY_REGISTRY, Accessory.class));
        CatHerderAPI.ACCESSORY_TYPE = event.create(makeRegistry(Keys.ACCESSORY_TYPE_REGISTRY, AccessoryType.class).disableSync());
        CatHerderAPI.STRUCTURE_MATERIAL = event.create(makeRegistry(Keys.STRUCTURE_REGISTRY, IStructureMaterial.class).addCallback(StructureCallbacks.INSTANCE));
        CatHerderAPI.COLOR_MATERIAL = event.create(makeRegistry(Keys.COLOR_REGISTRY, IColorMaterial.class).addCallback(ColorCallbacks.INSTANCE));
        CatHerderAPI.DYE_MATERIAL = event.create(makeRegistry(Keys.DYE_REGISTRY, IDyeMaterial.class).addCallback(DyeCallbacks.INSTANCE));
    }

    private static <T extends IForgeRegistryEntry<T>> RegistryBuilder<T> makeRegistry(final ResourceLocation rl, Class<T> type) {
        return new RegistryBuilder<T>().setName(rl).setType(type);
    }

    private static class StructureCallbacks implements IForgeRegistry.DummyFactory<IStructureMaterial> {

        static final StructureCallbacks INSTANCE = new StructureCallbacks();

        @Override
        public IStructureMaterial createDummy(ResourceLocation key) {
            return new MissingStructureMaterial().setRegistryName(key);
        }
    }

    private static class ColorCallbacks implements IForgeRegistry.DummyFactory<IColorMaterial> {

        static final ColorCallbacks INSTANCE = new ColorCallbacks();

        @Override
        public IColorMaterial createDummy(ResourceLocation key) {
            return new MissingColorMaterial().setRegistryName(key);
        }
    }

    private static class DyeCallbacks implements IForgeRegistry.DummyFactory<IDyeMaterial> {

        static final DyeCallbacks INSTANCE = new DyeCallbacks();

        @Override
        public IDyeMaterial createDummy(ResourceLocation key) {
            return new MissingDyeMaterial().setRegistryName(key);
        }
    }

//
//    private static class AccessoryCallbacks implements IForgeRegistry.DummyFactory<Accessory> {
//
//        static final AccessoryCallbacks INSTANCE = new AccessoryCallbacks();
//
//        @Override
//        public Accessory createDummy(ResourceLocation key) {
//            return new Accessory(() -> CatAccessoryTypes.CLOTHING).setRegistryName(key);
//        }
//    }
//
//    private static class AccessoryTypeCallbacks implements IForgeRegistry.DummyFactory<AccessoryType> {
//
//        static final AccessoryTypeCallbacks INSTANCE = new AccessoryTypeCallbacks();
//        static final AccessoryType dummyType = new AccessoryType();
//
//        @Override
//        public AccessoryType createDummy(ResourceLocation key) {
//            return this.dummyType.set;
//        }
//
//    }
}
