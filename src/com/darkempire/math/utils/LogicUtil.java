package com.darkempire.math.utils;

import com.darkempire.anji.annotation.AnjiExperimental;
import com.darkempire.anji.annotation.AnjiRewrite;
import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.math.struct.matrix.BooleanMatrix;

/**
 * Created by siredvin on 05.10.14.
 */
@AnjiUtil
public final class LogicUtil {
    private LogicUtil() {
    }

    public static int countTrue(boolean... values) {
        int counter = 0;
        for (boolean b : values) {
            if (b)
                counter++;
        }
        return counter;
    }

    public static int countFalse(boolean... values) {
        int counter = 0;
        for (boolean b : values) {
            if (!b)
                counter++;
        }
        return counter;
    }

    //TODO:написати Relaition part

    /**
     * Створює на основі матриці відношення матрицю транзитивного замикання цього відношення.
     *
     * @param a матриця відношення
     * @return транзитивне замикання
     */
    @AnjiExperimental
    @AnjiRewrite
    public static BooleanMatrix getCircuit(BooleanMatrix a) {
        BooleanMatrix a1 = a.add(a.multy(a));
        do {
            a = a1;
            a1 = a1.add(a1.multy(a));
        } while (!a.equals(a1));
        return a1;
    }

    /**
     * Змінює матриці відношення переваги та ідентифірентності за аналізом їх зв’язку та даних
     *
     * @param succMatrix матриця відношення переваги
     * @param simMatrix  матриці відношення ідентифірентності
     * @return змінені матриці
     */
    @AnjiRewrite
    public static BooleanMatrix[] deepAnalysisRelation(BooleanMatrix succMatrix, BooleanMatrix simMatrix) {
        BooleanMatrix succ = getCircuit(succMatrix.clone());
        BooleanMatrix sim = getCircuit(simMatrix.clone());
        int size = succ.getRowCount();
        do {
            for (int rowIndex = 0; rowIndex < size; rowIndex++) {
                for (int columnIndex = rowIndex + 1; columnIndex < size; columnIndex++) {
                    if (sim.get(rowIndex, columnIndex)) {
                        for (int succColumnIndex = 0; succColumnIndex < size; succColumnIndex++) {
                            if (succ.get(rowIndex, succColumnIndex)) {
                                succ.set(columnIndex, succColumnIndex, true);
                            }
                            if (succ.get(succColumnIndex, rowIndex)) {
                                succ.set(succColumnIndex, columnIndex, true);
                            }
                            if (succ.get(columnIndex, succColumnIndex)) {
                                succ.set(rowIndex, succColumnIndex, true);
                            }
                            if (succ.get(succColumnIndex, columnIndex)) {
                                succ.set(succColumnIndex, rowIndex, true);
                            }
                        }
                    }
                }
            }
            succMatrix = succ.clone();
            simMatrix = sim.clone();
            getCircuit(succ);
            getCircuit(sim);
        } while (!(succ.equals(succMatrix) || sim.equals(simMatrix)));
        return new BooleanMatrix[]{sim, succ};
    }
}
