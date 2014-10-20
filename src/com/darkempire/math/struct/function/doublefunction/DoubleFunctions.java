package com.darkempire.math.struct.function.doublefunction;

import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.math.MathMachine;
import com.darkempire.math.exception.SizeMissmatchException;
import com.darkempire.math.struct.IDeepcopy;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by siredvin on 20.10.14.
 *
 * @author siredvin
 */
@AnjiUtil
public final class DoubleFunctions {
    private DoubleFunctions() {
    }

    public static DoubleFunction sumFunctions(DoubleFunction... functions) {
        double[] coefs = new double[functions.length];
        Arrays.fill(coefs, 1);
        return new SumFunction(coefs, Arrays.asList(functions));
    }

    public static DoubleFunction sumFunctions(List<DoubleFunction> functions, double[] coefs) {
        if (functions.size() != coefs.length)
            throw new SizeMissmatchException();
        return new SumFunction(coefs, functions);
    }

    public static DoubleFunction sum(DoubleFunction f1, DoubleFunction f2) {
        return new SimpleSumFunction(f1, f2);
    }

    public static class SumFunction extends DoubleFunction {
        private double[] coefs;
        private List<DoubleFunction> functions;

        private SumFunction(double[] coefs, List<DoubleFunction> functions) {
            this.coefs = coefs;
            this.functions = functions;
        }

        @Override
        public double calc(double x) {
            double temp = 0;
            for (int i = 0; i < coefs.length; i++) {
                temp += coefs[i] * functions.get(i).calc(x);
            }
            return temp;
        }

        @Override
        public DoubleFunction iprod(double lambda) {
            for (int i = 0; i < coefs.length; i++) {
                coefs[i] *= lambda;
            }
            return this;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append(MathMachine.numberFormat.format(coefs[0])).append('(').append(functions.get(0)).append(')');
            for (int i = 1; i < coefs.length; i++) {
                double temp = coefs[i];
                if (temp == 0)
                    continue;
                if (temp > 0) {
                    builder.append('+');
                }
                builder.append(MathMachine.numberFormat.format(temp));
                builder.append('(').append(functions.get(i)).append(')');
            }
            if (builder.length() == '0') {
                builder.append('0');
            }
            return builder.toString();
        }

        @Override
        public DoubleFunction deepcopy() {
            return new SumFunction(coefs.clone(), functions.stream().map(IDeepcopy::deepcopy).collect(Collectors.toList()));
        }
    }

    public static class SimpleSumFunction extends DoubleFunction {
        private double c1, c2;
        private DoubleFunction f1, f2;

        private SimpleSumFunction(DoubleFunction f1, DoubleFunction f2) {
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
}
