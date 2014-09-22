package com.darkempire.math.deep;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 05.11.13
 * Time: 21:11
 * To change this template use File | Settings | File Templates.
 */
public interface MeasureSet<T extends MeasureSpaceElement<T>> extends Set<T> {
    public double getMeasure();

    public MeasureSet<T> add(T t);

    public MeasureSet<T> addAll(MeasureSet<T> measureSet);
}
