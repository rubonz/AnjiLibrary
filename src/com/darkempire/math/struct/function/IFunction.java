package com.darkempire.math.struct.function;

/**
 * Create in 18:20
 * Created by siredvin on 18.12.13.
 */
@FunctionalInterface
public interface IFunction<K, V> {
    public V impl_calc(K x);
}
