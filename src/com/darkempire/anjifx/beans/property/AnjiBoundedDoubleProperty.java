package com.darkempire.anjifx.beans.property;

import com.darkempire.anjifx.beans.BeanType;
import com.darkempire.anjifx.beans.IAnjiNumberValue;


/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 27.11.13
 * Time: 11:46
 * To change this template use File | Settings | File Templates.
 */
public class AnjiBoundedDoubleProperty extends AbstractAnjiProperty<Double> implements IAnjiNumberValue<Double> {
    private double value;
    private double maxValue;
    private double minValue;

    public AnjiBoundedDoubleProperty(String name, double minValue, double maxValue) {
        super(name);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.value = (minValue + maxValue) / 2;
    }

    public AnjiBoundedDoubleProperty(Object bean, String name, double minValue, double maxValue) {
        super(bean, name);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.value = (minValue + maxValue) / 2;
    }

    public AnjiBoundedDoubleProperty(String name, double minValue, double maxValue, double value) {
        super(name);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.value = value;
    }

    public AnjiBoundedDoubleProperty(Object bean, String name, double minValue, double maxValue, double value) {
        super(bean, name);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.value = value;
    }

    @Override
    protected void set(Double value) {
        this.value = value;
    }

    public double get() {
        return value;
    }

    public boolean inBounds(double value) {
        return (value >= minValue) && (value <= maxValue);
    }

    public double getMaxValue() {
        return maxValue;
    }

    public double getMinValue() {
        return minValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
        if (value > maxValue) {
            setValue(maxValue);
        }
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
        if (value < minValue) {
            setValue(minValue);
        }
    }

    @Override
    public BeanType getType() {
        return BeanType.DOUBLE_BOUNDED_TYPE;
    }

    @Override
    public Double getValue() {
        return value;
    }

    @Override
    public void setValue(Double value) {
        if (inBounds(value))
            super.setValue(value);
        else
            super.setValue(this.value);
    }
}
