package com.darkempire.anjifx.beans.property;

import com.darkempire.anjifx.beans.*;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 11.11.13
 * Time: 16:59
 * To change this template use File | Settings | File Templates.
 */
public class AnjiLongProperty extends AbstractAnjiProperty<Long> implements com.darkempire.anjifx.beans.IAnjiNumberValue<Long> {
    private long value;

    public AnjiLongProperty(String name) {
        super(name);
        this.value = 0;
    }

    public AnjiLongProperty(Object bean, String name) {
        super(bean, name);
        this.value = 0;
    }

    public AnjiLongProperty(String name, long value) {
        super(name);
        this.value = value;
    }

    public AnjiLongProperty(Object bean, String name, long value) {
        super(bean, name);
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
