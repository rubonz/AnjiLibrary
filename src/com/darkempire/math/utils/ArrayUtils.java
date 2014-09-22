package com.darkempire.math.utils;

import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.math.struct.function.interfaces.FIndexToDouble;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 15.11.13
 * Time: 12:56
 * To change this template use File | Settings | File Templates.
 */
@AnjiUtil
public final class ArrayUtils {
    private ArrayUtils() {
    }

    /**
     * Заповнення масиву за допомогою функції
     *
     * @param x        масив
     * @param function функція для заповнення
     * @return змінений масив
     */
    public static double[] fill(double[] x, FIndexToDouble function) {
        for (int i = 0; i < x.length; i++) {
            x[i] = function.calc(i);
        }
        return x;
    }

    /**
     * Простий метод для перемішуванння масиву
     *
     * @param arr   масив
     * @param count кількість перемішувань
     * @return перемішаний масив
     */
    public static Object[] suffle(Object[] arr, int count) {
        for (int i = 0; i < count; i++) {
            int i1 = (int) (Math.random() * (arr.length - 1));
            int i2 = (int) (Math.random() * (arr.length - 1));
            Object k = arr[i1];
            arr[i1] = arr[i2];
            arr[i2] = k;
        }
        return arr;
    }

    /**
     * Простий метод для перемішуванння масиву
     *
     * @param arr   масив
     * @param count кількість перемішувань
     * @return перемішаний масив
     */
    public static int[] suffle(int[] arr, int count) {
        for (int i = 0; i < count; i++) {
            int i1 = (int) (Math.random() * (arr.length - 1));
            int i2 = (int) (Math.random() * (arr.length - 1));
            int k = arr[i1];
            arr[i1] = arr[i2];
            arr[i2] = k;
        }
        return arr;
    }

    /**
     * Простий метод для перемішуванння масиву
     *
     * @param arr   масив
     * @param count кількість перемішувань
     * @return перемішаний масив
     */
    public static double[] suffle(double[] arr, int count) {
        for (int i = 0; i < count; i++) {
            int i1 = (int) (Math.random() * (arr.length - 1));
            int i2 = (int) (Math.random() * (arr.length - 1));
            double k = arr[i1];
            arr[i1] = arr[i2];
            arr[i2] = k;
        }
        return arr;
    }
}
