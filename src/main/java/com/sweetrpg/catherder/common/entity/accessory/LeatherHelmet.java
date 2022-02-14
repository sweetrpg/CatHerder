package com.sweetrpg.catherder.common.entity.accessory;

import com.sweetrpg.catherder.CatAccessoryTypes;
import com.sweetrpg.catherder.api.registry.AccessoryInstance;
import net.minecraft.world.level.ItemLike;

import java.util.function.Supplier;

public class LeatherHelmet extends DyeableAccessory {

    public LeatherHelmet(Supplier<? extends ItemLike> itemIn) {
        super(CatAccessoryTypes.HEAD, itemIn);
    }

    @Override
    public byte getRenderLayer() {
        return AccessoryInstance.RENDER_TOP;
    }
}
