package com.darkempire.math.struct.matrix;

import com.darkempire.math.struct.function.interfaces.FMatrixAndIndexToSome;
import com.darkempire.math.struct.function.interfaces.FMatrixIndexToSome;
import com.darkempire.math.struct.vector.MatrixVector;
import com.darkempire.math.struct.vector.Vector;

import java.util.stream.Stream;

/**
 * Create in 9:02
 * Created by siredvin on 18.12.13.
 */
public class Matrix<T> implements IMatrix<Matrix<T>, T>, IMatrixProvider<T> {
    protected T[] array;
    private int columnCount;
    private int rowCount;

    //region Конструктори
    protected Matrix(int rowCount, int columnCount, T[] array) {
        this.columnCount = columnCount;
        this.rowCount = rowCount;
        this.array = array;
    }
    //endregion

    //region Геттери

    //region Створення матриці
    public static <K> Matrix<K> createInstance(int rowCount, int columnCount, K[] array) {
        if (columnCount * rowCount != array.length)
            throw new ArrayIndexOutOfBoundsException();
        return new Matrix<>(rowCount, columnCount, array);
    }

    /**
     * @param columnIndex номер стовбця
     * @param rowIndex    номер рядку
     * @return елемент у комірці
     */
    @Override
    public T getEl(int rowIndex, int columnIndex) {
        return array[columnIndex + rowIndex * columnCount];
    }

    @Override
    public int getColumnCount() {
        return columnCount;
    }

    @Override
    public T get(int rowIndex, int columnIndex) {
        return array[columnIndex + rowIndex * columnCount];
    }
    //endregion

    //region Сеттери

    @Override
    public int getRowCount() {
        return rowCount;
    }

    /**
     * @param columnIndex номер стовбця
     * @param rowIndex    номер рядку
     * @param value       значення елементу у комірці
     */
    @Override
    public void setEl(int rowIndex, int columnIndex, T value) {
        array[columnIndex + rowIndex * columnCount] = value;
    }
    //endregion

    public void set(int rowIndex, int columnIndex, T value) {
        array[columnIndex + rowIndex * columnCount] = value;
    }

    //region Переміщення рядків або стовпчиків місцями
    @Override
    public Matrix<T> switchRows(int rowIndex1, int rowIndex2) {
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            T temp = array[columnIndex + rowIndex1 * columnCount];
            array[columnIndex + rowIndex1 * columnCount] = array[columnIndex + rowIndex2 * columnCount];
            array[columnIndex + rowIndex2 * columnCount] = temp;
        }
        return this;
    }
    //endregion

    @Override
    public Matrix<T> switchColumns(int columnIndex1, int columnIndex2) {
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            T temp = array[columnIndex1 + rowIndex * columnCount];
            array[columnIndex1 + rowIndex * columnCount] = array[columnIndex2 + rowIndex * columnCount];
            array[columnIndex2 + rowIndex * columnCount] = temp;
        }
        return this;
    }

    //region Системні функції
    @Override
    public Matrix<T> clone() {
        T[] arr = array.clone();
        return createInstance(columnCount, rowCount, arr);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                sb.append(getEl(rowIndex, columnIndex));
                sb.append(',');
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append('\n');
        }
        return sb.toString();
    }
    //endregion

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o instanceof Matrix) {
            Matrix m = (Matrix) o;
            int rowCount = m.getRowCount();
            int columntCount = m.getColumnCount();
            if (rowCount == this.rowCount && columntCount == this.columnCount) {
                for (int i = 0; i < rowCount; i++) {
                    for (int j = 0; j < columnCount; j++) {
                        if (!getEl(i, j).equals(m.getEl(i, j))) {
                            return false;
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }
    //endregion

    //region Заповнювачі
    //region Заповнювачі всієї матриці

    /**
     * Заповнює всю матрицю елементами
     *
     * @param function функція, яка залежить від положення елементу
     * @return цей об’єкт
     * @see com.darkempire.math.struct.function.interfaces.FMatrixIndexToSome
     */
    public Matrix<T> fill(FMatrixIndexToSome<T> function) {
        int columnCount = getColumnCount();
        int rowCount = getRowCount();
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                setEl(rowIndex, columnIndex, function.calc(rowIndex, columnIndex));
            }
        }
        return this;
    }

    /**
     * Заповнює всю матрицю елементами
     *
     * @param function функція, яка залежить від положення елементу і самої матриці
     * @return цей об’єкт
     * @see com.darkempire.math.struct.function.interfaces.FMatrixAndIndexToSome
     */
    public Matrix<T> fill(FMatrixAndIndexToSome<T> function) {
        int columnCount = getColumnCount();
        int rowCount = getRowCount();
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                setEl(rowIndex, columnIndex, function.calc(this, rowIndex, columnIndex));
            }
        }
        return this;
    }
    //endregion

    //region Заповнювачі прямокутників
    public Matrix<T> fillRectangle(int startRow, int startColumn, int endRow, int endColumn, FMatrixIndexToSome<T> function) {
        endColumn++;
        endRow++;
        for (int columnIndex = startColumn; columnIndex < endColumn; columnIndex++) {
            for (int rowIndex = startRow; rowIndex < endRow; rowIndex++) {
                setEl(rowIndex, columnIndex, function.calc(rowIndex, columnIndex));
            }
        }
        return this;
    }

    public Matrix<T> fillRectangle(int startRow, int startColumn, int endRow, int endColumn, FMatrixAndIndexToSome<T> function) {
        endColumn++;
        endRow++;
        for (int columnIndex = startColumn; columnIndex < endColumn; columnIndex++) {
            for (int rowIndex = startRow; rowIndex < endRow; rowIndex++) {
                setEl(rowIndex, columnIndex, function.calc(this, rowIndex, columnIndex));
            }
        }
        return this;
    }
    //endregion

    //region Заповнювачі рядків
    public Matrix<T> fillRow(int rowIndex, T... values) {
        int columnCount = getColumnCount();
        if (values.length != columnCount)
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MISMATCH);
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            setEl(rowIndex, columnIndex, values[columnIndex]);
        }
        return this;
    }

    public Matrix<T> fillRow(int rowIndex, FMatrixIndexToSome<T> function) {
        int columnCount = getColumnCount();
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            setEl(rowIndex, columnIndex, function.calc(rowIndex, columnIndex));
        }
        return this;
    }

    public Matrix<T> fillRow(int rowIndex, FMatrixAndIndexToSome<T> function) {
        int columnCount = getColumnCount();
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            setEl(rowIndex, columnIndex, function.calc(this, rowIndex, columnIndex));
        }
        return this;
    }

    public Matrix<T> fillFirstRow(FMatrixIndexToSome<T> function) {
        return fillRow(0, function);
    }

    public Matrix<T> fillFirstRow(FMatrixAndIndexToSome<T> function) {
        return fillRow(0, function);
    }

    public Matrix<T> fillLastRow(FMatrixAndIndexToSome<T> function) {
        return fillRow(getRowCount() - 1, function);
    }

    public Matrix<T> fillLastRow(FMatrixIndexToSome<T> function) {
        return fillRow(getRowCount() - 1, function);
    }
    //endregion

    //region Заповнювачі стовпчиків
    public Matrix fillColumn(int columnIndex, T... values) {
        int rowCount = getRowCount();
        if (values.length != rowCount)
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MISMATCH);
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            setEl(rowIndex, columnIndex, values[rowIndex]);
        }
        return this;
    }


    public Matrix<T> fillColumn(int columnIndex, FMatrixIndexToSome<T> function) {
        int rowCount = getRowCount();
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            setEl(rowIndex, columnIndex, function.calc(rowIndex, columnIndex));
        }
        return this;
    }

    public Matrix<T> fillColumn(int columnIndex, FMatrixAndIndexToSome<T> function) {
        int rowCount = getRowCount();
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            setEl(rowIndex, columnIndex, function.calc(this, rowIndex, columnIndex));
        }
        return this;
    }

    public Matrix<T> fillFirstColumn(FMatrixAndIndexToSome<T> function) {
        return fillColumn(0, function);
    }

    public Matrix<T> fillFirstColumn(FMatrixIndexToSome<T> function) {
        return fillColumn(0, function);
    }


    public Matrix<T> fillLastColumn(FMatrixAndIndexToSome<T> function) {
        return fillColumn(getColumnCount() - 1, function);
    }

    public Matrix<T> fillLastColumn(FMatrixIndexToSome<T> function) {
        return fillColumn(getColumnCount() - 1, function);
    }
    //endregion
    //endregion

    //region Дріблення матриці на вектори
    public Vector<T> row(int rowIndex) {
        return MatrixVector.row(rowIndex, this);
    }

    public Vector<T> column(int columnIndex) {
        return MatrixVector.column(columnIndex, this);
    }

    public Stream<Vector> rows() {
        int rowCount = getRowCount();
        Vector[] temp = new Vector[rowCount];
        for (int i = 0; i < rowCount; i++) {
            temp[i] = row(i);
        }
        return Stream.of(temp);
    }

    public Stream<Vector> columns() {
        int columnCount = getColumnCount();
        Vector[] temp = new Vector[columnCount];
        for (int i = 0; i < columnCount; i++) {
            temp[i] = column(i);
        }
        return Stream.of(temp);
    }
    //endregion
}
