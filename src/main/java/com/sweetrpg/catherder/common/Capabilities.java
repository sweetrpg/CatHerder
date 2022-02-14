package com.sweetrpg.catherder.common;

import com.sweetrpg.catherder.common.inventory.PackCatItemHandler;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;

public class Capabilities {

    public static void registerCaps(final RegisterCapabilitiesEvent event) {
        event.register(PackCatItemHandler.class);
    }
}
