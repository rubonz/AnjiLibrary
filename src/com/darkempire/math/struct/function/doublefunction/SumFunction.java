package com.darkempire.math.struct.function.doublefunction;

import com.darkempire.math.MathMachine;
import com.darkempire.math.struct.IDeepcopy;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by siredvin on 23.10.14.
 *
 * @author siredvin
 */
public class SumFunction extends DoubleFunction {
    private double[] coefs;
    private List<DoubleFunction> functions;

    SumFunction(double[] coefs, List<DoubleFunction> functions) {
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
