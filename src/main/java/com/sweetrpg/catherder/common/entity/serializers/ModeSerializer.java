package com.sweetrpg.catherder.common.entity.serializers;

import com.sweetrpg.catherder.api.feature.EnumMode;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;

public class ModeSerializer implements EntityDataSerializer<EnumMode> {

    @Override
    public void write(FriendlyByteBuf buf, EnumMode value) {
        buf.writeByte(value.getIndex());
    }

    @Override
    public EnumMode read(FriendlyByteBuf buf) {
        return EnumMode.byIndex(buf.readByte());
    }

    @Override
    public EnumMode copy(EnumMode value) {
        return value;
    }

}
