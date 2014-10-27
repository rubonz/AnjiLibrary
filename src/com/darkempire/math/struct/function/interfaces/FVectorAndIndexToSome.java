package com.darkempire.math.struct.function.interfaces;

import com.darkempire.math.struct.vector.IVectorProvider;

/**
 * Created by siredvin on 27.10.14.
 *
 * @author siredvin
 */
@FunctionalInterface
public interface FVectorAndIndexToSome<T> {
    public T calc(IVectorProvider<T> provider, int index);
}
