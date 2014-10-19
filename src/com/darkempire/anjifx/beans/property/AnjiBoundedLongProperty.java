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
public class AnjiBoundedLongProperty extends AbstractAnjiProperty<Long> implements IAnjiNumberValue<Long> {
    private long value;
    private long maxValue;
    private long minValue;

    public AnjiBoundedLongProperty(String name, long minValue, long maxValue) {
        super(name);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.value = (minValue + maxValue) / 2;
    }

    public AnjiBoundedLongProperty(Object bean, String name, long minValue, long maxValue) {
        super(bean, name);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.value = (minValue + maxValue) / 2;
    }

    public AnjiBoundedLongProperty(String name, long minValue, long maxValue, long value) {
        super(name);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.value = value;
    }

    public AnjiBoundedLongProperty(Object bean, String name, long minValue, long maxValue, long value) {
        super(bean, name);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.value = value;
    }

    @Override
    protected void set(Long value) {
        this.value = value;
    }

    public long get() {
        return value;
    }

    public boolean inBounds(long value) {
        return (value >= minValue) && (value <= maxValue);
    }

    public void inc() {
        setValue(value + 1);
    }

    public void dec() {
        setValue(value - 1);
    }

    public long getMaxValue() {
        return maxValue;
    }

    public long getMinValue() {
        return minValue;
    }

    public void setMaxValue(long maxValue) {
        this.maxValue = maxValue;
        if (value > maxValue) {
            setValue(maxValue);
        }
    }

    public void setMinValue(long minValue) {
        this.minValue = minValue;
        if (value < minValue) {
            setValue(minValue);
        }
    }

    @Override
    public BeanType getType() {
        return BeanType.LONG_BOUNDED_TYPE;
    }

    @Override
    public Long getValue() {
        return value;
    }

    @Override
    public void setValue(Long value) {
        if (inBounds(value))
            super.setValue(value);
    }
}
