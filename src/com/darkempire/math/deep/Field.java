package com.darkempire.math.deep;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 02.11.13
 * Time: 21:35
 * To change this template use File | Settings | File Templates.
 */
public interface Field<T extends FieldElement<T>> extends LinearSpace<T, T> {
    public T getOne();

    public T getNewOne();
}
