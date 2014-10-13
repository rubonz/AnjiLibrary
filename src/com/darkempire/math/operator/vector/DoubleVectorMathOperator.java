package com.darkempire.math.operator.vector;

import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.math.struct.vector.DoubleVector;

/**
 * Created by siredvin on 13.10.14.
 *
 * @author siredvin
 */
@AnjiUtil
public final class DoubleVectorMathOperator {
    private DoubleVectorMathOperator() {
    }

    public static double sum(DoubleVector vector) {
        double sum = 0;
        int size = vector.getSize();
        for (int i = 0; i < size; i++) {
            sum += vector.get(i);
        }
        return sum;
    }
}
