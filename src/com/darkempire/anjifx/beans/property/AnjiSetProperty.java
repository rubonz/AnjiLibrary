package com.darkempire.anjifx.beans.property;

import com.darkempire.anjifx.beans.BeanType;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 13.11.13
 * Time: 16:34
 * To change this template use File | Settings | File Templates.
 */
public class AnjiSetProperty<E> extends AbstractAnjiProperty<Set<E>> implements com.darkempire.anjifx.beans.IAnjiCollectionValue<Set<E>, E> {
    private Set<E> value;

    public AnjiSetProperty(String name, Set<E> value) {
        super(name);
        this.value = value;
    }

    public AnjiSetProperty(Object bean, String name, Set<E> value) {
        super(bean, name);
        this.value = value;
    }

    @Override
    protected void set(Set<E> value) {
        this.value = value;
    }

    @Override
    public BeanType getType() {
        return BeanType.SET_TYPE;
    }

    @Override
    public Set<E> getValue() {
        return value;
    }
}
