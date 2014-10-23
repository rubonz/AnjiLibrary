package com.darkempire.math.operator.vector;

import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.math.MathMachine;
import com.darkempire.math.struct.vector.DoubleVector;

/**
 * Created by siredvin on 23.10.14.
 *
 * @author siredvin
 */
@AnjiUtil
public final class DoubleVectorCompareOperator {
    private DoubleVectorCompareOperator() {
    }

    public static boolean isPracticalZero(DoubleVector vector) {
        int size = vector.getSize();
        for (int i = 0; i < size; i++) {
            if (Math.abs(vector.get(i)) > MathMachine.MACHINE_PRACTICAL_EPS) {
                return false;
            }
        }
        return true;
    }

}
