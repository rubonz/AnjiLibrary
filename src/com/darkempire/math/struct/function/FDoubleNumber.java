package com.darkempire.math.struct.function;

import com.darkempire.math.struct.*;

/**
 * Created by siredvin on 22.10.14.
 *
 * @author siredvin
 */
@FunctionalInterface
public interface FDoubleNumber<T extends com.darkempire.math.struct.Number<T>> extends IFunction<Double, T> {
    public T calc(double v);

    @Override
    default T impl_calc(Double x) {
        return calc(x);
    }
}
