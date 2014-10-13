package com.darkempire.math.struct.function.polynomial;

import com.darkempire.anji.annotation.AnjiStandartize;
import com.darkempire.anji.annotation.AnjiUnknow;
import com.darkempire.anji.document.tex.ITeXObject;
import com.darkempire.anji.document.tex.TeXEventWriter;
import com.darkempire.math.struct.Calcable;
import com.darkempire.math.struct.LinearCalcable;
import com.darkempire.math.struct.function.FDoubleDouble;
import com.darkempire.math.struct.function.FDoubleVectorDouble;
import com.darkempire.math.struct.function.polynomial.ADoublePolynomial;
import com.darkempire.math.struct.vector.IDoubleVectorProvider;

/**
 * Created by siredvin on 13.10.14.
 *
 * @author siredvin
 */
@AnjiStandartize
public class DoublePolynomial extends ADoublePolynomial implements FDoubleDouble, LinearCalcable<DoublePolynomial>, Calcable<DoublePolynomial> {
    private double[] coefs;

    //region Конструктори
    public DoublePolynomial(double... coefs) {
        this.coefs = coefs;
    }
    //endregion

    @Override
    public double calc(double x) {
        double result = 0;
        double powered = 1;
        for (int i = 0; i < coefs.length; i++) {
            result += get(i) * powered;
            powered *= x;
        }
        return result;
    }

    //region Геттери
    @Override
    public double get(int index) {
        if (index >= coefs.length)
            return 0;
        return coefs[index];
    }

    @Override
    public int getDimentions() {
        return 1;
    }

    @Override
    public int getMaxPower() {
        return coefs.length;
    }

    @Override
    public int getSize() {
        return coefs.length;
    }

    public double[] getCoefs() {
        return coefs;
    }
    //endregion

    //region Системні функції
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (coefs[0] != 0) {
            builder.append(coefs[0]);
        }
        for (int i = 1; i < coefs.length; i++) {
            double temp = coefs[i];
            if (temp != 0) {
                if (temp > 0) {
                    if (builder.length() != 0) {
                        builder.append('+');
                    }
                    builder.append(temp);
                    builder.append("*x^");
                    builder.append(i + 1);
                } else {
                    builder.append(temp);
                    builder.append("*x^");
                    builder.append(i + 1);
                }
            }
        }
        return builder.toString();
    }

    @Override
    public void write(TeXEventWriter out) {
        //TODO:реалізувати
    }

    @Override
    public DoublePolynomial deepcopy() {
        return new DoublePolynomial(coefs.clone());
    }
    //endregion

    //region Арифметичні операції з присвоєнням
    @Override
    public DoublePolynomial inegate() {
        for (int i = 0; i < coefs.length; i++) {
            coefs[i] = -coefs[i];
        }
        return this;
    }

    @Override
    public DoublePolynomial iadd(DoublePolynomial linearMultiDoublePolynomial) {
        int elseSize = linearMultiDoublePolynomial.getSize();
        if (elseSize > coefs.length) {
            double[] newCoef = new double[elseSize];
            System.arraycopy(coefs, 0, newCoef, 0, coefs.length);
            for (int i = 0; i < elseSize; i++) {
                newCoef[i] += linearMultiDoublePolynomial.coefs[i];
            }
            coefs = newCoef;
        } else {
            int size = Math.min(elseSize, coefs.length);
            for (int i = 0; i < size; i++) {
                coefs[i] += linearMultiDoublePolynomial.coefs[i];
            }
        }
        return this;
    }

    @Override
    public DoublePolynomial isubtract(DoublePolynomial linearMultiDoublePolynomial) {
        int elseSize = linearMultiDoublePolynomial.getSize();
        if (elseSize > coefs.length) {
            double[] newCoef = new double[elseSize];
            System.arraycopy(coefs, 0, newCoef, 0, coefs.length);
            for (int i = 0; i < elseSize; i++) {
                newCoef[i] -= linearMultiDoublePolynomial.coefs[i];
            }
            coefs = newCoef;
        } else {
            int size = Math.min(elseSize, coefs.length);
            for (int i = 0; i < size; i++) {
                coefs[i] -= linearMultiDoublePolynomial.coefs[i];
            }
        }
        return this;
    }

    @Override
    public DoublePolynomial imultiply(DoublePolynomial doublePolynomial) {
        return null;//TODO:обробити
    }

    @Override
    @AnjiUnknow
    public DoublePolynomial idivide(DoublePolynomial doublePolynomial) {
        throw new UnsupportedOperationException();
    }

    @Override
    @AnjiUnknow
    public DoublePolynomial iinverse() {
        throw new UnsupportedOperationException();
    }
    //endregion

    //region Арифметичні операції
    @Override
    public DoublePolynomial add(DoublePolynomial linearMultiDoublePolynomial) {
        int elseSize = linearMultiDoublePolynomial.getSize();
        int size = Math.max(coefs.length, elseSize);
        double[] newCoef = new double[size];
        for (int i = 0; i < size; i++) {
            newCoef[i] = get(i) + linearMultiDoublePolynomial.get(i);
        }
        return new DoublePolynomial(newCoef);
    }

    @Override
    public DoublePolynomial subtract(DoublePolynomial linearMultiDoublePolynomial) {
        int elseSize = linearMultiDoublePolynomial.getSize();
        int size = Math.max(coefs.length, elseSize);
        double[] newCoef = new double[size];
        for (int i = 0; i < size; i++) {
            newCoef[i] = get(i) - linearMultiDoublePolynomial.get(i);
        }
        return new DoublePolynomial(newCoef);
    }

    @Override
    public DoublePolynomial negate() {
        int size = coefs.length;
        double[] newCoef = new double[size];
        for (int i = 0; i < size; i++) {
            newCoef[i] = -get(i);
        }
        return new DoublePolynomial(newCoef);
    }

    @Override
    public void prop(double lambda) {
        for (int i = 0; i < coefs.length; i++) {
            coefs[i] *= lambda;
        }
    }

    @Override
    public DoublePolynomial multiply(DoublePolynomial doublePolynomial) {
        return null;//TODO:обробити
    }

    @Override
    @AnjiUnknow
    public DoublePolynomial divide(DoublePolynomial doublePolynomial) {
        throw new UnsupportedOperationException();
    }

    @Override
    @AnjiUnknow
    public DoublePolynomial inverse() {
        throw new UnsupportedOperationException();
    }
    //endregion
}
