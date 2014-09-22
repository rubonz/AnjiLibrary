package com.darkempire.anjifx.beans.value;

import com.darkempire.anjifx.beans.*;
import com.darkempire.math.struct.number.BigFraction;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 11.11.13
 * Time: 17:26
 * To change this template use File | Settings | File Templates.
 */
public class AnjiBigFractionValue extends AbstractAnjiValue<BigFraction> implements com.darkempire.anjifx.beans.IAnjiNumberValue<BigFraction> {
    private BigFraction value;

    public AnjiBigFractionValue() {
        value = new BigFraction();
    }

    public AnjiBigFractionValue(BigFraction value) {
        this.value = value;
    }

    @Override
    protected void set(BigFraction value) {
        this.value = value;
    }

    @Override
    public BigFraction getValue() {
        return value;
    }

    @Override
    public BeanType getType() {
        return BeanType.BIGFRACTION_TYPE;
    }
}
