package com.darkempire.math.deep;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 02.11.13
 * Time: 22:22
 * To change this template use File | Settings | File Templates.
 */
public interface LinearSpace<T extends LinearSpaceElement<T, K>, K extends FieldElement<K>> {
    public T getZero();

    public T getNewZero();
}
