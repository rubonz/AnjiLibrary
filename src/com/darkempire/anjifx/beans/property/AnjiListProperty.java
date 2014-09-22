package com.darkempire.anjifx.beans.property;

import com.darkempire.anjifx.beans.BeanType;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 13.11.13
 * Time: 16:25
 * To change this template use File | Settings | File Templates.
 */
public class AnjiListProperty<K> extends AbstractAnjiProperty<List<K>> implements com.darkempire.anjifx.beans.IAnjiCollectionValue<List<K>, K> {
    private List<K> value;

    public AnjiListProperty(String name, List<K> value) {
        super(name);
        this.value = value;
    }

    public AnjiListProperty(Object bean, String name, List<K> value) {
        super(bean, name);
        this.value = value;
    }

    @Override
    protected void set(List<K> value) {
        this.value = value;
    }


    @Override
    public BeanType getType() {
        return BeanType.LIST_TYPE;
    }

    @Override
    public List<K> getValue() {
        return value;
    }
}
