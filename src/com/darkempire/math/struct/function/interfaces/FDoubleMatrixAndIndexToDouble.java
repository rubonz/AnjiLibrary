package com.darkempire.math.struct.function.interfaces;

import com.darkempire.math.struct.matrix.IDoubleMatrixProvider;

/**
 * Create in 18:22
 * Created by siredvin on 18.12.13.
 */
@FunctionalInterface
public interface FDoubleMatrixAndIndexToDouble {
    public double calc(IDoubleMatrixProvider matrix, int rowIndex, int columnIndex);
}
