package com.darkempire.math.utils;

import com.darkempire.anji.annotation.AnjiUtil;
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

    public static double[] subarray(double[] array, int startIndex, int endIndex) {
        double[] result = new double[endIndex - startIndex + 1];
        System.arraycopy(array, startIndex, result, 0, endIndex - startIndex + 1);
        return result;
    }

    public static int[] subarray(int[] array, int startIndex, int endIndex) {
        int[] result = new int[endIndex - startIndex + 1];
        System.arraycopy(array, startIndex, result, 0, endIndex - startIndex + 1);
        return result;
    }
}
