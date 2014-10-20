package com.darkempire.math.struct.function.polynomial;

import com.darkempire.anji.document.tex.TeXEventWriter;
import com.darkempire.math.MathMachine;
import com.darkempire.math.struct.function.doublefunction.DoubleFunction;
import com.darkempire.math.utils.ArrayUtils;

/**
 * Базисний поліном
 * l_j(x) = \prod\limits_{i=0,j\neq i}^n \cfrac{x-x_i}{x_j - x_i}
 *
 * @author siredvin
 */
class LBasicPolynomial extends DoublePolynomial {
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

    public LBasicPolynomial(int index, double[] x_points) {
        this.index = index;
        this.x_points = x_points;
        coef = 1;
        for (int i = 0; i < x_points.length; i++) {
            if (i == index)
                continue;
            coef *= x_points[index] - x_points[i];
        }
    }

    private LBasicPolynomial(int index, double[] x_points, double coef) {
        this.index = index;
        this.coef = coef;
        this.x_points = x_points;
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
    public ArrayDoublePolynomial toRawPolynomial() {
        double x_index = x_points[index];
        x_points[index] = 0;
        double[] coefs = new double[x_points.length - 1];
        coefs[0] = 1;
        //TODO:реалізувати
        //return new ArrayDoublePolynomial(coefs);
        return null;
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
    public DoubleFunction iprod(double lambda) {
        coef *= lambda;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(MathMachine.numberFormat.format(1 / coef));
        for (double x : x_points) {
            builder.append("*(x");
            if (x > 0) {
                builder.append('+');
            }
            builder.append(MathMachine.numberFormat.format(x)).append(')');
        }
        return builder.toString();
    }

    @Override
    public void write(TeXEventWriter out) {
        //TODO:реалізувати
    }

    @Override
    public DoubleFunction deepcopy() {
        return new LBasicPolynomial(index, x_points.clone(), coef);
    }
}
