package com.darkempire.math.struct.matrix;

import com.darkempire.anji.document.tex.ITeXObject;
import com.darkempire.anji.document.tex.TeXEventWriter;
import com.darkempire.math.exception.MatrixSizeException;
import com.darkempire.math.struct.LinearCalcable;
import com.darkempire.math.struct.function.FDoubleDouble;
import com.darkempire.math.struct.function.interfaces.FDoubleMatrixAndIndexToDouble;
import com.darkempire.math.struct.function.interfaces.FDoubleMatrixElementAndMatrixIndexToDouble;
import com.darkempire.math.struct.function.interfaces.FMatrixIndexToDouble;
import com.darkempire.math.struct.vector.DoubleMatrixVector;
import com.darkempire.math.struct.vector.DoubleVector;
import com.darkempire.math.struct.vector.IDoubleVectorProvider;

import java.text.NumberFormat;
import java.util.StringJoiner;
import java.util.stream.Stream;

/**
 * Create in 9:13
 * Created by siredvin on 20.12.13.
 */
public abstract class DoubleMatrix implements IMatrix<DoubleMatrix, Double>, IDoubleMatrixProvider, LinearCalcable<DoubleMatrix>, ITeXObject {
    //region Геттери
    @Override
    public abstract double get(int rowIndex, int columnIndex);

    /**
     * Отримання елементу як об’єкту з матриці
     *
     * @param columnIndex індекс колонки
     * @param rowIndex    індекс рядку
     * @return значення поля
     * @see com.darkempire.math.struct.matrix.IMatrix
     */
    @Override
    public Double getEl(int rowIndex, int columnIndex) {
        return get(rowIndex, columnIndex);
    }

    @Override
    public Double getEl(MatrixIndex index) {
        return get(index.getRowIndex(), index.getColumnIndex());
    }

    public double get(MatrixIndex index) {
        return get(index.getRowIndex(), index.getColumnIndex());
    }

    protected abstract double get(int index);
    //endregion

    //region Сеттери
    public abstract void set(int rowIndex, int columnIndex, double value);

    /**
     * Запис елементу, заданого як об’єкт, у матрицю
     *
     * @param columnIndex індекс колонки
     * @param rowIndex    індекс рядку
     * @param value       значення
     * @see com.darkempire.math.struct.matrix.IMatrix
     */
    @Override
    public void setEl(int rowIndex, int columnIndex, Double value) {
        set(rowIndex, columnIndex, value);
    }

    protected abstract void set(int index, double value);
    //endregion

    //region Зрізи
    public DoubleMatrixSlice slice(int startRow, int startColumn, int endRow, int endColumn) {
        return DoubleMatrixSlice.createSlice(this, startRow, startColumn, endRow, endColumn);
    }

    public DoubleMatrixSlice slice(int[] rowIndexs, int[] columnIndexs) {
        return DoubleMatrixSlice.createSlice(this, rowIndexs, columnIndexs);
    }

    public DoubleMatrixSlice sliceColumns(int[] columnsIndex) {
        int rowCount = getRowCount();
        int[] rowIndexs = new int[rowCount];
        for (int i = 0; i < rowCount; i++) {
            rowIndexs[i] = i;
        }
        return DoubleMatrixSlice.createSlice(this, rowIndexs, columnsIndex);
    }

    public DoubleMatrixSlice sliceColumns(int startIndex, int endIndex) {
        int[] indexes = new int[endIndex - startIndex];
        for (int i = startIndex; i < endIndex; i++) {
            indexes[i] = i;
        }
        return sliceColumns(indexes);
    }

    public DoubleMatrixSlice sliceColumnsTo(int endIndex) {
        return sliceColumns(0, endIndex);
    }

    public DoubleMatrix sliceColumnsFrom(int startIndex) {
        return sliceColumns(startIndex, getColumnCount());
    }

    public DoubleMatrixSlice sliceRows(int[] rowIndex) {
        int columnCount = getColumnCount();
        int[] columnIndexs = new int[columnCount];
        for (int i = 0; i < columnCount; i++) {
            columnIndexs[i] = i;
        }
        return DoubleMatrixSlice.createSlice(this, rowIndex, columnIndexs);
    }

    public DoubleMatrixSlice sliceRows(int startIndex, int endIndex) {
        int[] indexes = new int[endIndex - startIndex];
        for (int i = startIndex; i < endIndex; i++) {
            indexes[i] = i;
        }
        return sliceRows(indexes);
    }

    public DoubleMatrixSlice sliceRowsTo(int endIndex) {
        return sliceRows(0, endIndex);
    }

    public DoubleMatrix sliceRowsFrom(int startIndex) {
        return sliceRows(startIndex, getRowCount());
    }

    public DoubleMatrixSlice slicePeriodic(int rowPeriod, int columnPeriod) {
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
        return DoubleMatrixSlice.createSlice(this, rowIndexs, columnIndexs);
    }
    //endregion

    //region Арифметичні операції з присвоєннями
    @Override
    public DoubleMatrix itranspose() {
        if (getRowCount() != getColumnCount()) {
            throw new MatrixSizeException(MatrixSizeException.MATRIX_IS_NOT_SQUARE);
        }
        int size = getRowCount();
        for (int rowIndex = 0; rowIndex < size; rowIndex++) {
            for (int columnIndex = rowIndex + 1; columnIndex < size; columnIndex++) {
                double temp = get(rowIndex, columnIndex);
                set(rowIndex, columnIndex, get(columnIndex, rowIndex));
                set(columnIndex, rowIndex, temp);
            }
        }
        return this;
    }
    //endregion

    //region Перевірка матриць на властивості
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

    //region Операції переставлення місцями

    /**
     * Міняє два рядка місцями
     *
     * @param rowIndex1 індекс першого рядку
     * @param rowIndex2 індекс другого рядку
     * @return цей об’єкт
     */
    @Override
    public DoubleMatrix switchRows(int rowIndex1, int rowIndex2) {
        int columnCount = getColumnCount();
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            double temp = get(rowIndex1, columnIndex);
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
    public DoubleMatrix switchColumns(int columnIndex1, int columnIndex2) {
        int rowCount = getRowCount();
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            double temp = get(rowIndex, columnIndex1);
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
     * @see com.darkempire.math.struct.function.interfaces.FMatrixIndexToDouble
     */
    public DoubleMatrix fill(FMatrixIndexToDouble function) {
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
     * @see com.darkempire.math.struct.function.interfaces.FDoubleMatrixAndIndexToDouble
     */
    public DoubleMatrix fill(FDoubleMatrixAndIndexToDouble function) {
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
    public DoubleMatrix fill(double value) {
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
    public DoubleMatrix fillRectangle(int startRow, int startColumn, int endRow, int endColumn, double value) {
        endColumn++;
        endRow++;
        for (int columnIndex = startColumn; columnIndex < endColumn; columnIndex++) {
            for (int rowIndex = startRow; rowIndex < endRow; rowIndex++) {
                set(rowIndex, columnIndex, value);
            }
        }
        return this;
    }

    public DoubleMatrix fillRectangle(int startRow, int startColumn, int endRow, int endColumn, FMatrixIndexToDouble function) {
        endColumn++;
        endRow++;
        for (int columnIndex = startColumn; columnIndex < endColumn; columnIndex++) {
            for (int rowIndex = startRow; rowIndex < endRow; rowIndex++) {
                set(rowIndex, columnIndex, function.calc(rowIndex, columnIndex));
            }
        }
        return this;
    }

    public DoubleMatrix fillRectangle(int startRow, int startColumn, int endRow, int endColumn, FDoubleMatrixAndIndexToDouble function) {
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
    public DoubleMatrix fillRow(int rowIndex, double... values) {
        int columnCount = getColumnCount();
        if (values.length != columnCount)
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MISMATCH);
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            set(rowIndex, columnIndex, values[columnIndex]);
        }
        return this;
    }

    public DoubleMatrix fillRow(int rowIndex, double value) {
        int columnCount = getColumnCount();
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            set(rowIndex, columnIndex, value);
        }
        return this;
    }

    public DoubleMatrix fillRow(int rowIndex, FMatrixIndexToDouble function) {
        int columnCount = getColumnCount();
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            set(rowIndex, columnIndex, function.calc(rowIndex, columnIndex));
        }
        return this;
    }

    public DoubleMatrix fillRow(int rowIndex, FDoubleMatrixAndIndexToDouble function) {
        int columnCount = getColumnCount();
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            set(rowIndex, columnIndex, function.calc(this, rowIndex, columnIndex));
        }
        return this;
    }

    public DoubleMatrix fillRow(int rowIndex, IDoubleVectorProvider provider) {
        int columnCount = getColumnCount();
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            set(rowIndex, columnIndex, provider.get(columnIndex));
        }
        return this;
    }

    public DoubleMatrix fillFirstRow(FMatrixIndexToDouble function) {
        return fillRow(0, function);
    }

    public DoubleMatrix fillFirstRow(FDoubleMatrixAndIndexToDouble function) {
        return fillRow(0, function);
    }

    public DoubleMatrix fillFirstRow(double value) {
        return fillRow(0, value);
    }

    public DoubleMatrix fillFirstRow(IDoubleVectorProvider provider) {
        return fillRow(0, provider);
    }

    public DoubleMatrix fillLastRow(FDoubleMatrixAndIndexToDouble function) {
        return fillRow(getRowCount() - 1, function);
    }

    public DoubleMatrix fillLastRow(FMatrixIndexToDouble function) {
        return fillRow(getRowCount() - 1, function);
    }

    public DoubleMatrix fillLastRow(double value) {
        return fillRow(getRowCount() - 1, value);
    }

    public DoubleMatrix fillLastRow(IDoubleVectorProvider provider) {
        return fillRow(getRowCount() - 1, provider);
    }
    //endregion


    //region Стовбчик
    public DoubleMatrix fillColumn(int columnIndex, double... values) {
        int rowCount = getRowCount();
        if (values.length != rowCount)
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MISMATCH);
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            set(rowIndex, columnIndex, values[rowIndex]);
        }
        return this;
    }

    public DoubleMatrix fillColumn(int columnIndex, double value) {
        int rowCount = getRowCount();
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            set(rowIndex, columnIndex, value);
        }
        return this;
    }

    public DoubleMatrix fillColumn(int columnIndex, FMatrixIndexToDouble function) {
        int rowCount = getRowCount();
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            set(rowIndex, columnIndex, function.calc(rowIndex, columnIndex));
        }
        return this;
    }

    public DoubleMatrix fillColumn(int columnIndex, FDoubleMatrixAndIndexToDouble function) {
        int rowCount = getRowCount();
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            set(rowIndex, columnIndex, function.calc(this, rowIndex, columnIndex));
        }
        return this;
    }

    public DoubleMatrix fillColumn(int columnIndex, IDoubleVectorProvider provider) {
        int rowCount = getRowCount();
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            set(rowIndex, columnIndex, provider.get(rowIndex));
        }
        return this;
    }

    public DoubleMatrix fillFirstColumn(FDoubleMatrixAndIndexToDouble function) {
        return fillColumn(0, function);
    }

    public DoubleMatrix fillFirstColumn(FMatrixIndexToDouble function) {
        return fillColumn(0, function);
    }

    public DoubleMatrix fillFirstColumn(double value) {
        return fillColumn(0, value);
    }

    public DoubleMatrix fillFirstColumn(IDoubleVectorProvider provider) {
        return fillColumn(0, provider);
    }

    public DoubleMatrix fillLastColumn(FDoubleMatrixAndIndexToDouble function) {
        return fillColumn(getColumnCount() - 1, function);
    }

    public DoubleMatrix fillLastColumn(FMatrixIndexToDouble function) {
        return fillColumn(getColumnCount() - 1, function);
    }

    public DoubleMatrix fillLastColumn(double value) {
        return fillColumn(getColumnCount() - 1, value);
    }

    public DoubleMatrix fillLastColumn(IDoubleVectorProvider provider) {
        return fillColumn(getColumnCount() - 1, provider);
    }
    //endregion
    //endregion

    //region Операції

    //region Над всіма елементами матриці
    public DoubleMatrix operate(FDoubleMatrixElementAndMatrixIndexToDouble function) {
        int rowCount = getRowCount();
        int columnCount = getColumnCount();
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                set(rowIndex, columnIndex, function.operate(get(rowIndex, columnIndex), rowIndex, columnIndex));
            }
        }
        return this;
    }

    public DoubleMatrix operate(FDoubleDouble function) {
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
    public DoubleMatrix operateRectange(int startRow, int startColumn, int endRow, int endColumn, FDoubleMatrixElementAndMatrixIndexToDouble function) {
        endRow++;
        endColumn++;
        for (int rowIndex = startRow; rowIndex < endRow; rowIndex++) {
            for (int columnIndex = startColumn; columnIndex < endColumn; columnIndex++) {
                set(rowIndex, columnIndex, function.operate(get(rowIndex, columnIndex), rowIndex, columnIndex));
            }
        }
        return this;
    }

    public DoubleMatrix operateRectangle(int startRow, int startColumn, int endRow, int endColumn, FDoubleDouble function) {
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
    public DoubleMatrix operateColumn(int columnIndex, FDoubleMatrixElementAndMatrixIndexToDouble function) {
        int rowCount = getRowCount();
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            set(rowIndex, columnIndex, function.operate(get(rowIndex, columnIndex), rowIndex, columnIndex));
        }
        return this;
    }

    public DoubleMatrix operateColumn(int columnIndex, FDoubleDouble function) {
        int rowCount = getRowCount();
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            set(rowIndex, columnIndex, function.calc(get(rowIndex, columnIndex)));
        }
        return this;
    }

    public DoubleMatrix operateFirstColumn(FDoubleDouble function) {
        return operateColumn(0, function);
    }

    public DoubleMatrix operateFirstColumn(FDoubleMatrixElementAndMatrixIndexToDouble function) {
        return operateColumn(0, function);
    }

    public DoubleMatrix operateLastColumn(FDoubleDouble function) {
        return operateColumn(getColumnCount() - 1, function);
    }

    public DoubleMatrix operateLastColumn(FDoubleMatrixElementAndMatrixIndexToDouble function) {
        return operateColumn(getColumnCount() - 1, function);
    }
    //endregion


    //region Над рядком
    public DoubleMatrix operateRow(int rowIndex, FDoubleMatrixElementAndMatrixIndexToDouble function) {
        int columnCount = getColumnCount();
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            set(rowIndex, columnIndex, function.operate(get(rowIndex, columnIndex), rowIndex, columnIndex));
        }
        return this;
    }

    public DoubleMatrix operateRow(int rowIndex, FDoubleDouble function) {
        int columnCount = getColumnCount();
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            set(rowIndex, columnIndex, function.calc(get(rowIndex, columnIndex)));
        }
        return this;
    }

    public DoubleMatrix operateFirstRow(FDoubleDouble function) {
        return operateRow(0, function);
    }

    public DoubleMatrix operateFirstRow(FDoubleMatrixElementAndMatrixIndexToDouble function) {
        return operateRow(0, function);
    }

    public DoubleMatrix operateLastRow(FDoubleDouble function) {
        return operateRow(getRowCount() - 1, function);
    }

    public DoubleMatrix operateLastRow(FDoubleMatrixElementAndMatrixIndexToDouble function) {
        return operateRow(getRowCount() - 1, function);
    }
    //endregion
    //endregion

    //region Дріблення матриць на вектори
    public DoubleVector row(int rowIndex) {
        return DoubleMatrixVector.row(rowIndex, this);
    }

    public DoubleVector column(int columnIndex) {
        return DoubleMatrixVector.column(columnIndex, this);
    }

    public DoubleVector diagonal() {
        return DoubleMatrixVector.diagonal(this);
    }

    public Stream<DoubleVector> rows() {
        int rowCount = getRowCount();
        DoubleVector[] temp = new DoubleVector[rowCount];
        for (int i = 0; i < rowCount; i++) {
            temp[i] = row(i);
        }
        return Stream.of(temp);
    }

    public Stream<DoubleVector> columns() {
        int columnCount = getColumnCount();
        DoubleVector[] temp = new DoubleVector[columnCount];
        for (int i = 0; i < columnCount; i++) {
            temp[i] = column(i);
        }
        return Stream.of(temp);
    }
    //endregion

    //region Системні методи
    public abstract DoubleMatrix clone();

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o instanceof DoubleMatrix) {
            DoubleMatrix temp = (DoubleMatrix) o;
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

    /**
     * Виводить до табличного виду без табуляції
     *
     * @return текстове зображення матриці
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int rowCount = getRowCount();
        int columnCount = getColumnCount();
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

    //region Арифметичні операції
    public abstract DoubleMatrix prod(double lambda);

    public abstract DoubleMatrix iprod(double lambda);

    public abstract DoubleMatrix transpose();

    public abstract DoubleMatrix prod(DoubleMatrix doubleMatrix);

    public abstract DoubleMatrix multy(DoubleMatrix doubleMatrix);

    public abstract DoubleVector multy(DoubleVector doubleVector);
    //endregion

    //region Функції виведення

    @Override
    public void write(TeXEventWriter out) {
        int rowCount = getRowCount();
        int columnCount = getColumnCount();
        NumberFormat format = out.getNumberFormat();
        StringJoiner mainJoiner = new StringJoiner("\\\n");
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            StringJoiner joiner = new StringJoiner(" & ");
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                joiner.add(format.format(get(rowIndex, columnIndex)));
            }
            mainJoiner.add(joiner.toString());
        }
        out.openEnvironment("pmatrix");
        out.text(mainJoiner.toString());
        out.closeEnvironment();
    }

    //endregion
}
