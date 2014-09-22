package com.darkempire.anjifx.beans.property.transform;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 27.11.13
 * Time: 20:58
 * To change this template use File | Settings | File Templates.
 */
public interface ITransform<K, V> {
    public K value(V value);

    public V key(K value);
}
