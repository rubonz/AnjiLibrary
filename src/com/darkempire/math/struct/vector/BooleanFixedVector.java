package com.darkempire.math.struct.vector;

import com.darkempire.math.exception.SizeMissmatchException;

import java.util.Arrays;

/**
 * Create in 10:53
 * Created by siredvin on 25.04.14.
 */
public class BooleanFixedVector extends BooleanVector {
    private boolean[] values;

    //region Конструктори
    public BooleanFixedVector(int size) {
        values = new boolean[size];
    }

    public BooleanFixedVector(boolean... values) {
        this.values = values;
    }
    //endregion

    //region Сеттери
    @Override
    public void set(int index, boolean value) {
        values[index] = value;
    }
    //endregion

    //region Отримання частини вектора
    @Override
    public BooleanVector subvector(int length) {
        return new BooleanFixedVector(Arrays.copyOf(values, length));
    }

    @Override
    public BooleanVector subvector(int startIndex, int length) {
        boolean[] temp = new boolean[length];
        System.arraycopy(values, startIndex, temp, 0, length);
        return new BooleanFixedVector(temp);
    }
    //endregion

    //region Геттери
    @Override
    public boolean get(int index) {
        return values[index];
    }

    @Override
    public int getSize() {
        return values.length;
    }
    //endregion

    @Override
    public boolean scalar(BooleanVector vector) {
        if (vector.getSize() != values.length)
            throw new SizeMissmatchException();
        boolean result = false;
        for (int i = 0; i < values.length; i++) {
            result = result || (values[i] && vector.get(i));
        }
        return result;
    }

    @Override
    public BooleanVector clone() {
        return new BooleanFixedVector(values.clone());
    }

    //region Арифметичні операції з присвоєнням
    @Override
    public BooleanVector inegate() {
        for (int i = 0; i < values.length; i++) {
            values[i] = !values[i];
        }
        return this;
    }

    @Override
    public BooleanVector iadd(BooleanVector vector) {
        if (vector.getSize() != values.length)
            throw new SizeMissmatchException();
        for (int i = 0; i < values.length; i++) {
            values[i] = values[i] || vector.get(i);
        }
        return this;
    }

    @Override
    public BooleanVector isubtract(BooleanVector vector) {
        if (vector.getSize() != values.length)
            throw new SizeMissmatchException();
        for (int i = 0; i < values.length; i++) {
            values[i] = values[i] ^ vector.get(i);
        }
        return this;
    }
    //endregion

    //region Арифметичні операції
    @Override
    public BooleanVector add(BooleanVector vector) {
        if (vector.getSize() != values.length)
            throw new SizeMissmatchException();
        boolean[] p = new boolean[values.length];
        for (int i = 0; i < values.length; i++) {
            p[i] = values[i] || vector.get(i);
        }
        return new BooleanFixedVector(p);
    }

    @Override
    public BooleanVector subtract(BooleanVector vector) {
        if (vector.getSize() != values.length)
            throw new SizeMissmatchException();
        boolean[] p = new boolean[values.length];
        for (int i = 0; i < values.length; i++) {
            p[i] = values[i] ^ vector.get(i);
        }
        return new BooleanFixedVector(p);
    }

    @Override
    public BooleanVector negate() {
        boolean[] p = new boolean[values.length];
        for (int i = 0; i < values.length; i++) {
            p[i] = !values[i];
        }
        return new BooleanFixedVector(p);
    }

    @Override
    public BooleanVector prod(boolean lambda) {
        boolean[] arr = new boolean[values.length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = values[i] && lambda;
        }
        return new BooleanFixedVector(arr);
    }
    //endregion


}
