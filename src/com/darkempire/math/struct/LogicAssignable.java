package com.darkempire.math.struct;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 11.11.13
 * Time: 15:46
 * To change this template use File | Settings | File Templates.
 */
public interface LogicAssignable<T extends LogicAssignable<T>> extends Cloneable {
    public T iand(T t);

    public T ior(T t);

    public T ixor(T t);

    public T inot();
}
