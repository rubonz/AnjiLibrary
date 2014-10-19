package com.darkempire.math.solver;

import com.darkempire.math.solver.geneticsolver.Rating;
import com.darkempire.math.solver.geneticsolver.Solution;
import com.darkempire.math.struct.matrix.DoubleMatrix;
import com.darkempire.math.struct.vector.DoubleFixedVector;
import com.darkempire.math.struct.vector.DoubleVector;
import net.sourceforge.evoj.GenePool;
import net.sourceforge.evoj.core.DefaultPoolFactory;
import net.sourceforge.evoj.handlers.DefaultHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Сергій on 19.10.2014.
 */
public class GeneticLinearEquationSystemSolver implements ILinearEquationSystemSolver{
    @Override
    public DoubleVector solve(DoubleMatrix m, DoubleVector b) {

        Map<String, String> context = new HashMap<>();
        context.put("itemsCount", Integer.toString(m.getColumnCount()));
        double max = getAbsMax(m, b);
        context.put("minElement", Double.toString(-max));
        context.put("maxElement", Double.toString(max));

        DefaultPoolFactory poolFactory = new DefaultPoolFactory();
        GenePool<Solution> genePool = poolFactory.createPool(40, Solution.class, context);
        Rating rating = new Rating(m,b);
        DefaultHandler handler = new DefaultHandler(rating);

        handler.iterate(genePool, 200_000);

        double val = rating.calcFunction(genePool.getBestSolution());
        System.out.printf("norm: %3.7f", val);

        DoubleVector x = new DoubleFixedVector(genePool.getBestSolution().getX().size());
        x.fill(index -> genePool.getBestSolution().getX().get(index));

        return x;
    }

    private double getAbsMax(DoubleMatrix m, DoubleVector b){
        double max = Double.MIN_VALUE;
        for(int i=0; i<m.getRowCount(); i++){
            for(int j=0; j<m.getColumnCount(); j++){
                if(max < Math.abs(m.get(i,j))){
                    max = Math.abs(m.get(i,j));
                }
            }
            if(max < Math.abs(b.get(i)))
                max = Math.abs(b.get(i));
        }
        return max;
    }
}
