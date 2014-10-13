package com.darkempire.math.operator.matrix;

import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.math.exception.MatrixSizeException;
import com.darkempire.math.struct.matrix.DoubleMatrix;

import static com.darkempire.math.operator.matrix.DoubleMatrixMathOperator.addRow;

/**
 * Created by siredvin on 13.10.14.
 *
 * @author siredvin
 */
@AnjiUtil
public final class DoubleMatrixInverseOperator {
    private DoubleMatrixInverseOperator() {
    }

    //region Обчислення обернених матриць
    public static DoubleMatrix inverseWithGauss(DoubleMatrix matrix) {
        int rowCount = matrix.getRowCount();
        int columnCount = matrix.getColumnCount();
        if (rowCount != columnCount) {
            throw new MatrixSizeException(MatrixSizeException.MATRIX_IS_NOT_SQUARE);
        }
        DoubleMatrix temp = matrix.clone();
        DoubleMatrix result = DoubleMatrixGenerateOperator.generateDiagonalMatrix(rowCount, temp);
        //Починаємо цикл
        for (int index = 0; index < columnCount; index++) {
            double baseElement = temp.get(index, index);
            if (baseElement != 0) {//Гілка, якщо базовий елемент не нульовий
                for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                    if (rowIndex == index)
                        continue;
                    double nextElement = -temp.get(rowIndex, index) / baseElement;
                    if (nextElement != 0) {
                        addRow(temp, rowIndex, index, nextElement);
                        addRow(result, rowIndex, index, nextElement);
                    }
                }
            } else {//Якщо базовий елемент таки нульовий
                return null;
            }
        }
        //А тепер приводимо матрицю temp з діагонального до одиничного виду
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            double baseElement = temp.get(rowIndex, rowIndex);
            temp.operateRow(rowIndex, d -> d / baseElement);
            result.operateRow(rowIndex, d -> d / baseElement);
        }
        return result;
    }
    //endregion

    /**
     * Будує обернену матрицю за заданим методом
     * 0 - через метод Гауса
     *
     * @param matrix матриця
     * @param method метод
     * @return обернена матриця
     */
    public static DoubleMatrix inverse(DoubleMatrix matrix, int method) {
        DoubleMatrix inverse;
        switch (method) {
            default:
                inverse = inverseWithGauss(matrix);
        }
        return inverse;
    }

    public static DoubleMatrix inverse(DoubleMatrix matrix) {
        return inverse(matrix, 0);
    }
}
