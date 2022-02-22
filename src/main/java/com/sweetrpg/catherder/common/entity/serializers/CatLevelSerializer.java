package com.sweetrpg.catherder.common.entity.serializers;

import com.sweetrpg.catherder.api.feature.CatLevel;
import com.sweetrpg.catherder.api.feature.CatLevel.Type;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;

public class CatLevelSerializer implements EntityDataSerializer<CatLevel> {

    @Override
    public void write(FriendlyByteBuf buf, CatLevel value) {
        buf.writeInt(value.getLevel(Type.NORMAL));
        buf.writeInt(value.getLevel(Type.WILD));
    }

    @Override
    public CatLevel read(FriendlyByteBuf buf) {
        return new CatLevel(buf.readInt(), buf.readInt());
    }

    @Override
    public CatLevel copy(CatLevel value) {
        return value.copy();
    }

}
