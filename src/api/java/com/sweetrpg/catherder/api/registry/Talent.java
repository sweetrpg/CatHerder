package com.sweetrpg.catherder.api.registry;

import java.util.function.BiFunction;

import javax.annotation.Nullable;

import com.sweetrpg.catherder.api.CatHerderAPI;
import net.minecraft.Util;
import net.minecraftforge.registries.ForgeRegistryEntry;

/**
 * @author ProPercivalalb
 */
public class Talent extends ForgeRegistryEntry<Talent> {

    @Nullable
    private String translationKey, translationInfoKey;

    @Nullable
    private final BiFunction<Talent, Integer, TalentInstance> create;

    /**
     * @param sup
     */
    public Talent(BiFunction<Talent, Integer, TalentInstance> sup) {
        this.create = sup;
    }

    public int getMaxLevel() {
        return 5;
    }

    public int getLevelCost(int forLevel) {
        return forLevel;
    }

    public int getCumulativeCost(int level) {
        return level * (level + 1) / 2;
    }

    public String getTranslationKey() {
        if (this.translationKey == null) {
            this.translationKey = Util.makeDescriptionId("talent", CatHerderAPI.TALENTS.get().getKey(this));
        }
        return this.translationKey;
    }

    public String getInfoTranslationKey() {
        if (this.translationInfoKey == null) {
            this.translationInfoKey = this.getTranslationKey() + ".description";
        }
        return this.translationInfoKey;
    }

    public TalentInstance getDefault(int level) {
        if (this.create == null) {
            return new TalentInstance(this, level);
        }
        return this.create.apply(this, level);
    }

    public TalentInstance getDefault() {
        return this.getDefault(1);
    }


    public boolean hasRenderer() {
        return false;
    }
}
