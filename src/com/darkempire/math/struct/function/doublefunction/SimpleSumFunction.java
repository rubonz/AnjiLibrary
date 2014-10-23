package com.darkempire.math.struct.function.doublefunction;

import com.darkempire.math.MathMachine;

/**
 * Created by siredvin on 23.10.14.
 *
 * @author siredvin
 */
public class SimpleSumFunction extends DoubleFunction {
    private double c1, c2;
    private DoubleFunction f1, f2;

    SimpleSumFunction(DoubleFunction f1, DoubleFunction f2) {
        this.f1 = f1;
        this.f2 = f2;
    }

    public SimpleSumFunction(double c1, double c2, DoubleFunction f1, DoubleFunction f2) {
        this.c1 = c1;
        this.c2 = c2;
        this.f1 = f1;
        this.f2 = f2;
    }

    @Override
    public double calc(double x) {
        return f1.calc(x) * c1 + f2.calc(x) * c2;
    }

    @Override
    public DoubleFunction iprod(double lambda) {
        c1 *= lambda;
        c2 *= lambda;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (c1 != 0) {
            builder.append(MathMachine.numberFormat.format(c1))
                    .append('(').append(f1).append(')');
            if (c2 > 0) {
                builder.append('+');
            }
        }
        if (c2 != 0) {
            builder.append(MathMachine.numberFormat.format(c2))
                    .append('(').append(f2).append(')');
        }
        if (builder.length() == 0) {
            builder.append('0');
        }
        return builder.toString();
    }

    @Override
    public DoubleFunction deepcopy() {
        return new SimpleSumFunction(c1, c2, f1.deepcopy(), f2.deepcopy());
    }
}
