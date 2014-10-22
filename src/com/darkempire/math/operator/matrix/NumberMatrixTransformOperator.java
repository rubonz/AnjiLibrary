package com.darkempire.math.operator.matrix;

import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.anji.log.Log;
import com.darkempire.math.exception.MatrixSizeException;
import com.darkempire.math.struct.matrix.NumberMatrix;

import static com.darkempire.math.operator.matrix.NumberMatrixMathOperator.addRow;

/**
 * Created by siredvin on 08.09.14.
 */
@AnjiUtil
public final class NumberMatrixTransformOperator {
    //TODO: не забути додати у трикутні форми переставлення стовпчиків. І ще у DoubleMatrix також
    private NumberMatrixTransformOperator() {
    }

    //region Трикутні форми
    public static <T extends com.darkempire.math.struct.Number<T>> NumberMatrix<T> makeUpperTriangle(NumberMatrix<T> matrix) {
        return makeUpperTriangle(matrix, false);
    }

    public static <T extends com.darkempire.math.struct.Number<T>> NumberMatrix<T> makeUpperTriangle(NumberMatrix<T> matrix, boolean rowSwitchAllow) {
        int rowCount = matrix.getRowCount();
        int columnCount = matrix.getColumnCount();
        if (rowCount != columnCount) {
            throw new MatrixSizeException(MatrixSizeException.MATRIX_IS_NOT_SQUARE);
        }
        //Починаємо цикл
        T zero = matrix.get(0, 0).getZero();
        for (int index = 0; index < columnCount; index++) {
            T baseElement = matrix.get(index, index);
            if (baseElement.compareTo(zero) != 0) {//Гілка, якщо базовий елемент не нульовий
                for (int rowIndex = index + 1; rowIndex < rowCount; rowIndex++) {
                    T nextElement = matrix.get(rowIndex, index).divide(baseElement).inegate();
                    if (nextElement.compareTo(zero) != 0) {
                        addRow(matrix, rowIndex, index, nextElement);
                    }
                }
            } else {//Якщо базовий елемент таки нульовий
                if (rowSwitchAllow) {
                    int noZeroIndex = -1;
                    //Шукаємо, чи є ненульовий елемент у нижньому трикутнику матриці
                    for (int rowIndex = index + 1; rowIndex < rowCount; rowIndex++) {
                        if (matrix.get(rowIndex, index).compareTo(zero) != 0) {
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
                            if (nextElement.compareTo(zero) != 0) {
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

    public static <T extends com.darkempire.math.struct.Number<T>> NumberMatrix<T> makeLowerTriangle(NumberMatrix<T> matrix) {
        return makeLowerTriangle(matrix, false);
    }

    public static <T extends com.darkempire.math.struct.Number<T>> NumberMatrix<T> makeLowerTriangle(NumberMatrix<T> matrix, boolean rowSwitchAllow) {
        int rowCount = matrix.getRowCount();
        int columnCount = matrix.getColumnCount();
        if (rowCount != columnCount) {
            throw new MatrixSizeException(MatrixSizeException.MATRIX_IS_NOT_SQUARE);
        }
        //Починаємо цикл
        T zero = matrix.get(0, 0).getZero();
        for (int index = columnCount - 1; index > -1; index--) {
            T baseElement = matrix.get(index, index);
            if (baseElement.compareTo(zero) != 0) {//Гілка, якщо базовий елемент не нульовий
                for (int rowIndex = 0; rowIndex < index; rowIndex++) {
                    T nextElement = matrix.get(rowIndex, index).divide(baseElement).inegate();
                    if (nextElement.compareTo(zero) != 0) {
                        addRow(matrix, rowIndex, index, nextElement);
                    }
                }
            } else {//Якщо базовий елемент таки нульовий
                if (rowSwitchAllow) {
                    int noZeroIndex = -1;
                    //Шукаємо, чи є ненульовий елемент у нижньому трикутнику матриці
                    for (int rowIndex = 0; rowIndex < index; rowIndex++) {
                        if (matrix.get(rowIndex, index).compareTo(zero) != 0) {
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
                            if (nextElement.compareTo(zero) != 0) {
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

    //region Трапецієвидні форми
    public static <T extends com.darkempire.math.struct.Number<T>> NumberMatrix<T> makeUpperTrapeze(NumberMatrix<T> matrix) {
        return makeUpperTrapeze(matrix, false);
    }

    public static <T extends com.darkempire.math.struct.Number<T>> NumberMatrix<T> makeUpperTrapeze(NumberMatrix<T> matrix, boolean switchAllow) {
        int rowCount = matrix.getRowCount();
        int columnCount = matrix.getColumnCount();
        int size = Math.min(rowCount, columnCount);
        T zero = matrix.get(0, 0).getZero();
        //Починаємо цикл
        for (int index = 0; index < size; index++) {
            T baseElement = matrix.get(index, index);
            if (baseElement.compareTo(zero) != 0) {//Гілка, якщо базовий елемент не нульовий
                for (int rowIndex = index + 1; rowIndex < rowCount; rowIndex++) {
                    T nextElement = matrix.get(rowIndex, index).divide(baseElement).inegate();
                    if (nextElement.compareTo(zero) != 0) {
                        NumberMatrixMathOperator.addRow(matrix, rowIndex, index, nextElement);
                    }
                }
            } else {//Якщо базовий елемент таки нульовий
                if (switchAllow) {
                    int noZeroRowIndex = -1;
                    int noZeroColumnIndex = -1;
                    //Шукаємо, чи є ненульовий елемент у нижньому трикутнику матриці
                    for (int rowIndex = index + 1; rowIndex < rowCount; rowIndex++) {
                        for (int columnIndex = index + 1; columnIndex < columnCount; columnIndex++) {
                            if (matrix.get(rowIndex, columnIndex).compareTo(zero) != 0) {
                                noZeroRowIndex = rowIndex;
                                noZeroColumnIndex = columnIndex;
                                break;
                            }
                        }
                    }
                    if (noZeroRowIndex != -1) {//Якщо є такий ненульовий елемент, то потрібно щось робити
                    /*
                    Виправляємо це таким чином:
                    - Міняємо місцями поточний рядок та рядок з ненульовим елементом
                    - Замінюємо baseElement на потрібний!
                    - Виконаємо ітерацію очищення
                    - Профіт!
                     */
                        matrix.switchRows(noZeroRowIndex, index);
                        matrix.switchColumns(noZeroColumnIndex, index);
                        baseElement = matrix.get(index, index);
                        for (int rowIndex = index + 1; rowIndex < rowCount; rowIndex++) {
                            T nextElement = matrix.get(rowIndex, index).divide(baseElement).inegate();
                            if (nextElement.compareTo(zero) != 0) {
                                NumberMatrixMathOperator.addRow(matrix, rowIndex, index, nextElement);
                            }
                        }
                    } else { //Ненульового елементу немає, тоді матриця вже трапецїєвидної форми
                        return matrix;
                    }
                } else {
                    return null; // Не можна міняти місцями рядки, тобто привести до трапецієвидної форми за
                    //таких обмежень неможливо
                }
            }
        }
        for (int rowIndex = size; rowIndex < rowCount; rowIndex++) {
            Log.log(Log.debugIndex, rowIndex);
            matrix.fillRow(rowIndex, zero.deepcopy());
        }
        return matrix;
    }
    //endregion

    //region Діагональні форми
    public static <T extends com.darkempire.math.struct.Number<T>> NumberMatrix<T> makeDiagonalForm(NumberMatrix<T> matrix) {
        return makeDiagonalForm(matrix, false);
    }

    public static <T extends com.darkempire.math.struct.Number<T>> NumberMatrix<T> makeDiagonalForm(NumberMatrix<T> matrix, boolean rowSwitchAllow) {
        int rowCount = matrix.getRowCount();
        int columnCount = matrix.getColumnCount();
        if (rowCount != columnCount) {
            throw new MatrixSizeException(MatrixSizeException.MATRIX_IS_NOT_SQUARE);
        }
        //Починаємо цикл
        T zero = matrix.get(0, 0).getZero();
        for (int index = 0; index < columnCount; index++) {
            T baseElement = matrix.get(index, index);
            if (baseElement.compareTo(zero) != 0) {//Гілка, якщо базовий елемент не нульовий
                for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                    if (rowIndex == index)
                        continue;
                    T nextElement = matrix.get(rowIndex, index).divide(baseElement).inegate();
                    if (nextElement.compareTo(zero) != 0) {
                        addRow(matrix, rowIndex, index, nextElement);
                    }
                }
            } else {//Якщо базовий елемент таки нульовий
                if (rowSwitchAllow) {
                    int noZeroIndex = -1;
                    //Шукаємо, чи є ненульовий елемент у нижньому трикутнику матриці
                    for (int rowIndex = index + 1; rowIndex < rowCount; rowIndex++) {
                        if (matrix.get(rowIndex, index).compareTo(zero) != 0) {
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
                            if (nextElement.compareTo(zero) != 0) {
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
}
