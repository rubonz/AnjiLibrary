package com.darkempire.anjifx.beans;

import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 13.11.13
 * Time: 16:24
 * To change this template use File | Settings | File Templates.
 */
public interface IAnjiIterableValue<T extends Iterable<K>, K> extends IAnjiValue<T>, Iterable<K> {
    @Override
    default Iterator<K> iterator() {
        return getValue().iterator();
    }
}
