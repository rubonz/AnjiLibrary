package com.darkempire.math.deep;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 05.11.13
 * Time: 21:11
 * To change this template use File | Settings | File Templates.
 */
public interface CloseSphere<T extends MetricalSpaceElement<T>> extends Set<T> {
    public double getRadius();

    public T getCenter();
}
