package com.darkempire.math.struct.matrix;

import com.darkempire.math.struct.vector.DoubleVector;

/**
 * Created by siredvin on 21.10.14.
 *
 * @author siredvin
 */
public class DoubleMatrixIndexHolder extends DoubleMatrix {
    private DoubleMatrix matrix;
    private int[] rowIndexes;
    private int[] columnIndexes;

    //region Конструктори
    public DoubleMatrixIndexHolder(DoubleMatrix matrix) {
        this.matrix = matrix;
        rowIndexes = new int[matrix.getRowCount()];
        columnIndexes = new int[matrix.getColumnCount()];
        recalcRows();
        recalcColumns();
    }

    private DoubleMatrixIndexHolder(DoubleMatrix matrix, int[] rowIndexes, int[] columnIndexes) {
        this.matrix = matrix;
        this.rowIndexes = rowIndexes;
        this.columnIndexes = columnIndexes;
    }
    //endregion

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

    private void recalcColumns() {
        for (int i = 0; i < columnIndexes.length; i++) {
            columnIndexes[i] = i;
        }
    }
    //endregion

    //region Геттери
    @Override
    public double get(int rowIndex, int columnIndex) {
        return matrix.get(rowIndex, columnIndex);
    }

    @Override
    protected double get(int index) {
        return matrix.get(index);
    }

    @Override
    public int getColumnCount() {
        return columnIndexes.length;
    }

    @Override
    public int getRowCount() {
        return rowIndexes.length;
    }

    //endregion

    //region Сеттери
    @Override
    public void set(int rowIndex, int columnIndex, double value) {
        matrix.set(rowIndex, columnIndex, value);
    }

    @Override
    protected void set(int index, double value) {
        matrix.set(index, value);
    }
    //endregion

    //region Системні методи
    @Override
    public DoubleMatrix clone() {
        return new DoubleMatrixIndexHolder(matrix.clone(), rowIndexes.clone(), columnIndexes.clone());
    }

    @Override
    public DoubleMatrix deepcopy() {
        return clone();
    }
    //endregion

    //region Арифметичні операції з присвоєнням
    @Override
    public DoubleMatrix inegate() {
        matrix.inegate();
        return this;
    }

    @Override
    public DoubleMatrix iadd(DoubleMatrix matrix) {
        this.matrix.iadd(matrix);
        checkSize();
        return this;
    }

    @Override
    public DoubleMatrix isubtract(DoubleMatrix matrix) {
        this.matrix.isubtract(matrix);
        checkSize();
        return this;
    }

    @Override
    public DoubleMatrix iprod(double lambda) {
        matrix.iprod(lambda);
        return this;
    }
    //endregion

    //region Арифметичні операції без присвоєння
    @Override
    public DoubleMatrix add(DoubleMatrix matrix) {
        return new DoubleMatrixIndexHolder(this.matrix.add(matrix));
    }

    @Override
    public DoubleMatrix subtract(DoubleMatrix matrix) {
        return new DoubleMatrixIndexHolder(this.matrix.subtract(matrix));
    }

    @Override
    public DoubleMatrix negate() {
        return new DoubleMatrixIndexHolder(this.matrix.negate());
    }

    @Override
    public DoubleMatrix prod(DoubleMatrix doubleMatrix) {
        return new DoubleMatrixIndexHolder(matrix.prod(doubleMatrix));
    }


    @Override
    public DoubleMatrix prod(double lambda) {
        return new DoubleMatrixIndexHolder(matrix.prod(lambda));
    }

    @Override
    public DoubleMatrix multy(DoubleMatrix doubleMatrix) {
        return new DoubleMatrixIndexHolder(matrix.multy(doubleMatrix));
    }

    @Override
    public DoubleVector multy(DoubleVector doubleVector) {
        return matrix.multy(doubleVector);
    }

    @Override
    public DoubleMatrix transpose() {
        return new DoubleMatrixIndexHolder(matrix.transpose());
    }
    //endregion

    //region Операції переставлення місцями

    @Override
    public DoubleMatrix switchRows(int rowIndex1, int rowIndex2) {
        matrix.switchRows(rowIndex1, rowIndex2);
        int temp = rowIndexes[rowIndex1];
        rowIndexes[rowIndex1] = rowIndexes[rowIndex2];
        rowIndexes[rowIndex2] = temp;
        return this;
    }

    @Override
    public DoubleMatrix switchColumns(int columnIndex1, int columnIndex2) {
        matrix.switchColumns(columnIndex1, columnIndex2);
        int temp = columnIndexes[columnIndex1];
        columnIndexes[columnIndex1] = columnIndexes[columnIndex2];
        columnIndexes[columnIndex2] = temp;
        return this;
    }
    //endregion

    //region Керування номерами стовпчиків та рядків
    public DoubleMatrix getMatrix() {
        return matrix;
    }

    public DoubleMatrix restoreMatrix() {
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

    public int getColumnIndex(int index) {
        return columnIndexes[index];
    }
    //endregion

    public static DoubleMatrixIndexHolder indexWrap(DoubleMatrix matrix) {
        return new DoubleMatrixIndexHolder(matrix);
    }
}
