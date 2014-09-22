package com.darkempire.math.utils;

import com.darkempire.anji.annotation.AnjiUtil;

@AnjiUtil
public final class MathUtils {
    /**
     * Знаходить максимум між двома значеннями
     *
     * @param a перше значення
     * @param b друге значення
     * @return максимум
     */
    public static double max(double a, double b) {
        return a > b ? a : b;
    }

    /**
     * Знаходить максимум серед масиву чисел
     *
     * @param ds масив чисел
     * @return максимум
     */
    public static double max(double... ds) {
        double max = ds[0];
        for (double k : ds) {
            if (max < k) max = k;
        }
        return max;
    }

    /**
     * Знаходить максимум між двома значеннями
     *
     * @param a перше значення
     * @param b друге значення
     * @return максимум
     */
    public static int max(int a, int b) {
        return a > b ? a : b;
    }

    /**
     * Знаходить максимум серед масиву чисел
     *
     * @param is масив чисел
     * @return максимум
     */
    public static int max(int... is) {
        int max = is[0];
        for (int k : is) {
            if (max < k) max = k;
        }
        return max;
    }

    /**
     * Знаходить мінімум між двома значеннями
     *
     * @param a перше значення
     * @param b друге значення
     * @return мінімум
     */
    public static double min(double a, double b) {
        return a > b ? b : a;
    }

    /**
     * Знаходить мінімум серед масиву чисел
     *
     * @param ds масив чисел
     * @return мінімум
     */
    public static double min(double... ds) {
        double min = ds[0];
        for (double k : ds) {
            if (min > k) min = k;
        }
        return min;
    }

    /**
     * Знаходить мінімум між двома значеннями
     *
     * @param a перше значення
     * @param b друге значення
     * @return мінімум
     */
    public static int min(int a, int b) {
        return a > b ? b : a;
    }

    /**
     * Знаходить мінімум серед масиву чисел
     *
     * @param is масив чисел
     * @return мінімум
     */
    public static int min(int... is) {
        int min = is[0];
        for (int k : is) {
            if (min > k) min = k;
        }
        return min;
    }

    /**
     * Знаходження найбільшого спільного дільника двох чисел
     *
     * @param a перше число
     * @param b друге число
     * @return gcd(a, b)
     */
    public static int gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        a = a % b;
        while (a > 0) {
            int t = b;
            b = a;
            a = t % b;
        }
        return b;
    }

    /**
     * Знаходження найменшого спільного кратного двох чисел
     *
     * @param a перше число
     * @param b друге число
     * @return lcm(a, b)
     */
    public static int lcm(int a, int b) {
        return Math.abs(a * b / gcd(a, b));
    }

    /**
     * Знаходження найбільшого спільного дільника двох чисел
     *
     * @param a перше число
     * @param b друге число
     * @return gcd(a, b)
     */
    public static long gcd(long a, long b) {
        a = Math.abs(a);
        b = Math.abs(b);
        a = a % b;
        while (a > 0) {
            long t = b;
            b = a;
            a = t % b;
        }
        return b;
    }

    /**
     * Знаходження найменшого спільного кратного двох чисел
     *
     * @param a перше число
     * @param b друге число
     * @return lcm(a, b)
     */
    public static long lcm(long a, long b) {
        return Math.abs(a * b / gcd(a, b));
    }
}
