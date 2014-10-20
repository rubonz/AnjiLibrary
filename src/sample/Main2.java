package sample;

import com.darkempire.anji.log.Log;
import com.darkempire.math.operator.matrix.DoubleMatrixDecompositionOperator;
import com.darkempire.math.operator.matrix.DoubleMatrixTransformOperator;
import com.darkempire.math.struct.matrix.DoubleFixedMatrix;
import com.darkempire.math.struct.matrix.DoubleMatrix;
import com.darkempire.math.struct.vector.DoubleFixedVector;
import com.darkempire.math.struct.vector.DoubleVector;
import com.darkempire.math.utils.ArrayUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Create in 10:19
 * Created by siredvin on 22.04.14.
 */
public class Main2 {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("t0", "t1", "t2", "t3", "t4", "t5", "t6", "t7", "t8");
        Log.log(Log.debugIndex, list.subList(1, 5));
    }

}
