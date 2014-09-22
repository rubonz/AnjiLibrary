package com.darkempire.math.operator.matrix;

import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.math.exception.MatrixSizeException;
import com.darkempire.math.struct.matrix.LinearMatrix;

import static com.darkempire.math.operator.matrix.LinearMatrixMathOperator.addRow;

/**
 * Created by siredvin on 08.09.14.
 */
@AnjiUtil
public final class LinearMatrixTransformOperator {

    private LinearMatrixTransformOperator() {
    }

    //region Трикутні форми
    public static <T extends com.darkempire.math.struct.Number<T>> LinearMatrix<T> makeUpperTriangle(LinearMatrix<T> matrix) {
        return makeUpperTriangle(matrix, false);
    }

    public static <T extends com.darkempire.math.struct.Number<T>> LinearMatrix<T> makeUpperTriangle(LinearMatrix<T> matrix, boolean rowSwitchAllow) {
        int rowCount = matrix.getRowCount();
        int columnCount = matrix.getColumnCount();
        if (rowCount != columnCount) {
            throw new MatrixSizeException(MatrixSizeException.MATRIX_IS_NOT_SQUARE);
        }
        //Починаємо цикл
        T zero = matrix.get(0, 0).getZero();
        for (int index = 0; index < columnCount; index++) {
            T baseElement = matrix.get(index, index);
            if (!baseElement.equals(zero)) {//Гілка, якщо базовий елемент не нульовий
                for (int rowIndex = index + 1; rowIndex < rowCount; rowIndex++) {
                    T nextElement = matrix.get(rowIndex, index).divide(baseElement).inegate();
                    if (!nextElement.equals(zero)) {
                        addRow(matrix, rowIndex, index, nextElement);
                    }
                }
            } else {//Якщо базовий елемент таки нульовий
                if (rowSwitchAllow) {
                    int noZeroIndex = -1;
                    //Шукаємо, чи є ненульовий елемент у нижньому трикутнику матриці
                    for (int rowIndex = index + 1; rowIndex < rowCount; rowIndex++) {
                        if (!matrix.get(rowIndex, index).equals(zero)) {
                            noZeroIndex = rowIndex;
                            break;
                        }
                    }
                    if (noZeroIndex != -1) {//Якщо є такий ненульовий елемент, то потрібно щось робити
                    /*
                    Виправляємо це таким чином:
                    - Міняємо місцями поточний рядок та рядок з ненульовим елементом
                    - Виконаємо ітерацію очищення
                    - Профіт!
                     */
                        matrix.switchRows(noZeroIndex, index);
                        for (int rowIndex = index + 1; rowIndex < rowCount; rowIndex++) {
                            T nextElement = matrix.get(rowIndex, index).divide(baseElement).inegate();
                            if (!nextElement.equals(zero)) {
                                addRow(matrix, rowIndex, index, nextElement);
                            }
                        }
                    } else { //Ненульового елементу немає, тоді матрицю не можна зробити діагональную
                        return null;
                    }
                } else {
                    return null;
                }
            }
        }
        return matrix;
    }

    public static <T extends com.darkempire.math.struct.Number<T>> LinearMatrix<T> makeLowerTriangle(LinearMatrix<T> matrix) {
        return makeLowerTriangle(matrix, false);
    }

    public static <T extends com.darkempire.math.struct.Number<T>> LinearMatrix<T> makeLowerTriangle(LinearMatrix<T> matrix, boolean rowSwitchAllow) {
        int rowCount = matrix.getRowCount();
        int columnCount = matrix.getColumnCount();
        if (rowCount != columnCount) {
            throw new MatrixSizeException(MatrixSizeException.MATRIX_IS_NOT_SQUARE);
        }
        //Починаємо цикл
        T zero = matrix.get(0, 0).getZero();
        for (int index = columnCount - 1; index > -1; index--) {
            T baseElement = matrix.get(index, index);
            if (!baseElement.equals(zero)) {//Гілка, якщо базовий елемент не нульовий
                for (int rowIndex = 0; rowIndex < index; rowIndex++) {
                    T nextElement = matrix.get(rowIndex, index).divide(baseElement).inegate();
                    if (!nextElement.equals(zero)) {
                        addRow(matrix, rowIndex, index, nextElement);
                    }
                }
            } else {//Якщо базовий елемент таки нульовий
                if (rowSwitchAllow) {
                    int noZeroIndex = -1;
                    //Шукаємо, чи є ненульовий елемент у нижньому трикутнику матриці
                    for (int rowIndex = 0; rowIndex < index; rowIndex++) {
                        if (!matrix.get(rowIndex, index).equals(zero)) {
                            noZeroIndex = rowIndex;
                            break;
                        }
                    }
                    if (noZeroIndex != -1) {//Якщо є такий ненульовий елемент, то потрібно щось робити
                    /*
                    Виправляємо це таким чином:
                    - Міняємо місцями поточний рядок та рядок з ненульовим елементом
                    - Виконаємо ітерацію очищення
                    - Профіт!
                     */
                        matrix.switchRows(noZeroIndex, index);
                        for (int rowIndex = 0; rowIndex < index; rowIndex++) {
                            T nextElement = matrix.get(rowIndex, index).divide(baseElement).inegate();
                            if (!nextElement.equals(zero)) {
                                addRow(matrix, rowIndex, index, nextElement);
                            }
                        }
                    } else { //Ненульового елементу немає, тоді матрицю не можна зробити діагональную
                        return null;
                    }
                } else {
                    return null;
                }
            }
        }
        return matrix;
    }
    //endregion

    //region Діагональні форми
    public static <T extends com.darkempire.math.struct.Number<T>> LinearMatrix<T> makeDiagonalForm(LinearMatrix<T> matrix) {
        return makeDiagonalForm(matrix, false);
    }

    public static <T extends com.darkempire.math.struct.Number<T>> LinearMatrix<T> makeDiagonalForm(LinearMatrix<T> matrix, boolean rowSwitchAllow) {
        int rowCount = matrix.getRowCount();
        int columnCount = matrix.getColumnCount();
        if (rowCount != columnCount) {
            throw new MatrixSizeException(MatrixSizeException.MATRIX_IS_NOT_SQUARE);
        }
        //Починаємо цикл
        T zero = matrix.get(0, 0).getZero();
        for (int index = 0; index < columnCount; index++) {
            T baseElement = matrix.get(index, index);
            if (!baseElement.equals(zero)) {//Гілка, якщо базовий елемент не нульовий
                for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                    if (rowIndex == index)
                        continue;
                    T nextElement = matrix.get(rowIndex, index).divide(baseElement).inegate();
                    if (!nextElement.equals(zero)) {
                        addRow(matrix, rowIndex, index, nextElement);
                    }
                }
            } else {//Якщо базовий елемент таки нульовий
                if (rowSwitchAllow) {
                    int noZeroIndex = -1;
                    //Шукаємо, чи є ненульовий елемент у нижньому трикутнику матриці
                    for (int rowIndex = index + 1; rowIndex < rowCount; rowIndex++) {
                        if (!matrix.get(rowIndex, index).equals(zero)) {
                            noZeroIndex = rowIndex;
                            break;
                        }
                    }
                    if (noZeroIndex != -1) {//Якщо є такий ненульовий елемент, то потрібно щось робити
                    /*
                    Виправляємо це таким чином:
                    - Міняємо місцями поточний рядок та рядок з ненульовим елементом
                    - Виконаємо ітерацію очищення
                    - Профіт!
                     */
                        matrix.switchRows(noZeroIndex, index);
                        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                            if (rowIndex == index)
                                continue;
                            T nextElement = matrix.get(rowIndex, index).divide(baseElement).inegate();
                            if (!nextElement.equals(zero)) {
                                addRow(matrix, rowIndex, index, nextElement);
                            }
                        }
                    } else { //Ненульового елементу немає, тоді матрицю не можна зробити діагональную
                        return null;
                    }
                } else {
                    return null;
                }
            }
        }
        return matrix;
    }
    //endregion

    //region Заміна місцями стовпчик або рядок
    @Deprecated
    public static <T extends com.darkempire.math.struct.Number<T>> LinearMatrix<T> switchRow(LinearMatrix<T> matrix, int rowIndex1, int rowIndex2) {
        return matrix.switchRows(rowIndex1, rowIndex2);
    }

    @Deprecated
    public static <T extends com.darkempire.math.struct.Number<T>> LinearMatrix<T> switchColumn(LinearMatrix<T> matrix, int columnIndex1, int columnIndex2) {
        return matrix.switchColumns(columnIndex1, columnIndex2);
    }
    //endregion
}
