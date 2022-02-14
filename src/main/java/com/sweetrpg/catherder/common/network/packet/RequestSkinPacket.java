package com.sweetrpg.catherder.common.network.packet;

import com.sweetrpg.catherder.CatHerder;
import com.sweetrpg.catherder.common.entity.texture.CatTextureServer;
import com.sweetrpg.catherder.common.network.IPacket;
import com.sweetrpg.catherder.common.network.packet.data.RequestSkinData;
import com.sweetrpg.catherder.common.network.packet.data.SendSkinData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class RequestSkinPacket implements IPacket<RequestSkinData> {

    @Override
    public void encode(RequestSkinData data, FriendlyByteBuf buf) {
        buf.writeUtf(data.hash, 128);
    }

    @Override
    public RequestSkinData decode(FriendlyByteBuf buf) {
        return new RequestSkinData(buf.readUtf(128));
    }

    @Override
    public void handle(RequestSkinData data, Supplier<Context> ctx) {
        ctx.get().enqueueWork(() -> {
            LogicalSide side = ctx.get().getDirection().getReceptionSide();

            if (side.isServer()) {
                byte[] stream = CatTextureServer.INSTANCE.getCachedBytes(CatTextureServer.INSTANCE.getServerFolder(), data.hash);
                if (stream != null) {
                    CatHerder.HANDLER.reply(new SendSkinData(0, stream), ctx.get());

                    CatHerder.LOGGER.debug("Client requested skin for hash  {}", data.hash);
                } else {
                    CatHerder.LOGGER.warn("Client requested skin but no cache was available {}", data.hash);
                }
            }

        });

        ctx.get().setPacketHandled(true);
    }
}
