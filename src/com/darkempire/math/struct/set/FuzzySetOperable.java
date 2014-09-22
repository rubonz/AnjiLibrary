package com.darkempire.math.struct.set;

import com.darkempire.math.struct.LogicCalcable;

/**
 * Create in 16:19
 * Created by siredvin on 23.04.14.
 */
public interface FuzzySetOperable<T extends FuzzySetOperable<T>> extends SetOperable<T>, LogicCalcable<T> {
    @Override
    T iand(T t);

    @Override
    T ior(T t);

    @Override
    T ixor(T t);

    @Override
    default T inot() {
        return this.icomplement();
    }

    @Override
    T and(T t);

    @Override
    T or(T t);

    @Override
    T xor(T t);

    @Override
    default T not() {
        return this.complement();
    }

}
