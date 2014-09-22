package com.darkempire.anjifx.beans;

import javafx.beans.value.ObservableValue;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 11.11.13
 * Time: 15:37
 * To change this template use File | Settings | File Templates.
 */
public interface IAnjiReadOnlyValue<T> extends ObservableValue<T> {
    public BeanType getType();
}
