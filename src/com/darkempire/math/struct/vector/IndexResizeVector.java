package com.darkempire.math.struct.vector;

/**
 * Create in 10:53
 * Created by siredvin on 25.04.14.
 */
public class IndexResizeVector extends IndexVector {
    private int[] values;

    //region Конструктори
    public IndexResizeVector(int size) {
        values = new int[size];
    }

    public IndexResizeVector(int... values) {
        this.values = values;
    }
    //endregion

    //region Сеттери
    @Override
    public void set(int index, int value) {
        if (index >= values.length)
            return;
        values[index] = value;
    }
    //endregion

    //region Геттери
    @Override
    public int get(int index) {
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
    public int scalar(IndexVector vector) {
        int size = Math.min(values.length, vector.getSize());
        int result = 0;
        for (int i = 0; i < size; i++) {
            result += get(i) * vector.get(i);
        }
        return result;
    }

    //region Арифметичні операції з присвоєння
    @Override
    public IndexVector inegate() {
        for (int i = 0; i < values.length; i++) {
            values[i] = -values[i];
        }
        return this;
    }

    @Override
    public IndexVector iadd(IndexVector resizeIndexVector) {
        if (resizeIndexVector.getSize() > values.length) {
            int[] temp = new int[resizeIndexVector.getSize()];
            for (int i = 0; i < temp.length; i++) {
                temp[i] = get(i) + resizeIndexVector.get(i);
            }
            this.values = temp;
            return this;
        }
        for (int i = 0; i < values.length; i++) {
            values[i] += resizeIndexVector.get(i);
        }
        return this;
    }

    @Override
    public IndexVector isubtract(IndexVector resizeIndexVector) {
        if (resizeIndexVector.getSize() > values.length) {
            int[] temp = new int[resizeIndexVector.getSize()];
            for (int i = 0; i < temp.length; i++) {
                temp[i] = get(i) - resizeIndexVector.get(i);
            }
            this.values = temp;
            return this;
        }
        for (int i = 0; i < values.length; i++) {
            values[i] -= resizeIndexVector.get(i);
        }
        return this;
    }
    //endregion

    //region Арифметичні операції
    @Override
    public IndexVector add(IndexVector resizeIndexVector) {
        int[] p = new int[Math.max(values.length, resizeIndexVector.getSize())];
        for (int i = 0; i < p.length; i++) {
            p[i] = get(i) + resizeIndexVector.get(i);
        }
        return new IndexResizeVector(p);
    }

    @Override
    public IndexVector subtract(IndexVector resizeIndexVector) {
        int[] p = new int[Math.max(values.length, resizeIndexVector.getSize())];
        for (int i = 0; i < p.length; i++) {
            p[i] = get(i) - resizeIndexVector.get(i);
        }
        return new IndexResizeVector(p);
    }

    @Override
    public IndexVector negate() {
        int[] p = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            p[i] = -values[i];
        }
        return new IndexResizeVector(p);
    }
    //endregion

    @Override
    public IndexVector clone() {
        return new IndexResizeVector(values.clone());
    }
}
