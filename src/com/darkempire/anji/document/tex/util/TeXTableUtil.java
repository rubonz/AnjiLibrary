package com.darkempire.anji.document.tex.util;

import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.anji.document.tex.TeXTableObject;
import com.darkempire.anji.log.Log;
import com.darkempire.anji.util.Util;
import com.darkempire.math.MathMachine;
import com.darkempire.math.exception.MatrixSizeException;
import com.darkempire.math.struct.matrix.DoubleMatrix;
import com.darkempire.math.struct.matrix.Matrix;
import com.darkempire.math.struct.vector.FixedVector;
import com.darkempire.math.struct.vector.IDoubleVectorProvider;
import com.darkempire.math.struct.vector.IVectorProvider;
import com.darkempire.math.struct.vector.Vector;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by siredvin on 26.09.14.
 */
@AnjiUtil
public final class TeXTableUtil {
    private TeXTableUtil() {
    }

    //region Створення таблиці з матриці чисел
    public static TeXTableObject matrix(DoubleMatrix m) {
        return matrix(m, "A", generateHeader("x", m.getColumnCount()), generateHeader("x", m.getRowCount()));
    }

    public static TeXTableObject matrix(DoubleMatrix m, String name) {
        return matrix(m, name, generateHeader("x", m.getColumnCount()), generateHeader("x", m.getRowCount()));
    }

    public static TeXTableObject matrix(DoubleMatrix m, String name, String[] columnHeader) {
        return matrix(m, name, columnHeader, generateHeader("x", m.getRowCount()));
    }

    public static TeXTableObject matrix(DoubleMatrix m, String name, String[] columnHeader, String[] rowHeader) {
        int rowCount = m.getRowCount();
        int columnCount = m.getColumnCount();
        if (rowHeader.length != rowCount && columnHeader.length != columnCount) {
            throw new MatrixSizeException(MatrixSizeException.MATRIX_SIZE_MISMATCH);
        }
        TeXTableObject result = new TeXTableObject(rowCount + 1, columnCount + 1);
        //Створення скелету таблиці
        result.getHorizontalLinesMatrix().fill(true);
        result.getVerticalLinesVector().fill(1);
        //Заповнення матриці
        Matrix<String> tableMatrix = result.getTableMatrix();
        tableMatrix.setEl(0, 0, name);//Ім’я
        result.row(0, 1, columnHeader);
        result.column(0, 1, rowHeader);
        result.rectangle(1, 1, m);
        return result;
    }

    public static TeXTableObject matrix(String name, String[] columnHeader, String[] rowHeader, IDoubleVectorProvider... vectors) {
        int rowCount = vectors.length;
        int columnCount = vectors[0].getSize();
        if (rowHeader.length != rowCount && columnHeader.length != columnCount) {
            throw new MatrixSizeException(MatrixSizeException.MATRIX_SIZE_MISMATCH);
        }
        TeXTableObject result = new TeXTableObject(rowCount + 1, columnCount + 1);
        //Створення скелету таблиці
        result.getHorizontalLinesMatrix().fill(true);
        result.getVerticalLinesVector().fill(1);
        //Заповнення матриці
        Matrix<String> tableMatrix = result.getTableMatrix();
        tableMatrix.setEl(0, 0, name);//Ім’я
        result.row(0, 1, columnHeader);
        result.column(0, 1, rowHeader);
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            result.row(rowIndex + 1, 1, vectors[rowIndex]);
        }
        return result;
    }
    //endregion

    //region Створення заголовків для таблиць
    public static String[] generateHeader(String t, int count) {
        String[] result = new String[count];
        for (int i = 0; i < count; i++) {
            result[i] = "$" + t + "_{" + (i + 1) + "}$";
        }
        return result;
    }
    //endregion

    //region Створення таблиці у спеціальних випадках
    //endregion

    //region Потокове заповнення
    public static TableFlowFillRow rowFlow() {
        return new TableFlowFillRow();
    }

    public static TableFlowFillColumn columnFlow() {
        return new TableFlowFillColumn();
    }
    //endregion

    public static final class TableFlowFillRow {
        private NumberFormat numberFormat;
        private List<Vector<String>> vectorList;

        //region Конструктори
        public TableFlowFillRow() {
            this(MathMachine.numberFormat);
        }

        public TableFlowFillRow(NumberFormat numberFormat) {
            this.numberFormat = numberFormat;
            vectorList = new ArrayList<>();
        }
        //endregion

        //region Заповнення іменованих рядків
        public TableFlowFillRow row(String name, IVectorProvider v) {
            int size = v.getSize();
            Vector<String> vector = new FixedVector<>(new String[size + 1]);
            vector.set(0, name);
            for (int i = 0; i < size; i++) {
                vector.set(i + 1, v.get(i).toString());
            }
            vectorList.add(vector);
            return this;
        }

        public TableFlowFillRow row(String name, IDoubleVectorProvider v) {
            int size = v.getSize();
            Vector<String> vector = new FixedVector<>(new String[size + 1]);
            vector.set(0, name);
            for (int i = 0; i < size; i++) {
                vector.set(i + 1, numberFormat.format(v.get(i)));
            }
            Log.log(Log.debugIndex, vector);
            vectorList.add(vector);
            return this;
        }

        public TableFlowFillRow row(String name, String[] values) {
            int size = values.length;
            Vector<String> vector = new FixedVector<>(new String[size + 1]);
            vector.set(0, name);
            for (int i = 0; i < size; i++) {
                vector.set(i + 1, values[i]);
            }
            vectorList.add(vector);
            return this;
        }
        //endregion

        //region Заповнення рядків
        public TableFlowFillRow row(Object[] objects) {
            int size = objects.length;
            Vector<String> vector = new FixedVector<>(new String[size + 1]);
            for (int i = 0; i < size; i++) {
                vector.set(i, objects[i].toString());
            }
            vectorList.add(vector);
            return this;
        }

        public TableFlowFillRow row(String... strings) {
            int size = strings.length;
            Vector<String> vector = new FixedVector<>(new String[size + 1]);
            for (int i = 0; i < size; i++) {
                vector.set(i, strings[i]);
            }
            vectorList.add(vector);
            return this;
        }

        public TableFlowFillRow row(IVectorProvider v) {
            int size = v.getSize();
            Vector<String> vector = new FixedVector<>(new String[size + 1]);
            for (int i = 0; i < size; i++) {
                vector.set(i, v.get(i).toString());
            }
            vectorList.add(vector);
            return this;
        }

        public TableFlowFillRow row(IDoubleVectorProvider v) {
            int size = v.getSize();
            Vector<String> vector = new FixedVector<>(new String[size + 1]);
            for (int i = 0; i < size; i++) {
                vector.set(i, numberFormat.format(v.get(i)));
            }
            vectorList.add(vector);
            return this;
        }
        //endregion

        public TeXTableObject compile() {
            int size = vectorList.get(0).getSize();
            int rowCount = vectorList.size();
            TeXTableObject tableObject = new TeXTableObject(rowCount, size);
            for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                tableObject.row(rowIndex, vectorList.get(rowIndex));
            }
            return tableObject;
        }
    }

    public static final class TableFlowFillColumn {
        private NumberFormat numberFormat;
        private List<Vector<String>> vectorList;

        //region Конструктори
        public TableFlowFillColumn() {
            this(MathMachine.numberFormat);
        }

        public TableFlowFillColumn(NumberFormat numberFormat) {
            this.numberFormat = numberFormat;
            vectorList = new ArrayList<>();
        }
        //endregion

        //region Заповнення іменованих колонок
        public TableFlowFillColumn column(String name, IVectorProvider v) {
            int size = v.getSize();
            Vector<String> vector = new FixedVector<>(new String[size + 1]);
            vector.set(0, name);
            for (int i = 0; i < size; i++) {
                vector.set(i + 1, v.get(i).toString());
            }
            vectorList.add(vector);
            return this;
        }

        public TableFlowFillColumn column(String name, IDoubleVectorProvider v) {
            int size = v.getSize();
            Vector<String> vector = new FixedVector<>(new String[size + 1]);
            vector.set(0, name);
            for (int i = 0; i < size; i++) {
                vector.set(i + 1, numberFormat.format(v.get(i)));
            }
            vectorList.add(vector);
            return this;
        }

        public TableFlowFillColumn column(String name, String[] values) {
            int size = values.length;
            Vector<String> vector = new FixedVector<>(new String[size + 1]);
            vector.set(0, name);
            for (int i = 0; i < size; i++) {
                vector.set(i + 1, values[i]);
            }
            vectorList.add(vector);
            return this;
        }
        //endregion

        //region Заповнення колонок
        public TableFlowFillColumn column(Object[] objects) {
            int size = objects.length;
            Vector<String> vector = new FixedVector<>(new String[size + 1]);
            for (int i = 0; i < size; i++) {
                vector.set(i, objects[i].toString());
            }
            vectorList.add(vector);
            return this;
        }

        public TableFlowFillColumn column(String... strings) {
            int size = strings.length;
            Vector<String> vector = new FixedVector<>(new String[size + 1]);
            for (int i = 0; i < size; i++) {
                vector.set(i, strings[i]);
            }
            vectorList.add(vector);
            return this;
        }

        public TableFlowFillColumn column(IVectorProvider v) {
            int size = v.getSize();
            Vector<String> vector = new FixedVector<>(new String[size + 1]);
            for (int i = 0; i < size; i++) {
                vector.set(i, v.get(i).toString());
            }
            vectorList.add(vector);
            return this;
        }

        public TableFlowFillColumn column(IDoubleVectorProvider v) {
            int size = v.getSize();
            Vector<String> vector = new FixedVector<>(new String[size + 1]);
            for (int i = 0; i < size; i++) {
                vector.set(i, numberFormat.format(v.get(i)));
            }
            vectorList.add(vector);
            return this;
        }
        //endregion

        public TeXTableObject compile() {
            int size = vectorList.get(0).getSize();
            int columnCount = vectorList.size();
            TeXTableObject tableObject = new TeXTableObject(size, columnCount);
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                tableObject.column(columnIndex, vectorList.get(columnIndex));
            }
            return tableObject;
        }
    }
}
