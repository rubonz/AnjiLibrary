package com.darkempire.math.struct.vector;

import com.darkempire.math.exception.SizeMissmatchException;

import java.util.Arrays;

/**
 * Create in 10:53
 * Created by siredvin on 25.04.14.
 */
public class DoubleFixedVector extends DoubleVector {
    private double[] values;

    //region Конструктори
    public DoubleFixedVector(int size) {
        values = new double[size];
    }

    public DoubleFixedVector(double... values) {
        this.values = values;
    }
    //endregion

    //region Сеттери
    @Override
    public void set(int index, double value) {
        values[index] = value;
    }
    //endregion

    //region Отримання частини вектора
    @Override
    public DoubleVector subvector(int length) {
        return new DoubleFixedVector(Arrays.copyOf(values, length));
    }

    @Override
    public DoubleVector subvector(int startIndex, int length) {
        double[] temp = new double[length];
        System.arraycopy(values, startIndex, temp, 0, length);
        return new DoubleFixedVector(temp);
    }
    //endregion

    //region Геттери
    @Override
    public double get(int index) {
        return values[index];
    }

    @Override
    public int getSize() {
        return values.length;
    }
    //endregion

    @Override
    public double scalar(DoubleVector vector) {
        if (vector.getSize() != values.length)
            throw new SizeMissmatchException();
        double result = 0;
        for (int i = 0; i < values.length; i++) {
            result += values[i] * vector.get(i);
        }
        return result;
    }

    @Override
    public DoubleVector clone() {
        return new DoubleFixedVector(values.clone());
    }

    @Override
    public double[] toRawArray() {
        return values.clone();
    }

    //region Арифметичні операції з присвоєнням
    @Override
    public DoubleVector inegate() {
        for (int i = 0; i < values.length; i++) {
            values[i] = -values[i];
        }
        return this;
    }

    @Override
    public DoubleVector iadd(DoubleVector vector) {
        if (vector.getSize() != values.length)
            throw new SizeMissmatchException();
        for (int i = 0; i < values.length; i++) {
            values[i] += vector.get(i);
        }
        return this;
    }

    @Override
    public DoubleVector isubtract(DoubleVector vector) {
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
    public DoubleVector add(DoubleVector vector) {
        if (vector.getSize() != values.length)
            throw new SizeMissmatchException();
        double[] p = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            p[i] = values[i] + vector.get(i);
        }
        return new DoubleFixedVector(p);
    }

    @Override
    public DoubleVector subtract(DoubleVector vector) {
        if (vector.getSize() != values.length)
            throw new SizeMissmatchException();
        double[] p = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            p[i] = values[i] - vector.get(i);
        }
        return new DoubleFixedVector(p);
    }

    @Override
    public DoubleVector negate() {
        double[] p = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            p[i] = -values[i];
        }
        return new DoubleFixedVector(p);
    }

    @Override
    public DoubleVector prod(double lambda) {
        double[] arr = new double[values.length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = values[i] * lambda;
        }
        return new DoubleFixedVector(arr);
    }
    //endregion

    @Override
    public DoubleVector deepcopy() {
        return clone();
    }


}
