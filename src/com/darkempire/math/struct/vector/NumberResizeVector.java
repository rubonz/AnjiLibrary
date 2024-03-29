package com.darkempire.math.struct.vector;

import java.util.Arrays;

/**
 * Create in 10:53
 * Created by siredvin on 25.04.14.
 */
public class NumberResizeVector<T extends com.darkempire.math.struct.Number<T>> extends NumberVector<T> {
    private T[] values;

    //region Конструктори
    public NumberResizeVector(T[] values) {
        this.values = values;
    }
    //endregion

    //region Сеттери
    @Override
    public void set(int index, T value) {
        if (index >= values.length)
            return;
        values[index] = value;
    }
    //endregion

    //region Отримання підвекторів
    @Override
    public NumberVector<T> subvector(int length) {
        return new NumberResizeVector<T>(Arrays.copyOf(values, length));
    }

    @Override
    public NumberVector<T> subvector(int startIndex, int length) {
        return new NumberResizeVector<T>(Arrays.copyOfRange(values, startIndex, length + startIndex));
    }
    //endregion

    //region Геттери
    @Override
    public T get(int index) {
        if (index >= values.length)
            return values[0].getZero();
        return values[index];
    }

    @Override
    public int getSize() {
        return values.length;
    }
    //endregion

    @Override
    public T scalar(NumberVector<T> vector) {
        int size = Math.min(values.length, vector.getSize());
        T result = get(0).multiply(vector.get(0));
        for (int i = 1; i < size; i++) {
            result.iadd(get(i).multiply(vector.get(i)));
        }
        return result;
    }

    //region Арифметичні операції з присвоєнням
    @Override
    public NumberVector<T> inegate() {
        for (T value : values) {
            value.inegate();
        }
        return this;
    }

    @Override
    public NumberVector<T> iadd(NumberVector<T> resizeLinearVector) {
        if (resizeLinearVector.getSize() > values.length) {
            T[] temp = Arrays.copyOf(values, resizeLinearVector.getSize());
            for (int i = 0; i < values.length; i++) {
                temp[i].iadd(resizeLinearVector.get(i));
            }
            for (int i = values.length; i < temp.length; i++) {
                temp[i] = resizeLinearVector.get(i).deepcopy();
            }
            this.values = temp;
            return this;
        }
        for (int i = 0; i < values.length; i++) {
            values[i].iadd(resizeLinearVector.get(i));
        }
        return this;
    }

    @Override
    public NumberVector<T> isubtract(NumberVector<T> resizeLinearVector) {
        if (resizeLinearVector.getSize() > values.length) {
            T[] temp = Arrays.copyOf(values, resizeLinearVector.getSize());
            for (int i = 0; i < values.length; i++) {
                temp[i].isubtract(resizeLinearVector.get(i));
            }
            for (int i = values.length; i < temp.length; i++) {
                temp[i] = resizeLinearVector.get(i).deepcopy().inegate();
            }
            this.values = temp;
            return this;
        }
        for (int i = 0; i < values.length; i++) {
            values[i].isubtract(resizeLinearVector.get(i));
        }
        return this;
    }
    //endregion

    //region Арифметичні операції
    @Override
    public NumberVector<T> add(NumberVector<T> resizeLinearVector) {
        T[] p = Arrays.copyOf(values, Math.max(values.length, resizeLinearVector.getSize()));
        for (int i = 0; i < p.length; i++) {
            p[i].iadd(resizeLinearVector.get(i));
        }
        return new NumberResizeVector<>(p);
    }

    @Override
    public NumberVector<T> subtract(NumberVector<T> resizeLinearVector) {
        T[] p = Arrays.copyOf(values, Math.max(values.length, resizeLinearVector.getSize()));
        for (int i = 0; i < p.length; i++) {
            p[i].isubtract(resizeLinearVector.get(i));
        }
        return new NumberResizeVector<>(p);
    }

    @Override
    public NumberVector<T> negate() {
        T[] p = Arrays.copyOf(values, values.length);
        for (int i = 0; i < values.length; i++) {
            p[i].inegate();
        }
        return new NumberResizeVector<>(p);
    }
    //endregion

    @Override
    public NumberVector<T> clone() {
        T[] value = values.clone();
        for (int i = 0; i < value.length; i++) {
            value[i] = value[i].deepcopy();
        }
        return new NumberResizeVector<>(values.clone());
    }

    @Override
    public NumberVector<T> deepcopy() {
        return clone();
    }
}
