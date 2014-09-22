package com.darkempire.anjifx.beans.value;

import com.darkempire.anjifx.beans.*;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 11.11.13
 * Time: 16:59
 * To change this template use File | Settings | File Templates.
 */
public class AnjiLongValue extends AbstractAnjiValue<Long> implements com.darkempire.anjifx.beans.IAnjiNumberValue<Long> {
    private long value;

    public AnjiLongValue() {
        this.value = 0;
    }

    public AnjiLongValue(long value) {
        this.value = value;
    }

    @Override
    protected void set(Long value) {
        this.value = value;
    }

    public long get() {
        return value;
    }

    public void inc() {
        setValue(value + 1);
    }

    public void dec() {
        setValue(value - 1);
    }

    @Override
    public Long getValue() {
        return value;
    }

    @Override
    public BeanType getType() {
        return BeanType.LONG_TYPE;
    }
}
