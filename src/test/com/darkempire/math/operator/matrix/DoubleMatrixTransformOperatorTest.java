package test.com.darkempire.math.operator.matrix;

import com.darkempire.math.struct.matrix.DoubleFixedMatrix;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.darkempire.math.operator.matrix.DoubleMatrixTransformOperator.makeDiagonalForm;
import static com.darkempire.math.operator.matrix.DoubleMatrixTransformOperator.makeUpperTriangle;
import static com.darkempire.math.operator.matrix.DoubleMatrixTransformOperator.makeLowerTriangle;
import static com.darkempire.math.operator.matrix.DoubleMatrixCompareOperator.*;

/**
 * Created by siredvin on 08.09.14.
 */
//TODO:додати в тест більше матриць
//TODO:адекватні помилки
public class DoubleMatrixTransformOperatorTest extends Assert {
    @Before
    public void before() {

    }

    @After
    public void after() {

    }

    @Test
    public void testUpperTriangle() {
        assertTrue(isEqualsMachineHalfEps(DoubleFixedMatrix.createInstance(2, 2, new double[]{2, 1, 0, 4.5}), makeUpperTriangle(DoubleFixedMatrix.createInstance(2, 2, new double[]{2, 1, 5, 7}))));
        assertEquals(null, makeUpperTriangle(DoubleFixedMatrix.createInstance(2, 2, new double[]{5, 7, 5, 7})));
        assertTrue(isEqualsMachineHalfEps(DoubleFixedMatrix.createInstance(3, 3, new double[]{7, 6, 5, 0, -20.0 / 7.0, -26.0 / 7.0, 0, 0, 77.0 / 10.0}), makeUpperTriangle(DoubleFixedMatrix.createInstance(3, 3, new double[]{7, 6, 5, 8, 4, 2, 5, 1, 7}))));
    }

    @Test
    public void testLowerTriangle() {
        assertTrue(isEqualsMachineHalfEps(DoubleFixedMatrix.createInstance(3, 3, new double[]{-77.0 / 13.0, 0, 0, 46.0 / 7.0, 26.0 / 7.0, 0, 5, 1, 7}), makeLowerTriangle(DoubleFixedMatrix.createInstance(3, 3, new double[]{7, 6, 5, 8, 4, 2, 5, 1, 7}))));
        //assertEquals(FixedDoubleMatrix.createInstance(3,3,new double[]{-77.0/13.0,0,0,46.0/7.0,26.0/7.0,0,5,1,7}),makeLowerTriangle(FixedDoubleMatrix.createInstance(3,3,new double[]{7,6,5,8,4,2,5,1,7})));

    }

    @Test
    public void testDiagonalForm() {
        assertTrue("FAIL", isEqualsMachineHalfEps(DoubleFixedMatrix.createInstance(3, 3, new double[]{4, 0, 0, 0, -0.75, 0, 0, 0, 72}), makeDiagonalForm(DoubleFixedMatrix.createInstance(3, 3, new double[]{4, 5, 6, 3, 3, -3, 7, 0, -5}))));
        //assertEquals(FixedDoubleMatrix.createInstance(3,3,new double[]{4, 0, 0,0, -0.75, 0,0, 0, 72}),makeDiagonalForm(FixedDoubleMatrix.createInstance(3,3,new double[]{4,5,6,3,3,-3,7,0,-5})));
    }
}
