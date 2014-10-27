package com.darkempire.math.struct.matrix;

import com.darkempire.math.struct.Number;
import com.darkempire.math.struct.function.interfaces.FMatrixAndIndexToSome;
import com.darkempire.math.struct.function.interfaces.FMatrixIndexToSome;
import com.darkempire.math.struct.vector.IVectorProvider;

import java.util.Arrays;

/**
 * Реалізація двовимірної матриці на основні одновимірного масиву.
 *
 * @author Sir Edvin
 * @version 0.1
 * @see com.darkempire.math.struct.LinearAssignable
 * @see com.darkempire.math.struct.LinearCalcable
 * @see com.darkempire.math.struct.LinearModifable
 * @see NumberMatrix
 * @see IMatrix
 * @since Anji 0.1
 */
public class NumberFixedMatrix<T extends com.darkempire.math.struct.Number<T>> extends NumberMatrix<T> {
    private T[] arr;
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
    private NumberFixedMatrix(int rowCount, int columnCount, T[] arr) {
        this.columnCount = columnCount;
        this.rowCount = rowCount;
        this.arr = arr;
    }
    //endregion

    //region Геттери

    //region Створення матриць
    public static <T extends Number<T>> NumberFixedMatrix<T> createInstance(int rowCount, int columnCount, T[] array) {
        if (columnCount * rowCount != array.length)
            throw new ArrayIndexOutOfBoundsException();
        return new NumberFixedMatrix<T>(rowCount, columnCount, array);
    }

    public static <T extends Number<T>> NumberFixedMatrix<T> createInstance(int rowCount, int columnCount) {
        return new NumberFixedMatrix<T>(rowCount, columnCount, (T[]) new Number[rowCount * columnCount]);
    }

    /**
     * Отримання елементу як примітивного типу з матриці
     *
     * @param columnIndex індекс колонки
     * @param rowIndex    індекс рядку
     * @return значення поля
     * @see NumberMatrix
     */
    @Override
    public T get(int rowIndex, int columnIndex) {
        return arr[columnIndex + rowIndex * columnCount];
    }

    @Override
    protected T get(int index) {
        return arr[index];
    }
    //endregion

    //region Сеттери

    /**
     * @return кількість колонок
     * @see IMatrix
     */
    @Override
    public int getColumnCount() {
        return columnCount;
    }

    /**
     * @return кількість рядків
     * @see NumberMatrix
     */
    @Override
    public int getRowCount() {
        return rowCount;
    }
    //endregion

    //region Системні методи

    /**
     * Запис елементу, заданого як примітивний тип, у матрицю
     *
     * @param columnIndex індекс колонки
     * @param rowIndex    індекс рядку
     * @param value       значення
     * @see NumberMatrix
     */
    @Override
    public void set(int rowIndex, int columnIndex, T value) {
        arr[columnIndex + rowIndex * columnCount] = value;
    }

    @Override
    protected void set(int index, T value) {
        arr[index] = value;
    }
    //endregion

    //region Заповнювачі

    /**
     * Реалізує глибоке копіювання матриці фіксованної розмірності
     *
     * @return Глибоку копію об’єкту
     */
    @Override
    public NumberFixedMatrix<T> clone() {
        T[] array = arr.clone();
        for (int i = 0; i < array.length; i++) {
            array[i] = array[i].deepcopy();
        }
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

    //region Заповнювачі рядків
    @Override
    public NumberFixedMatrix<T> fillRow(int rowIndex, T value) {
        rowIndex *= columnCount;
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            arr[rowIndex] = value.deepcopy();
            rowIndex++;
        }
        return this;
    }

    @Override
    public NumberFixedMatrix<T> fillRow(int rowIndex, FMatrixIndexToSome<T> function) {
        int pos = rowIndex * columnCount;
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            arr[pos] = function.calc(rowIndex, columnIndex);
            pos++;
        }
        return this;
    }
    //endregion

    @Override
    public NumberFixedMatrix<T> fillRow(int rowIndex, FMatrixAndIndexToSome<T> function) {
        int pos = rowIndex * columnCount;
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            arr[pos] = function.calc(this, rowIndex, columnIndex);
            pos++;
        }
        return this;
    }

    @Override
    public NumberFixedMatrix<T> fillRow(int rowIndex, IVectorProvider<T> provider) {
        int pos = rowIndex * columnCount;
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            arr[pos] = provider.get(columnIndex);
            pos++;
        }
        return this;
    }

    //region Заповнювачі колонок
    @Override
    public NumberFixedMatrix<T> fillColumn(int columnIndex, T value) {
        int pos = columnIndex;
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            arr[pos] = value.deepcopy();
            pos += columnCount;
        }
        return this;
    }

    @Override
    public NumberFixedMatrix<T> fillColumn(int columnIndex, FMatrixIndexToSome<T> function) {
        int pos = columnIndex;
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            arr[pos] = function.calc(rowIndex, columnIndex);
            pos += columnCount;
        }
        return this;
    }
    //endregion
    //endregion

    @Override
    public NumberFixedMatrix<T> fillColumn(int columnIndex, FMatrixAndIndexToSome<T> function) {
        int pos = columnIndex;
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            arr[pos] = function.calc(this, rowIndex, columnIndex);
            pos += columnCount;
        }
        return this;
    }

    @Override
    public NumberFixedMatrix<T> fillColumn(int columnIndex, IVectorProvider<T> provider) {
        int pos = columnIndex;
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            arr[pos] = provider.get(rowIndex);
            pos += columnCount;
        }
        return this;
    }

    //region Арифметичні операції з присвоєннями
    @Override
    public NumberFixedMatrix<T> inegate() {
        for (T anArr : arr) {
            anArr.inegate();
        }
        return this;
    }

    @Override
    public NumberFixedMatrix<T> iadd(NumberMatrix<T> doubleMatrix) {
        if (columnCount != doubleMatrix.getColumnCount() || rowCount != doubleMatrix.getRowCount()) {
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MISMATCH);
        }
        for (int i = 0; i < arr.length; i++) {
            arr[i].iadd(doubleMatrix.get(i));
        }
        return this;
    }

    @Override
    public NumberFixedMatrix<T> isubtract(NumberMatrix<T> doubleMatrix) {
        if (columnCount != doubleMatrix.getColumnCount() || rowCount != doubleMatrix.getRowCount()) {
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MISMATCH);
        }
        for (int i = 0; i < arr.length; i++) {
            arr[i].isubtract(doubleMatrix.get(i));
        }
        return this;
    }

    public NumberFixedMatrix<T> iprod(T lambda) {
        for (T anArr : arr) {
            anArr.imultiply(lambda);
        }
        return this;
    }
    //endregion

    public NumberFixedMatrix<T> iprod(NumberFixedMatrix<T> doubleMatrix) {
        if (columnCount != doubleMatrix.columnCount || rowCount != doubleMatrix.rowCount) {
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MISMATCH);
        }
        for (int i = 0; i < arr.length; i++) {
            arr[i].imultiply(doubleMatrix.arr[i]);
        }
        return this;
    }

    @Override
    public NumberFixedMatrix<T> itranspose() {
        if (columnCount != rowCount)
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_IS_NOT_SQUARE);
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            for (int rowIndex = columnIndex + 1; rowIndex < rowCount; rowIndex++) {
                T temp = get(rowIndex, columnIndex);
                set(rowIndex, columnIndex, get(rowIndex, columnIndex));
                set(rowIndex, columnIndex, temp);
            }
        }
        return this;
    }

    //region Арифметичні операції
    @Override
    public NumberFixedMatrix<T> add(NumberMatrix<T> doubleMatrix) {
        if (columnCount != doubleMatrix.getColumnCount() || rowCount != doubleMatrix.getRowCount()) {
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MISMATCH);
        }
        T[] arrN = Arrays.copyOf(arr, arr.length);
        for (int i = 0; i < arr.length; i++) {
            arrN[i] = arr[i].add(doubleMatrix.get(i));
        }
        return new NumberFixedMatrix<T>(rowCount, columnCount, arrN);
    }

    @Override
    public NumberFixedMatrix<T> subtract(NumberMatrix<T> doubleMatrix) {
        if (columnCount != doubleMatrix.getColumnCount() || rowCount != doubleMatrix.getRowCount()) {
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MISMATCH);
        }
        T[] arrN = Arrays.copyOf(arr, arr.length);
        for (int i = 0; i < arr.length; i++) {
            arrN[i] = arr[i].subtract(doubleMatrix.get(i));
        }
        return new NumberFixedMatrix<>(rowCount, columnCount, arrN);
    }

    @Override
    public NumberFixedMatrix<T> negate() {
        T[] arrN = Arrays.copyOf(arr, arr.length);
        for (int i = 0; i < arr.length; i++) {
            arrN[i] = arr[i].negate();
        }
        return new NumberFixedMatrix<>(rowCount, columnCount, arrN);
    }

    @Override
    public NumberFixedMatrix<T> prod(T lambda) {
        T[] arrN = Arrays.copyOf(arr, arr.length);
        for (int i = 0; i < arr.length; i++) {
            arrN[i] = lambda.multiply(arr[i]);
        }
        return new NumberFixedMatrix<T>(rowCount, columnCount, arrN);
    }

    @Override
    public NumberFixedMatrix<T> transpose() {
        NumberFixedMatrix<T> matrix = createInstance(columnCount, rowCount, arr);
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                matrix.set(columnIndex, rowIndex, get(rowIndex, columnIndex).deepcopy());
            }
        }
        return matrix;
    }
    //endregion

    @Override
    public NumberFixedMatrix<T> prod(NumberMatrix<T> doubleMatrix) {
        if (columnCount != doubleMatrix.getColumnCount() || rowCount != doubleMatrix.getRowCount()) {
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MISMATCH);
        }
        T[] arrN = Arrays.copyOf(arr, arr.length);
        for (int i = 0; i < arr.length; i++) {
            arrN[i] = arr[i].multiply(doubleMatrix.get(i));
        }
        return new NumberFixedMatrix<T>(rowCount, columnCount, arrN);
    }

    @Override
    public NumberMatrix<T> multy(NumberMatrix<T> doubleMatrix) {
        if (columnCount != doubleMatrix.getRowCount())
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MULTY_MISMATCH);
        NumberFixedMatrix<T> result = NumberFixedMatrix.createInstance(rowCount, doubleMatrix.getColumnCount());
        int columnCount = doubleMatrix.getColumnCount();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                T temp = get(i, 0).multiply(doubleMatrix.get(0, j));
                for (int k = 1; k < this.columnCount; k++) {
                    temp.iadd(get(i, k).multiply(doubleMatrix.get(k, j)));
                }
                result.set(i, j, temp);
            }
        }
        return result;
    }
    //endregion

    @Override
    public NumberMatrix<T> deepcopy() {
        return clone();
    }
}
