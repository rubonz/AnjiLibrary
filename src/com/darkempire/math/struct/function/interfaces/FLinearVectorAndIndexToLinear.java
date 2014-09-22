package com.darkempire.math.struct.function.interfaces;

import com.darkempire.math.struct.vector.ILinearVectorProvider;

/**
 * Create in 23:42
 * Created by siredvin on 24.04.14.
 */
@FunctionalInterface
public interface FLinearVectorAndIndexToLinear<T extends com.darkempire.math.struct.Number<T>> {
    public T calc(ILinearVectorProvider<T> provider, int index);
}
