package com.darkempire.math.utils;

import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.math.MathMachine;
import com.darkempire.math.struct.function.interfaces.FIndexToDouble;

import java.util.List;

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

    /**
     * Поєднує багато масивів в один
     * [1,2,3],[4,5] -> [1,2,3,4,5]
     *
     * @param arrays масиви
     * @return об’єднаний масив
     */
    public static double[] append(double[]... arrays) {
        int length = 0;
        for (double[] arr : arrays) {
            length += arr.length;
        }
        double[] result = new double[length];
        int counter = 0;
        for (double[] arr : arrays) {
            for (double d : arr) {
                result[counter] = d;
                counter++;
            }
        }
        return result;
    }

    /**
     * Поєднує багато масивів в один
     * [1,2,3],[4,5] -> [1,2,3,4,5]
     *
     * @param arrays масиви
     * @return об’єднаний масив
     */
    public static double[] append(List<double[]> arrays) {
        int length = 0;
        for (double[] arr : arrays) {
            length += arr.length;
        }
        double[] result = new double[length];
        int counter = 0;
        for (double[] arr : arrays) {
            for (double d : arr) {
                result[counter] = d;
                counter++;
            }
        }
        return result;
    }

    /**
     * Будує підмасив з початку (включно) до кінця (невключно)
     *
     * @param array      масив
     * @param startIndex початок
     * @param endIndex   кінець
     * @return підмасив
     */
    public static double[] subarray(double[] array, int startIndex, int endIndex) {
        int delta = endIndex - startIndex;
        double[] result = new double[delta];
        System.arraycopy(array, startIndex, result, 0, delta);
        return result;
    }

    /**
     * Будує підмасив з початку (включно) до кінця (невключно)
     *
     * @param array      масив
     * @param startIndex початок
     * @param endIndex   кінець
     * @return підмасив
     */
    public static int[] subarray(int[] array, int startIndex, int endIndex) {
        int delta = endIndex - startIndex;
        int[] result = new int[delta];
        System.arraycopy(array, startIndex, result, 0, delta);
        return result;
    }

    /**
     * Порівняння двох масивів з точнітсю MathMachine.EPS
     *
     * @param a перший массив
     * @param b другий массив
     * @return a==b
     */
    public static boolean equalsEps(double[] a, double[] b) {
        if (a == b)
            return true;
        if (a == null || b == null)
            return false;
        for (int i = 0; i < a.length; i++) {
            if (Math.abs(a[i] - b[i]) > MathMachine.MACHINE_EPS) {
                return false;
            }
        }
        return true;
    }
}
