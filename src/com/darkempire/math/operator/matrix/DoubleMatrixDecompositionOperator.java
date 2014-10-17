package com.darkempire.math.operator.matrix;

import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.math.exception.MatrixSizeException;
import com.darkempire.math.exception.MatrixTypeException;
import com.darkempire.math.struct.matrix.DoubleFixedMatrix;
import com.darkempire.math.struct.matrix.DoubleMatrix;

/**
 * Created by siredvin on 17.10.14.
 *
 * @author siredvin
 */
@AnjiUtil
public final class DoubleMatrixDecompositionOperator {
    private DoubleMatrixDecompositionOperator() {
    }

    //region Декомпозиція додатньовизначенних та невироджених матриць (Холецького)

    /**
     * Реалізує розклад Холецького.
     * Він знаходиться за формулою A = L.L^T
     * Де, L - нижньотрикутна матриця, а A - додатньовизначена та невироджена
     * Точне значення знаходиться за формулами:
     * <p>
     * l_{j,j} = \sqrt{a_{j,j} - \sum\limits_{k=1}^{j-1} l_{j,k}^2}
     * l_{i,j} = \cfrac1{l_{j,j}} \left( a_{i,j} - \sum\limits_{k=1}^{j-1} l_{i,k} l_{j,k} \right),~i>j
     *
     * @param a матрица A
     * @return матрицю L
     */
    public static DoubleMatrix choleskyDecomposition(DoubleMatrix a) {
        int size = a.getColumnCount();
        if (size != a.getRowCount()) {
            throw new MatrixSizeException(MatrixSizeException.MATRIX_IS_NOT_SQUARE);
        }
        DoubleMatrix result = DoubleFixedMatrix.createInstance(size, size);
        for (int rowIndex = 0; rowIndex < size; rowIndex++) {
            for (int columnIndex = 0; columnIndex <= rowIndex; columnIndex++) {
                double value;
                double temp = a.get(rowIndex, columnIndex);
                if (rowIndex == columnIndex) {
                    double temp1 = 0;
                    for (int k = 0; k < columnIndex; k++) {
                        //l_{j,k}
                        double temp2 = result.get(columnIndex, k);
                        temp1 += temp2 * temp2;
                    }
                    temp1 = temp - temp1;
                    if (temp1 < 0) {
                        throw new MatrixTypeException(MatrixTypeException.MATRIX_IS_NOT_POSITIVE);
                    }
                    value = Math.sqrt(temp1);
                } else {
                    //l_{j,j}
                    double temp1 = result.get(columnIndex, columnIndex);
                    if (temp1 == 0) {
                        throw new MatrixTypeException(MatrixTypeException.MATRIX_IS_NOT_DEFINITE);
                    }
                    double temp2 = 0;
                    for (int k = 0; k < columnIndex; k++) {
                        temp2 += result.get(rowIndex, k) * result.get(columnIndex, k);
                    }
                    value = (temp - temp2) / temp1;
                }
                result.set(rowIndex, columnIndex, value);
            }
        }
        return result;
    }
    //endregion

    //region Декомпозиція невироджених матриць (LDL)

    /**
     * Реалізує розклад LDL.
     * Він знаходиться за формулою A = L.D.L^T
     * Де, L - нижньотрикутна матриця на головній діагоналі якої одинички,
     * D - діагональна матриця, а A - невироджена
     * Точне значення знаходиться за формулами:
     * <p>
     * D_j = a_{j,j} - \sum\limits_{k=1}^{j-1} l_{j,k}^2 D_k
     * l_{i,j} = \cfrac1{D_j} \left( a_{i,j} - \sum\limits_{k=1}^{j-1} l_{i,k} l_{j,k} D_k \right),~i>j
     *
     * @param a матрица A
     * @return матрицю L
     */
    public static LDLDecompositionResult LDLDecomposition(DoubleMatrix a) {
        int size = a.getColumnCount();
        if (size != a.getRowCount()) {
            throw new MatrixSizeException(MatrixSizeException.MATRIX_IS_NOT_SQUARE);
        }
        DoubleMatrix d = DoubleFixedMatrix.createInstance(size, size);
        DoubleMatrix l = DoubleFixedMatrix.createInstance(size, size);
        for (int rowIndex = 0; rowIndex < size; rowIndex++) {
            l.set(rowIndex, rowIndex, 1);
            for (int columnIndex = 0; columnIndex <= rowIndex; columnIndex++) {
                double temp = a.get(rowIndex, columnIndex);
                if (rowIndex == columnIndex) {
                    double temp1 = 0;
                    for (int k = 0; k < columnIndex; k++) {
                        //l_{j,k}
                        double temp2 = l.get(columnIndex, k);
                        temp1 += temp2 * temp2 * d.get(k, k);
                    }
                    temp1 = temp - temp1;
                    d.set(rowIndex, columnIndex, temp1);
                } else {
                    //l_{j,j}
                    double temp1 = d.get(columnIndex, columnIndex);
                    if (temp1 == 0) {
                        throw new MatrixTypeException(MatrixTypeException.MATRIX_IS_NOT_DEFINITE);
                    }
                    double temp2 = 0;
                    for (int k = 0; k < columnIndex; k++) {
                        temp2 += l.get(rowIndex, k) * l.get(columnIndex, k) * d.get(k, k);
                    }
                    l.set(rowIndex, columnIndex, (temp - temp2) / temp1);
                }
            }
        }
        return new LDLDecompositionResult(l, d);
    }

    public static class LDLDecompositionResult {
        public DoubleMatrix L;
        public DoubleMatrix D;

        public LDLDecompositionResult(DoubleMatrix l, DoubleMatrix d) {
            L = l;
            D = d;
        }
    }
    //endregion
}
