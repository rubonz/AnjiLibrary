package com.darkempire.anjifx.beans.value;

import com.darkempire.anjifx.beans.*;
import com.darkempire.math.struct.complex.Complex;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 13.11.13
 * Time: 16:16
 * To change this template use File | Settings | File Templates.
 */
public class AnjiComplexValue extends AbstractAnjiValue<Complex> implements com.darkempire.anjifx.beans.IAnjiAssignableValue<Complex> {
    private Complex value;

    public AnjiComplexValue() {
        value = new Complex();
    }

    public AnjiComplexValue(Complex value) {
        this.value = value;
    }

    @Override
    protected void set(Complex value) {
        this.value = value;
    }

    @Override
    public BeanType getType() {
        return BeanType.COMPLEX_TYPE;
    }

    @Override
    public Complex getValue() {
        return value;
    }

    public double getRe() {
        return value.getRe();
    }

    public double getIm() {
        return value.getIm();
    }
}
