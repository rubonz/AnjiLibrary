package sample;

import com.darkempire.anji.log.Log;
import com.darkempire.math.struct.function.polynomial.ChebyshevPolynomialBuilder;
import com.darkempire.math.struct.function.polynomial.DoublePolynomial;
import com.darkempire.math.struct.function.polynomial.LaguerrePolynomialBuilder;

/**
 * Create in 10:19
 * Created by siredvin on 22.04.14.
 */
public class Main2 {

    public static void main(String[] args) {
        ChebyshevPolynomialBuilder builder = ChebyshevPolynomialBuilder.createFirstKindBuilder();
        Log.log(Log.debugIndex, builder.calcIndexed(3));
        Log.log(Log.debugIndex, builder.calcIndexed(5));
    }

}
