package com.darkempire.math.struct.vector;

import com.darkempire.math.exception.SizeMissmatchException;
import com.darkempire.math.struct.LinearCalcable;

import java.util.Arrays;

/**
 * Create in 10:53
 * Created by siredvin on 25.04.14.
 */
public class LinearFixedVector<T extends LinearCalcable<T>> extends LinearVector<T> {
    private T[] values;

    //region Конструктор
    public LinearFixedVector(T[] values) {
        this.values = values;
    }
    //endregion

    //region Сеттери
    @Override
    public void set(int index, T value) {
        values[index] = value;
    }
    //endregion

    //region Отримання підвекторів
    @Override
    public LinearVector<T> subvector(int length) {
        return new LinearFixedVector<T>(Arrays.copyOf(values, length));
    }

    @Override
    public LinearVector<T> subvector(int startIndex, int length) {
        return new LinearFixedVector<T>(Arrays.copyOfRange(values, startIndex, length + startIndex));
    }
    //endregion

    //region Геттери
    @Override
    public T get(int index) {
        return values[index];
    }

    @Override
    public int getSize() {
        return values.length;
    }
    //endregion

    @Override
    public LinearVector<T> clone() {
        T[] value = values.clone();
        for (int i = 0; i < value.length; i++) {
            value[i] = value[i].deepcopy();
        }
        return new LinearFixedVector<>(values.clone());
    }

    //region Арифметичні операції з присвоєнням
    @Override
    public LinearVector<T> inegate() {
        for (T value : values) {
            value.inegate();
        }
        return this;
    }

    @Override
    public LinearVector<T> iadd(LinearVector<T> vector) {
        if (vector.getSize() != values.length)
            throw new SizeMissmatchException();
        for (int i = 0; i < values.length; i++) {
            values[i].iadd(vector.get(i));
        }
        return this;
    }

    @Override
    public LinearVector<T> isubtract(LinearVector<T> vector) {
        if (vector.getSize() != values.length)
            throw new SizeMissmatchException();
        for (int i = 0; i < values.length; i++) {
            values[i].isubtract(vector.get(i));
        }
        return this;
    }
    //endregion

    //region Арифметичні операції
    @Override
    public LinearVector<T> add(LinearVector<T> vector) {
        if (vector.getSize() != values.length)
            throw new SizeMissmatchException();
        T[] p = Arrays.copyOf(values, values.length);
        for (int i = 0; i < values.length; i++) {
            p[i].iadd(vector.get(i));
        }
        return new LinearFixedVector<>(p);
    }

    @Override
    public LinearVector<T> subtract(LinearVector<T> vector) {
        if (vector.getSize() != values.length)
            throw new SizeMissmatchException();
        T[] p = Arrays.copyOf(values, values.length);
        for (int i = 0; i < values.length; i++) {
            p[i].isubtract(vector.get(i));
        }
        return new LinearFixedVector<>(p);
    }

    @Override
    public LinearVector<T> negate() {
        T[] p = Arrays.copyOf(values, values.length);
        for (int i = 0; i < values.length; i++) {
            p[i].inegate();
        }
        return new LinearFixedVector<>(p);
    }
    //endregion

    @Override
    public LinearVector<T> deepcopy() {
        return clone();
    }
}
