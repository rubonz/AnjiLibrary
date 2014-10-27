package com.darkempire.math.struct.function.interfaces;

/**
 * Created by siredvin on 27.10.14.
 *
 * @author siredvin
 */
@FunctionalInterface
public interface FMatrixSomeAndIndexToSome<T> {
    public T calc(T x, int rowIndex, int columnIndex);
}
