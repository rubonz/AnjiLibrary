package com.darkempire.math.struct.function.interfaces;

/**
 * Create in 18:22
 * Created by siredvin on 18.12.13.
 */
@FunctionalInterface
public interface FMatrixIndexToLinear<T extends com.darkempire.math.struct.Number<T>> {
    public T calc(int rowIndex, int columnIndex);
}
