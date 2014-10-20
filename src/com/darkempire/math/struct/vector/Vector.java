package com.darkempire.math.struct.vector;

import com.darkempire.anji.util.Util;
import com.darkempire.math.struct.function.interfaces.FIndexToSome;
import com.darkempire.math.struct.function.interfaces.FVectorIndexToSome;

/**
 * Create in 10:42
 * Created by siredvin on 25.04.14.
 */
public abstract class Vector<T> implements IVectorProvider<T> {
    //region Сеттери
    public abstract void set(int index, T value);
    //endregion

    //region Отримання підвектора
    public abstract Vector subvector(int length);

    public abstract Vector subvector(int startIndex, int length);
    //endregion

    //region Геттери
    @Override
    public abstract T get(int index);

    @Override
    public abstract int getSize();
    //endregion

    //region Заповнювачі
    public Vector<T> fill(FIndexToSome<T> function) {
        int size = getSize();
        for (int i = 0; i < size; i++) {
            set(i, function.calc(i));
        }
        return this;
    }

    public Vector<T> fill(FVectorIndexToSome<T> function) {
        int size = getSize();
        for (int i = 0; i < size; i++) {
            set(i, function.calc(this, i));
        }
        return this;
    }
    //endregion

    //region Заповнювачі підвекторів

    /**
     * Заповнення підвектору від початку (включаючи) до кінця (виключаючи)
     *
     * @param start    початок
     * @param end      кінець
     * @param function функція
     * @return вектор
     */
    public Vector<T> fillSubvector(int start, int end, FIndexToSome<T> function) {
        for (int i = start; i < end; i++) {
            set(i, function.calc(i));
        }
        return this;
    }

    /**
     * Заповнення підвектору від початку (включаючи) до кінця (виключаючи)
     * @param start початок
     * @param end кінець
     * @param function функція
     * @return вектор
     */
    public Vector<T> fillSubvector(int start, int end, FVectorIndexToSome<T> function) {
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
        if (o instanceof Vector) {
            Vector vector = (Vector) o;
            if (getSize() != vector.getSize())
                return false;
            int size = getSize();
            for (int i = 0; i < size; i++) {
                if (get(i).equals(vector.get(i)))
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
    //endregion
}
