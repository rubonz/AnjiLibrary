package com.darkempire.anji.document.tex;

import com.darkempire.math.struct.matrix.DoubleMatrix;
import com.darkempire.math.exception.MatrixSizeException;

import java.io.PrintStream;
import java.text.NumberFormat;

/**
 * Create in 18:48
 * Created by siredvin on 22.04.14.
 */
@Deprecated
public class TexStreamWriter {
    private PrintStream out;
    private NumberFormat numberFormat;

    public TexStreamWriter(PrintStream out, NumberFormat numberFormat) {
        this.out = out;
        this.numberFormat = numberFormat;
    }

    public TexStreamWriter out(DoubleMatrix m, boolean isCentered) {
        StringBuilder sb = new StringBuilder();
        if (isCentered) {
            sb.append("\\begin{center}\n");
        }
        int rowCount = m.getRowCount();
        int columnCount = m.getColumnCount();
        sb.append("\t\\begin{tabular}{|");
        for (int i = 0; i < columnCount; i++) {
            sb.append("c|");
        }
        sb.append("}\\hline\n");
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            sb.append("\t\t");
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                sb.append(numberFormat.format(m.get(columnIndex, rowIndex)));
                sb.append('&');
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append("\\\\\\hline\n");
        }
        sb.append("\t\\end{tabular}\n");
        if (isCentered) {
            sb.append("\\end{center}\n");
        }
        out.println(sb.toString());
        return this;
    }

    public TexStreamWriter out(DoubleMatrix m, boolean isCentered, String header) {
        StringBuilder sb = new StringBuilder();
        if (isCentered) {
            sb.append("\\begin{center}\n");
        }
        int rowCount = m.getRowCount();
        int columnCount = m.getColumnCount();
        sb.append("\t\\begin{tabular}{|");
        for (int i = 0; i < columnCount; i++) {
            sb.append("c|");
        }
        sb.append("}\\hline\n\t\t");
        sb.append(header);
        sb.append("\\\\\\hline\n");
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            sb.append("\t\t");
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                sb.append(numberFormat.format(m.get(columnIndex, rowIndex)));
                sb.append('&');
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append("\\\\\\hline\n");
        }
        sb.append("\t\\end{tabular}\n");
        if (isCentered) {
            sb.append("\\end{center}\n");
        }
        out.println(sb.toString());
        return this;
    }

    public TexStreamWriter out(DoubleMatrix m, boolean isCentered, String header, String... rowsHeader) {
        int rowCount = m.getRowCount();
        int columnCount = m.getColumnCount();
        if (rowsHeader.length != rowCount) {
            throw new MatrixSizeException(MatrixSizeException.MATRIX_SIZE_MISMATCH);
        }
        StringBuilder sb = new StringBuilder();
        if (isCentered) {
            sb.append("\\begin{center}\n");
        }
        sb.append("\t\\begin{tabular}{|c||");
        for (int i = 0; i < columnCount; i++) {
            sb.append("c|");
        }
        sb.append("}\\hline\n\t\t");
        sb.append(header);
        sb.append("\\\\\\hline\n");
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            sb.append("\t\t");
            sb.append(rowsHeader[rowIndex]);
            sb.append("&");
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                sb.append(numberFormat.format(m.get(columnIndex, rowIndex)));
                sb.append('&');
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append("\\\\\\hline\n");
        }
        sb.append("\t\\end{tabular}\n");
        if (isCentered) {
            sb.append("\\end{center}\n");
        }
        out.println(sb.toString());
        return this;
    }

    public TexStreamWriter out(DoubleMatrix m, String header, String... rowsHeader) {
        return out(m, true, header, rowsHeader);
    }

    public TexStreamWriter out(DoubleMatrix m, String header) {
        return out(m, true, header);
    }

    public TexStreamWriter out(DoubleMatrix m) {
        return out(m, true);
    }

    public TexStreamWriter out(String text) {
        out.println(text);
        return this;
    }
}
