package com.darkempire.math.struct.function.interfaces;

/**
 * Create in 18:21
 * Created by siredvin on 18.12.13.
 */
@FunctionalInterface
public interface FNumberToNumber<T extends com.darkempire.math.struct.Number<T>> {
    public T calc(T x);
}
