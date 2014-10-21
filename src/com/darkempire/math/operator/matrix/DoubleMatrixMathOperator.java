package com.darkempire.math.operator.matrix;

import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.math.exception.MatrixSizeException;
import com.darkempire.math.struct.matrix.DoubleMatrix;
import com.darkempire.math.struct.matrix.MatrixIndex;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Created by siredvin on 08.09.14.
 */
@AnjiUtil
public final class DoubleMatrixMathOperator {
    private DoubleMatrixMathOperator() {

    }

    //region Додавання рядків та стовпчиків між собою
    public static DoubleMatrix addRow(DoubleMatrix matrix, int sourceRow, int additionalRow, double lambda) {
        int columnCount = matrix.getColumnCount();
        for (int j = 0; j < columnCount; j++) {
            double k = matrix.get(additionalRow, j) * lambda;
            matrix.set(sourceRow, j, matrix.get(sourceRow, j) + k);
        }
        return matrix;
    }

    public static DoubleMatrix addColumn(DoubleMatrix matrix, int sourceColumn, int additionalColumn, double lambda) {
        int rowCount = matrix.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            double k = matrix.get(i, additionalColumn) * lambda;
            matrix.set(i, sourceColumn, matrix.get(i, sourceColumn) + k);
        }
        return matrix;
    }
    //endregion

    //region Перетворення рядків та стовпчиків на одиничні
    public static DoubleMatrix makeBaseRow(DoubleMatrix matrix, int baseRowIndex, int baseColumnIndex) {
        double baseElement = matrix.get(baseRowIndex, baseColumnIndex);
        matrix.operateColumn(baseColumnIndex, d -> d / baseElement);
        int columnCount = matrix.getColumnCount();
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            if (columnIndex == baseColumnIndex) {
                continue;
            }
            double nextElement = -matrix.get(baseRowIndex, columnIndex);
            addColumn(matrix, columnIndex, baseColumnIndex, nextElement);
        }
        return matrix;
    }

    public static DoubleMatrix makeBaseColumn(DoubleMatrix matrix, int baseRowIndex, int baseColumnIndex) {
        double baseElement = matrix.get(baseRowIndex, baseColumnIndex);
        matrix.operateRow(baseRowIndex, d -> d / baseElement);
        int rowCount = matrix.getRowCount();
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            if (rowIndex == baseRowIndex) {
                continue;
            }
            double nextElement = -matrix.get(rowIndex, baseColumnIndex);
            addRow(matrix, rowIndex, baseRowIndex, nextElement);
        }
        return matrix;
    }
    //endregion

    //region Обчислення характеристик матриці

    /**
     * Знаходить найбільший елемент за модулем у матриці
     *
     * @param matrix матриця A
     * @return base A
     */
    public static MatrixIndex getBaseElement(DoubleMatrix matrix) {
        int i1 = 0, j1 = 1, n = matrix.getColumnCount(), m = matrix
                .getRowCount();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    if (abs(matrix.get(i, j)) > abs(matrix.get(i1, j1))) {
                        i1 = i;
                        j1 = j;
                    }
                }
            }
        }
        return new MatrixIndex(i1, j1);
    }

    /**
     * Обчислює суму елементів сліду у квадраті
     *
     * @param matrix матриця A
     * @return trace A
     */
    public static double calcSTrance(DoubleMatrix matrix) {
        int n = min(matrix.getColumnCount(), matrix.getRowCount());
        double res = 0;
        for (int i = 0; i < n; i++) {
            double k = matrix.get(i, i);
            res += k * k;
        }
        return res;
    }

    /**
     * Обчислює суму елементів, які не належать сліду у квадраті
     *
     * @param matrix матриця
     * @return сума квадратів недіагональних елементів
     */
    public static double calcSUnTrance(DoubleMatrix matrix) {
        int n = matrix.getColumnCount();
        int m = matrix.getRowCount();
        double res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    double k = matrix.get(i, j);
                    res += k * k;
                }
            }
        }
        return res;
    }

    /**
     * Обчислює кількість ненульових елементів на головній діагоналі матриці.
     * Корисна допоміжна функція. Використовується, наприклад, для обчислення рангу трапецевидної матриці
     *
     * @param matrix матриця A
     * @return кількість ненульових елементів на головній діагоналі
     */
    public static int calcUnZeroDiagonalElementCount(DoubleMatrix matrix) {
        int size = Math.min(matrix.getRowCount(), matrix.getColumnCount());
        int result = 0;
        for (int i = 0; i < size; i++) {
            if (matrix.get(i, i) != 0) {
                result++;
            }
        }
        return result;
    }
    //endregion

}
