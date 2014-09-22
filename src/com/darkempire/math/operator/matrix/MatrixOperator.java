package com.darkempire.math.operator.matrix;

import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.math.struct.matrix.IMatrix;
import com.darkempire.math.struct.matrix.Matrix;

/**
 * Create in 9:31
 * Created by siredvin on 18.12.13.
 */
@AnjiUtil
public final class MatrixOperator {
    private MatrixOperator() {
    }

    @SuppressWarnings("unchecked")
    public static <T> Matrix<T> transpose(Matrix<T> m1) {
        int n = m1.getColumnCount();
        int m = m1.getRowCount();
        Matrix<T> result = Matrix.createInstance(n, m, (T[]) new Object[n * m]);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                result.setEl(j, i, m1.getEl(i, j));
            }
        }
        return result;
    }

    public static <T extends IMatrix<T, K>, K> boolean checkSymmetry(IMatrix<T, K> m1) {
        boolean k = false;
        int n = m1.getColumnCount();
        int m = m1.getRowCount();
        if (n == m) {
            k = true;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (!m1.getEl(i, j).equals(m1.getEl(j, i))) {
                        k = false;
                        break;
                    }
                }
                if (!k)
                    break;
            }
        }
        return k;
    }


    @SuppressWarnings("unchecked")
    public static <T> Matrix<T> addRow(Matrix<T> m1, T[] row) {
        if (row.length != m1.getColumnCount())
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MISMATCH);
        int n = m1.getColumnCount();
        int m = m1.getRowCount();
        T[] array = (T[]) new Object[n * (m + 1)];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                array[j + i * n] = m1.getEl(i, j);
            }
        }
        System.arraycopy(row, 0, array, m * n, n);
        return Matrix.createInstance(m + 1, n, array);
    }

    @SuppressWarnings("unchecked")
    public static <T> Matrix<T> addColumn(Matrix<T> m1, T[] column) {
        if (column.length != m1.getRowCount())
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MISMATCH);
        int n = m1.getColumnCount();
        int m = m1.getRowCount();
        T[] array = (T[]) new Object[(n + 1) * m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                array[j + i * (n + 1)] = m1.getEl(i, j);
            }
        }
        for (int j = 0; j < m; j++) {
            array[n + j * (n + 1)] = column[j];
        }
        return Matrix.createInstance(m, n + 1, array);
    }

    @SuppressWarnings("unchecked")
    public static <T> Matrix<T> removeColumn(Matrix<T> m1, int indexC) {
        if (m1.getColumnCount() < indexC - 1 || indexC < 0)
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MISMATCH);
        int n = m1.getColumnCount();
        int m = m1.getRowCount();
        T[] array = (T[]) new Object[(n - 1) * m];
        Matrix<T> temp = Matrix.createInstance(m, n - 1, array);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < indexC; j++) {
                temp.setEl(i, j, m1.getEl(i, j));
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = indexC + 1; j < n; j++) {
                temp.setEl(i, j - 1, m1.getEl(i, j));
            }
        }
        return temp;
    }

    @SuppressWarnings("unchecked")
    public static <T> Matrix<T> removeRow(Matrix<T> m1, int indexR) {
        if (m1.getRowCount() < indexR - 1 || indexR < 0)
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MISMATCH);
        int n = m1.getColumnCount();
        int m = m1.getRowCount();
        T[] array = (T[]) new Object[n * (m - 1)];
        Matrix<T> temp = Matrix.createInstance(m - 1, n, array);
        for (int i = 0; i < indexR; i++) {
            for (int j = 0; j < n; j++) {
                temp.setEl(i, j, m1.getEl(i, j));
            }
        }
        for (int i = indexR + 1; i < m; i++) {
            for (int j = 0; j < n; j++) {
                temp.setEl(i - 1, j, m1.getEl(i, j));
            }
        }
        return temp;
    }
}
