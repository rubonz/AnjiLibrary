package com.darkempire.math.struct.matrix;

import com.darkempire.math.MathMachine;
import com.darkempire.math.exception.MatrixSizeException;
import com.darkempire.math.struct.LogicCalcable;
import com.darkempire.math.struct.function.interfaces.FDoubleMatrixAndIndexToDouble;
import com.darkempire.math.struct.function.interfaces.FMatrixIndexToDouble;
import com.darkempire.math.struct.set.SetOperable;
import com.darkempire.math.struct.vector.DoubleResizeVector;
import com.darkempire.math.struct.vector.DoubleVector;

import java.util.Arrays;

import static java.lang.Math.abs;

/**
 * Create in 11:55
 * Created by siredvin on 21.12.13.
 */
public class FuzzyRelationMatrix extends DoubleMatrix implements SetOperable<FuzzyRelationMatrix>, LogicCalcable<FuzzyRelationMatrix> {
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
    private FuzzyRelationMatrix(int rowCount, int columnCount, double[] arr) {
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

    @Override
    protected double get(int index) {
        return arr[index];
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
    public FuzzyRelationMatrix clone() {
        double[] array = arr.clone();
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
    //endregion

    //region Заповнювачі

    //region Заповнювачі рядків
    @Override
    public FuzzyRelationMatrix fillRow(int rowIndex, double value) {
        rowIndex *= columnCount;
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            arr[rowIndex] = value;
            rowIndex++;
        }
        return this;
    }

    @Override
    public FuzzyRelationMatrix fillRow(int rowIndex, FMatrixIndexToDouble function) {
        int pos = rowIndex * columnCount;
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            arr[pos] = function.calc(rowIndex, columnIndex);
            pos++;
        }
        return this;
    }

    @Override
    public FuzzyRelationMatrix fillRow(int rowIndex, FDoubleMatrixAndIndexToDouble function) {
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
    public FuzzyRelationMatrix fillColumn(int columnIndex, double value) {
        int pos = columnIndex;
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            arr[pos] = value;
            pos += columnCount;
        }
        return this;
    }

    @Override
    public FuzzyRelationMatrix fillColumn(int columnIndex, FMatrixIndexToDouble function) {
        int pos = columnIndex;
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            arr[pos] = function.calc(rowIndex, columnIndex);
            pos += columnCount;
        }
        return this;
    }

    @Override
    public FuzzyRelationMatrix fillColumn(int columnIndex, FDoubleMatrixAndIndexToDouble function) {
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
    public FuzzyRelationMatrix inegate() {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1 - arr[i];
        }
        return this;
    }

    @Override
    public FuzzyRelationMatrix iadd(DoubleMatrix doubleMatrix) {
        if (columnCount != doubleMatrix.getColumnCount() || rowCount != doubleMatrix.getRowCount()) {
            int newColumnCount = Math.max(columnCount, doubleMatrix.getColumnCount());
            int newRowCount = Math.max(rowCount, doubleMatrix.getRowCount());
            double[] nArr = new double[newColumnCount * newRowCount];
            for (int columnIndex = 0; columnIndex < newColumnCount; columnIndex++) {
                for (int rowIndex = 0; rowIndex < newRowCount; rowIndex++) {
                    nArr[columnIndex + rowIndex * newColumnCount] = Math.min(get(rowIndex, columnIndex) + doubleMatrix.get(rowIndex, columnIndex), 1);
                }
            }
            this.arr = nArr;
            return this;
        }

        for (int i = 0; i < arr.length; i++) {
            arr[i] = Math.min(1, arr[i] + doubleMatrix.get(i));
        }
        return this;
    }

    @Override
    public FuzzyRelationMatrix isubtract(DoubleMatrix doubleMatrix) {
        if (columnCount != doubleMatrix.getColumnCount() || rowCount != doubleMatrix.getRowCount()) {
            int newColumnCount = Math.max(columnCount, doubleMatrix.getColumnCount());
            int newRowCount = Math.max(rowCount, doubleMatrix.getRowCount());
            double[] nArr = new double[newColumnCount * newRowCount];
            for (int columnIndex = 0; columnIndex < newColumnCount; columnIndex++) {
                for (int rowIndex = 0; rowIndex < newRowCount; rowIndex++) {
                    nArr[columnIndex + rowIndex * newColumnCount] = Math.max(get(rowIndex, columnIndex) - doubleMatrix.get(rowIndex, columnIndex), 0);
                }
            }
            this.arr = nArr;
            return this;
        }
        for (int i = 0; i < arr.length; i++) {
            arr[i] = Math.max(0, arr[i] - doubleMatrix.get(i));
        }
        return this;
    }

    @Override
    public FuzzyRelationMatrix itranspose() {
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

    public FuzzyRelationMatrix iprod(double lambda) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] *= lambda;
        }
        return this;
    }

    public FuzzyRelationMatrix iprod(FuzzyRelationMatrix doubleMatrix) {
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
    //endregion

    //region Арифметичні операції
    @Override
    public FuzzyRelationMatrix add(DoubleMatrix doubleMatrix) {
        int newColumnCount = Math.max(columnCount, doubleMatrix.getColumnCount());
        int newRowCount = Math.max(rowCount, doubleMatrix.getRowCount());
        double[] nArr = new double[newColumnCount * newRowCount];
        for (int columnIndex = 0; columnIndex < newColumnCount; columnIndex++) {
            for (int rowIndex = 0; rowIndex < newRowCount; rowIndex++) {
                nArr[columnIndex + rowIndex * newColumnCount] = Math.min(get(rowIndex, columnIndex) + doubleMatrix.get(rowIndex, columnIndex), 1);
            }
        }
        return new FuzzyRelationMatrix(newRowCount, newColumnCount, nArr);
    }

    @Override
    public FuzzyRelationMatrix subtract(DoubleMatrix doubleMatrix) {
        int newColumnCount = Math.max(columnCount, doubleMatrix.getColumnCount());
        int newRowCount = Math.max(rowCount, doubleMatrix.getRowCount());
        double[] nArr = new double[newColumnCount * newRowCount];
        for (int columnIndex = 0; columnIndex < newColumnCount; columnIndex++) {
            for (int rowIndex = 0; rowIndex < newRowCount; rowIndex++) {
                nArr[columnIndex + rowIndex * newColumnCount] = Math.max(get(rowIndex, columnIndex) - doubleMatrix.get(rowIndex, columnIndex), 0);
            }
        }
        return new FuzzyRelationMatrix(newRowCount, newColumnCount, nArr);
    }

    @Override
    public FuzzyRelationMatrix negate() {
        double[] nArr = new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            nArr[i] = 1 - arr[i];
        }
        return new FuzzyRelationMatrix(rowCount, columnCount, nArr);
    }


    @Override
    public FuzzyRelationMatrix transpose() {
        FuzzyRelationMatrix matrix = createInstance(columnCount, rowCount);
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
        return new FuzzyRelationMatrix(newRowCount, newColumnCount, nArr);
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

    @Override
    public FuzzyRelationMatrix prod(double lambda) {
        double[] nArr = new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            nArr[i] = lambda * arr[i];
        }
        return new FuzzyRelationMatrix(rowCount, columnCount, nArr);
    }


    public FuzzyRelationMatrix prod(FuzzyRelationMatrix doubleMatrix) {
        int newColumnCount = Math.max(columnCount, doubleMatrix.columnCount);
        int newRowCount = Math.max(rowCount, doubleMatrix.rowCount);
        double[] nArr = new double[newColumnCount * newRowCount];
        for (int columnIndex = 0; columnIndex < newColumnCount; columnIndex++) {
            for (int rowIndex = 0; rowIndex < newRowCount; rowIndex++) {
                nArr[columnIndex + rowIndex * newColumnCount] = get(rowIndex, columnIndex) * doubleMatrix.get(rowIndex, columnIndex);
            }
        }
        return new FuzzyRelationMatrix(newRowCount, newColumnCount, nArr);
    }
    //endregion

    //region Створення матриці
    public static FuzzyRelationMatrix createInstance(int rowCount, int columnCount, double[] array) {
        if (columnCount * rowCount != array.length)
            throw new ArrayIndexOutOfBoundsException();
        return new FuzzyRelationMatrix(rowCount, columnCount, array);
    }

    public static FuzzyRelationMatrix createInstance(int rowCount, int columnCount) {
        double[] array = new double[columnCount * rowCount];
        return createInstance(rowCount, columnCount, array);
    }
    //endregion

    //region Операції над множинами з присвоєнням
    @Override
    public FuzzyRelationMatrix iunion(FuzzyRelationMatrix fuzzyRelationMatrix) {
        if (columnCount != fuzzyRelationMatrix.getColumnCount() || rowCount != fuzzyRelationMatrix.getRowCount()) {
            int newColumnCount = Math.max(columnCount, fuzzyRelationMatrix.getColumnCount());
            int newRowCount = Math.max(rowCount, fuzzyRelationMatrix.getRowCount());
            double[] nArr = new double[newColumnCount * newRowCount];
            for (int columnIndex = 0; columnIndex < newColumnCount; columnIndex++) {
                for (int rowIndex = 0; rowIndex < newRowCount; rowIndex++) {
                    nArr[columnIndex + rowIndex * newColumnCount] = Math.max(get(rowIndex, columnIndex), fuzzyRelationMatrix.get(rowIndex, columnIndex));
                }
            }
            this.arr = nArr;
            return this;
        }
        for (int i = 0; i < arr.length; i++) {
            arr[i] = Math.max(arr[i], fuzzyRelationMatrix.get(i));
        }
        return this;
    }

    @Override
    public FuzzyRelationMatrix iintersection(FuzzyRelationMatrix fuzzyRelationMatrix) {
        if (columnCount != fuzzyRelationMatrix.getColumnCount() || rowCount != fuzzyRelationMatrix.getRowCount()) {
            int newColumnCount = Math.max(columnCount, fuzzyRelationMatrix.getColumnCount());
            int newRowCount = Math.max(rowCount, fuzzyRelationMatrix.getRowCount());
            double[] nArr = new double[newColumnCount * newRowCount];
            for (int columnIndex = 0; columnIndex < newColumnCount; columnIndex++) {
                for (int rowIndex = 0; rowIndex < newRowCount; rowIndex++) {
                    nArr[columnIndex + rowIndex * newColumnCount] = Math.min(get(rowIndex, columnIndex), fuzzyRelationMatrix.get(rowIndex, columnIndex));
                }
            }
            this.arr = nArr;
            return this;
        }
        for (int i = 0; i < arr.length; i++) {
            arr[i] = Math.min(arr[i], fuzzyRelationMatrix.get(i));
        }
        return this;
    }

    @Override
    public FuzzyRelationMatrix icomplement() {
        return inegate();
    }

    @Override
    public FuzzyRelationMatrix isetminus(FuzzyRelationMatrix fuzzyRelationMatrix) {
        return isubtract(fuzzyRelationMatrix);
    }
    //endregion

    //region Операції над множинами
    @Override
    public FuzzyRelationMatrix union(FuzzyRelationMatrix fuzzyRelationMatrix) {
        int newColumnCount = Math.max(columnCount, fuzzyRelationMatrix.getColumnCount());
        int newRowCount = Math.max(rowCount, fuzzyRelationMatrix.getRowCount());
        double[] nArr = new double[newColumnCount * newRowCount];
        for (int columnIndex = 0; columnIndex < newColumnCount; columnIndex++) {
            for (int rowIndex = 0; rowIndex < newRowCount; rowIndex++) {
                nArr[columnIndex + rowIndex * newColumnCount] = Math.max(get(rowIndex, columnIndex), fuzzyRelationMatrix.get(rowIndex, columnIndex));
            }
        }
        return new FuzzyRelationMatrix(newRowCount, newColumnCount, nArr);
    }

    @Override
    public FuzzyRelationMatrix intersection(FuzzyRelationMatrix fuzzyRelationMatrix) {
        int newColumnCount = Math.max(columnCount, fuzzyRelationMatrix.getColumnCount());
        int newRowCount = Math.max(rowCount, fuzzyRelationMatrix.getRowCount());
        double[] nArr = new double[newColumnCount * newRowCount];
        for (int columnIndex = 0; columnIndex < newColumnCount; columnIndex++) {
            for (int rowIndex = 0; rowIndex < newRowCount; rowIndex++) {
                nArr[columnIndex + rowIndex * newColumnCount] = Math.min(get(rowIndex, columnIndex), fuzzyRelationMatrix.get(rowIndex, columnIndex));
            }
        }
        return new FuzzyRelationMatrix(newRowCount, newColumnCount, nArr);
    }

    @Override
    public FuzzyRelationMatrix complement() {
        return negate();
    }

    @Override
    public FuzzyRelationMatrix setminus(FuzzyRelationMatrix fuzzyRelationMatrix) {
        return subtract(fuzzyRelationMatrix);
    }
    //endregion

    //region Операції над нечіткими множинами
    public FuzzyRelationMatrix pintersection(FuzzyRelationMatrix fuzzyRelationMatrix) {
        int newColumnCount = Math.max(columnCount, fuzzyRelationMatrix.getColumnCount());
        int newRowCount = Math.max(rowCount, fuzzyRelationMatrix.getRowCount());
        double[] nArr = new double[newColumnCount * newRowCount];
        for (int columnIndex = 0; columnIndex < newColumnCount; columnIndex++) {
            for (int rowIndex = 0; rowIndex < newRowCount; rowIndex++) {
                nArr[columnIndex + rowIndex * newColumnCount] = get(rowIndex, columnIndex) * fuzzyRelationMatrix.get(rowIndex, columnIndex);
            }
        }
        return new FuzzyRelationMatrix(newRowCount, newColumnCount, nArr);
    }

    public FuzzyRelationMatrix maxminC(FuzzyRelationMatrix fuzzyRelationMatrix) {
        if (columnCount != fuzzyRelationMatrix.getRowCount())
            throw new MatrixSizeException(MatrixSizeException.MATRIX_SIZE_MULTY_MISMATCH);
        FuzzyRelationMatrix result = createInstance(rowCount, fuzzyRelationMatrix.getColumnCount());
        result.fill((rowIndex, columnIndex) -> {
            double sup = 0;
            for (int k = 0; k < columnCount; k++) {
                double temp = Math.min(fuzzyRelationMatrix.get(k, columnIndex), get(rowIndex, k));
                if (temp > sup) {
                    sup = temp;
                }
            }
            return sup;
        });
        return result;
    }

    public FuzzyRelationMatrix maxminC() {
        if (columnCount != rowCount)
            throw new MatrixSizeException(MatrixSizeException.MATRIX_IS_NOT_SQUARE);
        FuzzyRelationMatrix result = createInstance(rowCount, columnCount);
        result.fill((rowIndex, columnIndex) -> {
            double sup = 0;
            for (int k = 0; k < columnCount; k++) {
                double temp = Math.min(get(k, columnIndex), get(rowIndex, k));
                if (temp > sup) {
                    sup = temp;
                }
            }
            return sup;
        });
        return result;
    }

    public FuzzyRelationMatrix minmaxC(FuzzyRelationMatrix fuzzyRelationMatrix) {
        if (columnCount != fuzzyRelationMatrix.getRowCount())
            throw new MatrixSizeException(MatrixSizeException.MATRIX_SIZE_MULTY_MISMATCH);
        FuzzyRelationMatrix result = createInstance(rowCount, fuzzyRelationMatrix.getColumnCount());
        result.fill((rowIndex, columnIndex) -> {
            double min = 1;
            for (int k = 0; k < columnCount; k++) {
                double temp = Math.max(fuzzyRelationMatrix.get(k, columnIndex), get(rowIndex, k));
                if (temp < min) {
                    min = temp;
                }
            }
            return min;
        });
        return result;
    }

    public FuzzyRelationMatrix minmaxC() {
        if (columnCount != rowCount)
            throw new MatrixSizeException(MatrixSizeException.MATRIX_IS_NOT_SQUARE);
        FuzzyRelationMatrix result = createInstance(rowCount, columnCount);
        result.fill((rowIndex, columnIndex) -> {
            double min = 1;
            for (int k = 0; k < columnCount; k++) {
                double temp = Math.max(get(k, columnIndex), get(rowIndex, k));
                if (temp < min) {
                    min = temp;
                }
            }
            return min;
        });
        return result;
    }

    public FuzzyRelationMatrix maxmultC(FuzzyRelationMatrix fuzzyRelationMatrix) {
        if (columnCount != fuzzyRelationMatrix.getRowCount())
            throw new MatrixSizeException(MatrixSizeException.MATRIX_SIZE_MULTY_MISMATCH);
        FuzzyRelationMatrix result = createInstance(rowCount, fuzzyRelationMatrix.getColumnCount());
        result.fill((rowIndex, columnIndex) -> {
            double sup = 0;
            for (int k = 0; k < columnCount; k++) {
                double temp = fuzzyRelationMatrix.get(k, columnIndex) * get(rowIndex, k);
                if (temp > sup) {
                    sup = temp;
                }
            }
            return sup;
        });
        return result;
    }

    public FuzzyRelationMatrix maxmultC() {
        if (columnCount != rowCount)
            throw new MatrixSizeException(MatrixSizeException.MATRIX_IS_NOT_SQUARE);
        FuzzyRelationMatrix result = createInstance(rowCount, columnCount);
        result.fill((rowIndex, columnIndex) -> {
            double sup = 0;
            for (int k = 0; k < columnCount; k++) {
                double temp = get(k, columnIndex) * get(rowIndex, k);
                if (temp > sup) {
                    sup = temp;
                }
            }
            return sup;
        });
        return result;
    }
    //endregion

    //region Операції над нечіткими множинами з присвоєнням
    public FuzzyRelationMatrix ipintersection(FuzzyRelationMatrix fuzzyRelationMatrix) {
        if (columnCount != fuzzyRelationMatrix.getColumnCount() || rowCount != fuzzyRelationMatrix.getRowCount()) {
            int newColumnCount = Math.max(columnCount, fuzzyRelationMatrix.getColumnCount());
            int newRowCount = Math.max(rowCount, fuzzyRelationMatrix.getRowCount());
            double[] nArr = new double[newColumnCount * newRowCount];
            for (int columnIndex = 0; columnIndex < newColumnCount; columnIndex++) {
                for (int rowIndex = 0; rowIndex < newRowCount; rowIndex++) {
                    nArr[columnIndex + rowIndex * newColumnCount] = get(rowIndex, columnIndex) * fuzzyRelationMatrix.get(rowIndex, columnIndex);
                }
            }
            this.arr = nArr;
            return this;
        }
        for (int i = 0; i < arr.length; i++) {
            arr[i] = arr[i] * fuzzyRelationMatrix.get(i);
        }
        return this;
    }
    //endregion

    //region Логічні операції з присвоєнням
    @Override
    public FuzzyRelationMatrix iand(FuzzyRelationMatrix fuzzyRelationMatrix) {
        if (columnCount != fuzzyRelationMatrix.getColumnCount() || rowCount != fuzzyRelationMatrix.getRowCount()) {
            int newColumnCount = Math.max(columnCount, fuzzyRelationMatrix.getColumnCount());
            int newRowCount = Math.max(rowCount, fuzzyRelationMatrix.getRowCount());
            double[] nArr = new double[newColumnCount * newRowCount];
            for (int columnIndex = 0; columnIndex < newColumnCount; columnIndex++) {
                for (int rowIndex = 0; rowIndex < newRowCount; rowIndex++) {
                    nArr[columnIndex + rowIndex * newColumnCount] = get(rowIndex, columnIndex) * fuzzyRelationMatrix.get(rowIndex, columnIndex);
                }
            }
            this.arr = nArr;
            return this;
        }

        for (int i = 0; i < arr.length; i++) {
            arr[i] = arr[i] * fuzzyRelationMatrix.get(i);
        }
        return this;
    }

    @Override
    public FuzzyRelationMatrix ior(FuzzyRelationMatrix fuzzyRelationMatrix) {
        return iadd(fuzzyRelationMatrix);
    }

    @Override
    public FuzzyRelationMatrix ixor(FuzzyRelationMatrix fuzzyRelationMatrix) {
        if (columnCount != fuzzyRelationMatrix.getColumnCount() || rowCount != fuzzyRelationMatrix.getRowCount()) {
            int newColumnCount = Math.max(columnCount, fuzzyRelationMatrix.getColumnCount());
            int newRowCount = Math.max(rowCount, fuzzyRelationMatrix.getRowCount());
            double[] nArr = new double[newColumnCount * newRowCount];
            for (int columnIndex = 0; columnIndex < newColumnCount; columnIndex++) {
                for (int rowIndex = 0; rowIndex < newRowCount; rowIndex++) {
                    nArr[columnIndex + rowIndex * newColumnCount] = Math.max(Math.min(1 - get(rowIndex, columnIndex), fuzzyRelationMatrix.get(rowIndex, columnIndex)), Math.min(get(rowIndex, columnIndex), 1 - fuzzyRelationMatrix.get(rowIndex, columnIndex)));
                }
            }
            this.arr = nArr;
            return this;
        }

        for (int i = 0; i < arr.length; i++) {
            arr[i] = Math.max(Math.min(arr[i], 1 - fuzzyRelationMatrix.get(i)), Math.min(1 - arr[i], fuzzyRelationMatrix.get(i)));
        }
        return this;
    }

    @Override
    public FuzzyRelationMatrix inot() {
        return inegate();
    }
    //endregion

    //region Логічні операції
    @Override
    public FuzzyRelationMatrix and(FuzzyRelationMatrix fuzzyRelationMatrix) {
        int newColumnCount = Math.max(columnCount, fuzzyRelationMatrix.getColumnCount());
        int newRowCount = Math.max(rowCount, fuzzyRelationMatrix.getRowCount());
        double[] nArr = new double[newColumnCount * newRowCount];
        for (int columnIndex = 0; columnIndex < newColumnCount; columnIndex++) {
            for (int rowIndex = 0; rowIndex < newRowCount; rowIndex++) {
                nArr[columnIndex + rowIndex * newColumnCount] = get(rowIndex, columnIndex) * fuzzyRelationMatrix.get(rowIndex, columnIndex);
            }
        }
        return new FuzzyRelationMatrix(newRowCount, newColumnCount, nArr);
    }

    @Override
    public FuzzyRelationMatrix or(FuzzyRelationMatrix fuzzyRelationMatrix) {
        return add(fuzzyRelationMatrix);
    }

    @Override
    public FuzzyRelationMatrix xor(FuzzyRelationMatrix fuzzyRelationMatrix) {
        int newColumnCount = Math.max(columnCount, fuzzyRelationMatrix.getColumnCount());
        int newRowCount = Math.max(rowCount, fuzzyRelationMatrix.getRowCount());
        double[] nArr = new double[newColumnCount * newRowCount];
        for (int columnIndex = 0; columnIndex < newColumnCount; columnIndex++) {
            for (int rowIndex = 0; rowIndex < newRowCount; rowIndex++) {
                nArr[columnIndex + rowIndex * newColumnCount] = Math.max(Math.min(1 - get(rowIndex, columnIndex), fuzzyRelationMatrix.get(rowIndex, columnIndex)), Math.min(get(rowIndex, columnIndex), 1 - fuzzyRelationMatrix.get(rowIndex, columnIndex)));
            }
        }
        return new FuzzyRelationMatrix(newRowCount, newColumnCount, nArr);
    }

    @Override
    public FuzzyRelationMatrix not() {
        return negate();
    }
    //endregion

    @Override
    public FuzzyRelationMatrix deepcopy() {
        return clone();
    }
}
