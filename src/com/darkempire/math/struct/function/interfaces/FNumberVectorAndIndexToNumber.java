package com.darkempire.math.struct.function.interfaces;

import com.darkempire.math.struct.vector.INumberVectorProvider;

/**
 * Create in 23:42
 * Created by siredvin on 24.04.14.
 */
@FunctionalInterface
public interface FNumberVectorAndIndexToNumber<T extends com.darkempire.math.struct.Number<T>> {
    public T calc(INumberVectorProvider<T> provider, int index);
}
