package com.sweetrpg.catherder.common.entity.accessory;

import com.sweetrpg.catherder.common.registry.ModAccessoryTypes;
import com.sweetrpg.catherder.api.registry.AccessoryInstance;
import net.minecraft.world.level.ItemLike;

import java.util.function.Supplier;

public class LeatherHelmet extends DyeableAccessory {

    public LeatherHelmet(Supplier<? extends ItemLike> itemIn) {
        super(ModAccessoryTypes.HEAD, itemIn);
    }

    @Override
    public byte getRenderLayer() {
        return AccessoryInstance.RENDER_TOP;
    }
}
