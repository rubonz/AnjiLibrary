package com.darkempire.math.operator.matrix;

import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.math.struct.Number;
import com.darkempire.math.struct.matrix.*;

/**
 * Created by siredvin on 08.09.14.
 */
@AnjiUtil
public final class LinearMatrixGenerateOperator {
    private LinearMatrixGenerateOperator() {

    }
    //region Діагональні матриці

    /**
     * Створює діагональну матрицю такого ж типу, якого була матриця, що сюди подали
     *
     * @param size розмірність матриці
     * @param type тип матриці
     * @return діагональна матриця заданого типу
     */
    public static <T extends com.darkempire.math.struct.Number<T>> LinearMatrix<T> generateDiagonalMatrix(int size, LinearMatrix<T> type) {
        LinearMatrix<T> result = null;
        T el = type.get(0, 0);
        if (type instanceof LinearFixedMatrix) {
            result = generateFixedDiagonalMatrix(size, el);
        }
        if (type instanceof LinearResizeMatrix) {
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
    public static <T extends Number<T>> LinearFixedMatrix<T> generateFixedDiagonalMatrix(int size, T el) {
        LinearFixedMatrix<T> result = LinearFixedMatrix.createInstance(size, size);
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
    public static <T extends Number<T>> LinearResizeMatrix<T> generateResizeDiagonalMatrix(int size, T el) {
        LinearResizeMatrix<T> result = LinearResizeMatrix.createInstance(size, size);
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
}
