package com.darkempire.math.solver;

import com.darkempire.math.random.randomgenerator.DoubleRandom;
import com.darkempire.math.struct.matrix.DoubleMatrix;
import com.darkempire.math.struct.vector.DoubleFixedVector;
import com.darkempire.math.struct.vector.DoubleVector;

import java.util.*;

/**
 * Created by Сергій on 17.10.2014.
 */
public class RandomLinearSystemSolver implements ILinearEquationSystemSolver {
    private static final double EPSILON = 0.1;
    private static final long MAX_ITERATION = 100_000;

    private static final int POPULATION_SIZE = 26;
    private static final int TOURNAMENT_SIZE = 2;
    private static final int CROSSING_COUNT = 1;
    private static final double CROSSING_PROBABILITY = 0.5;// to 0.3
    private static final double MUTATION_PROBABILITY = 0.03; //0.0075

    private DoubleVector fitnessVector;
    private Random random;

    private DoubleMatrix m;
    private DoubleVector b;

    private double max_m_value;
    private double min_m_value;

    Set<Integer> crossing_point_set;

    public RandomLinearSystemSolver(Random random){
        this.random = random;
        crossing_point_set = new HashSet<>();
    }

    private DoubleVector calculateFitnessVector(List<DoubleVector> populations){
        DoubleVector fitness = new DoubleFixedVector(POPULATION_SIZE);

        for(int k=0; k<fitness.getSize(); k++){
            DoubleVector vector = m.multy(populations.get(k)).subtract(b);
            double x = 0;
            for(int i=0; i<vector.getSize(); i++){
                x += Math.abs(vector.get(i));
            }
            fitness.set(k, x);
        }
        return fitness;
    }

    @Override
    public DoubleVector solve(DoubleMatrix m, DoubleVector b) {
        this.m = m;
        this.b = b;
        findMatrixMinMax();
        DoubleVector x_result = new DoubleFixedVector(m.getColumnCount());
        boolean got_result = false;

        List<DoubleVector> population = initPopulation();
        fitnessVector = calculateFitnessVector(population);
        for(int i=0; i<MAX_ITERATION; i++) {
            population = selectionAndCrossing(population);
            population = mutation(population);
            fitnessVector = calculateFitnessVector(population);
            for(int j=0; j<fitnessVector.getSize(); j++){
                if(fitnessVector.get(j)<EPSILON){
                    got_result = true;
                    x_result = population.get(j);
                }
            }
            if(got_result)
                return x_result;
            if(i%10_000==0) {
                double min = Double.MAX_VALUE;
                for(int j=0; j<POPULATION_SIZE; j++){
                    if(min > fitnessVector.get(j)){
                        min = fitnessVector.get(j);
                    }
                }
               // Log.log(Log.logIndex, "iteration: " + i + " norm: " + min);
            }
        }
        if(!got_result){
            int min_index = -1;
            double min_fitness = Double.MAX_VALUE;
            for(int i=0; i<POPULATION_SIZE; i++){
                if(fitnessVector.get(i)<min_fitness){
                    min_fitness = fitnessVector.get(i);
                    min_index = i;
                }
            }
            //Log.log(Log.logIndex, min_fitness);
            x_result = population.get(min_index).clone();
        }

        return x_result;
    }


    private List<DoubleVector> initPopulation(){
        double findmax = 0;

        for(int i=0; i<m.getRowCount(); i++ ){
            for(int j=0; j<m.getColumnCount(); j++){
                double el = m.getEl(i,j);
                if(Math.abs(el) > findmax) {
                    findmax = el;
                }
            }
            double el = b.get(i);
            if(Math.abs(el)>findmax){
                findmax = el;
            }
        }

        List<DoubleVector> newPopulation = new ArrayList<>();
            DoubleRandom doubleRandom = new DoubleRandom(random);
            for(int i=0; i<POPULATION_SIZE; i++){
                final double finalFindmax = findmax;
                DoubleVector v = new DoubleFixedVector(m.getColumnCount());
                v.fill(index -> doubleRandom.getRandomNumber(-finalFindmax, finalFindmax));
                newPopulation.add(v);
        }

        return newPopulation;
    }



    private List<DoubleVector> selectionAndCrossing(List<DoubleVector> population){
        List<DoubleVector> parentPool = new ArrayList<>(POPULATION_SIZE);

        for(int i=0; i<POPULATION_SIZE; i++){
            int newGroupNumbers[] = random.ints(TOURNAMENT_SIZE, 0, POPULATION_SIZE).toArray();//TOURNAMENT_SIZE
            double best = Double.MAX_VALUE;//Double.MAX_VALUE;
            int best_index = -1;
            for(int num : newGroupNumbers){ //todo: ищем наилучший(минимальный??) элемент в группе
                if(fitnessVector.get(num)<best){
                    best = fitnessVector.get(num);
                    best_index = num;
                }
            }
            parentPool.add(population.get(best_index));
        }
        List<DoubleVector> newPopulation = new ArrayList<>();
        final int crossing_counter = parentPool.size()/2; //?????? 2
        for(int i=0; i<crossing_counter; i++){
            int currentCrossingGroupIndexes[] = random.ints(TOURNAMENT_SIZE, 0, parentPool.size()).toArray();
            double cross_probability = random.nextDouble();
            if(cross_probability < CROSSING_PROBABILITY){
                //todo: abstract crossing with TOURNAMENT_SIZE
                int genes_count = parentPool.get(0).getSize();
                DoubleVector first_parent = parentPool.get(currentCrossingGroupIndexes[0]);
                DoubleVector second_parent = parentPool.get(currentCrossingGroupIndexes[1]);
                DoubleVector first = new DoubleFixedVector(genes_count);
                DoubleVector second = new DoubleFixedVector(genes_count);
                int crossing_point = random.nextInt(genes_count - 1 ) + 1;  //warning!!!!!!!!!!!!!

                crossing_point_set.add(crossing_point);

                /*first.fillSubvector(0, crossing_point, index -> first_parent.get(index));
                second.fillSubvector(0, crossing_point, index -> second_parent.get(index));
                first.fillSubvector(crossing_point, genes_count-1, index ->second_parent.get(index));
                second.fillSubvector(crossing_point, genes_count-1, index -> first_parent.get(index));*/

                for(int j=0; j<crossing_point; j++){
                    first.set(j,first_parent.get(j));
                    second.set(j,second_parent.get(j));
                }
                for (int j=crossing_point; j<genes_count; j++){
                    first.set(j,second_parent.get(j));
                    second.set(j,first_parent.get(j));
                }

                newPopulation.add(first);
                newPopulation.add(second);
            }else{
                for(int num : currentCrossingGroupIndexes){
                    newPopulation.add(parentPool.get(num));
                }
            }
        }
        for(int i=newPopulation.size(); i<POPULATION_SIZE; i++){
            newPopulation.add(parentPool.get(random.nextInt(POPULATION_SIZE)).clone());
        }

        return newPopulation;
    }

    private List<DoubleVector> mutation(List<DoubleVector> population) {
        boolean muted = false;
        for(DoubleVector v : population){
            if(random.nextDouble()<=MUTATION_PROBABILITY){//
                DoubleRandom doubleRandom = new DoubleRandom(random);
                final double finalMax = max_m_value;
                final double finalMin = min_m_value;
                v.fill(index -> doubleRandom.getRandomNumber(finalMin, finalMax));
                muted = true;
            }
        }

        return population;
    }

    private void findMatrixMinMax(){
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;

        for(int i=0; i<m.getRowCount(); i++){
            for(int j=0; j<m.getColumnCount(); j++){
                double x =  m.get(i,j);
                if(min > x){
                    min = x;
                }
                if(max < x){
                    max = x;
                }
            }
        }
        max_m_value = max;
        min_m_value = min;
       // Log.log(Log.logIndex, "min: " + min + " max: "  + max);
    }


}
