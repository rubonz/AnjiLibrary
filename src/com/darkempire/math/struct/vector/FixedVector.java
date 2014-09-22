package com.darkempire.math.struct.vector;

import java.util.Arrays;

/**
 * Create in 10:53
 * Created by siredvin on 25.04.14.
 */
public class FixedVector<T> extends Vector<T> {
    private T[] values;

    public FixedVector(T[] values) {
        this.values = values;
    }

    @Override
    public void set(int index, T value) {
        values[index] = value;
    }

    @Override
    public Vector<T> subvector(int length) {
        return new FixedVector<>(Arrays.copyOf(values, length));
    }

    @Override
    public Vector<T> subvector(int startIndex, int length) {
        return new FixedVector<>(Arrays.copyOfRange(values, startIndex, length + startIndex));
    }

    @Override
    public T get(int index) {
        return values[index];
    }


    @Override
    public int getSize() {
        return values.length;
    }
}
