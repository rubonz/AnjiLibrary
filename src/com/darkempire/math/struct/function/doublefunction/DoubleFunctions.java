package com.darkempire.math.struct.function.doublefunction;

import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.math.exception.SizeMissmatchException;

import java.util.Arrays;
import java.util.List;

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

}
