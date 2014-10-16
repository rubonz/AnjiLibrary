package com.darkempire.math.struct.function.polynomial;

import com.darkempire.anji.annotation.AnjiStandartize;
import com.darkempire.anji.document.tex.ITeXObject;
import com.darkempire.anji.document.tex.TeXEventWriter;
import com.darkempire.math.struct.LinearCalcable;
import com.darkempire.math.struct.function.FDoubleVectorDouble;
import com.darkempire.math.struct.vector.IDoubleVectorProvider;

/**
 * Created by siredvin on 02.07.14.
 *
 * @author siredvin
 */
@AnjiStandartize
public class LinearMultiDoublePolynomial implements FDoubleVectorDouble, LinearCalcable<LinearMultiDoublePolynomial>, IDoubleVectorProvider, ITeXObject {
    private double[] coefs;

    //region Конструктори
    public LinearMultiDoublePolynomial(double... coefs) {
        this.coefs = coefs;
    }
    //endregion

    @Override
    public double calc(IDoubleVectorProvider provider) {
        double result = 0;
        int size = provider.getSize();
        for (int i = 0; i < size; i++) {
            result += get(i) * provider.get(i);
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

    public int getDimentions() {
        return coefs.length;
    }

    public int getMaxPower() {
        return 1;
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
            builder.append("*x_1");
        }
        for (int i = 1; i < coefs.length; i++) {
            double temp = coefs[i];
            if (temp != 0) {
                if (temp > 0) {
                    if (builder.length() != 0) {
                        builder.append('+');
                    }
                    builder.append(temp);
                    builder.append("*x_");
                    builder.append(i + 1);
                } else {
                    builder.append(temp);
                    builder.append("*x_");
                    builder.append(i + 1);
                }
            }
        }
        return builder.toString();
    }

    @Override
    public void write(TeXEventWriter out) {
        boolean isStarted = false;
        StringBuilder builder = new StringBuilder();
        if (coefs[0] != 0) {
            isStarted = true;
            if (coefs[0] == 1) {
                builder.append("x_{1}");
            } else {
                if (coefs[0] == -1) {
                    builder.append("-x_{1}");
                } else {
                    builder.append(coefs[0]);
                    builder.append("x_{1}");
                }
            }
        }
        for (int i = 1; i < coefs.length; i++) {
            double temp = coefs[i];
            if (temp == 1) {
                if (isStarted) {
                    builder.append("+");
                } else {
                    isStarted = true;
                }
                builder.append("x_{");
                builder.append(i + 1);
                builder.append("}");
                continue;
            }
            if (temp == -1) {
                builder.append("-");
                if (!isStarted) {
                    isStarted = true;
                }
                builder.append("x_{");
                builder.append(i + 1);
                builder.append("}");
                continue;
            }
            if (temp != 0) {
                if (temp > 0) {
                    if (isStarted) {
                        builder.append("+");
                    } else {
                        isStarted = true;
                    }
                    builder.append(temp);
                    builder.append("x_{");
                    builder.append(i + 1);
                    builder.append("}");
                } else {
                    builder.append(temp);
                    builder.append("x_{");
                    builder.append(i + 1);
                    builder.append("}");
                }
            }
        }
        out.append(builder.toString());
    }

    @Override
    public LinearMultiDoublePolynomial deepcopy() {
        return new LinearMultiDoublePolynomial(coefs.clone());
    }
    //endregion

    //region Арифметичні операції з присвоєнням
    @Override
    public LinearMultiDoublePolynomial inegate() {
        for (int i = 0; i < coefs.length; i++) {
            coefs[i] = -coefs[i];
        }
        return this;
    }

    @Override
    public LinearMultiDoublePolynomial iadd(LinearMultiDoublePolynomial linearMultiDoublePolynomial) {
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
    public LinearMultiDoublePolynomial isubtract(LinearMultiDoublePolynomial linearMultiDoublePolynomial) {
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

    public LinearMultiDoublePolynomial iprop(double lambda) {
        for (int i = 0; i < coefs.length; i++) {
            coefs[i] *= lambda;
        }
        return this;
    }
    //endregion

    //region Арифметичні операції
    @Override
    public LinearMultiDoublePolynomial add(LinearMultiDoublePolynomial linearMultiDoublePolynomial) {
        int elseSize = linearMultiDoublePolynomial.getSize();
        int size = Math.max(coefs.length, elseSize);
        double[] newCoef = new double[size];
        for (int i = 0; i < size; i++) {
            newCoef[i] = get(i) + linearMultiDoublePolynomial.get(i);
        }
        return new LinearMultiDoublePolynomial(newCoef);
    }

    @Override
    public LinearMultiDoublePolynomial subtract(LinearMultiDoublePolynomial linearMultiDoublePolynomial) {
        int elseSize = linearMultiDoublePolynomial.getSize();
        int size = Math.max(coefs.length, elseSize);
        double[] newCoef = new double[size];
        for (int i = 0; i < size; i++) {
            newCoef[i] = get(i) - linearMultiDoublePolynomial.get(i);
        }
        return new LinearMultiDoublePolynomial(newCoef);
    }

    @Override
    public LinearMultiDoublePolynomial negate() {
        int size = coefs.length;
        double[] newCoef = new double[size];
        for (int i = 0; i < size; i++) {
            newCoef[i] = -get(i);
        }
        return new LinearMultiDoublePolynomial(newCoef);
    }
    //endregion
}
