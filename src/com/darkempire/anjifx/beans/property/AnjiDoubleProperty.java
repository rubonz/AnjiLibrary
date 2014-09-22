package com.darkempire.anjifx.beans.property;

import com.darkempire.anjifx.beans.*;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 11.11.13
 * Time: 16:59
 * To change this template use File | Settings | File Templates.
 */
public class AnjiDoubleProperty extends AbstractAnjiProperty<Double> implements com.darkempire.anjifx.beans.IAnjiNumberValue<Double> {
    private double value;

    public AnjiDoubleProperty(String name) {
        super(name);
        this.value = 0;
    }

    public AnjiDoubleProperty(Object bean, String name) {
        super(bean, name);
        this.value = 0;
    }

    public AnjiDoubleProperty(String name, double value) {
        super(name);
        this.value = value;
    }

    public AnjiDoubleProperty(Object bean, String name, double value) {
        super(bean, name);
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
