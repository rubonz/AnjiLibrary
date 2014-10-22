package com.darkempire.math.operator.matrix;

import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.math.struct.Number;
import com.darkempire.math.struct.matrix.NumberMatrix;

import static java.lang.Math.abs;
import static java.lang.Math.min;

/**
 * Create in 9:27
 * Created by siredvin on 18.12.13.
 */
@AnjiUtil
public final class NumberMatrixOperator {
    private NumberMatrixOperator() {
    }

    public static <T extends Number<T>> NumberMatrix<T> addRow(NumberMatrix<T> matrix, int sourceRow, int additionalRow, T lambda) {
        int columnCount = matrix.getColumnCount();
        for (int j = 0; j < columnCount; j++) {
            T k = matrix.get(additionalRow, j).multiply(lambda);
            matrix.get(sourceRow, j).iadd(k);
        }
        return matrix;
    }

    public static <T extends Number<T>> NumberMatrix<T> addColumn(NumberMatrix<T> matrix, int sourceColumn, int additionalColumn, T lambda) {
        int rowCount = matrix.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            T k = matrix.get(i, additionalColumn).multiply(lambda);
            matrix.get(i, sourceColumn).iadd(k);
        }
        return matrix;
    }

    public static <T extends Number<T>> NumberMatrix<T> makeBaseRow(NumberMatrix<T> matrix, int baseRowIndex, int baseColumnIndex) {
        T baseElement = matrix.get(baseRowIndex, baseColumnIndex).deepcopy();
        matrix.operateColumn(baseColumnIndex, d -> d.idivide(baseElement));
        int columnCount = matrix.getColumnCount();
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            if (columnIndex == baseColumnIndex) {
                continue;
            }
            T nextElement = matrix.get(baseRowIndex, columnIndex).negate();
            addColumn(matrix, columnIndex, baseColumnIndex, nextElement);
        }
        return matrix;
    }

    public static <T extends com.darkempire.math.struct.Number<T>> NumberMatrix<T> makeBaseColumn(NumberMatrix<T> matrix, int baseRowIndex, int baseColumnIndex) {
        T baseElement = matrix.get(baseRowIndex, baseColumnIndex).deepcopy();
        matrix.operateRow(baseRowIndex, d -> d.idivide(baseElement));
        //Log.log(Log.debugIndex,"Після ділення:\n",matrix);
        int rowCount = matrix.getRowCount();
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            if (rowIndex == baseRowIndex) {
                continue;
            }
            T nextElement = matrix.get(rowIndex, baseColumnIndex).negate();
            addRow(matrix, rowIndex, baseRowIndex, nextElement);
        }
        return matrix;
    }

}
