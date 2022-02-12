package kittytalents.common.network.packet;

import kittytalents.api.feature.EnumMode;
import kittytalents.common.network.packet.data.DogModeData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class DogModePacket extends DogPacket<DogModeData> {

    @Override
    public void encode(DogModeData data, FriendlyByteBuf buf) {
        super.encode(data, buf);
        buf.writeInt(data.mode.getIndex());
    }

    @Override
    public DogModeData decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        int modeIndex = buf.readInt();
        return new DogModeData(entityId, EnumMode.byIndex(modeIndex));
    }

    @Override
    public void handleDog(kittytalents.common.entity.CatEntity dogIn, DogModeData data, Supplier<Context> ctx) {
        if (!dogIn.canInteract(ctx.get().getSender())) {
            return;
        }

        dogIn.setMode(data.mode);
    }
}
