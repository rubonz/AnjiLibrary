package sample;

import com.darkempire.anji.log.Log;
import com.darkempire.math.struct.function.polynomial.IteratedPolynomialBuilder;

/**
 * Create in 10:19
 * Created by siredvin on 22.04.14.
 */
public class Main2 {

    public static void main(String[] args) throws Exception {
        for (IteratedPolynomialBuilder.PolynomialType p : IteratedPolynomialBuilder.PolynomialType.values()) {
            Log.log(Log.debugIndex, p);
        }
    }
}
