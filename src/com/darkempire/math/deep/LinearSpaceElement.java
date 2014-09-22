package com.darkempire.math.deep;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 02.11.13
 * Time: 22:21
 * To change this template use File | Settings | File Templates.
 */
public interface LinearSpaceElement<T extends LinearSpaceElement<T, K>, K extends FieldElement<K>> extends Cloneable {
    public T add(T t);

    public T subtract(T t);

    public T negate();

    public T multiply(K k);

    public T divide(K k);

    public LinearSpace<T, K> getSpace();
}
