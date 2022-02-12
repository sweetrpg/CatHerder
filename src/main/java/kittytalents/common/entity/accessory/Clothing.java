package kittytalents.common.entity.accessory;

import kittytalents.KittyAccessoryTypes;
import kittytalents.api.registry.Accessory;
import net.minecraft.world.level.ItemLike;

import java.util.function.Supplier;

public class Clothing extends Accessory {

    public Clothing(Supplier<? extends ItemLike> itemIn) {
        super(KittyAccessoryTypes.CLOTHING, itemIn);
    }

}
