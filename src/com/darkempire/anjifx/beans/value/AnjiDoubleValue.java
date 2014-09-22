package com.darkempire.anjifx.beans.value;

import com.darkempire.anjifx.beans.*;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 11.11.13
 * Time: 16:59
 * To change this template use File | Settings | File Templates.
 */
public class AnjiDoubleValue extends AbstractAnjiValue<Double> implements com.darkempire.anjifx.beans.IAnjiNumberValue<Double> {
    private double value;

    public AnjiDoubleValue() {
        this.value = 0;
    }

    public AnjiDoubleValue(double value) {
        this.value = value;
    }

    @Override
    protected void set(Double value) {
        this.value = value;
    }

    public double get() {
        return value;
    }

    @Override
    public Double getValue() {
        return value;
    }

    @Override
    public BeanType getType() {
        return BeanType.DOUBLE_TYPE;
    }
}
