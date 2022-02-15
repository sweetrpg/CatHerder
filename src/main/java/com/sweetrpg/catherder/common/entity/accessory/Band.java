package com.sweetrpg.catherder.common.entity.accessory;

import com.sweetrpg.catherder.common.registry.ModAccessoryTypes;
import com.sweetrpg.catherder.api.registry.Accessory;
import com.sweetrpg.catherder.api.registry.AccessoryInstance;
import net.minecraft.world.level.ItemLike;

import java.util.function.Supplier;

public class Band extends Accessory {

    public Band(Supplier<? extends ItemLike> itemIn) {
        super(ModAccessoryTypes.BAND, itemIn);
    }

    @Override
    public byte getRenderLayer() {
        return AccessoryInstance.RENDER_TOP;
    }
}
