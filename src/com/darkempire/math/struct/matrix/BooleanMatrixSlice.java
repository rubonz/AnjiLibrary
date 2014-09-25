package com.darkempire.math.struct.matrix;

import java.util.Arrays;

/**
 * Create in 9:35
 * Created by siredvin on 20.12.13.
 */
public abstract class BooleanMatrixSlice extends BooleanMatrix {
    protected BooleanMatrix booleanMatrix;

    //region Конструктори
    private BooleanMatrixSlice(BooleanMatrix booleanMatrix) {
        this.booleanMatrix = booleanMatrix;
    }
    //endregion

    //region Геттери
    @Override
    public abstract boolean get(int rowIndex, int columnIndex);

    @Override
    protected boolean get(int index) {
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
    public abstract void set(int rowIndex, int columnIndex, boolean value);

    @Override
    protected void set(int index, boolean value) {
        int columnIndex = index % getColumnCount();
        int rowIndex = index / getRowCount();
        set(rowIndex, columnIndex, value);
    }
    //endregion

    //region Системні методи
    @Override
    public BooleanMatrix clone() {
        int rowCount = getRowCount();
        int columnCount = getColumnCount();
        BooleanMatrix matrix = BooleanFixedMatrix.createInstance(rowCount, columnCount);
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                matrix.set(rowIndex, columnIndex, get(rowIndex, columnIndex));
            }
        }
        return matrix;
    }
    //endregion

    //region Арифметичні операції з присвоєнням
    @Override
    public BooleanMatrix inegate() {
        int rowCount = getRowCount();
        int columnCount = getColumnCount();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                set(i, j, !get(i, j));
            }
        }
        return this;
    }

    @Override
    public BooleanMatrix iadd(BooleanMatrix matrix) {
        int rowCount = getRowCount();
        int columnCount = getColumnCount();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                set(i, j, matrix.get(i, j) || get(i, j));
            }
        }
        return this;
    }

    @Override
    public BooleanMatrix isubtract(BooleanMatrix matrix) {
        int rowCount = getRowCount();
        int columnCount = getColumnCount();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                set(i, j, get(i, j) ^ matrix.get(i, j));
            }
        }
        return this;
    }
    //endregion

    //region Арифметичні операції
    @Override
    public BooleanMatrix add(BooleanMatrix matrix) {
        int newColumnCount = Math.max(getColumnCount(), matrix.getColumnCount());
        int newRowCount = Math.max(getRowCount(), matrix.getRowCount());
        boolean[] nArr = new boolean[newColumnCount * newRowCount];
        for (int columnIndex = 0; columnIndex < newColumnCount; columnIndex++) {
            for (int rowIndex = 0; rowIndex < newRowCount; rowIndex++) {
                nArr[columnIndex + rowIndex * newColumnCount] = get(rowIndex, columnIndex) || matrix.get(rowIndex, columnIndex);
            }
        }
        return BooleanFixedMatrix.createInstance(newRowCount, newColumnCount, nArr);
    }

    @Override
    public BooleanMatrix subtract(BooleanMatrix matrix) {
        int newColumnCount = Math.max(getColumnCount(), matrix.getColumnCount());
        int newRowCount = Math.max(getRowCount(), matrix.getRowCount());
        boolean[] nArr = new boolean[newColumnCount * newRowCount];
        for (int columnIndex = 0; columnIndex < newColumnCount; columnIndex++) {
            for (int rowIndex = 0; rowIndex < newRowCount; rowIndex++) {
                nArr[columnIndex + rowIndex * newColumnCount] = get(rowIndex, columnIndex) ^ matrix.get(rowIndex, columnIndex);
            }
        }
        return BooleanFixedMatrix.createInstance(newRowCount, newColumnCount, nArr);
    }

    @Override
    public BooleanMatrix negate() {
        int size = getColumnCount() * getRowCount();
        boolean[] nArr = new boolean[size];
        for (int i = 0; i < size; i++) {
            nArr[i] = !get(i);
        }
        return BooleanFixedMatrix.createInstance(getRowCount(), getColumnCount(), nArr);
    }

    @Override
    public BooleanMatrix transpose() {
        int rowCount = getRowCount();
        int columnCount = getRowCount();
        BooleanFixedMatrix matrix = BooleanFixedMatrix.createInstance(columnCount, rowCount);
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                matrix.set(columnIndex, rowIndex, get(rowIndex, columnIndex));
            }
        }
        return matrix;
    }

    @Override
    public BooleanMatrix prod(BooleanMatrix booleanMatrix) {
        int rowCount = getRowCount();
        int columnCount = getColumnCount();
        if (columnCount != booleanMatrix.getColumnCount() || rowCount != booleanMatrix.getRowCount()) {
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MISMATCH);
        }
        BooleanMatrix matrix = BooleanFixedMatrix.createInstance(rowCount, columnCount);
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                matrix.set(rowIndex, columnIndex, get(rowIndex, columnIndex) && booleanMatrix.get(rowIndex, columnIndex));
            }
        }
        return matrix;
    }

    @Override
    public BooleanMatrix multy(BooleanMatrix booleanMatrix) {
        int rowCount = getRowCount();
        int columnCount = getColumnCount();
        if (columnCount != booleanMatrix.getRowCount())
            throw new com.darkempire.math.exception.MatrixSizeException(com.darkempire.math.exception.MatrixSizeException.MATRIX_SIZE_MULTY_MISMATCH);
        BooleanFixedMatrix result = BooleanFixedMatrix.createInstance(rowCount, booleanMatrix.getColumnCount());
        int ncolumnCount = booleanMatrix.getColumnCount();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < ncolumnCount; j++) {
                boolean temp = false;
                for (int k = 0; k < columnCount; k++) {
                    temp = temp || (get(i, k) && booleanMatrix.get(k, j));
                }
                result.set(i, j, temp);
            }
        }
        return result;
    }
    //endregion

    //Зріз, де потрібні колонки та стовпчики вказані напряму
    private static class IndexBooleanMatrixSlice extends BooleanMatrixSlice {
        private int[] columnIndexs;
        private int[] rowIndexs;

        //region Конструктор
        private IndexBooleanMatrixSlice(BooleanMatrix booleanMatrix, int[] rowIndexs, int[] columnIndexs) {
            super(booleanMatrix);
            this.columnIndexs = columnIndexs;
            this.rowIndexs = rowIndexs;
        }
        //endregion

        //region Геттери
        @Override
        public boolean get(int rowIndex, int columnIndex) {
            return booleanMatrix.get(rowIndexs[rowIndex], columnIndexs[columnIndex]);
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
        public void set(int rowIndex, int columnIndex, boolean value) {
            booleanMatrix.set(rowIndexs[rowIndex], columnIndexs[columnIndex], value);
        }
        //endregion

        //region Зрізи
        @Override
        public BooleanMatrixSlice slice(int startRow, int startColumn, int endRow, int endColumn) {
            int[] newColumnIndexs = new int[endColumn - startColumn + 1];
            int[] newRowIndexs = new int[endRow - startRow + 1];
            endColumn++;
            endRow++;
            System.arraycopy(columnIndexs, startColumn, newColumnIndexs, 0, endColumn - startColumn);
            System.arraycopy(rowIndexs, startRow, newRowIndexs, 0, endRow - startRow);
            return booleanMatrix.slice(newRowIndexs, newColumnIndexs);
        }

        @Override
        public BooleanMatrixSlice slice(int[] rowIndexs, int[] columnIndexs) {
            int[] newColumnIndexs = new int[columnIndexs.length];
            int[] newRowIndexs = new int[rowIndexs.length];
            for (int i = 0; i < columnIndexs.length; i++) {
                newColumnIndexs[i] = this.columnIndexs[columnIndexs[i]];
            }
            for (int i = 0; i < rowIndexs.length; i++) {
                newRowIndexs[i] = this.rowIndexs[rowIndexs[i]];
            }
            return booleanMatrix.slice(newRowIndexs, newColumnIndexs);
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
    private static class RectangleBooleanMatrixSlice extends BooleanMatrixSlice {
        private int rowCount;
        private int columnCount;
        private int startRow;
        private int startColumn;

        //region Конструктор
        private RectangleBooleanMatrixSlice(BooleanMatrix booleanMatrix, int startRow, int startColumn, int endRow, int endColumn) {
            super(booleanMatrix);
            this.startColumn = startColumn;
            this.startRow = startRow;
            this.columnCount = endColumn - startColumn + 1;
            this.rowCount = endRow - startRow + 1;
        }
        //endregion

        //region Геттери
        @Override
        public boolean get(int rowIndex, int columnIndex) {
            if (rowIndex >= rowCount || columnIndex >= columnCount) {
                throw new ArrayIndexOutOfBoundsException();
            }
            return booleanMatrix.get(rowIndex + startRow, columnIndex + startColumn);
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
        public void set(int rowIndex, int columnIndex, boolean value) {
            if (rowIndex >= rowCount || columnIndex >= columnCount) {
                throw new ArrayIndexOutOfBoundsException();
            }
            booleanMatrix.set(rowIndex + startRow, columnIndex + startColumn, value);
        }
        //endregion

        //region Зрізи
        @Override
        public BooleanMatrixSlice slice(int startRow, int startColumn, int endRow, int endColumn) {
            if (startRow >= rowCount || endRow >= rowCount || endColumn >= columnCount || startColumn >= columnCount) {
                throw new ArrayIndexOutOfBoundsException();
            }
            return booleanMatrix.slice(startRow + this.startRow, startColumn + this.startColumn, endRow + this.startRow, endColumn + this.startColumn);
        }

        @Override
        public BooleanMatrixSlice slice(int[] rowIndexs, int[] columnIndexs) {
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
            return booleanMatrix.slice(rowIndexs, columnIndexs);
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
    public static BooleanMatrixSlice createSlice(BooleanMatrix matrix, int startRow, int startColumn, int endRow, int endColumn) {
        return new RectangleBooleanMatrixSlice(matrix, startRow, startColumn, endRow, endColumn);
    }

    public static BooleanMatrixSlice createSlice(BooleanMatrix matrix, int[] rowIndexs, int[] columnIndexs) {
        return new IndexBooleanMatrixSlice(matrix, rowIndexs, columnIndexs);
    }
    //endregion
}
