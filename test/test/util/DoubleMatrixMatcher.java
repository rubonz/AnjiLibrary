package test.util;

import com.darkempire.math.operator.matrix.DoubleMatrixCompareOperator;
import com.darkempire.math.struct.matrix.DoubleMatrix;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Created by siredvin on 23.10.14.
 *
 * @author siredvin
 */
public class DoubleMatrixMatcher extends TypeSafeMatcher<DoubleMatrix> {
    private DoubleMatrix matrix;
    private double eps;

    public DoubleMatrixMatcher(DoubleMatrix matrix, double eps) {
        this.matrix = matrix;
        this.eps = eps;
    }

    public static Matcher<DoubleMatrix> equalsEps(DoubleMatrix matrix, double eps) {
        return new DoubleMatrixMatcher(matrix, eps);
    }

    @Override
    protected boolean matchesSafely(DoubleMatrix matrix) {
        return DoubleMatrixCompareOperator.isEqualsEps(this.matrix, matrix, eps);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(matrix.toString());
    }
}
