package kittytalents.common.network.packet;

import kittytalents.common.network.packet.data.DogNameData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class DogNamePacket extends DogPacket<DogNameData> {

    @Override
    public void encode(DogNameData data, FriendlyByteBuf buf) {
        super.encode(data, buf);
        buf.writeUtf(data.name, 64);
    }

    @Override
    public DogNameData decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        String name = buf.readUtf(64);
        return new DogNameData(entityId, name);
    }

    @Override
    public void handleDog(kittytalents.common.entity.CatEntity dogIn, DogNameData data, Supplier<Context> ctx) {
        if (!dogIn.canInteract(ctx.get().getSender())) {
            return;
        }

        if (data.name.isEmpty()) {
            dogIn.setCustomName(null);
        }
        else {
            dogIn.setCustomName(new TextComponent(data.name));
        }
    }
}
