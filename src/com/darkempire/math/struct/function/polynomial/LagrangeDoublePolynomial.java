package com.darkempire.math.struct.function.polynomial;

import com.darkempire.anji.document.tex.TeXEventWriter;

import java.util.ArrayList;
import java.util.List;

/**
 * Клас реалізує поліном Лагранджа виду
 * L(x) = \sum\limits_{j=0}^n y_j l_j(x)
 * На основі сітки значень y_j та сітки точок x_j.
 * l_j(x) - це базисні поліноми, які визначаються за формулою:
 * l_j(x) = \prod\limits_{i=0,j\neq i}^n \cfrac{x-x_i}{x_j - x_i}
 * <p>
 * Цей поліном використовується, наприклад, для ітерполяції:
 * f(x)\approx \sum\limits_{j=0}^n f(x_j) l_j(x)
 * Або для інтегрування:
 * \int\limits_a^b f(x) dx \approx \sum\limits_{j=0}^n f(x_j) \int\limits_a^b l_j(x) dx
 *
 * @author siredvin
 */
public class LagrangeDoublePolynomial extends DoublePolynomial {
    private double[] x_points;
    private double[] y_points;
    private List<LPolynomial> lPolynomialList;

    public LagrangeDoublePolynomial(double[] x_points, double[] y_points) {
        this.x_points = x_points;
        this.y_points = y_points;
        lPolynomialList = new ArrayList<>();
        for (int j = 0; j < x_points.length; j++) {
            lPolynomialList.add(new LPolynomial(j, x_points));
        }
    }

    @Override
    public int getMaxPower() {
        return x_points.length;
    }

    @Override
    public int getMinPower() {
        return 0;
    }

    @Override
    public double calc(double x) {
        double result = 0;
        for (int i = 0; i < y_points.length; i++) {
            result += y_points[i] * lPolynomialList.get(i).calc(x);
        }
        return result;
    }

    @Override
    public void write(TeXEventWriter out) {
        //TODO:реалізувати
    }

    /**
     * Базисний поліном для полінома Лагранджа
     * l_j(x) = \prod\limits_{i=0,j\neq i}^n \cfrac{x-x_i}{x_j - x_i}
     */
    private static class LPolynomial extends DoublePolynomial {
        /**
         * Індекс базисного поліному j
         */
        private int index;
        /**
         * Всі точки поліному
         */
        private double[] x_points;
        /**
         * Створений для простоти обчислення:
         * coef = \prod\limits_{i=0,j\neq i}^n (x_j-x_i)
         */
        private double coef;

        private LPolynomial(int index, double[] x_points) {
            this.index = index;
            this.x_points = x_points;
            coef = 1;
            for (int i = 0; i < x_points.length; i++) {
                if (i == index)
                    continue;
                coef *= x_points[i] * x_points[index];
            }
        }

        @Override
        public int getMaxPower() {
            return x_points.length;
        }

        @Override
        public int getMinPower() {
            return 0;
        }

        @Override
        public double calc(double x) {
            double result = 1;
            for (int i = 0; i < x_points.length; i++) {
                if (i == index) {
                    continue;
                }
                result *= x - x_points[i];
            }
            return result / coef;
        }

        @Override
        public void write(TeXEventWriter out) {
            //TODO:реалізувати
        }
    }
}
