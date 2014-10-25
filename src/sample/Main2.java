package sample;

import com.darkempire.anji.document.wolfram.util.WolframConvertUtils;
import com.darkempire.anji.log.Log;
import com.darkempire.math.operator.matrix.*;
import com.darkempire.math.struct.function.polynomial.IteratedPolynomialBuilder;
import com.darkempire.math.struct.matrix.DoubleFixedMatrix;
import com.darkempire.math.struct.matrix.DoubleMatrix;
import com.darkempire.math.struct.matrix.DoubleMatrixIndexHolder;
import com.darkempire.math.struct.matrix.NumberMatrix;
import com.darkempire.math.struct.number.Decimal;
import com.darkempire.math.utils.ListUtil;
import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathLinkFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
