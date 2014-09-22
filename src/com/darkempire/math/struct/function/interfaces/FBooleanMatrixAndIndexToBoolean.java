package com.darkempire.math.struct.function.interfaces;

import com.darkempire.math.struct.matrix.IBooleanMatrixProvider;

/**
 * Create in 18:22
 * Created by siredvin on 18.12.13.
 */
@FunctionalInterface
public interface FBooleanMatrixAndIndexToBoolean {
    public boolean calc(IBooleanMatrixProvider matrix, int rowIndex, int columnIndex);
}
