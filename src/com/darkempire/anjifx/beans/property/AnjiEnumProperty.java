package com.darkempire.anjifx.beans.property;

import com.darkempire.anjifx.beans.BeanType;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 18.11.13
 * Time: 9:02
 * To change this template use File | Settings | File Templates.
 */
public class AnjiEnumProperty<T extends Enum<T>> extends AbstractAnjiProperty<T> {
    private T value;

    public AnjiEnumProperty(String name, T value) {
        super(name);
        this.value = value;
    }

    public AnjiEnumProperty(Object bean, String name, T value) {
        super(bean, name);
        this.value = value;
    }

    @Override
    protected void set(T value) {
        this.value = value;
    }

    @Override
    public BeanType getType() {
        return BeanType.ENUM_TYPE;
    }

    @Override
    public T getValue() {
        return value;
    }

    public void setValue(String s) {
        T temp = null;
        try {
            temp = (T) Enum.valueOf(value.getClass(), s);
        } finally {
            if (temp != null)
                setValue(temp);
        }
    }

    public T[] values() {
        return value.getDeclaringClass().getEnumConstants();
    }
}
