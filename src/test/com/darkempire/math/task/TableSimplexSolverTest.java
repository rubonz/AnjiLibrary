package test.com.darkempire.math.task;

import com.darkempire.anji.log.Log;
import com.darkempire.math.struct.SignType;
import com.darkempire.math.struct.SimpleLinearOptimizeTask;
import com.darkempire.math.struct.function.LinearMultiPolynomial;
import com.darkempire.math.struct.logic.SimpleLinearBoundConditional;
import com.darkempire.math.struct.number.Fraction;
import com.darkempire.math.struct.vector.LinearFixedVector;
import com.darkempire.math.struct.vector.LinearVector;
import com.darkempire.math.solver.TableSimplexSolver;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * OperationResearchTasker Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>лип. 3, 2014</pre>
 */
public class TableSimplexSolverTest extends Assert {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: solve(SimpleLinearOptimizeTask task)
     */
    @Test
    public void testSolveSimplexMethod() throws Exception {
        Log.addStream(new PrintStream(new FileOutputStream(new File("log.txt"))));
        Log.setHidePrefix(Log.debugIndex, true);
        TableSimplexSolver tasker = new TableSimplexSolver(null);
        LinearMultiPolynomial targetFunction;
        List<SimpleLinearBoundConditional> conditionalList;
        SimpleLinearOptimizeTask task;
        List<SimpleLinearOptimizeTask> taskList = new ArrayList<>();
        targetFunction = new LinearMultiPolynomial(7, 6);
        conditionalList = new ArrayList<>();
        conditionalList.add(new SimpleLinearBoundConditional(new LinearMultiPolynomial(2, 5), SignType.GREAT_THEN_OR_EQL, 10));
        conditionalList.add(new SimpleLinearBoundConditional(new LinearMultiPolynomial(5, 2), SignType.GREAT_THEN_OR_EQL, 10));
        conditionalList.add(new SimpleLinearBoundConditional(new LinearMultiPolynomial(1, 0), SignType.LOWER_THEN_OR_EQL, 6));
        conditionalList.add(new SimpleLinearBoundConditional(new LinearMultiPolynomial(0, 1), SignType.LOWER_THEN_OR_EQL, 5));
        task = new SimpleLinearOptimizeTask(true, targetFunction, conditionalList);
        taskList.add(task);
        targetFunction = new LinearMultiPolynomial(3, -2);
        conditionalList = new ArrayList<>();
        conditionalList.add(new SimpleLinearBoundConditional(new LinearMultiPolynomial(2, 1), SignType.LOWER_THEN_OR_EQL, 11));
        conditionalList.add(new SimpleLinearBoundConditional(new LinearMultiPolynomial(-3, 2), SignType.LOWER_THEN_OR_EQL, 10));
        conditionalList.add(new SimpleLinearBoundConditional(new LinearMultiPolynomial(3, 4), SignType.GREAT_THEN_OR_EQL, 20));
        task = new SimpleLinearOptimizeTask(true, targetFunction, conditionalList);
        taskList.add(task);
        targetFunction = new LinearMultiPolynomial(5, -3);
        conditionalList = new ArrayList<>();
        conditionalList.add(new SimpleLinearBoundConditional(new LinearMultiPolynomial(3, 2), SignType.GREAT_THEN_OR_EQL, 6));
        conditionalList.add(new SimpleLinearBoundConditional(new LinearMultiPolynomial(2, -3), SignType.GREAT_THEN_OR_EQL, -6));
        conditionalList.add(new SimpleLinearBoundConditional(new LinearMultiPolynomial(1, -1), SignType.LOWER_THEN_OR_EQL, 4));
        conditionalList.add(new SimpleLinearBoundConditional(new LinearMultiPolynomial(4, 7), SignType.LOWER_THEN_OR_EQL, 28));
        task = new SimpleLinearOptimizeTask(true, targetFunction, conditionalList);
        taskList.add(task);
        targetFunction = new LinearMultiPolynomial(1, 2);
        conditionalList = new ArrayList<>();
        conditionalList.add(new SimpleLinearBoundConditional(new LinearMultiPolynomial(2, 1), SignType.LOWER_THEN_OR_EQL, 14));
        conditionalList.add(new SimpleLinearBoundConditional(new LinearMultiPolynomial(-3, 2), SignType.LOWER_THEN_OR_EQL, 9));
        conditionalList.add(new SimpleLinearBoundConditional(new LinearMultiPolynomial(3, 4), SignType.GREAT_THEN_OR_EQL, 27));
        task = new SimpleLinearOptimizeTask(true, targetFunction, conditionalList);
        taskList.add(task);
        targetFunction = new LinearMultiPolynomial(7, -2);
        conditionalList = new ArrayList<>();
        conditionalList.add(new SimpleLinearBoundConditional(new LinearMultiPolynomial(5, -2), SignType.LOWER_THEN_OR_EQL, 3));
        conditionalList.add(new SimpleLinearBoundConditional(new LinearMultiPolynomial(1, 1), SignType.GREAT_THEN_OR_EQL, 1));
        conditionalList.add(new SimpleLinearBoundConditional(new LinearMultiPolynomial(-3, 1), SignType.LOWER_THEN_OR_EQL, 3));
        conditionalList.add(new SimpleLinearBoundConditional(new LinearMultiPolynomial(2, 1), SignType.LOWER_THEN_OR_EQL, 4));
        task = new SimpleLinearOptimizeTask(true, targetFunction, conditionalList);
        taskList.add(task);
        targetFunction = new LinearMultiPolynomial(7, -1);
        conditionalList = new ArrayList<>();
        conditionalList.add(new SimpleLinearBoundConditional(new LinearMultiPolynomial(1, 1), SignType.GREAT_THEN_OR_EQL, 3));
        conditionalList.add(new SimpleLinearBoundConditional(new LinearMultiPolynomial(5, 1), SignType.GREAT_THEN_OR_EQL, 5));
        conditionalList.add(new SimpleLinearBoundConditional(new LinearMultiPolynomial(1, 5), SignType.GREAT_THEN_OR_EQL, 5));
        conditionalList.add(new SimpleLinearBoundConditional(new LinearMultiPolynomial(1, 0), SignType.LOWER_THEN_OR_EQL, 4));
        conditionalList.add(new SimpleLinearBoundConditional(new LinearMultiPolynomial(0, 1), SignType.LOWER_THEN_OR_EQL, 4));
        task = new SimpleLinearOptimizeTask(false, targetFunction, conditionalList);
        taskList.add(task);
        targetFunction = new LinearMultiPolynomial(1, 1);
        conditionalList = new ArrayList<>();
        conditionalList.add(new SimpleLinearBoundConditional(new LinearMultiPolynomial(3, 1), SignType.GREAT_THEN_OR_EQL, 8));
        conditionalList.add(new SimpleLinearBoundConditional(new LinearMultiPolynomial(1, 2), SignType.GREAT_THEN_OR_EQL, 6));
        conditionalList.add(new SimpleLinearBoundConditional(new LinearMultiPolynomial(1, -1), SignType.LOWER_THEN_OR_EQL, 3));
        task = new SimpleLinearOptimizeTask(false, targetFunction, conditionalList);
        taskList.add(task);
        targetFunction = new LinearMultiPolynomial(1, 2, 3, -1);
        conditionalList = new ArrayList<>();
        conditionalList.add(new SimpleLinearBoundConditional(new LinearMultiPolynomial(1, 2, 3), SignType.EQL, 15));
        conditionalList.add(new SimpleLinearBoundConditional(new LinearMultiPolynomial(2, 1, 5), SignType.EQL, 20));
        conditionalList.add(new SimpleLinearBoundConditional(new LinearMultiPolynomial(1, 2, 0, 1), SignType.EQL, 10));
        task = new SimpleLinearOptimizeTask(true, targetFunction, conditionalList);
        taskList.add(task);
        List<LinearVector<Fraction>> answerList = new ArrayList<>();
        answerList.add(new LinearFixedVector<>(new Fraction[]{new Fraction(6), new Fraction(5)}));
        answerList.add(new LinearFixedVector<>(new Fraction[]{new Fraction(24, 5), new Fraction(7, 5)}));
        answerList.add(new LinearFixedVector<>(new Fraction[]{new Fraction(56, 11), new Fraction(12, 11)}));
        answerList.add(new LinearFixedVector<>(new Fraction[]{new Fraction(19, 7), new Fraction(60, 7)}));
        answerList.add(new LinearFixedVector<>(new Fraction[]{new Fraction(11, 9), new Fraction(14, 9)}));
        answerList.add(new LinearFixedVector<>(new Fraction[]{new Fraction(1, 5), new Fraction(4)}));
        answerList.add(new LinearFixedVector<>(new Fraction[]{new Fraction(2), new Fraction(2)}));
        answerList.add(new LinearFixedVector<>(new Fraction[]{new Fraction(0), new Fraction(15, 7), new Fraction(25, 7), new Fraction(0)}));
        int size = answerList.size();
        for (int i = 0; i < size; i++) {
            Log.log(Log.debugIndex, "Виконується тест №", i + 1);
            assertEquals(tasker.solve(taskList.get(i)).subvector(answerList.get(i).getSize()), answerList.get(i));
        }
    }
} 
