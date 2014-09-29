package com.darkempire.math.struct.fractal.lsystem;

/**
 * Created by siredvin on 29.09.14.
 */
public class SimpleRule implements IRule {
    private String rule;

    public SimpleRule(String rule) {
        this.rule = rule;
    }

    @Override
    public String getRule() {
        return rule;
    }
}
