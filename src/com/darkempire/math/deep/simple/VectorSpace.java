package com.darkempire.math.deep.simple;

import com.darkempire.math.deep.FieldElement;
import com.darkempire.math.deep.LinearSpace;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 02.11.13
 * Time: 22:49
 * To change this template use File | Settings | File Templates.
 */
public class VectorSpace<K extends FieldElement<K>> implements LinearSpace<VectorSpaceElement<K>, K> {
    private VectorSpaceElement<K> zero = new VectorSpaceElement<>();

    @Override
    public VectorSpaceElement<K> getZero() {
        return zero;
    }

    @Override
    public VectorSpaceElement<K> getNewZero() {
        return zero.clone();
    }
}
