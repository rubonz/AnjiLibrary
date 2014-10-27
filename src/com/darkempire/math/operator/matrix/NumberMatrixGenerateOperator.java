package com.darkempire.math.operator.matrix;

import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.math.struct.Number;
import com.darkempire.math.struct.function.FDoubleNumber;
import com.darkempire.math.struct.matrix.DoubleMatrix;
import com.darkempire.math.struct.matrix.NumberFixedMatrix;
import com.darkempire.math.struct.matrix.NumberMatrix;
import com.darkempire.math.struct.matrix.NumberResizeMatrix;
import com.darkempire.math.struct.vector.IVectorProvider;

import java.util.List;

/**
 * Created by siredvin on 08.09.14.
 *
 * @author siredvin
 */
@AnjiUtil
public final class NumberMatrixGenerateOperator {
    private NumberMatrixGenerateOperator() {

    }
    //region Діагональні матриці

    /**
     * Створює діагональну матрицю такого ж типу, якого була матриця, що сюди подали
     *
     * @param size розмірність матриці
     * @param type тип матриці
     * @return діагональна матриця заданого типу
     */
    public static <T extends com.darkempire.math.struct.Number<T>> NumberMatrix<T> generateDiagonalMatrix(int size, NumberMatrix<T> type) {
        NumberMatrix<T> result = null;
        T el = type.get(0, 0);
        if (type instanceof NumberFixedMatrix) {
            result = generateFixedDiagonalMatrix(size, el);
        }
        if (type instanceof NumberResizeMatrix) {
            result = generateResizeDiagonalMatrix(size, el);
        }
        return result;
    }

    /**
     * Створює діагональну матрицю фіксованого розміру
     *
     * @param size розмірність матриці
     * @return діагональна фіксована матриця
     */
    public static <T extends Number<T>> NumberFixedMatrix<T> generateFixedDiagonalMatrix(int size, T el) {
        NumberFixedMatrix<T> result = NumberFixedMatrix.createInstance(size, size);
        for (int rowIndex = 0; rowIndex < size; rowIndex++) {
            for (int columnIndex = 0; columnIndex < size; columnIndex++) {
                if (rowIndex == columnIndex) {
                    result.set(rowIndex, columnIndex, el.getOne());
                } else {
                    result.set(rowIndex, columnIndex, el.getZero());
                }
            }
        }
        return result;
    }

    /**
     * Створює діагональну матрицю змінного розміру
     *
     * @param size розмірність матриці
     * @return діагональна матриця
     */
    public static <T extends Number<T>> NumberResizeMatrix<T> generateResizeDiagonalMatrix(int size, T el) {
        NumberResizeMatrix<T> result = NumberResizeMatrix.createInstance(size, size);
        for (int rowIndex = 0; rowIndex < size; rowIndex++) {
            for (int columnIndex = 0; columnIndex < size; columnIndex++) {
                if (rowIndex == columnIndex) {
                    result.set(rowIndex, columnIndex, el.getOne());
                } else {
                    result.set(rowIndex, columnIndex, el.getZero());
                }
            }
        }
        return result;
    }
    //endregion

    //region Матриці з векторів

    //region Вектори як рядки
    @SafeVarargs
    public static <T extends Number<T>> NumberFixedMatrix<T> fixedFromRows(IVectorProvider<T>... rows) {
        NumberFixedMatrix<T> matrix = NumberFixedMatrix.createInstance(rows.length, rows[0].getSize());
        for (int rowIndex = 0; rowIndex < rows.length; rowIndex++) {
            matrix.fillRow(rowIndex, rows[rowIndex]);
        }
        return matrix;
    }

    public static <T extends Number<T>> NumberFixedMatrix<T> fixedFromRows(List<IVectorProvider<T>> rows) {
        int size = rows.size();
        NumberFixedMatrix<T> matrix = NumberFixedMatrix.createInstance(size, rows.get(0).getSize());
        for (int rowIndex = 0; rowIndex < size; rowIndex++) {
            matrix.fillRow(rowIndex, rows.get(rowIndex));
        }
        return matrix;
    }

    @SafeVarargs
    public static <T extends Number<T>> NumberResizeMatrix<T> resizeFromRows(IVectorProvider<T>... rows) {
        NumberResizeMatrix<T> matrix = NumberResizeMatrix.createInstance(rows.length, rows[0].getSize());
        for (int rowIndex = 0; rowIndex < rows.length; rowIndex++) {
            matrix.fillRow(rowIndex, rows[rowIndex]);
        }
        return matrix;
    }

    public static <T extends Number<T>> NumberResizeMatrix<T> resizeFromRows(List<IVectorProvider<T>> rows) {
        int size = rows.size();
        NumberResizeMatrix<T> matrix = NumberResizeMatrix.createInstance(size, rows.get(0).getSize());
        for (int rowIndex = 0; rowIndex < size; rowIndex++) {
            matrix.fillRow(rowIndex, rows.get(rowIndex));
        }
        return matrix;
    }
    //endregion

    //region Вектори як стовпчики
    @SafeVarargs
    public static <T extends Number<T>> NumberFixedMatrix<T> fixedFromColumns(IVectorProvider<T>... columns) {
        NumberFixedMatrix<T> matrix = NumberFixedMatrix.createInstance(columns.length, columns[0].getSize());
        for (int columnIndex = 0; columnIndex < columns.length; columnIndex++) {
            matrix.fillColumn(columnIndex, columns[columnIndex]);
        }
        return matrix;
    }

    public static <T extends Number<T>> NumberFixedMatrix<T> fixedFromColumns(List<IVectorProvider<T>> columns) {
        int size = columns.size();
        NumberFixedMatrix<T> matrix = NumberFixedMatrix.createInstance(size, columns.get(0).getSize());
        for (int columnIndex = 0; columnIndex < size; columnIndex++) {
            matrix.fillColumn(columnIndex, columns.get(columnIndex));
        }
        return matrix;
    }

    @SafeVarargs
    public static <T extends Number<T>> NumberResizeMatrix<T> resizeFromColumns(IVectorProvider<T>... columns) {
        NumberResizeMatrix<T> matrix = NumberResizeMatrix.createInstance(columns.length, columns[0].getSize());
        for (int columnIndex = 0; columnIndex < columns.length; columnIndex++) {
            matrix.fillColumn(columnIndex, columns[columnIndex]);
        }
        return matrix;
    }

    public static <T extends Number<T>> NumberResizeMatrix<T> resizeFromColumns(List<IVectorProvider<T>> columns) {
        int size = columns.size();
        NumberResizeMatrix<T> matrix = NumberResizeMatrix.createInstance(size, columns.get(0).getSize());
        for (int columnIndex = 0; columnIndex < size; columnIndex++) {
            matrix.fillColumn(columnIndex, columns.get(columnIndex));
        }
        return matrix;
    }
    //endregion
    //endregion

    //region Спеціальні матриці
    public static <T extends com.darkempire.math.struct.Number<T>> NumberFixedMatrix<T> fromDoubletoFixed(DoubleMatrix matrix, FDoubleNumber<T> converter) {
        int rowCount = matrix.getRowCount();
        int columnCount = matrix.getColumnCount();
        NumberFixedMatrix<T> result = NumberFixedMatrix.createInstance(rowCount, columnCount, (T[]) new Number[rowCount * columnCount]);
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                result.set(rowIndex, columnIndex, converter.calc(matrix.get(rowIndex, columnIndex)));
            }
        }
        return result;
    }

    public static <T extends com.darkempire.math.struct.Number<T>> NumberResizeMatrix<T> fromDoubletoResize(DoubleMatrix matrix, FDoubleNumber<T> converter) {
        int rowCount = matrix.getRowCount();
        int columnCount = matrix.getColumnCount();
        NumberResizeMatrix<T> result = NumberResizeMatrix.createInstance(rowCount, columnCount, (T[]) new Number[rowCount * columnCount]);
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                result.set(rowIndex, columnIndex, converter.calc(matrix.get(rowIndex, columnIndex)));
            }
        }
        return result;
    }
    //endregion
}
