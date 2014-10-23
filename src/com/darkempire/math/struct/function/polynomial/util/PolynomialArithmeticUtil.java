package com.darkempire.math.struct.function.polynomial.util;

import com.darkempire.anji.annotation.AnjiRewrite;
import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.anji.log.Log;
import com.darkempire.math.struct.function.polynomial.ArrayDoublePolynomial;
import com.darkempire.math.struct.function.polynomial.DoublePolynomial;
import com.darkempire.math.struct.function.polynomial.IteratedPolynomialBuilder;
import com.darkempire.math.struct.vector.DoubleFixedVector;
import com.darkempire.math.struct.vector.DoubleVector;
import com.darkempire.math.struct.vector.IndexFixedVector;
import com.darkempire.math.struct.vector.IndexVector;
import com.darkempire.math.utils.MathUtils;
import sun.plugin.javascript.navig.Array;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by siredvin on 22.10.14.
 *
 * @author siredvin
 */
@AnjiUtil
public final class PolynomialArithmeticUtil {
    private PolynomialArithmeticUtil() {
    }

    /**
     * Розкриває поліном виду p(ax+b)
     *
     * @param polynomial p(x)
     * @param xCoef      a
     * @param free       b
     * @return p(ax + b) як поліном
     */
    public static ArrayDoublePolynomial expand(ArrayDoublePolynomial polynomial, double xCoef, double free) {
        int size = polynomial.getSize();
        ArrayDoublePolynomial p = new ArrayDoublePolynomial(free, xCoef);
        List<ArrayDoublePolynomial> pList = new ArrayList<>();
        pList.add(p.deepcopy());
        int sizep1 = size - 1;
        for (int i = 1; i < sizep1; i++) {
            ArrayDoublePolynomial pTemp = pList.get(i - 1).multiply(p);
            pList.add(pTemp);
        }
        for (int i = 0; i < sizep1; i++) {
            pList.get(i).iprod(polynomial.get(i + 1));
        }
        ArrayDoublePolynomial result = ArrayDoublePolynomial.sum(pList);
        result.set(0, result.get(0) + polynomial.get(0));
        return result;
    }

    /**
     * Піднесення полінома у дискретну степінь
     *
     * @param polynomial поліном
     * @param pow        степінь
     * @return polynomial^pow
     * @throws java.lang.UnsupportedOperationException бо поки працює лише для поліномів виду ax-b
     */
    @AnjiRewrite(reason = "Для всіх степеней")
    public static ArrayDoublePolynomial pow(DoublePolynomial polynomial, int pow) {
        ArrayDoublePolynomial p = polynomial.toRawPolynomial();
        int size = p.getSize();
        if (size == 1) {
            return new ArrayDoublePolynomial(Math.pow(p.get(0), pow));
        }
        if (p.getSize() > 2) {
            throw new UnsupportedOperationException();
        }
        int[] facts = MathUtils.factArr(pow);
        double a = p.get(1);
        double b = p.get(0);
        int pow1 = pow + 1;
        ArrayDoublePolynomial p1 = new ArrayDoublePolynomial(new double[pow1]);
        for (int k = 0; k < pow1; k++) {
            int c = facts[pow] / (facts[k] * facts[pow - k]);
            p1.set(k, c * Math.pow(a, k) * Math.pow(b, pow - k));
        }
        return p1;
    }
}
