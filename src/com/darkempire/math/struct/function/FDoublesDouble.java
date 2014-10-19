package com.darkempire.math.struct.function;

import java.util.List;

/**
 * Created by siredvin on 20.10.14.
 *
 * @author siredvin
 */
@FunctionalInterface
public interface FDoublesDouble extends IFunction<double[], Double> {

    public double calc(double... doubles);

    @Override
    default Double impl_calc(double... doubles) {
        return calc(doubles);
    }
}
