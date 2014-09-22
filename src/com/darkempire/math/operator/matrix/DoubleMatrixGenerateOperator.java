package com.darkempire.math.operator.matrix;

import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.math.struct.matrix.DoubleMatrix;
import com.darkempire.math.struct.matrix.DoubleFixedMatrix;
import com.darkempire.math.struct.matrix.DoubleResizeMatrix;

/**
 * Created by siredvin on 08.09.14.
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
}
