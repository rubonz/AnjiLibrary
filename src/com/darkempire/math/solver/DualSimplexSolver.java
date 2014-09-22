package com.darkempire.math.solver;

import com.darkempire.anji.document.tex.TeXDocumentManager;
import com.darkempire.math.struct.SimpleLinearOptimizeTask;
import com.darkempire.math.struct.number.Fraction;
import com.darkempire.math.struct.vector.LinearVector;

/**
 * Created by siredvin on 10.07.14.
 */
public class DualSimplexSolver {
    private TeXDocumentManager manager;

    public DualSimplexSolver(TeXDocumentManager manager) {
        this.manager = manager;
    }

    public LinearVector<Fraction> solve(SimpleLinearOptimizeTask task) {
        //TODO:реалізація
        LinearVector<Fraction> result = null;
        return result;
    }

}
