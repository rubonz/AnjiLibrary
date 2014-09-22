package com.darkempire.math.utils;

import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.math.struct.matrix.DoubleFixedMatrix;
import com.darkempire.math.struct.matrix.DoubleResizeMatrix;
import com.darkempire.math.struct.vector.IDoubleVectorProvider;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 13.11.13
 * Time: 15:41
 * To change this template use File | Settings | File Templates.
 */
@AnjiUtil
public final class MatrixUtils {
    private MatrixUtils() {
    }

    /**
     * Обчислює визначний матриці виду:
     * a11 a12
     * a21 a22
     *
     * @return визначник
     */
    public static double determinant(double a11, double a12, double a21, double a22) {
        return a11 * a22 - a12 * a21;
    }

    /**
     * Обчислює визначний матриці виду:
     * a11 a12 a13
     * a21 a22 a23
     * a31 a32 a33
     *
     * @return визначник
     */
    public static double determinant(double a11, double a12, double a13, double a21, double a22, double a23, double a31, double a32, double a33) {
        return a11 * determinant(a22, a23, a32, a33) - a12 * determinant(a21, a23, a31, a33) + a13 * determinant(a21, a22, a31, a32);
    }

    /**
     * Будує за векторами матрицю фіксованого розміру, записуючи ці вектори у рядки
     *
     * @param columnCount кількість колонок
     * @param vectors     вектори-рядки
     * @return утворена матрия
     */
    public static DoubleFixedMatrix createMatrixRowsF(int columnCount, IDoubleVectorProvider... vectors) {
        DoubleFixedMatrix result = DoubleFixedMatrix.createInstance(vectors.length, columnCount);
        for (int i = 0; i < columnCount; i++) {
            int temp = i;
            result.fillColumn(i, (rowIndex, columnIndex) -> vectors[rowIndex].get(temp));
        }
        return result;
    }

    /**
     * Будує за векторами матрицю змінного розміру, записуючи ці вектори у рядки
     *
     * @param columnCount кількість колонок
     * @param vectors     вектори-рядки
     * @return утворена матрия
     */
    public static DoubleResizeMatrix createMatrixRowsR(int columnCount, IDoubleVectorProvider... vectors) {
        DoubleResizeMatrix result = DoubleResizeMatrix.createInstance(vectors.length, columnCount);
        for (int i = 0; i < columnCount; i++) {
            int temp = i;
            result.fillColumn(i, (rowIndex, columnIndex) -> vectors[rowIndex].get(temp));
        }
        return result;
    }

    /**
     * Будує за векторами матрицю фіксованого розміру, записуючи ці вектори у стовбці
     *
     * @param rowCount кількість рядків
     * @param vectors  вектори-стовбці
     * @return утворена матрия
     */
    public static DoubleFixedMatrix createMatrixColumnF(int rowCount, IDoubleVectorProvider... vectors) {
        DoubleFixedMatrix result = DoubleFixedMatrix.createInstance(rowCount, vectors.length);
        for (int i = 0; i < rowCount; i++) {
            int temp = i;
            result.fillRow(i, (rowIndex, columnIndex) -> vectors[columnIndex].get(temp));
        }
        return result;
    }

    /**
     * Будує за векторами матрицю змінного розміру, записуючи ці вектори у стовбці
     *
     * @param rowCount кількість рядків
     * @param vectors  вектори-стовбці
     * @return утворена матрия
     */
    public static DoubleResizeMatrix createMatrixColumnR(int rowCount, IDoubleVectorProvider... vectors) {
        DoubleResizeMatrix result = DoubleResizeMatrix.createInstance(rowCount, vectors.length);
        for (int i = 0; i < rowCount; i++) {
            int temp = i;
            result.fillRow(i, (rowIndex, columnIndex) -> vectors[columnIndex].get(temp));
        }
        return result;
    }
}
