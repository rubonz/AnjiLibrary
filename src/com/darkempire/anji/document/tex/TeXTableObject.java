package com.darkempire.anji.document.tex;

import com.darkempire.anji.log.Log;
import com.darkempire.math.MathMachine;
import com.darkempire.math.struct.matrix.*;
import com.darkempire.math.struct.vector.IndexFixedVector;
import com.darkempire.math.struct.vector.IDoubleVectorProvider;
import com.darkempire.math.struct.vector.IVectorProvider;
import com.darkempire.math.struct.vector.IndexVector;

import java.text.NumberFormat;

import static com.darkempire.anji.util.AnjiStringUtil.generateCharString;

/**
 * Created by siredvin on 10.07.14.
 */
public class TeXTableObject implements ITeXObject {
    private NumberFormat numberFormat;
    private Matrix<String> tableMatrix;
    private IndexVector verticalLines;
    private BooleanMatrix horizontalLines;
    private char[] tableVector;

    //region Конструктори
    public TeXTableObject(int rowCount, int columnCount) {
        tableMatrix = Matrix.createInstance(rowCount, columnCount, new String[rowCount * columnCount]);
        verticalLines = new IndexFixedVector(columnCount + 1);
        numberFormat = MathMachine.numberFormat;
        horizontalLines = BooleanFixedMatrix.createInstance(rowCount + 1, columnCount);
        tableVector = new char[columnCount];
        for (int i = 0; i < columnCount; i++) {
            tableVector[i] = 'c';
        }
    }
    //endregion

    //region Геттери
    public Matrix<String> getTableMatrix() {
        return tableMatrix;
    }

    public IndexVector getVerticalLinesVector() {
        return verticalLines;
    }

    public BooleanMatrix getHorizontalLinesMatrix() {
        return horizontalLines;
    }

    public NumberFormat getNumberFormat() {
        return numberFormat;
    }
    //endregion

    //region Сеттери
    public void setColumnPrefix(int index, char prefix) {
        tableVector[index] = prefix;
    }

    public void setNumberFormat(NumberFormat numberFormat) {
        this.numberFormat = numberFormat;
    }
    //endregion

    //region Заповнення рядків

    //region Заповнення рядків масивом рядків
    public TeXTableObject row(int rowIndex, Object[] strings) {
        int columnCount = Math.min(tableMatrix.getColumnCount(), strings.length);
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            tableMatrix.setEl(rowIndex, columnIndex, strings[columnIndex].toString());
        }
        return this;
    }

    public TeXTableObject row(int rowIndex, int columnStart, Object[] strings) {
        int columnCount = Math.min(tableMatrix.getColumnCount() - columnStart, strings.length) + 1;
        for (int columnIndex = columnStart; columnIndex < columnCount; columnIndex++) {
            tableMatrix.setEl(rowIndex, columnIndex, strings[columnIndex - columnStart].toString());
        }
        return this;
    }

    public TeXTableObject row(int rowIndex, int columnStart, int columnEnd, Object[] strings) {
        columnEnd++;
        for (int columnIndex = columnStart; columnIndex < columnEnd; columnIndex++) {
            tableMatrix.setEl(rowIndex, columnIndex, strings[columnIndex - columnStart].toString());
        }
        return this;
    }

    public TeXTableObject row(int rowIndex, String... strings) {
        int columnCount = Math.min(tableMatrix.getColumnCount(), strings.length);
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            tableMatrix.setEl(rowIndex, columnIndex, strings[columnIndex]);
        }
        return this;
    }

    public TeXTableObject row(int rowIndex, int columnStart, String... strings) {
        int columnCount = Math.min(tableMatrix.getColumnCount() - columnStart, strings.length) + 1;
        for (int columnIndex = columnStart; columnIndex < columnCount; columnIndex++) {
            tableMatrix.setEl(rowIndex, columnIndex, strings[columnIndex - columnStart]);
        }
        return this;
    }

    public TeXTableObject row(int rowIndex, int columnStart, int columnEnd, String... strings) {
        columnEnd++;
        for (int columnIndex = columnStart; columnIndex < columnEnd; columnIndex++) {
            tableMatrix.setEl(rowIndex, columnIndex, strings[columnIndex - columnStart]);
        }
        return this;
    }
    //endregion

    //region Заповнення рядків векторами зі строками
    public TeXTableObject row(int rowIndex, IVectorProvider strings) {
        int columnCount = Math.min(tableMatrix.getColumnCount(), strings.getSize());
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            tableMatrix.setEl(rowIndex, columnIndex, strings.get(columnIndex).toString());
        }
        return this;
    }

    public TeXTableObject row(int rowIndex, int columnStart, IVectorProvider set) {
        int columnCount = Math.min(tableMatrix.getColumnCount() - columnStart, set.getSize()) + 1;
        for (int columnIndex = columnStart; columnIndex < columnCount; columnIndex++) {
            tableMatrix.setEl(rowIndex, columnIndex, set.get(columnIndex - columnStart).toString());
        }
        return this;
    }

    public TeXTableObject row(int rowIndex, int columnStart, int columnEnd, IVectorProvider set) {
        columnEnd++;
        for (int columnIndex = columnStart; columnIndex < columnEnd; columnIndex++) {
            tableMatrix.setEl(rowIndex, columnIndex, set.get(columnIndex - columnStart).toString());
        }
        return this;
    }
    //endregion

    //region Заповнення рядків векторами з double елементами

    public TeXTableObject row(int rowIndex, IDoubleVectorProvider set) {
        int columnCount = Math.min(tableMatrix.getColumnCount(), set.getSize());
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            tableMatrix.setEl(rowIndex, columnIndex, numberFormat.format(set.get(columnIndex)));
        }
        return this;
    }

    public TeXTableObject row(int rowIndex, int columnStart, IDoubleVectorProvider set) {
        int columnCount = Math.min(tableMatrix.getColumnCount() - columnStart, set.getSize()) + 1;
        Log.log(Log.debugIndex, set);
        for (int columnIndex = columnStart; columnIndex < columnCount; columnIndex++) {
            tableMatrix.setEl(rowIndex, columnIndex, numberFormat.format(set.get(columnIndex - columnStart)));
        }
        return this;
    }

    public TeXTableObject row(int rowIndex, int columnStart, int columnEnd, IDoubleVectorProvider set) {
        columnEnd++;
        for (int columnIndex = columnStart; columnIndex < columnEnd; columnIndex++) {
            tableMatrix.setEl(rowIndex, columnIndex, numberFormat.format(set.get(columnIndex - columnStart)));
        }
        return this;
    }
    //endregion

    //endregionre

    //region Заповнення стовпчиків

    //region Заповнення стовпчиків масивом рядків
    public TeXTableObject column(int columnIndex, Object[] strings) {
        int rowCount = Math.min(tableMatrix.getRowCount(), strings.length);
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            tableMatrix.setEl(rowIndex, columnIndex, strings[rowIndex].toString());
        }
        return this;
    }

    public TeXTableObject column(int columnIndex, int rowStart, Object[] strings) {
        int rowCount = Math.min(tableMatrix.getRowCount() - rowStart, strings.length) + 1;
        for (int rowIndex = rowStart; rowIndex < rowCount; rowIndex++) {
            tableMatrix.setEl(rowIndex, columnIndex, strings[rowIndex - rowStart].toString());
        }
        return this;
    }

    public TeXTableObject column(int columnIndex, int rowStart, int rowEnd, Object[] strings) {
        rowEnd++;
        for (int rowIndex = rowStart; rowIndex < rowEnd; rowIndex++) {
            tableMatrix.setEl(rowIndex, columnIndex, strings[rowIndex - rowStart].toString());
        }
        return this;
    }

    public TeXTableObject column(int columnIndex, String... strings) {
        int rowCount = Math.min(tableMatrix.getRowCount(), strings.length);
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            tableMatrix.setEl(rowIndex, columnIndex, strings[rowIndex]);
        }
        return this;
    }

    public TeXTableObject column(int columnIndex, int rowStart, String... strings) {
        int rowCount = Math.min(tableMatrix.getRowCount() - rowStart, strings.length) + 1;
        for (int rowIndex = rowStart; rowIndex < rowCount; rowIndex++) {
            tableMatrix.setEl(rowIndex, columnIndex, strings[rowIndex - rowStart]);
        }
        return this;
    }

    public TeXTableObject column(int columnIndex, int rowStart, int rowEnd, String... strings) {
        rowEnd++;
        for (int rowIndex = rowStart; rowIndex < rowEnd; rowIndex++) {
            tableMatrix.setEl(rowIndex, columnIndex, strings[rowIndex - rowStart]);
        }
        return this;
    }
    //endregion

    //region Заповнення стовпчиків векторами зі строками
    public TeXTableObject column(int columnIndex, IVectorProvider strings) {
        int rowCount = Math.min(tableMatrix.getRowCount(), strings.getSize());
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            tableMatrix.setEl(rowIndex, columnIndex, strings.get(rowIndex).toString());
        }
        return this;
    }

    public TeXTableObject column(int columnIndex, int rowStart, IVectorProvider strings) {
        int rowCount = Math.min(tableMatrix.getRowCount() - rowStart, strings.getSize()) + 1;
        for (int rowIndex = rowStart; rowIndex < rowCount; rowIndex++) {
            tableMatrix.setEl(rowIndex, columnIndex, strings.get(rowIndex - rowStart).toString());
        }
        return this;
    }

    public TeXTableObject column(int columnIndex, int rowStart, int rowEnd, IVectorProvider strings) {
        rowEnd++;
        for (int rowIndex = rowStart; rowIndex < rowEnd; rowIndex++) {
            tableMatrix.setEl(rowIndex, columnIndex, strings.get(rowIndex - rowStart).toString());
        }
        return this;
    }
    //endregion

    //region Заповнення стовпчиків векторами з double елементами

    public TeXTableObject column(int columnIndex, IDoubleVectorProvider set) {
        int rowCount = Math.min(tableMatrix.getColumnCount(), set.getSize());
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            tableMatrix.setEl(rowIndex, columnIndex, numberFormat.format(set.get(rowIndex)));
        }
        return this;
    }

    public TeXTableObject column(int columnIndex, int rowStart, IDoubleVectorProvider set) {
        int rowCount = Math.min(tableMatrix.getColumnCount() - rowStart, set.getSize()) + 1;
        for (int rowIndex = rowStart; rowIndex < rowCount; rowIndex++) {
            tableMatrix.setEl(rowIndex, columnIndex, numberFormat.format(set.get(rowIndex - rowStart)));
        }
        return this;
    }

    public TeXTableObject column(int columnIndex, int rowStart, int rowEnd, IDoubleVectorProvider set) {
        rowEnd++;
        for (int rowIndex = rowStart; rowIndex < rowEnd; rowIndex++) {
            tableMatrix.setEl(rowIndex, columnIndex, numberFormat.format(set.get(rowIndex - rowStart)));
        }
        return this;
    }
    //endregion

    //endregion

    //region Заповнення прямокутника
    //region Заповнення прямокутника матрицею об’єктів
    public TeXTableObject rectangle(int rowStart, int columnStart, int rowEnd, int columnEnd, IMatrixProvider provider) {
        rowEnd++;
        columnEnd++;
        for (int rowIndex = rowStart; rowIndex < rowEnd; rowIndex++) {
            for (int columnIndex = columnStart; columnIndex < columnEnd; columnIndex++) {
                tableMatrix.setEl(rowIndex, columnIndex, provider.getEl(rowIndex - rowStart, columnIndex - columnStart).toString());
            }
        }
        return this;
    }

    public TeXTableObject rectangle(int rowStart, int columnStart, IMatrixProvider provider) {
        int rowCount = tableMatrix.getRowCount();
        int columnCount = tableMatrix.getColumnCount();
        for (int rowIndex = rowStart; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = columnStart; columnIndex < columnCount; columnIndex++) {
                tableMatrix.setEl(rowIndex, columnIndex, provider.getEl(rowIndex - rowStart, columnIndex - columnStart).toString());
            }
        }
        return this;
    }

    public TeXTableObject rectangle(IMatrixProvider provider) {
        int rowCount = tableMatrix.getRowCount();
        int columnCount = tableMatrix.getColumnCount();
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                tableMatrix.setEl(rowIndex, columnIndex, provider.getEl(rowIndex, columnIndex).toString());
            }
        }
        return this;
    }
    //endregion

    //region Заповнення прямокутника матрицею чисел
    public TeXTableObject rectangle(int rowStart, int columnStart, int rowEnd, int columnEnd, IDoubleMatrixProvider provider) {
        rowEnd++;
        columnEnd++;
        for (int rowIndex = rowStart; rowIndex < rowEnd; rowIndex++) {
            for (int columnIndex = columnStart; columnIndex < columnEnd; columnIndex++) {
                tableMatrix.setEl(rowIndex, columnIndex, numberFormat.format(provider.get(rowIndex - rowStart, columnIndex - columnStart)));
            }
        }
        return this;
    }

    public TeXTableObject rectangle(int rowStart, int columnStart, IDoubleMatrixProvider provider) {
        int rowCount = tableMatrix.getRowCount();
        int columnCount = tableMatrix.getColumnCount();
        for (int rowIndex = rowStart; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = columnStart; columnIndex < columnCount; columnIndex++) {
                tableMatrix.setEl(rowIndex, columnIndex, numberFormat.format(provider.get(rowIndex - rowStart, columnIndex - columnStart)));
            }
        }
        return this;
    }

    public TeXTableObject rectangle(IDoubleMatrixProvider provider) {
        int rowCount = tableMatrix.getRowCount();
        int columnCount = tableMatrix.getColumnCount();
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                tableMatrix.setEl(rowIndex, columnIndex, numberFormat.format(provider.get(rowIndex, columnIndex)));
            }
        }
        return this;
    }
    //endregion
    //endregion

    //region Заповнення іменованих рядків
    public TeXTableObject row(int rowIndex, String name, IVectorProvider v) {
        tableMatrix.setEl(rowIndex, 0, name);
        int columnCount = tableMatrix.getColumnCount();
        for (int columnIndex = 1; columnIndex < columnCount; columnIndex++) {
            tableMatrix.setEl(rowIndex, columnIndex, v.get(columnIndex - 1).toString());
        }
        return this;
    }

    public TeXTableObject row(int rowIndex, String name, IDoubleVectorProvider v) {
        tableMatrix.setEl(rowIndex, 0, name);
        int columnCount = tableMatrix.getColumnCount();
        for (int columnIndex = 1; columnIndex < columnCount; columnIndex++) {
            tableMatrix.setEl(rowIndex, columnIndex, numberFormat.format(v.get(columnIndex - 1)));
        }
        return this;
    }
    //endregion

    @Override
    public void write(TeXEventWriter out) {
        out.openEnvironment("tabular", null, convertToParam());
        int rowCount = tableMatrix.getRowCount();
        int columnCount = tableMatrix.getColumnCount();
        genereateHorizontalSeparator(out, columnCount, 0);
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            row(out, tableMatrix.row(rowIndex));
            genereateHorizontalSeparator(out, columnCount, rowIndex + 1);
        }
        out.closeEnvironment();
    }

    //region Приватні методи запису частин
    private void genereateHorizontalSeparator(TeXEventWriter out, int columnCount, int rowIndex) {
        if (horizontalLines.isRow(rowIndex)) {
            out.hline();
        } else {
            boolean cline_started = false;
            int cline_start = 0, cline_end = 0;
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                if (horizontalLines.get(rowIndex, columnIndex)) {
                    if (!cline_started) {
                        cline_started = true;
                        cline_start = columnIndex + 1;
                    }
                } else {
                    if (cline_started) {
                        cline_started = false;
                        cline_end = columnIndex + 1;
                        out.cline(cline_start, cline_end);
                    }
                }
            }
            if (cline_started) {
                cline_end = columnCount;
                out.cline(cline_start, cline_end);
            }
        }
    }

    private String convertToParam() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(generateCharString('|', verticalLines.get(0)));
        for (int i = 0; i < tableVector.length; i++) {
            stringBuilder.append(tableVector[i]);
            stringBuilder.append(generateCharString('|', verticalLines.get(i + 1)));
        }
        return stringBuilder.toString();
    }

    private void row(TeXEventWriter out, IVectorProvider<String> vector) {
        out.text(vector.get(0));
        int size = vector.getSize();
        for (int i = 1; i < size; i++) {
            out.append('&');
            out.append(vector.get(i));
        }
        out.newline();
    }
    //endregion
}
