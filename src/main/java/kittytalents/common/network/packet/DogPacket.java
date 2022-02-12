package kittytalents.common.network.packet;

import kittytalents.common.network.IPacket;
import kittytalents.common.network.packet.data.DogData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent.Context;

import java.util.function.Supplier;

public abstract class DogPacket<T extends DogData> implements IPacket<T> {

    @Override
    public void encode(T data, FriendlyByteBuf buf) {
        buf.writeInt(data.entityId);
    }

    @Override
    public abstract T decode(FriendlyByteBuf buf);

    @Override
    public final void handle(T data, Supplier<Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Entity target = ctx.get().getSender().level.getEntity(data.entityId);

            if (!(target instanceof kittytalents.common.entity.CatEntity)) {
                return;
            }

            this.handleDog((kittytalents.common.entity.CatEntity) target, data, ctx);
        });

        ctx.get().setPacketHandled(true);
    }

    public abstract void handleDog(kittytalents.common.entity.CatEntity dogIn, T data, Supplier<Context> ctx);

}
