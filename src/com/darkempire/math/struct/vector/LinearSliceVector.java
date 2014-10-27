package com.darkempire.math.struct.vector;

import com.darkempire.math.exception.MatrixSizeException;
import com.darkempire.math.struct.LinearCalcable;

/**
 * Created by siredvin on 16.09.14.
 */
public class LinearSliceVector<T extends LinearCalcable<T>> extends LinearVector<T> {
    private int startIndex;
    private int endIndex;
    private LinearVector<T> vector;

    //region Конструктори
    public LinearSliceVector(int startIndex, int endIndex, LinearVector<T> vector) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.vector = vector;
    }

    public LinearSliceVector(int endIndex, LinearVector<T> vector) {
        this.endIndex = endIndex;
        this.vector = vector;
    }
    //endregion

    @Override
    public void set(int index, T value) {
        index += startIndex;
        if (index > endIndex)
            throw new IndexOutOfBoundsException();
        vector.set(index, value);
    }

    @Override
    public LinearVector<T> subvector(int length) {
        return vector.subvector(startIndex, length);
    }

    @Override
    public LinearVector<T> subvector(int startIndex, int length) {
        return vector.subvector(startIndex + this.startIndex, length);
    }

    @Override
    public T get(int index) {
        index += startIndex;
        if (index > endIndex)
            throw new IndexOutOfBoundsException();
        return vector.get(index);
    }

    @Override
    public int getSize() {
        return endIndex - startIndex + 1;
    }


    @Override
    public LinearVector<T> deepcopy() {
        return clone();
    }

    @Override
    public LinearVector<T> clone() {
        int size = getSize();
        LinearVector<T> result = new LinearFixedVector<>((T[]) new LinearCalcable[size]);
        for (int i = 0; i < size; i++) {
            result.set(i, get(i));
        }
        return result;
    }

    @Override
    public LinearVector<T> inegate() {
        int size = getSize();
        for (int i = 0; i < size; i++) {
            get(i).inegate();
        }
        return this;
    }

    @Override
    public LinearVector<T> iadd(LinearVector<T> doubleVector) {
        int size = getSize();
        if (doubleVector.getSize() != size)
            throw new MatrixSizeException(MatrixSizeException.MATRIX_SIZE_MISMATCH);
        for (int i = 0; i < size; i++) {
            get(i).iadd(doubleVector.get(i));
        }
        return this;
    }

    @Override
    public LinearVector<T> isubtract(LinearVector<T> doubleVector) {
        int size = getSize();
        if (doubleVector.getSize() != size)
            throw new MatrixSizeException(MatrixSizeException.MATRIX_SIZE_MISMATCH);
        for (int i = 0; i < size; i++) {
            get(i).isubtract(doubleVector.get(i));
        }
        return this;
    }

    @Override
    public LinearVector<T> add(LinearVector<T> doubleVector) {
        int size = getSize();
        if (doubleVector.getSize() != size)
            throw new MatrixSizeException(MatrixSizeException.MATRIX_SIZE_MISMATCH);
        LinearVector<T> result = new LinearFixedVector<>((T[]) new LinearCalcable[size]);
        for (int i = 0; i < size; i++) {
            result.set(i, doubleVector.get(i).add(get(i)));
        }
        return result;
    }

    @Override
    public LinearVector<T> subtract(LinearVector<T> doubleVector) {
        int size = getSize();
        if (doubleVector.getSize() != size)
            throw new MatrixSizeException(MatrixSizeException.MATRIX_SIZE_MISMATCH);
        LinearVector<T> result = new LinearFixedVector<>((T[]) new LinearCalcable[size]);
        for (int i = 0; i < size; i++) {
            result.set(i, doubleVector.get(i).subtract(get(i)));
        }
        return result;
    }

    @Override
    public LinearVector<T> negate() {
        int size = getSize();
        LinearVector<T> result = new LinearFixedVector<>((T[]) new LinearCalcable[size]);
        for (int i = 0; i < size; i++) {
            result.set(i, get(i).negate());
        }
        return result;
    }
}
