package com.darkempire.math.operator.matrix;

import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.anji.log.Log;
import com.darkempire.math.exception.MatrixSizeException;
import com.darkempire.math.struct.matrix.DoubleFixedMatrix;
import com.darkempire.math.struct.matrix.DoubleMatrix;
import com.darkempire.math.struct.matrix.DoubleResizeMatrix;
import com.darkempire.math.struct.vector.DoubleVector;

import static com.darkempire.math.operator.matrix.DoubleMatrixMathOperator.addRow;

/**
 * Created by siredvin on 08.09.14.
 */
@AnjiUtil
public final class DoubleMatrixTransformOperator {

    private DoubleMatrixTransformOperator() {
    }

    //region Трикутні форми
    public static DoubleMatrix makeUpperTriangle(DoubleMatrix matrix) {
        return makeUpperTriangle(matrix, false);
    }

    public static DoubleMatrix makeUpperTriangle(DoubleMatrix matrix, boolean rowSwitchAllow) {
        int rowCount = matrix.getRowCount();
        int columnCount = matrix.getColumnCount();
        if (rowCount != columnCount) {
            throw new MatrixSizeException(MatrixSizeException.MATRIX_IS_NOT_SQUARE);
        }
        //Починаємо цикл
        for (int index = 0; index < columnCount; index++) {
            double baseElement = matrix.get(index, index);
            if (baseElement != 0) {//Гілка, якщо базовий елемент не нульовий
                for (int rowIndex = index + 1; rowIndex < rowCount; rowIndex++) {
                    double nextElement = -matrix.get(rowIndex, index) / baseElement;
                    if (nextElement != 0) {
                        addRow(matrix, rowIndex, index, nextElement);
                    }
                }
            } else {//Якщо базовий елемент таки нульовий
                if (rowSwitchAllow) {
                    int noZeroIndex = -1;
                    //Шукаємо, чи є ненульовий елемент у нижньому трикутнику матриці
                    for (int rowIndex = index + 1; rowIndex < rowCount; rowIndex++) {
                        if (matrix.get(rowIndex, index) != 0) {
                            noZeroIndex = rowIndex;
                            break;
                        }
                    }
                    if (noZeroIndex != -1) {//Якщо є такий ненульовий елемент, то потрібно щось робити
                    /*
                    Виправляємо це таким чином:
                    - Міняємо місцями поточний рядок та рядок з ненульовим елементом
                    - Замінюємо baseElement на потрібний!
                    - Виконаємо ітерацію очищення
                    - Профіт!
                     */
                        matrix.switchRows(noZeroIndex, index);
                        baseElement = matrix.get(index, index);
                        for (int rowIndex = index + 1; rowIndex < rowCount; rowIndex++) {
                            double nextElement = -matrix.get(rowIndex, index) / baseElement;
                            if (nextElement != 0) {
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

    public static DoubleMatrix makeLowerTriangle(DoubleMatrix matrix) {
        return makeLowerTriangle(matrix, false);
    }

    public static DoubleMatrix makeLowerTriangle(DoubleMatrix matrix, boolean rowSwitchAllow) {
        int rowCount = matrix.getRowCount();
        int columnCount = matrix.getColumnCount();
        if (rowCount != columnCount) {
            throw new MatrixSizeException(MatrixSizeException.MATRIX_IS_NOT_SQUARE);
        }
        //Починаємо цикл
        for (int index = columnCount - 1; index > -1; index--) {
            double baseElement = matrix.get(index, index);
            if (baseElement != 0) {//Гілка, якщо базовий елемент не нульовий
                for (int rowIndex = 0; rowIndex < index; rowIndex++) {
                    double nextElement = -matrix.get(rowIndex, index) / baseElement;
                    if (nextElement != 0) {
                        addRow(matrix, rowIndex, index, nextElement);
                    }
                }
            } else {//Якщо базовий елемент таки нульовий
                if (rowSwitchAllow) {
                    int noZeroIndex = -1;
                    //Шукаємо, чи є ненульовий елемент у нижньому трикутнику матриці
                    for (int rowIndex = 0; rowIndex < index; rowIndex++) {
                        if (matrix.get(rowIndex, index) != 0) {
                            noZeroIndex = rowIndex;
                            break;
                        }
                    }
                    if (noZeroIndex != -1) {//Якщо є такий ненульовий елемент, то потрібно щось робити
                    /*
                    Виправляємо це таким чином:
                    - Міняємо місцями поточний рядок та рядок з ненульовим елементом
                    - Заміюємо baseElement на потрібний!
                    - Виконаємо ітерацію очищення
                    - Профіт!
                     */
                        matrix.switchRows(noZeroIndex, index);
                        baseElement = matrix.get(index, index);
                        for (int rowIndex = 0; rowIndex < index; rowIndex++) {
                            double nextElement = -matrix.get(rowIndex, index) / baseElement;
                            if (nextElement != 0) {
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
    public static DoubleMatrix makeUpperTrapeze(DoubleMatrix matrix) {
        return makeUpperTrapeze(matrix, false);
    }

    public static DoubleMatrix makeUpperTrapeze(DoubleMatrix matrix, boolean switchAllow) {
        int rowCount = matrix.getRowCount();
        int columnCount = matrix.getColumnCount();
        int size = Math.min(rowCount, columnCount);
        //Починаємо цикл
        for (int index = 0; index < size; index++) {
            double baseElement = matrix.get(index, index);
            if (baseElement != 0) {//Гілка, якщо базовий елемент не нульовий
                for (int rowIndex = index + 1; rowIndex < rowCount; rowIndex++) {
                    double nextElement = -matrix.get(rowIndex, index) / baseElement;
                    if (nextElement != 0) {
                        addRow(matrix, rowIndex, index, nextElement);
                    }
                }
            } else {//Якщо базовий елемент таки нульовий
                if (switchAllow) {
                    int noZeroRowIndex = -1;
                    int noZeroColumnIndex = -1;
                    //Шукаємо, чи є ненульовий елемент у нижньому трикутнику матриці
                    for (int rowIndex = index + 1; rowIndex < rowCount; rowIndex++) {
                        for (int columnIndex = index + 1; columnIndex < columnCount; columnIndex++) {
                            if (matrix.get(rowIndex, columnIndex) != 0) {
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
                            double nextElement = -matrix.get(rowIndex, index) / baseElement;
                            if (nextElement != 0) {
                                addRow(matrix, rowIndex, index, nextElement);
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
            matrix.fillRow(rowIndex, 0);
        }
        return matrix;
    }
    //endregion

    //region Діагональні форми
    public static DoubleMatrix makeDiagonalForm(DoubleMatrix matrix) {
        return makeDiagonalForm(matrix, false);
    }

    public static DoubleMatrix makeDiagonalForm(DoubleMatrix matrix, boolean rowSwitchAllow) {
        int rowCount = matrix.getRowCount();
        int columnCount = matrix.getColumnCount();
        if (rowCount != columnCount) {
            throw new MatrixSizeException(MatrixSizeException.MATRIX_IS_NOT_SQUARE);
        }
        //Починаємо цикл
        for (int index = 0; index < columnCount; index++) {
            double baseElement = matrix.get(index, index);
            if (baseElement != 0) {//Гілка, якщо базовий елемент не нульовий
                for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                    if (rowIndex == index)
                        continue;
                    double nextElement = -matrix.get(rowIndex, index) / baseElement;
                    if (nextElement != 0) {
                        addRow(matrix, rowIndex, index, nextElement);
                    }
                }
            } else {//Якщо базовий елемент таки нульовий
                if (rowSwitchAllow) {
                    int noZeroIndex = -1;
                    //Шукаємо, чи є ненульовий елемент у нижньому трикутнику матриці
                    for (int rowIndex = index + 1; rowIndex < rowCount; rowIndex++) {
                        if (matrix.get(rowIndex, index) != 0) {
                            noZeroIndex = rowIndex;
                            break;
                        }
                    }
                    if (noZeroIndex != -1) {//Якщо є такий ненульовий елемент, то потрібно щось робити
                    /*
                    Виправляємо це таким чином:
                    - Міняємо місцями поточний рядок та рядок з ненульовим елементом
                    - Замінюємо baseElement на потрібний!
                    - Виконаємо ітерацію очищення
                    - Профіт!
                     */
                        matrix.switchRows(noZeroIndex, index);
                        baseElement = matrix.get(index, index);
                        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                            if (rowIndex == index)
                                continue;
                            double nextElement = -matrix.get(rowIndex, index) / baseElement;
                            if (nextElement != 0) {
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

    //region Додавання та видалення рядків
    public static DoubleFixedMatrix appendRowF(DoubleMatrix m1, double[] row) {
        if (row.length != m1.getColumnCount())
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MISMATCH);
        int n = m1.getColumnCount();
        int m = m1.getRowCount();
        double[] array = new double[n * (m + 1)];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                array[j + i * n] = m1.getEl(i, j);
            }
        }
        System.arraycopy(row, 0, array, m * n, n);
        return DoubleFixedMatrix.createInstance(m + 1, n, array);
    }

    public static DoubleFixedMatrix appendRowF(DoubleMatrix m1, DoubleVector row) {
        if (row.getSize() != m1.getColumnCount())
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MISMATCH);
        int columnCount = m1.getColumnCount();
        int rowCount = m1.getRowCount();
        double[] array = new double[columnCount * (rowCount + 1)];
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                array[columnIndex + rowIndex * columnCount] = m1.getEl(rowIndex, columnIndex);
            }
        }
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            array[columnIndex + rowCount * columnCount] = row.get(columnIndex);
        }
        return DoubleFixedMatrix.createInstance(rowCount + 1, columnCount, array);
    }

    public static DoubleFixedMatrix removeRowF(DoubleMatrix m1, int indexR) {
        if (m1.getRowCount() < indexR - 1 || indexR < 0)
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MISMATCH);
        int n = m1.getColumnCount();
        int m = m1.getRowCount();
        double[] array = new double[n * (m - 1)];
        DoubleFixedMatrix temp = DoubleFixedMatrix.createInstance(m - 1, n, array);
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

    public static DoubleResizeMatrix appendRowR(DoubleMatrix m1, double[] row) {
        if (row.length != m1.getColumnCount())
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MISMATCH);
        int n = m1.getColumnCount();
        int m = m1.getRowCount();
        double[] array = new double[n * (m + 1)];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                array[j + i * n] = m1.getEl(i, j);
            }
        }
        System.arraycopy(row, 0, array, m * n, n);
        return DoubleResizeMatrix.createInstance(m + 1, n, array);
    }

    public static DoubleResizeMatrix appendRowR(DoubleMatrix m1, DoubleVector row) {
        if (row.getSize() != m1.getColumnCount())
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MISMATCH);
        int columnCount = m1.getColumnCount();
        int rowCount = m1.getRowCount();
        double[] array = new double[columnCount * (rowCount + 1)];
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                array[columnIndex + rowIndex * columnCount] = m1.getEl(rowIndex, columnIndex);
            }
        }
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            array[columnIndex + rowCount * columnCount] = row.get(columnIndex);
        }
        return DoubleResizeMatrix.createInstance(rowCount + 1, columnCount, array);
    }


    public static DoubleResizeMatrix removeRowR(DoubleMatrix m1, int indexR) {
        if (m1.getRowCount() < indexR - 1 || indexR < 0)
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MISMATCH);
        int n = m1.getColumnCount();
        int m = m1.getRowCount();
        double[] array = new double[n * (m - 1)];
        DoubleResizeMatrix temp = DoubleResizeMatrix.createInstance(m - 1, n, array);
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
    //endregion

    //region Видалення та додавання стовпчиків
    public static DoubleFixedMatrix appendColumnF(DoubleMatrix m1, double[] column) {
        if (column.length != m1.getRowCount())
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MISMATCH);
        int n = m1.getColumnCount();
        int m = m1.getRowCount();
        double[] array = new double[(n + 1) * m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                array[j + i * (n + 1)] = m1.getEl(i, j);
            }
        }
        for (int j = 0; j < m; j++) {
            array[n + j * (n + 1)] = column[j];
        }
        return DoubleFixedMatrix.createInstance(m, n + 1, array);
    }

    public static DoubleFixedMatrix appendColumnF(DoubleMatrix m1, DoubleVector column) {
        if (column.getSize() != m1.getRowCount())
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MISMATCH);
        int columnCount = m1.getColumnCount();
        int rowCount = m1.getRowCount();
        double[] array = new double[(columnCount + 1) * rowCount];
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                array[columnIndex + rowIndex * (columnCount + 1)] = m1.getEl(rowIndex, columnIndex);
            }
        }
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            array[columnCount + rowIndex * (columnCount + 1)] = column.get(rowIndex);
        }
        return DoubleFixedMatrix.createInstance(rowCount, columnCount + 1, array);
    }

    public static DoubleFixedMatrix removeColumnF(DoubleMatrix m1, int indexC) {
        if (m1.getColumnCount() < indexC - 1 || indexC < 0)
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MISMATCH);
        int n = m1.getColumnCount();
        int m = m1.getRowCount();
        double[] array = new double[(n - 1) * m];
        DoubleFixedMatrix temp = DoubleFixedMatrix.createInstance(m, n - 1, array);
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

    public static DoubleResizeMatrix appendColumnR(DoubleMatrix m1, double[] column) {
        if (column.length != m1.getRowCount())
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MISMATCH);
        int n = m1.getColumnCount();
        int m = m1.getRowCount();
        double[] array = new double[(n + 1) * m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                array[j + i * (n + 1)] = m1.getEl(i, j);
            }
        }
        for (int j = 0; j < m; j++) {
            array[n + j * (n + 1)] = column[j];
        }
        return DoubleResizeMatrix.createInstance(m, n + 1, array);
    }

    public static DoubleResizeMatrix appendColumnR(DoubleMatrix m1, DoubleVector column) {
        if (column.getSize() != m1.getRowCount())
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MISMATCH);
        int columnCount = m1.getColumnCount();
        int rowCount = m1.getRowCount();
        double[] array = new double[(columnCount + 1) * rowCount];
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                array[columnIndex + rowIndex * (columnCount + 1)] = m1.getEl(rowIndex, columnIndex);
            }
        }
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            array[columnCount + rowIndex * (columnCount + 1)] = column.get(rowIndex);
        }
        return DoubleResizeMatrix.createInstance(rowCount, columnCount + 1, array);
    }

    public static DoubleResizeMatrix removeColumnR(DoubleMatrix m1, int indexC) {
        if (m1.getColumnCount() < indexC - 1 || indexC < 0)
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MISMATCH);
        int n = m1.getColumnCount();
        int m = m1.getRowCount();
        double[] array = new double[(n - 1) * m];
        DoubleResizeMatrix temp = DoubleResizeMatrix.createInstance(m, n - 1, array);
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
    //endregion
}
