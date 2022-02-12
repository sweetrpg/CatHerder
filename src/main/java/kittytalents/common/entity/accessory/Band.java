package kittytalents.common.entity.accessory;

import kittytalents.KittyAccessoryTypes;
import kittytalents.api.registry.Accessory;
import kittytalents.api.registry.AccessoryInstance;
import net.minecraft.world.level.ItemLike;

import java.util.function.Supplier;

public class Band extends Accessory {

    public Band(Supplier<? extends ItemLike> itemIn) {
        super(KittyAccessoryTypes.BAND, itemIn);
    }

    @Override
    public byte getRenderLayer() {
        return AccessoryInstance.RENDER_TOP;
    }
}
