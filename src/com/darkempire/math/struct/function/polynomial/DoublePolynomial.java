package com.darkempire.math.struct.function.polynomial;

import com.darkempire.anji.annotation.AnjiStandartize;
import com.darkempire.anji.document.tex.ITeXObject;
import com.darkempire.math.struct.function.FDoubleDouble;
import com.darkempire.math.struct.vector.IDoubleVectorProvider;

/**
 * Created by siredvin on 13.10.14.
 *
 * @author siredvin
 */
@AnjiStandartize
public abstract class DoublePolynomial implements ITeXObject, FDoubleDouble {
    public abstract int getMaxPower();

    public abstract int getMinPower();
    //TODO:public abstract ArrayDoublePolynomial toRawPolynomial();
}
