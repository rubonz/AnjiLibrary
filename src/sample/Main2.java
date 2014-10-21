package sample;

import com.darkempire.anji.document.wolfram.util.WolframConvertUtils;
import com.darkempire.anji.log.Log;
import com.darkempire.math.operator.matrix.DoubleMatrixInverseOperator;
import com.darkempire.math.operator.matrix.DoubleMatrixTransformOperator;
import com.darkempire.math.struct.matrix.DoubleFixedMatrix;
import com.darkempire.math.struct.matrix.DoubleMatrixIndexHolder;

/**
 * Create in 10:19
 * Created by siredvin on 22.04.14.
 */
public class Main2 {

    public static void main(String[] args) {
        DoubleMatrixIndexHolder matrix = new DoubleMatrixIndexHolder(DoubleFixedMatrix.createInstance(3, 4, new double[]{
                1, 2, 3, 4,
                1, 2, 3, 4,
                5, 4, 3, 1
        }));
        Log.log(Log.debugIndex, WolframConvertUtils.convertDoubleMatrix(matrix));
        Log.log(Log.debugIndex, WolframConvertUtils.convertDoubleMatrix(DoubleMatrixInverseOperator.pseudoInverse(matrix)));
    }

}
