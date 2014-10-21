package com.darkempire.math.operator.matrix;

import com.darkempire.anji.annotation.AnjiExperimental;
import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.anji.log.Log;
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
     * Обчислення псевдооберненої матриці за допомогою скелетного розкладу матриці A
     * на матриці B та С і використання формули:
     * A^+ = C^+ * B^+ , причому A = BC
     * @param matrix матриця A
     * @return A^+
     */
    @AnjiExperimental
    public static DoubleMatrix pseudoInverse(DoubleMatrix matrix) {
        DoubleMatrixDecompositionOperator.SkeletonDecompositionResult result = DoubleMatrixDecompositionOperator.skeletonDecomposition(matrix, true);
        int rowCount = matrix.getRowCount();
        int columnCount = matrix.getColumnCount();
        if (result.CPseudoInverse == null) {//Отже, ранг матриці A дорівнює одній з її розмірностей
            //Потрібно з’ясувати якій, адже для знаходження псевдооберненої матриці це має значення (праве або ліве множення)
            //Очевидно, що це буде мінімальний, тому просто знайдемо мінімальний серед двох
            if (rowCount < columnCount) {//Отже, матриця A прихована у матриці B.
                DoubleMatrix b = result.B;
                DoubleMatrix b_t = b.transpose();
                return inverse(b_t.multy(b)).multy(b_t);
            } else {//Отже, матриця A прихована у матриці С
                DoubleMatrix c = result.C;
                DoubleMatrix c_t = c.transpose();
                return c_t.multy(inverse(c.multy(c_t)));
            }
        }
        //Якщо ми дійшли сюди, то ранг матриці A менший за її розміри
        //Будуємо псевдообернену для матриці B
        DoubleMatrix b = result.B;
        DoubleMatrix b_t = b.transpose();
        DoubleMatrix b_p = inverse(b_t.multy(b)).multy(b_t);
        return result.CPseudoInverse.multy(b_p);
    }

    /**
     * Будує обернену матрицю за заданим методом
     * 0 - через метод Гауса
     *
     * @param matrix матриця
     * @param type метод
     * @return обернена матриця
     */
    public static DoubleMatrix inverse(DoubleMatrix matrix, InverseMethodType type) {
        DoubleMatrix inverse = null;
        switch (type) {
            case GAUSSIAN_ELIMINATION:
                inverse = inverseWithGauss(matrix);
                break;
        }
        return inverse;
    }

    public static DoubleMatrix inverse(DoubleMatrix matrix) {
        return inverse(matrix, InverseMethodType.GAUSSIAN_ELIMINATION);
    }

    public static enum InverseMethodType {
        /**
         * Обчислення оберненної методом Гауса
         */
        GAUSSIAN_ELIMINATION
    }
}
