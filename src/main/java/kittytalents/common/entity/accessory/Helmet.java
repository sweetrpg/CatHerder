package kittytalents.common.entity.accessory;

import kittytalents.KittyAccessoryTypes;
import net.minecraft.world.level.ItemLike;

import java.util.function.Supplier;

public class Helmet extends ArmourAccessory {

    public Helmet(Supplier<? extends ItemLike> itemIn) {
        super(KittyAccessoryTypes.HEAD, itemIn);
    }

}
