package com.darkempire.math.struct.function.polynomial;

/**
 * Created by siredvin on 14.10.14.
 *
 * @author siredvin
 */
public interface IInteratedPolynomialBuilder<T extends ADoublePolynomial> {
    public int getMaxIndex();

    public T calcNext();

    public T calcIndexed(int index);
}
