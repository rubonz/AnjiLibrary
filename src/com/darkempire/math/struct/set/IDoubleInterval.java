package com.darkempire.math.struct.set;

/**
 * Created by siredvin on 05.10.14.
 */
//TODO:секціонувати
public interface IDoubleInterval {
    public boolean isContain(double value);

    public int getIntervalCount();

    public boolean isSimple();

    public boolean isInside(double value);
}
