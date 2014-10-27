package com.darkempire.math.struct.function.polynomial;

import com.darkempire.anji.annotation.AnjiStandartize;
import com.darkempire.anji.annotation.AnjiUnknow;
import com.darkempire.anji.document.tex.TeXEventWriter;
import com.darkempire.math.MathMachine;
import com.darkempire.math.struct.Assignable;
import com.darkempire.math.struct.LinearAssignable;
import com.darkempire.math.struct.vector.IDoubleVectorProvider;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by siredvin on 13.10.14.
 *
 * @author siredvin
 */
@AnjiStandartize
public class ArrayDoublePolynomial extends DoublePolynomial implements IDoubleVectorProvider, LinearAssignable<ArrayDoublePolynomial>, Assignable<ArrayDoublePolynomial> {
    private double[] coefs;

    //region Конструктори
    public ArrayDoublePolynomial(double... coefs) {
        if (coefs.length == 0) {
            this.coefs = new double[]{0};
        }
        this.coefs = coefs;
    }
    //endregion

    public static ArrayDoublePolynomial sum(ArrayDoublePolynomial... polynomials) {
        int maxSize = Arrays.stream(polynomials).map(ArrayDoublePolynomial::getSize)
                .max(Comparator.<Integer>naturalOrder()).get();
        ArrayDoublePolynomial result = new ArrayDoublePolynomial(new double[maxSize]);
        for (int pi = 0; pi < maxSize; pi++) {
            double sum = 0;
            for (ArrayDoublePolynomial polynomial : polynomials) {
                sum += polynomial.get(pi);
            }
            result.set(pi, sum);
        }
        return result;
    }

    public static ArrayDoublePolynomial sum(List<ArrayDoublePolynomial> polynomials) {
        int maxSize = polynomials.stream().map(ArrayDoublePolynomial::getSize)
                .max(Comparator.<Integer>naturalOrder()).get();
        ArrayDoublePolynomial result = new ArrayDoublePolynomial(new double[maxSize]);
        for (int pi = 0; pi < maxSize; pi++) {
            double sum = 0;
            for (ArrayDoublePolynomial polynomial : polynomials) {
                sum += polynomial.get(pi);
            }
            result.set(pi, sum);
        }
        return result;
    }

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

    public void set(int index, double coef) {
        coefs[index] = coef;
    }

    //region Геттери
    @Override
    public double get(int index) {
        if (index >= coefs.length)
            return 0;
        return coefs[index];
    }

    @Override
    public int getMinPower() {
        return 0;
    }

    @Override
    public int getMaxPower() {
        return coefs.length;
    }
    //endregion

    @Override
    public int getSize() {
        return coefs.length;
    }

    public double[] getCoefs() {
        return coefs;
    }

    //region Системні функції
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (coefs.length == 1) {
            return builder.append(coefs[0]).toString();
        }
        if (coefs[0] != 0) {
            builder.append(MathMachine.numberFormat.format(coefs[0]));
        }
        for (int i = 1; i < coefs.length; i++) {
            double temp = coefs[i];
            if (temp != 0) {
                if (temp > 0 && builder.length() != 0) {
                    builder.append('+');
                }
                    builder.append(MathMachine.numberFormat.format(temp));
                    if (i == 1) {
                        builder.append("*x");
                    } else {
                        builder.append("*x^");
                        builder.append(i);
                    }
            }
        }
        return builder.toString();
    }

    @Override
    public void write(TeXEventWriter out) {
        StringBuilder builder = new StringBuilder();
        if (coefs.length == 1) {
            out.append(builder.append(coefs[0]).toString());
            return;
        }
        if (coefs[0] != 0) {
            builder.append(MathMachine.numberFormat.format(coefs[0]));
        }
        for (int i = 1; i < coefs.length; i++) {
            double temp = coefs[i];
            if (temp != 0) {
                if (temp > 0 && builder.length() != 0) {
                    builder.append('+');
                }
                builder.append(MathMachine.numberFormat.format(temp));
                if (i == 1) {
                    builder.append("*x");
                } else {
                    builder.append("*x^{");
                    builder.append(i);
                    builder.append("}");
                }
            }
        }
        out.append(builder.toString());
    }

    @Override
    public ArrayDoublePolynomial deepcopy() {
        return new ArrayDoublePolynomial(coefs.clone());
    }

    //endregion

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArrayDoublePolynomial)) return false;
        ArrayDoublePolynomial that = (ArrayDoublePolynomial) o;
        return Arrays.equals(coefs, that.coefs);

    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(coefs);
    }

    //region Арифметичні операції з присвоєнням
    @Override
    public ArrayDoublePolynomial inegate() {
        for (int i = 0; i < coefs.length; i++) {
            coefs[i] = -coefs[i];
        }
        return this;
    }

    @Override
    public ArrayDoublePolynomial iadd(ArrayDoublePolynomial arrayDoublePolynomial) {
        int elseSize = arrayDoublePolynomial.getSize();
        if (elseSize > coefs.length) {
            double[] newCoef = new double[elseSize];
            System.arraycopy(coefs, 0, newCoef, 0, coefs.length);
            for (int i = 0; i < elseSize; i++) {
                newCoef[i] += arrayDoublePolynomial.coefs[i];
            }
            coefs = newCoef;
        } else {
            int size = Math.min(elseSize, coefs.length);
            for (int i = 0; i < size; i++) {
                coefs[i] += arrayDoublePolynomial.coefs[i];
            }
        }
        return this;
    }

    @Override
    public ArrayDoublePolynomial isubtract(ArrayDoublePolynomial arrayDoublePolynomial) {
        int elseSize = arrayDoublePolynomial.getSize();
        if (elseSize > coefs.length) {
            double[] newCoef = new double[elseSize];
            System.arraycopy(coefs, 0, newCoef, 0, coefs.length);
            for (int i = 0; i < elseSize; i++) {
                newCoef[i] -= arrayDoublePolynomial.coefs[i];
            }
            coefs = newCoef;
        } else {
            int size = Math.min(elseSize, coefs.length);
            for (int i = 0; i < size; i++) {
                coefs[i] -= arrayDoublePolynomial.coefs[i];
            }
        }
        return this;
    }

    @Override
    public ArrayDoublePolynomial imultiply(ArrayDoublePolynomial arrayDoublePolynomial) {
        int size = coefs.length;
        int asize = arrayDoublePolynomial.getSize();
        double[] temp = new double[size + asize];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < asize; j++) {
                temp[i + j] += coefs[i] * arrayDoublePolynomial.get(j);
            }
        }
        coefs = temp;
        return this;
    }

    @Override
    @AnjiUnknow
    public ArrayDoublePolynomial idivide(ArrayDoublePolynomial arrayDoublePolynomial) {
        throw new UnsupportedOperationException();
    }
    //endregion

    @Override
    @AnjiUnknow
    public ArrayDoublePolynomial iinverse() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ArrayDoublePolynomial iprod(double lambda) {
        for (int i = 0; i < coefs.length; i++) {
            coefs[i] *= lambda;
        }
        return this;
    }

    //region Арифметичні операції
    @Override
    public ArrayDoublePolynomial add(DoublePolynomial doublePolynomial) {
        ArrayDoublePolynomial arrayDoublePolynomial = doublePolynomial.toRawPolynomial();
        int elseSize = arrayDoublePolynomial.getSize();
        int size = Math.max(coefs.length, elseSize);
        double[] newCoef = new double[size];
        for (int i = 0; i < size; i++) {
            newCoef[i] = get(i) + arrayDoublePolynomial.get(i);
        }
        return new ArrayDoublePolynomial(newCoef);
    }

    @Override
    public ArrayDoublePolynomial subtract(DoublePolynomial arrayDoublePolynomial) {
        ArrayDoublePolynomial polynomial = arrayDoublePolynomial.toRawPolynomial();
        int elseSize = polynomial.getSize();
        int size = Math.max(coefs.length, elseSize);
        double[] newCoef = new double[size];
        for (int i = 0; i < size; i++) {
            newCoef[i] = get(i) - polynomial.get(i);
        }
        return new ArrayDoublePolynomial(newCoef);
    }

    @Override
    public ArrayDoublePolynomial negate() {
        int size = coefs.length;
        double[] newCoef = new double[size];
        for (int i = 0; i < size; i++) {
            newCoef[i] = -get(i);
        }
        return new ArrayDoublePolynomial(newCoef);
    }

    @Override
    public ArrayDoublePolynomial prod(double lambda) {
        ArrayDoublePolynomial result = deepcopy();
        for (int i = 0; i < coefs.length; i++) {
            result.coefs[i] *= lambda;
        }
        return result;
    }

    @Override
    public ArrayDoublePolynomial multiply(DoublePolynomial doublePolynomial) {
        ArrayDoublePolynomial arrayDoublePolynomial = doublePolynomial.toRawPolynomial();
        int size = coefs.length;
        int asize = arrayDoublePolynomial.getSize();
        ArrayDoublePolynomial result = new ArrayDoublePolynomial(new double[size + asize - 1]);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < asize; j++) {
                result.coefs[i + j] += coefs[i] * arrayDoublePolynomial.get(j);
            }
        }
        return result;
    }
    //endregion

    @Override
    @AnjiUnknow
    public ArrayDoublePolynomial divide(DoublePolynomial doublePolynomial) {
        throw new UnsupportedOperationException();
    }

    @Override
    @AnjiUnknow
    public ArrayDoublePolynomial inverse() {
        throw new UnsupportedOperationException();
    }
    //endregion

    //region Зміна розмірів
    public ArrayDoublePolynomial resize(int newLength) {
        double[] newCoef = new double[newLength];
        int size = Math.min(newLength, coefs.length);
        System.arraycopy(coefs, 0, newCoef, 0, size);
        return new ArrayDoublePolynomial(newCoef);
    }

    public ArrayDoublePolynomial iresize(int newLength) {
        double[] newCoef = new double[newLength];
        int size = Math.min(newLength, coefs.length);
        System.arraycopy(coefs, 0, newCoef, 0, size);
        coefs = newCoef;
        return this;
    }

    @Override
    public ArrayDoublePolynomial toRawPolynomial() {
        return this;
    }
}
