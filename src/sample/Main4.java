package sample;

import com.darkempire.anji.log.Log;
import com.darkempire.math.solver.RandomLinearSystemSolver;
import com.darkempire.math.struct.matrix.DoubleFixedMatrix;
import com.darkempire.math.struct.matrix.DoubleMatrix;
import com.darkempire.math.struct.vector.DoubleFixedVector;
import com.darkempire.math.struct.vector.DoubleVector;

import java.util.Random;

/**
 * Created by Сергій on 18.10.2014.
 */
public class Main4 {

    public static void main(String[] args) {
        int rows = 50, cols = 40;
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        double arr[] = random.doubles(rows*cols).toArray();
        double b_arr[] = random.doubles(rows).toArray();

        DoubleMatrix m = DoubleFixedMatrix.createInstance(3,6,new double[]{
                2 , -3, -1, 0.1, 4, 3,
                -3, 5,  2, -0.1, 2,-5,
                -1, 2,  1, 1,    5, 0
        });
        DoubleVector b = new DoubleFixedVector(new double[]{
                -7, 19, 12
        });
        /*DoubleMatrix m = DoubleFixedMatrix.createInstance(rows,cols, arr);
        DoubleVector b = new DoubleFixedVector(b_arr);*/


        //DoubleVector x = solver.solve(m,b);
        //Log.log(Log.logIndex, x.toString());
        for(int i=0; i<3; i++){
            RandomLinearSystemSolver solver = new RandomLinearSystemSolver(random);
            DoubleVector x = solver.solve(m,b);
            Log.log(Log.logIndex, "final norm: " + m.multy(x).subtract(b).norm());
        }


    }
}
