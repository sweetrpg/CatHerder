package kittytalents.common.entity.accessory;

import kittytalents.KittyAccessoryTypes;
import kittytalents.api.registry.Accessory;
import kittytalents.api.registry.AccessoryInstance;
import net.minecraft.world.level.ItemLike;

import java.util.function.Supplier;

public class Glasses extends Accessory {

    public Glasses(Supplier<? extends ItemLike> itemIn) {
        super(KittyAccessoryTypes.GLASSES, itemIn);
    }

    @Override
    public byte getRenderLayer() {
        return AccessoryInstance.RENDER_TOP;
    }
}
