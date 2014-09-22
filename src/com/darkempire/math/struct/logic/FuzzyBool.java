package com.darkempire.math.struct.logic;

import com.darkempire.math.struct.LogicCalcable;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 11.11.13
 * Time: 15:43
 * To change this template use File | Settings | File Templates.
 */
public abstract class FuzzyBool<T extends FuzzyBool<T>> implements LogicCalcable<T>, Comparable<FuzzyBool<T>> {
    public abstract double value();

    public boolean exists() {
        return value() > 0.5;
    }

    //region Системні методи
    @Override
    public abstract String toString();

    @Override
    public int hashCode() {
        return ((Double) value()).hashCode();
    }
    //endregion

    //region Порівняння
    @Override
    public int compareTo(FuzzyBool<T> value) {
        return (int) (Math.signum(value() - value.value()) * 1);
    }
    //endregion

    //region Розбір
    public static FuzzyBool parseFuzzyBool(String value) {
        if (value.startsWith("addmulty:"))
            return AddMultyFuzzyBool.valueOf(value);
        if (value.startsWith("minmax:"))
            return MinMaxFuzzyBool.valueOf(value);
        throw new IllegalArgumentException();
    }
    //endregion
}
