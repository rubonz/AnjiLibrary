package com.darkempire.math.struct.function.interfaces;

import com.darkempire.math.struct.vector.IBooleanVectorProvider;

/**
 * Create in 23:42
 * Created by siredvin on 24.04.14.
 */
@FunctionalInterface
public interface FBooleanVectorIndexBoolean {
    public boolean calc(IBooleanVectorProvider provider, int index);
}
