package com.darkempire.math.deep;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 01.11.13
 * Time: 8:10
 * To change this template use File | Settings | File Templates.
 */
public interface FieldElement<T extends FieldElement<T>> extends LinearSpaceElement<T, T>, Cloneable {
    public T inverse();

    public Field<T> getField();

    public Object clone();
}
