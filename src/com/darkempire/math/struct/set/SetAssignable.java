package com.darkempire.math.struct.set;

/**
 * Create in 13:50
 * Created by siredvin on 23.04.14.
 */
public interface SetAssignable<T extends SetAssignable<T>> {
    public T iunion(T t);

    public T iintersection(T t);

    public T icomplement();

    public T isetminus(T t);
}
