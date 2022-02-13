package kittytalents.common.network.packet;

import kittytalents.common.network.packet.data.FriendlyFireData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class FriendlyFirePacket extends CatPacket<FriendlyFireData> {

    @Override
    public void encode(FriendlyFireData data, FriendlyByteBuf buf) {
        super.encode(data, buf);
        buf.writeBoolean(data.friendlyFire);
    }

    @Override
    public FriendlyFireData decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        boolean obeyOthers = buf.readBoolean();
        return new FriendlyFireData(entityId, obeyOthers);
    }

    @Override
    public void handleDog(kittytalents.common.entity.CatEntity catIn, FriendlyFireData data, Supplier<Context> ctx) {
        if (!catIn.canInteract(ctx.get().getSender())) {
            return;
        }

        catIn.setCanPlayersAttack(data.friendlyFire);
    }
}
