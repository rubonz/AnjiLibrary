package com.darkempire.math.struct.logic;

import com.darkempire.anji.document.tex.ITeXObject;
import com.darkempire.anji.document.tex.TeXEventWriter;
import com.darkempire.math.struct.SignType;
import com.darkempire.math.struct.function.FSomeBoolean;
import com.darkempire.math.struct.function.LinearMultiPolynomial;
import com.darkempire.math.struct.vector.IDoubleVectorProvider;

/**
 * Використовується для зображення лінійного обмеження a_1x_1+a_2x_2+...+a_nx_n < rightPart
 *
 * @author siredvin
 * @since Anji v0.1
 */
public class SimpleLinearBoundConditional implements FSomeBoolean<IDoubleVectorProvider>, IDoubleVectorProvider, ITeXObject {
    /**
     * Ліва частина обмеження
     */
    private LinearMultiPolynomial polynomial;
    /**
     * Знак обмеження
     */
    private SignType sign;
    /**
     * Права частина обмеження
     */
    private double rightPart;

    public SimpleLinearBoundConditional(LinearMultiPolynomial polynomial, SignType sign, double rightPart) {
        this.polynomial = polynomial;
        this.sign = sign;
        this.rightPart = rightPart;
    }

    @Override
    public boolean calc(IDoubleVectorProvider x) {
        double left = polynomial.calc(x);
        boolean result = false;
        switch (sign) {
            case GREAT_THEN:
                result = left > rightPart;
                break;
            case LOWER_THEN:
                result = left < rightPart;
                break;
            case GREAT_THEN_OR_EQL:
                result = left >= rightPart;
                break;
            case LOWER_THEN_OR_EQL:
                result = left <= rightPart;
                break;
            case EQL:
                result = left == rightPart;
                break;
            case NO_EQL:
                result = left != rightPart;
                break;
        }
        return result;
    }

    @Override
    public double get(int index) {
        return polynomial.get(index);
    }

    @Override
    public int getSize() {
        return polynomial.getSize();
    }

    public double getRightPart() {
        return rightPart;
    }

    public SignType getSign() {
        return sign;
    }

    public double[] getCoefs() {
        return polynomial.getCoefs();
    }

    @Override
    public String toString() {
        return polynomial + sign.toString() + rightPart;
    }

    @Override
    public void write(TeXEventWriter out) {
        polynomial.write(out);
        out.append(" ");
        out.append(sign.toTeXString());
        out.append(" ");
        out.append(rightPart);
        out.append(" ");
    }
}
