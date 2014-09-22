package com.darkempire.math.struct.function.interfaces;

/**
 * Created by siredvin on 27.06.14.
 */
@FunctionalInterface
public interface FBooleanMatrixElementAndMatrixIndexToBoolean {
    public boolean operate(boolean k, int rowIndex, int columnIndex);
}
