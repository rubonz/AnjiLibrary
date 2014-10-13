package test.com.darkempire.math.operator.matrix;

import com.darkempire.math.struct.matrix.DoubleFixedMatrix;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static com.darkempire.math.operator.matrix.DoubleMatrixDeterminantOperator.det;

/**
 * DoubleMatrixOperator Tester.
 *
 * @author SirEdvin
 * @version 1.0
 * @since <pre>вер. 8, 2014</pre>
 */
public class DoubleMatrixDeterminatorOperatorTest extends Assert {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testDetUpper() throws Exception {
        double delta = 1E-100;
        //Цей тест валиться при точності 1e-7
        assertEquals(143156154, det(DoubleFixedMatrix.createInstance(6, 6, new double[]{30, 22, 35, 4, 6, 13, 21, 32, 1, 16, 18, 39, 20, 0, 2, 16, 3, 30, 23, 26, 13, 21, 17, 0, 22, 22, 7, 39, 35, 5, 6, 20, 11, 1, 38, 39})), delta);
        //Цей тест валиться при точності 1e-9
        assertEquals(-40010303, det(DoubleFixedMatrix.createInstance(6, 6, new double[]{36, 4, 29, 34, 29, 1, 14, 19, 26, 26, 40, 17, 18, 7, 18, 35, 16, 2, 22, 14, 23, 38, 26, 0, 21, 18, 34, 20, 8, 4, 14, 19, 23, 0, 6, 18})), delta);
        //Цей тест валиться при точності 1e-7
        assertEquals(102615970, det(DoubleFixedMatrix.createInstance(6, 6, new double[]{21, 1, 38, 35, 27, 38, 2, 9, 9, 34, 18, 26, 30, 18, 34, 28, 35, 22, 11, 8, 39, 25, 39, 4, 0, 36, 16, 3, 16, 15, 6, 26, 31, 28, 26, 29})), delta);
        //Цей тест валиться при точності 1e-8
        assertEquals(263712128, det(DoubleFixedMatrix.createInstance(6, 6, new double[]{33, 39, 19, 21, 20, 25, 34, 17, 26, 40, 24, 5, 2, 12, 33, 34, 20, 5, 0, 18, 16, 26, 18, 30, 1, 34, 6, 21, 7, 1, 40, 6, 8, 15, 1, 0})), delta);
        //Цей тест валиться при точності 1e-9
        assertEquals(-6745497, det(DoubleFixedMatrix.createInstance(5, 5, new double[]{31, 14, 26, 25, 22, 38, 13, 28, 4, 20, 18, 24, 35, 2, 24, 26, 40, 27, 31, 32, 11, 31, 26, 19, 9})), delta);
        //Цей тест валиться при точності 1e-9
        assertEquals(2807165, det(DoubleFixedMatrix.createInstance(5, 5, new double[]{20, 35, 12, 25, 10, 3, 19, 34, 30, 18, 3, 38, 21, 26, 11, 2, 31, 15, 29, 10, 23, 29, 33, 40, 3})), delta);
        //Цей тест валиться при точності 1e-9
        assertEquals(-1024408, det(DoubleFixedMatrix.createInstance(5, 5, new double[]{11, 3, 16, 23, 27, 14, 3, 11, 19, 14, 17, 26, 19, 33, 10, 5, 11, 33, 23, 37, 15, 35, 20, 18, 8})), delta);
        //Цей тест валиться при точності 1e-9
        assertEquals(9920243, det(DoubleFixedMatrix.createInstance(5, 5, new double[]{11, 36, 10, 28, 21, 34, 16, 40, 7, 2, 5, 20, 27, 26, 1, 27, 35, 13, 7, 11, 14, 10, 29, 11, 39})), delta);
        //Цей тест валиться при точності 1e-12
        assertEquals(16280, det(DoubleFixedMatrix.createInstance(3, 3, new double[]{35, 26, 18, 30, 31, 40, 40, 12, 24})), delta);
        //Цей тест валиться при точності 1e-13
        assertEquals(3377, det(DoubleFixedMatrix.createInstance(3, 3, new double[]{27, 28, 35, 5, 24, 18, 8, 5, 15})), delta);
        //Цей тест валиться при точності 1e-12
        assertEquals(8581, det(DoubleFixedMatrix.createInstance(3, 3, new double[]{13, 27, 28, 23, 16, 31, 14, 37, 14})), delta);
        //Ті, що ніжчі взагалі не валяться
        assertEquals(-2422, det(DoubleFixedMatrix.createInstance(3, 3, new double[]{2, 2, 7, 32, 36, 39, 18, 3, 34})), delta);
        assertEquals(18, det(DoubleFixedMatrix.createInstance(2, 2, new double[]{18, 30, 12, 21})), delta);
        assertEquals(40, det(DoubleFixedMatrix.createInstance(2, 2, new double[]{13, 9, 36, 28})), delta);
        assertEquals(576, det(DoubleFixedMatrix.createInstance(2, 2, new double[]{18, 7, 0, 32})), delta);
        assertEquals(266, det(DoubleFixedMatrix.createInstance(2, 2, new double[]{30, 22, 37, 36})), delta);
    }

    @Test
    public void testDetLower() throws Exception {
        double delta = 1E-5;
        //Цей тест валиться при точності 1e-7
        assertEquals(143156154, det(DoubleFixedMatrix.createInstance(6, 6, new double[]{30, 22, 35, 4, 6, 13, 21, 32, 1, 16, 18, 39, 20, 0, 2, 16, 3, 30, 23, 26, 13, 21, 17, 0, 22, 22, 7, 39, 35, 5, 6, 20, 11, 1, 38, 39}), 1), delta);
        //Цей тест валиться при точності 1e-9
        assertEquals(-40010303, det(DoubleFixedMatrix.createInstance(6, 6, new double[]{36, 4, 29, 34, 29, 1, 14, 19, 26, 26, 40, 17, 18, 7, 18, 35, 16, 2, 22, 14, 23, 38, 26, 0, 21, 18, 34, 20, 8, 4, 14, 19, 23, 0, 6, 18}), 1), delta);
        //Цей тест валиться при точності 1e-7
        assertEquals(102615970, det(DoubleFixedMatrix.createInstance(6, 6, new double[]{21, 1, 38, 35, 27, 38, 2, 9, 9, 34, 18, 26, 30, 18, 34, 28, 35, 22, 11, 8, 39, 25, 39, 4, 0, 36, 16, 3, 16, 15, 6, 26, 31, 28, 26, 29}), 1), delta);
        //Цей тест валиться при точності 1e-8
        assertEquals(263712128, det(DoubleFixedMatrix.createInstance(6, 6, new double[]{33, 39, 19, 21, 20, 25, 34, 17, 26, 40, 24, 5, 2, 12, 33, 34, 20, 5, 0, 18, 16, 26, 18, 30, 1, 34, 6, 21, 7, 1, 40, 6, 8, 15, 1, 0}), 1), delta);
        //Цей тест валиться при точності 1e-9
        assertEquals(-6745497, det(DoubleFixedMatrix.createInstance(5, 5, new double[]{31, 14, 26, 25, 22, 38, 13, 28, 4, 20, 18, 24, 35, 2, 24, 26, 40, 27, 31, 32, 11, 31, 26, 19, 9}), 1), delta);
        //Цей тест валиться при точності 1e-9
        assertEquals(2807165, det(DoubleFixedMatrix.createInstance(5, 5, new double[]{20, 35, 12, 25, 10, 3, 19, 34, 30, 18, 3, 38, 21, 26, 11, 2, 31, 15, 29, 10, 23, 29, 33, 40, 3}), 1), delta);
        //Цей тест валиться при точності 1e-9
        assertEquals(-1024408, det(DoubleFixedMatrix.createInstance(5, 5, new double[]{11, 3, 16, 23, 27, 14, 3, 11, 19, 14, 17, 26, 19, 33, 10, 5, 11, 33, 23, 37, 15, 35, 20, 18, 8}), 1), delta);
        //Цей тест валиться при точності 1e-9
        assertEquals(9920243, det(DoubleFixedMatrix.createInstance(5, 5, new double[]{11, 36, 10, 28, 21, 34, 16, 40, 7, 2, 5, 20, 27, 26, 1, 27, 35, 13, 7, 11, 14, 10, 29, 11, 39}), 1), delta);
        //Цей тест валиться при точності 1e-12
        assertEquals(16280, det(DoubleFixedMatrix.createInstance(3, 3, new double[]{35, 26, 18, 30, 31, 40, 40, 12, 24}), 1), delta);
        //Цей тест валиться при точності 1e-13
        assertEquals(3377, det(DoubleFixedMatrix.createInstance(3, 3, new double[]{27, 28, 35, 5, 24, 18, 8, 5, 15}), 1), delta);
        //Цей тест валиться при точності 1e-12
        assertEquals(8581, det(DoubleFixedMatrix.createInstance(3, 3, new double[]{13, 27, 28, 23, 16, 31, 14, 37, 14}), 1), delta);
        //Ті, що ніжчі взагалі не валяться
        assertEquals(-2422, det(DoubleFixedMatrix.createInstance(3, 3, new double[]{2, 2, 7, 32, 36, 39, 18, 3, 34}), 1), delta);
        assertEquals(18, det(DoubleFixedMatrix.createInstance(2, 2, new double[]{18, 30, 12, 21}), 1), delta);
        assertEquals(40, det(DoubleFixedMatrix.createInstance(2, 2, new double[]{13, 9, 36, 28}), 1), delta);
        assertEquals(576, det(DoubleFixedMatrix.createInstance(2, 2, new double[]{18, 7, 0, 32}), 1), delta);
        assertEquals(266, det(DoubleFixedMatrix.createInstance(2, 2, new double[]{30, 22, 37, 36}), 1), delta);
    }


} 
