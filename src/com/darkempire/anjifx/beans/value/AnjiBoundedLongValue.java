package com.darkempire.anjifx.beans.value;

import com.darkempire.anjifx.beans.BeanType;
import com.darkempire.anjifx.beans.IAnjiNumberValue;


/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 27.11.13
 * Time: 11:46
 * To change this template use File | Settings | File Templates.
 */
public class AnjiBoundedLongValue extends AbstractAnjiValue<Long> implements IAnjiNumberValue<Long> {
    private long value;
    private long maxValue;
    private long minValue;

    public AnjiBoundedLongValue(long minValue, long maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.value = (minValue + maxValue) / 2;
    }

    public AnjiBoundedLongValue(long minValue, long maxValue, long value) {
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
        return (value > minValue) && (value < maxValue);
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
    }

    public void setMinValue(long minValue) {
        this.minValue = minValue;
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
