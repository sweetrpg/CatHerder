package com.sweetrpg.catherder.api.registry;

import javax.annotation.Nullable;

import com.sweetrpg.catherder.api.CatHerderAPI;
import net.minecraft.Util;

public class AccessoryType {

    @Nullable
    private String translationKey;

    public int numberToPutOn() {
        return 1;
    }

    public String getTranslationKey() {
        if (this.translationKey == null) {
            this.translationKey = Util.makeDescriptionId("accessory_type", CatHerderAPI.ACCESSORY_TYPE.get().getKey(this));
        }
        return this.translationKey;
    }
}
