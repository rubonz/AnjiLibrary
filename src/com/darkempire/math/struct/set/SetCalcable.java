package com.darkempire.math.struct.set;

/**
 * Create in 13:50
 * Created by siredvin on 23.04.14.
 */
public interface SetCalcable<T extends SetCalcable<T>> {
    public T union(T t);

    public T intersection(T t);

    public T complement();

    public T setminus(T t);
}
