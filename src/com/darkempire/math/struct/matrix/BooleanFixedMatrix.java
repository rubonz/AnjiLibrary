package com.darkempire.math.struct.matrix;

import com.darkempire.math.struct.function.interfaces.FBooleanMatrixAndIndexToBoolean;
import com.darkempire.math.struct.function.interfaces.FMatrixIndexToBoolean;

import java.util.Arrays;

/**
 * Реалізація двовимірної матриці на основні одновимірного масиву.
 *
 * @author Sir Edvin
 * @version 0.1
 * @see com.darkempire.math.struct.LinearAssignable
 * @see com.darkempire.math.struct.LinearCalcable
 * @see com.darkempire.math.struct.LinearModifable
 * @see BooleanMatrix
 * @see IMatrix
 * @since Anji 0.1
 */
public class BooleanFixedMatrix extends BooleanMatrix {
    private boolean[] arr;
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
    private BooleanFixedMatrix(int rowCount, int columnCount, boolean[] arr) {
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
     * @see BooleanMatrix
     */
    @Override
    public void set(int rowIndex, int columnIndex, boolean value) {
        arr[columnIndex + rowIndex * columnCount] = value;
    }

    @Override
    protected void set(int index, boolean value) {
        arr[index] = value;
    }
    //endregion

    //region Геттери

    /**
     * @return кількість колонок
     * @see IMatrix
     */
    @Override
    public int getColumnCount() {
        return columnCount;
    }

    /**
     * Отримання елементу як примітивного типу з матриці
     *
     * @param columnIndex індекс колонки
     * @param rowIndex    індекс рядку
     * @return значення поля
     * @see BooleanMatrix
     */
    @Override
    public boolean get(int rowIndex, int columnIndex) {
        return arr[columnIndex + rowIndex * columnCount];
    }


    @Override
    protected boolean get(int index) {
        return arr[index];
    }

    /**
     * @return кількість рядків
     * @see BooleanMatrix
     */
    @Override
    public int getRowCount() {
        return rowCount;
    }
    //endregion

    //region Системні методи

    /**
     * Реалізує глибоке копіювання матриці фіксованної розмірності
     *
     * @return Глибоку копію об’єкту
     */
    @Override
    public BooleanFixedMatrix clone() {
        boolean[] array = arr.clone();
        return createInstance(columnCount, rowCount, array);
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

    /**
     * Виводить до табличного виду без табуляції
     *
     * @return текстове зображення матриці
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                sb.append(arr[columnIndex + rowIndex * columnCount]);
                sb.append(',');
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append('\n');
        }
        return sb.toString();
    }
    //endregion

    //region Заповнювачі
    //region Заповнювачі рядків
    @Override
    public BooleanFixedMatrix fillRow(int rowIndex, boolean value) {
        rowIndex *= columnCount;
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            arr[rowIndex] = value;
            rowIndex++;
        }
        return this;
    }

    @Override
    public BooleanFixedMatrix fillRow(int rowIndex, FMatrixIndexToBoolean function) {
        int pos = rowIndex * columnCount;
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            arr[pos] = function.calc(rowIndex, columnIndex);
            pos++;
        }
        return this;
    }

    @Override
    public BooleanFixedMatrix fillRow(int rowIndex, FBooleanMatrixAndIndexToBoolean function) {
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
    public BooleanFixedMatrix fillColumn(int columnIndex, boolean value) {
        int pos = columnIndex;
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            arr[pos] = value;
            pos += columnCount;
        }
        return this;
    }

    @Override
    public BooleanFixedMatrix fillColumn(int columnIndex, FMatrixIndexToBoolean function) {
        int pos = columnIndex;
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            arr[pos] = function.calc(rowIndex, columnIndex);
            pos += columnCount;
        }
        return this;
    }

    @Override
    public BooleanFixedMatrix fillColumn(int columnIndex, FBooleanMatrixAndIndexToBoolean function) {
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
    public BooleanFixedMatrix inegate() {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = !arr[i];
        }
        return this;
    }

    @Override
    public BooleanFixedMatrix iadd(BooleanMatrix booleanMatrix) {
        if (columnCount != booleanMatrix.getColumnCount() || rowCount != booleanMatrix.getRowCount()) {
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MISMATCH);
        }
        for (int i = 0; i < arr.length; i++) {
            arr[i] = arr[i] || booleanMatrix.get(i);
        }
        return this;
    }

    @Override
    public BooleanFixedMatrix isubtract(BooleanMatrix booleanMatrix) {
        if (columnCount != booleanMatrix.getColumnCount() || rowCount != booleanMatrix.getRowCount()) {
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MISMATCH);
        }
        for (int i = 0; i < arr.length; i++) {
            arr[i] = arr[i] ^ booleanMatrix.get(i);
        }
        return this;
    }

    public BooleanFixedMatrix iprod(boolean lambda) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = arr[i] && lambda;
        }
        return this;
    }

    public BooleanFixedMatrix iprod(BooleanFixedMatrix booleanMatrix) {
        if (columnCount != booleanMatrix.columnCount || rowCount != booleanMatrix.rowCount) {
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MISMATCH);
        }
        for (int i = 0; i < arr.length; i++) {
            arr[i] = arr[i] && booleanMatrix.arr[i];
        }
        return this;
    }

    @Override
    public BooleanFixedMatrix itranspose() {
        if (columnCount != rowCount)
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_IS_NOT_SQUARE);
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            for (int rowIndex = columnIndex + 1; rowIndex < rowCount; rowIndex++) {
                boolean temp = get(rowIndex, columnIndex);
                set(rowIndex, columnIndex, get(rowIndex, columnIndex));
                set(rowIndex, columnIndex, temp);
            }
        }
        return this;
    }
    //endregion

    //region Арифметичні операції
    @Override
    public BooleanFixedMatrix add(BooleanMatrix booleanMatrix) {
        boolean[] arrN = new boolean[arr.length];
        if (columnCount != booleanMatrix.getColumnCount() || rowCount != booleanMatrix.getRowCount()) {
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MISMATCH);
        }
        for (int i = 0; i < arr.length; i++) {
            arrN[i] = arr[i] || booleanMatrix.get(i);
        }
        return new BooleanFixedMatrix(rowCount, columnCount, arrN);
    }

    @Override
    public BooleanFixedMatrix subtract(BooleanMatrix booleanMatrix) {
        boolean[] arrN = new boolean[arr.length];
        if (columnCount != booleanMatrix.getColumnCount() || rowCount != booleanMatrix.getRowCount()) {
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MISMATCH);
        }
        for (int i = 0; i < arr.length; i++) {
            arrN[i] = arr[i] ^ booleanMatrix.get(i);
        }
        return new BooleanFixedMatrix(rowCount, columnCount, arrN);
    }

    @Override
    public BooleanFixedMatrix negate() {
        boolean[] arrN = new boolean[arr.length];
        for (int i = 0; i < arr.length; i++) {
            arrN[i] = !arr[i];
        }
        return new BooleanFixedMatrix(rowCount, columnCount, arrN);
    }

    public BooleanFixedMatrix prod(boolean lambda) {
        boolean[] arrN = new boolean[arr.length];
        for (int i = 0; i < arr.length; i++) {
            arrN[i] = lambda && arr[i];
        }
        return new BooleanFixedMatrix(rowCount, columnCount, arrN);
    }

    @Override
    public BooleanFixedMatrix transpose() {
        BooleanFixedMatrix matrix = createInstance(columnCount, rowCount);
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                matrix.set(columnIndex, rowIndex, get(rowIndex, columnIndex));
            }
        }
        return matrix;
    }

    @Override
    public BooleanFixedMatrix prod(BooleanMatrix booleanMatrix) {
        boolean[] arrN = new boolean[arr.length];
        if (columnCount != booleanMatrix.getColumnCount() || rowCount != booleanMatrix.getRowCount()) {
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MISMATCH);
        }
        for (int i = 0; i < arr.length; i++) {
            arrN[i] = arr[i] && booleanMatrix.get(i);
        }
        return new BooleanFixedMatrix(rowCount, columnCount, arrN);
    }

    @Override
    public BooleanFixedMatrix multy(BooleanMatrix booleanMatrix) {
        if (columnCount != booleanMatrix.getRowCount())
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MULTY_MISMATCH);
        BooleanFixedMatrix result = BooleanFixedMatrix.createInstance(rowCount, booleanMatrix.getColumnCount());
        int columnCount = booleanMatrix.getColumnCount();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                boolean temp = false;
                for (int k = 0; k < this.columnCount; k++) {
                    temp = temp || (get(i, k) && booleanMatrix.get(k, j));
                }
                result.set(i, j, temp);
            }
        }
        return result;
    }
    //endregion

    //region Створення матриць
    public static BooleanFixedMatrix createInstance(int rowCount, int columnCount, boolean[] array) {
        if (columnCount * rowCount != array.length)
            throw new ArrayIndexOutOfBoundsException();
        return new BooleanFixedMatrix(rowCount, columnCount, array);
    }

    public static BooleanFixedMatrix createInstance(int rowCount, int columnCount) {
        boolean[] array = new boolean[columnCount * rowCount];
        return createInstance(rowCount, columnCount, array);
    }
    //endregion
}
