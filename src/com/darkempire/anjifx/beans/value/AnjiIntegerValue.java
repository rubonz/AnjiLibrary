package com.darkempire.anjifx.beans.value;

import com.darkempire.anjifx.beans.*;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 11.11.13
 * Time: 16:59
 * To change this template use File | Settings | File Templates.
 */
public class AnjiIntegerValue extends AbstractAnjiValue<Integer> implements com.darkempire.anjifx.beans.IAnjiNumberValue<Integer> {
    private int value;

    public AnjiIntegerValue() {
        value = 0;
    }

    public AnjiIntegerValue(int value) {
        this.value = value;
    }

    @Override
    protected void set(Integer value) {
        this.value = value;
    }

    public int get() {
        return value;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public void inc() {
        setValue(value + 1);
    }

    public void dec() {
        setValue(value - 1);
    }

    @Override
    public BeanType getType() {
        return BeanType.INT_TYPE;
    }
}
