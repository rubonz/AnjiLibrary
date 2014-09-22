package com.darkempire.math.struct.vector;

import com.darkempire.math.struct.matrix.BooleanMatrix;

/**
 * Create in 11:51
 * Created by siredvin on 25.04.14.
 * За замовучанням створюються нові BooleanFixedVector
 */
public abstract class BooleanMatrixVector extends BooleanVector {
    //region Сеттери
    @Override
    public abstract void set(int index, boolean value);
    //endregion

    //region Геттери
    @Override
    public abstract boolean get(int index);

    @Override
    public abstract int getSize();
    //endregion

    @Override
    public boolean scalar(BooleanVector vector) {
        int size = Math.min(getSize(), vector.getSize());
        boolean temp = false;
        for (int i = 0; i < size; i++) {
            temp = temp || (get(i) && vector.get(i));
        }
        return temp;
    }

    //region Арифметичні операції з присвоєнням
    @Override
    public BooleanVector inegate() {
        int size = getSize();
        for (int i = 0; i < size; i++) {
            set(i, !get(i));
        }
        return this;
    }

    @Override
    public BooleanVector iadd(BooleanVector vector) {
        int size = Math.max(getSize(), vector.getSize());
        for (int i = 0; i < size; i++) {
            set(i, get(i) || vector.get(i));
        }
        return this;
    }

    @Override
    public BooleanVector isubtract(BooleanVector vector) {
        int size = Math.max(getSize(), vector.getSize());
        for (int i = 0; i < size; i++) {
            set(i, get(i) ^ vector.get(i));
        }
        return this;
    }
    //endregion

    //region Арифметичні операції
    @Override
    public BooleanVector add(BooleanVector vector) {
        int size = Math.max(getSize(), vector.getSize());
        boolean[] p = new boolean[size];
        for (int i = 0; i < size; i++) {
            p[i] = get(i) || vector.get(i);
        }
        return new BooleanFixedVector(p);
    }

    @Override
    public BooleanVector subtract(BooleanVector vector) {
        int size = Math.max(getSize(), vector.getSize());
        boolean[] p = new boolean[size];
        for (int i = 0; i < size; i++) {
            p[i] = get(i) ^ vector.get(i);
        }
        return new BooleanFixedVector(p);
    }

    @Override
    public BooleanVector negate() {
        boolean[] p = new boolean[getSize()];
        for (int i = 0; i < p.length; i++) {
            p[i] = !get(i);
        }
        return new BooleanFixedVector(p);
    }

    @Override
    public BooleanVector prod(boolean lambda) {
        int size = getSize();
        boolean[] arr = new boolean[size];
        for (int i = 0; i < size; i++) {
            arr[i] = get(i) && lambda;
        }
        return new BooleanFixedVector(arr);
    }
    //endregion

    private static class BooleanMatrixRow extends BooleanMatrixVector {
        private BooleanMatrix matrix;
        private int rowIndex;

        //region Конструктори
        private BooleanMatrixRow(int rowIndex, BooleanMatrix matrix) {
            this.matrix = matrix;
            this.rowIndex = rowIndex;
        }
        //endregion

        //region Сеттери
        @Override
        public void set(int index, boolean value) {
            matrix.set(rowIndex, index, value);
        }
        //endregion

        //region Отримання підвекторів
        @Override
        public BooleanVector subvector(int length) {
            boolean[] result = new boolean[length];
            for (int i = 0; i < length; i++) {
                result[i] = get(i);
            }
            return new BooleanFixedVector(result);
        }

        @Override
        public BooleanVector subvector(int startIndex, int length) {
            boolean[] result = new boolean[length];
            for (int i = 0; i < length; i++) {
                result[i] = get(i + startIndex);
            }
            return new BooleanFixedVector(result);
        }
        //endregion

        //region Геттери
        @Override
        public boolean get(int index) {
            return matrix.get(rowIndex, index);
        }

        @Override
        public int getSize() {
            return matrix.getColumnCount();
        }
        //endregion

        @Override
        public BooleanVector clone() {
            boolean[] temp = new boolean[matrix.getColumnCount()];
            for (int i = 0; i < temp.length; i++) {
                temp[i] = get(i);
            }
            return new BooleanFixedVector(temp);
        }
    }

    private static class BooleanMatrixColumn extends BooleanMatrixVector {
        private BooleanMatrix matrix;
        private int columnIndex;

        //region Конструктори
        private BooleanMatrixColumn(int columnIndex, BooleanMatrix matrix) {
            this.matrix = matrix;
            this.columnIndex = columnIndex;
        }
        //endregion

        //region Сеттери
        @Override
        public void set(int index, boolean value) {
            matrix.set(index, columnIndex, value);
        }
        //endregion

        //region Отримання підвекторів
        @Override
        public BooleanVector subvector(int length) {
            boolean[] result = new boolean[length];
            for (int i = 0; i < length; i++) {
                result[i] = get(i);
            }
            return new BooleanFixedVector(result);
        }

        @Override
        public BooleanVector subvector(int startIndex, int length) {
            boolean[] result = new boolean[length];
            for (int i = 0; i < length; i++) {
                result[i] = get(i + startIndex);
            }
            return new BooleanFixedVector(result);
        }
        //endregion

        //region Геттери
        @Override
        public boolean get(int index) {
            return matrix.get(index, columnIndex);
        }

        @Override
        public int getSize() {
            return matrix.getRowCount();
        }
        //endregion

        @Override
        public BooleanVector clone() {
            boolean[] temp = new boolean[matrix.getRowCount()];
            for (int i = 0; i < temp.length; i++) {
                temp[i] = get(i);
            }
            return new BooleanFixedVector(temp);
        }
    }

    //region Отримання частин матриці
    public static BooleanVector row(int rowIndex, BooleanMatrix matrix) {
        return new BooleanMatrixRow(rowIndex, matrix);
    }

    public static BooleanVector column(int columnIndex, BooleanMatrix matrix) {
        return new BooleanMatrixColumn(columnIndex, matrix);
    }
    //endregion
}
