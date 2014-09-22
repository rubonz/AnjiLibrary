package com.darkempire.math.deep.simple;

import com.darkempire.math.deep.Field;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 02.11.13
 * Time: 22:32
 * To change this template use File | Settings | File Templates.
 */
public class DoubleField implements Field<DoubleFieldElement> {
    private static DoubleField ourInstance = new DoubleField();

    public static DoubleField getInstance() {
        return ourInstance;
    }

    private DoubleField() {
    }

    private static final DoubleFieldElement zero = new DoubleFieldElement(0);
    private static final DoubleFieldElement one = new DoubleFieldElement(1);

    @Override
    public DoubleFieldElement getZero() {
        return zero;
    }

    @Override
    public DoubleFieldElement getOne() {
        return one;
    }

    @Override
    public DoubleFieldElement getNewZero() {
        return zero.clone();
    }

    @Override
    public DoubleFieldElement getNewOne() {
        return one.clone();
    }
}
