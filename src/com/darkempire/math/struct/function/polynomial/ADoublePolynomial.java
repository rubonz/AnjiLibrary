package com.darkempire.math.struct.function.polynomial;

import com.darkempire.anji.annotation.AnjiStandartize;
import com.darkempire.anji.document.tex.ITeXObject;
import com.darkempire.math.struct.vector.IDoubleVectorProvider;

/**
 * Created by siredvin on 13.10.14.
 *
 * @author siredvin
 */
@AnjiStandartize
public abstract class ADoublePolynomial implements IDoubleVectorProvider, ITeXObject {
    public abstract int getDimentions();

    public abstract int getMaxPower();

    public abstract void prop(double lambda);
}
