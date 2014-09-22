package com.darkempire.math.struct.function;

/**
 * Created by siredvin on 02.07.14.
 */
public interface FSomeBoolean<K> extends IFunction<K, Boolean> {
    public boolean calc(K x);

    @Override
    default Boolean impl_calc(K x) {
        return calc(x);
    }
}
