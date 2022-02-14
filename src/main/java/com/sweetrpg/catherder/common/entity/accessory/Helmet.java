package com.sweetrpg.catherder.common.entity.accessory;

import com.sweetrpg.catherder.CatAccessoryTypes;
import net.minecraft.world.level.ItemLike;

import java.util.function.Supplier;

public class Helmet extends ArmourAccessory {

    public Helmet(Supplier<? extends ItemLike> itemIn) {
        super(CatAccessoryTypes.HEAD, itemIn);
    }

}
