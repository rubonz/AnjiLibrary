package com.darkempire.math.struct.function.polynomial;

import com.darkempire.anji.annotation.AnjiUtil;

/**
 * Created by siredvin on 16.10.14.
 *
 * @author siredvin
 */
@AnjiUtil
public final class InterpolationUtil {
    private InterpolationUtil() {
    }

    /**
     * Інтерполяція за допомогою поліномів Лагранджа
     *
     * @param x точки сітки
     * @param y значення функції в точках
     * @return наближену функцію
     */
    public static LagrangeDoublePolynomial lagrangeInterpolation(double[] x, double[] y) {
        return new LagrangeDoublePolynomial(x, y);
    }


}
