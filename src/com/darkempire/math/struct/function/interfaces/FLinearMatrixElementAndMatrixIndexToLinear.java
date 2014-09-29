package com.darkempire.math.struct.function.interfaces;

import com.darkempire.math.struct.LinearCalcable;

/**
 * Created by siredvin on 27.06.14.
 */
@FunctionalInterface
public interface FLinearMatrixElementAndMatrixIndexToLinear<T extends LinearCalcable<T>> {
    public T operate(T k, int rowIndex, int columnIndex);
}
