package com.darkempire.math.struct;


/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 11.11.13
 * Time: 15:46
 * To change this template use File | Settings | File Templates.
 */
public interface Modifable<T extends Modifable<T>> extends LinearModifable<T> {
    public T multiply(T t);

    public T divide(T t);

    public T inverse();
}
