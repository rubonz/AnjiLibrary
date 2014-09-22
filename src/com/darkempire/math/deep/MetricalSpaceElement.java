package com.darkempire.math.deep;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 05.11.13
 * Time: 21:07
 * To change this template use File | Settings | File Templates.
 */
public interface MetricalSpaceElement<T extends MetricalSpaceElement<T>> {
    public double distance(T t);

    public MetricalSpace<T> getSpace();
}
