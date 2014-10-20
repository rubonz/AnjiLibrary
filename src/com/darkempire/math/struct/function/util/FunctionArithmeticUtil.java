package com.darkempire.math.struct.function.util;

import com.darkempire.anji.annotation.AnjiExperimental;
import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.math.exception.SizeMissmatchException;
import com.darkempire.math.struct.function.FDoubleDouble;
import com.darkempire.math.struct.function.doublefunction.DoubleFunction;
import com.darkempire.math.struct.function.doublesfunction.MultiParamSum;
import com.darkempire.math.struct.function.polynomial.ArrayDoublePolynomial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by siredvin on 20.10.14.
 *
 * @author siredvin
 */
@AnjiUtil
@AnjiExperimental
public final class FunctionArithmeticUtil {
    private FunctionArithmeticUtil() {
    }


    public static MultiParamSum createMultiParamSum(DoubleFunction... functions) {
        List<DoubleFunction> functionsL = new ArrayList<>();
        Collections.addAll(functionsL, functions);
        return new MultiParamSum(functionsL);
    }

    public static MultiParamSum createMultiParamSum(List<DoubleFunction> functions, double[] coefs) {
        if (functions.size() != coefs.length) {
            throw new SizeMissmatchException();
        }
        return new MultiParamSum(functions, coefs);
    }

    public static MultiParamSum createMultiParamSumFromSum(List<MultiParamSum> functions, double[] coefs) {
        if (functions.size() != coefs.length) {
            throw new SizeMissmatchException();
        }
        int size = functions.size();
        int coefCount = 0;
        List<DoubleFunction> functionsList = new ArrayList<>();
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

    /**
     * Розкриває поліном виду p(ax+b)
     *
     * @param polynomial p(x)
     * @param xCoef      a
     * @param free       b
     * @return p(ax + b) як поліном
     */
    public static ArrayDoublePolynomial expand(ArrayDoublePolynomial polynomial, double xCoef, double free) {
        double nFree = 0;
        int size = polynomial.getSize();
        for (int i = 1; i < size; i++) {
            double temp = polynomial.get(i);
            nFree += free * temp;
            polynomial.set(i, temp * xCoef);
        }
        polynomial.set(0, polynomial.get(0) + nFree);
        return polynomial;
    }
}
