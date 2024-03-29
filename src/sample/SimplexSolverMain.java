package sample;

import com.darkempire.anji.document.tex.util.TeXUtil;
import com.darkempire.anji.log.Log;
import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.anji.document.tex.TeXProjectManager;
import com.darkempire.math.solver.TableSimplexSolver;
import com.darkempire.math.struct.SignType;
import com.darkempire.math.struct.SimpleLinearOptimizeTask;
import com.darkempire.math.struct.function.polynomial.LinearMultiDoublePolynomial;
import com.darkempire.math.struct.logic.SimpleLinearBoundConditional;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.Process;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by siredvin on 01.09.14.
 */
@AnjiUtil
public final class SimplexSolverMain {
    private SimplexSolverMain() {
    }

    public static void main(String[] args) {
        Log.initSimpleLog();
        File dir = new File("./TeX");
        TeXProjectManager projectManager = new TeXProjectManager(dir, "main1.tex");
        TeXUtil.initSimpleHeader(projectManager.getMainFileManager().getEventWriter()).openEnvironment("document");
        TableSimplexSolver solver = new TableSimplexSolver(projectManager.getMainFileManager());
        LinearMultiDoublePolynomial targetFunction = new LinearMultiDoublePolynomial(7, 6);
        ArrayList<SimpleLinearBoundConditional> conditionalList = new ArrayList<>();
        conditionalList.add(new SimpleLinearBoundConditional(new LinearMultiDoublePolynomial(2, 5), SignType.GREAT_THEN_OR_EQL, 10));
        conditionalList.add(new SimpleLinearBoundConditional(new LinearMultiDoublePolynomial(5, 2), SignType.GREAT_THEN_OR_EQL, 10));
        conditionalList.add(new SimpleLinearBoundConditional(new LinearMultiDoublePolynomial(1, 0), SignType.LOWER_THEN_OR_EQL, 6));
        conditionalList.add(new SimpleLinearBoundConditional(new LinearMultiDoublePolynomial(0, 1), SignType.LOWER_THEN_OR_EQL, 5));
        solver.solve(new SimpleLinearOptimizeTask(true, targetFunction, conditionalList));
        projectManager.getMainFileManager().getEventWriter().closeEnvironment();
        Process process = null;
        Runtime r = Runtime.getRuntime();
        String cmd = "";
        try {
            process = r.exec(new String[]{"pdflatex", "main1.tex"}, null, dir);
            if (!process.isAlive()) {
                Log.log(Log.debugIndex, "Fail");
            } else {
                Log.log(Log.debugIndex, "Run");
            }
            InputStream os = process.getInputStream();
            Scanner s = new Scanner(os);
            while (s.hasNextLine()) {
                Log.log(Log.debugIndex, s.nextLine());
            }
        } catch (IOException e) {
            Log.err(Log.debugIndex, e);
        }
        if (process != null && process.isAlive()) {
            Log.log(Log.debugIndex, "Run2");
        }
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            Log.err(Log.debugIndex, e);
        }
    }
}
