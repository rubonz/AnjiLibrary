package com.darkempire.math.deep;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 05.11.13
 * Time: 21:09
 * To change this template use File | Settings | File Templates.
 */
public interface OpenSphere<T extends MetricalSpaceElement<T>> extends Set<T> {
    public double getRaduis();

    public T getCenter();

}
