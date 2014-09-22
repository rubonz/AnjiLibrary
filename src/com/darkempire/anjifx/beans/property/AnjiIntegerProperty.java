package com.darkempire.anjifx.beans.property;

import com.darkempire.anjifx.beans.*;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 11.11.13
 * Time: 16:59
 * To change this template use File | Settings | File Templates.
 */
public class AnjiIntegerProperty extends AbstractAnjiProperty<Integer> implements com.darkempire.anjifx.beans.IAnjiNumberValue<Integer> {
    private int value;

    public AnjiIntegerProperty(String name) {
        super(name);
        value = 0;
    }

    public AnjiIntegerProperty(Object bean, String name) {
        super(bean, name);
        value = 0;
    }

    public AnjiIntegerProperty(String name, int value) {
        super(name);
        this.value = value;
    }

    public AnjiIntegerProperty(Object bean, String name, int value) {
        super(bean, name);
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

    @Override
    public String toString() {
        return "AnjiIntegerProperty(" + getName() + ")=" + value;
    }
}
