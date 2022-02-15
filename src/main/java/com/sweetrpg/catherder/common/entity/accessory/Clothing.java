package com.sweetrpg.catherder.common.entity.accessory;

import com.sweetrpg.catherder.common.registry.ModAccessoryTypes;
import com.sweetrpg.catherder.api.registry.Accessory;
import net.minecraft.world.level.ItemLike;

import java.util.function.Supplier;

public class Clothing extends Accessory {

    public Clothing(Supplier<? extends ItemLike> itemIn) {
        super(ModAccessoryTypes.CLOTHING, itemIn);
    }

}
