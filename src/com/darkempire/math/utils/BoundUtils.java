package com.darkempire.math.utils;

import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.math.struct.SignType;
import com.darkempire.math.struct.function.LinearMultiPolynomial;
import com.darkempire.math.struct.logic.SimpleLinearBoundConditional;

import java.util.Arrays;

/**
 * Created by siredvin on 02.07.14.
 */
@AnjiUtil
public final class BoundUtils {
    private BoundUtils() {
    }

    public static SimpleLinearBoundConditional transformToСanonicalForm(SimpleLinearBoundConditional conditional) {
        int size = conditional.getSize();
        double[] coefs = Arrays.copyOf(conditional.getCoefs(), size + 1);
        switch (conditional.getSign()) {
            case GREAT_THEN:
            case GREAT_THEN_OR_EQL:
                coefs[size] = -1;
                break;
            case LOWER_THEN:
            case LOWER_THEN_OR_EQL:
                coefs[size] = 1;
                break;
            case EQL:
                break;
            case NO_EQL:
                throw new UnsupportedOperationException();
        }
        return new SimpleLinearBoundConditional(new LinearMultiPolynomial(coefs), SignType.EQL, conditional.getRightPart());
    }

    public static SimpleLinearBoundConditional transformToСanonicalForm(SimpleLinearBoundConditional conditional, int systemSize) {
        double[] coefs = Arrays.copyOf(conditional.getCoefs(), systemSize + 1);
        switch (conditional.getSign()) {
            case GREAT_THEN:
            case GREAT_THEN_OR_EQL:
                coefs[systemSize] = -1;
                break;
            case LOWER_THEN:
            case LOWER_THEN_OR_EQL:
                coefs[systemSize] = 1;
                break;
            case EQL:
                break;
            case NO_EQL:
                throw new UnsupportedOperationException();
        }
        return new SimpleLinearBoundConditional(new LinearMultiPolynomial(coefs), SignType.EQL, conditional.getRightPart());
    }
}
