package com.darkempire.math.struct;


/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 11.11.13
 * Time: 15:45
 * To change this template use File | Settings | File Templates.
 */
public interface LinearModifable<T extends LinearModifable<T>> extends Cloneable {
    public T add(T t);

    public T subtract(T t);

    public T negate();
}
