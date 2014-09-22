package com.darkempire.anjifx.beans;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 11.11.13
 * Time: 15:38
 * To change this template use File | Settings | File Templates.
 */
public interface IAnjiNumberValue<T extends Number> extends IAnjiValue<T> {
    default double doubleValue() {
        return getValue().doubleValue();
    }

    default int intValue() {
        return getValue().intValue();
    }

    default long longValue() {
        return getValue().longValue();
    }

    default float floatValue() {
        return getValue().floatValue();
    }
}
