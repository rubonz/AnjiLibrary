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

    public static double sum(DoubleVector vector) {
        double sum = 0;
        int size = vector.getSize();
        for (int i = 0; i < size; i++) {
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
