package com.darkempire.math.struct.vector;

import com.darkempire.anji.util.Util;
import com.darkempire.math.struct.LinearCalcable;
import com.darkempire.math.struct.function.interfaces.FIndexToSome;
import com.darkempire.math.struct.function.interfaces.FVectorAndIndexToSome;

/**
 * Create in 10:42
 * Created by siredvin on 25.04.14.
 */
public abstract class LinearVector<T extends LinearCalcable<T>> implements LinearCalcable<LinearVector<T>>, IVectorProvider<T> {
    //region Сеттери
    public abstract void set(int index, T value);
    //endregion

    //region Отримання підвекторів
    public abstract LinearVector<T> subvector(int length);

    public abstract LinearVector<T> subvector(int startIndex, int length);
    //endregion

    //region Геттери
    @Override
    public abstract T get(int index);

    @Override
    public abstract int getSize();
    //endregion

    //region Зрізи
    public LinearVector<T> sliceFrom(int startIndex) {
        return new LinearSliceVector<>(startIndex, getSize() - 1, this);
    }

    public LinearVector<T> sliceTo(int endIndex) {
        return new LinearSliceVector<>(endIndex, this);
    }

    public LinearVector<T> slice(int startIndex, int endIndex) {
        return new LinearSliceVector<>(startIndex, endIndex, this);
    }
    //endregion

    //region Заповнення
    public LinearVector fill(T value) {
        int size = getSize();
        for (int i = 0; i < size; i++) {
            set(i, value.deepcopy());
        }
        return this;
    }


    public LinearVector<T> fill(FIndexToSome<T> function) {
        int size = getSize();
        for (int i = 0; i < size; i++) {
            set(i, function.calc(i));
        }
        return this;
    }

    public LinearVector<T> fill(FVectorAndIndexToSome<T> function) {
        int size = getSize();
        for (int i = 0; i < size; i++) {
            set(i, function.calc(this, i));
        }
        return this;
    }
    //endregion

    //region Заповнення частин векторів

    /**
     * Заповнення підвектору від початку (включаючи) до кінця (виключаючи)
     *
     * @param start початок
     * @param end   кінець
     * @param value значення
     * @return вектор
     */
    public LinearVector<T> fillSubvector(int start, int end, T value) {
        for (int i = start; i < end; i++) {
            set(i, value.deepcopy());
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
    public LinearVector<T> fillSubvector(int start, int end, FIndexToSome<T> function) {
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
    public LinearVector<T> fillSubvector(int start, int end, FVectorAndIndexToSome<T> function) {
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
        if (o instanceof LinearVector) {
            LinearVector vector = (LinearVector) o;
            if (getSize() != vector.getSize())
                return false;
            int size = getSize();
            for (int i = 0; i < size; i++) {
                if (!get(i).equals(vector.get(i)))
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
    public abstract LinearVector clone();
    //endregion
}
