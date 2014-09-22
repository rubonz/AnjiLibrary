package com.darkempire.math.struct.fractal.lsystem;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 02.11.13
 * Time: 21:51
 * To change this template use File | Settings | File Templates.
 */
public class SimpleCharType implements ICharType {
    private ILSystem.CharType charType;

    public SimpleCharType(ILSystem.CharType charType) {
        this.charType = charType;
    }

    public void setCharType(ILSystem.CharType charType) {
        this.charType = charType;
    }

    @Override
    public ILSystem.CharType getCharType() {
        return charType;
    }
}
