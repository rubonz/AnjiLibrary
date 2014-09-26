package sample;

import com.darkempire.anji.document.tex.TeXDocumentManager;
import com.darkempire.anji.document.tex.TeXEventWriter;
import com.darkempire.anji.document.tex.TeXTableObject;
import com.darkempire.anji.document.tex.util.TeXTableUtil;
import com.darkempire.anji.log.Log;
import com.darkempire.math.struct.set.FuzzyNumberSet;
import com.darkempire.math.struct.set.NumberSet;

/**
 * Create in 13:05
 * Created by siredvin on 28.04.14.
 */
public class Test1 {
    public static void main(String[] args) {
        //DoubleFixedMatrix matrix = DoubleFixedMatrix.createInstance(3, 3, new double[]{-4, 3, 6, 5, -10, 4, -9, -1, 1});
        TeXDocumentManager documentManager = new TeXDocumentManager(System.out);
        Log.removeConsoleLog();
        Log.setHidePrefix(Log.debugIndex, true);
        Log.log(Log.debugIndex, "start");
        Test1 test = new Test1();
        /*test.task1(new FuzzyNumberSet(new double[]{1, 0.9, 0.3, 0.9, 0.1, 0.1, 0.4, 0.1, 0.8, 0.4, 0.5, 0}),
                new FuzzyNumberSet(new double[]{0.2, 0.6, 0.2, 0.6, 0.7, 0, 0.3, 0.4, 0.6, 0.9, 0.7, 0.6}), documentManager.getEventWriter());*/
        test.task3(new FuzzyNumberSet(0.2, 0.3, 0.7, 0.4, 0.6, 0.9, 0.2, 0.1, 0.9, 0.3),
                new FuzzyNumberSet(0.3, 0.3, 0.6, 0, 0.5, 0.2, 0.4, 0.7, 0.3, 0.3),
                new FuzzyNumberSet(0.8, 0, 0.1, 0.2, 0.9, 0.8, 0.7, 0.9, 0.2, 0.1),
                documentManager.getEventWriter(), 0.4, 0.1, 0.5, 0.4);
    }

    public void task1(FuzzyNumberSet setA, FuzzyNumberSet setB, TeXEventWriter out) {
        out.center();
        out.text("Завдання №1");
        out.closeEnvironment();
        out.text("Задані нечіткі множини $A$ та $B$. Необхідно знайти $A\\cup B,A\\cap B,\\overline A,\\overline B,A\\setminus B,A\\overline\\cup B,A\\overline\\cap B, \\cb{A \\cup B}\\overline\\cup B$");
        out.center();
        FuzzyNumberSet unionAB = setA.union(setB);
        FuzzyNumberSet interAB = setA.intersection(setB);
        FuzzyNumberSet notA = setA.complement();
        FuzzyNumberSet notB = setB.complement();
        FuzzyNumberSet setminusAB = setA.setminus(setB);
        FuzzyNumberSet orAB = setA.or(setB);
        FuzzyNumberSet andAb = setA.and(setB);
        FuzzyNumberSet orunionABB = unionAB.or(setB);
        TeXTableObject table = TeXTableUtil.matrix("X", TeXTableUtil.generateHeader("x", setA.getSize()), new String[]{"$A$", "$B$", "$A\\cup B$", "$A\\cap B$", "$\\overline A$", "$\\overline B$", "$A\\setminus B$",
                        "$A\\overline\\cup B$", "$A\\overline\\cap B$", "$\\cb{A\\cup B}\\overline\\cup B$"},
                setA, setB, unionAB, interAB, notA, notB, setminusAB, orAB, andAb, orunionABB);
        table.write(out);
        out.closeEnvironment();
    }

    public void task2(FuzzyNumberSet setA, FuzzyNumberSet setB, TeXEventWriter out, double al2, double... als) {
        out.center();
        out.text("Завдання №2");
        out.closeEnvironment();
        out.text("Задані дві нечіткі множини $A$ та $B$.");
        out.newline();
        out.text("Необхідно знайти множини рівня $A_\\al,B_\\al$, де $\\al = ");
        out.append(als);
        out.append("$ та перевірити, чи виконується формула\n");
        out.openEnvironment("equation*");
        out.text("\\cb{A\\cup B}_\\al = A_\\al \\cup B_\\al,\\cb{A\\cap B}_\\al=A_\\al \\cap B_\\al");
        out.closeEnvironment();
        out.text("Для $\\al=");
        out.append(al2);
        out.append("$");
        out.newline();
        out.text("Для першого завдання заповнимо таблицю:");
        out.center();
        //Перша частина другого завдання
        NumberSet setA1, setA2, setA3, setB1, setB2, setB3;
        setA1 = setA.levelSet(als[0]);
        setA2 = setA.levelSet(als[1]);
        setA3 = setA.levelSet(als[2]);
        setB1 = setB.levelSet(als[0]);
        setB2 = setB.levelSet(als[1]);
        setB3 = setB.levelSet(als[2]);
        TeXTableObject table = TeXTableUtil.matrix("X", TeXTableUtil.generateHeader("x", setA.getSize()),
                new String[]{"$A$", "$B$", "$A_{" + out.getNumberFormat().format(als[0]) + "}$",
                        "$B_{" + out.getNumberFormat().format(als[0]) + "}$",
                        "$A_{" + out.getNumberFormat().format(als[1]) + "}$",
                        "$B_{" + out.getNumberFormat().format(als[1]) + "}$",
                        "$A_{" + out.getNumberFormat().format(als[2]) + "}$",
                        "$B_{" + out.getNumberFormat().format(als[2]) + "}$"},
                setA, setB, setA1, setB1, setA2, setB2, setA3, setB3);
        table.write(out);
        out.text("Для виконання другого завдання спочатку необхідно знайти потрібні множини і після цього знайти їх множини рівня.");
        out.center();
        setA1 = setA.levelSet(al2);
        setB1 = setB.levelSet(al2);
        FuzzyNumberSet unionAB = setA.union(setB), interAB = setA.intersection(setB);
        NumberSet unionAB1 = unionAB.levelSet(al2), interAB1 = interAB.levelSet(al2);
        table = TeXTableUtil.matrix("X", TeXTableUtil.generateHeader("x", setA.getSize()),
                new String[]{
                        "$A$",
                        "$B$",
                        "$A\\cup B$",
                        "$A\\cap B$",
                        "$A_{" + out.getNumberFormat().format(al2) + "}$",
                        "$B_{" + out.getNumberFormat().format(al2) + "}$",
                        "$A_{" + out.getNumberFormat().format(al2) + "}\\cup B_{" + out.getNumberFormat().format(al2) + "}$",
                        "$\\cb{A\\cup B}_{" + out.getNumberFormat().format(al2) + "}$",
                        "$A_{" + out.getNumberFormat().format(al2) + "}\\cap B_{" + out.getNumberFormat().format(al2) + "}$",
                        "$\\cb{A\\cap B}_{" + out.getNumberFormat().format(al2) + "}$"
                },
                setA, setB, unionAB, interAB, setA1, setB1, setA1.union(setB1), unionAB1, setA1.intersection(setB2), interAB1);
        table.write(out);
        out.closeEnvironment();
    }


    public void task3(FuzzyNumberSet setA, FuzzyNumberSet setB, FuzzyNumberSet setC, TeXEventWriter out, double l1, double l2, double l3, double al) {
        out.center().text("Завдання №3").closeEnvironment()
                .text("Задані нечіткі множини $A$,$B$ та $C$. Необхідно знайти множину з такою функцією приналежності:\n")
                .openEnvironment("equation*").text("\\mu_\\la = \\system{1,\\la_A \\mu_A(x)+\\la_B \\mu_B(x) + \\la_C \\mu_C(x)\\geq 1\\\\ \\la_A \\mu_A(x)+\\la_B \\mu_B(x) + \\la_C \\mu_C(x)}")
                .closeEnvironment().text("$\\la_A=").append(l1, "\\la_B=", l2, "\\la_C=", l3, "$").newline()
                .text("Також знайти $\\cb{A\\overline\\cup B\\overline\\cup C}_{").append(al, "},\\cb{A\\overline\\cap B\\overline\\cap C}_{", al, "}$ та перевірити:")
                .openEnvironment("eqnarray*").text("&\\cb{A\\overline\\cup B\\overline\\cup C}_{").append(al, "} \\supseteq \\cb{A_{", al, "}\\cup B_{", al, "}\\cup C_{", al, "}}").newline()
                .text("&\\cb{A\\overline\\cap B\\overline\\cap C}_{").append(al, "} \\subseteq \\cb{A_{", al, "}\\cap B_{", al, "} \\cap C_{", al, "}}").closeEnvironment();
        FuzzyNumberSet lambda = FuzzyNumberSet.create(setA.getSize(), i -> Math.min(l1 * setA.get(i) + l2 * setB.get(i) + l3 * setC.get(i), 1));
        NumberSet setA1 = setA.levelSet(al), setB1 = setB.levelSet(al), setC1 = setC.levelSet(al);
        FuzzyNumberSet setABCunion = FuzzyNumberSet.pUnion(setA, setB, setC), setABCintersection = FuzzyNumberSet.pIntersection(setA, setB, setC);
        out.center();
        TeXTableObject tableObject = TeXTableUtil.rowFlow()
                .row("X", TeXTableUtil.generateHeader("x", setA.getSize()))
                .row("$A$", setA)
                .row("$B$", setB)
                .row("$C$", setC)
                .row("$\\Lambda$", lambda)
                .row("$A_{" + out.getNumberFormat().format(al) + "}$", setA1)
                .row("$B_{" + out.getNumberFormat().format(al) + "}$", setB1)
                .row("$C_{" + out.getNumberFormat().format(al) + "}$", setC1)
                .row("$A\\cup B\\cup C$", setABCunion)
                .row("$A\\cap B\\cap C$", setABCintersection)
                .row("$\\cb{A\\cup B\\cup C}_{" + out.getNumberFormat().format(al) + "}$", setABCunion.levelSet(al))
                .row("$A_{" + out.getNumberFormat().format(al) + "}\\cup B_{" + out.getNumberFormat().format(al) + "}\\cup C_{" + out.getNumberFormat().format(al) + "}$", NumberSet.union(setA1, setB1, setC1))
                .row("$\\cb{A\\cap B\\cap C}_{" + out.getNumberFormat().format(al) + "}$", setABCintersection.levelSet(al))
                .row("$A_{" + out.getNumberFormat().format(al) + "}\\cap B_{" + out.getNumberFormat().format(al) + "}\\cap C_{" + out.getNumberFormat().format(al) + "}$", NumberSet.intersection(setA1, setB1, setC1))
                .compile();
        tableObject.write(out);
        out.closeEnvironment();
    }
/*
    public void task6(FuzzyRelationMatrix matrix, TeXEventWriter out) {
        out.center().text("Завдання №6").closeEnvironment().text("Перевірити, чи є відношення $R$ задане таблицею транзитивним.").center();
        out.matrix(matrix, "X").closeEnvironment().text("Відомо, що відношення називається транзитивним, якщо його композиція є підмножиною $R$.").newline()
                .text("Є три способи композиції відношення $A$ та $B$:").openEnvironment("description")
                .item("Максимінна").append("$\\sup\\limits_{z} \\min\\set{\\mu_A(x,z),\\mu_B(z,y)}$")
                .item("Мінімаксна").append("$\\min\\limits_{z} \\max\\set{\\mu_A(x,z),\\mu_B(z,y)}$")
                .item("Максимультиплікативна").append("$\\sup\\limits_{z} \\set{\\mu_A(x,z)\\cdot \\mu_B(z,y)}$").closeEnvironment()
                .text("Побудуємо таблицю для максимінної композиції:").center();
        FuzzyRelationMatrix minmaxC = matrix.minmaxC(), maxminC = matrix.maxminC(), maxmultC = matrix.maxmultC();
        boolean maxmin = SetUtils.isSubset(maxminC, matrix), minmax = SetUtils.isSubset(minmaxC, matrix), maxmult = SetUtils.isSubset(maxmultC, matrix);
        out.matrix(maxminC, "$R\\ast R$").closeEnvironment();
        if (maxmin) {
            out.text("Отже, відношення транзитивне за максимінною композіцією");
        } else {
            out.text("Отже, якщо використовувати у якості композиції максимінну, то відношення не буде транзитивним.");
        }
        out.newline().text("Побудуємо таблицю для мінімаксної композиції:").center();
        out.matrix(minmaxC, "$R\\circ R$").closeEnvironment();
        if (minmax) {
            out.text("Отже, відношення транзитивне за мінімаксною композіцією");
        } else {
            out.text("Отже, якщо використовувати у якості композиції мінімаксну, то відношення не буде транзитивним.");
        }
        out.newline().text("Побудуємо таблицю для максимультиплікативної композиції").center();
        out.matrix(maxmultC, "$R \\cdot R$").closeEnvironment();
        if (minmax) {
            out.text("Отже, відношення транзитивне за максимультиплікативною композіцією");
        } else {
            out.text("Отже, якщо використовувати у якості композиції максимультиплікативну, то відношення не буде транзитивним.");
        }
    }

    public void task7(FuzzyRelationMatrix A, FuzzyRelationMatrix B, TeXEventWriter out) {
        out.center().text("Завдання №7").closeEnvironment().text("Для нечітких відношень $A$ та $B$ побудувати: $A\\cup B,A\\cap B,\\overline A,\\overline B,A\\setminus B,A^{-1},B^{-1},A \\oplus B$")
                .center();
        out.matrix(A, "A").matrix(B, "B").closeEnvironment().text("Знайдемо об’єднання та перетин. Вони знаходяться за формулами:")
                .openEnvironment("eqnarray*").text("&\\mu_{A\\cup B} (x,y) = \\max\\set{\\mu_A(x,y),\\mu_B(x,y)}")
                .newline().text("&\\mu_{A\\cap B} (x,y) = \\min\\set{\\mu_A(x,y),\\mu_B(x,y)}").closeEnvironment().center();
        out.matrix(A.union(B), "$A\\cup B$").matrix(A.intersection(B), "$A\\cap B$").closeEnvironment().text("Знайдемо доповнення, які знаходяться за формулою:")
                .openEnvironment("equation*").text("\\mu_{\\overline A} (x,y) = 1 - \\mu_A(x,y)").closeEnvironment().center();
        out.matrix(A.negate(), "$\\overline A$").matrix(B.negate(), "$\\overline B$").closeEnvironment()
                .text("Знайдемо $A\\setminus B$ за такою формулою:").openEnvironment("equation*").text("\\mu_{A\\setminus B} (x,y) =\\max\\set{\\mu_A(x,y) -\\mu_B(x,y),0}")
                .closeEnvironment().center();
        out.matrix(A.setminus(B), "$A\\setminus B$").closeEnvironment().text("Знайдемо зворотні відношення до $A$ та $B$ за такою формулою:")
                .openEnvironment("equation*").text("\\mu_{A^{-1}}(x,y) = \\mu_A(y,x)").closeEnvironment().center();
        out.matrix(A.transpose(), "$A^{-1}$").matrix(B.transpose(), "$B^{-1}$").closeEnvironment().text("Знайдемо суму за модулем для $A$ і $B$ за формулою:")
                .openEnvironment("equation*").text("\\mu_{A\\oplus B} (x,y) = \\mu_{\\cb{A\\cap\\overline B} \\cup \\cb{\\overline A \\cap B}}(x,y) = \\max\\set{\\min\\set{\\mu_A(x,y),\\mu_{\\overline B}(x,y)},\\min\\set{\\mu_{\\overline A}(x,y),\\mu_B(x,y)}}")
                .closeEnvironment().center();
        out.matrix(A.xor(B), "$A\\oplus B$").closeEnvironment();
    }

    public void task8(FuzzyRelationMatrix A, FuzzyRelationMatrix B, TeXEventWriter out) {
        out.center().text("Завдання №8").closeEnvironment().text("Задані нечіткі відношення $A$ та $B$. Необхідно знайти між ними максиміну, мінімаксну та максимультиплікативну композицію.")
                .center();
        String[] xRows = out.generateRows("x", A.getRowCount());
        String zColumns = out.generateHeader("z", B.getColumnCount());
        out.matrix(A, "A", out.generateHeader("y", A.getColumnCount()), xRows).
                matrix(B, "B", zColumns, out.generateRows("y", B.getRowCount())).closeEnvironment();
        out.text("Побудуємо таблицю для максимінної композиції за ").openEnvironment("equation*").text("\\mu_{A\\ast B} (x,z) = \\sup\\limits_{y\\in Y} \\min\\set{\\mu_A(x,y),\\mu_B(y,z)}")
                .closeEnvironment().center();
        out.matrix(A.maxminC(B), "$A\\ast B$", zColumns, xRows).closeEnvironment()
                .text("Побудуємо таблицю для мінімаксної композиції за формулою").openEnvironment("equation*")
                .text("\\mu_{A\\circ B} (x,z) = \\min\\limits_{y\\in Y} \\max\\set{\\mu_A(x,y),\\mu_B(y,z)}").closeEnvironment().center();
        out.matrix(A.minmaxC(B), "$A\\circ B$", zColumns, xRows).closeEnvironment()
                .text("Побудуємо таблицю для максимультиплікативної композиції за формулою").openEnvironment("equation*")
                .text("\\mu_{A\\cdot B} (x,z) = \\sup\\limits_{y\\in Y} \\set{\\mu_A(x,y)\\cdot \\mu_B(y,z)}").closeEnvironment().center();
        out.matrix(A.maxmultC(B), "$A \\cdot B$", zColumns, xRows).closeEnvironment();
    }

    public void task9(FuzzyRelationMatrix R, TeXEventWriter out) {
        out.center().text("Завдання №9").closeEnvironment().text("Задане нечітке відношення $R$. Побудувати для нього $R_1^2,R_2^2,R_3^2$ за формулами:")
                .openEnvironment("eqnarray*").text("&R_1^2 = R\\cdot R").newline().text("&R_2^2 = R \\circ R").newline()
                .text("&R_3^2 = R \\ast R").closeEnvironment().text("та перевірити виконання формули:").openEnvironment("equation*")
                .text("R_3^2 \\subseteq R_2^2 \\subseteq R_1^2").closeEnvironment().center();
        out.matrix(R, "$R$").closeEnvironment().text("Побудуємо відношення").center();
        FuzzyRelationMatrix R1 = R.maxminC(), R2 = R.minmaxC(), R3 = R.maxmultC();
        out.matrix(R1, "$R_1^2$").matrix(R2, "$R_2^2$").matrix(R3, "$R_3^2$").closeEnvironment();
        if (SetUtils.isSubset(R2, R3) && SetUtils.isSubset(R1, R2)) {
            out.text("Помітно, що формула виконується");
        } else {
            out.text("Помітно, що формула не виконується");
        }
    }

    public void task10(TeXEventWriter out, FuzzyRelationMatrix... relations) {
        out.center().text("Завдання №10").closeEnvironment().text("Для наступних нечітких відношень переваги, які задані функцією приналежності у вигляді таблиці, знайти відношення строгої переваги, множину недомінованих альтернатив і найбільш недоміновану альтернативу.").newline();
        for (int i = 0; i < relations.length; i++) {
            FuzzyRelationMatrix R = relations[i];
            out.text("Задано таке відношення:").center();
            out.matrix(R, "$R_{" + out.getNumberFormat().format(i) + "}$").closeEnvironment();
            out.text("Знайдемо відношення строгої переваги для цього відношення за формулою:").openEnvironment("equation*")
                    .text("\\mu_{R_S}(x,y) = \\max\\set{\\mu_R(x,y)-\\mu_R(y,x),0}").closeEnvironment().center();
            FuzzyRelationMatrix rs = SetUtils.buildStrong(R);
            out.matrix(rs, "$R_s$").closeEnvironment();
            out.text("Знайдемо множину недомінованих альтернатив за формулою:").openEnvironment("equation*")
                    .text("\\mu_{R^{nd}}(x) = 1 - \\max\\limits_{y\\in X}\\set{\\mu_{R_s}(x,y)}").closeEnvironment().center();
            FuzzyNumberSet rnd = SetUtils.buildNotDominate(rs);
            out.createTabular(rnd.getSize() + 1, true).row("$R^{nd}$", out.generateHeader("x", rnd.getSize()))
                    .row("$\\mu_{R^{nd}}(x)$", rnd).closeTabular();
            out.closeEnvironment();
            int size = rnd.getSize();
            double max = 0;
            for (int ii = 0; ii < size; ii++) {
                double temp = rnd.get(ii);
                if (max < temp)
                    max = temp;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("$");
            for (int ii = 0; ii < size; ii++) {
                if (max == rnd.get(ii)) {
                    stringBuilder.append("x_{");
                    stringBuilder.append(ii + 1);
                    stringBuilder.append("},");
                }
            }
            Util.removeLastChar(stringBuilder);
            stringBuilder.append("$");
            out.text("Список найбільш недомінованих альтернатив:").append(stringBuilder.toString());
        }
    }

    public static void main(String[] args) {
        Test1 test = new Test1();
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(3);
        TeXEventWriter out = new TeXEventWriter(System.out, numberFormat);
        //Перше завдання
        test.task1(FuzzyNumberSet.create(1, 0.9, 0.3, 0.9, 0.1, 0.1, 0.4, 0.1, 0.4, 0.8, 0.4, 0), FuzzyNumberSet.create(0.2, 0.6, 0.2, 0.6, 0.7, 0, 0.3, 0.4, 0.6, 0.9, 0.7, 0.6), out);
        //Друге завдання
        test.task2(FuzzyNumberSet.create(0, 0.5, 0.2, 0.1, 0, 0.9, 0.1, 0.6, 0.5, 0, 0), FuzzyNumberSet.create(1, 0.5, 0.7, 0.4, 0.5, 0.1, 0.7, 0.4, 0.7, 0.9, 0.8), out, 0.1, 0.3, 0.8, 0.9);
        //Третє завдання:
        test.task3(FuzzyNumberSet.create(0, 0.2, 0.3, 0.7, 0.4, 0.6, 0.9, 0.2, 0.1, 0.9, 0.3),
                FuzzyNumberSet.create(1, 0.3, 0.3, 0.6, 0, 0.5, 0.2, 0.4, 0.7, 0.3, 0.3),
                FuzzyNumberSet.create(2, 0.8, 0, 0.1, 0.2, 0.9, 0.8, 0.7, 0.9, 0.2, 0.1), out, 0.4, 0.1, 0.5, 0.4);
        //Шосте завдання
        test.task6(FuzzyRelationMatrix.createInstance(4, 4, new double[]{
                0.9, 0.1, 0.6, 0.5,
                0.4, 0.6, 0.3, 0.3,
                0.1, 0.5, 0.3, 0.9,
                0.4, 0.1, 0.5, 0.1
        }), out);
        //Сьоме завдання
        test.task7(FuzzyRelationMatrix.createInstance(5, 5, new double[]{
                0.2, 0.8, 0.3, 0.5, 0.7,
                0.4, 0.5, 0.9, 0.2, 0.4,
                0.9, 0.2, 0.5, 0.4, 0.7,
                0.5, 0.4, 0.9, 0.6, 0.5,
                0.6, 0.8, 0.6, 0.7, 0.7
        }), FuzzyRelationMatrix.createInstance(5, 5, new double[]{
                0.3, 0.2, 0.7, 0.7, 0.1,
                0.8, 0.5, 0.7, 0.6, 0.9,
                0.3, 0.2, 0.4, 0.4, 0.9,
                0.6, 0.3, 0.2, 0.6, 0.7,
                0.6, 0, 0, 0.6, 0.6
        }), out);
        //Восьме завдання
        test.task8(FuzzyRelationMatrix.createInstance(3, 5, new double[]{
                0.3, 0.8, 0.6, 0.9, 0.4,
                0, 0.6, 0.2, 0.2, 0.4,
                0.4, 0.2, 0.1, 0.2, 0.8,
        }), FuzzyRelationMatrix.createInstance(5, 4, new double[]{
                0.8, 0.5, 0.9, 0,
                0.7, 0.9, 0.9, 0.2,
                0, 0.6, 0.6, 0.2,
                0.9, 0.7, 0.8, 0.6,
                0.7, 0, 0.7, 0
        }), out);
        //Дев’яте завдання:
        test.task9(FuzzyRelationMatrix.createInstance(3, 3, new double[]{
                1, 0.6, 0.2,
                0.9, 0.9, 0.6,
                0.2, 0.6, 0.9,
        }), out);
        //Десяте завдання:
        FuzzyRelationMatrix s10A = FuzzyRelationMatrix.createInstance(5, 5, new double[]{
                1, 0.3, 0.7, 0.7, 0.9,
                0.1, 1, 0.2, 0.8, 0.4,
                0.8, 0.1, 1, 0.9, 0.3,
                0.4, 0.8, 0.7, 1, 0.9,
                0.5, 0.8, 0.4, 0.4, 1

        });
        FuzzyRelationMatrix s10B = FuzzyRelationMatrix.createInstance(5, 5, new double[]{
                1, 0.4, 0.3, 0.3, 0.9,
                0.7, 1, 0.6, 0.1, 0.4,
                0.4, 0.6, 1, 0.9, 0.7,
                0.6, 0.7, 0.7, 1, 0.6,
                0.6, 0.1, 0.7, 0.5, 1

        });
        FuzzyRelationMatrix s10C = FuzzyRelationMatrix.createInstance(6, 6, new double[]{
                1, 0.4, 0.5, 0.9, 0.8, 0.7,
                0.6, 1, 0.7, 0.5, 0.6, 0.5,
                0.1, 0.8, 1, 0.4, 0.4, 0.5,
                0.7, 0.7, 0.5, 1, 0.3, 0.4,
                0.7, 0.3, 0.8, 0.4, 1, 0.1,
                0.6, 0.9, 0.4, 0.2, 0.5, 1

        });
        FuzzyRelationMatrix s10D = FuzzyRelationMatrix.createInstance(6, 6, new double[]{
                1, 0.8, 0.3, 0.5, 0.2, 0.1,
                0.8, 1, 0.3, 0.4, 0.8, 0.5,
                0.7, 0.9, 1, 0.7, 0.3, 0.7,
                0.4, 0.2, 0.9, 1, 0.9, 0.1,
                0.3, 0.7, 0.9, 0.1, 1, 0.2,
                0.8, 0.5, 0.6, 0.4, 0.5, 1

        });
        test.task10(out, s10A, s10B, s10C, s10D);
    }*/
    //TODO:реалізувати на новому TeXApi

}
