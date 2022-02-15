package com.sweetrpg.catherder.common.entity.accessory;

import com.sweetrpg.catherder.common.registry.ModAccessoryTypes;
import net.minecraft.world.level.ItemLike;

import java.util.function.Supplier;

public class Helmet extends ArmourAccessory {

    public Helmet(Supplier<? extends ItemLike> itemIn) {
        super(ModAccessoryTypes.HEAD, itemIn);
    }

}
