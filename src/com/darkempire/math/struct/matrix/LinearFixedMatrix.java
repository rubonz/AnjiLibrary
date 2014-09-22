package com.darkempire.math.struct.matrix;

import com.darkempire.math.struct.Number;
import com.darkempire.math.struct.function.interfaces.FLinearMatrixAndIndexToLinear;
import com.darkempire.math.struct.function.interfaces.FMatrixIndexToLinear;

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
 * @see LinearMatrix
 * @see IMatrix
 * @since Anji 0.1
 */
public class LinearFixedMatrix<T extends com.darkempire.math.struct.Number<T>> extends LinearMatrix<T> {
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
    private LinearFixedMatrix(int rowCount, int columnCount, T[] arr) {
        this.columnCount = columnCount;
        this.rowCount = rowCount;
        this.arr = arr;
    }
    //endregion

    //region Геттери

    /**
     * Отримання елементу як примітивного типу з матриці
     *
     * @param columnIndex індекс колонки
     * @param rowIndex    індекс рядку
     * @return значення поля
     * @see LinearMatrix
     */
    @Override
    public T get(int rowIndex, int columnIndex) {
        return arr[columnIndex + rowIndex * columnCount];
    }

    @Override
    protected T get(int index) {
        return arr[index];
    }

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
     * @see LinearMatrix
     */
    @Override
    public int getRowCount() {
        return rowCount;
    }
    //endregion

    //region Сеттери

    /**
     * Запис елементу, заданого як примітивний тип, у матрицю
     *
     * @param columnIndex індекс колонки
     * @param rowIndex    індекс рядку
     * @param value       значення
     * @see LinearMatrix
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

    //region Системні методи

    /**
     * Реалізує глибоке копіювання матриці фіксованної розмірності
     *
     * @return Глибоку копію об’єкту
     */
    @Override
    public LinearFixedMatrix<T> clone() {
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
    public LinearFixedMatrix<T> fillRow(int rowIndex, T value) {
        rowIndex *= columnCount;
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            arr[rowIndex] = value.deepcopy();
            rowIndex++;
        }
        return this;
    }

    @Override
    public LinearFixedMatrix<T> fillRow(int rowIndex, FMatrixIndexToLinear<T> function) {
        int pos = rowIndex * columnCount;
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            arr[pos] = function.calc(rowIndex, columnIndex);
            pos++;
        }
        return this;
    }

    @Override
    public LinearFixedMatrix<T> fillRow(int rowIndex, FLinearMatrixAndIndexToLinear<T> function) {
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
    public LinearFixedMatrix<T> fillColumn(int columnIndex, T value) {
        int pos = columnIndex;
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            arr[pos] = value.deepcopy();
            pos += columnCount;
        }
        return this;
    }

    @Override
    public LinearFixedMatrix<T> fillColumn(int columnIndex, FMatrixIndexToLinear<T> function) {
        int pos = columnIndex;
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            arr[pos] = function.calc(rowIndex, columnIndex);
            pos += columnCount;
        }
        return this;
    }

    @Override
    public LinearFixedMatrix<T> fillColumn(int columnIndex, FLinearMatrixAndIndexToLinear<T> function) {
        int pos = columnIndex;
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            arr[pos] = function.calc(this, rowIndex, columnIndex);
            pos += columnCount;
        }
        return this;
    }
    //endregion
    //endregion

    //region Арифметичні операції з присвоєннями
    @Override
    public LinearFixedMatrix<T> inegate() {
        for (T anArr : arr) {
            anArr.inegate();
        }
        return this;
    }

    @Override
    public LinearFixedMatrix<T> iadd(LinearMatrix<T> doubleMatrix) {
        if (columnCount != doubleMatrix.getColumnCount() || rowCount != doubleMatrix.getRowCount()) {
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MISMATCH);
        }
        for (int i = 0; i < arr.length; i++) {
            arr[i].iadd(doubleMatrix.get(i));
        }
        return this;
    }

    @Override
    public LinearFixedMatrix<T> isubtract(LinearMatrix<T> doubleMatrix) {
        if (columnCount != doubleMatrix.getColumnCount() || rowCount != doubleMatrix.getRowCount()) {
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MISMATCH);
        }
        for (int i = 0; i < arr.length; i++) {
            arr[i].isubtract(doubleMatrix.get(i));
        }
        return this;
    }

    public LinearFixedMatrix<T> iprod(T lambda) {
        for (T anArr : arr) {
            anArr.imultiply(lambda);
        }
        return this;
    }

    public LinearFixedMatrix<T> iprod(LinearFixedMatrix<T> doubleMatrix) {
        if (columnCount != doubleMatrix.columnCount || rowCount != doubleMatrix.rowCount) {
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MISMATCH);
        }
        for (int i = 0; i < arr.length; i++) {
            arr[i].imultiply(doubleMatrix.arr[i]);
        }
        return this;
    }

    @Override
    public LinearFixedMatrix<T> itranspose() {
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
    //endregion

    //region Арифметичні операції
    @Override
    public LinearFixedMatrix<T> add(LinearMatrix<T> doubleMatrix) {
        if (columnCount != doubleMatrix.getColumnCount() || rowCount != doubleMatrix.getRowCount()) {
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MISMATCH);
        }
        T[] arrN = Arrays.copyOf(arr, arr.length);
        for (int i = 0; i < arr.length; i++) {
            arrN[i] = arr[i].add(doubleMatrix.get(i));
        }
        return new LinearFixedMatrix<T>(rowCount, columnCount, arrN);
    }

    @Override
    public LinearFixedMatrix<T> subtract(LinearMatrix<T> doubleMatrix) {
        if (columnCount != doubleMatrix.getColumnCount() || rowCount != doubleMatrix.getRowCount()) {
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MISMATCH);
        }
        T[] arrN = Arrays.copyOf(arr, arr.length);
        for (int i = 0; i < arr.length; i++) {
            arrN[i] = arr[i].subtract(doubleMatrix.get(i));
        }
        return new LinearFixedMatrix<>(rowCount, columnCount, arrN);
    }

    @Override
    public LinearFixedMatrix<T> negate() {
        T[] arrN = Arrays.copyOf(arr, arr.length);
        for (int i = 0; i < arr.length; i++) {
            arrN[i] = arr[i].negate();
        }
        return new LinearFixedMatrix<>(rowCount, columnCount, arrN);
    }


    @Override
    public LinearFixedMatrix<T> prod(T lambda) {
        T[] arrN = Arrays.copyOf(arr, arr.length);
        for (int i = 0; i < arr.length; i++) {
            arrN[i] = lambda.multiply(arr[i]);
        }
        return new LinearFixedMatrix<T>(rowCount, columnCount, arrN);
    }


    @Override
    public LinearFixedMatrix<T> transpose() {
        LinearFixedMatrix<T> matrix = createInstance(columnCount, rowCount, arr);
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                matrix.set(columnIndex, rowIndex, get(rowIndex, columnIndex).deepcopy());
            }
        }
        return matrix;
    }

    @Override
    public LinearFixedMatrix<T> prod(LinearMatrix<T> doubleMatrix) {
        if (columnCount != doubleMatrix.getColumnCount() || rowCount != doubleMatrix.getRowCount()) {
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MISMATCH);
        }
        T[] arrN = Arrays.copyOf(arr, arr.length);
        for (int i = 0; i < arr.length; i++) {
            arrN[i] = arr[i].multiply(doubleMatrix.get(i));
        }
        return new LinearFixedMatrix<T>(rowCount, columnCount, arrN);
    }


    @Override
    public LinearMatrix<T> multy(LinearMatrix<T> doubleMatrix) {
        if (columnCount != doubleMatrix.getRowCount())
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MULTY_MISMATCH);
        LinearFixedMatrix<T> result = LinearFixedMatrix.createInstance(rowCount, doubleMatrix.getColumnCount());
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

    //region Створення матриць
    public static <T extends Number<T>> LinearFixedMatrix<T> createInstance(int rowCount, int columnCount, T[] array) {
        if (columnCount * rowCount != array.length)
            throw new ArrayIndexOutOfBoundsException();
        return new LinearFixedMatrix<T>(rowCount, columnCount, array);
    }

    public static <T extends Number<T>> LinearFixedMatrix<T> createInstance(int rowCount, int columnCount) {
        return new LinearFixedMatrix<T>(rowCount, columnCount, (T[]) new Number[rowCount * columnCount]);
    }
    //endregion
}
