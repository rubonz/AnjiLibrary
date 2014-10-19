package com.darkempire.math.solver;

import com.darkempire.math.solver.geneticsolver.Rating;
import com.darkempire.math.solver.geneticsolver.Solution;
import com.darkempire.math.struct.matrix.DoubleMatrix;
import com.darkempire.math.struct.vector.DoubleFixedVector;
import com.darkempire.math.struct.vector.DoubleVector;
import net.sourceforge.evoj.GenePool;
import net.sourceforge.evoj.core.DefaultPoolFactory;
import net.sourceforge.evoj.handlers.MultithreadedHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Сергій on 19.10.2014.
 */
public class GeneticLinearEquationSystemSolver implements ILinearEquationSystemSolver{

    private final static double EPSILON = 1e-3;
    private final static int POPULATION_SIZE = 26;//40
    private final static int THREADS_NUMBER = 10;

    @Override
    public DoubleVector solve(DoubleMatrix m, DoubleVector b) {

        Map<String, String> context = new HashMap<>();
        context.put("itemsCount", Integer.toString(m.getColumnCount()));
        double max = getAbsMax(m, b);
        context.put("minElement", Double.toString(-max));
        context.put("maxElement", Double.toString(max));

        DefaultPoolFactory poolFactory = new DefaultPoolFactory();
        GenePool<Solution> genePool = poolFactory.createPool(POPULATION_SIZE, Solution.class, context);
        Rating rating = new Rating(m,b);

        MultithreadedHandler handler = new MultithreadedHandler(THREADS_NUMBER, rating);
        try{
            handler.iterate(genePool, o -> rating.calcFunction((Solution)o)<EPSILON , 20_000);
        }
        finally {
            handler.shutdown();
        }


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
