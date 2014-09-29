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
}
