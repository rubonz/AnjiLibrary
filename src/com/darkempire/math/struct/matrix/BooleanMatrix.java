package com.darkempire.math.struct.matrix;

import com.darkempire.math.exception.MatrixSizeException;
import com.darkempire.math.struct.LinearCalcable;
import com.darkempire.math.struct.function.FBooleanBoolean;
import com.darkempire.math.struct.function.interfaces.FBooleanMatrixAndIndexToBoolean;
import com.darkempire.math.struct.function.interfaces.FBooleanMatrixElementAndMatrixIndexToBoolean;
import com.darkempire.math.struct.function.interfaces.FMatrixIndexToBoolean;
import com.darkempire.math.struct.vector.BooleanMatrixVector;
import com.darkempire.math.struct.vector.BooleanVector;

import java.util.stream.Stream;

/**
 * Create in 9:13
 * Created by siredvin on 20.12.13.
 */
public abstract class BooleanMatrix implements IMatrix<BooleanMatrix, Boolean>, IBooleanMatrixProvider, LinearCalcable<BooleanMatrix> {
    //region Геттери
    @Override
    public abstract boolean get(int rowIndex, int columnIndex);

    /**
     * Отримання елементу як об’єкту з матриці
     *
     * @param columnIndex індекс колонки
     * @param rowIndex    індекс рядку
     * @return значення поля
     * @see IMatrix
     */
    @Override
    public Boolean getEl(int rowIndex, int columnIndex) {
        return get(rowIndex, columnIndex);
    }

    @Override
    public Boolean getEl(MatrixIndex index) {
        return get(index.getRowIndex(), index.getColumnIndex());
    }

    public boolean get(MatrixIndex index) {
        return get(index.getRowIndex(), index.getColumnIndex());
    }

    protected abstract boolean get(int index);

    //endregion

    //region Сеттери
    public abstract void set(int rowIndex, int columnIndex, boolean value);

    protected abstract void set(int index, boolean value);

    /**
     * Запис елементу, заданого як об’єкт, у матрицю
     *
     * @param columnIndex індекс колонки
     * @param rowIndex    індекс рядку
     * @param value       значення
     * @see IMatrix
     */
    @Override
    public void setEl(int rowIndex, int columnIndex, Boolean value) {
        set(rowIndex, columnIndex, value);
    }

    //endregion

    //region Зрізи
    public BooleanMatrixSlice slice(int startRow, int startColumn, int endRow, int endColumn) {
        return BooleanMatrixSlice.createSlice(this, startRow, startColumn, endRow, endColumn);
    }

    public BooleanMatrixSlice slice(int[] rowIndexs, int[] columnIndexs) {
        return BooleanMatrixSlice.createSlice(this, rowIndexs, columnIndexs);
    }

    public BooleanMatrixSlice sliceColumns(int[] columnsIndex) {
        int rowCount = getRowCount();
        int[] rowIndexs = new int[rowCount];
        for (int i = 0; i < rowCount; i++) {
            rowIndexs[i] = i;
        }
        return BooleanMatrixSlice.createSlice(this, rowIndexs, columnsIndex);
    }

    public BooleanMatrixSlice sliceRows(int[] rowIndex) {
        int columnCount = getColumnCount();
        int[] columnIndexs = new int[columnCount];
        for (int i = 0; i < columnCount; i++) {
            columnIndexs[i] = i;
        }
        return BooleanMatrixSlice.createSlice(this, rowIndex, columnIndexs);
    }

    public BooleanMatrixSlice slicePeriodic(int rowPeriod, int columnPeriod) {
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
        return BooleanMatrixSlice.createSlice(this, rowIndexs, columnIndexs);
    }
    //endregion

    //region Операції з присвоєннями
    @Override
    public BooleanMatrix itranspose() {
        if (getRowCount() != getColumnCount()) {
            throw new MatrixSizeException(MatrixSizeException.MATRIX_IS_NOT_SQUARE);
        }
        int size = getRowCount();
        for (int rowIndex = 0; rowIndex < size; rowIndex++) {
            for (int columnIndex = rowIndex + 1; columnIndex < size; columnIndex++) {
                boolean temp = get(rowIndex, columnIndex);
                set(rowIndex, columnIndex, get(columnIndex, rowIndex));
                set(columnIndex, rowIndex, temp);
            }
        }
        return this;
    }
    //endregion

    //region Перевірки матриці на властивості
    @Override
    public boolean isSymmetric() {
        boolean isSymmetric = false;
        int columnCount = getColumnCount();
        int rowCount = getRowCount();
        if (columnCount == rowCount) {
            isSymmetric = true;
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                    if (get(rowIndex, columnIndex) != get(rowIndex, columnIndex)) {
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

    //region Системні методи
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o instanceof BooleanMatrix) {
            BooleanMatrix temp = (BooleanMatrix) o;
            int columnCount = getColumnCount();
            int rowCount = getRowCount();
            if (columnCount == temp.getColumnCount() && rowCount == temp.getRowCount()) {
                for (int i = 0; i < rowCount; i++) {
                    for (int j = 0; j < columnCount; j++) {
                        if (get(i, j) != temp.get(i, j)) {
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
    public abstract BooleanMatrix clone();

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

    //region Операції переставлення місцями

    /**
     * Міняє два рядка місцями
     *
     * @param rowIndex1 індекс першого рядку
     * @param rowIndex2 індекс другого рядку
     * @return цей об’єкт
     */
    @Override
    public BooleanMatrix switchRows(int rowIndex1, int rowIndex2) {
        int columnCount = getColumnCount();
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            boolean temp = get(rowIndex1, columnIndex);
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
    public BooleanMatrix switchColumns(int columnIndex1, int columnIndex2) {
        int rowCount = getRowCount();
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            boolean temp = get(rowIndex, columnIndex1);
            set(rowIndex, columnIndex1, get(rowIndex, columnIndex2));
            set(rowIndex, columnIndex2, temp);
        }
        return this;
    }
    //endregion

    //region Заповнювачі
    //region Вся матриця

    /**
     * Заповнює всю матрицю елементами
     *
     * @param function функція, яка залежить від положення елементу
     * @return цей об’єкт
     * @see com.darkempire.math.struct.function.interfaces.FMatrixIndexToBoolean
     */
    public BooleanMatrix fill(FMatrixIndexToBoolean function) {
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
     * @see com.darkempire.math.struct.function.interfaces.FBooleanMatrixAndIndexToBoolean
     */
    public BooleanMatrix fill(FBooleanMatrixAndIndexToBoolean function) {
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
    public BooleanMatrix fill(boolean value) {
        int columnCount = getColumnCount();
        int rowCount = getRowCount();
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                set(rowIndex, columnIndex, value);
            }
        }
        return this;
    }
    //endregion


    //region Прямокутник
    public BooleanMatrix fillRectangle(int startRow, int startColumn, int endRow, int endColumn, boolean value) {
        endColumn++;
        endRow++;
        for (int columnIndex = startColumn; columnIndex < endColumn; columnIndex++) {
            for (int rowIndex = startRow; rowIndex < endRow; rowIndex++) {
                set(rowIndex, columnIndex, value);
            }
        }
        return this;
    }

    public BooleanMatrix fillRectangle(int startRow, int startColumn, int endRow, int endColumn, FMatrixIndexToBoolean function) {
        endColumn++;
        endRow++;
        for (int columnIndex = startColumn; columnIndex < endColumn; columnIndex++) {
            for (int rowIndex = startRow; rowIndex < endRow; rowIndex++) {
                set(rowIndex, columnIndex, function.calc(rowIndex, columnIndex));
            }
        }
        return this;
    }

    public BooleanMatrix fillRectangle(int startRow, int startColumn, int endRow, int endColumn, FBooleanMatrixAndIndexToBoolean function) {
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


    //region Рядок
    public BooleanMatrix fillRow(int rowIndex, boolean... values) {
        int columnCount = getColumnCount();
        if (values.length != columnCount)
            throw new MatrixSizeException(MatrixSizeException.MATRIX_SIZE_MISMATCH);
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            set(rowIndex, columnIndex, values[columnIndex]);
        }
        return this;
    }

    public BooleanMatrix fillRow(int rowIndex, boolean value) {
        int columnCount = getColumnCount();
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            set(rowIndex, columnIndex, value);
        }
        return this;
    }

    public BooleanMatrix fillRow(int rowIndex, FMatrixIndexToBoolean function) {
        int columnCount = getColumnCount();
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            set(rowIndex, columnIndex, function.calc(rowIndex, columnIndex));
        }
        return this;
    }

    public BooleanMatrix fillRow(int rowIndex, FBooleanMatrixAndIndexToBoolean function) {
        int columnCount = getColumnCount();
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            set(rowIndex, columnIndex, function.calc(this, rowIndex, columnIndex));
        }
        return this;
    }

    public BooleanMatrix fillFirstRow(FMatrixIndexToBoolean function) {
        return fillRow(0, function);
    }

    public BooleanMatrix fillFirstRow(FBooleanMatrixAndIndexToBoolean function) {
        return fillRow(0, function);
    }

    public BooleanMatrix fillFirstRow(boolean value) {
        return fillRow(0, value);
    }

    public BooleanMatrix fillLastRow(FBooleanMatrixAndIndexToBoolean function) {
        return fillRow(getRowCount() - 1, function);
    }

    public BooleanMatrix fillLastRow(FMatrixIndexToBoolean function) {
        return fillRow(getRowCount() - 1, function);
    }

    public BooleanMatrix fillLastRow(boolean value) {
        return fillRow(getRowCount() - 1, value);
    }
    //endregion


    //region Стовбчик
    public BooleanMatrix fillColumn(int columnIndex, boolean... values) {
        int rowCount = getRowCount();
        if (values.length != rowCount)
            throw new MatrixSizeException(MatrixSizeException.MATRIX_SIZE_MISMATCH);
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            set(rowIndex, columnIndex, values[rowIndex]);
        }
        return this;
    }

    public BooleanMatrix fillColumn(int columnIndex, boolean value) {
        int rowCount = getRowCount();
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            set(rowIndex, columnIndex, value);
        }
        return this;
    }

    public BooleanMatrix fillColumn(int columnIndex, FMatrixIndexToBoolean function) {
        int rowCount = getRowCount();
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            set(rowIndex, columnIndex, function.calc(rowIndex, columnIndex));
        }
        return this;
    }

    public BooleanMatrix fillColumn(int columnIndex, FBooleanMatrixAndIndexToBoolean function) {
        int rowCount = getRowCount();
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            set(rowIndex, columnIndex, function.calc(this, rowIndex, columnIndex));
        }
        return this;
    }

    public BooleanMatrix fillFirstColumn(FBooleanMatrixAndIndexToBoolean function) {
        return fillColumn(0, function);
    }

    public BooleanMatrix fillFirstColumn(FMatrixIndexToBoolean function) {
        return fillColumn(0, function);
    }

    public BooleanMatrix fillFirstColumn(boolean value) {
        return fillColumn(0, value);
    }

    public BooleanMatrix fillLastColumn(FBooleanMatrixAndIndexToBoolean function) {
        return fillColumn(getColumnCount() - 1, function);
    }

    public BooleanMatrix fillLastColumn(FMatrixIndexToBoolean function) {
        return fillColumn(getColumnCount() - 1, function);
    }

    public BooleanMatrix fillLastColumn(boolean value) {
        return fillColumn(getColumnCount() - 1, value);
    }
    //endregion
    //endregion

    //region Операції

    //region Над всіма елементами матриці
    public BooleanMatrix operate(FBooleanMatrixElementAndMatrixIndexToBoolean function) {
        int rowCount = getRowCount();
        int columnCount = getColumnCount();
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                set(rowIndex, columnIndex, function.operate(get(rowIndex, columnIndex), rowIndex, columnIndex));
            }
        }
        return this;
    }

    public BooleanMatrix operate(FBooleanBoolean function) {
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


    //region Над прямокутною ділянкою
    public BooleanMatrix operateRectange(int startRow, int startColumn, int endRow, int endColumn, FBooleanMatrixElementAndMatrixIndexToBoolean function) {
        endRow++;
        endColumn++;
        for (int rowIndex = startRow; rowIndex < endRow; rowIndex++) {
            for (int columnIndex = startColumn; columnIndex < endColumn; columnIndex++) {
                set(rowIndex, columnIndex, function.operate(get(rowIndex, columnIndex), rowIndex, columnIndex));
            }
        }
        return this;
    }

    public BooleanMatrix operateRectangle(int startRow, int startColumn, int endRow, int endColumn, FBooleanBoolean function) {
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


    //region Над стовбчиком
    public BooleanMatrix operateColumn(int columnIndex, FBooleanMatrixElementAndMatrixIndexToBoolean function) {
        int rowCount = getRowCount();
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            set(rowIndex, columnIndex, function.operate(get(rowIndex, columnIndex), rowIndex, columnIndex));
        }
        return this;
    }

    public BooleanMatrix operateColumn(int columnIndex, FBooleanBoolean function) {
        int rowCount = getRowCount();
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            set(rowIndex, columnIndex, function.calc(get(rowIndex, columnIndex)));
        }
        return this;
    }

    public BooleanMatrix operateFirstColumn(FBooleanBoolean function) {
        return operateColumn(0, function);
    }

    public BooleanMatrix operateFirstColumn(FBooleanMatrixElementAndMatrixIndexToBoolean function) {
        return operateColumn(0, function);
    }

    public BooleanMatrix operateLastColumn(FBooleanBoolean function) {
        return operateColumn(getColumnCount() - 1, function);
    }

    public BooleanMatrix operateLastColumn(FBooleanMatrixElementAndMatrixIndexToBoolean function) {
        return operateColumn(getColumnCount() - 1, function);
    }
    //endregion


    //region Над рядком
    public BooleanMatrix operateRow(int rowIndex, FBooleanMatrixElementAndMatrixIndexToBoolean function) {
        int columnCount = getColumnCount();
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            set(rowIndex, columnIndex, function.operate(get(rowIndex, columnIndex), rowIndex, columnIndex));
        }
        return this;
    }

    public BooleanMatrix operateRow(int rowIndex, FBooleanBoolean function) {
        int columnCount = getColumnCount();
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            set(rowIndex, columnIndex, function.calc(get(rowIndex, columnIndex)));
        }
        return this;
    }

    public BooleanMatrix operateFirstRow(FBooleanBoolean function) {
        return operateRow(0, function);
    }

    public BooleanMatrix operateFirstRow(FBooleanMatrixElementAndMatrixIndexToBoolean function) {
        return operateRow(0, function);
    }

    public BooleanMatrix operateLastRow(FBooleanBoolean function) {
        return operateRow(getRowCount() - 1, function);
    }

    public BooleanMatrix operateLastRow(FBooleanMatrixElementAndMatrixIndexToBoolean function) {
        return operateRow(getRowCount() - 1, function);
    }
    //endregion
    //endregion

    //region Методи перевірки матриці
    public boolean isRow(int rowIndex) {
        boolean result = true;
        int columnCount = getColumnCount();
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            result = result && get(rowIndex, columnIndex);
        }
        return result;
    }

    public boolean isColumn(int columnIndex) {
        boolean result = true;
        int rowCount = getRowCount();
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            result = result && get(rowIndex, columnIndex);
        }
        return result;
    }
    //endregion

    //region Дрібленння матриці на вектори
    public Stream<BooleanVector> rows() {
        int rowCount = getRowCount();
        BooleanVector[] temp = new BooleanVector[rowCount];
        for (int i = 0; i < rowCount; i++) {
            temp[i] = row(i);
        }
        return Stream.of(temp);
    }

    public Stream<BooleanVector> columns() {
        int columnCount = getColumnCount();
        BooleanVector[] temp = new BooleanVector[columnCount];
        for (int i = 0; i < columnCount; i++) {
            temp[i] = column(i);
        }
        return Stream.of(temp);
    }

    public BooleanVector row(int rowIndex) {
        return BooleanMatrixVector.row(rowIndex, this);
    }

    public BooleanVector column(int columnIndex) {
        return BooleanMatrixVector.column(columnIndex, this);
    }
    //endregion

    //region Арифметичні операції
    public abstract BooleanMatrix transpose();

    public abstract BooleanMatrix multy(BooleanMatrix booleanMatrix);

    public abstract BooleanMatrix prod(BooleanMatrix doubleMatrix);
    //endregion
}
