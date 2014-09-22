package com.darkempire.math.struct.function;

/**
 * Create in 18:21
 * Created by siredvin on 18.12.13.
 */
@FunctionalInterface
public interface FDoubleDouble extends IFunction<Double, Double> {
    public double calc(double x);

    @Override
    default Double impl_calc(Double x) {
        return calc(x);
    }
}
