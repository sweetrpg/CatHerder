package com.sweetrpg.catherder.common.util;

import com.sweetrpg.catherder.common.lib.Constants;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class TextUtils {
    private static final MutableComponent NO_EFFECTS = (Component.translatable("effect.none")).withStyle(ChatFormatting.GRAY);

    /**
     * Syntactic sugar for custom translation keys. Always prefixed with the mod's ID in lang files (e.g. farmersdelight.your.key.here).
     */
    public static MutableComponent getTranslation(String type, String key, Object... args) {
        return Component.translatable(type + "." + Constants.MOD_ID + "." + key, args);
    }

}
