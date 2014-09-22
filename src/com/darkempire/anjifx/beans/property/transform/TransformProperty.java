package com.darkempire.anjifx.beans.property.transform;

import com.darkempire.anjifx.beans.BeanType;
import com.darkempire.anjifx.beans.property.AbstractAnjiProperty;
import javafx.beans.property.Property;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 27.11.13
 * Time: 20:57
 * To change this template use File | Settings | File Templates.
 * Я його ввів, але він загалом повністю поламаний.
 */
@Deprecated
public class TransformProperty<K, V> extends AbstractAnjiProperty<V> {
    private Property<K> property;
    private ITransform<K, V> transform;

    public TransformProperty(Property<K> property, ITransform<K, V> transform) {
        super(property.getBean(), property.getName());
        this.property = property;
        this.transform = transform;
    }

    @Override
    protected void set(V value) {
        property.setValue(transform.value(value));
    }

    @Override
    public BeanType getType() {
        return BeanType.OBJECT_TYPE;
    }

    @Override
    public V getValue() {
        return transform.key(property.getValue());
    }

}
