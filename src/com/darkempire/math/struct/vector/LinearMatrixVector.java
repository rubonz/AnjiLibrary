package com.darkempire.math.struct.vector;

import com.darkempire.math.struct.LinearCalcable;
import com.darkempire.math.struct.matrix.LinearMatrix;

/**
 * Create in 11:51
 * Created by siredvin on 25.04.14.
 * За замовучанням створюються нові ResizeLinearVector
 */
public abstract class LinearMatrixVector<T extends LinearCalcable<T>> extends LinearVector<T> {
    //region Отримання частин
    public static <T extends LinearCalcable<T>> LinearVector<T> row(int rowIndex, LinearMatrix<T> matrix) {
        return new LinearMatrixRow<T>(rowIndex, matrix);
    }
    //endregion

    public static <T extends LinearCalcable<T>> LinearVector<T> column(int columnIndex, LinearMatrix<T> matrix) {
        return new LinearMatrixColumn<T>(columnIndex, matrix);
    }

    //region Сеттери
    @Override
    public abstract void set(int index, T value);
    //endregion

    //region Геттери
    @Override
    public abstract T get(int index);

    @Override
    public abstract int getSize();

    @Override
    public LinearVector<T> deepcopy() {
        return clone();
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
    //endregion

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

    //region Арифметичні операції
    @Override
    public LinearVector<T> add(LinearVector<T> vector) {
        int size = Math.max(getSize(), vector.getSize());
        T[] p = (T[]) new LinearCalcable[size];
        for (int i = 0; i < size; i++) {
            p[i] = get(i).add(vector.get(i));
        }
        return new LinearResizeVector<>(p);
    }
    //endregion

    @Override
    public LinearVector<T> subtract(LinearVector<T> vector) {
        int size = Math.max(getSize(), vector.getSize());
        T[] p = (T[]) new LinearCalcable[size];
        for (int i = 0; i < size; i++) {
            p[i] = get(i).subtract(vector.get(i));
        }
        return new LinearResizeVector<T>(p);
    }

    @Override
    public LinearVector<T> negate() {
        T[] p = (T[]) new LinearCalcable[getSize()];
        for (int i = 0; i < p.length; i++) {
            p[i] = get(i).negate();
        }
        return new LinearResizeVector<>(p);
    }

    private static class LinearMatrixRow<T extends LinearCalcable<T>> extends LinearMatrixVector<T> {
        private LinearMatrix<T> matrix;
        private int rowIndex;

        //region Конструктор
        private LinearMatrixRow(int rowIndex, LinearMatrix<T> matrix) {
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
            LinearCalcable[] arr = new LinearCalcable[length];
            for (int i = 0; i < length; i++) {
                arr[i] = get(i);
            }
            return new LinearResizeVector<T>((T[]) arr);
        }

        @Override
        public LinearVector<T> subvector(int startIndex, int length) {
            LinearCalcable[] arr = new LinearCalcable[length];
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
            LinearCalcable[] arr = new LinearCalcable[length];
            for (int i = 0; i < length; i++) {
                arr[i] = get(i).deepcopy();
            }
            return new LinearResizeVector<T>((T[]) arr);
        }
    }

    private static class LinearMatrixColumn<T extends LinearCalcable<T>> extends LinearMatrixVector<T> {
        private LinearMatrix<T> matrix;
        private int columnIndex;

        //region Конструктор
        private LinearMatrixColumn(int columnIndex, LinearMatrix<T> matrix) {
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
            LinearCalcable[] arr = new LinearCalcable[length];
            for (int i = 0; i < length; i++) {
                arr[i] = get(i);
            }
            return new LinearResizeVector<T>((T[]) arr);
        }

        @Override
        public LinearVector<T> subvector(int startIndex, int length) {
            LinearCalcable[] arr = new LinearCalcable[length];
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
        public LinearVector<T> clone() {
            int length = matrix.getRowCount();
            LinearCalcable[] arr = new LinearCalcable[length];
            for (int i = 0; i < length; i++) {
                arr[i] = get(i).deepcopy();
            }
            return new LinearResizeVector<T>((T[]) arr);
        }
    }
    //endregion
}
