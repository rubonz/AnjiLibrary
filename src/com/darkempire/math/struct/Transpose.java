package com.darkempire.math.struct;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 07.11.13
 * Time: 8:17
 * To change this template use File | Settings | File Templates.
 */
public class Transpose {
    private int i;
    private int j;

    public Transpose(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public int getValue() {
        return i;
    }

    public int getTranspose() {
        return j;
    }

    @Override
    public int hashCode() {
        return Math.max(i, j) << 16 + Math.min(i, j);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o instanceof Transpose) {
            Transpose t = (Transpose) o;
            return (t.i == i && t.j == j) || (t.j == i && t.i == j);
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("%d<->%d", i, j);
    }
}
