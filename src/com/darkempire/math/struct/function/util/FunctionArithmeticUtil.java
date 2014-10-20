package com.darkempire.math.struct.function.util;

import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.math.MathMachine;
import com.darkempire.math.struct.function.FDoubleDouble;
import com.darkempire.math.struct.function.FDoublesDouble;
import com.darkempire.math.struct.function.MultiParamSum;
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


    public static MultiParamSum createMultiParamSum(FDoubleDouble... functions) {
        List<FDoubleDouble> functionsL = new ArrayList<>();
        Collections.addAll(functionsL, functions);
        return new MultiParamSum(functionsL);
    }

    public static MultiParamSum createMultiParamSum(List<FDoubleDouble> functions, double[] coefs) {
        return new MultiParamSum(functions, coefs);
    }

    public static MultiParamSum createMultiParamSumFromSum(List<MultiParamSum> functions, double[] coefs) {
        int size = functions.size();
        int coefCount = 0;
        List<FDoubleDouble> functionsList = new ArrayList<>();
        for (MultiParamSum sum : functions) {
            functionsList.addAll(sum.getFunctions());
            coefCount += sum.getCoefs().length;
        }
        double[] nCoefs = new double[coefCount];
        int index = 0;
        for (int i = 0; i < size; i++) {
            MultiParamSum sum = functions.get(i);
            double[] sCoef = sum.getCoefs();
            for (double aSCoef : sCoef) {
                nCoefs[index] = aSCoef * coefs[i];
                index++;
            }
        }
        return new MultiParamSum(functionsList, nCoefs);
    }
}
