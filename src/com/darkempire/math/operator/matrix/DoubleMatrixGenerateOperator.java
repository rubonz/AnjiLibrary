package com.darkempire.math.operator.matrix;

import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.math.struct.*;
import com.darkempire.math.struct.matrix.DoubleMatrix;
import com.darkempire.math.struct.matrix.DoubleFixedMatrix;
import com.darkempire.math.struct.matrix.DoubleResizeMatrix;
import com.darkempire.math.struct.matrix.NumberMatrix;
import com.darkempire.math.struct.vector.IDoubleVectorProvider;

import java.util.List;

/**
 * Created by siredvin on 08.09.14.
 *
 * @author siredvin
 */
@AnjiUtil
public final class DoubleMatrixGenerateOperator {
    private DoubleMatrixGenerateOperator() {

    }
    //region Діагональні матриці

    /**
     * Створює діагональну матрицю такого ж типу, якого була матриця, що сюди подали
     *
     * @param size розмірність матриці
     * @param type тип матриці
     * @return діагональна матриця заданого типу
     */
    public static DoubleMatrix generateDiagonalMatrix(int size, DoubleMatrix type) {
        DoubleMatrix result = null;
        if (type instanceof DoubleFixedMatrix) {
            result = generateFixedDiagonalMatrix(size);
        }
        if (type instanceof DoubleResizeMatrix) {
            result = generateResizeDiagonalMatrix(size);
        }
        if (result == null) {
            result = generateFixedDiagonalMatrix(size);
        }
        return result;
    }

    /**
     * Створює діагональну матрицю фіксованого розміру
     *
     * @param size розмірність матриці
     * @return діагональна фіксована матриця
     */
    public static DoubleFixedMatrix generateFixedDiagonalMatrix(int size) {
        DoubleFixedMatrix result = DoubleFixedMatrix.createInstance(size, size);
        for (int k = 0; k < size; k++) {
            result.set(k, k, 1d);
        }
        return result;
    }

    /**
     * Створює діагональну матрицю змінного розміру
     *
     * @param size розмірність матриці
     * @return діагональна матриця
     */
    public static DoubleResizeMatrix generateResizeDiagonalMatrix(int size) {
        DoubleResizeMatrix result = DoubleResizeMatrix.createInstance(size, size);
        for (int k = 0; k < size; k++) {
            result.set(k, k, 1d);
        }
        return result;
    }
    //endregion

    //region Матриці з векторів

    //region Вектори як рядки
    public static DoubleFixedMatrix fixedFromRows(IDoubleVectorProvider... rows) {
        DoubleFixedMatrix matrix = DoubleFixedMatrix.createInstance(rows.length, rows[0].getSize());
        for (int rowIndex = 0; rowIndex < rows.length; rowIndex++) {
            matrix.fillRow(rowIndex, rows[rowIndex]);
        }
        return matrix;
    }

    public static DoubleFixedMatrix fixedFromRows(List<IDoubleVectorProvider> rows) {
        int size = rows.size();
        DoubleFixedMatrix matrix = DoubleFixedMatrix.createInstance(size, rows.get(0).getSize());
        for (int rowIndex = 0; rowIndex < size; rowIndex++) {
            matrix.fillRow(rowIndex, rows.get(rowIndex));
        }
        return matrix;
    }

    public static DoubleResizeMatrix resizeFromRows(IDoubleVectorProvider... rows) {
        DoubleResizeMatrix matrix = DoubleResizeMatrix.createInstance(rows.length, rows[0].getSize());
        for (int rowIndex = 0; rowIndex < rows.length; rowIndex++) {
            matrix.fillRow(rowIndex, rows[rowIndex]);
        }
        return matrix;
    }

    public static DoubleResizeMatrix resizeFromRows(List<IDoubleVectorProvider> rows) {
        int size = rows.size();
        DoubleResizeMatrix matrix = DoubleResizeMatrix.createInstance(size, rows.get(0).getSize());
        for (int rowIndex = 0; rowIndex < size; rowIndex++) {
            matrix.fillRow(rowIndex, rows.get(rowIndex));
        }
        return matrix;
    }
    //endregion

    //region Вектори як стовпчики
    public static DoubleFixedMatrix fixedFromColumns(IDoubleVectorProvider... columns) {
        DoubleFixedMatrix matrix = DoubleFixedMatrix.createInstance(columns.length, columns[0].getSize());
        for (int columnIndex = 0; columnIndex < columns.length; columnIndex++) {
            matrix.fillColumn(columnIndex, columns[columnIndex]);
        }
        return matrix;
    }

    public static DoubleFixedMatrix fixedFromColumns(List<IDoubleVectorProvider> columns) {
        int size = columns.size();
        DoubleFixedMatrix matrix = DoubleFixedMatrix.createInstance(size, columns.get(0).getSize());
        for (int columnIndex = 0; columnIndex < size; columnIndex++) {
            matrix.fillColumn(columnIndex, columns.get(columnIndex));
        }
        return matrix;
    }

    public static DoubleResizeMatrix resizeFromColumns(IDoubleVectorProvider... columns) {
        DoubleResizeMatrix matrix = DoubleResizeMatrix.createInstance(columns.length, columns[0].getSize());
        for (int columnIndex = 0; columnIndex < columns.length; columnIndex++) {
            matrix.fillColumn(columnIndex, columns[columnIndex]);
        }
        return matrix;
    }

    public static DoubleResizeMatrix resizeFromColumns(List<IDoubleVectorProvider> columns) {
        int size = columns.size();
        DoubleResizeMatrix matrix = DoubleResizeMatrix.createInstance(size, columns.get(0).getSize());
        for (int columnIndex = 0; columnIndex < size; columnIndex++) {
            matrix.fillColumn(columnIndex, columns.get(columnIndex));
        }
        return matrix;
    }
    //endregion
    //endregion

    //region Спеціальні матриці
    public static <T extends com.darkempire.math.struct.Number<T>> DoubleFixedMatrix fromNumberToFixed(NumberMatrix<T> matrix) {
        int rowCount = matrix.getRowCount();
        int columnCount = matrix.getColumnCount();
        DoubleFixedMatrix result = DoubleFixedMatrix.createInstance(rowCount, columnCount);
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                result.set(rowIndex, columnIndex, matrix.get(rowIndex, columnIndex).doubleValue());
            }
        }
        return result;
    }

    public static <T extends com.darkempire.math.struct.Number<T>> DoubleResizeMatrix fromNumberToResize(NumberMatrix<T> matrix) {
        int rowCount = matrix.getRowCount();
        int columnCount = matrix.getColumnCount();
        DoubleResizeMatrix result = DoubleResizeMatrix.createInstance(rowCount, columnCount);
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                result.set(rowIndex, columnIndex, matrix.get(rowIndex, columnIndex).doubleValue());
            }
        }
        return result;
    }
    //endregion
}
