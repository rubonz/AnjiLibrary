package sample;

import com.darkempire.anji.document.wolfram.util.WolframConvertUtils;
import com.darkempire.anji.log.Log;
import com.darkempire.math.struct.matrix.DoubleFixedMatrix;
import com.darkempire.math.struct.matrix.DoubleMatrix;

import java.io.FileNotFoundException;

import static com.darkempire.math.operator.matrix.DoubleMatrixInverseOperator.*;

/**
 * Create in 14:14
 * Created by siredvin on 23.06.14.
 */
public class Main3 {

    public static void main(String[] args) throws FileNotFoundException {
        /*DoubleMatrix a = DoubleFixedMatrix.createInstance( 2 , 3 ,new double[]{ 0.8970050194306886, 0.9857680155104352, 0.7601300603098005, 0.9521287306168374, 0.6272604169687124, 0.35445771652890967 });
        Log.log(Log.debugIndex, pseudoInverse(a));*/
        DoubleMatrix a = DoubleFixedMatrix.createInstance(4, 3, new double[]{
                1, -1, 0,
                -1, 2, 1,
                2, -3, -1,
                0, -1, 1
        });
        Log.log(Log.debugIndex, pseudoInverse(a));
    }
}

