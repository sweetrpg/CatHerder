package com.sweetrpg.catherder.common.entity.serializers;

import com.sweetrpg.catherder.api.feature.Mode;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;

public class ModeSerializer implements EntityDataSerializer<Mode> {

    @Override
    public void write(FriendlyByteBuf buf, Mode value) {
        buf.writeByte(value.getIndex());
    }

    @Override
    public Mode read(FriendlyByteBuf buf) {
        return Mode.byIndex(buf.readByte());
    }

    @Override
    public Mode copy(Mode value) {
        return value;
    }

}
