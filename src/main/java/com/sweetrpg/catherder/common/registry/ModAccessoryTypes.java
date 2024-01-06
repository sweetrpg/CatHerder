package com.sweetrpg.catherder.common.registry;

import com.sweetrpg.catherder.api.CatHerderAPI;
import com.sweetrpg.catherder.api.registry.AccessoryType;
import com.sweetrpg.catherder.common.lib.Constants;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModAccessoryTypes {

    public static final DeferredRegister<AccessoryType> ACCESSORY_TYPES = DeferredRegister.create(ModRegistries.Keys.ACCESSORY_TYPE_REGISTRY, CatHerderAPI.MOD_ID);

    public static final RegistryObject<AccessoryType> COLLAR = register("collar");
    public static final RegistryObject<AccessoryType> CLOTHING = register("clothing");
    public static final RegistryObject<AccessoryType> GLASSES = register("glasses");
    public static final RegistryObject<AccessoryType> BAND = register("band");
    public static final RegistryObject<AccessoryType> HEAD = register("head");
    public static final RegistryObject<AccessoryType> FEET = register("feet");
    public static final RegistryObject<AccessoryType> TAIL = register("tail");

    private static RegistryObject<AccessoryType> register(final String name) {
        return register(name, () -> new AccessoryType());
    }

    private static <T extends AccessoryType> RegistryObject<T> register(final String name, final Supplier<T> sup) {
        return ACCESSORY_TYPES.register(name, sup);
    }
}
