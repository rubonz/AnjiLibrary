package com.darkempire.math.struct.function.polynomial;

/**
 * Created by siredvin on 14.10.14.
 *
 * @author siredvin
 */
public interface IIteratedPolynomialBuilder<T extends DoublePolynomial> {
    public int getMaxIndex();

    public T calcNext();

    public T calcIndexed(int index);

    public void clearCache();
}
