package com.darkempire.math.struct.matrix;

import com.darkempire.math.struct.function.interfaces.FDoubleMatrixAndIndexToDouble;
import com.darkempire.math.struct.function.interfaces.FMatrixIndexToDouble;
import com.darkempire.math.struct.vector.DoubleResizeVector;
import com.darkempire.math.struct.vector.DoubleVector;

import java.util.Arrays;

import static java.lang.Math.abs;

/**
 * Create in 11:55
 * Created by siredvin on 21.12.13.
 */
public class DoubleResizeMatrix extends DoubleMatrix {
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
    private DoubleResizeMatrix(int rowCount, int columnCount, double[] arr) {
        this.columnCount = columnCount;
        this.rowCount = rowCount;
        this.arr = arr;
    }
    //endregion

    //region Геттери

    /**
     * Отримання елементу як примітивного типу з матриці
     * Зауважимо, що це матриця, що розтягується, отже всі невказані елементи нульові.
     *
     * @param columnIndex індекс колонки
     * @param rowIndex    індекс рядку
     * @return значення поля
     * @see com.darkempire.math.struct.matrix.DoubleMatrix
     */
    @Override
    public double get(int rowIndex, int columnIndex) {
        if (columnIndex > columnCount - 1 || rowIndex > rowCount - 1)
            return 0;
        return arr[columnIndex + rowIndex * columnCount];
    }

    @Override
    protected double get(int index) {
        return arr[index];
    }

    /**
     * @return кількість колонок
     * @see com.darkempire.math.struct.matrix.IMatrix
     */
    @Override
    public int getColumnCount() {
        return columnCount;
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
        if (columnIndex > columnCount - 1 || rowIndex > rowCount - 1)
            return;
        arr[columnIndex + rowIndex * columnCount] = value;
    }

    @Override
    protected void set(int index, double value) {
        arr[index] = value;
    }
    //endregion

    //region Системні функції

    /**
     * Реалізує глибоке копіювання матриці фіксованної розмірності
     *
     * @return Глибоку копію об’єкту
     */
    @Override
    public DoubleResizeMatrix clone() {
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

    //region Заповнювачі рядків
    @Override
    public DoubleResizeMatrix fillRow(int rowIndex, double value) {
        rowIndex *= columnCount;
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            arr[rowIndex] = value;
            rowIndex++;
        }
        return this;
    }

    @Override
    public DoubleResizeMatrix fillRow(int rowIndex, FMatrixIndexToDouble function) {
        int pos = rowIndex * columnCount;
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            arr[pos] = function.calc(rowIndex, columnIndex);
            pos++;
        }
        return this;
    }

    @Override
    public DoubleResizeMatrix fillRow(int rowIndex, FDoubleMatrixAndIndexToDouble function) {
        int pos = rowIndex * columnCount;
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            arr[pos] = function.calc(this, rowIndex, columnIndex);
            pos++;
        }
        return this;
    }
    //endregion

    //region Заповнювачі стовпчиків
    @Override
    public DoubleResizeMatrix fillColumn(int columnIndex, double value) {
        int pos = columnIndex;
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            arr[pos] = value;
            pos += columnCount;
        }
        return this;
    }

    @Override
    public DoubleResizeMatrix fillColumn(int columnIndex, FMatrixIndexToDouble function) {
        int pos = columnIndex;
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            arr[pos] = function.calc(rowIndex, columnIndex);
            pos += columnCount;
        }
        return this;
    }

    @Override
    public DoubleResizeMatrix fillColumn(int columnIndex, FDoubleMatrixAndIndexToDouble function) {
        int pos = columnIndex;
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            arr[pos] = function.calc(this, rowIndex, columnIndex);
            pos += columnCount;
        }
        return this;
    }
    //endregion

    //region Арифметичні операції з присвоєнням
    @Override
    public DoubleResizeMatrix inegate() {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = -arr[i];
        }
        return this;
    }

    @Override
    public DoubleResizeMatrix iadd(DoubleMatrix doubleMatrix) {
        if (columnCount != doubleMatrix.getColumnCount() || rowCount != doubleMatrix.getRowCount()) {
            int newColumnCount = Math.max(columnCount, doubleMatrix.getColumnCount());
            int newRowCount = Math.max(rowCount, doubleMatrix.getRowCount());
            double[] nArr = new double[newColumnCount * newRowCount];
            for (int columnIndex = 0; columnIndex < newColumnCount; columnIndex++) {
                for (int rowIndex = 0; rowIndex < newRowCount; rowIndex++) {
                    nArr[columnIndex + rowIndex * newColumnCount] = get(rowIndex, columnIndex) + doubleMatrix.get(rowIndex, columnIndex);
                }
            }
            this.arr = nArr;
            return this;
        }

        for (int i = 0; i < arr.length; i++) {
            arr[i] += doubleMatrix.get(i);
        }
        return this;
    }

    @Override
    public DoubleResizeMatrix isubtract(DoubleMatrix doubleMatrix) {
        if (columnCount != doubleMatrix.getColumnCount() || rowCount != doubleMatrix.getRowCount()) {
            int newColumnCount = Math.max(columnCount, doubleMatrix.getColumnCount());
            int newRowCount = Math.max(rowCount, doubleMatrix.getRowCount());
            double[] nArr = new double[newColumnCount * newRowCount];
            for (int columnIndex = 0; columnIndex < newColumnCount; columnIndex++) {
                for (int rowIndex = 0; rowIndex < newRowCount; rowIndex++) {
                    nArr[columnIndex + rowIndex * newColumnCount] = get(rowIndex, columnIndex) - doubleMatrix.get(rowIndex, columnIndex);
                }
            }
            this.arr = nArr;
            return this;
        }
        for (int i = 0; i < arr.length; i++) {
            arr[i] -= doubleMatrix.get(i);
        }
        return this;
    }

    public DoubleResizeMatrix iprod(double lambda) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] *= lambda;
        }
        return this;
    }

    public DoubleResizeMatrix iprod(DoubleResizeMatrix doubleMatrix) {
        if (columnCount != doubleMatrix.columnCount || rowCount != doubleMatrix.rowCount) {
            int newColumnCount = Math.max(columnCount, doubleMatrix.columnCount);
            int newRowCount = Math.max(rowCount, doubleMatrix.rowCount);
            double[] nArr = new double[newColumnCount * newRowCount];
            for (int columnIndex = 0; columnIndex < newColumnCount; columnIndex++) {
                for (int rowIndex = 0; rowIndex < newRowCount; rowIndex++) {
                    nArr[columnIndex + rowIndex * newColumnCount] = get(rowIndex, columnIndex) * doubleMatrix.get(rowIndex, columnIndex);
                }
            }
            this.arr = nArr;
            return this;
        }
        for (int i = 0; i < arr.length; i++) {
            arr[i] *= doubleMatrix.arr[i];
        }
        return this;
    }

    @Override
    public DoubleResizeMatrix itranspose() {
        if (columnCount != rowCount) {
            double[] nArr = new double[columnCount * rowCount];
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
    public DoubleResizeMatrix add(DoubleMatrix doubleMatrix) {
        int newColumnCount = Math.max(columnCount, doubleMatrix.getColumnCount());
        int newRowCount = Math.max(rowCount, doubleMatrix.getRowCount());
        double[] nArr = new double[newColumnCount * newRowCount];
        for (int columnIndex = 0; columnIndex < newColumnCount; columnIndex++) {
            for (int rowIndex = 0; rowIndex < newRowCount; rowIndex++) {
                nArr[columnIndex + rowIndex * newColumnCount] = get(rowIndex, columnIndex) + doubleMatrix.get(rowIndex, columnIndex);
            }
        }
        return new DoubleResizeMatrix(newRowCount, newColumnCount, nArr);
    }

    @Override
    public DoubleResizeMatrix subtract(DoubleMatrix doubleMatrix) {
        int newColumnCount = Math.max(columnCount, doubleMatrix.getColumnCount());
        int newRowCount = Math.max(rowCount, doubleMatrix.getRowCount());
        double[] nArr = new double[newColumnCount * newRowCount];
        for (int columnIndex = 0; columnIndex < newColumnCount; columnIndex++) {
            for (int rowIndex = 0; rowIndex < newRowCount; rowIndex++) {
                nArr[columnIndex + rowIndex * newColumnCount] = get(rowIndex, columnIndex) - doubleMatrix.get(rowIndex, columnIndex);
            }
        }
        return new DoubleResizeMatrix(newRowCount, newColumnCount, nArr);
    }

    @Override
    public DoubleResizeMatrix negate() {
        double[] nArr = new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            nArr[i] = -arr[i];
        }
        return new DoubleResizeMatrix(rowCount, columnCount, nArr);
    }


    @Override
    public DoubleResizeMatrix prod(double lambda) {
        double[] nArr = new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            nArr[i] = lambda * arr[i];
        }
        return new DoubleResizeMatrix(rowCount, columnCount, nArr);
    }


    @Override
    public DoubleResizeMatrix transpose() {
        DoubleResizeMatrix matrix = createInstance(columnCount, rowCount);
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                matrix.set(columnIndex, rowIndex, get(rowIndex, columnIndex));
            }
        }
        return matrix;
    }

    @Override
    public DoubleMatrix prod(DoubleMatrix doubleMatrix) {
        int newColumnCount = Math.max(columnCount, doubleMatrix.getColumnCount());
        int newRowCount = Math.max(rowCount, doubleMatrix.getRowCount());
        double[] nArr = new double[newColumnCount * newRowCount];
        for (int columnIndex = 0; columnIndex < newColumnCount; columnIndex++) {
            for (int rowIndex = 0; rowIndex < newRowCount; rowIndex++) {
                nArr[columnIndex + rowIndex * newColumnCount] = get(rowIndex, columnIndex) * doubleMatrix.get(rowIndex, columnIndex);
            }
        }
        return new DoubleResizeMatrix(newRowCount, newColumnCount, nArr);
    }

    @Override
    public DoubleMatrix multy(DoubleMatrix doubleMatrix) {
        if (columnCount != doubleMatrix.getRowCount())
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MULTY_MISMATCH);
        DoubleResizeMatrix result = DoubleResizeMatrix.createInstance(rowCount, doubleMatrix.getColumnCount());
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
        DoubleVector result = new DoubleResizeVector(rowCount);
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

    //region Створення матриці
    public static DoubleResizeMatrix createInstance(int rowCount, int columnCount, double[] array) {
        if (columnCount * rowCount != array.length)
            throw new ArrayIndexOutOfBoundsException();
        return new DoubleResizeMatrix(rowCount, columnCount, array);
    }

    public static DoubleResizeMatrix createInstance(int rowCount, int columnCount) {
        double[] array = new double[columnCount * rowCount];
        return createInstance(rowCount, columnCount, array);
    }
    //endregion

    @Override
    public DoubleMatrix deepcopy() {
        return clone();
    }
}
