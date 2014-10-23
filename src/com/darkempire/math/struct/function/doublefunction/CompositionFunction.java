package com.darkempire.math.struct.function.doublefunction;

import com.darkempire.math.MathMachine;
import com.darkempire.math.struct.function.polynomial.DoublePolynomial;

/**
 * Реалізує композицію функції:
 * <p>
 * h(x) = g(f(x))
 *
 * @author siredvin
 */
public class CompositionFunction extends DoubleFunction {
    private DoubleFunction f;
    private DoubleFunction g;
    private double coef;

    public CompositionFunction(DoubleFunction g, DoubleFunction f) {
        this.f = f;
        this.g = g;
        this.coef = 1;
    }

    @Override
    public double calc(double x) {
        return coef * g.calc(f.calc(x));
    }

    @Override
    public DoubleFunction iprod(double lambda) {
        coef *= lambda;
        return this;
    }

    @Override
    public String toString() {
        String fs = '(' + f.toString() + ')';
        if (coef == 1) {
            return g.toString().replace("x", fs);
        }
        return MathMachine.numberFormat.format(coef) + '(' + g.toString().replace("x", fs) + ')';
    }

    @Override
    public DoubleFunction deepcopy() {
        return new CompositionFunction(g.deepcopy(), f.deepcopy());
    }
}
