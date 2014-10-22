package com.darkempire.math.struct.matrix;

import com.darkempire.math.struct.*;
import com.darkempire.math.struct.Number;
import com.darkempire.math.struct.vector.DoubleVector;

/**
 * Created by siredvin on 21.10.14.
 *
 * @author siredvin
 */
public class NumberMatrixIndexHolder<T extends com.darkempire.math.struct.Number<T>> extends NumberMatrix<T> {
    private NumberMatrix<T> matrix;
    private int[] rowIndexes;
    private int[] columnIndexes;

    //region Конструктори
    public NumberMatrixIndexHolder(NumberMatrix<T> matrix) {
        this.matrix = matrix;
        rowIndexes = new int[matrix.getRowCount()];
        columnIndexes = new int[matrix.getColumnCount()];
        recalcRows();
        recalcColumns();
    }

    private NumberMatrixIndexHolder(NumberMatrix<T> matrix, int[] rowIndexes, int[] columnIndexes) {
        this.matrix = matrix;
        this.rowIndexes = rowIndexes;
        this.columnIndexes = columnIndexes;
    }
    //endregion

    public static <T extends Number<T>> NumberMatrixIndexHolder<T> indexWrap(NumberMatrix<T> matrix) {
        return new NumberMatrixIndexHolder<>(matrix);
    }

    //region Приватні методи керування
    private void checkSize() {
        int columnCount = matrix.getColumnCount();
        int rowCount = matrix.getRowCount();
        if (rowIndexes.length != rowCount) {
            rowIndexes = new int[rowCount];
            recalcRows();
        }
        if (columnIndexes.length != columnCount) {
            columnIndexes = new int[columnCount];
            recalcColumns();
        }
    }

    private void recalcRows() {
        for (int i = 0; i < rowIndexes.length; i++) {
            rowIndexes[i] = i;
        }
    }
    //endregion

    private void recalcColumns() {
        for (int i = 0; i < columnIndexes.length; i++) {
            columnIndexes[i] = i;
        }
    }

    //region Геттери
    @Override
    public T get(int rowIndex, int columnIndex) {
        return matrix.get(rowIndex, columnIndex);
    }

    @Override
    protected T get(int index) {
        return matrix.get(index);
    }

    @Override
    public int getColumnCount() {
        return columnIndexes.length;
    }

    //endregion

    @Override
    public int getRowCount() {
        return rowIndexes.length;
    }

    //region Сеттери
    @Override
    public void set(int rowIndex, int columnIndex, T value) {
        matrix.set(rowIndex, columnIndex, value);
    }
    //endregion

    @Override
    protected void set(int index, T value) {
        matrix.set(index, value);
    }

    //region Системні методи
    @Override
    public NumberMatrix<T> clone() {
        return new NumberMatrixIndexHolder<>(matrix.clone(), rowIndexes.clone(), columnIndexes.clone());
    }
    //endregion

    @Override
    public NumberMatrix<T> deepcopy() {
        return clone();
    }

    //region Арифметичні операції з присвоєнням
    @Override
    public NumberMatrix<T> inegate() {
        matrix.inegate();
        return this;
    }

    @Override
    public NumberMatrix<T> iadd(NumberMatrix<T> matrix) {
        this.matrix.iadd(matrix);
        checkSize();
        return this;
    }
    //endregion

    @Override
    public NumberMatrix<T> isubtract(NumberMatrix<T> matrix) {
        this.matrix.isubtract(matrix);
        checkSize();
        return this;
    }

    //region Арифметичні операції без присвоєння
    @Override
    public NumberMatrix<T> add(NumberMatrix<T> matrix) {
        return new NumberMatrixIndexHolder<>(this.matrix.add(matrix));
    }

    @Override
    public NumberMatrix<T> subtract(NumberMatrix<T> matrix) {
        return new NumberMatrixIndexHolder<>(this.matrix.subtract(matrix));
    }

    @Override
    public NumberMatrix<T> negate() {
        return new NumberMatrixIndexHolder<>(this.matrix.negate());
    }

    @Override
    public NumberMatrix<T> prod(NumberMatrix<T> TMatrix) {
        return new NumberMatrixIndexHolder<>(matrix.prod(TMatrix));
    }

    @Override
    public NumberMatrix<T> prod(T lambda) {
        return new NumberMatrixIndexHolder<>(matrix.prod(lambda));
    }

    @Override
    public NumberMatrix<T> multy(NumberMatrix<T> TMatrix) {
        return new NumberMatrixIndexHolder<>(matrix.multy(TMatrix));
    }
    //endregion

    //region Операції переставлення місцями

    @Override
    public NumberMatrix<T> transpose() {
        return new NumberMatrixIndexHolder<>(matrix.transpose());
    }

    @Override
    public NumberMatrix<T> switchRows(int rowIndex1, int rowIndex2) {
        matrix.switchRows(rowIndex1, rowIndex2);
        int temp = rowIndexes[rowIndex1];
        rowIndexes[rowIndex1] = rowIndexes[rowIndex2];
        rowIndexes[rowIndex2] = temp;
        return this;
    }
    //endregion

    @Override
    public NumberMatrix<T> switchColumns(int columnIndex1, int columnIndex2) {
        matrix.switchColumns(columnIndex1, columnIndex2);
        int temp = columnIndexes[columnIndex1];
        columnIndexes[columnIndex1] = columnIndexes[columnIndex2];
        columnIndexes[columnIndex2] = temp;
        return this;
    }

    //region Керування номерами стовпчиків та рядків
    public NumberMatrix<T> getMatrix() {
        return matrix;
    }

    public NumberMatrix<T> restoreMatrix() {
        for (int i = 0; i < rowIndexes.length; i++) {
            int index = rowIndexes[i];
            if (index != i) {
                switchRows(index, i);
            }
        }
        for (int i = 0; i < columnIndexes.length; i++) {
            int index = columnIndexes[i];
            if (index != i) {
                switchColumns(i, index);
            }
        }
        return matrix;
    }

    public int getRowIndex(int index) {
        return rowIndexes[index];
    }
    //endregion

    public int getColumnIndex(int index) {
        return columnIndexes[index];
    }
}
