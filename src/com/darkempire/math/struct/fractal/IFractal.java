package com.darkempire.math.struct.fractal;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 04.11.13
 * Time: 9:31
 * To change this template use File | Settings | File Templates.
 */
public interface IFractal {
    public enum FractalDisplayType {
        LINDERMAYER_SYSTEM
    }

    public String getName();

    public FractalDisplayType getDisplayType();
}
