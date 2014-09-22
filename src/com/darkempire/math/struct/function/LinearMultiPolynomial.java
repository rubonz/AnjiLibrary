package com.darkempire.math.struct.function;

import com.darkempire.anji.log.Log;
import com.darkempire.anji.document.tex.ITeXObject;
import com.darkempire.anji.document.tex.TeXEventWriter;
import com.darkempire.math.struct.vector.IDoubleVectorProvider;

/**
 * Created by siredvin on 02.07.14.
 */
public class LinearMultiPolynomial implements FDoubleVectorDouble, IDoubleVectorProvider, ITeXObject {
    private double[] coefs;

    public LinearMultiPolynomial(double... coefs) {
        this.coefs = coefs;
    }

    @Override
    public double calc(IDoubleVectorProvider provider) {
        double result = 0;
        int size = provider.getSize();
        for (int i = 0; i < size; i++) {
            result += get(i) * provider.get(i);
        }
        return result;
    }

    @Override
    public double get(int index) {
        if (index >= coefs.length)
            return 0;
        return coefs[index];
    }

    @Override
    public int getSize() {
        return coefs.length;
    }

    public double[] getCoefs() {
        return coefs;
    }

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
                Log.log(Log.debugIndex, "temp else");
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
}
