package com.darkempire.math.struct.matrix;

import com.darkempire.math.struct.Number;
import com.darkempire.math.struct.function.interfaces.FMatrixAndIndexToSome;
import com.darkempire.math.struct.function.interfaces.FMatrixIndexToSome;
import com.darkempire.math.struct.vector.IVectorProvider;

import java.util.Arrays;

/**
 * Create in 11:55
 * Created by siredvin on 21.12.13.
 */
public class NumberResizeMatrix<T extends com.darkempire.math.struct.Number<T>> extends NumberMatrix<T> {
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
    private NumberResizeMatrix(int rowCount, int columnCount, T[] arr) {
        this.columnCount = columnCount;
        this.rowCount = rowCount;
        this.arr = arr;
    }
    //endregionи

    //region Геттери

    //region Створення матриці
    public static <T extends Number<T>> NumberResizeMatrix<T> createInstance(int rowCount, int columnCount, T[] array) {
        if (columnCount * rowCount != array.length)
            throw new ArrayIndexOutOfBoundsException();
        return new NumberResizeMatrix<T>(rowCount, columnCount, array);
    }

    public static <T extends Number<T>> NumberResizeMatrix<T> createInstance(int rowCount, int columnCount) {
        T[] array = (T[]) new Number[columnCount * rowCount];
        return createInstance(rowCount, columnCount, array);
    }

    /**
     * Отримання елементу як примітивного типу з матриці
     * Зауважимо, що це матриця, що розтягується, отже всі невказані елементи нульові.
     *
     * @param columnIndex індекс колонки
     * @param rowIndex    індекс рядку
     * @return значення поля
     * @see NumberMatrix <T>
     */
    @Override
    public T get(int rowIndex, int columnIndex) {
        if (columnIndex > columnCount - 1 || rowIndex > rowCount - 1)
            return arr[0].getZero();
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
     * @see NumberMatrix <T>
     */
    @Override
    public int getRowCount() {
        return rowCount;
    }

    //endregion

    //region Системні функції

    /**
     * Запис елементу, заданого як примітивний тип, у матрицю
     *
     * @param columnIndex індекс колонки
     * @param rowIndex    індекс рядку
     * @param value       значення
     * @see NumberMatrix <T>
     */
    @Override
    public void set(int rowIndex, int columnIndex, T value) {
        if (columnIndex > columnCount - 1 || rowIndex > rowCount - 1)
            return;
        arr[columnIndex + rowIndex * columnCount] = value;
    }

    @Override
    protected void set(int index, T value) {
        arr[index] = value;
    }
    //endregion

    /**
     * Реалізує глибоке копіювання матриці фіксованної розмірності
     *
     * @return Глибоку копію об’єкту
     */
    @Override
    public NumberResizeMatrix<T> clone() {
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
    public NumberResizeMatrix<T> fillRow(int rowIndex, T value) {
        rowIndex *= columnCount;
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            arr[rowIndex] = value.deepcopy();
            rowIndex++;
        }
        return this;
    }

    @Override
    public NumberResizeMatrix<T> fillRow(int rowIndex, FMatrixIndexToSome<T> function) {
        int pos = rowIndex * columnCount;
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            arr[pos] = function.calc(rowIndex, columnIndex);
            pos++;
        }
        return this;
    }
    //endregion

    @Override
    public NumberResizeMatrix<T> fillRow(int rowIndex, FMatrixAndIndexToSome<T> function) {
        int pos = rowIndex * columnCount;
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            arr[pos] = function.calc(this, rowIndex, columnIndex);
            pos++;
        }
        return this;
    }

    @Override
    public NumberResizeMatrix<T> fillRow(int rowIndex, IVectorProvider<T> provider) {
        int pos = rowIndex * columnCount;
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            arr[pos] = provider.get(columnIndex);
            pos++;
        }
        return this;
    }

    //region Заповнювачі стовпчиків
    @Override
    public NumberResizeMatrix<T> fillColumn(int columnIndex, T value) {
        int pos = columnIndex;
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            arr[pos] = value.deepcopy();
            pos += columnCount;
        }
        return this;
    }

    @Override
    public NumberResizeMatrix<T> fillColumn(int columnIndex, FMatrixIndexToSome<T> function) {
        int pos = columnIndex;
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            arr[pos] = function.calc(rowIndex, columnIndex);
            pos += columnCount;
        }
        return this;
    }
    //endregion

    @Override
    public NumberResizeMatrix<T> fillColumn(int columnIndex, FMatrixAndIndexToSome<T> function) {
        int pos = columnIndex;
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            arr[pos] = function.calc(this, rowIndex, columnIndex);
            pos += columnCount;
        }
        return this;
    }

    @Override
    public NumberResizeMatrix<T> fillColumn(int columnIndex, IVectorProvider<T> provider) {
        int pos = columnIndex;
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            arr[pos] = provider.get(rowIndex);
            pos += columnCount;
        }
        return this;
    }

    //region Арифметичні операції з присвоєнням
    @Override
    public NumberResizeMatrix<T> inegate() {
        for (T anArr : arr) {
            anArr.inegate();
        }
        return this;
    }

    @Override
    public NumberResizeMatrix<T> iadd(NumberMatrix<T> doubleMatrix) {
        if (columnCount != doubleMatrix.getColumnCount() || rowCount != doubleMatrix.getRowCount()) {
            int newColumnCount = Math.max(columnCount, doubleMatrix.getColumnCount());
            int newRowCount = Math.max(rowCount, doubleMatrix.getRowCount());
            T[] nArr = (T[]) new Number[newColumnCount * newRowCount];
            for (int columnIndex = 0; columnIndex < newColumnCount; columnIndex++) {
                for (int rowIndex = 0; rowIndex < newRowCount; rowIndex++) {
                    nArr[columnIndex + rowIndex * newColumnCount] = get(rowIndex, columnIndex).iadd(doubleMatrix.get(rowIndex, columnIndex));
                }
            }
            this.arr = nArr;
            return this;
        }

        for (int i = 0; i < arr.length; i++) {
            arr[i].iadd(doubleMatrix.get(i));
        }
        return this;
    }

    @Override
    public NumberResizeMatrix<T> isubtract(NumberMatrix<T> doubleMatrix) {
        if (columnCount != doubleMatrix.getColumnCount() || rowCount != doubleMatrix.getRowCount()) {
            int newColumnCount = Math.max(columnCount, doubleMatrix.getColumnCount());
            int newRowCount = Math.max(rowCount, doubleMatrix.getRowCount());
            T[] nArr = (T[]) new Number[newColumnCount * newRowCount];
            for (int columnIndex = 0; columnIndex < newColumnCount; columnIndex++) {
                for (int rowIndex = 0; rowIndex < newRowCount; rowIndex++) {
                    nArr[columnIndex + rowIndex * newColumnCount] = get(rowIndex, columnIndex).isubtract(doubleMatrix.get(rowIndex, columnIndex));
                }
            }
            this.arr = nArr;
            return this;
        }
        for (int i = 0; i < arr.length; i++) {
            arr[i].isubtract(doubleMatrix.get(i));
        }
        return this;
    }

    public NumberResizeMatrix<T> iprod(T lambda) {
        for (T anArr : arr) {
            anArr.imultiply(lambda);
        }
        return this;
    }
    //endregion

    public NumberResizeMatrix<T> iprod(NumberResizeMatrix<T> doubleMatrix) {
        if (columnCount != doubleMatrix.columnCount || rowCount != doubleMatrix.rowCount) {
            int newColumnCount = Math.max(columnCount, doubleMatrix.columnCount);
            int newRowCount = Math.max(rowCount, doubleMatrix.rowCount);
            T[] nArr = (T[]) new Number[newColumnCount * newRowCount];
            for (int columnIndex = 0; columnIndex < newColumnCount; columnIndex++) {
                for (int rowIndex = 0; rowIndex < newRowCount; rowIndex++) {
                    nArr[columnIndex + rowIndex * newColumnCount] = get(rowIndex, columnIndex).imultiply(doubleMatrix.get(rowIndex, columnIndex));
                }
            }
            this.arr = nArr;
            return this;
        }
        for (int i = 0; i < arr.length; i++) {
            arr[i].imultiply(doubleMatrix.arr[i]);
        }
        return this;
    }

    @Override
    public NumberResizeMatrix<T> itranspose() {
        if (columnCount != rowCount) {
            T[] nArr = (T[]) new Number[columnCount * rowCount];
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                    nArr[rowIndex + columnIndex * rowCount] = get(rowIndex, columnIndex);
                }
            }
            this.arr = nArr;
            int temp = columnCount;
            columnCount = rowCount;
            rowCount = temp;
            return this;
        }
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
    public NumberResizeMatrix<T> add(NumberMatrix<T> doubleMatrix) {
        int newColumnCount = Math.max(columnCount, doubleMatrix.getColumnCount());
        int newRowCount = Math.max(rowCount, doubleMatrix.getRowCount());
        T[] nArr = (T[]) new Number[newColumnCount * newRowCount];
        for (int columnIndex = 0; columnIndex < newColumnCount; columnIndex++) {
            for (int rowIndex = 0; rowIndex < newRowCount; rowIndex++) {
                nArr[columnIndex + rowIndex * newColumnCount] = get(rowIndex, columnIndex).add(doubleMatrix.get(rowIndex, columnIndex));
            }
        }
        return new NumberResizeMatrix<T>(newRowCount, newColumnCount, nArr);
    }

    @Override
    public NumberResizeMatrix<T> subtract(NumberMatrix<T> doubleMatrix) {
        int newColumnCount = Math.max(columnCount, doubleMatrix.getColumnCount());
        int newRowCount = Math.max(rowCount, doubleMatrix.getRowCount());
        T[] nArr = (T[]) new Number[newColumnCount * newRowCount];
        for (int columnIndex = 0; columnIndex < newColumnCount; columnIndex++) {
            for (int rowIndex = 0; rowIndex < newRowCount; rowIndex++) {
                nArr[columnIndex + rowIndex * newColumnCount] = get(rowIndex, columnIndex).subtract(doubleMatrix.get(rowIndex, columnIndex));
            }
        }
        return new NumberResizeMatrix<T>(newRowCount, newColumnCount, nArr);
    }

    @Override
    public NumberResizeMatrix<T> negate() {
        T[] nArr = (T[]) new Number[arr.length];
        for (int i = 0; i < arr.length; i++) {
            nArr[i] = arr[i].negate();
        }
        return new NumberResizeMatrix<T>(rowCount, columnCount, nArr);
    }

    @Override
    public NumberResizeMatrix<T> prod(T lambda) {
        T[] nArr = (T[]) new Number[arr.length];
        for (int i = 0; i < arr.length; i++) {
            nArr[i] = lambda.multiply(arr[i]);
        }
        return new NumberResizeMatrix<T>(rowCount, columnCount, nArr);
    }

    @Override
    public NumberResizeMatrix<T> transpose() {
        NumberResizeMatrix<T> matrix = createInstance(columnCount, rowCount, arr);
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                matrix.set(columnIndex, rowIndex, get(rowIndex, columnIndex).deepcopy());
            }
        }
        return matrix;
    }
    //endregion

    public NumberResizeMatrix<T> prod(NumberMatrix<T> doubleMatrix) {
        int newColumnCount = Math.max(columnCount, doubleMatrix.getColumnCount());
        int newRowCount = Math.max(rowCount, doubleMatrix.getRowCount());
        T[] nArr = (T[]) new Number[newColumnCount * newRowCount];
        for (int columnIndex = 0; columnIndex < newColumnCount; columnIndex++) {
            for (int rowIndex = 0; rowIndex < newRowCount; rowIndex++) {
                nArr[columnIndex + rowIndex * newColumnCount] = get(rowIndex, columnIndex).multiply(doubleMatrix.get(rowIndex, columnIndex));
            }
        }
        return new NumberResizeMatrix<T>(newRowCount, newColumnCount, nArr);
    }

    @Override
    public NumberMatrix<T> multy(NumberMatrix<T> doubleMatrix) {
        if (columnCount != doubleMatrix.getRowCount())
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MULTY_MISMATCH);
        NumberResizeMatrix<T> result = NumberResizeMatrix.createInstance(rowCount, doubleMatrix.getColumnCount());
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
