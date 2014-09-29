package com.darkempire.anji.document.tex;

import java.util.Stack;

/**
 * Create in 8:07
 * Created by siredvin on 07.06.14.
 */
public class TeXFlowObjectManager {
    private TeXEventWriter out;

    //Зовнішні флаги
    private boolean createLabel;
    //Внутрішні змінні
    /**
     * Флаг, якій змінюється таким чином
     * 0 - нічого
     * 1 - figure
     * 2 - table
     */
    private byte currentEnvironment;
    //Лічильники
    private int figureCount;
    private int tableCount;
    //Внутрішній список оточень
    private Stack<String> flowEnvironmentStack;
    private TeXLabelManager labelManager;

    public TeXFlowObjectManager(TeXEventWriter out, TeXLabelManager labelManager) {
        this.out = out;
        createLabel = true;
        this.labelManager = labelManager;
        tableCount = figureCount = 0;
        flowEnvironmentStack = new Stack<>();
    }

    //Плаваючі об’єкти без параметрів
    public TeXFlowObjectManager figure() {
        out.openEnvironment("figure");
        flowEnvironmentStack.push("figure");
        currentEnvironment = 1;
        return this;
    }

    public TeXFlowObjectManager table() {
        out.openEnvironment("table");
        flowEnvironmentStack.push("table");
        currentEnvironment = 2;
        return this;
    }

    //Плаваючі об’єкти з параметрами

    public TeXFlowObjectManager table(String modificators) {
        out.openEnvironment("table", modificators);
        flowEnvironmentStack.push("table");
        currentEnvironment = 2;
        return this;
    }

    public TeXFlowObjectManager figure(String modificators) {
        out.openEnvironment("figure", modificators);
        flowEnvironmentStack.push("figure");
        currentEnvironment = 1;
        return this;
    }

    //Команди роботи з плаваючими об’єктами
    public TeXFlowObjectManager clearpage() {
        out.comm("clearpage");
        return this;
    }

    public TeXFlowObjectManager cleardoublepage() {
        out.comm("cleardoublepage");
        return this;
    }

    public TeXFlowObjectManager caption(String caption) {
        out.commP("caption", caption);
        String label;
        if (createLabel) {
            switch (currentEnvironment) {
                case 1:
                    label = "figure:" + figureCount;
                    labelManager.createLabel(label, label);
                    figureCount++;
                    break;
                case 2:
                    label = "table:" + tableCount;
                    labelManager.createLabel(label, label);
                    tableCount++;
                    break;
                default:
                    break;
            }
        }
        return this;
    }

    public TeXFlowObjectManager listoffigures() {
        out.comm("listoffigures");
        return this;
    }

    public TeXFlowObjectManager listoftables() {
        out.comm("listoftables");
        return this;
    }

    //Допоможні методи
    public TeXFlowObjectManager closeEnvironment() {
        out.closeEnvironment();
        flowEnvironmentStack.pop();
        if (flowEnvironmentStack.isEmpty()) {
            currentEnvironment = 0;
            return this;
        }
        String next = flowEnvironmentStack.peek();
        switch (next) {
            case "figure":
                currentEnvironment = 1;
                break;
            case "table":
                currentEnvironment = 2;
                break;
            default:
                currentEnvironment = 0;
                break;
        }
        return this;
    }

    //Флаги

    public boolean isCreateLabel() {
        return createLabel;
    }

    public void setCreateLabel(boolean isCreateLabel) {
        this.createLabel = isCreateLabel;
    }

}
