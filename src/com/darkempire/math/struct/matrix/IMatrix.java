package com.darkempire.math.struct.matrix;


/**
 * Create in 8:57
 * Created by siredvin on 18.12.13.
 */
public interface IMatrix<T extends IMatrix<T, K>, K> extends Cloneable {
    public K getEl(int i, int j);

    default public K getEl(MatrixIndex index) {
        return getEl(index.getColumnIndex(), index.getRowIndex());
    }

    public void setEl(int i, int j, K value);

    public int getColumnCount();

    public int getRowCount();

    public IMatrix<T, K> switchRows(int i1, int i2);

    public IMatrix<T, K> switchColumns(int j1, int j2);

    default public IMatrix<T, K> itranspose() {
        int n = getColumnCount();
        int m = getRowCount();
        if (n != m)
            throw new ArrayIndexOutOfBoundsException();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                K temp = getEl(i, j);
                setEl(i, j, getEl(j, i));
                setEl(j, i, temp);
            }
        }
        return this;
    }

    default public boolean isSymmetric() {
        boolean k = false;
        int n = getColumnCount();
        int m = getRowCount();
        if (n == m) {
            k = true;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (!getEl(i, j).equals(getEl(j, i))) {
                        k = false;
                        break;
                    }
                }
                if (!k)
                    break;
            }
        }
        return k;
    }
}
