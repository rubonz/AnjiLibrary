package com.darkempire.math.deep.simple;

import com.darkempire.math.deep.Field;
import com.darkempire.math.deep.FieldElement;
import com.darkempire.math.deep.LinearSpace;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 02.11.13
 * Time: 22:27
 * To change this template use File | Settings | File Templates.
 */
public class DoubleFieldElement implements FieldElement<DoubleFieldElement> {
    private double d;

    public DoubleFieldElement(double d) {
        this.d = d;
    }

    public double getValue() {
        return d;
    }

    @Override
    public DoubleFieldElement inverse() {
        d = 1 / d;
        return this;
    }

    @Override
    public Field<DoubleFieldElement> getField() {
        return DoubleField.getInstance();
    }

    @Override
    public DoubleFieldElement add(DoubleFieldElement doubleFieldElement) {
        d += doubleFieldElement.d;
        return this;
    }

    @Override
    public DoubleFieldElement subtract(DoubleFieldElement doubleFieldElement) {
        d -= doubleFieldElement.d;
        return this;
    }

    @Override
    public DoubleFieldElement negate() {
        d = -d;
        return this;
    }

    @Override
    public DoubleFieldElement multiply(DoubleFieldElement doubleFieldElement) {
        d *= doubleFieldElement.d;
        return this;
    }

    @Override
    public DoubleFieldElement divide(DoubleFieldElement doubleFieldElement) {
        d /= doubleFieldElement.d;
        return this;
    }

    @Override
    public LinearSpace<DoubleFieldElement, DoubleFieldElement> getSpace() {
        return getField();
    }

    @Override
    public DoubleFieldElement clone() {
        return new DoubleFieldElement(d);
    }

    @Override
    public String toString() {
        return String.valueOf(d);
    }
}
