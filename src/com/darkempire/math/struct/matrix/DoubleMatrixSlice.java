package com.darkempire.math.struct.matrix;

import com.darkempire.math.struct.vector.DoubleFixedVector;
import com.darkempire.math.struct.vector.DoubleVector;

import java.util.Arrays;

/**
 * Create in 9:35
 * Created by siredvin on 20.12.13.
 */
public abstract class DoubleMatrixSlice extends DoubleMatrix {
    protected DoubleMatrix doubleMatrix;

    //region Конструктори
    private DoubleMatrixSlice(DoubleMatrix doubleMatrix) {
        this.doubleMatrix = doubleMatrix;
    }
    //endregion

    //region Геттери
    @Override
    public abstract double get(int rowIndex, int columnIndex);

    @Override
    protected double get(int index) {
        int columnIndex = index % getColumnCount();
        int rowIndex = index / getRowCount();
        return get(rowIndex, columnIndex);
    }

    @Override
    public abstract int getRowCount();

    @Override
    public abstract int getColumnCount();
    //endregion

    //region Сеттери
    @Override
    public abstract void set(int rowIndex, int columnIndex, double value);

    @Override
    protected void set(int index, double value) {
        int columnIndex = index % getColumnCount();
        int rowIndex = index / getRowCount();
        set(rowIndex, columnIndex, value);
    }
    //endregion

    //region Системні методи

    @Override
    public DoubleMatrix clone() {
        int rowCount = getRowCount();
        int columnCount = getColumnCount();
        DoubleMatrix matrix = DoubleFixedMatrix.createInstance(rowCount, columnCount);
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                matrix.set(rowIndex, columnIndex, get(rowIndex, columnIndex));
            }
        }
        return matrix;
    }

    @Override
    public DoubleMatrix deepcopy() {
        return clone();
    }
    //endregion

    //region Арифметичні операції з присвоєнням
    @Override
    public DoubleMatrix inegate() {
        int rowCount = getRowCount();
        int columnCount = getColumnCount();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                set(i, j, -get(i, j));
            }
        }
        return this;
    }

    @Override
    public DoubleMatrix iadd(DoubleMatrix matrix) {
        int rowCount = getRowCount();
        int columnCount = getColumnCount();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                set(i, j, matrix.get(i, j) + get(i, j));
            }
        }
        return this;
    }

    @Override
    public DoubleMatrix isubtract(DoubleMatrix matrix) {
        int rowCount = getRowCount();
        int columnCount = getColumnCount();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                set(i, j, get(i, j) - matrix.get(i, j));
            }
        }
        return this;
    }

    @Override
    public DoubleMatrix iprod(double lambda) {
        int rowCount = getRowCount();
        int columnCount = getColumnCount();
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                set(rowIndex, columnIndex, get(rowIndex, columnIndex) * lambda);
            }
        }
        return this;
    }
    //endregion

    //region Арифметичні операції
    @Override
    public DoubleMatrix add(DoubleMatrix matrix) {
        int newColumnCount = Math.max(getColumnCount(), matrix.getColumnCount());
        int newRowCount = Math.max(getRowCount(), matrix.getRowCount());
        double[] nArr = new double[newColumnCount * newRowCount];
        for (int columnIndex = 0; columnIndex < newColumnCount; columnIndex++) {
            for (int rowIndex = 0; rowIndex < newRowCount; rowIndex++) {
                nArr[columnIndex + rowIndex * newColumnCount] = get(rowIndex, columnIndex) + matrix.get(rowIndex, columnIndex);
            }
        }
        return DoubleResizeMatrix.createInstance(newRowCount, newColumnCount, nArr);
    }

    @Override
    public DoubleMatrix subtract(DoubleMatrix matrix) {
        int newColumnCount = Math.max(getColumnCount(), matrix.getColumnCount());
        int newRowCount = Math.max(getRowCount(), matrix.getRowCount());
        double[] nArr = new double[newColumnCount * newRowCount];
        for (int columnIndex = 0; columnIndex < newColumnCount; columnIndex++) {
            for (int rowIndex = 0; rowIndex < newRowCount; rowIndex++) {
                nArr[columnIndex + rowIndex * newColumnCount] = get(rowIndex, columnIndex) - matrix.get(rowIndex, columnIndex);
            }
        }
        return DoubleResizeMatrix.createInstance(newRowCount, newColumnCount, nArr);
    }

    @Override
    public DoubleMatrix negate() {
        int size = getColumnCount() * getRowCount();
        double[] nArr = new double[size];
        for (int i = 0; i < size; i++) {
            nArr[i] = -get(i);
        }
        return DoubleResizeMatrix.createInstance(getRowCount(), getColumnCount(), nArr);
    }


    @Override
    public DoubleVector multy(DoubleVector doubleVector) {
        int rowCount = getRowCount();
        int columnCount = getColumnCount();
        if (columnCount != doubleVector.getSize())
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MULTY_MISMATCH);
        DoubleVector result = new DoubleFixedVector(rowCount);
        for (int vectIndex = 0; vectIndex < rowCount; vectIndex++) {
            double summ = 0;
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                summ += get(vectIndex, columnIndex) * doubleVector.get(columnIndex);
            }
            result.set(vectIndex, summ);
        }
        return result;
    }

    @Override
    public DoubleMatrix prod(double lambda) {
        int rowCount = getRowCount();
        int columnCount = getColumnCount();
        DoubleMatrix matrix = DoubleFixedMatrix.createInstance(rowCount, columnCount);
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                matrix.set(rowIndex, columnIndex, get(rowIndex, columnIndex) * lambda);
            }
        }
        return matrix;
    }

    @Override
    public DoubleMatrix transpose() {
        int rowCount = getRowCount();
        int columnCount = getRowCount();
        DoubleFixedMatrix matrix = DoubleFixedMatrix.createInstance(columnCount, rowCount);
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                matrix.set(columnIndex, rowIndex, get(rowIndex, columnIndex));
            }
        }
        return matrix;
    }

    @Override
    public DoubleMatrix prod(DoubleMatrix doubleMatrix) {
        int rowCount = getRowCount();
        int columnCount = getColumnCount();
        if (columnCount != doubleMatrix.getColumnCount() || rowCount != doubleMatrix.getRowCount()) {
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MISMATCH);
        }
        DoubleMatrix matrix = DoubleFixedMatrix.createInstance(rowCount, columnCount);
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                matrix.set(rowIndex, columnIndex, get(rowIndex, columnIndex) * doubleMatrix.get(rowIndex, columnIndex));
            }
        }
        return matrix;
    }

    @Override
    public DoubleMatrix multy(DoubleMatrix doubleMatrix) {
        int rowCount = getRowCount();
        int columnCount = getColumnCount();
        if (columnCount != doubleMatrix.getRowCount())
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MULTY_MISMATCH);
        DoubleFixedMatrix result = DoubleFixedMatrix.createInstance(rowCount, doubleMatrix.getColumnCount());
        int ncolumnCount = doubleMatrix.getColumnCount();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < ncolumnCount; j++) {
                double temp = 0;
                for (int k = 0; k < columnCount; k++) {
                    temp += get(i, k) * doubleMatrix.get(k, j);
                }
                result.set(i, j, temp);
            }
        }
        return result;
    }
    //endregion

    //Зріз, де потрібні колонки та стовпчики вказані напряму
    private static class IndexDoubleMatrixSlice extends DoubleMatrixSlice {
        private int[] columnIndexs;
        private int[] rowIndexs;

        //region Конструктор
        private IndexDoubleMatrixSlice(DoubleMatrix doubleMatrix, int[] rowIndexs, int[] columnIndexs) {
            super(doubleMatrix);
            this.columnIndexs = columnIndexs;
            this.rowIndexs = rowIndexs;
        }
        //endregion

        //region Геттери
        @Override
        public double get(int rowIndex, int columnIndex) {
            return doubleMatrix.get(rowIndexs[rowIndex], columnIndexs[columnIndex]);
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
        public void set(int rowIndex, int columnIndex, double value) {
            doubleMatrix.set(rowIndexs[rowIndex], columnIndexs[columnIndex], value);
        }
        //endregion

        //region Зрізи
        @Override
        public DoubleMatrixSlice slice(int startRow, int startColumn, int endRow, int endColumn) {
            int[] newColumnIndexs = new int[endColumn - startColumn + 1];
            int[] newRowIndexs = new int[endRow - startRow + 1];
            endColumn++;
            endRow++;
            System.arraycopy(columnIndexs, startColumn, newColumnIndexs, 0, endColumn - startColumn);
            System.arraycopy(rowIndexs, startRow, newRowIndexs, 0, endRow - startRow);
            return doubleMatrix.slice(newRowIndexs, newColumnIndexs);
        }

        @Override
        public DoubleMatrixSlice slice(int[] rowIndexs, int[] columnIndexs) {
            int[] newColumnIndexs = new int[columnIndexs.length];
            int[] newRowIndexs = new int[rowIndexs.length];
            for (int i = 0; i < columnIndexs.length; i++) {
                newColumnIndexs[i] = this.columnIndexs[columnIndexs[i]];
            }
            for (int i = 0; i < rowIndexs.length; i++) {
                newRowIndexs[i] = this.rowIndexs[rowIndexs[i]];
            }
            return doubleMatrix.slice(newRowIndexs, newColumnIndexs);
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

    //Зріз, де потрібні колонки та стовпчики знаходяться у вказаному прямокутнику
    private static class RectangleDoubleMatrixSlice extends DoubleMatrixSlice {
        private int rowCount;
        private int columnCount;
        private int startRow;
        private int startColumn;

        //region Конструктор
        private RectangleDoubleMatrixSlice(DoubleMatrix doubleMatrix, int startRow, int startColumn, int endRow, int endColumn) {
            super(doubleMatrix);
            this.startColumn = startColumn;
            this.startRow = startRow;
            this.columnCount = endColumn - startColumn + 1;
            this.rowCount = endRow - startRow + 1;
        }
        //endregion

        //region Геттери
        @Override
        public double get(int rowIndex, int columnIndex) {
            if (rowIndex >= rowCount || columnIndex >= columnCount) {
                throw new ArrayIndexOutOfBoundsException();
            }
            return doubleMatrix.get(rowIndex + startRow, columnIndex + startColumn);
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
        public void set(int rowIndex, int columnIndex, double value) {
            if (rowIndex >= rowCount || columnIndex >= columnCount) {
                throw new ArrayIndexOutOfBoundsException();
            }
            doubleMatrix.set(rowIndex + startRow, columnIndex + startColumn, value);
        }
        //endregion

        //region Зрізи
        @Override
        public DoubleMatrixSlice slice(int startRow, int startColumn, int endRow, int endColumn) {
            if (startRow >= rowCount || endRow >= rowCount || endColumn >= columnCount || startColumn >= columnCount) {
                throw new ArrayIndexOutOfBoundsException();
            }
            return doubleMatrix.slice(startRow + this.startRow, startColumn + this.startColumn, endRow + this.startRow, endColumn + this.startColumn);
        }

        @Override
        public DoubleMatrixSlice slice(int[] rowIndexs, int[] columnIndexs) {
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
            return doubleMatrix.slice(rowIndexs, columnIndexs);
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

    //region Створення зрізів
    public static DoubleMatrixSlice createSlice(DoubleMatrix matrix, int startRow, int startColumn, int endRow, int endColumn) {
        return new RectangleDoubleMatrixSlice(matrix, startRow, startColumn, endRow, endColumn);
    }

    public static DoubleMatrixSlice createSlice(DoubleMatrix matrix, int[] rowIndexs, int[] columnIndexs) {
        return new IndexDoubleMatrixSlice(matrix, rowIndexs, columnIndexs);
    }
    //endregion
}
