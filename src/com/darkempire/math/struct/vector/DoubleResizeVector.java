package com.darkempire.math.struct.vector;

import java.util.Arrays;

/**
 * Create in 10:53
 * Created by siredvin on 25.04.14.
 */
public class DoubleResizeVector extends DoubleVector {
    private double[] values;

    //region Конструктори
    public DoubleResizeVector(int size) {
        values = new double[size];
    }

    public DoubleResizeVector(double... values) {
        this.values = values;
    }
    //endregion

    //region Сеттери
    @Override
    public void set(int index, double value) {
        if (index >= values.length)
            return;
        values[index] = value;
    }
    //endregion

    //region Отримання підвектору
    @Override
    public DoubleVector subvector(int length) {
        return new DoubleResizeVector(Arrays.copyOf(values, length));
    }

    @Override
    public DoubleVector subvector(int startIndex, int length) {
        double[] temp = new double[length];
        System.arraycopy(values, startIndex, temp, 0, length);
        return new DoubleResizeVector(temp);
    }
    //endregion

    //region Геттери
    @Override
    public double get(int index) {
        if (index >= values.length)
            return 0;
        return values[index];
    }

    @Override
    public int getSize() {
        return values.length;
    }
    //endregion

    @Override
    public double scalar(DoubleVector vector) {
        int size = Math.min(values.length, vector.getSize());
        double result = 0;
        for (int i = 0; i < size; i++) {
            result += get(i) * vector.get(i);
        }
        return result;
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
    public DoubleVector iadd(DoubleVector resizeDoubleVector) {
        if (resizeDoubleVector.getSize() > values.length) {
            double[] temp = new double[resizeDoubleVector.getSize()];
            for (int i = 0; i < temp.length; i++) {
                temp[i] = get(i) + resizeDoubleVector.get(i);
            }
            this.values = temp;
            return this;
        }
        for (int i = 0; i < values.length; i++) {
            values[i] += resizeDoubleVector.get(i);
        }
        return this;
    }

    @Override
    public DoubleVector isubtract(DoubleVector resizeDoubleVector) {
        if (resizeDoubleVector.getSize() > values.length) {
            double[] temp = new double[resizeDoubleVector.getSize()];
            for (int i = 0; i < temp.length; i++) {
                temp[i] = get(i) - resizeDoubleVector.get(i);
            }
            this.values = temp;
            return this;
        }
        for (int i = 0; i < values.length; i++) {
            values[i] -= resizeDoubleVector.get(i);
        }
        return this;
    }
    //endregion

    //region Арифметичні операції
    @Override
    public DoubleVector add(DoubleVector resizeDoubleVector) {
        double[] p = new double[Math.max(values.length, resizeDoubleVector.getSize())];
        for (int i = 0; i < p.length; i++) {
            p[i] = get(i) + resizeDoubleVector.get(i);
        }
        return new DoubleResizeVector(p);
    }

    @Override
    public DoubleVector subtract(DoubleVector resizeDoubleVector) {
        double[] p = new double[Math.max(values.length, resizeDoubleVector.getSize())];
        for (int i = 0; i < p.length; i++) {
            p[i] = get(i) - resizeDoubleVector.get(i);
        }
        return new DoubleResizeVector(p);
    }

    @Override
    public DoubleVector negate() {
        double[] p = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            p[i] = -values[i];
        }
        return new DoubleResizeVector(p);
    }

    @Override
    public DoubleVector prod(double lambda) {
        double[] arr = new double[values.length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = values[i] * lambda;
        }
        return new DoubleResizeVector(arr);
    }
    //endregion

    @Override
    public DoubleVector clone() {
        return new DoubleResizeVector(values.clone());
    }

    @Override
    public double[] toRawArray() {
        return values.clone();
    }

    @Override
    public DoubleVector deepcopy() {
        return clone();
    }
}
