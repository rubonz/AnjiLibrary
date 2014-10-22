package sample;

import com.darkempire.anji.log.Log;
import com.darkempire.math.operator.matrix.NumberMatrixMathOperator;
import com.darkempire.math.struct.matrix.NumberFixedMatrix;
import com.darkempire.math.struct.matrix.NumberMatrix;
import com.darkempire.math.struct.number.Fraction;

import java.io.FileNotFoundException;
import java.math.BigDecimal;

import static com.darkempire.math.operator.matrix.DoubleMatrixTransformOperator.makeDiagonalForm;
import static com.darkempire.math.operator.matrix.NumberMatrixTransformOperator.makeDiagonalForm;

/**
 * Create in 14:14
 * Created by siredvin on 23.06.14.
 */
public class Main3 {

    public static void main(String[] args) throws FileNotFoundException {
        BigDecimal zero = new BigDecimal("0E-11368");
        Log.log(Log.debugIndex, zero);
        Log.log(Log.debugIndex, zero.compareTo(BigDecimal.ZERO));
    }
}

