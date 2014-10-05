package com.darkempire.math.struct.set;

import com.darkempire.anji.document.tex.ITeXObject;
import com.darkempire.math.utils.IWolframObject;

import java.util.List;

/**
 * Created by siredvin on 05.10.14.
 */
//TODO:секціонувати
public abstract class DoubleInterval implements IDoubleInterval, SetCalcable<DoubleInterval>, ITeXObject, IWolframObject {
    /*public abstract double getStart(int index);
    public abstract void setStart(int index,double value);
    public abstract double getEnd(int index);
    public abstract void setEnd(int index,double value);
    public abstract boolean isClosedStart(int index);
    public abstract void closeStart(int index,boolean value);
    public abstract boolean isClosedEnd(int index);
    public abstract void closeEnd(int index,boolean value);
    public abstract boolean isClosed(int index);
    public abstract void close(int index,boolean value);*/
    public abstract double getStart();

    public abstract void setStart(double value);

    public abstract double getEnd();

    public abstract void setEnd(double value);

    public abstract boolean isClosedStart();

    public abstract void closeStart(boolean value);

    public abstract boolean isClosedEnd();

    public abstract void closeEnd(boolean value);

    public abstract boolean isClosed();

    public abstract void close(boolean value);

    public abstract DoubleInterval boundStart(double value);

    public abstract DoubleInterval boundEnd(double value);

    public abstract List<SimpleDoubleInterval> split();

    public abstract boolean isIntersection(DoubleInterval interval);
}
