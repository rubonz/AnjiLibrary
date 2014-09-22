package com.darkempire.math.struct.function.interfaces;

import com.darkempire.math.struct.vector.IDoubleVectorProvider;

/**
 * Create in 23:42
 * Created by siredvin on 24.04.14.
 */
@FunctionalInterface
public interface FDoubleVectorIndexDouble {
    public double calc(IDoubleVectorProvider provider, int index);
}
