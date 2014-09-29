package com.darkempire.math.struct.function.interfaces;

import com.darkempire.math.struct.matrix.INumberMatrixProvider;

/**
 * Create in 18:22
 * Created by siredvin on 18.12.13.
 */
@FunctionalInterface
public interface FNumberMatrixAndIndexToNumber<T extends com.darkempire.math.struct.Number<T>> {
    public T calc(INumberMatrixProvider<T> matrix, int rowIndex, int columnIndex);
}
