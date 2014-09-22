package com.darkempire.math.struct;

/**
 * Created by siredvin on 05.07.14.
 */
public interface Number<T extends Number<T>> extends LinearCalcable<T>, Calcable<T>, Comparable<T> {

    public int intValue();

    public long longValue();

    public float floatValue();

    public double doubleValue();

    public T getZero();

    public T getOne();

    public T deepcopy();
}
