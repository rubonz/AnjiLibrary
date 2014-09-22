package com.darkempire.math.struct;


/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 11.11.13
 * Time: 11:29
 * To change this template use File | Settings | File Templates.
 */
public interface LinearAssignable<T extends LinearAssignable<T>> extends Cloneable {
    public T inegate();

    public T iadd(T t);

    public T isubtract(T t);
}
