package com.darkempire.math.operator.vector;

import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.math.struct.vector.DoubleVector;

/**
 * Created by siredvin on 13.10.14.
 *
 * @author siredvin
 */
@AnjiUtil
public final class DoubleVectorMathOperator {
    private DoubleVectorMathOperator() {
    }

    /**
     * Обчислює суму елементів вектору
     *
     * @param vector вектор
     * @return сума
     */
    public static double sum(DoubleVector vector) {
        double sum = 0;
        int size = vector.getSize();
        for (int i = 0; i < size; i++) {
            sum += vector.get(i);
        }
        return sum;
    }

    /**
     * Обчислює суму елементів вектору від індексу e включно
     *
     * @param vector     вектор
     * @param startIndex індекс е
     * @return сума
     */
    public static double sumFrom(DoubleVector vector, int startIndex) {
        double sum = 0;
        int size = vector.getSize();
        for (int i = startIndex; i < size; i++) {
            sum += vector.get(i);
        }
        return sum;
    }

    /**
     * Обчислює суму елементів вектору до індексу e невключно
     *
     * @param vector   вектор
     * @param endIndex індекс е
     * @return сума
     */
    public static double sumTo(DoubleVector vector, int endIndex) {
        double sum = 0;
        for (int i = 0; i < endIndex; i++) {
            sum += vector.get(i);
        }
        return sum;
    }

    /**
     * Обчислює суму елементів вектору від індексу с до індексу e невключно
     *
     * @param vector     вектор
     * @param startIndex індекс с
     * @param endIndex   індекс е
     * @return сума
     */
    public static double sum(DoubleVector vector, int startIndex, int endIndex) {
        double sum = 0;
        for (int i = startIndex; i < endIndex; i++) {
            sum += vector.get(i);
        }
        return sum;
    }

    public static double max(DoubleVector vector) {
        double max = vector.get(0);
        int size = vector.getSize();
        for (int i = 1; i < size; i++) {
            double temp = vector.get(i);
            if (temp > max) {
                max = temp;
            }
        }
        return max;
    }

    public static double min(DoubleVector vector) {
        double min = vector.get(0);
        int size = vector.getSize();
        for (int i = 1; i < size; i++) {
            double temp = vector.get(i);
            if (temp < min) {
                min = temp;
            }
        }
        return min;
    }

    public static double maxAbs(DoubleVector vector) {
        double max = vector.get(0);
        int size = vector.getSize();
        for (int i = 1; i < size; i++) {
            double temp = vector.get(i);
            if (Math.abs(temp) > Math.abs(max)) {
                max = temp;
            }
        }
        return max;
    }

    public static double minAbs(DoubleVector vector) {
        double min = vector.get(0);
        int size = vector.getSize();
        for (int i = 1; i < size; i++) {
            double temp = vector.get(i);
            if (Math.abs(temp) < Math.abs(min)) {
                min = temp;
            }
        }
        return min;
    }

    /**
     * Нормалізує вектор (тобто всі його координати будуть лежати від 0 до 1)
     *
     * @param vector вектор
     * @return нормальнізований вектор
     */
    public static DoubleVector normalize(DoubleVector vector) {
        double max = vector.get(0);
        double min = max;
        int size = vector.getSize();
        for (int i = 1; i < size; i++) {
            double temp = vector.get(i);
            if (temp > max) {
                max = temp;
            }
            if (temp < min) {
                min = temp;
            }
        }
        double delta = max - min;
        for (int i = 0; i < size; i++) {
            double temp = vector.get(i);
            vector.set(i, (temp - min) / delta);
        }
        return vector;
    }

}
