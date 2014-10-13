package test.com.darkempire.math.struct.vector;

import com.darkempire.math.struct.number.Fraction;
import com.darkempire.math.struct.vector.NumberResizeVector;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/**
 * ResizeLinearVector Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>лип. 9, 2014</pre>
 */
public class ResizeLinearVectorTest extends Assert {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: subvector(int length)
     */
    @Test
    public void testSubvectorLength() throws Exception {
        NumberResizeVector<Fraction> temp1 = new NumberResizeVector<>(new Fraction[]{new Fraction(1),
                new Fraction(2), new Fraction(3, 5), new Fraction(20, 10)});
        assertEquals(temp1.subvector(1), new NumberResizeVector<>(new Fraction[]{new Fraction(1)}));
        assertEquals(temp1.subvector(2), new NumberResizeVector<>(new Fraction[]{new Fraction(1),
                new Fraction(2)}));
        assertEquals(temp1.subvector(3), new NumberResizeVector<>(new Fraction[]{new Fraction(1),
                new Fraction(2), new Fraction(3, 5)}));
        assertEquals(temp1.subvector(4), new NumberResizeVector<>(new Fraction[]{new Fraction(1),
                new Fraction(2), new Fraction(3, 5), new Fraction(20, 10)}));
    }

    /**
     * Method: subvector(int startIndex, int length)
     */
    @Test
    public void testSubvectorForStartIndexLength() throws Exception {
        NumberResizeVector<Fraction> temp1 = new NumberResizeVector<>(new Fraction[]{new Fraction(1),
                new Fraction(2), new Fraction(3, 5), new Fraction(20, 10)});
        assertEquals(temp1.subvector(0, 2), new NumberResizeVector<>(new Fraction[]{new Fraction(1),
                new Fraction(2)}));
        assertEquals(temp1.subvector(1, 2), new NumberResizeVector<>(new Fraction[]{
                new Fraction(2), new Fraction(3, 5)}));
        assertEquals(temp1.subvector(2, 2), new NumberResizeVector<>(new Fraction[]{new Fraction(3, 5), new Fraction(20, 10)}));
        assertEquals(temp1.subvector(1, 3), new NumberResizeVector<>(new Fraction[]{
                new Fraction(2), new Fraction(3, 5), new Fraction(20, 10)}));
    }


    /**
     * Method: inegate()
     */
    @Test
    public void testInegate() throws Exception {
        assertEquals((new NumberResizeVector<>(new Fraction[]{new Fraction(-5107, 2867), new Fraction(957, -6300), new Fraction(8876, 9335)})).inegate(), new NumberResizeVector<>(new Fraction[]{new Fraction(5107, 2867), new Fraction(-957, -6300), new Fraction(-8876, 9335)}));
        assertEquals((new NumberResizeVector<>(new Fraction[]{new Fraction(-4573, -3599), new Fraction(7514, -5546), new Fraction(-538, -3703)})).inegate(), new NumberResizeVector<>(new Fraction[]{new Fraction(4573, -3599), new Fraction(-7514, -5546), new Fraction(538, -3703)}));
        assertEquals((new NumberResizeVector<>(new Fraction[]{new Fraction(-1915, -3661), new Fraction(-4654, 5480), new Fraction(3715, 9982)})).inegate(), new NumberResizeVector<>(new Fraction[]{new Fraction(1915, -3661), new Fraction(4654, 5480), new Fraction(-3715, 9982)}));
        assertEquals((new NumberResizeVector<>(new Fraction[]{new Fraction(9359, -422), new Fraction(4991, -5904), new Fraction(3949, 1926)})).inegate(), new NumberResizeVector<>(new Fraction[]{new Fraction(-9359, -422), new Fraction(-4991, -5904), new Fraction(-3949, 1926)}));
        assertEquals((new NumberResizeVector<>(new Fraction[]{new Fraction(-2251, 4957), new Fraction(2693, 8941), new Fraction(4973, -2265)})).inegate(), new NumberResizeVector<>(new Fraction[]{new Fraction(2251, 4957), new Fraction(-2693, 8941), new Fraction(-4973, -2265)}));
        assertEquals((new NumberResizeVector<>(new Fraction[]{new Fraction(-4493, 2218), new Fraction(-7996, -3197), new Fraction(-2876, 8829)})).inegate(), new NumberResizeVector<>(new Fraction[]{new Fraction(4493, 2218), new Fraction(7996, -3197), new Fraction(2876, 8829)}));
        assertEquals((new NumberResizeVector<>(new Fraction[]{new Fraction(1246, 1800), new Fraction(-4978, -1161), new Fraction(-6487, -5288)})).inegate(), new NumberResizeVector<>(new Fraction[]{new Fraction(-1246, 1800), new Fraction(4978, -1161), new Fraction(6487, -5288)}));
        assertEquals((new NumberResizeVector<>(new Fraction[]{new Fraction(4268, -7951), new Fraction(-8970, -7862), new Fraction(2646, 2498)})).inegate(), new NumberResizeVector<>(new Fraction[]{new Fraction(-4268, -7951), new Fraction(8970, -7862), new Fraction(-2646, 2498)}));
        assertEquals((new NumberResizeVector<>(new Fraction[]{new Fraction(4015, -6109), new Fraction(5007, 8874), new Fraction(4606, 1087)})).inegate(), new NumberResizeVector<>(new Fraction[]{new Fraction(-4015, -6109), new Fraction(-5007, 8874), new Fraction(-4606, 1087)}));
        assertEquals((new NumberResizeVector<>(new Fraction[]{new Fraction(-2098, -8367), new Fraction(5863, -456), new Fraction(-9128, 6929)})).inegate(), new NumberResizeVector<>(new Fraction[]{new Fraction(2098, -8367), new Fraction(-5863, -456), new Fraction(9128, 6929)}));
    }

    /**
     * Method: negate()
     */
    @Test
    public void testNegate() throws Exception {
        assertEquals((new NumberResizeVector<>(new Fraction[]{new Fraction(-6448, -1692), new Fraction(9932, -6682), new Fraction(-9081, -3215)})).negate(), new NumberResizeVector<>(new Fraction[]{new Fraction(6448, -1692), new Fraction(-9932, -6682), new Fraction(9081, -3215)}));
        assertEquals((new NumberResizeVector<>(new Fraction[]{new Fraction(6983, -1072), new Fraction(8878, -2721), new Fraction(-8588, -1219)})).negate(), new NumberResizeVector<>(new Fraction[]{new Fraction(-6983, -1072), new Fraction(-8878, -2721), new Fraction(8588, -1219)}));
        assertEquals((new NumberResizeVector<>(new Fraction[]{new Fraction(434, 2154), new Fraction(9162, -4785), new Fraction(-5360, -9064)})).negate(), new NumberResizeVector<>(new Fraction[]{new Fraction(-434, 2154), new Fraction(-9162, -4785), new Fraction(5360, -9064)}));
        assertEquals((new NumberResizeVector<>(new Fraction[]{new Fraction(-5896, 4896), new Fraction(-8297, -1708), new Fraction(9890, 6403)})).negate(), new NumberResizeVector<>(new Fraction[]{new Fraction(5896, 4896), new Fraction(8297, -1708), new Fraction(-9890, 6403)}));
        assertEquals((new NumberResizeVector<>(new Fraction[]{new Fraction(9712, 5010), new Fraction(-7629, -434), new Fraction(7372, -2501)})).negate(), new NumberResizeVector<>(new Fraction[]{new Fraction(-9712, 5010), new Fraction(7629, -434), new Fraction(-7372, -2501)}));
        assertEquals((new NumberResizeVector<>(new Fraction[]{new Fraction(-1698, 3474), new Fraction(-3727, 7685), new Fraction(-5023, 7967)})).negate(), new NumberResizeVector<>(new Fraction[]{new Fraction(1698, 3474), new Fraction(3727, 7685), new Fraction(5023, 7967)}));
        assertEquals((new NumberResizeVector<>(new Fraction[]{new Fraction(-1671, 7649), new Fraction(2777, 2402), new Fraction(-1543, -8121)})).negate(), new NumberResizeVector<>(new Fraction[]{new Fraction(1671, 7649), new Fraction(-2777, 2402), new Fraction(1543, -8121)}));
        assertEquals((new NumberResizeVector<>(new Fraction[]{new Fraction(-4, -5388), new Fraction(-8300, -2020), new Fraction(-2327, 1454)})).negate(), new NumberResizeVector<>(new Fraction[]{new Fraction(4, -5388), new Fraction(8300, -2020), new Fraction(2327, 1454)}));
        assertEquals((new NumberResizeVector<>(new Fraction[]{new Fraction(247, 4913), new Fraction(5782, 1281), new Fraction(-1837, 1840)})).negate(), new NumberResizeVector<>(new Fraction[]{new Fraction(-247, 4913), new Fraction(-5782, 1281), new Fraction(1837, 1840)}));
        assertEquals((new NumberResizeVector<>(new Fraction[]{new Fraction(-6003, -483), new Fraction(-8126, 5366), new Fraction(-5734, -9079)})).negate(), new NumberResizeVector<>(new Fraction[]{new Fraction(6003, -483), new Fraction(8126, 5366), new Fraction(5734, -9079)}));

    }


} 
