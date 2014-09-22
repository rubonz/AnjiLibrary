package com.darkempire.anjifx.beans.property;

import com.darkempire.anjifx.beans.*;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 11.11.13
 * Time: 16:59
 * To change this template use File | Settings | File Templates.
 */
public class AnjiFloatProperty extends AbstractAnjiProperty<Float> implements com.darkempire.anjifx.beans.IAnjiNumberValue<Float> {
    private float value;

    public AnjiFloatProperty(String name) {
        super(name);
        value = 0;
    }

    public AnjiFloatProperty(Object bean, String name) {
        super(bean, name);
        value = 0;
    }

    public AnjiFloatProperty(String name, float value) {
        super(name);
        this.value = value;
    }

    public AnjiFloatProperty(Object bean, String name, float value) {
        super(bean, name);
        this.value = value;
    }

    @Override
    protected void set(Float value) {
        this.value = value;
    }

    public float get() {
        return value;
    }

    @Override
    public Float getValue() {
        return value;
    }

    @Override
    public BeanType getType() {
        return BeanType.FLOAT_TYPE;
    }
}
