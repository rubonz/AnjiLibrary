package com.darkempire.math.struct.function.interfaces;

import com.darkempire.math.struct.LinearCalcable;

/**
 * Create in 18:21
 * Created by siredvin on 18.12.13.
 */
@FunctionalInterface
public interface FLinearToLinear<T extends LinearCalcable<T>> {
    public T calc(T x);
}
