package sample;

import com.darkempire.anji.log.Log;
import com.darkempire.math.operator.matrix.LinearMatrixMathOperator;
import com.darkempire.math.struct.matrix.NumberFixedMatrix;
import com.darkempire.math.struct.matrix.NumberMatrix;
import com.darkempire.math.struct.number.Fraction;

import java.io.FileNotFoundException;

import static com.darkempire.math.operator.matrix.DoubleMatrixTransformOperator.makeDiagonalForm;
import static com.darkempire.math.operator.matrix.LinearMatrixTransformOperator.makeDiagonalForm;

/**
 * Create in 14:14
 * Created by siredvin on 23.06.14.
 */
public class Main3 {

    public static void main(String[] args) throws FileNotFoundException {
        /*DoubleMatrix m = DoubleFixedMatrix.createInstance(3, 3, new double[]{1, 2, 3, 6, 4, 0, 7, 0, -1});
        Log.log(Log.debugIndex, m);
        Log.log(Log.debugIndex,"Діагональна матриця:\n", makeDiagonalForm(m.clone()));
        Log.log(Log.debugIndex,"Обернена матриця:\n", DoubleMatrixMathOperator.calcInverseMatrix(m));*/
        NumberMatrix<Fraction> m1 = NumberFixedMatrix.createInstance(3, 3, new Fraction[]{
                new Fraction(1), new Fraction(2), new Fraction(3),
                new Fraction(6), new Fraction(4), new Fraction(0),
                new Fraction(7), new Fraction(0), new Fraction(-1)
        });
        //Log.log(Log.debugIndex, "Діагональна матриця:\n",makeDiagonalForm(m1.clone()));
        Log.log(Log.debugIndex, "Обернена матриця:\n", LinearMatrixMathOperator.calcInverseMatrix(m1));
        NumberMatrix<Fraction> m2 = NumberFixedMatrix.createInstance(3, 3, new Fraction[]{
                new Fraction(1, 19), new Fraction(-1, 38), new Fraction(3, 19),
                new Fraction(-3, 38), new Fraction(11, 38), new Fraction(-9, 38),
                new Fraction(7, 19), new Fraction(-7, 38), new Fraction(2, 19)});
        Log.log(Log.debugIndex, "Має бути така обернена матриця:\n", m2);
        Log.log(Log.debugIndex, "Перевірочка:\n", m1.multy(m2));
        Log.log(Log.debugIndex, "Перевірочка №2:\n", m1.equals(m2) ? "Все рахується правильно" : "Все лажа");
    }
}