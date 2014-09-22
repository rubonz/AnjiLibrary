package com.darkempire.math.struct.function;

/**
 * Create in 18:37
 * Created by siredvin on 18.12.13.
 */
public interface FIndexDouble extends IFunction<Integer, Double> {
    public double calc(int x);

    @Override
    default Double impl_calc(Integer x) {
        return calc(x);
    }
}
