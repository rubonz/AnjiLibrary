package com.darkempire.math.struct.vector;

import com.darkempire.math.struct.Number;
import com.darkempire.math.struct.matrix.Matrix;

/**
 * Create in 11:51
 * Created by siredvin on 25.04.14.
 * За замовучанням створюються нові ResizeLinearVector
 */
public abstract class MatrixVector<T> extends Vector<T> {
    //region Сеттери
    @Override
    public abstract void set(int index, T value);
    //endregion

    //region Геттери
    @Override
    public abstract T get(int index);

    @Override
    public abstract int getSize();
    //endregion


    private static class MatrixRow<T> extends MatrixVector<T> {
        private Matrix<T> matrix;
        private int rowIndex;

        //region Конструктор
        private MatrixRow(int rowIndex, Matrix<T> matrix) {
            this.matrix = matrix;
            this.rowIndex = rowIndex;
        }
        //endregion

        //region Сеттер
        @Override
        public void set(int index, T value) {
            matrix.setEl(rowIndex, index, value);
        }
        //endregion

        //region Отримання підвекторів
        @Override
        public Vector<T> subvector(int length) {
            Object[] arr = new Object[length];
            for (int i = 0; i < length; i++) {
                arr[i] = get(i);
            }
            return new FixedVector<>((T[]) arr);
        }

        @Override
        public Vector<T> subvector(int startIndex, int length) {
            Object[] arr = new Object[length];
            int max = startIndex + length;
            for (int i = startIndex; i < max; i++) {
                arr[i] = get(i);
            }
            return new FixedVector<>((T[]) arr);
        }
        //endregion

        //region Геттери
        @Override
        public T get(int index) {
            return matrix.getEl(rowIndex, index);
        }

        @Override
        public int getSize() {
            return matrix.getColumnCount();
        }
        //endregion
    }

    private static class MatrixColumn<T> extends MatrixVector<T> {
        private Matrix<T> matrix;
        private int columnIndex;

        //region Конструктор
        private MatrixColumn(int columnIndex, Matrix<T> matrix) {
            this.matrix = matrix;
            this.columnIndex = columnIndex;
        }
        //endregion

        //region Сеттери
        @Override
        public void set(int index, T value) {
            matrix.setEl(index, columnIndex, value);
        }
        //endregion

        //region Отримання підвекторів
        @Override
        public Vector<T> subvector(int length) {
            Object[] arr = new Object[length];
            for (int i = 0; i < length; i++) {
                arr[i] = get(i);
            }
            return new FixedVector<T>((T[]) arr);
        }

        @Override
        public Vector<T> subvector(int startIndex, int length) {
            Object[] arr = new Number[length];
            int max = startIndex + length;
            for (int i = startIndex; i < max; i++) {
                arr[i] = get(i);
            }
            return new FixedVector<>((T[]) arr);
        }
        //endregion

        //region Геттери
        @Override
        public T get(int index) {
            return matrix.getEl(index, columnIndex);
        }

        @Override
        public int getSize() {
            return matrix.getRowCount();
        }
        //endregion
    }

    public static <T> Vector<T> row(int rowIndex, Matrix<T> matrix) {
        return new MatrixRow<T>(rowIndex, matrix);
    }

    public static <T> Vector<T> column(int columnIndex, Matrix<T> matrix) {
        return new MatrixColumn<T>(columnIndex, matrix);
    }
}
