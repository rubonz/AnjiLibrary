package com.darkempire.math.utils;

import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.math.MathArgumentException;
import com.darkempire.math.struct.function.FDoubleDouble;
import com.darkempire.math.struct.matrix.DoubleFixedMatrix;

/**
 * Create in 14:15
 * Created by siredvin on 21.12.13.
 */
@AnjiUtil
public final class SolveUtils {
    private SolveUtils() {
    }

    /**
     * Знаходить приблизний розв’язок параболічного рівняння методом сіток з прямою формулою.
     * Умова стікості: tau<=h*h/(2*a*a)
     * Рівняння має вид: u_t = a*a*u_xx -q*u
     * А початкові умови: u(t,startx)=u(t,endx)=0;u(0,t)=xFunction
     *
     * @param h         крок за x
     * @param tau       крок за t
     * @param startx    початкова координата по
     * @param endx      кінцева координата по
     * @param startt    початкова коориданат по
     * @param endt      кінцева координата по
     * @param xFunction крайова умова у нульовий момент часу
     * @param q         коефіцієнт при вільному члені рівняння
     * @param a         коефіцієнт при другій похідній
     * @return матрицю значень
     * @throws com.darkempire.math.MathArgumentException
     */
    public static DoubleFixedMatrix solveSimpleParabolic(double q, double a, double h, double tau, double startx, double endx, double startt, double endt, FDoubleDouble xFunction) throws MathArgumentException {
        if (tau > h * h / (2 * a * a)) {
            throw new MathArgumentException("Для цього методу не виконується умова стікості. Це сумно, це дуже засмучує метод!");
        }
        int xCount = (int) ((endx - startx) / h + 1);
        double[] x = new double[xCount];
        ArrayUtils.fill(x, i -> h * i);
        int tCount = (int) ((endt - startt) / tau + 1);
        double[] t = new double[tCount];
        ArrayUtils.fill(t, i -> i * tau);
        DoubleFixedMatrix doubleMatrix = DoubleFixedMatrix.createInstance(xCount, tCount);
        doubleMatrix.fillLastColumn(0).fillFirstColumn(0).fillFirstRow((matrix, columnIndex, rowIndex) -> xFunction.calc(x[columnIndex]));
        doubleMatrix.fillRectangle(1, 1, xCount - 2, tCount - 1, (m, columnIndex, rowIndex) -> a * a * tau * (m.get(columnIndex + 1, rowIndex - 1) - 2 * m.get(columnIndex, rowIndex - 1) + m.get(columnIndex - 1, rowIndex - 1)) / (h * h) + m.get(columnIndex, rowIndex) * (1 - q));
        return doubleMatrix;
    }
}
