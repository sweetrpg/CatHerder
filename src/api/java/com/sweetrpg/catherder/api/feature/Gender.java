package com.sweetrpg.catherder.api.feature;

import net.minecraft.util.RandomSource;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public enum Gender {

    MALE(1, "male"),
    FEMALE(2, "female"),
    UNISEX(0, "unisex");

    private int index;
    private String saveName;
    private String unlocalisedTip;
    private String unlocalisedName;
    private String unlocalisedPronoun;
    private String unlocalisedSubject;
    private String unlocalisedTitle;

    public static final Gender[] VALUES = Arrays.stream(Gender.values()).sorted(Comparator.comparingInt(Gender::getIndex)).toArray(size -> {
        return new Gender[size];
    });

    private Gender(int index, String name) {
        this.index = index;
        this.saveName = name;
        this.unlocalisedName = "cat.gender." + name;
        this.unlocalisedTip = this.unlocalisedName + ".indicator";
        this.unlocalisedPronoun = this.unlocalisedName + ".pronoun";
        this.unlocalisedSubject = this.unlocalisedName + ".subject";
        this.unlocalisedTitle = this.unlocalisedName + ".title";
    }

    public int getIndex() {
        return this.index;
    }

    public String getSaveName() {
        return this.saveName;
    }

    public String getUnlocalizedTip() {
        return this.unlocalisedTip;
    }

    public String getUnlocalizedName() {
        return this.unlocalisedName;
    }

    public String getUnlocalisedPronoun() {
        return this.unlocalisedPronoun;
    }

    public String getUnlocalisedSubject() {
        return this.unlocalisedSubject;
    }

    public String getUnlocalizedTitle() {
        return this.unlocalisedTitle;
    }

    public boolean canMateWith(Gender gender) {
        boolean equalGenders = this == gender;
        return (equalGenders && this == Gender.UNISEX) || !equalGenders;
    }

    public static Gender byIndex(int i) {
        if (i < 0 | i >= VALUES.length) {
            i = Gender.UNISEX.getIndex();
        }
        return VALUES[i];
    }

    public static Gender bySaveName(String saveName) {
        for (Gender gender : Gender.values()) {
            if (gender.getSaveName().equals(saveName)) {
                return gender;
            }
        }

        return UNISEX;
    }

    public static Gender random(RandomSource rng) {
        return rng.nextBoolean() ? MALE : FEMALE;
    }

}
