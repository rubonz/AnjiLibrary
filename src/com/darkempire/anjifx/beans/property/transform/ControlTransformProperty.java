package com.darkempire.anjifx.beans.property.transform;

import com.darkempire.anjifx.beans.BeanType;
import com.darkempire.anjifx.beans.property.AbstractAnjiProperty;
import javafx.beans.Observable;
import javafx.beans.property.Property;

/**
 * Created by siredvin on 20.03.14.
 */
public class ControlTransformProperty<K, V> extends AbstractAnjiProperty<V> {
    private V value;
    private Property<K> property;
    private ITransform<K, V> transform;

    public ControlTransformProperty(Property<K> property, ITransform<K, V> transform) {
        super(property.getBean(), property.getName());
        property.addListener(this::setKeyValue);
        this.property = property;
        this.transform = transform;
    }

    @Override
    protected void set(V value) {
        //property.setValue(getRule.value(value));
        this.value = value;
    }

    private void setKeyValue(Observable observable) {
        setValue(transform.key(property.getValue()));
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
