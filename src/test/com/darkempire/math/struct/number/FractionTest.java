package test.com.darkempire.math.struct.number;

import com.darkempire.math.struct.number.Fraction;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by siredvin on 11.09.14.
 */
public class FractionTest extends Assert {

    @Test
    public void testIdivide() {
        //Тестування багаторазового ділення на одне число
        //Спочатку у порядку спадання
        Fraction toTest = new Fraction(8);
        Fraction divide = new Fraction(2);
        assertEquals(new Fraction(4), toTest.idivide(divide));
        assertEquals(new Fraction(2), toTest.idivide(divide));
        assertEquals(new Fraction(1), toTest.idivide(divide));
        assertEquals(new Fraction(1, 2), toTest.idivide(divide));
        assertEquals(new Fraction(1, 4), toTest.idivide(divide));
        assertEquals(new Fraction(1, 8), toTest.idivide(divide));
        assertEquals(new Fraction(1, 16), toTest.idivide(divide));
        //А потім у порядку зростання
        divide = new Fraction(1, 2);
        assertEquals(new Fraction(1, 8), toTest.idivide(divide));
        assertEquals(new Fraction(1, 4), toTest.idivide(divide));
        assertEquals(new Fraction(1, 2), toTest.idivide(divide));
        assertEquals(new Fraction(1), toTest.idivide(divide));
        assertEquals(new Fraction(2), toTest.idivide(divide));
        assertEquals(new Fraction(4), toTest.idivide(divide));
        assertEquals(new Fraction(8), toTest.idivide(divide));
        assertEquals(new Fraction(16), toTest.idivide(divide));
        //Окремі
        assertEquals(new Fraction(1), (new Fraction(19, 2)).idivide(new Fraction(19, 2)));

    }
}
