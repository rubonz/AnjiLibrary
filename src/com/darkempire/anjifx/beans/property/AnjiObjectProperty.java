package com.darkempire.anjifx.beans.property;

import com.darkempire.anjifx.beans.BeanType;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 02.12.13
 * Time: 11:44
 * To change this template use File | Settings | File Templates.
 */
public class AnjiObjectProperty<K> extends AbstractAnjiProperty<K> {
    private K value;

    public AnjiObjectProperty(String name, K value) {
        super(name);
        this.value = value;
    }

    public AnjiObjectProperty(Object bean, String name, K value) {
        super(bean, name);
        this.value = value;
    }

    @Override
    protected void set(K value) {
        this.value = value;
    }

    @Override
    public BeanType getType() {
        return BeanType.OBJECT_TYPE;
    }

    @Override
    public K getValue() {
        return value;
    }
}
