package sample;

import com.darkempire.anji.log.Log;
import com.darkempire.math.struct.function.polynomial.DoublePolynomial;
import com.darkempire.math.struct.function.polynomial.LaguerrePolynomialBuilder;

/**
 * Create in 10:19
 * Created by siredvin on 22.04.14.
 */
public class Main2 {

    public static void main(String[] args) {
        Log.log(Log.debugIndex, LaguerrePolynomialBuilder.calcStandalone(3));
        Log.log(Log.debugIndex, LaguerrePolynomialBuilder.calcStandalone(6));
    }

}
