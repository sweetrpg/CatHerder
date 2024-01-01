package com.sweetrpg.catherder.api.feature;

import com.sweetrpg.catherder.api.inferface.AbstractCatEntity;

import java.util.Arrays;
import java.util.Comparator;

public enum Mode {

    DOCILE(0, "docile"),
    WANDERING(1, "wandering"),
    ATTACK(2, "attack"),
    TACTICAL(3, "tactical"),
    DOMESTIC(4, "domestic"),
    GUARD(5, "guard"),
    SKITTISH(6, "skittish");

    private int index;
    private String saveName;
    private String unlocalizedTip;
    private String unlocalizedName;
    private String unlocalizedInfo;

    public static final Mode[] VALUES = Arrays.stream(Mode.values()).sorted(Comparator.comparingInt(Mode::getIndex)).toArray(size -> {
        return new Mode[size];
    });

    private Mode(int index, String name) {
        this(index, name, "cat.mode." + name, "cat.mode." + name + ".indicator", "cat.mode." + name + ".description");
    }

    private Mode(int index, String mode, String unlocalizedName, String tip, String info) {
        this.index = index;
        this.saveName = mode;
        this.unlocalizedName = unlocalizedName;
        this.unlocalizedTip = tip;
        this.unlocalizedInfo = info;
    }

    public int getIndex() {
        return this.index;
    }

    public String getSaveName() {
        return this.saveName;
    }

    public String getTip() {
        return this.unlocalizedTip;
    }

    public String getUnlocalizedName() {
        return this.unlocalizedName;
    }

    public String getUnlocalizedInfo() {
        return this.unlocalizedInfo;
    }

    public void onModeSet(AbstractCatEntity cat, Mode prev) {
        switch(prev) {
            default:
                cat.getNavigation().stop();
                cat.setTarget(null);
                cat.setLastHurtByMob(null);
                break;
        }
    }

    public Mode previousMode() {
        int i = this.getIndex() - 1;
        if(i < 0) {
            i = VALUES.length - 1;
        }
        return VALUES[i];
    }

    public Mode nextMode() {
        int i = this.getIndex() + 1;
        if(i >= VALUES.length) {
            i = 0;
        }

        return VALUES[i];
    }

    public static Mode byIndex(int i) {
        if(i < 0 || i >= VALUES.length) {
            i = Mode.DOCILE.getIndex();
        }

        return VALUES[i];
    }

    public static Mode bySaveName(String saveName) {
        for(Mode value : Mode.values()) {
            if(value.saveName.equals(saveName)) {
                return value;
            }
        }

        return DOCILE;
    }
}
