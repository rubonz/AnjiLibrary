package com.darkempire.anjifx.beans.property;

import com.darkempire.anjifx.beans.*;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 11.11.13
 * Time: 17:17
 * To change this template use File | Settings | File Templates.
 */
public class AnjiBooleanProperty extends AbstractAnjiProperty<Boolean> implements com.darkempire.anjifx.beans.IAnjiLogicValue<Boolean> {
    private boolean value;

    public AnjiBooleanProperty(String name) {
        super(name);
        value = false;
    }

    public AnjiBooleanProperty(Object bean, String name) {
        super(bean, name);
        value = false;
    }

    public AnjiBooleanProperty(String name, boolean value) {
        super(name);
        this.value = value;
    }

    public AnjiBooleanProperty(Object bean, String name, boolean value) {
        super(bean, name);
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
    public AnjiBooleanProperty ior(Boolean aBoolean) {
        value = value || aBoolean;
        setValue(value);
        return this;
    }

    @Override
    public AnjiBooleanProperty iand(Boolean aBoolean) {
        value = value && aBoolean;
        setValue(value);
        return this;
    }

    @Override
    public AnjiBooleanProperty ixor(Boolean aBoolean) {
        value = value ^ aBoolean;
        setValue(value);
        return this;
    }

    @Override
    public AnjiBooleanProperty inot() {
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
