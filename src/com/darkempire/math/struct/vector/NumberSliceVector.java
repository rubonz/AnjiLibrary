package com.darkempire.math.struct.vector;

import com.darkempire.math.exception.MatrixSizeException;
import com.darkempire.math.struct.*;
import com.darkempire.math.struct.Number;

/**
 * Created by siredvin on 16.09.14.
 */
public class NumberSliceVector<T extends com.darkempire.math.struct.Number<T>> extends NumberVector<T> {
    private int startIndex;
    private int endIndex;
    private NumberVector<T> vector;

    //region Конструктори
    public NumberSliceVector(int startIndex, int endIndex, NumberVector<T> vector) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.vector = vector;
    }

    public NumberSliceVector(int endIndex, NumberVector<T> vector) {
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
    public NumberVector<T> subvector(int length) {
        return vector.subvector(startIndex, length);
    }

    @Override
    public NumberVector<T> subvector(int startIndex, int length) {
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
    public NumberVector<T> deepcopy() {
        return clone();
    }

    @Override
    public T scalar(NumberVector<T> vector) {
        int size = Math.min(getSize(), vector.getSize());
        T result = null;
        for (int i = 0; i < size; i++) {
            if (result == null) {
                result = vector.get(i).multiply(get(i));
                continue;
            }
            result.iadd(vector.get(i).multiply(get(i)));
        }
        return result;
    }

    @Override
    public NumberVector<T> clone() {
        int size = getSize();
        NumberVector<T> result = new NumberFixedVector<>((T[]) new Number[size]);
        for (int i = 0; i < size; i++) {
            result.set(i, get(i));
        }
        return result;
    }

    @Override
    public NumberVector<T> inegate() {
        int size = getSize();
        for (int i = 0; i < size; i++) {
            get(i).inegate();
        }
        return this;
    }

    @Override
    public NumberVector<T> iadd(NumberVector<T> doubleVector) {
        int size = getSize();
        if (doubleVector.getSize() != size)
            throw new MatrixSizeException(MatrixSizeException.MATRIX_SIZE_MISMATCH);
        for (int i = 0; i < size; i++) {
            get(i).iadd(doubleVector.get(i));
        }
        return this;
    }

    @Override
    public NumberVector<T> isubtract(NumberVector<T> doubleVector) {
        int size = getSize();
        if (doubleVector.getSize() != size)
            throw new MatrixSizeException(MatrixSizeException.MATRIX_SIZE_MISMATCH);
        for (int i = 0; i < size; i++) {
            get(i).isubtract(doubleVector.get(i));
        }
        return this;
    }

    @Override
    public NumberVector<T> add(NumberVector<T> doubleVector) {
        int size = getSize();
        if (doubleVector.getSize() != size)
            throw new MatrixSizeException(MatrixSizeException.MATRIX_SIZE_MISMATCH);
        NumberVector<T> result = new NumberFixedVector<>((T[]) new Number[size]);
        for (int i = 0; i < size; i++) {
            result.set(i, doubleVector.get(i).add(get(i)));
        }
        return result;
    }

    @Override
    public NumberVector<T> subtract(NumberVector<T> doubleVector) {
        int size = getSize();
        if (doubleVector.getSize() != size)
            throw new MatrixSizeException(MatrixSizeException.MATRIX_SIZE_MISMATCH);
        NumberVector<T> result = new NumberFixedVector<>((T[]) new Number[size]);
        ;
        for (int i = 0; i < size; i++) {
            result.set(i, doubleVector.get(i).subtract(get(i)));
        }
        return result;
    }

    @Override
    public NumberVector<T> negate() {
        int size = getSize();
        NumberVector<T> result = new NumberFixedVector<>((T[]) new Number[size]);
        ;
        for (int i = 0; i < size; i++) {
            result.set(i, get(i).negate());
        }
        return result;
    }
}
