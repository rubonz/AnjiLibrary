package sample;

import com.darkempire.anji.log.Log;
import com.darkempire.math.operator.matrix.NumberMatrixMathOperator;
import com.darkempire.math.struct.function.polynomial.ArrayDoublePolynomial;
import com.darkempire.math.struct.matrix.NumberFixedMatrix;
import com.darkempire.math.struct.matrix.NumberMatrix;
import com.darkempire.math.struct.number.Fraction;
import com.darkempire.math.utils.MathUtils;

import java.io.FileNotFoundException;
import java.math.BigDecimal;

import static com.darkempire.math.operator.matrix.DoubleMatrixTransformOperator.makeDiagonalForm;
import static com.darkempire.math.operator.matrix.NumberMatrixTransformOperator.makeDiagonalForm;
import static com.darkempire.math.struct.function.polynomial.util.PolynomialArithmeticUtil.expand;

/**
 * Create in 14:14
 * Created by siredvin on 23.06.14.
 */
public class Main3 {

    public static void main(String[] args) throws FileNotFoundException {
        ArrayDoublePolynomial source = new ArrayDoublePolynomial(1, 0, 1, 0, 1, 0, -68);
        ArrayDoublePolynomial result = new ArrayDoublePolynomial(-4331, 13020, -16295, 10872, -4079, 816, -68);
        Log.log(Log.debugIndex, expand(source, 1, -2));
        Log.log(Log.debugIndex, result);
        Log.log(Log.debugIndex, result.getSize(), expand(source, 1, -2).getSize());
        Log.err(Log.debugIndex, expand(source, 1, -2).equals(result));
    }
}

