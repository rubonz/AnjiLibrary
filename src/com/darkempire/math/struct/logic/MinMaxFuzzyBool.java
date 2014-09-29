package com.darkempire.math.struct.logic;

import static com.darkempire.math.utils.MathUtils.max;
import static com.darkempire.math.utils.MathUtils.min;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 11.11.13
 * Time: 18:09
 * To change this template use File | Settings | File Templates.
 */
public class MinMaxFuzzyBool extends FuzzyBool<MinMaxFuzzyBool> {
    private double value;

    //region Конструктори
    public MinMaxFuzzyBool() {
        value = 0;
    }

    public MinMaxFuzzyBool(double value) {
        if (value > 1 || value < 0) {
            this.value = 0;
        } else {
            this.value = value;
        }
    }
    //endregion

    @Override
    public double value() {
        return value;
    }


    //region Логічні операції з присвоєнням
    @Override
    public MinMaxFuzzyBool iand(MinMaxFuzzyBool minMaxFuzzyBool) {
        value = min(value, minMaxFuzzyBool.value);
        return this;
    }

    @Override
    public MinMaxFuzzyBool ior(MinMaxFuzzyBool minMaxFuzzyBool) {
        value = max(value, minMaxFuzzyBool.value);
        return this;
    }

    @Override
    public MinMaxFuzzyBool ixor(MinMaxFuzzyBool minMaxFuzzyBool) {
        double b = minMaxFuzzyBool.value;
        value = max(min(value, 1 - b), min(1 - value, b));
        return this;
    }

    @Override
    public MinMaxFuzzyBool inot() {
        value = 1 - value;
        return this;
    }
    //endregion

    //region Логічні операції
    @Override
    public MinMaxFuzzyBool and(MinMaxFuzzyBool minMaxFuzzyBool) {
        return new MinMaxFuzzyBool(min(value, minMaxFuzzyBool.value));
    }

    @Override
    public MinMaxFuzzyBool or(MinMaxFuzzyBool minMaxFuzzyBool) {
        return new MinMaxFuzzyBool(max(value, minMaxFuzzyBool.value));
    }

    @Override
    public MinMaxFuzzyBool xor(MinMaxFuzzyBool minMaxFuzzyBool) {
        double b = minMaxFuzzyBool.value;
        return new MinMaxFuzzyBool(max(min(value, 1 - b), min(1 - value, b)));
    }

    @Override
    public MinMaxFuzzyBool not() {
        return new MinMaxFuzzyBool(1 - value);
    }
    //endregion

    //region Системні функції
    @Override
    public MinMaxFuzzyBool clone() {
        return new MinMaxFuzzyBool(value);
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof MinMaxFuzzyBool && value == ((MinMaxFuzzyBool) o).value;
    }

    @Override
    public String toString() {
        return "minmax:" + value;
    }
    //endregion

    protected static MinMaxFuzzyBool valueOf(String v) {
        return new MinMaxFuzzyBool(Double.valueOf(v.replace("minmax:", "")));
    }

    @Override
    public MinMaxFuzzyBool deepcopy() {
        return clone();
    }
}
