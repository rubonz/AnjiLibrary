package com.darkempire.math.struct.function.polynomial;

import com.darkempire.anji.annotation.AnjiStandartize;
import com.darkempire.anji.document.tex.ITeXObject;
import com.darkempire.anji.util.ClassUtil;
import com.darkempire.math.struct.function.FDoubleDouble;
import com.darkempire.math.struct.function.doublefunction.DoubleFunction;
import com.darkempire.math.struct.vector.IDoubleVectorProvider;

/**
 * Created by siredvin on 13.10.14.
 *
 * @author siredvin
 */
@AnjiStandartize
public abstract class DoublePolynomial extends DoubleFunction implements ITeXObject, FDoubleDouble {
    public abstract int getMaxPower();

    public abstract int getMinPower();

    public abstract ArrayDoublePolynomial toRawPolynomial();

    @Override
    public DoubleFunction multiply(DoubleFunction doubleFunction) {
        if (ClassUtil.isSuperClass(DoublePolynomial.class, doubleFunction.getClass())) {
            return multiply((DoublePolynomial) doubleFunction);
        }
        return super.multiply(doubleFunction);
    }

    @Override
    public DoubleFunction divide(DoubleFunction doubleFunction) {
        if (ClassUtil.isSuperClass(DoublePolynomial.class, doubleFunction.getClass())) {
            return divide((DoublePolynomial) doubleFunction);
        }
        return super.divide(doubleFunction);
    }

    @Override
    public DoubleFunction add(DoubleFunction doubleFunction) {
        if (ClassUtil.isSuperClass(DoublePolynomial.class, doubleFunction.getClass())) {
            return add((DoublePolynomial) doubleFunction);
        }
        return super.add(doubleFunction);
    }

    @Override
    public DoubleFunction subtract(DoubleFunction doubleFunction) {
        if (ClassUtil.isSuperClass(DoublePolynomial.class, doubleFunction.getClass())) {
            return subtract((DoublePolynomial) doubleFunction);
        }
        return super.subtract(doubleFunction);
    }

    public DoublePolynomial add(DoublePolynomial polynomial) {
        return toRawPolynomial().add(polynomial);
    }

    public DoublePolynomial subtract(DoublePolynomial polynomial) {
        return toRawPolynomial().subtract(polynomial);
    }

    public DoublePolynomial divide(DoublePolynomial polynomial) {
        return toRawPolynomial().divide(polynomial);
    }

    public DoublePolynomial multiply(DoublePolynomial polynomial) {
        return toRawPolynomial().multiply(polynomial);
    }

}
