package kittytalents.common.entity.accessory;

import kittytalents.KittyAccessoryTypes;
import kittytalents.api.registry.AccessoryInstance;
import net.minecraft.world.level.ItemLike;

import java.util.function.Supplier;

public class LeatherHelmet extends DyeableAccessory {

    public LeatherHelmet(Supplier<? extends ItemLike> itemIn) {
        super(KittyAccessoryTypes.HEAD, itemIn);
    }

    @Override
    public byte getRenderLayer() {
        return AccessoryInstance.RENDER_TOP;
    }
}
