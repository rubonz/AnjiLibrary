package com.darkempire.math.struct;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 11.11.13
 * Time: 15:47
 * To change this template use File | Settings | File Templates.
 */
public interface LogicModifable<T extends LogicModifable<T>> extends Cloneable {
    public T and(T t);

    public T or(T t);

    public T xor(T t);

    public T not();
}
