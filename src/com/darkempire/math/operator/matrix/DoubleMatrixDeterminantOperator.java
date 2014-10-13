package com.darkempire.math.operator.matrix;

import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.math.struct.matrix.DoubleMatrix;

import static com.darkempire.math.operator.matrix.DoubleMatrixTransformOperator.makeUpperTriangle;
import static com.darkempire.math.operator.matrix.DoubleMatrixTransformOperator.makeLowerTriangle;

/**
 * Created by siredvin on 08.09.14.
 */
@AnjiUtil
public final class DoubleMatrixDeterminantOperator {
    public static final int UPPER_TRIANGLE_METHOD = 0;
    public static final int LOWER_TRIANGLE_METHOD = 1;

    private DoubleMatrixDeterminantOperator() {

    }

    public static double det(DoubleMatrix matrix) {
        return det(matrix, 0);
    }

    /**
     * Обчислює деретмінант матриці різними методами:
     * 0 - через верхню трикутну матрицю
     * 1 - через нижню трикутну матрицю
     *
     * @param matrix матриця, детермінант якої потрібно обчислити
     * @param method номер методу для обчислення
     * @return визначник матриці
     */
    public static double det(DoubleMatrix matrix, int method) {
        double det;
        switch (method) {
            case LOWER_TRIANGLE_METHOD:
                det = detWithLowerTriangle(matrix);
                break;
            default:
                det = detWithUpperTriangle(matrix);
        }
        return det;
    }

    public static double detWithUpperTriangle(DoubleMatrix matrix) {
        DoubleMatrix upperTriangle = makeUpperTriangle(matrix.clone());
        double det = 0;
        if (upperTriangle != null) {
            det = 1;
            int rowCount = upperTriangle.getRowCount();
            for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                det *= upperTriangle.get(rowIndex, rowIndex);
            }
        }
        return det;
    }

    public static double detWithLowerTriangle(DoubleMatrix matrix) {
        DoubleMatrix lowerTriangle = makeLowerTriangle(matrix.clone());
        double det = 0;
        if (lowerTriangle != null) {
            det = 1;
            int rowCount = lowerTriangle.getRowCount();
            for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                det *= lowerTriangle.get(rowIndex, rowIndex);
            }
        }
        return det;
    }
}
