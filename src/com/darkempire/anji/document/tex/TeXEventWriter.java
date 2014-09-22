package com.darkempire.anji.document.tex;

import com.darkempire.anji.util.Util;
import com.darkempire.math.exception.MatrixSizeException;
import com.darkempire.math.struct.matrix.DoubleMatrix;

import java.io.PrintStream;
import java.text.NumberFormat;
import java.util.Stack;

/**
 * Create in 9:16
 * Created by siredvin on 24.04.14.
 */
public class TeXEventWriter {

    private PrintStream out;

    private NumberFormat numberFormat;

    private Stack<String> environmentStack;
    //Внутрішні флаги3
    /**
     * Флаг відповідає за те, чи потрібний символ переходу на нову лінію
     */
    private boolean isNeedNewline;
    //Зовнішні флаги
    /**
     * Флаг, який вказує на те, чи додавати \hline після кожного рядка в таблиці
     */
    private boolean isAddHline;

    public TeXEventWriter(PrintStream out, NumberFormat numberFormat) {
        this.out = out;
        this.numberFormat = numberFormat;
        environmentStack = new Stack<>();
        isNeedNewline = false;
        isAddHline = true;
    }

    public boolean isNeedNewline() {
        return isNeedNewline;
    }

    public void setNeedNewline(boolean isNeedNewline) {
        this.isNeedNewline = isNeedNewline;
    }

    public NumberFormat getNumberFormat() {
        return numberFormat;
    }

    //Загальні команди
    public TeXEventWriter comm(String comm) {
        generateTabular();
        out.print("\\");
        out.println(comm);
        return this;
    }

    public TeXEventWriter commP(String comm, String... params) {
        generateTabular();
        out.print('\\');
        out.print(comm);
        for (String s : params) {
            out.print('{');
            out.print(s);
            out.print('}');
        }
        out.println();
        return this;
    }

    public TeXEventWriter commSP(String comm, String second, String... params) {
        generateTabular();
        out.print("\\");
        out.print(comm);
        out.print('[');
        out.print(second);
        out.print(']');
        for (String s : params) {
            out.print('{');
            out.print(s);
            out.print('}');
        }
        out.println();
        return this;
    }

    public TeXEventWriter icomm(String comm) {
        generateTabular();
        out.print("\\");
        out.print(comm);
        return this;
    }

    public TeXEventWriter icommP(String comm, String... params) {
        generateTabular();
        out.print('\\');
        out.print(comm);
        for (String s : params) {
            out.print('{');
            out.print(s);
            out.print('}');
        }
        return this;
    }

    public TeXEventWriter icommSP(String comm, String second, String... params) {
        generateTabular();
        out.print("\\");
        out.print(comm);
        out.print('[');
        out.print(second);
        out.print(']');
        for (String s : params) {
            out.print('{');
            out.print(s);
            out.print('}');
        }
        return this;
    }

    //Розділ окремих команд
    public TeXEventWriter usePackage(String name) {
        return commP("usepackage", name);
    }

    public TeXEventWriter usePackage(String name, String params) {
        return commSP("usepackage", params, name);
    }

    public TeXEventWriter newpage() {
        return comm("newpage");
    }

    public TeXEventWriter newline() {
        return comm("\\");
    }

    public TeXEventWriter item() {
        return icomm("item");
    }

    public TeXEventWriter item(String text) {
        return icommSP("item", text);
    }

    public TeXEventWriter input(String name) {
        return icommP("input", name);
    }

    //Розділ оточень
    public TeXEventWriter openEnvironment(String environment) {
        generateTabular();
        out.print("\\begin{");
        out.print(environment);
        out.println("}");
        environmentStack.push(environment);
        return this;
    }

    public TeXEventWriter openEnvironment(String environment, String modificators) {
        generateTabular();
        out.print("\\begin{");
        out.print(environment);
        out.print('}');
        if (modificators != null && !modificators.isEmpty()) {
            out.print('[');
            out.print(modificators);
            out.print(']');
        }
        out.println();
        environmentStack.push(environment);
        return this;
    }

    public TeXEventWriter openEnvironment(String environment, String modificators, String params) {
        generateTabular();
        out.print("\\begin{");
        out.print(environment);
        out.print("}");
        if (modificators != null && !modificators.isEmpty()) {
            out.print('[');
            out.print(modificators);
            out.print(']');
        }
        if (params != null && !params.isEmpty()) {
            out.print('{');
            out.print(params);
            out.print('}');
        }
        out.println();
        environmentStack.push(environment);
        return this;
    }

    public TeXEventWriter closeEnvironment() {
        if (isNeedNewline) {
            out.println();
            isNeedNewline = false;
        }
        generateTabularm1();
        String environment = environmentStack.pop();
        out.print("\\end{");
        out.print(environment);
        out.println("}");
        return this;
    }

    //Стандартні оточення
    public TeXEventWriter center() {
        openEnvironment("center");
        return this;
    }

    public TeXEventWriter left() {
        openEnvironment("flushleft");
        return this;
    }

    public TeXEventWriter right() {
        openEnvironment("flushright");
        return this;
    }

    public TeXEventWriter equation() {
        openEnvironment("equation");
        return this;
    }

    public TeXEventWriter eqnarray() {
        openEnvironment("eqnarray");
        return this;
    }

    public TeXEventWriter multline() {
        openEnvironment("multline");
        return this;
    }

    public TeXEventWriter equationa() {
        openEnvironment("equation*");
        return this;
    }

    public TeXEventWriter eqnarraya() {
        openEnvironment("eqnarray*");
        return this;
    }

    public TeXEventWriter multlinea() {
        openEnvironment("multline*");
        return this;
    }

    public TeXEventWriter itemize() {
        openEnvironment("itemize");
        return this;
    }

    public TeXEventWriter enumerate() {
        openEnvironment("enumerate");
        return this;
    }

    public TeXEventWriter description() {
        openEnvironment("description");
        return this;
    }

    //Розділ введення нового тексту
    public TeXEventWriter text(String x) {
        generateTabular();
        append(x);
        isNeedNewline = true;
        return this;
    }

    public TeXEventWriter text(Object x) {
        generateTabular();
        append(x);
        isNeedNewline = true;
        return this;
    }

    public TeXEventWriter text(double x) {
        generateTabular();
        append(x);
        isNeedNewline = true;
        return this;
    }

    public TeXEventWriter text(int x) {
        generateTabular();
        append(x);
        isNeedNewline = true;
        return this;
    }

    public TeXEventWriter text(char x) {
        generateTabular();
        append(x);
        isNeedNewline = true;
        return this;
    }

    public TeXEventWriter text(boolean x) {
        generateTabular();
        append(x);
        isNeedNewline = true;
        return this;
    }

    public TeXEventWriter text(float x) {
        generateTabular();
        append(x);
        isNeedNewline = true;
        return this;
    }

    public TeXEventWriter text(long x) {
        generateTabular();
        append(x);
        isNeedNewline = true;
        return this;
    }

    public TeXEventWriter text(String... x) {
        generateTabular();
        append(x);
        isNeedNewline = true;
        return this;
    }

    public TeXEventWriter text(Object... x) {
        generateTabular();
        append(x);
        isNeedNewline = true;
        return this;
    }

    public TeXEventWriter text(double... x) {
        generateTabular();
        append(x);
        isNeedNewline = true;
        return this;
    }

    public TeXEventWriter text(int... x) {
        generateTabular();
        append(x);
        isNeedNewline = true;
        return this;
    }

    public TeXEventWriter text(char... x) {
        generateTabular();
        append(x);
        isNeedNewline = true;
        return this;
    }

    public TeXEventWriter text(boolean... x) {
        generateTabular();
        append(x);
        isNeedNewline = true;
        return this;
    }

    public TeXEventWriter text(float... x) {
        generateTabular();
        append(x);
        isNeedNewline = true;
        return this;
    }

    public TeXEventWriter text(long... x) {
        generateTabular();
        append(x);
        isNeedNewline = true;
        return this;
    }

    //Розділ додавання тексту
    public TeXEventWriter append(String text) {
        out.print(text);
        return this;
    }

    public TeXEventWriter append(Object object) {
        out.print(object);
        return this;
    }

    public TeXEventWriter append(double s) {
        out.print(numberFormat.format(s));
        return this;
    }

    public TeXEventWriter append(int s) {
        out.print(numberFormat.format(s));
        return this;
    }

    public TeXEventWriter append(char c) {
        out.print(c);
        return this;
    }

    public TeXEventWriter append(boolean b) {
        out.print(b);
        return this;
    }

    public TeXEventWriter append(float f) {
        out.print(numberFormat.format(f));
        return this;
    }

    public TeXEventWriter append(long l) {
        out.print(numberFormat.format(l));
        return this;
    }

    public TeXEventWriter append(String... texts) {
        for (String s : texts) {
            out.print(s);
        }
        return this;
    }

    public TeXEventWriter append(Object... objects) {
        for (Object object : objects) {
            out.print(object);
        }
        return this;
    }

    public TeXEventWriter append(double... ds) {
        for (int i = 0; i < ds.length; i++) {
            out.print(numberFormat.format(ds[i]));
            if (ds.length != i + 1)
                out.print(',');
        }
        return this;
    }

    public TeXEventWriter append(int... is) {
        for (int i = 0; i < is.length; i++) {
            out.print(numberFormat.format(is[i]));
            if (is.length != i + 1)
                out.print(',');
        }
        return this;
    }

    public TeXEventWriter append(char... chars) {
        for (int i = 0; i < chars.length; i++) {
            out.print(chars[i]);
            if (chars.length != i + 1)
                out.print(',');
        }
        return this;
    }

    public TeXEventWriter append(boolean... bs) {
        for (int i = 0; i < bs.length; i++) {
            out.print(bs[i]);
            if (bs.length != i + 1)
                out.print(',');
        }
        return this;
    }


    public TeXEventWriter append(float... fs) {
        for (int i = 0; i < fs.length; i++) {
            out.print(numberFormat.format(fs[i]));
            if (fs.length != i + 1)
                out.print(',');
        }
        return this;
    }

    public TeXEventWriter append(long... ls) {
        for (int i = 0; i < ls.length; i++) {
            out.print(numberFormat.format(ls[i]));
            if (ls.length != i + 1)
                out.print(',');
        }
        return this;
    }

    //Математичний введення
    public TeXEventWriter matrix(DoubleMatrix m) {
        matrix(m, "A", generateHeader("x", m.getColumnCount()), generateRows("x", m.getRowCount()));
        return this;
    }

    public TeXEventWriter matrix(DoubleMatrix m, String name) {
        matrix(m, name, generateHeader("x", m.getColumnCount()), generateRows("x", m.getRowCount()));
        return this;
    }

    public TeXEventWriter matrix(DoubleMatrix m, String name, String header) {
        matrix(m, name, header, generateRows("x", m.getRowCount()));
        return this;
    }

    public TeXEventWriter matrix(DoubleMatrix m, String name, String header, String... rowsHeader) {
        int rowCount = m.getRowCount();
        int columnCount = m.getColumnCount();
        if (rowsHeader.length != rowCount) {
            throw new MatrixSizeException(MatrixSizeException.MATRIX_SIZE_MISMATCH);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("|c||");
        for (int i = 0; i < columnCount; i++) {
            sb.append("c|");
        }
        openEnvironment("tabular", null, sb.toString());
        if (isAddHline) {
            hline();
        }
        text(name);
        append("&");
        append(header);
        newline();
        hline();
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            text(rowsHeader[rowIndex]);
            append("&");
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                append(getNumberFormat().format(m.get(rowIndex, columnIndex)));
                if (columnCount != columnIndex + 1)
                    append("&");
            }
            newline();
            if (isAddHline) {
                hline();
            }
        }
        closeEnvironment();
        newline();
        return this;
    }


    public TeXEventWriter hline() {
        return text("\\hline");
    }

    public TeXEventWriter cline(int start, int end) {
        text("\\cline{");
        append(start);
        append('-');
        append(end);
        return append('}');
    }


    //Допоміжні внутрішні функції
    private TeXEventWriter generateTabular() {
        if (isNeedNewline) {
            out.println();
            isNeedNewline = false;
        }
        for (String anEnvironmentStack : environmentStack) {
            out.print('\t');
        }
        return this;
    }

    private TeXEventWriter generateTabularm1() {
        int size = environmentStack.size() - 1;
        if (isNeedNewline) {
            out.println();
            isNeedNewline = false;
        }
        for (int i = 0; i < size; i++) {
            out.print('\t');
        }
        return this;
    }

    //Допоміжні зовнішні функції
    public String[] generateRows(String t, int count) {
        String[] result = new String[count];
        for (int i = 0; i < count; i++) {
            result[i] = "$" + t + "_{" + (i + 1) + "}$";
        }
        return result;
    }

    public String generateHeader(String t, int count) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            builder.append("$");
            builder.append(t);
            builder.append("_{");
            builder.append(i + 1);
            builder.append("}$&");
        }
        Util.removeLastChar(builder);
        return builder.toString();
    }

    public String closeNamedEnvironment() {
        generateTabularm1();
        String environment = environmentStack.pop();
        out.print("\\end{");
        out.print(environment);
        out.println("}");
        return environment;
    }

    public String getLastEnvironment() {
        return environmentStack.peek();
    }

    protected Stack<String> getEnvironmentStack() {
        return environmentStack;
    }
}
