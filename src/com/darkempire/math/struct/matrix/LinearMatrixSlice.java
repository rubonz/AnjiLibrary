package com.darkempire.math.struct.matrix;


import com.darkempire.math.struct.LinearCalcable;

import java.util.Arrays;

/**
 * Create in 9:35
 * Created by siredvin on 20.12.13.
 */
public abstract class LinearMatrixSlice<T extends LinearCalcable<T>> extends LinearMatrix<T> {
    protected LinearMatrix<T> linearMatrix;

    //region Конструктори
    private LinearMatrixSlice(LinearMatrix<T> linearMatrix) {
        this.linearMatrix = linearMatrix;
    }
    //endregion

    //region Створення зрізів
    public static <T extends LinearCalcable<T>> LinearMatrixSlice createSlice(LinearMatrix<T> matrix, int startRow, int startColumn, int endRow, int endColumn) {
        return new RectangleLinearMatrixSlice<>(matrix, startRow, startColumn, endRow, endColumn);
    }

    public static <T extends LinearCalcable<T>> LinearMatrixSlice createSlice(LinearMatrix<T> matrix, int[] rowIndexs, int[] columnIndexs) {
        return new IndexLinearMatrixSlice<>(matrix, rowIndexs, columnIndexs);
    }

    //region Геттери
    @Override
    public abstract T get(int rowIndex, int columnIndex);

    @Override
    public abstract int getRowCount();

    //endregion

    @Override
    public abstract int getColumnCount();

    @Override
    protected T get(int index) {
        int columnIndex = index % getColumnCount();
        int rowIndex = index / getRowCount();
        return get(rowIndex, columnIndex);
    }
    //endregion

    //region Сеттери
    @Override
    public abstract void set(int rowIndex, int columnIndex, T value);

    @Override
    protected void set(int index, T value) {
        int columnIndex = index % getColumnCount();
        int rowIndex = index / getRowCount();
        set(rowIndex, columnIndex, value);
    }
    //endregion

    //region Системні методи
    @Override
    public LinearMatrix<T> clone() {
        int rowCount = getRowCount();
        int columnCount = getColumnCount();
        LinearFixedMatrix<T> matrix = LinearFixedMatrix.createInstance(rowCount, columnCount, (T[]) new LinearCalcable[rowCount * columnCount]);
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                matrix.set(rowIndex, columnIndex, get(rowIndex, columnIndex).deepcopy());
            }
        }
        return matrix;
    }

    @Override
    public LinearMatrix<T> deepcopy() {
        return clone();
    }

    //region Арифметичні операції з присвоєнням
    @Override
    public LinearMatrix<T> inegate() {
        int rowCount = getRowCount();
        int columnCount = getColumnCount();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                get(i, j).inegate();
            }
        }
        return this;
    }
    //endregion

    @Override
    public LinearMatrix<T> iadd(LinearMatrix<T> matrix) {
        int rowCount = getRowCount();
        int columnCount = getColumnCount();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                get(i, j).iadd(matrix.get(i, j));
            }
        }
        return this;
    }

    @Override
    public LinearMatrix<T> isubtract(LinearMatrix<T> matrix) {
        int rowCount = getRowCount();
        int columnCount = getColumnCount();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                get(i, j).isubtract(matrix.get(i, j));
            }
        }
        return this;
    }

    //region Арифметичні операції
    @Override
    public LinearMatrix<T> add(LinearMatrix<T> matrix) {
        int newColumnCount = Math.max(getColumnCount(), matrix.getColumnCount());
        int newRowCount = Math.max(getRowCount(), matrix.getRowCount());
        T[] nArr = (T[]) new LinearCalcable[newColumnCount * newRowCount];
        for (int columnIndex = 0; columnIndex < newColumnCount; columnIndex++) {
            for (int rowIndex = 0; rowIndex < newRowCount; rowIndex++) {
                nArr[columnIndex + rowIndex * newColumnCount] = get(rowIndex, columnIndex).add(matrix.get(rowIndex, columnIndex));
            }
        }
        return LinearFixedMatrix.createInstance(newRowCount, newColumnCount, nArr);
    }

    @Override
    public LinearMatrix<T> subtract(LinearMatrix<T> matrix) {
        int newColumnCount = Math.max(getColumnCount(), matrix.getColumnCount());
        int newRowCount = Math.max(getRowCount(), matrix.getRowCount());
        T[] nArr = (T[]) new LinearCalcable[newColumnCount * newRowCount];
        for (int columnIndex = 0; columnIndex < newColumnCount; columnIndex++) {
            for (int rowIndex = 0; rowIndex < newRowCount; rowIndex++) {
                nArr[columnIndex + rowIndex * newColumnCount] = get(rowIndex, columnIndex).subtract(matrix.get(rowIndex, columnIndex));
            }
        }
        return LinearFixedMatrix.createInstance(newRowCount, newColumnCount, nArr);
    }

    //endregion

    @Override
    public LinearMatrix<T> negate() {
        int size = getColumnCount() * getRowCount();
        T[] nArr = (T[]) new LinearCalcable[size];
        for (int i = 0; i < size; i++) {
            nArr[i] = get(i).negate();
        }
        return LinearFixedMatrix.createInstance(getRowCount(), getColumnCount(), nArr);
    }

    @Override
    public LinearMatrix<T> transpose() {
        int rowCount = getRowCount();
        int columnCount = getColumnCount();
        LinearFixedMatrix<T> matrix = LinearFixedMatrix.createInstance(columnCount, rowCount, (T[]) new LinearCalcable[rowCount * columnCount]);
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                matrix.set(columnIndex, rowIndex, get(rowIndex, columnIndex).deepcopy());
            }
        }
        return matrix;
    }

    private static class IndexLinearMatrixSlice<T extends LinearCalcable<T>> extends LinearMatrixSlice<T> {
        private int[] columnIndexs;
        private int[] rowIndexs;

        //region Конструктори
        private IndexLinearMatrixSlice(LinearMatrix<T> doubleMatrix, int[] rowIndexs, int[] columnIndexs) {
            super(doubleMatrix);
            this.columnIndexs = columnIndexs;
            this.rowIndexs = rowIndexs;
        }
        //endregion

        //region Геттери
        @Override
        public T get(int rowIndex, int columnIndex) {
            return linearMatrix.get(rowIndexs[rowIndex], columnIndexs[columnIndex]);
        }

        @Override
        public int getRowCount() {
            return rowIndexs.length;
        }

        @Override
        public int getColumnCount() {
            return columnIndexs.length;
        }
        //endregion

        //region Сеттери
        @Override
        public void set(int rowIndex, int columnIndex, T value) {
            linearMatrix.set(rowIndexs[rowIndex], columnIndexs[columnIndex], value);
        }
        //endregion

        //region Зрізи
        @Override
        public LinearMatrixSlice slice(int startRow, int startColumn, int endRow, int endColumn) {
            int[] newColumnIndexs = new int[endColumn - startColumn + 1];
            int[] newRowIndexs = new int[endRow - startRow + 1];
            endColumn++;
            endRow++;
            System.arraycopy(columnIndexs, startColumn, newColumnIndexs, 0, endColumn - startColumn);
            System.arraycopy(rowIndexs, startRow, newRowIndexs, 0, endRow - startRow);
            return linearMatrix.slice(newRowIndexs, newColumnIndexs);
        }

        @Override
        public LinearMatrixSlice slice(int[] rowIndexs, int[] columnIndexs) {
            int[] newColumnIndexs = new int[columnIndexs.length];
            int[] newRowIndexs = new int[rowIndexs.length];
            for (int i = 0; i < columnIndexs.length; i++) {
                newColumnIndexs[i] = this.columnIndexs[columnIndexs[i]];
            }
            for (int i = 0; i < rowIndexs.length; i++) {
                newRowIndexs[i] = this.rowIndexs[rowIndexs[i]];
            }
            return linearMatrix.slice(newRowIndexs, newColumnIndexs);
        }
        //endregion

        //region Системні функції
        @Override
        public int hashCode() {
            int result = Arrays.hashCode(columnIndexs);
            result = 31 * result + Arrays.hashCode(rowIndexs);
            return result;
        }
        //endregion

    }

    private static class RectangleLinearMatrixSlice<T extends LinearCalcable<T>> extends LinearMatrixSlice<T> {
        private int rowCount;
        private int columnCount;
        private int startRow;
        private int startColumn;

        //region Конструктори
        private RectangleLinearMatrixSlice(LinearMatrix<T> doubleMatrix, int startRow, int startColumn, int endRow, int endColumn) {
            super(doubleMatrix);
            this.startColumn = startColumn;
            this.startRow = startRow;
            this.columnCount = endColumn - startColumn + 1;
            this.rowCount = endRow - startRow + 1;
        }
        //endregion

        //region Геттери
        @Override
        public T get(int rowIndex, int columnIndex) {
            if (rowIndex >= rowCount || columnIndex >= columnCount) {
                throw new ArrayIndexOutOfBoundsException();
            }
            return linearMatrix.get(rowIndex + startRow, columnIndex + startColumn);
        }

        @Override
        public int getRowCount() {
            return rowCount;
        }

        @Override
        public int getColumnCount() {
            return columnCount;
        }
        //endregion

        //region Сеттери
        @Override
        public void set(int rowIndex, int columnIndex, T value) {
            if (rowIndex >= rowCount || columnIndex >= columnCount) {
                throw new ArrayIndexOutOfBoundsException();
            }
            linearMatrix.set(rowIndex + startRow, columnIndex + startColumn, value);
        }
        //endregion

        //region Зрізи
        @Override
        public LinearMatrixSlice slice(int startRow, int startColumn, int endRow, int endColumn) {
            if (startRow >= rowCount || endRow >= rowCount || endColumn >= columnCount || startColumn >= columnCount) {
                throw new ArrayIndexOutOfBoundsException();
            }
            return linearMatrix.slice(startRow + this.startRow, startColumn + this.startColumn, endRow + this.startRow, endColumn + this.startColumn);
        }

        @Override
        public LinearMatrixSlice slice(int[] rowIndexs, int[] columnIndexs) {
            for (int i = 0; i < columnIndexs.length; i++) {
                if (i >= columnCount)
                    throw new ArrayIndexOutOfBoundsException();
                columnIndexs[i] += startColumn;
            }
            for (int i = 0; i < rowIndexs.length; i++) {
                if (i >= rowCount)
                    throw new ArrayIndexOutOfBoundsException();
                rowIndexs[i] += startRow;
            }
            return linearMatrix.slice(rowIndexs, columnIndexs);
        }
        //endregion

        //region Системні функції
        @Override
        public int hashCode() {
            int result = rowCount;
            result = 31 * result + columnCount;
            result = 31 * result + startRow;
            result = 31 * result + startColumn;
            return result;
        }
        //endregion

    }
    //endregion
}
