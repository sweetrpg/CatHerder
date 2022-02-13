package kittytalents.common;

import kittytalents.common.inventory.PackKittyItemHandler;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;

public class Capabilities {

    public static void registerCaps(final RegisterCapabilitiesEvent event) {
        event.register(PackKittyItemHandler.class);
    }
}
