package com.darkempire.anjifx.beans.value;

import com.darkempire.anji.log.Log;
import com.darkempire.anjifx.annotation.AnjiFXClassConstructor;
import com.darkempire.anjifx.beans.BeanType;

import java.lang.reflect.InvocationTargetException;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 18.11.13
 * Time: 9:02
 * To change this template use File | Settings | File Templates.
 */
@AnjiFXClassConstructor(isEmptyExist = false)
public class AnjiEnumValue<T extends Enum<T>> extends AbstractAnjiValue<T> {
    private T value;

    public AnjiEnumValue(T value) {
        this.value = value;
    }

    @Override
    protected void set(T value) {
        this.value = value;
    }

    @Override
    public BeanType getType() {
        return BeanType.ENUM_TYPE;
    }

    @Override
    public T getValue() {
        return value;
    }

    public void setValue(String s) {
        T temp = null;
        try {
            temp = (T) Enum.valueOf(value.getClass(), s);
        } finally {
            if (temp != null)
                setValue(temp);
        }
    }

    public T[] values() {
        T[] values = null;
        try {
            values = (T[]) value.getDeclaringClass().getMethod("values").invoke(value);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            Log.log(1, "Такого не має статися");
        }
        return values;
    }
}
