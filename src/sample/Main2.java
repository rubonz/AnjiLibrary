package sample;

import com.darkempire.anji.log.Log;
import com.darkempire.math.operator.matrix.DoubleMatrixTransformOperator;
import com.darkempire.math.struct.matrix.DoubleFixedMatrix;
import com.darkempire.math.struct.matrix.DoubleMatrix;

/**
 * Create in 10:19
 * Created by siredvin on 22.04.14.
 */
public class Main2 {

    public static void main(String[] args) throws Exception {
        DoubleMatrix a = DoubleFixedMatrix.createInstance(3, 3, new double[]{
                1, 1, 1,
                2, 2, 3,
                4, 5, 6
        });
        Log.log(Log.debugIndex, DoubleMatrixTransformOperator.makeDiagonalForm(a));
    }
}
