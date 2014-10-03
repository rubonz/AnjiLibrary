package com.darkempire.anji.document.tex;

import com.darkempire.math.MathMachine;

import java.io.PrintStream;
import java.text.NumberFormat;

/**
 * Create in 16:04
 * Created by siredvin on 11.06.14.
 */
public class TeXDocumentManager {
    private TeXLabelManager labelManager;
    private TeXEventWriter out;
    private TeXFlowObjectManager flowObjectManager;

    public TeXDocumentManager(PrintStream pw) {
        out = new TeXEventWriter(pw, MathMachine.numberFormat);
        labelManager = new TeXLabelManager(out);
        flowObjectManager = new TeXFlowObjectManager(out, labelManager);
    }

    public TeXDocumentManager(PrintStream pw, NumberFormat numberFormat) {
        out = new TeXEventWriter(pw, numberFormat);
        labelManager = new TeXLabelManager(out);
        flowObjectManager = new TeXFlowObjectManager(out, labelManager);
    }

    public TeXLabelManager getLabelManager() {
        return labelManager;
    }

    public TeXEventWriter getEventWriter() {
        return out;
    }

    public TeXFlowObjectManager getFlowObjectManager() {
        return flowObjectManager;
    }

    public void close() {
        out.close();
    }
}
