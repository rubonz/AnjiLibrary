package com.darkempire.anjifx.beans;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 13.11.13
 * Time: 16:25
 * To change this template use File | Settings | File Templates.
 */
public interface IAnjiCollectionValue<T extends Collection<K>, K> extends IAnjiIterableValue<T, K>, Collection<K> {
    @Override
    default int size() {
        return getValue().size();
    }

    @Override
    default boolean isEmpty() {
        return getValue().isEmpty();
    }

    @Override
    default boolean contains(Object o) {
        return getValue().contains(o);
    }

    @Override
    default Object[] toArray() {
        return getValue().toArray();
    }

    @Override
    default <V> V[] toArray(V[] a) {
        return getValue().toArray(a);
    }

    @Override
    default boolean add(K k) {
        boolean result = getValue().add(k);
        if (result)
            setValue(getValue());
        return result;
    }

    @Override
    default boolean remove(Object o) {
        boolean result = getValue().remove(o);
        if (result)
            setValue(getValue());
        return result;
    }

    @Override
    default boolean containsAll(Collection<?> c) {
        return getValue().containsAll(c);
    }

    @Override
    default boolean addAll(Collection<? extends K> c) {
        boolean result = getValue().addAll(c);
        if (result)
            setValue(getValue());
        return result;
    }

    @Override
    default boolean removeAll(Collection<?> c) {
        boolean result = getValue().removeAll(c);
        if (result)
            setValue(getValue());
        return result;
    }

    @Override
    default boolean retainAll(Collection<?> c) {
        boolean result = getValue().retainAll(c);
        if (result)
            setValue(getValue());
        return result;
    }

    @Override
    default void clear() {
        getValue().clear();
        setValue(getValue());
    }

    @Override
    default Iterator<K> iterator() {
        return getValue().iterator();
    }
}
