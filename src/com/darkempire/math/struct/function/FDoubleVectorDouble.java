package com.darkempire.math.struct.function;

import com.darkempire.math.struct.vector.IDoubleVectorProvider;

/**
 * Created by siredvin on 02.07.14.
 */
public interface FDoubleVectorDouble extends IFunction<IDoubleVectorProvider, Double> {
    public double calc(IDoubleVectorProvider x);

    @Override
    default Double impl_calc(IDoubleVectorProvider x) {
        return calc(x);
    }
}
