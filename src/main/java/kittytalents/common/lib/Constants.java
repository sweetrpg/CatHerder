package kittytalents.common.lib;

import kittytalents.common.util.Util;
import net.minecraft.resources.ResourceLocation;

public class Constants {

    public static final String MOD_ID = "kittytalents";
    public static final String MOD_NAME = "Kitty Talents";

    public static final String VANILLA_ID = "minecraft";
    public static final String VANILLA_NAME = "Minecraft";

    // Network
    public static final ResourceLocation CHANNEL_NAME = Util.getResource("channel");
    public static final String PROTOCOL_VERSION = Integer.toString(3);

    // Storage
    public static final String STORAGE_CAT_RESPAWN = MOD_ID + "DeadCats";
    public static final String STORAGE_CAT_LOCATION = "cat_locations";

    public static class EntityState {

        public static final byte DEATH = 3;
        public static final byte CAT_SMOKE = 6;
        public static final byte CAT_HEARTS = 7;
        public static final byte CAT_START_SHAKING = 8;
        public static final byte GUARDIAN_SOUND = 21;
        public static final byte TOTEM_OF_UNDYING = 35;
        public static final byte SLIDING_DOWN_HONEY = 53;
        public static final byte CAT_INTERUPT_SHAKING = 56;
    }
}
