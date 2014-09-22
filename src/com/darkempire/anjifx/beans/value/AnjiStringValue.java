package com.darkempire.anjifx.beans.value;

import com.darkempire.anjifx.beans.BeanType;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 11.11.13
 * Time: 16:51
 * To change this template use File | Settings | File Templates.
 */
public class AnjiStringValue extends AbstractAnjiValue<String> {
    private String value;

    public AnjiStringValue() {
        value = "";
    }

    public AnjiStringValue(String value) {
        this.value = value;
    }

    @Override
    protected void set(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public BeanType getType() {
        return BeanType.STRING_TYPE;
    }
}
