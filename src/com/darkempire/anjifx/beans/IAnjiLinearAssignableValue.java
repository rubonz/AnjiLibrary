package com.darkempire.anjifx.beans;

import com.darkempire.math.struct.LinearAssignable;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 11.11.13
 * Time: 15:56
 * To change this template use File | Settings | File Templates.
 */
public interface IAnjiLinearAssignableValue<T extends LinearAssignable<T>> extends IAnjiValue<T> {
    default IAnjiLinearAssignableValue<T> iadd(T T) {
        setValue(getValue().iadd(T));
        return this;
    }

    default IAnjiLinearAssignableValue<T> isubtract(T T) {
        setValue(getValue().isubtract(T));
        return this;
    }

    default IAnjiLinearAssignableValue<T> inegate() {
        setValue(getValue().inegate());
        return this;
    }
}
