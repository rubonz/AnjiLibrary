package com.darkempire.math.struct.vector;

import com.darkempire.math.struct.Number;
import com.darkempire.math.struct.matrix.NumberMatrix;

/**
 * Create in 11:51
 * Created by siredvin on 25.04.14.
 * За замовучанням створюються нові ResizeLinearVector
 */
public abstract class LinearMatrixVector<T extends com.darkempire.math.struct.Number<T>> extends LinearVector<T> {
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

    @Override
    public T scalar(LinearVector<T> vector) {
        int size = Math.min(getSize(), vector.getSize());
        T temp = get(0).multiply(vector.get(0));
        for (int i = 1; i < size; i++) {
            temp.iadd(get(i).multiply(vector.get(i)));
        }
        return temp;
    }

    //region Арифметичні операції з присвоєнням
    @Override
    public LinearVector<T> inegate() {
        int size = getSize();
        for (int i = 0; i < size; i++) {
            get(i).inegate();
        }
        return this;
    }

    @Override
    public LinearVector<T> iadd(LinearVector<T> vector) {
        int size = Math.max(getSize(), vector.getSize());
        for (int i = 0; i < size; i++) {
            get(i).iadd(vector.get(i));
        }
        return this;
    }

    @Override
    public LinearVector<T> isubtract(LinearVector<T> vector) {
        int size = Math.max(getSize(), vector.getSize());
        for (int i = 0; i < size; i++) {
            get(i).isubtract(vector.get(i));
        }
        return this;
    }
    //endregion

    //region Арифметичні операції
    @Override
    public LinearVector<T> add(LinearVector<T> vector) {
        int size = Math.max(getSize(), vector.getSize());
        T[] p = (T[]) new Number[size];
        for (int i = 0; i < size; i++) {
            p[i] = get(i).add(vector.get(i));
        }
        return new LinearResizeVector<>(p);
    }

    @Override
    public LinearVector<T> subtract(LinearVector<T> vector) {
        int size = Math.max(getSize(), vector.getSize());
        T[] p = (T[]) new Number[size];
        for (int i = 0; i < size; i++) {
            p[i] = get(i).subtract(vector.get(i));
        }
        return new LinearResizeVector<T>(p);
    }

    @Override
    public LinearVector<T> negate() {
        T[] p = (T[]) new Number[getSize()];
        for (int i = 0; i < p.length; i++) {
            p[i] = get(i).negate();
        }
        return new LinearResizeVector<>(p);
    }
    //endregion

    private static class LinearMatrixRow<T extends Number<T>> extends LinearMatrixVector<T> {
        private NumberMatrix<T> matrix;
        private int rowIndex;

        //region Конструктор
        private LinearMatrixRow(int rowIndex, NumberMatrix<T> matrix) {
            this.matrix = matrix;
            this.rowIndex = rowIndex;
        }
        //endregion

        //region Сеттери
        @Override
        public void set(int index, T value) {
            matrix.set(rowIndex, index, value);
        }
        //endregion

        //region Отримання підвекторів
        @Override
        public LinearVector<T> subvector(int length) {
            Number[] arr = new Number[length];
            for (int i = 0; i < length; i++) {
                arr[i] = get(i);
            }
            return new LinearResizeVector<T>((T[]) arr);
        }

        @Override
        public LinearVector<T> subvector(int startIndex, int length) {
            Number[] arr = new Number[length];
            int max = startIndex + length;
            for (int i = startIndex; i < max; i++) {
                arr[i] = get(i);
            }
            return new LinearResizeVector<T>((T[]) arr);
        }
        //endregion

        //region Геттери
        @Override
        public T get(int index) {
            return matrix.get(rowIndex, index);
        }

        @Override
        public int getSize() {
            return matrix.getColumnCount();
        }
        //endregion

        @Override
        public LinearVector clone() {
            int length = matrix.getColumnCount();
            Number[] arr = new Number[length];
            for (int i = 0; i < length; i++) {
                arr[i] = get(i).deepcopy();
            }
            return new LinearResizeVector<T>((T[]) arr);
        }
    }

    private static class LinearMatrixColumn<T extends Number<T>> extends LinearMatrixVector<T> {
        private NumberMatrix<T> matrix;
        private int columnIndex;

        //region Конструктор
        private LinearMatrixColumn(int columnIndex, NumberMatrix<T> matrix) {
            this.matrix = matrix;
            this.columnIndex = columnIndex;
        }
        //endregion

        //region Сеттер
        @Override
        public void set(int index, T value) {
            matrix.set(index, columnIndex, value);
        }
        //endregion

        //region Отримання підвекторів
        @Override
        public LinearVector<T> subvector(int length) {
            Number[] arr = new Number[length];
            for (int i = 0; i < length; i++) {
                arr[i] = get(i);
            }
            return new LinearResizeVector<T>((T[]) arr);
        }

        @Override
        public LinearVector<T> subvector(int startIndex, int length) {
            Number[] arr = new Number[length];
            int max = startIndex + length;
            for (int i = startIndex; i < max; i++) {
                arr[i] = get(i);
            }
            return new LinearResizeVector<T>((T[]) arr);
        }
        //endregion

        //region Геттери
        @Override
        public T get(int index) {
            return matrix.get(index, columnIndex);
        }

        @Override
        public int getSize() {
            return matrix.getRowCount();
        }
        //endregion

        @Override
        public LinearVector clone() {
            int length = matrix.getRowCount();
            Number[] arr = new Number[length];
            for (int i = 0; i < length; i++) {
                arr[i] = get(i).deepcopy();
            }
            return new LinearResizeVector<T>((T[]) arr);
        }
    }

    //region Отримання частин
    public static <T extends Number<T>> LinearVector<T> row(int rowIndex, NumberMatrix<T> matrix) {
        return new LinearMatrixRow<T>(rowIndex, matrix);
    }

    public static <T extends Number<T>> LinearVector<T> column(int columnIndex, NumberMatrix<T> matrix) {
        return new LinearMatrixColumn<T>(columnIndex, matrix);
    }
    //endregion
}
