package com.darkempire.math.struct.vector;

import com.darkempire.math.struct.LinearCalcable;

/**
 * Create in 23:42
 * Created by siredvin on 24.04.14.
 */
public interface ILinearVectorProvider<T extends LinearCalcable<T>> {
    public T get(int index);

    public int getSize();
}
