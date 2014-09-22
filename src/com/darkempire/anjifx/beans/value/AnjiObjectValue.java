package com.darkempire.anjifx.beans.value;

import com.darkempire.anjifx.annotation.AnjiFXClassConstructor;
import com.darkempire.anjifx.beans.BeanType;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 14.11.13
 * Time: 11:45
 * To change this template use File | Settings | File Templates.
 */
@AnjiFXClassConstructor(isEmptyExist = false)
public class AnjiObjectValue<T> extends AbstractAnjiValue<T> {
    private T value;

    public AnjiObjectValue(T value) {
        this.value = value;
    }

    @Override
    protected void set(T value) {
        this.value = value;
    }

    @Override
    public BeanType getType() {
        return BeanType.OBJECT_TYPE;
    }

    @Override
    public T getValue() {
        return value;
    }
}
