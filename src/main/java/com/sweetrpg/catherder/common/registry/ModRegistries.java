package com.sweetrpg.catherder.common.registry;

import com.sweetrpg.catherder.api.CatHerderAPI;
import com.sweetrpg.catherder.api.impl.MissingBeddingMaterial;
import com.sweetrpg.catherder.api.impl.MissingCasingMissing;
import com.sweetrpg.catherder.api.registry.*;
import com.sweetrpg.catherder.common.util.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;

public class ModRegistries {

    public static void newRegistry(NewRegistryEvent event) {
        CatHerderAPI.TALENTS = event.create(makeRegistry(CatHerderAPI.RegistryKeys.TALENTS_REGISTRY, Talent.class));
        CatHerderAPI.ACCESSORIES = event.create(makeRegistry(CatHerderAPI.RegistryKeys.ACCESSORY_REGISTRY, Accessory.class));
        CatHerderAPI.ACCESSORY_TYPE = event.create(makeRegistry(CatHerderAPI.RegistryKeys.ACCESSORY_TYPE_REGISTRY, AccessoryType.class).disableSync());
//        CatHerderAPI.BEDDING_MATERIAL = event.create(makeRegistry(CatHerderAPI.RegistryKeys.BEDDING_REGISTRY, IBeddingMaterial.class).addCallback(BeddingCallbacks.INSTANCE));
//        CatHerderAPI.CASING_MATERIAL = event.create(makeRegistry(CatHerderAPI.RegistryKeys.CASING_REGISTRY, ICasingMaterial.class).addCallback(CasingCallbacks.INSTANCE)); //TODO ADD holder object
    }

    private static <T> RegistryBuilder<T> makeRegistry(final ResourceLocation rl, Class<T> type) {
        return new RegistryBuilder<T>().setName(rl);
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
