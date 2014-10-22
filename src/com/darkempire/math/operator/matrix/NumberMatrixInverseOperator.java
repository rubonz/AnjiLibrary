package com.darkempire.math.operator.matrix;

import com.darkempire.anji.annotation.AnjiExperimental;
import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.math.exception.MatrixSizeException;
import com.darkempire.math.struct.Number;
import com.darkempire.math.struct.matrix.NumberMatrix;

import static com.darkempire.math.operator.matrix.NumberMatrixMathOperator.addRow;

/**
 * Created by siredvin on 13.10.14.
 *
 * @author siredvin
 */
@AnjiUtil
public final class NumberMatrixInverseOperator {
    private NumberMatrixInverseOperator() {
    }

    //region Обчислення обернених матриць
    public static <T extends com.darkempire.math.struct.Number<T>> NumberMatrix<T> inverseWithGauss(NumberMatrix<T> matrix) {
        int rowCount = matrix.getRowCount();
        int columnCount = matrix.getColumnCount();
        if (rowCount != columnCount) {
            throw new MatrixSizeException(MatrixSizeException.MATRIX_IS_NOT_SQUARE);
        }
        NumberMatrix<T> temp = matrix.clone();
        NumberMatrix<T> result = NumberMatrixGenerateOperator.generateDiagonalMatrix(rowCount, temp);
        T zero = temp.get(0, 0).getZero();
        //Починаємо цикл
        for (int index = 0; index < columnCount; index++) {
            T baseElement = temp.get(index, index);
            if (baseElement.compareTo(zero) != 0) {//Гілка, якщо базовий елемент не нульовий
                for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                    if (rowIndex == index)
                        continue;
                    T nextElement = temp.get(rowIndex, index).divide(baseElement).inegate();
                    if (nextElement.compareTo(zero) != 0) {
                        addRow(temp, rowIndex, index, nextElement);
                        addRow(result, rowIndex, index, nextElement);
                    }
                }
            } else {//Якщо базовий елемент таки нульовий, то матриця вироджена
                return null;
            }
        }
        //А тепер приводимо матрицю temp з діагонального до одиничного виду
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            T baseElement = temp.get(rowIndex, rowIndex).deepcopy();
            temp.operateRow(rowIndex, d -> d.idivide(baseElement));
            result.operateRow(rowIndex, d -> d.idivide(baseElement));
        }
        return result;
    }
    //endregion

    /**
     * Обчислення псевдооберненої матриці за допомогою скелетного розкладу матриці A
     * на матриці B та С і використання формули:
     * A^+ = C^+ * B^+ , причому A = BC
     *
     * @param matrix матриця A
     * @return A^+
     */
    @AnjiExperimental
    public static <T extends Number<T>> NumberMatrix<T> pseudoInverse(NumberMatrix<T> matrix) {
        NumberMatrixDecompositionOperator.SkeletonDecompositionResult<T> result = NumberMatrixDecompositionOperator.skeletonDecomposition(matrix, true);
        int rowCount = matrix.getRowCount();
        int columnCount = matrix.getColumnCount();
        if (result.CPseudoInverse == null) {//Отже, ранг матриці A дорівнює одній з її розмірностей
            //Потрібно з’ясувати якій, адже для знаходження псевдооберненої матриці це має значення (праве або ліве множення)
            //Очевидно, що це буде мінімальний, тому просто знайдемо мінімальний серед двох
            if (rowCount < columnCount) {//Отже, матриця A прихована у матриці B.
                NumberMatrix<T> b = result.B;
                NumberMatrix<T> b_t = b.transpose();
                return inverse(b_t.multy(b)).multy(b_t);
            } else {//Отже, матриця A прихована у матриці С
                NumberMatrix<T> c = result.C;
                NumberMatrix<T> c_t = c.transpose();
                return c_t.multy(inverse(c.multy(c_t)));
            }
        }
        //Якщо ми дійшли сюди, то ранг матриці A менший за її розміри
        //Будуємо псевдообернену для матриці B
        NumberMatrix<T> b = result.B;
        NumberMatrix<T> b_t = b.transpose();
        NumberMatrix<T> b_i = inverse(b_t.multy(b));
        if (b_i == null) {
//            Log.log(Log.debugIndex,"B=", WolframConvertUtils.convertDoubleMatrix(b));
//            //Log.log(Log.debugIndex,"A=",WolframConvertUtils.convertDoubleMatrix(matrix));
//            Log.log(Log.debugIndex,"C=",WolframConvertUtils.convertDoubleMatrix(result.C));
//            Log.log(Log.debugIndex,"Ci=",WolframConvertUtils.convertDoubleMatrix(result.CPseudoInverse));
        }
        NumberMatrix<T> b_p = b_i.multy(b_t);
        return result.CPseudoInverse.multy(b_p);
    }

    /**
     * Будує обернену матрицю за заданим методом
     *
     * @param matrix матриця
     * @param type   метод
     * @return обернена матриця
     */
    public static <T extends Number<T>> NumberMatrix<T> inverse(NumberMatrix<T> matrix, InverseMethodType type) {
        NumberMatrix<T> inverse = null;
        switch (type) {
            case GAUSSIAN_ELIMINATION:
                inverse = inverseWithGauss(matrix);
                break;
        }
        return inverse;
    }

    public static <T extends Number<T>> NumberMatrix<T> inverse(NumberMatrix<T> matrix) {
        return inverse(matrix, InverseMethodType.GAUSSIAN_ELIMINATION);
    }

    public static enum InverseMethodType {
        /**
         * Обчислення оберненної методом Гауса
         */
        GAUSSIAN_ELIMINATION
    }
}
