package com.darkempire.math.struct.vector;

import com.darkempire.anji.document.tex.ITeXObject;
import com.darkempire.anji.document.tex.TeXEventWriter;
import com.darkempire.anji.util.Util;
import com.darkempire.math.MathMachine;
import com.darkempire.math.exception.MatrixSizeException;
import com.darkempire.math.struct.LinearCalcable;
import com.darkempire.math.struct.function.interfaces.FDoubleVectorIndexDouble;
import com.darkempire.math.struct.function.interfaces.FIndexToDouble;
import com.darkempire.math.struct.matrix.DoubleFixedMatrix;
import com.darkempire.math.struct.matrix.DoubleMatrix;
import com.darkempire.math.utils.GeometryUtils;

import java.text.NumberFormat;
import java.util.StringJoiner;

/**
 * Create in 10:42
 * Created by siredvin on 25.04.14.
 */
public abstract class DoubleVector implements LinearCalcable<DoubleVector>, IDoubleVectorProvider, ITeXObject {
    //region Cеттери
    public abstract void set(int index, double value);
    //endregion

    //region Отримання частини вектора
    public abstract DoubleVector subvector(int length);

    public abstract DoubleVector subvector(int startIndex, int length);
    //endregion

    //region Геттери
    @Override
    public abstract double get(int index);

    @Override
    public abstract int getSize();
    //endregion

    public abstract double scalar(DoubleVector vector);

    public double norm() {
        return Math.sqrt(scalar(this));
    }

    //region Зрізи
    public DoubleVector sliceFrom(int startIndex) {
        return new DoubleSliceVector(startIndex, getSize() - 1, this);
    }

    public DoubleVector sliceTo(int endIndex) {
        return new DoubleSliceVector(endIndex, this);
    }

    public DoubleVector slice(int startIndex, int endIndex) {
        return new DoubleSliceVector(startIndex, endIndex, this);
    }
    //endregion

    //region Заповнювачі всього вектора
    public DoubleVector fill(double value) {
        int size = getSize();
        for (int i = 0; i < size; i++) {
            set(i, value);
        }
        return this;
    }

    public DoubleVector fill(FIndexToDouble function) {
        int size = getSize();
        for (int i = 0; i < size; i++) {
            set(i, function.calc(i));
        }
        return this;
    }

    public DoubleVector fill(FDoubleVectorIndexDouble function) {
        int size = getSize();
        for (int i = 0; i < size; i++) {
            set(i, function.calc(this, i));
        }
        return this;
    }
    //endregion

    //region Заповнення підвектора

    /**
     * Заповнення підвектору від початку (включаючи) до кінця (виключаючи)
     *
     * @param start початок
     * @param end   кінець
     * @param value значення
     * @return вектор
     */
    public DoubleVector fillSubvector(int start, int end, double value) {
        for (int i = start; i < end; i++) {
            set(i, value);
        }
        return this;
    }

    /**
     * Заповнення підвектору від початку (включаючи) до кінця (виключаючи)
     *
     * @param start    початок
     * @param end      кінець
     * @param function функція
     * @return вектор
     */
    public DoubleVector fillSubvector(int start, int end, FIndexToDouble function) {
        for (int i = start; i < end; i++) {
            set(i, function.calc(i));
        }
        return this;
    }

    /**
     * Заповнення підвектору від початку (включаючи) до кінця (виключаючи)
     *
     * @param start    початок
     * @param end      кінець
     * @param function функція
     * @return вектор
     */
    public DoubleVector fillSubvector(int start, int end, FDoubleVectorIndexDouble function) {
        for (int i = start; i < end; i++) {
            set(i, function.calc(this, i));
        }
        return this;
    }
    //endregion

    //region Системні функції
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o instanceof DoubleVector) {
            DoubleVector vector = (DoubleVector) o;
            if (getSize() != vector.getSize())
                return false;
            int size = getSize();
            for (int i = 0; i < size; i++) {
                if (get(i) != vector.get(i))
                    return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int size = getSize();
        sb.append('{');
        for (int i = 0; i < size; i++) {
            sb.append(get(i));
            sb.append(',');
        }
        Util.removeLastChar(sb);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public abstract DoubleVector clone();

    public abstract double[] toRawArray();
    //endregion

    //region Функції виведення

    @Override
    public void write(TeXEventWriter out) {
        int size = getSize();
        NumberFormat format = out.getNumberFormat();
        out.openEnvironment("pmatrix");
        StringJoiner joiner = new StringJoiner("\\\\");
        for (int i = 0; i < size; i++) {
            joiner.add(format.format(get(i)));
        }
        out.text(joiner.toString());
        out.closeEnvironment();
    }

    //endregion

    public DoubleVector irotate(int i, int j, double angle) {
        return GeometryUtils.rotateVector(this, i, j, angle);
    }

    //region Арифметичні операції з присвоєнням
    public DoubleVector iprod(double lambda) {
        int size = getSize();
        for (int i = 0; i < size; i++) {
            set(i, get(i) * lambda);
        }
        return this;
    }

    //endregion

    public DoubleMatrix mult(DoubleVector vector) {
        int rowCount = getSize();
        int columnCount = vector.getSize();
        DoubleFixedMatrix result = DoubleFixedMatrix.createInstance(rowCount, columnCount);
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                result.set(rowIndex, columnIndex, get(rowIndex) * vector.get(columnIndex));
            }
        }
        return result;
    }

    public DoubleVector mult(DoubleMatrix matrix) {
        int rowCount = matrix.getRowCount();
        int columnCount = matrix.getColumnCount();
        int size = getSize();
        if (size != rowCount) {
            throw new MatrixSizeException(MatrixSizeException.MATRIX_SIZE_MULTY_MISMATCH);
        }
        DoubleVector result = new DoubleFixedVector(columnCount);
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            double temp = 0;
            for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                temp += get(rowIndex) * matrix.get(rowIndex, columnIndex);
            }
            result.set(columnIndex, temp);
        }
        return result;
    }

    public abstract DoubleVector prod(double lambda);
}
