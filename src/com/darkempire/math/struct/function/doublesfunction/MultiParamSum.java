package com.darkempire.math.struct.function.doublesfunction;

import com.darkempire.math.MathMachine;
import com.darkempire.math.struct.IDeepcopy;
import com.darkempire.math.struct.function.FDoubleDouble;
import com.darkempire.math.struct.function.FDoublesDouble;
import com.darkempire.math.struct.function.doublefunction.DoubleFunction;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by siredvin on 20.10.14.
 *
 * @author siredvin
 */
public class MultiParamSum extends DoublesFunction {
    private double[] coefs;
    private List<DoubleFunction> functions;

    public MultiParamSum(List<DoubleFunction> functions, double[] coefs) {
        this.coefs = coefs;
        this.functions = functions;
    }

    public MultiParamSum(List<DoubleFunction> functions) {
        this.functions = functions;
        coefs = new double[functions.size()];
        Arrays.fill(coefs, 1);
    }

    public double[] getCoefs() {
        return coefs;
    }

    public List<DoubleFunction> getFunctions() {
        return functions;
    }

    @Override
    public double calc(double... doubles) {
        double sum = 0;
        for (int i = 0; i < doubles.length; i++) {
            sum += functions.get(i).calc(doubles[i]) * coefs[i];
        }
        return sum;
    }

    @Override
    public DoublesFunction iprod(double lambda) {
        for (int i = 0; i < coefs.length; i++) {
            coefs[i] *= lambda;
        }
        return this;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        int size = coefs.length;
        builder.append(MathMachine.numberFormat.format(coefs[0]));
        builder.append("*(");
        builder.append(functions.get(0).toString());
        builder.append(')');
        for (int i = 1; i < size; i++) {
            double temp = coefs[i];
            if (temp != 0) {
                if (temp > 0) {
                    builder.append('+');
                }
                builder.append(MathMachine.numberFormat.format(temp));
                builder.append("*(");
                builder.append(functions.get(i).toString());
                builder.append(')');
            }
        }
        return builder.toString();
    }

    @Override
    public DoublesFunction deepcopy() {
        return new MultiParamSum(functions.stream().map(IDeepcopy::deepcopy).collect(Collectors.toList()), coefs.clone());
    }
}
