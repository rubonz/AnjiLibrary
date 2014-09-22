package com.darkempire.math.struct.function.interfaces;

import com.darkempire.math.struct.matrix.ILinearMatrixProvider;

/**
 * Create in 18:22
 * Created by siredvin on 18.12.13.
 */
@FunctionalInterface
public interface FLinearMatrixAndIndexToLinear<T extends com.darkempire.math.struct.Number<T>> {
    public T calc(ILinearMatrixProvider<T> matrix, int rowIndex, int columnIndex);
}
