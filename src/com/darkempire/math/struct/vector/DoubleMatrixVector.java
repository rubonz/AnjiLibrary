package com.darkempire.math.struct.vector;

import com.darkempire.math.struct.matrix.DoubleMatrix;

/**
 * Create in 11:51
 * Created by siredvin on 25.04.14.
 * За замовучанням створюються нові ResizeDoubleVector
 */
public abstract class DoubleMatrixVector extends DoubleVector {
    //region Отримання частин матриці
    public static DoubleVector row(int rowIndex, DoubleMatrix matrix) {
        return new DoubleMatrixRow(rowIndex, matrix);
    }
    //endregion

    public static DoubleVector column(int columnIndex, DoubleMatrix matrix) {
        return new DoubleMatrixColumn(columnIndex, matrix);
    }

    public static DoubleVector diagonal(DoubleMatrix matrix) {
        return new DoubleMatrixDiagonal(matrix);
    }
    //endregion

    //region Сеттери
    @Override
    public abstract void set(int index, double value);

    //region Геттери
    @Override
    public abstract double get(int index);

    @Override
    public abstract int getSize();

    @Override
    public DoubleVector deepcopy() {
        return clone();
    }

    @Override
    public double[] toRawArray() {
        int size = getSize();
        double[] arr = new double[size];
        for (int i = 0; i < size; i++) {
            arr[i] = get(i);
        }
        return arr;
    }

    @Override
    public double scalar(DoubleVector vector) {
        int size = Math.min(getSize(), vector.getSize());
        double temp = 0;
        for (int i = 0; i < size; i++) {
            temp += get(i) * vector.get(i);
        }
        return temp;
    }
    //endregion

    //region Арифметичні операції з присвоєнням
    @Override
    public DoubleVector inegate() {
        int size = getSize();
        for (int i = 0; i < size; i++) {
            set(i, -get(i));
        }
        return this;
    }

    @Override
    public DoubleVector iadd(DoubleVector vector) {
        int size = Math.max(getSize(), vector.getSize());
        for (int i = 0; i < size; i++) {
            set(i, get(i) + vector.get(i));
        }
        return this;
    }

    @Override
    public DoubleVector isubtract(DoubleVector vector) {
        int size = Math.max(getSize(), vector.getSize());
        for (int i = 0; i < size; i++) {
            set(i, get(i) - vector.get(i));
        }
        return this;
    }

    //region Арифметичні операції
    @Override
    public DoubleVector add(DoubleVector vector) {
        int size = Math.max(getSize(), vector.getSize());
        double[] p = new double[size];
        for (int i = 0; i < size; i++) {
            p[i] = get(i) + vector.get(i);
        }
        return new DoubleResizeVector(p);
    }
    //endregion

    @Override
    public DoubleVector subtract(DoubleVector vector) {
        int size = Math.max(getSize(), vector.getSize());
        double[] p = new double[size];
        for (int i = 0; i < size; i++) {
            p[i] = get(i) - vector.get(i);
        }
        return new DoubleResizeVector(p);
    }

    @Override
    public DoubleVector negate() {
        double[] p = new double[getSize()];
        for (int i = 0; i < p.length; i++) {
            p[i] = -get(i);
        }
        return new DoubleResizeVector(p);
    }

    @Override
    public DoubleVector prod(double lambda) {
        int size = getSize();
        double[] arr = new double[size];
        for (int i = 0; i < size; i++) {
            arr[i] = get(i) * lambda;
        }
        return new DoubleFixedVector(arr);
    }

    private static class DoubleMatrixRow extends DoubleMatrixVector {
        private DoubleMatrix matrix;
        private int rowIndex;

        //region Конструктори
        private DoubleMatrixRow(int rowIndex, DoubleMatrix matrix) {
            this.matrix = matrix;
            this.rowIndex = rowIndex;
        }
        //endregion

        //region Сеттери
        @Override
        public void set(int index, double value) {
            matrix.set(rowIndex, index, value);
        }
        //endregion

        //region Отримання підвекторів
        @Override
        public DoubleVector subvector(int length) {
            double[] result = new double[length];
            for (int i = 0; i < length; i++) {
                result[i] = get(i);
            }
            return new DoubleResizeVector(result);
        }

        @Override
        public DoubleVector subvector(int startIndex, int length) {
            double[] result = new double[length];
            for (int i = 0; i < length; i++) {
                result[i] = get(i + startIndex);
            }
            return new DoubleResizeVector(result);
        }
        //endregion

        //region Геттери
        @Override
        public double get(int index) {
            return matrix.get(rowIndex, index);
        }

        @Override
        public int getSize() {
            return matrix.getColumnCount();
        }
        //endregion

        @Override
        public DoubleVector clone() {
            double[] temp = new double[matrix.getColumnCount()];
            for (int i = 0; i < temp.length; i++) {
                temp[i] = get(i);
            }
            return new DoubleFixedVector(temp);
        }
    }

    private static class DoubleMatrixColumn extends DoubleMatrixVector {
        private DoubleMatrix matrix;
        private int columnIndex;

        //region Конструктори
        private DoubleMatrixColumn(int columnIndex, DoubleMatrix matrix) {
            this.matrix = matrix;
            this.columnIndex = columnIndex;
        }
        //endregion

        //region Сеттери
        @Override
        public void set(int index, double value) {
            matrix.set(index, columnIndex, value);
        }
        //endregion

        //region Отримання підвекторів
        @Override
        public DoubleVector subvector(int length) {
            double[] result = new double[length];
            for (int i = 0; i < length; i++) {
                result[i] = get(i);
            }
            return new DoubleResizeVector(result);
        }

        @Override
        public DoubleVector subvector(int startIndex, int length) {
            double[] result = new double[length];
            for (int i = 0; i < length; i++) {
                result[i] = get(i + startIndex);
            }
            return new DoubleResizeVector(result);
        }
        //endregion

        //region Геттери
        @Override
        public double get(int index) {
            return matrix.get(index, columnIndex);
        }

        @Override
        public int getSize() {
            return matrix.getRowCount();
        }
        //endregion

        @Override
        public DoubleVector clone() {
            double[] temp = new double[matrix.getRowCount()];
            for (int i = 0; i < temp.length; i++) {
                temp[i] = get(i);
            }
            return new DoubleFixedVector(temp);
        }
    }

    private static class DoubleMatrixDiagonal extends DoubleMatrixVector {
        private DoubleMatrix matrix;
        private int size;

        //region Конструктори
        private DoubleMatrixDiagonal(DoubleMatrix matrix) {
            this.matrix = matrix;
            size = Math.min(matrix.getRowCount(), matrix.getColumnCount());
        }
        //endregion

        //region Сеттери
        @Override
        public void set(int index, double value) {
            matrix.set(index, index, value);
        }
        //endregion

        //region Отримання підвекторів
        @Override
        public DoubleVector subvector(int length) {
            double[] result = new double[length];
            for (int i = 0; i < length; i++) {
                result[i] = get(i);
            }
            return new DoubleResizeVector(result);
        }

        @Override
        public DoubleVector subvector(int startIndex, int length) {
            double[] result = new double[length];
            for (int i = 0; i < length; i++) {
                result[i] = get(i + startIndex);
            }
            return new DoubleResizeVector(result);
        }
        //endregion

        //region Геттери
        @Override
        public double get(int index) {
            return matrix.get(index, index);
        }

        @Override
        public int getSize() {
            return size;
        }
        //endregion

        @Override
        public DoubleVector clone() {
            double[] temp = new double[size];
            for (int i = 0; i < temp.length; i++) {
                temp[i] = get(i);
            }
            return new DoubleFixedVector(temp);
        }
    }
    //endregion
}
