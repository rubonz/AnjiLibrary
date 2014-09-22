package com.darkempire.anjifx.beans;

import com.darkempire.math.struct.Assignable;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 11.11.13
 * Time: 15:57
 * To change this template use File | Settings | File Templates.
 */
public interface IAnjiAssignableValue<T extends Assignable<T>> extends IAnjiLinearAssignableValue<T> {

    default IAnjiAssignableValue<T> idivide(T T) {
        setValue(getValue().idivide(T));
        return this;
    }

    default IAnjiAssignableValue<T> iinverse() {
        setValue(getValue().iinverse());
        return this;
    }

    default IAnjiAssignableValue<T> imultiply(T T) {
        setValue(getValue().imultiply(T));
        return this;
    }

}
