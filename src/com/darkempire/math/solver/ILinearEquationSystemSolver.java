package com.darkempire.math.solver;

import com.darkempire.anji.annotation.AnjiExperimental;
import com.darkempire.math.struct.matrix.DoubleMatrix;
import com.darkempire.math.struct.vector.DoubleVector;

/**
 * Created by siredvin on 16.10.14.
 *
 * @author siredvin
 */
@FunctionalInterface
@AnjiExperimental
public interface ILinearEquationSystemSolver {
    /**
     * Розв’язує систему виду:
     * m.x = b
     *
     * @param m - матриця системи
     * @param b - стовпець вільних членів
     * @return деякий розв’язок або псевдорозв’язок системи
     */
    public DoubleVector solve(DoubleMatrix m, DoubleVector b);
}
