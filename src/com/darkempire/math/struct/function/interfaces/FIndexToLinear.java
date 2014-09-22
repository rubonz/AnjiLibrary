package com.darkempire.math.struct.function.interfaces;

/**
 * Create in 23:42
 * Created by siredvin on 24.04.14.
 */
@FunctionalInterface
public interface FIndexToLinear<T extends com.darkempire.math.struct.Number<T>> {
    public T calc(int index);
}
