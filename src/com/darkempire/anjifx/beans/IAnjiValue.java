package com.darkempire.anjifx.beans;

import javafx.beans.value.WritableValue;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 11.11.13
 * Time: 15:38
 * To change this template use File | Settings | File Templates.
 */
public interface IAnjiValue<T> extends IAnjiReadOnlyValue<T>, WritableValue<T> {
}
