package com.darkempire.math.struct.function;

/**
 * Create in 18:21
 * Created by siredvin on 18.12.13.
 */
@FunctionalInterface
public interface FBooleanBoolean extends IFunction<Boolean, Boolean> {
    public boolean calc(boolean x);

    @Override
    default Boolean impl_calc(Boolean x) {
        return calc(x);
    }
}
