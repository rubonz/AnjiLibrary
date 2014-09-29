package com.darkempire.math.solver;

import com.darkempire.anji.log.Log;
import com.darkempire.anji.document.tex.TeXDocumentManager;
import com.darkempire.anji.document.tex.TeXEventWriter;
import com.darkempire.anji.document.tex.TeXFlowObjectManager;
import com.darkempire.math.operator.matrix.LinearMatrixOperator;
import com.darkempire.math.struct.SignType;
import com.darkempire.math.struct.SimpleLinearOptimizeTask;
import com.darkempire.math.struct.function.LinearMultiPolynomial;
import com.darkempire.math.struct.logic.SimpleLinearBoundConditional;
import com.darkempire.math.struct.matrix.*;
import com.darkempire.math.struct.number.Fraction;
import com.darkempire.math.struct.number.FractionWithInfinity;
import com.darkempire.math.struct.vector.*;

import java.util.*;

/**
 * Created by siredvin on 02.07.14.
 */
public class TableSimplexSolver {
    private TeXEventWriter out;
    private TeXFlowObjectManager flowManager;
    private boolean enableLog;
    private boolean enableTeX;

    public boolean isEnableLog() {
        return enableLog;
    }

    public void setEnableLog(boolean enableLog) {
        this.enableLog = enableLog;
    }

    public boolean isEnableTeX() {
        return enableTeX;
    }

    public void setEnableTeX(boolean enableTeX) {
        this.enableTeX = enableTeX;
    }

    public TableSimplexSolver(TeXDocumentManager manager) {
        if (manager != null) {
            out = manager.getEventWriter();
            flowManager = manager.getFlowObjectManager();
            enableTeX = true;
            enableLog = false;
        } else {
            enableLog = true;
            enableTeX = false;
        }
    }

    public NumberVector<Fraction> solve(SimpleLinearOptimizeTask task) {
        //Створення нового завдання
        if (enableLog) {
            Log.log(Log.debugIndex, "Початкове завдання:\n", task);
        }
        if (enableTeX) {
            task.write(out);
            out.text("Приведемо до канонічної форми:");
        }
        //Множина штучних змінних
        BitSet artificialVariablesSet = new BitSet();
        //Множина базисних змінних
        BitSet basisVariables = new BitSet();
        //Список канонічних обмежень
        List<SimpleLinearBoundConditional> conditionalList = new ArrayList<>();
        boolean isMaximize = task.isMaximize();
        int variableCount = task.getConditionalList().stream().mapToInt(SimpleLinearBoundConditional::getSize).max().getAsInt();
        if (enableTeX) {
            out.eqnarray();
            out.text(isMaximize ? "&\\max\\{" : "&\\min\\{");
            task.getTargetFunction().write(out);
            out.append("\\}");
            out.newline();
        }
        //Приведення по одному обмеження до канонічного виду
        for (SimpleLinearBoundConditional conditional : task) {
            SimpleLinearBoundConditional cond = transformToСanonicalFormWith(conditional, variableCount);
            conditionalList.add(cond);
            switch (conditional.getSign()) {
                case GREAT_THEN_OR_EQL:
                    variableCount += 2;
                    artificialVariablesSet.set(variableCount - 1);
                    basisVariables.set(variableCount - 1);
                    break;
                case LOWER_THEN_OR_EQL:
                    variableCount++;
                    basisVariables.set(variableCount - 1);
                    break;
                case EQL:
                    variableCount++;
                    basisVariables.set(variableCount - 1);
                    break;
                case LOWER_THEN:
                case GREAT_THEN:
                case NO_EQL:
                    throw new UnsupportedOperationException();
            }
            if (enableTeX) {
                out.text("&");
                cond.write(out);
                out.newline();
            }
        }
        if (enableTeX) {
            out.closeEnvironment();
            out.text("Складемо симплекс-таблицю:");
        }
        //TODO:продовжити далі
        double[] coefs = task.getTargetFunction().getCoefs();
        coefs = Arrays.copyOf(coefs, variableCount);
        FractionWithInfinity[] tempCoefs = new FractionWithInfinity[coefs.length];
        for (int i = 0; i < coefs.length; i++) {
            if (artificialVariablesSet.get(i)) {
                tempCoefs[i] = new FractionWithInfinity((float) coefs[i], isMaximize ? -1 : 1);
            } else {
                tempCoefs[i] = new FractionWithInfinity((float) coefs[i]);
            }
        }
        NumberVector<FractionWithInfinity> coefVector = new NumberFixedVector<>(tempCoefs);
        int conditionalCount = conditionalList.size();
        //Складаємо симплекс таблицю
        NumberMatrix<FractionWithInfinity> simplexMatrix = NumberFixedMatrix.createInstance(conditionalCount, variableCount + 1, new FractionWithInfinity[conditionalCount * (variableCount + 1)]);
        for (int rowIndex = 0; rowIndex < conditionalCount; rowIndex++) {//Записуємо у таблицю вектор A_0
            simplexMatrix.set(rowIndex, 0, new FractionWithInfinity((float) conditionalList.get(rowIndex).getRightPart()));
        }
        for (int conditionalCounter = 0; conditionalCounter < conditionalCount; conditionalCounter++) {
            SimpleLinearBoundConditional conditional = conditionalList.get(conditionalCounter);
            for (int variableCounter = 0; variableCounter < variableCount; variableCounter++) {
                simplexMatrix.set(conditionalCounter, variableCounter + 1, new FractionWithInfinity((float) conditional.get(variableCounter)));
            }
        }
        if (enableLog) {
            Log.log(Log.debugIndex, simplexMatrix);
        }
        //Визначаємо базові змінні
        IndexVector basisVariableIndex = new IndexFixedVector(conditionalCount);
        int basisCounter = 0;
        for (int i = 0; i < variableCount; i++) {
            if (basisVariables.get(i)) {
                basisVariableIndex.set(basisCounter, i);
                basisCounter++;
            }
        }
        basisVariables.clear();
        if (enableTeX) {
            out.text("Складемо симплект-таблицю:");
            //TODO:TeXTableObject table = new TeXTableObject()
        }
        //Ітерацій симплекс-методу
        byte result = simplexMethodIterate(coefVector, simplexMatrix, basisVariableIndex, variableCount, conditionalCount, isMaximize);
        if (enableLog) {
            Log.log(Log.debugIndex, simplexMatrix);
        }
        while (result == 1) {
            result = simplexMethodIterate(coefVector, simplexMatrix, basisVariableIndex, variableCount, conditionalCount, isMaximize);
        }
        //Кінець інтерацій, обробка результатів
        if (result == 2) {//Якщо розв’язок знайти неможливо
            return null;
        }
        NumberVector<Fraction> resultVector = new NumberFixedVector<Fraction>(new Fraction[variableCount]);
        NumberVector<FractionWithInfinity> resultMatrixVector = simplexMatrix.column(0);
        for (int i = 0; i < conditionalCount; i++) {
            resultVector.set(basisVariableIndex.get(i), resultMatrixVector.get(i).getDoublePart());
        }
        for (int i = 0; i < variableCount; i++) {
            if (resultVector.get(i) == null) {
                resultVector.set(i, new Fraction(0));
            }
        }
        if (enableLog) {
            Log.log(Log.debugIndex, resultVector);
        }
        return resultVector;
    }

    private MatrixIndex findBaseElement(NumberVector<FractionWithInfinity> targetFunction, NumberMatrix<FractionWithInfinity> simplexMatrix, IndexVector basisVariables, int variableCount, int conditionalCount, boolean isMaximize) {
        if (enableLog) {
            Log.log(Log.debugIndex, "Базисні змінні:", basisVariables);
            Log.log(Log.debugIndex, "Починаємо шукати базовий елемент");
        }
        NumberVector<FractionWithInfinity> estimationVector = new NumberFixedVector<>(new FractionWithInfinity[variableCount]);
        NumberVector<FractionWithInfinity> coefVector = new NumberFixedVector<>(new FractionWithInfinity[conditionalCount]);
        //Ініціалізуємо вектор коефіцієнтів для базових змінних
        for (int i = 0; i < conditionalCount; i++) {
            coefVector.set(i, targetFunction.get(basisVariables.get(i)));
        }
        if (enableLog) {
            Log.log(Log.debugIndex, "Вектор коефіцієнтів:", coefVector);
        }
        //Обчислюємо оцінки стовбців
        for (int i = 0; i < variableCount; i++) {

            estimationVector.set(i, coefVector.scalar(simplexMatrix.column(i + 1)).isubtract(targetFunction.get(i)));
        }
        if (enableLog) {
            Log.log(Log.debugIndex, "Вектор оцінок стовбчиків:", estimationVector);
        }
        int columnIndex = 0;
        if (isMaximize) {
            //Знаходимо мінімальний елемент і, відповідно, спрямовуючий стовпчик у випадку, якщо максимізуємо
            for (int i = 0; i < variableCount; i++) {
                FractionWithInfinity temp = estimationVector.get(i);
                if (temp.isLessThenZero()) {
                    if (temp.compareTo(estimationVector.get(columnIndex)) < 0) {
                        columnIndex = i;
                    }
                }
            }
        } else {
            //Знаходимо максимальний елемент i, відповідно, спрямовуючий стовпчик у випадку, якщо мінімізуємо
            for (int i = 0; i < variableCount; i++) {
                FractionWithInfinity temp = estimationVector.get(i);
                if (temp.isGreatThenZero()) {
                    if (temp.compareTo(estimationVector.get(columnIndex)) > 0) {
                        columnIndex = i;
                    }
                }
            }
        }
        if (enableLog) {
            Log.log(Log.debugIndex, "Потрібний нам стовбчик має номер ", columnIndex);
        }
        if (columnIndex == 0) {
            FractionWithInfinity temp = estimationVector.get(columnIndex);
            if (temp.getM().getNumerator() == 0) {
                double tempd = temp.getDoublePart().doubleValue();
                if (isMaximize ? tempd >= 0 : tempd <= 0) {
                    Log.log(Log.debugIndex, "Ми досягли оптимальності");
                    return new MatrixIndex(0, -1);
                }
            }
        }
        //Побудуємо вектор оцінок для рядків
        NumberVector<FractionWithInfinity> rowEstimationVector = new NumberFixedVector<FractionWithInfinity>(new FractionWithInfinity[conditionalCount]);
        for (int i = 0; i < conditionalCount; i++) {
            FractionWithInfinity temp = simplexMatrix.get(i, columnIndex + 1);
            if (temp.getDoublePart().getNumerator() > 0 || temp.getM().getNumerator() > 0) {
                rowEstimationVector.set(i, simplexMatrix.get(i, 0).divide(temp));
                //oLog.log(Log.debugIndex,"Ділимо ", simplexMatrix.get(i,0)," на ", temp, " отримуємо ", rowEstimationVector.get(i));
            } else {
                rowEstimationVector.set(i, new FractionWithInfinity(-1));
            }
        }
        if (enableLog) {
            Log.log(Log.debugIndex, "Вектор оцінки для рядків:", rowEstimationVector);
        }
        //Знаходимо мінімальне відношення
        int rowIndex = 0;
        for (int i = 0; i < conditionalCount; i++) {
            FractionWithInfinity temp = rowEstimationVector.get(i);
            if ((temp.getDoublePart().getNumerator() != -1)) {
                if (rowEstimationVector.get(rowIndex).getDoublePart().getNumerator() != -1) {
                    if (temp.compareTo(rowEstimationVector.get(rowIndex)) < 0) {
                        rowIndex = i;
                    }
                } else {
                    rowIndex = i;
                }
            }
        }
        if (rowIndex == 0) {
            if (rowEstimationVector.get(rowIndex).getDoublePart().getNumerator() <= 0 && simplexMatrix.get(rowIndex, 0).getDoublePart().getNumerator() <= 0) {
                rowIndex = -1;
            }
        }
        if (enableLog) {
            Log.log(Log.debugIndex, "Потрібний нам рядом має номер ", rowIndex);
        }
        return new MatrixIndex(rowIndex, columnIndex);
    }

    /**
     * @param targetFunction
     * @param simplexMatrix
     * @param basisVariables
     * @param variableCount
     * @param conditionalCount
     * @param isMaximize
     * @return Результат ітерації: 0 - знайдений оптимальний розв’язок, 1 - потрібно продовжувати, 2 - розв’язок знайти неможливо
     */
    private byte simplexMethodIterate(NumberVector<FractionWithInfinity> targetFunction, NumberMatrix<FractionWithInfinity> simplexMatrix, IndexVector basisVariables, int variableCount, int conditionalCount, boolean isMaximize) {
        MatrixIndex newIndex = findBaseElement(targetFunction, simplexMatrix, basisVariables, variableCount, conditionalCount, isMaximize);
        if (newIndex.getColumnIndex() == -1) {
            return 0;
        }
        if (newIndex.getRowIndex() == -1) {
            return 2;
        }
        if (enableLog) {
            Log.log(Log.debugIndex, "Робимо ітерацію методом Гауса");
        }
        LinearMatrixOperator.makeBaseColumn(simplexMatrix, newIndex.getRowIndex(), newIndex.getColumnIndex() + 1);
        if (enableLog) {
            Log.log(Log.debugIndex, simplexMatrix);
        }
        basisVariables.set(newIndex.getRowIndex(), newIndex.getColumnIndex());
        if (enableLog) {
            Log.log(Log.debugIndex, "Базисні змінні:", basisVariables);
        }
        return 1;
    }

    private SimpleLinearBoundConditional transformToСanonicalFormWith(SimpleLinearBoundConditional conditional, int systemSize) {
        double[] coefs = null;
        switch (conditional.getSign()) {
            case GREAT_THEN_OR_EQL:
                coefs = Arrays.copyOf(conditional.getCoefs(), systemSize + 2);
                coefs[systemSize] = -1;
                coefs[systemSize + 1] = 1;
                break;
            case LOWER_THEN_OR_EQL:
            case EQL:
                //Різниці між цими параметрами немає на рівні обмеження
                //Вона виникає вже на рівні обробки, коли у випадку рівності, ця змінна
                //записується до критерію з відповідним коефіцієнтом
                coefs = Arrays.copyOf(conditional.getCoefs(), systemSize + 1);
                coefs[systemSize] = 1;
                break;
            case GREAT_THEN:
            case NO_EQL:
            case LOWER_THEN:
                throw new UnsupportedOperationException();
        }
        return new SimpleLinearBoundConditional(new LinearMultiPolynomial(coefs), SignType.EQL, conditional.getRightPart());
    }
}
