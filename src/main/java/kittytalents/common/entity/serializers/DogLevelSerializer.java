package kittytalents.common.entity.serializers;

import kittytalents.api.feature.CatLevel.Type;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;

public class DogLevelSerializer implements EntityDataSerializer<kittytalents.api.feature.CatLevel> {

    @Override
    public void write(FriendlyByteBuf buf, kittytalents.api.feature.CatLevel value) {
        buf.writeInt(value.getLevel(Type.NORMAL));
        buf.writeInt(value.getLevel(Type.DIRE));
    }

    @Override
    public kittytalents.api.feature.CatLevel read(FriendlyByteBuf buf) {
        return new kittytalents.api.feature.CatLevel(buf.readInt(), buf.readInt());
    }

    @Override
    public kittytalents.api.feature.CatLevel copy(kittytalents.api.feature.CatLevel value) {
        return value.copy();
    }

}
