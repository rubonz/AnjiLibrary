package com.darkempire.math.struct.matrix;

/**
 * Create in 12:03
 * Created by siredvin on 19.12.13.
 */
public class MatrixIndex {
    private int columnIndex;
    private int rowIndex;
    private int hash = 0;

    //region Системні методи
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MatrixIndex)) return false;

        MatrixIndex that = (MatrixIndex) o;

        return columnIndex == that.columnIndex && rowIndex == that.rowIndex;

    }

    @Override
    public int hashCode() {
        if (hash == 0) {
            hash = columnIndex;
            hash = 31 * hash + rowIndex;
        }
        return hash;
    }

    @Override
    public String toString() {
        return "(" + rowIndex + "," + columnIndex + ")";
    }
    //endregion

    //region Геттери
    public int getColumnIndex() {

        return columnIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public MatrixIndex(int rowIndex, int columnIndex) {
        this.columnIndex = columnIndex;
        this.rowIndex = rowIndex;
    }
    //endregion
}
