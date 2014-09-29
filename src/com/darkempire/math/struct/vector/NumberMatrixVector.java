package com.darkempire.math.struct.vector;

import com.darkempire.math.struct.Number;
import com.darkempire.math.struct.matrix.NumberMatrix;

/**
 * Create in 11:51
 * Created by siredvin on 25.04.14.
 * За замовучанням створюються нові ResizeLinearVector
 */
public abstract class NumberMatrixVector<T extends com.darkempire.math.struct.Number<T>> extends NumberVector<T> {
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
    public NumberVector<T> deepcopy() {
        return clone();
    }

    @Override
    public T scalar(NumberVector<T> vector) {
        int size = Math.min(getSize(), vector.getSize());
        T temp = get(0).multiply(vector.get(0));
        for (int i = 1; i < size; i++) {
            temp.iadd(get(i).multiply(vector.get(i)));
        }
        return temp;
    }

    //region Арифметичні операції з присвоєнням
    @Override
    public NumberVector<T> inegate() {
        int size = getSize();
        for (int i = 0; i < size; i++) {
            get(i).inegate();
        }
        return this;
    }

    @Override
    public NumberVector<T> iadd(NumberVector<T> vector) {
        int size = Math.max(getSize(), vector.getSize());
        for (int i = 0; i < size; i++) {
            get(i).iadd(vector.get(i));
        }
        return this;
    }

    @Override
    public NumberVector<T> isubtract(NumberVector<T> vector) {
        int size = Math.max(getSize(), vector.getSize());
        for (int i = 0; i < size; i++) {
            get(i).isubtract(vector.get(i));
        }
        return this;
    }
    //endregion

    //region Арифметичні операції
    @Override
    public NumberVector<T> add(NumberVector<T> vector) {
        int size = Math.max(getSize(), vector.getSize());
        T[] p = (T[]) new Number[size];
        for (int i = 0; i < size; i++) {
            p[i] = get(i).add(vector.get(i));
        }
        return new NumberResizeVector<>(p);
    }

    @Override
    public NumberVector<T> subtract(NumberVector<T> vector) {
        int size = Math.max(getSize(), vector.getSize());
        T[] p = (T[]) new Number[size];
        for (int i = 0; i < size; i++) {
            p[i] = get(i).subtract(vector.get(i));
        }
        return new NumberResizeVector<T>(p);
    }

    @Override
    public NumberVector<T> negate() {
        T[] p = (T[]) new Number[getSize()];
        for (int i = 0; i < p.length; i++) {
            p[i] = get(i).negate();
        }
        return new NumberResizeVector<>(p);
    }
    //endregion

    private static class NumberMatrixRow<T extends Number<T>> extends NumberMatrixVector<T> {
        private NumberMatrix<T> matrix;
        private int rowIndex;

        //region Конструктор
        private NumberMatrixRow(int rowIndex, NumberMatrix<T> matrix) {
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
        public NumberVector<T> subvector(int length) {
            Number[] arr = new Number[length];
            for (int i = 0; i < length; i++) {
                arr[i] = get(i);
            }
            return new NumberResizeVector<T>((T[]) arr);
        }

        @Override
        public NumberVector<T> subvector(int startIndex, int length) {
            Number[] arr = new Number[length];
            int max = startIndex + length;
            for (int i = startIndex; i < max; i++) {
                arr[i] = get(i);
            }
            return new NumberResizeVector<T>((T[]) arr);
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
        public NumberVector clone() {
            int length = matrix.getColumnCount();
            Number[] arr = new Number[length];
            for (int i = 0; i < length; i++) {
                arr[i] = get(i).deepcopy();
            }
            return new NumberResizeVector<T>((T[]) arr);
        }
    }

    private static class NumberMatrixColumn<T extends Number<T>> extends NumberMatrixVector<T> {
        private NumberMatrix<T> matrix;
        private int columnIndex;

        //region Конструктор
        private NumberMatrixColumn(int columnIndex, NumberMatrix<T> matrix) {
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
        public NumberVector<T> subvector(int length) {
            Number[] arr = new Number[length];
            for (int i = 0; i < length; i++) {
                arr[i] = get(i);
            }
            return new NumberResizeVector<T>((T[]) arr);
        }

        @Override
        public NumberVector<T> subvector(int startIndex, int length) {
            Number[] arr = new Number[length];
            int max = startIndex + length;
            for (int i = startIndex; i < max; i++) {
                arr[i] = get(i);
            }
            return new NumberResizeVector<T>((T[]) arr);
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
        public NumberVector clone() {
            int length = matrix.getRowCount();
            Number[] arr = new Number[length];
            for (int i = 0; i < length; i++) {
                arr[i] = get(i).deepcopy();
            }
            return new NumberResizeVector<T>((T[]) arr);
        }
    }

    //region Отримання частин
    public static <T extends Number<T>> NumberVector<T> row(int rowIndex, NumberMatrix<T> matrix) {
        return new NumberMatrixRow<T>(rowIndex, matrix);
    }

    public static <T extends Number<T>> NumberVector<T> column(int columnIndex, NumberMatrix<T> matrix) {
        return new NumberMatrixColumn<T>(columnIndex, matrix);
    }
    //endregion
}
