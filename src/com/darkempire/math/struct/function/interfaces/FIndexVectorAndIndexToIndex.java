package com.darkempire.math.struct.function.interfaces;

import com.darkempire.math.struct.vector.IIndexVectorProvider;

/**
 * Create in 23:42
 * Created by siredvin on 24.04.14.
 */
@FunctionalInterface
public interface FIndexVectorAndIndexToIndex {
    public int calc(IIndexVectorProvider provider, int index);
}
