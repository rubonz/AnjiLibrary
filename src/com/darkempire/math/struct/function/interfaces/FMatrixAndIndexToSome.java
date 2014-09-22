package com.darkempire.math.struct.function.interfaces;

import com.darkempire.math.struct.matrix.IMatrixProvider;

/**
 * Create in 18:22
 * Created by siredvin on 18.12.13.
 */
@FunctionalInterface
public interface FMatrixAndIndexToSome<T> {
    public T calc(IMatrixProvider<T> matrix, int rowIndex, int columnIndex);
}
