package com.darkempire.math.struct.matrix;

import com.darkempire.math.struct.LinearCalcable;

/**
 * Create in 23:39
 * Created by siredvin on 24.04.14.
 */
public interface ILinearMatrixProvider<T extends LinearCalcable<T>> {
    public T get(int rowIndex, int columnIndex);

    public int getRowCount();

    public int getColumnCount();
}
