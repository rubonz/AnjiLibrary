package com.darkempire.math.struct.function.interfaces;

import com.darkempire.math.struct.vector.IVectorProvider;

/**
 * Create in 23:42
 * Created by siredvin on 24.04.14.
 */
@FunctionalInterface
public interface FVectorIndexToSome<T> {
    public T calc(IVectorProvider<T> provider, int index);
}
