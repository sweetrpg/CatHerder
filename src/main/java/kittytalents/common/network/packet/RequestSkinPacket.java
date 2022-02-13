package kittytalents.common.network.packet;

import kittytalents.KittyTalents2;
import kittytalents.common.entity.texture.CatTextureServer;
import kittytalents.common.network.IPacket;
import kittytalents.common.network.packet.data.RequestSkinData;
import kittytalents.common.network.packet.data.SendSkinData;
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
                    KittyTalents2.HANDLER.reply(new SendSkinData(0, stream), ctx.get());

                    KittyTalents2.LOGGER.debug("Client requested skin for hash  {}", data.hash);
                } else {
                    KittyTalents2.LOGGER.warn("Client requested skin but no cache was available {}", data.hash);
                }
            }

        });

        ctx.get().setPacketHandled(true);
    }
}
