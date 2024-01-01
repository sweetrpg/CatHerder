package com.sweetrpg.catherder.common.entity.serializers;

import com.sweetrpg.catherder.common.entity.misc.DimensionDependentArg;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class TreeLocationsSerializer<D, T extends EntityDataSerializer<D>> implements EntityDataSerializer<DimensionDependentArg<D>> {

    @Override
    public void write(FriendlyByteBuf buf, DimensionDependentArg<D> value) {
        EntityDataSerializer<D> ser = value.getSerializer();
        buf.writeInt(EntityDataSerializers.getSerializedId(ser));
        buf.writeInt(value.size());
        value.entrySet().forEach((entry) -> {
            buf.writeResourceLocation(entry.getKey().location());
            ser.write(buf, entry.getValue());
        });
    }

    @Override
    public DimensionDependentArg<D> read(FriendlyByteBuf buf) {
        EntityDataSerializer<D> ser = (EntityDataSerializer<D>) EntityDataSerializers.getSerializer(buf.readInt());
        DimensionDependentArg<D> value = new DimensionDependentArg<>(() -> ser);
        int size = buf.readInt();

        for (int i = 0; i < size; i++) {
            ResourceLocation loc = buf.readResourceLocation();
            ResourceKey<Level> type = ResourceKey.create(Registry.DIMENSION_REGISTRY, loc);
            D subV = ser.read(buf);
            value.map.put(type, subV);
        }

        return value;
    }

    @Override
    public DimensionDependentArg<D> copy(DimensionDependentArg<D> value) {
        return value.copy();
    }
}
