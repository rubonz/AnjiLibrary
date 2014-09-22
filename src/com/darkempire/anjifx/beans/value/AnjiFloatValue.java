package com.darkempire.anjifx.beans.value;

import com.darkempire.anjifx.beans.*;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 11.11.13
 * Time: 16:59
 * To change this template use File | Settings | File Templates.
 */
public class AnjiFloatValue extends AbstractAnjiValue<Float> implements com.darkempire.anjifx.beans.IAnjiNumberValue<Float> {
    private float value;

    public AnjiFloatValue() {
        value = 0;
    }

    public AnjiFloatValue(float value) {
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
