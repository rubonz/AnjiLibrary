package com.darkempire.math.struct.function.util;

import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.math.MathMachine;
import com.darkempire.math.struct.function.FDoubleDouble;
import com.darkempire.math.struct.function.FDoublesDouble;
import com.darkempire.math.utils.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by siredvin on 20.10.14.
 *
 * @author siredvin
 */
@AnjiUtil
public final class FunctionArithmeticUtil {
    private FunctionArithmeticUtil() {
    }


    public static FDoublesDouble createMultiParamSum(FDoubleDouble... functions) {
        List<FDoubleDouble> functionsL = new ArrayList<>();
        Collections.addAll(functionsL, functions);
        return new MultiParamSum(functionsL);
    }

    public static FDoublesDouble createMultiParamSum(List<FDoubleDouble> functions, double[] coefs) {
        return new MultiParamSum(functions, coefs);
    }


    private static class MultiParamSum implements FDoublesDouble {
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
}
