package com.sweetrpg.catherder.common.network.packet;

import com.sweetrpg.catherder.common.entity.CatEntity;
import com.sweetrpg.catherder.common.network.packet.data.CatTextureData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class CatTexturePacket extends CatPacket<CatTextureData> {

    @Override
    public void encode(CatTextureData data, FriendlyByteBuf buf) {
        super.encode(data, buf);
        buf.writeUtf(data.hash);
    }

    @Override
    public CatTextureData decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        String texture = buf.readUtf(128);
        return new CatTextureData(entityId, texture);
    }

    @Override
    public void handleCat(CatEntity catIn, CatTextureData data, Supplier<Context> ctx) {
        if (!catIn.canInteract(ctx.get().getSender())) {
            return;
        }

        catIn.setSkinHash(data.hash);
    }
}
