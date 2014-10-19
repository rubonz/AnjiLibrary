package com.darkempire.math.solver;

import com.darkempire.anji.log.Log;
import com.darkempire.math.random.randomgenerator.DoubleRandom;
import com.darkempire.math.struct.matrix.DoubleMatrix;
import com.darkempire.math.struct.vector.DoubleFixedVector;
import com.darkempire.math.struct.vector.DoubleVector;

import java.util.*;

/**
 * Created by Сергій on 17.10.2014.
 */
public class RandomLinearSystemSolver implements ILinearEquationSystemSolver {
    private static final double EPSILON = 0.0001;
    private static final long MAX_ITERATION = 2_000;

    private static final int POPULATION_SIZE = 32;
    private static final int TOURNAMENT_SIZE = 2;
    private static final int CROSSING_COUNT = 1;
    private  double CROSSING_PROBABILITY = 0.4;// to 0.3
    private  double MUTATION_PROBABILITY = 0.008; //0.0075

    public void setProbabilities(double crossing, double mut){
        CROSSING_PROBABILITY = crossing;
        MUTATION_PROBABILITY = mut;
    }

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
//            double x = 0;
//            for(int i=0; i<vector.getSize(); i++){
//                x += Math.abs(vector.get(i));
//            }
//            fitness.set(k, x);
            fitness.set(k, vector.norm());
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
            population = selectionAndCrossingByRange(population);//selectionAndCrossing(population);
            population = mutation(population);
            Collections.sort(population, new DoubleVectorComparator());
            fitnessVector = calculateFitnessVector(population);
            if(fitnessVector.get(0)<EPSILON){
                got_result = true;
                x_result = population.get(0);
            }
            /*for(int j=0; j<fitnessVector.getSize(); j++){
                if(fitnessVector.get(j)<EPSILON){
                    got_result = true;
                    x_result = population.get(j);
                }
            }*/
            if(got_result) {
                Log.log(Log.logIndex, "Less Iterations: "+ x_result.toString());
                return x_result;
            }
//            if(i%10_000==0) {
//                double min = Double.MAX_VALUE;
//                for(int j=0; j<POPULATION_SIZE; j++){
//                    if(min > fitnessVector.get(j)){
//                        min = fitnessVector.get(j);
//                    }
//                }
//               // Log.log(Log.logIndex, "iteration: " + i + " norm: " + min);
//            }
        }
        if(!got_result){
//            int min_index = -1;
//            double min_fitness = Double.MAX_VALUE;
//            for(int i=0; i<POPULATION_SIZE; i++){
//                if(fitnessVector.get(i)<min_fitness){
//                    min_fitness = fitnessVector.get(i);
//                    min_index = i;
//                }
//            }
//            //Log.log(Log.logIndex, min_fitness);
            x_result = population.get(0).clone();
        }

        return x_result;
    }

    private List<DoubleVector> initPopulation(){
        double findmax = max_m_value;

        for(int i=0; i<m.getRowCount(); i++ ){
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
                DoubleVector first_parent = parentPool.get(currentCrossingGroupIndexes[0]);
                DoubleVector second_parent = parentPool.get(currentCrossingGroupIndexes[1]);

                List<DoubleVector> list = crossing(first_parent,second_parent);
                for(DoubleVector v : list){
                    newPopulation.add(v);
                }
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

    private List<DoubleVector> selectionAndCrossingByRange(List<DoubleVector> population){
        Collections.sort(population, new DoubleVectorComparator());

        DoubleVector best = population.get(0);
        List<DoubleVector> newPopulation = new ArrayList<>();
        for(int i=1; i<population.size()/3; i++){
            newPopulation.add(population.get(i));
            List<DoubleVector> list = crossing(best, population.get(i));
            //newPopulation.add(m.multy(list.get(0)).subtract(b).norm()<m.multy(list.get(1)).subtract(b).norm() ? list.get(0): list.get(1));

            for(DoubleVector v : list){
                newPopulation.add(v);
            }
            //population.set(population.size()/2+i, crossing2(best, population.get(i)));

        }
        for(int i=newPopulation.size(); i<POPULATION_SIZE; i = newPopulation.size()){//warning
            newPopulation.add(best);
        }

        return newPopulation;
    }

    /*private DoubleVector crossing2(DoubleVector parent1, DoubleVector parent2){
        int genes_count = parent1.getSize();

        DoubleVector child1 = new DoubleFixedVector(genes_count);

        final double r = m.multy(parent1).subtract(b).norm();
        final double k = 1.5;
        final double n2 = 1, n1 = 2, n0 = 1e-6;

        child1.fill(j -> parent1.get(j) + k*(parent1.get(j)-parent2.get(j)) +
                (random.nextDouble()-0.5)*(n2*r*r + n1*r + n0));

        return child1;
    }*/

    private List<DoubleVector> crossing(DoubleVector parent1, DoubleVector parent2){
        int genes_count = parent1.getSize();
        int crossing_point = random.nextInt(genes_count - 1 ) + 1;  //warning!!!!!!!!!!!!!

        DoubleVector child1 = new DoubleFixedVector(genes_count);
        DoubleVector child2 = new DoubleFixedVector(genes_count);
        for(int i=0; i<crossing_point; i++){
            child1.set(i,parent1.get(i));
            child2.set(i,parent2.get(i));
        }
        for(int i=crossing_point; i<genes_count; i++){
            child1.set(i,parent2.get(i));
            child2.set(i,parent1.get(i));
        }


        List <DoubleVector> list = new ArrayList<>();
        list.add(child1);
        list.add(child2);
        return list;
    }

    private List<DoubleVector> mutation(List<DoubleVector> population) {
        for(DoubleVector v : population){
            for(int i=0; i<v.getSize(); i++) {
                if (random.nextDouble() <= MUTATION_PROBABILITY) {
                    DoubleRandom doubleRandom = new DoubleRandom(random);

                    v.set(i,doubleRandom.getRandomNumber(min_m_value,max_m_value));
                }
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

    private class DoubleVectorComparator implements Comparator<DoubleVector>{
        @Override
        public int compare(DoubleVector o1, DoubleVector o2) {
            double n1 = m.multy(o1).subtract(b).norm();
            double n2 = m.multy(o2).subtract(b).norm();

            return n1 == n2 ? 0 : (n1<n2 ? -1: 1);
        }
    }

}
