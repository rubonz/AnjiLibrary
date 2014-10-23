package com.darkempire.math.struct.function.doublesfunction;

import com.darkempire.math.MathMachine;
import com.darkempire.math.struct.function.doublefunction.DoubleFunction;

/**
 * h(x_1,\ldots) = g(f(x_1,\ldots))
 *
 * @author siredvin
 */
public class CompositionFunction extends DoublesFunction {
    private DoublesFunction f;
    private DoubleFunction g;

    private double coef;

    public CompositionFunction(DoubleFunction g, DoublesFunction f) {
        this.f = f;
        this.g = g;
        this.coef = 1;
    }

    @Override
    public double calc(double... x) {
        return coef * g.calc(f.calc(x));
    }

    @Override
    public DoublesFunction iprod(double lambda) {
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
    public DoublesFunction deepcopy() {
        return new CompositionFunction(g.deepcopy(), f.deepcopy());
    }
}
