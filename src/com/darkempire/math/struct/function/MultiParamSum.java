package com.darkempire.math.struct.function;

import com.darkempire.math.MathMachine;

import java.util.Arrays;
import java.util.List;

/**
 * Created by siredvin on 20.10.14.
 *
 * @author siredvin
 */
public class MultiParamSum implements FDoublesDouble {
    private double[] coefs;
    private List<FDoubleDouble> functions;

    public MultiParamSum(List<FDoubleDouble> functions, double[] coefs) {
        this.coefs = coefs;
        this.functions = functions;
    }

    public MultiParamSum(List<FDoubleDouble> functions) {
        this.functions = functions;
        coefs = new double[functions.size()];
        Arrays.fill(coefs, 1);
    }

    public double[] getCoefs() {
        return coefs;
    }

    public List<FDoubleDouble> getFunctions() {
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
                builder.append(temp);
                builder.append("*(");
                builder.append(functions.get(i).toString());
                builder.append(')');
            }
        }
        return builder.toString();
    }
}
