package com.darkempire.math.struct.fractal.lsystem;

import com.darkempire.math.struct.fractal.IFractal;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 02.11.13
 * Time: 21:50
 * To change this template use File | Settings | File Templates.
 */
public interface ILSystem extends IFractal {
    public enum CharType {
        MOVE, LINE, ARC, NONE;
    }

    public enum LSystemType {
        SIMPLE2D;
    }

    public double getAngle();

    public String getAxiom();

    public String getName();

    public CharType getCharType(char key);

    public String getRule(char key);

    public LSystemType getLSystemType();
}
