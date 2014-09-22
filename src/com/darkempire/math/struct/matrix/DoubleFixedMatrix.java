package com.darkempire.math.struct.matrix;

import com.darkempire.math.struct.function.interfaces.FDoubleMatrixAndIndexToDouble;
import com.darkempire.math.struct.function.interfaces.FMatrixIndexToDouble;
import com.darkempire.math.struct.vector.DoubleFixedVector;
import com.darkempire.math.struct.vector.DoubleVector;

import java.util.Arrays;

import static java.lang.Math.abs;

/**
 * Реалізація двовимірної матриці на основні одновимірного масиву.
 *
 * @author Sir Edvin
 * @version 0.1
 * @see com.darkempire.math.struct.LinearAssignable
 * @see com.darkempire.math.struct.LinearCalcable
 * @see com.darkempire.math.struct.LinearModifable
 * @see com.darkempire.math.struct.matrix.DoubleMatrix
 * @see com.darkempire.math.struct.matrix.IMatrix
 * @since Anji 0.1
 */
public class DoubleFixedMatrix extends DoubleMatrix {
    private double[] arr;
    private int columnCount;
    private int rowCount;
    //region Конструктори

    /**
     * Конструювання матриці фіксованного розміру
     *
     * @param columnCount кількість колонок
     * @param rowCount    кількість рядків
     * @param arr         масив, які зберігає двувимірну матрицю
     */
    private DoubleFixedMatrix(int rowCount, int columnCount, double[] arr) {
        this.columnCount = columnCount;
        this.rowCount = rowCount;
        this.arr = arr;
    }
    //endregion

    //region Сеттери

    /**
     * Запис елементу, заданого як примітивний тип, у матрицю
     *
     * @param columnIndex індекс колонки
     * @param rowIndex    індекс рядку
     * @param value       значення
     * @see com.darkempire.math.struct.matrix.DoubleMatrix
     */
    @Override
    public void set(int rowIndex, int columnIndex, double value) {
        arr[columnIndex + rowIndex * columnCount] = value;
    }

    @Override
    protected void set(int index, double value) {
        arr[index] = value;
    }
    //endregion

    //region Геттери

    /**
     * Отримання елементу як примітивного типу з матриці
     *
     * @param columnIndex індекс колонки
     * @param rowIndex    індекс рядку
     * @return значення поля
     * @see com.darkempire.math.struct.matrix.DoubleMatrix
     */
    @Override
    public double get(int rowIndex, int columnIndex) {
        return arr[columnIndex + rowIndex * columnCount];
    }

    /**
     * @return кількість колонок
     * @see com.darkempire.math.struct.matrix.IMatrix
     */
    @Override
    public int getColumnCount() {
        return columnCount;
    }

    @Override
    protected double get(int index) {
        return arr[index];
    }

    /**
     * @return кількість рядків
     * @see com.darkempire.math.struct.matrix.DoubleMatrix
     */
    @Override
    public int getRowCount() {
        return rowCount;
    }
    //endregion

    //region Системні функції

    /**
     * Реалізує глибоке копіювання матриці фіксованної розмірності
     *
     * @return Глибоку копію об’єкту
     */
    @Override
    public DoubleFixedMatrix clone() {
        double[] array = arr.clone();
        return createInstance(rowCount, columnCount, array);
    }

    /**
     * Генерує хеш-код для матриці
     *
     * @return хеш-код
     */
    @Override
    public int hashCode() {
        int result = Arrays.hashCode(arr);
        result = 31 * result + columnCount;
        result = 31 * result + rowCount;
        return result;
    }
    //endregion

    //region Заповнювачі
    //region Заповнювачі рядків
    @Override
    public DoubleFixedMatrix fillRow(int rowIndex, double value) {
        rowIndex *= columnCount;
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            arr[rowIndex] = value;
            rowIndex++;
        }
        return this;
    }

    @Override
    public DoubleFixedMatrix fillRow(int rowIndex, FMatrixIndexToDouble function) {
        int pos = rowIndex * columnCount;
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            arr[pos] = function.calc(rowIndex, columnIndex);
            pos++;
        }
        return this;
    }

    @Override
    public DoubleFixedMatrix fillRow(int rowIndex, FDoubleMatrixAndIndexToDouble function) {
        int pos = rowIndex * columnCount;
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            arr[pos] = function.calc(this, rowIndex, columnIndex);
            pos++;
        }
        return this;
    }
    //endregion


    //region Заповнювачі колонок
    @Override
    public DoubleFixedMatrix fillColumn(int columnIndex, double value) {
        int pos = columnIndex;
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            arr[pos] = value;
            pos += columnCount;
        }
        return this;
    }

    @Override
    public DoubleFixedMatrix fillColumn(int columnIndex, FMatrixIndexToDouble function) {
        int pos = columnIndex;
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            arr[pos] = function.calc(rowIndex, columnIndex);
            pos += columnCount;
        }
        return this;
    }

    @Override
    public DoubleFixedMatrix fillColumn(int columnIndex, FDoubleMatrixAndIndexToDouble function) {
        int pos = columnIndex;
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            arr[pos] = function.calc(this, rowIndex, columnIndex);
            pos += columnCount;
        }
        return this;
    }
    //endregion
    //endregion

    //region Арифметичні операції з присвоєнням
    @Override
    public DoubleFixedMatrix inegate() {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = -arr[i];
        }
        return this;
    }

    @Override
    public DoubleFixedMatrix iadd(DoubleMatrix doubleMatrix) {
        if (columnCount != doubleMatrix.getColumnCount() || rowCount != doubleMatrix.getRowCount()) {
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MISMATCH);
        }
        for (int i = 0; i < arr.length; i++) {
            arr[i] += doubleMatrix.get(i);
        }
        return this;
    }

    @Override
    public DoubleFixedMatrix isubtract(DoubleMatrix doubleMatrix) {
        if (columnCount != doubleMatrix.getColumnCount() || rowCount != doubleMatrix.getRowCount()) {
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MISMATCH);
        }
        for (int i = 0; i < arr.length; i++) {
            arr[i] -= doubleMatrix.get(i);
        }
        return this;
    }

    public DoubleFixedMatrix iprod(double lambda) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] *= lambda;
        }
        return this;
    }

    public DoubleFixedMatrix iprod(DoubleFixedMatrix doubleMatrix) {
        if (columnCount != doubleMatrix.columnCount || rowCount != doubleMatrix.rowCount) {
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MISMATCH);
        }
        for (int i = 0; i < arr.length; i++) {
            arr[i] *= doubleMatrix.arr[i];
        }
        return this;
    }

    @Override
    public DoubleFixedMatrix itranspose() {
        if (columnCount != rowCount)
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_IS_NOT_SQUARE);
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            for (int rowIndex = columnIndex + 1; rowIndex < rowCount; rowIndex++) {
                double temp = get(rowIndex, columnIndex);
                set(rowIndex, columnIndex, get(rowIndex, columnIndex));
                set(rowIndex, columnIndex, temp);
            }
        }
        return this;
    }
    //endregion

    //region Арифметичні операції
    @Override
    public DoubleFixedMatrix add(DoubleMatrix doubleMatrix) {
        double[] arrN = new double[arr.length];
        if (columnCount != doubleMatrix.getColumnCount() || rowCount != doubleMatrix.getRowCount()) {
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MISMATCH);
        }
        for (int i = 0; i < arr.length; i++) {
            arrN[i] = arr[i] + doubleMatrix.get(i);
        }
        return new DoubleFixedMatrix(rowCount, columnCount, arrN);
    }

    @Override
    public DoubleFixedMatrix subtract(DoubleMatrix doubleMatrix) {
        double[] arrN = new double[arr.length];
        if (columnCount != doubleMatrix.getColumnCount() || rowCount != doubleMatrix.getRowCount()) {
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MISMATCH);
        }
        for (int i = 0; i < arr.length; i++) {
            arrN[i] = arr[i] - doubleMatrix.get(i);
        }
        return new DoubleFixedMatrix(rowCount, columnCount, arrN);
    }

    @Override
    public DoubleFixedMatrix negate() {
        double[] arrN = new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            arrN[i] = -arr[i];
        }
        return new DoubleFixedMatrix(rowCount, columnCount, arrN);
    }


    @Override
    public DoubleFixedMatrix prod(double lambda) {
        double[] arrN = new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            arrN[i] = lambda * arr[i];
        }
        return new DoubleFixedMatrix(rowCount, columnCount, arrN);
    }


    @Override
    public DoubleFixedMatrix transpose() {
        DoubleFixedMatrix matrix = createInstance(columnCount, rowCount);
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                matrix.set(columnIndex, rowIndex, get(rowIndex, columnIndex));
            }
        }
        return matrix;
    }

    @Override
    public DoubleMatrix prod(DoubleMatrix doubleMatrix) {
        double[] arrN = new double[arr.length];
        if (columnCount != doubleMatrix.getColumnCount() || rowCount != doubleMatrix.getRowCount()) {
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MISMATCH);
        }
        for (int i = 0; i < arr.length; i++) {
            arrN[i] = arr[i] * doubleMatrix.get(i);
        }
        return new DoubleFixedMatrix(rowCount, columnCount, arrN);
    }

    @Override
    public DoubleMatrix multy(DoubleMatrix doubleMatrix) {
        if (columnCount != doubleMatrix.getRowCount())
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MULTY_MISMATCH);
        DoubleFixedMatrix result = DoubleFixedMatrix.createInstance(rowCount, doubleMatrix.getColumnCount());
        int columnCount = doubleMatrix.getColumnCount();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                double temp = 0;
                for (int k = 0; k < this.columnCount; k++) {
                    temp += get(i, k) * doubleMatrix.get(k, j);
                }
                result.set(i, j, temp);
            }
        }
        return result;
    }

    @Override
    public DoubleVector multy(DoubleVector doubleVector) {
        if (columnCount != doubleVector.getSize())
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MULTY_MISMATCH);
        DoubleVector result = new DoubleFixedVector(rowCount);
        for (int vectIndex = 0; vectIndex < rowCount; vectIndex++) {
            double summ = 0;
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                summ += get(vectIndex, columnIndex) * doubleVector.get(columnIndex);
            }
            result.set(vectIndex, summ);
        }
        return result;
    }
    //endregion

    //region Створення матриць
    public static DoubleFixedMatrix createInstance(int rowCount, int columnCount, double[] array) {
        if (columnCount * rowCount != array.length)
            throw new ArrayIndexOutOfBoundsException();
        return new DoubleFixedMatrix(rowCount, columnCount, array);
    }

    public static DoubleFixedMatrix createInstance(int rowCount, int columnCount) {
        double[] array = new double[columnCount * rowCount];
        return createInstance(rowCount, columnCount, array);
    }
    //endregion
}
