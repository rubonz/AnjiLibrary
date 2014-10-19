package com.darkempire.math.solver.geneticsolver;

import com.darkempire.math.struct.matrix.DoubleMatrix;
import com.darkempire.math.struct.vector.DoubleFixedVector;
import com.darkempire.math.struct.vector.DoubleVector;
import net.sourceforge.evoj.strategies.sorting.AbstractSimpleRating;

/**
 * Created by Сергій on 19.10.2014.
 */
public class Rating extends AbstractSimpleRating<Solution, Double> {
    final private DoubleMatrix m;
    final private DoubleVector b;

    public Rating(DoubleMatrix m, DoubleVector b){
        this.m = m;
        this.b = b;
    }

    public double calcFunction(Solution solution) {
        DoubleVector x = new DoubleFixedVector(solution.getX().size());
        x.fill(index -> solution.getX().get(index));
        return m.multy(x).subtract(b).norm();
    }

    @Override
    public Double doCalcRating(Solution solution) {
        double fn = calcFunction(solution);
        if (Double.isNaN(fn)) {
            return null;
        } else {
            return -fn;
        }
    }
}
