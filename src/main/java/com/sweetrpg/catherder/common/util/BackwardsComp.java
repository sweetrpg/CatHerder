package com.sweetrpg.catherder.common.util;

import com.google.common.collect.Maps;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.CatVariant;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class BackwardsComp {

    private static final Map<Integer, ResourceLocation> typeToVariantMap = net.minecraft.Util.make(Maps.newHashMap(), (m) -> {
        m.put(0, CatVariant.TABBY.location());
        m.put(1, CatVariant.BLACK.location());
        m.put(2, CatVariant.RED.location());
        m.put(3, CatVariant.SIAMESE.location());
        m.put(4, CatVariant.BRITISH_SHORTHAIR.location());
        m.put(5, CatVariant.CALICO.location());
        m.put(6, CatVariant.PERSIAN.location());
        m.put(7, CatVariant.RAGDOLL.location());
        m.put(8, CatVariant.WHITE.location());
        m.put(9, CatVariant.JELLIE.location());
        m.put(10, CatVariant.ALL_BLACK.location());
    });

    public static void getCatVariant(CompoundTag compound, Consumer<CatVariant> consumer) {
        if(compound.contains("original_breed", Tag.TAG_ANY_NUMERIC)) {
            var originalBreed = compound.getInt("original_breed");
            Optional.ofNullable(BackwardsComp.typeToVariantMap.get(originalBreed)).ifPresent((rl) -> {
                var variant = BuiltInRegistries.CAT_VARIANT.get(rl);
                consumer.accept(variant);
            });
        }
    }

    public static void init() {

    }
}
