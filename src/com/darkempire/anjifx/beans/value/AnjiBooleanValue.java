package com.darkempire.anjifx.beans.value;

import com.darkempire.anjifx.beans.*;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 11.11.13
 * Time: 17:17
 * To change this template use File | Settings | File Templates.
 */
public class AnjiBooleanValue extends AbstractAnjiValue<Boolean> implements com.darkempire.anjifx.beans.IAnjiLogicValue<Boolean> {

    private boolean value;

    public AnjiBooleanValue() {
        value = false;
    }

    public AnjiBooleanValue(boolean value) {
        this.value = value;
    }

    @Override
    protected void set(Boolean value) {
        this.value = value;
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public AnjiBooleanValue ior(Boolean aBoolean) {
        value = value || aBoolean;
        setValue(value);
        return this;
    }

    @Override
    public AnjiBooleanValue iand(Boolean aBoolean) {
        value = value && aBoolean;
        setValue(value);
        return this;
    }

    @Override
    public AnjiBooleanValue ixor(Boolean aBoolean) {
        value = value ^ aBoolean;
        setValue(value);
        return this;
    }

    @Override
    public AnjiBooleanValue inot() {
        value = !value;
        setValue(value);
        return this;
    }

    @Override
    public boolean get() {
        return value;
    }

    @Override
    public BeanType getType() {
        return BeanType.BOOLEAN_TYPE;
    }
}
