package com.darkempire.math.struct.vector;

/**
 * Created by siredvin on 16.09.14.
 */
public class DoubleSliceVector extends DoubleVector {
    private int startIndex;
    private int endIndex;
    private DoubleVector vector;

    //region Конструктори
    public DoubleSliceVector(int startIndex, int endIndex, DoubleVector vector) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.vector = vector;
    }

    public DoubleSliceVector(int endIndex, DoubleVector vector) {
        this.endIndex = endIndex;
        this.vector = vector;
    }
    //endregion

    @Override
    public void set(int index, double value) {
        index += startIndex;
        if (index > endIndex)
            throw new IndexOutOfBoundsException();
        vector.set(index, value);
    }

    @Override
    public DoubleVector subvector(int length) {
        return vector.subvector(startIndex, length);
    }

    @Override
    public DoubleVector subvector(int startIndex, int length) {
        return vector.subvector(startIndex + this.startIndex, length);
    }

    @Override
    public double get(int index) {
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
    public double scalar(DoubleVector vector) {
        int size = Math.min(getSize(), vector.getSize());
        double result = 0;
        for (int i = 0; i < size; i++) {
            result += vector.get(i) * get(i);
        }
        return result;
    }

    @Override
    public DoubleVector clone() {
        return null;//TODO:обробити
    }

    @Override
    public DoubleVector prod(double lambda) {
        return null;//TODO:обробити
    }

    @Override
    public DoubleVector inegate() {
        return null;//TODO:обробити
    }

    @Override
    public DoubleVector iadd(DoubleVector doubleVector) {
        return null;//TODO:обробити
    }

    @Override
    public DoubleVector isubtract(DoubleVector doubleVector) {
        return null;//TODO:обробити
    }

    @Override
    public DoubleVector add(DoubleVector doubleVector) {
        return null;//TODO:обробити
    }

    @Override
    public DoubleVector subtract(DoubleVector doubleVector) {
        return null;//TODO:обробити
    }

    @Override
    public DoubleVector negate() {
        return null;//TODO:обробити
    }
}
