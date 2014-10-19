package sample;

import com.darkempire.math.struct.matrix.DoubleFixedMatrix;
import com.darkempire.math.struct.matrix.DoubleMatrix;
import com.darkempire.math.struct.vector.DoubleFixedVector;
import com.darkempire.math.struct.vector.DoubleVector;

/**
 * Created by Сергій on 18.10.2014.
 */
public class Main4 {

    public static void main(String[] args) {
       /*int rows = 10, cols = 10;
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        DoubleMatrix m = DoubleFixedMatrix.createInstance(rows,cols);
        final DoubleRandom r = new DoubleRandom(random);
        m.fill((i1,i2) -> r.getRandomNumber(-0.8,0.8));
        DoubleVector b = new DoubleFixedVector(rows);
        b.fill(index -> r.getRandomNumber(-0.5,0.5));
        */
        /*DoubleMatrix m = DoubleFixedMatrix.createInstance(3, 6, new double[]{
                2, -3, -1, 0.1, 4, 3,
                -3, 5, 2, -0.1, 2, -5,
                -1, 2, 1, 1, 5, 0
        });
        DoubleVector b = new DoubleFixedVector(-7, 19, 12);

        GeneticLinearEquationSystemSolver solver = new GeneticLinearEquationSystemSolver();
        DoubleVector x = solver.solve(m,b);*/

        /*for(double p_crossing = 0.3; p_crossing <= 0.5 ; p_crossing += 0.1){
            System.out.printf("============= %3.3f ==============%n", p_crossing);
            for(double p_mutation = 0.007; p_mutation < 0.1; p_mutation += 0.01  ) {
                random.setSeed(System.currentTimeMillis());
                RandomLinearSystemSolver solver = new RandomLinearSystemSolver(random);
                solver.setProbabilities(p_crossing, p_mutation);
                DoubleVector x = solver.solve(m, b);
//                System.out.println("crossing: " + p_crossing + " mutation: " + p_mutation +
//                        " final norm: " + m.multy(x).subtract(b).norm());
                System.out.printf("crossing: %3.3f mutation: %3.3f final norm: %3.8f %n", p_crossing, p_mutation, m.multy(x).subtract(b).norm());

            }
        }*/

    }
}
