package com.darkempire.math.struct.matrix;

/**
 * Create in 23:39
 * Created by siredvin on 24.04.14.
 */
public interface IMatrixProvider<T> {
    public T getEl(int rowIndex, int columnIndex);

    public int getRowCount();

    public int getColumnCount();
}
