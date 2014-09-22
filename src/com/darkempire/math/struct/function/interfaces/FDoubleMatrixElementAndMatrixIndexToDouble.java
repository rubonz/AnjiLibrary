package com.darkempire.math.struct.function.interfaces;

/**
 * Created by siredvin on 27.06.14.
 */
@FunctionalInterface
public interface FDoubleMatrixElementAndMatrixIndexToDouble {
    public double operate(double k, int rowIndex, int columnIndex);
}
