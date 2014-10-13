package test.com.darkempire.math.operator.matrix;

import com.darkempire.math.struct.matrix.DoubleMatrix;
import com.darkempire.math.struct.matrix.DoubleFixedMatrix;
import org.junit.Assert;
import org.junit.Test;

import static com.darkempire.math.operator.matrix.DoubleMatrixCompareOperator.*;
import static com.darkempire.math.operator.matrix.DoubleMatrixInverseOperator.*;

/**
 * Created by siredvin on 10.09.14.
 */
public class DoubleMatrixMathOperatorTest extends Assert {

    @Test
    public void testCalcInverseMatrix() {
        DoubleMatrix result = DoubleFixedMatrix.createInstance(3, 3, new double[]{0.052631578947368421053, -0.026315789473684210526,
                0.15789473684210526316, -0.078947368421052631579,
                0.28947368421052631579, -0.23684210526315789474,
                0.36842105263157894737, -0.18421052631578947368,
                0.10526315789473684211});
        DoubleMatrix calced = inverse(DoubleFixedMatrix.createInstance(3, 3, new double[]{1, 2, 3, 6, 4, 0, 7, 0, -1}));
        assertTrue(result + "\n" + calced, isEqualsMachineHalfEps(result, calced));
        //TODO:додати більше тестів
    }
}
