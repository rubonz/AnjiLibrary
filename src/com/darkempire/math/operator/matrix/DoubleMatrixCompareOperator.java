package com.darkempire.math.operator.matrix;

import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.math.MathMachine;
import com.darkempire.math.struct.matrix.DoubleMatrix;

import static java.lang.Math.abs;

/**
 * Created by siredvin on 08.09.14.
 */
@AnjiUtil
public final class DoubleMatrixCompareOperator {

    private DoubleMatrixCompareOperator() {
    }

    //region Порівняння з нулем
    public static boolean isZeroRow(DoubleMatrix matrix, int rowIndex) {
        boolean result = true;
        int columnCount = matrix.getColumnCount();
        for (int j = 0; j < columnCount; j++) {
            if (matrix.get(rowIndex, j) != 0) {
                result = false;
                break;
            }
        }
        return result;
    }

    public static boolean isZeroColumn(DoubleMatrix matrix, int columnIndex) {
        boolean result = true;
        int rowCount = matrix.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            if (matrix.get(i, columnIndex) != 0) {
                result = false;
                break;
            }
        }
        return result;
    }
    //endregion

    //region Порівння з машинним нулем
    public static boolean isMachineZeroRow(DoubleMatrix matrix, int rowIndex) {
        boolean result = true;
        int columnCount = matrix.getColumnCount();
        for (int j = 0; j < columnCount; j++) {
            if (Math.abs(matrix.get(rowIndex, j)) > MathMachine.MACHINE_EPS) {
                result = false;
                break;
            }
        }
        return result;
    }

    public static boolean isMachineZeroColumn(DoubleMatrix matrix, int columnIndex) {
        boolean result = true;
        int rowCount = matrix.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            if (Math.abs(matrix.get(i, columnIndex)) > MathMachine.MACHINE_EPS) {
                result = false;
                break;
            }
        }
        return result;
    }
    //endregion

    //region Порівняння з деяким eps
    public static boolean isEpsZeroRow(DoubleMatrix matrix, int rowIndex, double eps) {
        boolean result = true;
        int columnCount = matrix.getColumnCount();
        for (int j = 0; j < columnCount; j++) {
            if (Math.abs(matrix.get(rowIndex, j)) > eps) {
                result = false;
                break;
            }
        }
        return result;
    }


    public static boolean isEpsZeroColumn(DoubleMatrix matrix, int columnIndex, double eps) {
        boolean result = true;
        int rowCount = matrix.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            if (Math.abs(matrix.get(i, columnIndex)) > eps) {
                result = false;
                break;
            }
        }
        return result;
    }
    //endregion

    //region Перетвореня елементів матриці у нулі за умови того, що вони менші
    public static DoubleMatrix machineZeroToZero(DoubleMatrix matrix) {
        int rowCount = matrix.getRowCount();
        int columnCount = matrix.getColumnCount();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                if (Math.abs(matrix.get(i, j)) <= MathMachine.MACHINE_EPS) {
                    matrix.set(i, j, 0);
                }
            }
        }
        return matrix;
    }

    public static DoubleMatrix epsZeroToZero(DoubleMatrix matrix, double eps) {
        int rowCount = matrix.getRowCount();
        int columnCount = matrix.getColumnCount();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                if (Math.abs(matrix.get(i, j)) <= eps) {
                    matrix.set(i, j, 0);
                }
            }
        }
        return matrix;
    }
    //endregion

    //region Порівняння матриць
    public static boolean isEquals(DoubleMatrix m1, DoubleMatrix m2) {
        if (m1 == m2)
            return true;
        int columnCount = m1.getColumnCount();
        int rowCount = m1.getRowCount();
        if (columnCount == m2.getColumnCount() && rowCount == m2.getRowCount()) {
            for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                    if (m1.get(rowIndex, columnIndex) != m2.get(rowIndex, columnIndex)) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    public static boolean isEqualsMachineEps(DoubleMatrix m1, DoubleMatrix m2) {
        if (m1 == m2)
            return true;
        int columnCount = m1.getColumnCount();
        int rowCount = m1.getRowCount();
        if (columnCount == m2.getColumnCount() && rowCount == m2.getRowCount()) {
            for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                    if (abs(m1.get(rowIndex, columnIndex) - m2.get(rowIndex, columnIndex)) > MathMachine.MACHINE_EPS) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    public static boolean isEqualsMachineHalfEps(DoubleMatrix m1, DoubleMatrix m2) {
        if (m1 == m2)
            return true;
        int columnCount = m1.getColumnCount();
        int rowCount = m1.getRowCount();
        if (columnCount == m2.getColumnCount() && rowCount == m2.getRowCount()) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                    if (abs(m1.get(rowIndex, columnIndex) - m2.get(rowIndex, columnIndex)) > MathMachine.MACHINE_HALF_EPS) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    public static boolean isEqualsEps(DoubleMatrix m1, DoubleMatrix m2, double eps) {
        if (m1 == m2)
            return true;
        int columnCount = m1.getColumnCount();
        int rowCount = m1.getRowCount();
        if (columnCount == m2.getColumnCount() && rowCount == m2.getRowCount()) {
            for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                    if (abs(m1.get(rowIndex, columnIndex) - m2.get(rowIndex, columnIndex)) > eps) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
    //endregion
}
