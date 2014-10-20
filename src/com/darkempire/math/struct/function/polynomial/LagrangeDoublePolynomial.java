package com.darkempire.math.struct.function.polynomial;

import com.darkempire.anji.document.tex.TeXEventWriter;
import com.darkempire.math.MathMachine;
import com.darkempire.math.struct.function.doublefunction.DoubleFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private List<LBasicPolynomial> lPolynomialList;

    public LagrangeDoublePolynomial(double[] x_points, double[] y_points) {
        this.x_points = x_points;
        this.y_points = y_points;
        lPolynomialList = new ArrayList<>();
        for (int j = 0; j < x_points.length; j++) {
            lPolynomialList.add(new LBasicPolynomial(j, x_points));
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
    public ArrayDoublePolynomial toRawPolynomial() {
        return ArrayDoublePolynomial.sum(lPolynomialList.stream().map(LBasicPolynomial::toRawPolynomial).collect(Collectors.toList()));
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
    public DoubleFunction iprod(double lambda) {
        for (int i = 0; i < y_points.length; i++) {
            y_points[i] *= lambda;
        }
        return this;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(MathMachine.numberFormat.format(y_points[0]))
                .append('(').append(lPolynomialList.get(0)).append(')');
        for (int i = 1; i < y_points.length; i++) {
            double temp = y_points[i];
            if (temp > 0) {
                builder.append('+');
            }
            builder.append(MathMachine.numberFormat.format(temp))
                    .append('(').append(lPolynomialList.get(i)).append(')');
        }
        return builder.toString();
    }

    @Override
    public void write(TeXEventWriter out) {
        //TODO:реалізувати
    }

    @Override
    public DoubleFunction deepcopy() {
        return new LagrangeDoublePolynomial(x_points.clone(), y_points.clone());
    }

}
