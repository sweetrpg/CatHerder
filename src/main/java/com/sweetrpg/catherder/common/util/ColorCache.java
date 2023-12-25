package com.sweetrpg.catherder.common.util;

public class ColorCache {

    public static final ColorCache WHITE = new ColorCache(16632255);

    private final int color;
    private float[] floatArray;

    protected ColorCache(int color) {
        this.color = color;
    }

    public int get() {
        return this.color;
    }

    public float[] getFloatArray() {
        if (this.floatArray == null) {
            this.floatArray = Util.rgbIntToFloatArray(this.color);
        }

        return this.floatArray;
    }

    public static ColorCache make(int color) {
        return new ColorCache(color);
    }

    @Override
    public int hashCode() {
        return this.color;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj.getClass() == ColorCache.class)) {
            return false;
        }

        if (!(obj instanceof ColorCache)) {
            return false;
        }

        ColorCache other = (ColorCache) obj;
        return other.color == this.color;
    }

    public boolean is(int colorIn) {
        return this.color == colorIn;
    }
}
