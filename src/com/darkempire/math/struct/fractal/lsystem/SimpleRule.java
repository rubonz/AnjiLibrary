package com.darkempire.math.struct.fractal.lsystem;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 26.10.13
 * Time: 21:23
 * To change this template use File | Settings | File Templates.
 */
public class SimpleRule implements IRule {
    private String transform;

    public SimpleRule(String transform) {
        this.transform = transform;
    }

    @Override
    public String getRule() {
        return transform;
    }

    public void setTransform(String transform) {
        this.transform = transform;
    }
}
