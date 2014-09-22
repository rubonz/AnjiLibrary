package com.darkempire.anjifx.beans.property;

import com.darkempire.anjifx.beans.BeanType;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 02.12.13
 * Time: 11:46
 * To change this template use File | Settings | File Templates.
 */
public class AnjiStringProperty extends AbstractAnjiProperty<String> {
    private String value;

    public AnjiStringProperty(String name) {
        super(name);
        value = "";
    }

    public AnjiStringProperty(String name, String value) {
        super(name);
        this.value = value;
    }

    public AnjiStringProperty(Object bean, String name) {
        super(bean, name);
        value = "";
    }

    public AnjiStringProperty(Object bean, String name, String value) {
        super(bean, name);
        this.value = value;
    }

    @Override
    protected void set(String value) {
        this.value = value;
    }

    @Override
    public BeanType getType() {
        return BeanType.STRING_TYPE;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "AnjiStringProperty(" + getName() + ")=" + value;
    }
}
