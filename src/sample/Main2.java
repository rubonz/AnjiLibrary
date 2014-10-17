package sample;

import com.darkempire.anji.log.Log;
import com.darkempire.math.operator.matrix.DoubleMatrixDecompositionOperator;
import com.darkempire.math.operator.matrix.DoubleMatrixTransformOperator;
import com.darkempire.math.struct.matrix.DoubleFixedMatrix;
import com.darkempire.math.struct.matrix.DoubleMatrix;

/**
 * Create in 10:19
 * Created by siredvin on 22.04.14.
 */
public class Main2 {

    public static void main(String[] args) {
        DoubleMatrix a = DoubleFixedMatrix.createInstance(3, 3, new double[]{
                10, -5, -5,
                -5, 7, -2,
                -5, -2, 7
        });
        Log.log(Log.debugIndex, a);
        Log.log(Log.debugIndex, DoubleMatrixTransformOperator.makeDiagonalForm(a));
        DoubleMatrixDecompositionOperator.LDLDecompositionResult result = DoubleMatrixDecompositionOperator.LDLDecomposition(a);
        Log.log(Log.debugIndex, result.L.multy(result.D).multy(result.L.transpose()));
        Log.log(Log.debugIndex, result.D);
    }

}
