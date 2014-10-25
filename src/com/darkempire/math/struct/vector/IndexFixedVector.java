package com.darkempire.math.struct.vector;

import com.darkempire.math.exception.SizeMissmatchException;

/**
 * Create in 10:53
 * Created by siredvin on 25.04.14.
 */
public class IndexFixedVector extends IndexVector {
    private int[] values;

    //region Конструктори
    public IndexFixedVector(int size) {
        values = new int[size];
    }

    public IndexFixedVector(int... values) {
        this.values = values;
    }
    //endregion

    //region Сеттери
    @Override
    public void set(int index, int value) {
        values[index] = value;
    }
    //endregion

    //region Геттери
    @Override
    public int get(int index) {
        return values[index];
    }

    @Override
    public int getSize() {
        return values.length;
    }
    //endregion

    @Override
    public int scalar(IndexVector vector) {
        if (vector.getSize() != values.length)
            throw new SizeMissmatchException();
        int result = 0;
        for (int i = 0; i < values.length; i++) {
            result += values[i] * vector.get(i);
        }
        return result;
    }

    //region Арифметичні операції з присвоєнням
    @Override
    public IndexVector inegate() {
        for (int i = 0; i < values.length; i++) {
            values[i] = -values[i];
        }
        return this;
    }

    @Override
    public IndexVector iadd(IndexVector vector) {
        if (vector.getSize() != values.length)
            throw new SizeMissmatchException();
        for (int i = 0; i < values.length; i++) {
            values[i] += vector.get(i);
        }
        return this;
    }

    @Override
    public IndexVector isubtract(IndexVector vector) {
        if (vector.getSize() != values.length)
            throw new SizeMissmatchException();
        for (int i = 0; i < values.length; i++) {
            values[i] -= vector.get(i);
        }
        return this;
    }
    //endregion

    //region Арифметичні операції
    @Override
    public IndexVector add(IndexVector vector) {
        if (vector.getSize() != values.length)
            throw new SizeMissmatchException();
        int[] p = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            p[i] = values[i] + vector.get(i);
        }
        return new IndexFixedVector(p);
    }

    @Override
    public IndexVector subtract(IndexVector vector) {
        if (vector.getSize() != values.length)
            throw new SizeMissmatchException();
        int[] p = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            p[i] = values[i] - vector.get(i);
        }
        return new IndexFixedVector(p);
    }

    @Override
    public IndexVector negate() {
        int[] p = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            p[i] = -values[i];
        }
        return new IndexFixedVector(p);
    }
    //endregion

    @Override
    public IndexVector clone() {
        return new IndexFixedVector(values.clone());
    }

    @Override
    public int[] toRawArray() {
        return values;
    }

    @Override
    public IndexVector deepcopy() {
        return clone();
    }
}
