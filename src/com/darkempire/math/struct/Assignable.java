package com.darkempire.math.struct;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 11.11.13
 * Time: 11:29
 * To change this template use File | Settings | File Templates.
 */
public interface Assignable<T extends Assignable<T>> extends LinearAssignable<T> {
    public T imultiply(T t);

    public T idivide(T t);

    public T iinverse();
}
