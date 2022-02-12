package kittytalents.common.entity.serializers;

import kittytalents.api.KittyTalentsAPI;
import kittytalents.api.registry.Accessory;
import kittytalents.api.registry.AccessoryInstance;
import kittytalents.common.util.Util;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;

import java.util.Optional;

public class CollarSerializer implements EntityDataSerializer<Optional<AccessoryInstance>> {

    @Override
    public void write(FriendlyByteBuf buf, Optional<AccessoryInstance> value) {
        Util.acceptOrElse(value, (inst) -> {
            buf.writeBoolean(true);
            buf.writeRegistryIdUnsafe(KittyTalentsAPI.ACCESSORIES, inst.getAccessory());
            inst.getAccessory().write(inst, buf);
        }, () -> {
            buf.writeBoolean(false);
        });

    }

    @Override
    public Optional<AccessoryInstance> read(FriendlyByteBuf buf) {
        if (buf.readBoolean()) {
            Accessory type = buf.readRegistryIdUnsafe(KittyTalentsAPI.ACCESSORIES);
            return Optional.of(type.createInstance(buf));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<AccessoryInstance> copy(Optional<AccessoryInstance> value) {
        if (value.isPresent()) {
            return Optional.of(value.get().copy());
        }
        return Optional.empty();
    }

}
