package com.darkempire.anji.document.tex.util;

import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.anji.document.tex.TeXTableObject;
import com.darkempire.anji.util.Util;
import com.darkempire.math.exception.MatrixSizeException;
import com.darkempire.math.struct.matrix.DoubleMatrix;
import com.darkempire.math.struct.matrix.Matrix;
import com.darkempire.math.struct.vector.IDoubleVectorProvider;

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
}
