package com.sweetrpg.catherder.common.entity.serializers;

import com.sweetrpg.catherder.api.feature.Gender;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;

public class GenderSerializer implements EntityDataSerializer<Gender> {

    @Override
    public void write(FriendlyByteBuf buf, Gender value) {
        buf.writeByte(value.getIndex());
    }

    @Override
    public Gender read(FriendlyByteBuf buf) {
        return Gender.byIndex(buf.readByte());
    }

    @Override
    public Gender copy(Gender value) {
        return value;
    }

}
