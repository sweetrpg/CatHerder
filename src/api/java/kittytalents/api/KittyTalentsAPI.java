package kittytalents.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import kittytalents.api.registry.Accessory;
import kittytalents.api.registry.AccessoryType;
import kittytalents.api.registry.IBeddingMaterial;
import kittytalents.api.registry.ICasingMaterial;
import kittytalents.api.registry.Talent;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * @author ProPercivalalb
 */
public class KittyTalentsAPI {

    public static IForgeRegistry<Talent> TALENTS;
    public static IForgeRegistry<Accessory> ACCESSORIES;
    public static IForgeRegistry<AccessoryType> ACCESSORY_TYPE;
    public static IForgeRegistry<IBeddingMaterial> BEDDING_MATERIAL;
    public static IForgeRegistry<ICasingMaterial> CASING_MATERIAL;

    public static final Logger LOGGER = LogManager.getLogger("kittytalents");
}
