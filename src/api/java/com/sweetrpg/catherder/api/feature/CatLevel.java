package com.sweetrpg.catherder.api.feature;

public class CatLevel {

    private int level;
    private int direLevel;

    public static enum Type {
        NORMAL("normal_treat"),
        DIRE("dire_treat");

        String n;

        Type(String n) {
            this.n = n;
        }

        public String getName() {
            return this.n;
        }
    }

    public CatLevel(int level, int direLevel) {
        this.level = level;
        this.direLevel = direLevel;
    }

    public int getLevel(Type type) {
        return type == Type.DIRE ? this.direLevel : this.level;
    }

    public boolean canIncrease(Type type) {
        return type == Type.DIRE ? this.level >= 60 : true;
    }

    @Deprecated
    public void setLevel(Type type, int level) {
        if (type == Type.DIRE) {
            this.direLevel = level;
        } else {
            this.level = level;
        }
    }

    @Deprecated
    public void incrementLevel(Type type) {
        this.setLevel(type, this.getLevel(type) + 1);
    }

    public CatLevel copy() {
        return new CatLevel(this.level, this.direLevel);
    }

    /**
     * Combines parents levels together
     */
    public CatLevel combine(CatLevel levelIn) {
        int combinedLevel = this.getLevel(Type.NORMAL) + levelIn.getLevel(Type.NORMAL);
        combinedLevel /= 2;
        combinedLevel = Math.min(combinedLevel, 20);
        return new CatLevel(combinedLevel, 0);
    }

    public final boolean isDireCat() {
        return this.direLevel >= 30;
    }

}
