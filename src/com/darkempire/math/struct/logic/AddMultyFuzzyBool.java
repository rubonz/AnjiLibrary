package com.darkempire.math.struct.logic;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 11.11.13
 * Time: 18:21
 * To change this template use File | Settings | File Templates.
 */
public class AddMultyFuzzyBool extends FuzzyBool<AddMultyFuzzyBool> {
    private double value;

    //region Конструктори
    public AddMultyFuzzyBool() {
        value = 0;
    }

    public AddMultyFuzzyBool(double value) {
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
    public AddMultyFuzzyBool iand(AddMultyFuzzyBool addMultyFuzzyBool) {
        value *= addMultyFuzzyBool.value;
        return this;
    }

    @Override
    public AddMultyFuzzyBool ior(AddMultyFuzzyBool addMultyFuzzyBool) {
        value = value + addMultyFuzzyBool.value - value * addMultyFuzzyBool.value;
        return this;
    }

    @Override
    public AddMultyFuzzyBool ixor(AddMultyFuzzyBool addMultyFuzzyBool) {
        double b = addMultyFuzzyBool.value;
        double a = value;
        value = -a * a * b * b + a * a * b + a * b * b - 3 * a * b + a + b;
        return this;
    }

    @Override
    public AddMultyFuzzyBool inot() {
        value = 1 - value;
        return this;
    }
    //endregion

    //region Логічні операції
    @Override
    public AddMultyFuzzyBool and(AddMultyFuzzyBool addMultyFuzzyBool) {
        return new AddMultyFuzzyBool(value * addMultyFuzzyBool.value);
    }

    @Override
    public AddMultyFuzzyBool or(AddMultyFuzzyBool addMultyFuzzyBool) {
        return new AddMultyFuzzyBool(value + addMultyFuzzyBool.value - value * addMultyFuzzyBool.value);
    }

    @Override
    public AddMultyFuzzyBool xor(AddMultyFuzzyBool addMultyFuzzyBool) {
        double b = addMultyFuzzyBool.value;
        double a = value;
        return new AddMultyFuzzyBool(-a * a * b * b + a * a * b + a * b * b - 3 * a * b + a + b);
    }

    @Override
    public AddMultyFuzzyBool not() {
        return new AddMultyFuzzyBool(1 - value);
    }
    //endregion

    //region Системні функції
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o instanceof AddMultyFuzzyBool) {
            return value == ((AddMultyFuzzyBool) o).value;
        }
        return false;
    }

    @Override
    public String toString() {
        return "addmulty:" + value;
    }

    @Override
    public AddMultyFuzzyBool clone() {
        return new AddMultyFuzzyBool(value);
    }
    //endregion

    //region Розпізнання
    protected static AddMultyFuzzyBool valueOf(String v) {
        return new AddMultyFuzzyBool(Double.valueOf(v.replace("addmulty:", "")));
    }
    //endregion

    @Override
    public AddMultyFuzzyBool deepcopy() {
        return clone();
    }
}
