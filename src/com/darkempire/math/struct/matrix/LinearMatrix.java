package com.darkempire.math.struct.matrix;

import com.darkempire.math.exception.MatrixSizeException;
import com.darkempire.math.struct.LinearCalcable;
import com.darkempire.math.struct.function.interfaces.*;
import com.darkempire.math.struct.vector.NumberVector;

import java.util.stream.Stream;

/**
 * Created by siredvin on 29.09.14.
 */
public abstract class LinearMatrix<T extends LinearCalcable<T>> implements LinearCalcable<LinearMatrix<T>>, ILinearMatrixProvider<T>, IMatrix<LinearMatrix<T>, T> {

    //region Геттери
    @Override
    public abstract T get(int rowIndex, int columnIndex);

    /**
     * Отримання елементу як об’єкту з матриці
     *
     * @param columnIndex індекс колонки
     * @param rowIndex    індекс рядку
     * @return значення поля
     * @see IMatrix
     */
    @Override
    public T getEl(int rowIndex, int columnIndex) {
        return get(rowIndex, columnIndex);
    }

    @Override
    public T getEl(MatrixIndex index) {
        return get(index.getRowIndex(), index.getColumnIndex());
    }

    public T get(MatrixIndex index) {
        return get(index.getRowIndex(), index.getColumnIndex());
    }

    protected abstract T get(int index);
    //endregion

    //region Сеттери
    public abstract void set(int rowIndex, int columnIndex, T value);

    /**
     * Запис елементу, заданого як об’єкт, у матрицю
     *
     * @param columnIndex індекс колонки
     * @param rowIndex    індекс рядку
     * @param value       значення
     * @see IMatrix
     */
    @Override
    public void setEl(int rowIndex, int columnIndex, T value) {
        set(rowIndex, columnIndex, value);
    }

    protected abstract void set(int index, T value);
    //endregion

    //region Зрізи
    /*
    public LinearMatrixSlice slice(int startRow, int startColumn, int endRow, int endColumn) {
        return LinearMatrixSlice.createSlice(this, startRow, startColumn, endRow, endColumn);
    }

    public LinearMatrixSlice slice(int[] rowIndexs, int[] columnIndexs) {
        return LinearMatrixSlice.createSlice(this, rowIndexs, columnIndexs);
    }

    public LinearMatrixSlice sliceColumns(int[] columnsIndex) {
        int rowCount = getRowCount();
        int[] rowIndexs = new int[rowCount];
        for (int i = 0; i < rowCount; i++) {
            rowIndexs[i] = i;
        }
        return LinearMatrixSlice.createSlice(this, rowIndexs, columnsIndex);
    }

    public LinearMatrixSlice sliceRows(int[] rowIndex) {
        int columnCount = getColumnCount();
        int[] columnIndexs = new int[columnCount];
        for (int i = 0; i < columnCount; i++) {
            columnIndexs[i] = i;
        }
        return LinearMatrixSlice.createSlice(this, rowIndex, columnIndexs);
    }

    public LinearMatrixSlice slicePeriodic(int rowPeriod, int columnPeriod) {
        int rowCount = getRowCount();
        int columnCount = getColumnCount();
        int columnAdditional = columnCount % columnPeriod != 0 ? 1 : 0;
        int rowAdditional = rowCount % rowPeriod != 0 ? 1 : 0;
        int[] rowIndexs = new int[rowCount / rowPeriod + rowAdditional];
        int[] columnIndexs = new int[columnCount / columnPeriod + columnAdditional];
        for (int i = 0; i < rowIndexs.length; i++) {
            rowIndexs[i] = i * rowPeriod;
        }
        for (int i = 0; i < columnIndexs.length; i++) {
            columnIndexs[i] = i * columnPeriod;
        }
        return LinearMatrixSlice.createSlice(this, rowIndexs, columnIndexs);
    }TODO:реалізувати
    */
    //endregion

    //region Арифметичні операції з присвоєнням
    @Override
    public LinearMatrix<T> itranspose() {
        if (getRowCount() != getColumnCount()) {
            throw new MatrixSizeException(MatrixSizeException.MATRIX_IS_NOT_SQUARE);
        }
        int size = getRowCount();
        for (int rowIndex = 0; rowIndex < size; rowIndex++) {
            for (int columnIndex = rowIndex + 1; columnIndex < size; columnIndex++) {
                T temp = get(rowIndex, columnIndex);
                set(rowIndex, columnIndex, get(columnIndex, rowIndex));
                set(columnIndex, rowIndex, temp);
            }
        }
        return this;
    }
    //endregion

    //region Перевірка матриці на властивості
    @Override
    public boolean isSymmetric() {
        boolean isSymmetric = false;
        int columnCount = getColumnCount();
        int rowCount = getRowCount();
        if (columnCount == rowCount) {
            isSymmetric = true;
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                    if (!get(rowIndex, columnIndex).equals(get(rowIndex, columnIndex))) {
                        isSymmetric = false;
                        break;
                    }
                }
                if (!isSymmetric)
                    break;
            }
        }
        return isSymmetric;
    }
    //endregion

    //region Системні функції
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o instanceof LinearMatrix) {
            LinearMatrix temp = (LinearMatrix) o;
            int columnCount = getColumnCount();
            int rowCount = getRowCount();
            if (columnCount == temp.getColumnCount() && rowCount == temp.getRowCount()) {
                for (int i = 0; i < rowCount; i++) {
                    for (int j = 0; j < columnCount; j++) {
                        if (get(i, j).equals(temp.get(i, j))) {
                            return false;
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public abstract LinearMatrix<T> clone();

    /**
     * Виводить до табличного виду без табуляції
     *
     * @return текстове зображення матриці
     */
    @Override
    public String toString() {
        int rowCount = getRowCount();
        int columnCount = getColumnCount();
        StringBuilder sb = new StringBuilder();
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                sb.append(get(rowIndex, columnIndex));
                sb.append(",\t");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.deleteCharAt(sb.length() - 1);
            sb.append(";\n");
        }
        return sb.toString();
    }
    //endregion

    //region Переміщення рядоків або стовпчиків місцями

    /**
     * Міняє два рядка місцями
     *
     * @param rowIndex1 індекс першого рядку
     * @param rowIndex2 індекс другого рядку
     * @return цей об’єкт
     */
    @Override
    public LinearMatrix<T> switchRows(int rowIndex1, int rowIndex2) {
        int columnCount = getColumnCount();
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            T temp = get(rowIndex1, columnIndex);
            set(rowIndex1, columnIndex, get(rowIndex2, columnIndex));
            set(rowIndex2, columnIndex, temp);
        }
        return this;
    }

    /**
     * Міняє дві колонки місцями
     *
     * @param columnIndex1 індекс першої колонки
     * @param columnIndex2 індекс другої колонки
     * @return цей об’єкт
     */
    @Override
    public LinearMatrix<T> switchColumns(int columnIndex1, int columnIndex2) {
        int rowCount = getRowCount();
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            T temp = get(rowIndex, columnIndex1);
            set(rowIndex, columnIndex1, get(rowIndex, columnIndex2));
            set(rowIndex, columnIndex2, temp);
        }
        return this;
    }
    //endregion

    //region Заповнювачі
    //region Заповнювачі всієї матриці

    /**
     * Заповнює всю матрицю елементами
     *
     * @param function функція, яка залежить від положення елементу
     * @return цей об’єкт
     * @see com.darkempire.math.struct.function.interfaces.FMatrixIndexToLinear
     */
    public LinearMatrix<T> fill(FMatrixIndexToLinear<T> function) {
        int columnCount = getColumnCount();
        int rowCount = getRowCount();
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                set(rowIndex, columnIndex, function.calc(rowIndex, columnIndex));
            }
        }
        return this;
    }

    /**
     * Заповнює всю матрицю елементами
     *
     * @param function функція, яка залежить від положення елементу і самої матриці
     * @return цей об’єкт
     * @see com.darkempire.math.struct.function.interfaces.FLinearMatrixAndIndexToLinear
     */
    public LinearMatrix<T> fill(FLinearMatrixAndIndexToLinear<T> function) {
        int columnCount = getColumnCount();
        int rowCount = getRowCount();
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                set(rowIndex, columnIndex, function.calc(this, rowIndex, columnIndex));
            }
        }
        return this;
    }

    /**
     * Заповнює всю матрицю одним числом
     *
     * @param value число, яким заповнити марицю
     * @return цей об’єкт
     */
    public LinearMatrix<T> fill(T value) {
        int columnCount = getColumnCount();
        int rowCount = getRowCount();
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                set(rowIndex, columnIndex, value.deepcopy());
            }
        }
        return this;
    }
    //endregion

    //region Заповнювачі прямокутників
    public LinearMatrix<T> fillRectangle(int startRow, int startColumn, int endRow, int endColumn, T value) {
        endColumn++;
        endRow++;
        for (int columnIndex = startColumn; columnIndex < endColumn; columnIndex++) {
            for (int rowIndex = startRow; rowIndex < endRow; rowIndex++) {
                set(rowIndex, columnIndex, value.deepcopy());
            }
        }
        return this;
    }

    public LinearMatrix<T> fillRectangle(int startRow, int startColumn, int endRow, int endColumn, FMatrixIndexToLinear<T> function) {
        endColumn++;
        endRow++;
        for (int columnIndex = startColumn; columnIndex < endColumn; columnIndex++) {
            for (int rowIndex = startRow; rowIndex < endRow; rowIndex++) {
                set(rowIndex, columnIndex, function.calc(rowIndex, columnIndex));
            }
        }
        return this;
    }

    public LinearMatrix<T> fillRectangle(int startRow, int startColumn, int endRow, int endColumn, FLinearMatrixAndIndexToLinear<T> function) {
        endColumn++;
        endRow++;
        for (int columnIndex = startColumn; columnIndex < endColumn; columnIndex++) {
            for (int rowIndex = startRow; rowIndex < endRow; rowIndex++) {
                set(rowIndex, columnIndex, function.calc(this, rowIndex, columnIndex));
            }
        }
        return this;
    }
    //endregion

    //region Заповнювачі рядків
    public LinearMatrix<T> fillRow(int rowIndex, T... values) {
        int columnCount = getColumnCount();
        if (values.length != columnCount)
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MISMATCH);
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            set(rowIndex, columnIndex, values[columnIndex]);
        }
        return this;
    }

    public LinearMatrix<T> fillRow(int rowIndex, T value) {
        int columnCount = getColumnCount();
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            set(rowIndex, columnIndex, value.deepcopy());
        }
        return this;
    }

    public LinearMatrix<T> fillRow(int rowIndex, FMatrixIndexToLinear<T> function) {
        int columnCount = getColumnCount();
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            set(rowIndex, columnIndex, function.calc(rowIndex, columnIndex));
        }
        return this;
    }

    public LinearMatrix<T> fillRow(int rowIndex, FLinearMatrixAndIndexToLinear<T> function) {
        int columnCount = getColumnCount();
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            set(rowIndex, columnIndex, function.calc(this, rowIndex, columnIndex));
        }
        return this;
    }

    public LinearMatrix<T> fillFirstRow(FMatrixIndexToLinear<T> function) {
        return fillRow(0, function);
    }

    public LinearMatrix<T> fillFirstRow(FLinearMatrixAndIndexToLinear<T> function) {
        return fillRow(0, function);
    }

    public LinearMatrix<T> fillFirstRow(T value) {
        return fillRow(0, value);
    }

    public LinearMatrix<T> fillLastRow(FLinearMatrixAndIndexToLinear<T> function) {
        return fillRow(getRowCount() - 1, function);
    }

    public LinearMatrix<T> fillLastRow(FMatrixIndexToLinear<T> function) {
        return fillRow(getRowCount() - 1, function);
    }

    public LinearMatrix<T> fillLastRow(T value) {
        return fillRow(getRowCount() - 1, value);
    }
    //endregion

    //region Заповнювачі стовпчиків
    public LinearMatrix<T> fillColumn(int columnIndex, T... values) {
        int rowCount = getRowCount();
        if (values.length != rowCount)
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MISMATCH);
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            set(rowIndex, columnIndex, values[rowIndex]);
        }
        return this;
    }

    public LinearMatrix<T> fillColumn(int columnIndex, T value) {
        int rowCount = getRowCount();
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            set(rowIndex, columnIndex, value.deepcopy());
        }
        return this;
    }

    public LinearMatrix<T> fillColumn(int columnIndex, FMatrixIndexToLinear<T> function) {
        int rowCount = getRowCount();
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            set(rowIndex, columnIndex, function.calc(rowIndex, columnIndex));
        }
        return this;
    }

    public LinearMatrix<T> fillColumn(int columnIndex, FLinearMatrixAndIndexToLinear<T> function) {
        int rowCount = getRowCount();
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            set(rowIndex, columnIndex, function.calc(this, rowIndex, columnIndex));
        }
        return this;
    }

    public LinearMatrix<T> fillFirstColumn(FLinearMatrixAndIndexToLinear<T> function) {
        return fillColumn(0, function);
    }

    public LinearMatrix<T> fillFirstColumn(FMatrixIndexToLinear<T> function) {
        return fillColumn(0, function);
    }

    public LinearMatrix<T> fillFirstColumn(T value) {
        return fillColumn(0, value);
    }

    public LinearMatrix<T> fillLastColumn(FLinearMatrixAndIndexToLinear<T> function) {
        return fillColumn(getColumnCount() - 1, function);
    }

    public LinearMatrix<T> fillLastColumn(FMatrixIndexToLinear<T> function) {
        return fillColumn(getColumnCount() - 1, function);
    }

    public LinearMatrix<T> fillLastColumn(T value) {
        return fillColumn(getColumnCount() - 1, value);
    }
    //endregion
    //endregion

    //region Операції над матрицями
    //region Операції над всією матрицею
    public LinearMatrix<T> operate(FLinearMatrixElementAndMatrixIndexToLinear<T> function) {
        int rowCount = getRowCount();
        int columnCount = getColumnCount();
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                set(rowIndex, columnIndex, function.operate(get(rowIndex, columnIndex), rowIndex, columnIndex));
            }
        }
        return this;
    }

    public LinearMatrix<T> operate(FLinearToLinear<T> function) {
        int rowCount = getRowCount();
        int columnCount = getColumnCount();
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                set(rowIndex, columnIndex, function.calc(get(rowIndex, columnIndex)));
            }
        }
        return this;
    }
    //endregion

    //region Операція над прямокутною ділянкою
    public LinearMatrix<T> operateRectange(int startRow, int startColumn, int endRow, int endColumn, FLinearMatrixElementAndMatrixIndexToLinear<T> function) {
        endRow++;
        endColumn++;
        for (int rowIndex = startRow; rowIndex < endRow; rowIndex++) {
            for (int columnIndex = startColumn; columnIndex < endColumn; columnIndex++) {
                set(rowIndex, columnIndex, function.operate(get(rowIndex, columnIndex), rowIndex, columnIndex));
            }
        }
        return this;
    }

    public LinearMatrix<T> operateRectangle(int startRow, int startColumn, int endRow, int endColumn, FLinearToLinear<T> function) {
        endRow++;
        endColumn++;
        for (int rowIndex = startRow; rowIndex < endRow; rowIndex++) {
            for (int columnIndex = startColumn; columnIndex < endColumn; columnIndex++) {
                set(rowIndex, columnIndex, function.calc(get(rowIndex, columnIndex)));
            }
        }
        return this;
    }
    //endregion


    //region Операції над стобпчиками
    public LinearMatrix<T> operateColumn(int columnIndex, FLinearMatrixElementAndMatrixIndexToLinear<T> function) {
        int rowCount = getRowCount();
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            set(rowIndex, columnIndex, function.operate(get(rowIndex, columnIndex), rowIndex, columnIndex));
        }
        return this;
    }

    public LinearMatrix<T> operateColumn(int columnIndex, FLinearToLinear<T> function) {
        int rowCount = getRowCount();
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            set(rowIndex, columnIndex, function.calc(get(rowIndex, columnIndex)));
        }
        return this;
    }

    public LinearMatrix<T> operateFirstColumn(FLinearToLinear<T> function) {
        return operateColumn(0, function);
    }

    public LinearMatrix<T> operateFirstColumn(FLinearMatrixElementAndMatrixIndexToLinear<T> function) {
        return operateColumn(0, function);
    }

    public LinearMatrix<T> operateLastColumn(FLinearToLinear<T> function) {
        return operateColumn(getColumnCount() - 1, function);
    }

    public LinearMatrix<T> operateLastColumn(FLinearMatrixElementAndMatrixIndexToLinear<T> function) {
        return operateColumn(getColumnCount() - 1, function);
    }
    //endregion


    //region Операції над рядками
    public LinearMatrix<T> operateRow(int rowIndex, FLinearMatrixElementAndMatrixIndexToLinear<T> function) {
        int columnCount = getColumnCount();
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            set(rowIndex, columnIndex, function.operate(get(rowIndex, columnIndex), rowIndex, columnIndex));
        }
        return this;
    }

    public LinearMatrix<T> operateRow(int rowIndex, FLinearToLinear<T> function) {
        int columnCount = getColumnCount();
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            set(rowIndex, columnIndex, function.calc(get(rowIndex, columnIndex)));
        }
        return this;
    }

    public LinearMatrix<T> operateFirstRow(FLinearToLinear<T> function) {
        return operateRow(0, function);
    }

    public LinearMatrix<T> operateFirstRow(FLinearMatrixElementAndMatrixIndexToLinear<T> function) {
        return operateRow(0, function);
    }

    public LinearMatrix<T> operateLastRow(FLinearToLinear<T> function) {
        return operateRow(getRowCount() - 1, function);
    }

    public LinearMatrix<T> operateLastRow(FLinearMatrixElementAndMatrixIndexToLinear<T> function) {
        return operateRow(getRowCount() - 1, function);
    }
    //endregion
    //endregion

    //region Дріблення матриць на вектори
    /*
    public NumberVector<T> row(int rowIndex) {
        return LinearMatrixVector.row(rowIndex, this);
    }

    public NumberVector<T> column(int columnIndex) {
        return LinearMatrixVector.column(columnIndex, this);
    }

    public Stream<NumberVector> rows() {
        int rowCount = getRowCount();
        NumberVector[] temp = new NumberVector[rowCount];
        for (int i = 0; i < rowCount; i++) {
            temp[i] = row(i);
        }
        return Stream.of(temp);
    }

    public Stream<NumberVector> columns() {
        int columnCount = getColumnCount();
        NumberVector[] temp = new NumberVector[columnCount];
        for (int i = 0; i < columnCount; i++) {
            temp[i] = column(i);
        }
        return Stream.of(temp);
    }TODO:реалізувати дроблення
    */
    //endregion
}
