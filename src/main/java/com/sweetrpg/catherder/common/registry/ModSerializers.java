package com.sweetrpg.catherder.common.registry;

import com.sweetrpg.catherder.api.CatHerderAPI;
import com.sweetrpg.catherder.common.entity.serializers.*;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DataSerializerEntry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModSerializers {

    public static final DeferredRegister<DataSerializerEntry> SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.DATA_SERIALIZERS, CatHerderAPI.MOD_ID);

    public static final RegistryObject<DataSerializerEntry> TALENT_SERIALIZER = register2("talents", TalentListSerializer::new);
    public static final RegistryObject<DataSerializerEntry> COLLAR_TYPE_SERIALIZER = register2("collar", CollarSerializer::new);
    public static final RegistryObject<DataSerializerEntry> ACCESSORY_SERIALIZER = register2("accessories", AccessorySerializer::new);
    public static final RegistryObject<DataSerializerEntry> GENDER_SERIALIZER = register2("gender", GenderSerializer::new);
    public static final RegistryObject<DataSerializerEntry> MODE_SERIALIZER = register2("mode", ModeSerializer::new);
    public static final RegistryObject<DataSerializerEntry> CAT_LEVEL_SERIALIZER = register2("cat_level", CatLevelSerializer::new);
    public static final RegistryObject<DataSerializerEntry> CAT_TREE_LOC_SERIALIZER = register2("cat_tree_location", TreeLocationsSerializer::new);
    public static final RegistryObject<DataSerializerEntry> ORIGINAL_BREED_SERIALIZER = register2("original_breed", () -> EntityDataSerializers.INT);

    private static <X extends EntityDataSerializer<?>> RegistryObject<DataSerializerEntry> register2(final String name, final Supplier<X> factory) {
        return register(name, () -> new DataSerializerEntry(factory.get()));
    }

    private static RegistryObject<DataSerializerEntry> register(final String name, final Supplier<DataSerializerEntry> sup) {
        return SERIALIZERS.register(name, sup);
    }
}
