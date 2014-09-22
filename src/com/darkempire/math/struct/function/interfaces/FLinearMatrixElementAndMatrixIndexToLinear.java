package com.darkempire.math.struct.function.interfaces;

/**
 * Created by siredvin on 27.06.14.
 */
@FunctionalInterface
public interface FLinearMatrixElementAndMatrixIndexToLinear<T extends com.darkempire.math.struct.Number<T>> {
    public T operate(T k, int rowIndex, int columnIndex);
}
