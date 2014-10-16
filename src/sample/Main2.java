package sample;

import com.darkempire.anji.log.Log;
import com.darkempire.math.struct.function.polynomial.HermitePolynomialBuilder;

/**
 * Create in 10:19
 * Created by siredvin on 22.04.14.
 */
public class Main2 {

    public static void main(String[] args) {
        HermitePolynomialBuilder builder = HermitePolynomialBuilder.createPropabilistsBuilder();
        Log.log(Log.debugIndex, builder.calcIndexed(3));
        Log.log(Log.debugIndex, builder.calcIndexed(5));
    }

}
