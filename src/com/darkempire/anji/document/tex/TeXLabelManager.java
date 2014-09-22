package com.darkempire.anji.document.tex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create in 16:12
 * Created by siredvin on 11.06.14.
 */
public class TeXLabelManager {
    private Map<String, String> labelMap;
    private List<String> labelList;
    private TeXEventWriter out;
    private String labelPrefix;
    //Флаги
    private boolean isPrefixGlobal;

    public TeXLabelManager(TeXEventWriter out) {
        labelMap = new HashMap<>();
        labelList = new ArrayList<>();
        this.out = out;
        labelPrefix = "label";
        isPrefixGlobal = false;
    }

    public void createLabel(String name, String label) {
        if (isPrefixGlobal) {
            label = labelPrefix + ":" + label;
        }
        out.commP("label", label);
        labelMap.put(name, label);
        labelList.add(label);
    }

    public void createLabel(String name) {
        String label = labelPrefix + ':' + labelList.size();
        out.commP("label", label);
        labelMap.put(name, label);
        labelList.add(label);
    }

    public String getLabel(String name) {
        return labelMap.get(name);
    }

    public String getLabel(int index) {
        return labelList.get(index);
    }

    public String getLabelPrefix() {
        return labelPrefix;
    }

    public void setLabelPrefix(String labelPrefix) {
        this.labelPrefix = labelPrefix;
    }

    public boolean isPrefixGlobal() {
        return isPrefixGlobal;
    }

    public void setPrefixGlobal(boolean isPrefixGlobal) {
        this.isPrefixGlobal = isPrefixGlobal;
    }
}

