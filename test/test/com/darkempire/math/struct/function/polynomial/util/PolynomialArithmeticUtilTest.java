package test.com.darkempire.math.struct.function.polynomial.util;

import com.darkempire.math.struct.function.polynomial.ArrayDoublePolynomial;
import junit.framework.Assert;
import org.junit.Test;

import static com.darkempire.math.struct.function.polynomial.util.PolynomialArithmeticUtil.expand;
import static com.darkempire.math.struct.function.polynomial.util.PolynomialArithmeticUtil.pow;

/**
 * Created by siredvin on 22.10.14.
 *
 * @author siredvin
 */
public class PolynomialArithmeticUtilTest extends Assert {

    @Test
    public void testExpand() {
        assertEquals(new ArrayDoublePolynomial(-34, 7), expand(new ArrayDoublePolynomial(1, 7), 1, -5));
        ArrayDoublePolynomial polynomial = new ArrayDoublePolynomial(1, 0, 1, 0, 1, 0, -68);
        assertEquals(new ArrayDoublePolynomial(-4331, 13020, -16295, 10872, -4079, 816, -68), expand(polynomial, 1, -2));
        assertEquals(new ArrayDoublePolynomial(-1061849, 8921430, -31230101, 58303140, -61223099, 34286280, -8000132), expand(polynomial, 7, -5));
    }

    @Test
    public void testPow() {
        assertEquals(new ArrayDoublePolynomial(3125, 6250, 5000, 2000, 400, 32), pow(new ArrayDoublePolynomial(5, 2), 5));
    }
}
