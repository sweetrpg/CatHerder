package com.sweetrpg.catherder.common.registry;

import com.sweetrpg.catherder.api.CatHerderAPI;
import com.sweetrpg.catherder.api.feature.CatLevel;
import com.sweetrpg.catherder.api.feature.Gender;
import com.sweetrpg.catherder.api.feature.Mode;
import com.sweetrpg.catherder.api.registry.AccessoryInstance;
import com.sweetrpg.catherder.api.registry.TalentInstance;
import com.sweetrpg.catherder.common.entity.misc.DimensionDependentArg;
import com.sweetrpg.catherder.common.entity.serializers.*;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class ModSerializers {

    public static final DeferredRegister<EntityDataSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS, CatHerderAPI.MOD_ID);

    public static final EntityDataSerializer<List<TalentInstance>> TALENT_SERIALIZER = new TalentListSerializer();
    public static final EntityDataSerializer<Optional<AccessoryInstance>> COLLAR_TYPE_SERIALIZER = new CollarSerializer();
    public static final EntityDataSerializer<List<AccessoryInstance>> ACCESSORY_SERIALIZER = new AccessorySerializer();
    public static final EntityDataSerializer<Gender> GENDER_SERIALIZER = new GenderSerializer();
    public static final EntityDataSerializer<Mode> MODE_SERIALIZER = new ModeSerializer();
    public static final EntityDataSerializer<CatLevel> CAT_LEVEL_SERIALIZER = new CatLevelSerializer();
    public static final EntityDataSerializer<DimensionDependentArg<Optional<BlockPos>>> CAT_TREE_LOC_SERIALIZER = new TreeLocationsSerializer();
    public static final EntityDataSerializer<Integer> ORIGINAL_BREED_SERIALIZER = EntityDataSerializers.INT;

    private static RegistryObject<EntityDataSerializer> register(final String name, final Supplier<EntityDataSerializer> sup) {
        return SERIALIZERS.register(name, sup);
    }

    static {
        register("talents", () -> TALENT_SERIALIZER);
        register("collar", () -> COLLAR_TYPE_SERIALIZER);
        register("accessories", () -> ACCESSORY_SERIALIZER);
        register("gender", () -> GENDER_SERIALIZER);
        register("mode", () -> MODE_SERIALIZER);
        register("cat_level", () -> CAT_LEVEL_SERIALIZER);
        register("cat_tree_location", () -> CAT_TREE_LOC_SERIALIZER);
        register("original_breed", () -> ORIGINAL_BREED_SERIALIZER);
    }
}
