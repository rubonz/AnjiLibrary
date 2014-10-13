package com.darkempire.math.operator.vector;

import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.math.exception.SizeMissmatchException;
import com.darkempire.math.struct.*;
import com.darkempire.math.struct.Number;
import com.darkempire.math.struct.vector.DoubleVector;
import com.darkempire.math.struct.vector.NumberVector;

/**
 * Created by siredvin on 13.10.14.
 *
 * @author siredvin
 */
@AnjiUtil
public final class NumberVectorTricksOperator {
    private NumberVectorTricksOperator() {
    }

    /**
     * Міняє значення обох векторів місцями
     * Наприклад swap({1,2,3},{5,6,7}) -> {5,6,7},{1,2,3}
     *
     * @param v1  Перший вектор
     * @param v2  Другий вектор
     * @param <T> тип чисел, які зберігають вектори
     */
    public static <T extends com.darkempire.math.struct.Number<T>> void swap(NumberVector<T> v1, NumberVector<T> v2) {
        int size = v1.getSize();
        if (size != v2.getSize()) {
            throw new SizeMissmatchException();
        }
        for (int i = 0; i < size; i++) {
            T temp = v1.get(i);
            v1.set(i, v2.get(i));
            v2.set(i, temp);
        }
    }

    public static <T extends Number<T>> NumberVector<T> reverse(NumberVector<T> v) {
        int size = v.getSize();
        int asize = size / 2;
        for (int i = 0; i < asize; i++) {
            int rSize = size - i - 1;
            T temp = v.get(i);
            v.set(i, v.get(rSize));
            v.set(rSize, temp);
        }
        return v;
    }

    public static <T extends Number<T>> NumberVector<T> reverse(NumberVector<T> v, int startMargin) {
        return reverse(v.sliceFrom(startMargin));
    }

    public static <T extends Number<T>> NumberVector<T> reverse(NumberVector<T> v, int startMargin, int endMargin) {
        return reverse(v.slice(startMargin, endMargin));
    }
}
