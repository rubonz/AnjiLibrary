package com.darkempire.anjifx.beans.value;

import com.darkempire.anjifx.beans.*;
import com.darkempire.anjifx.beans.IAnjiNumberValue;
import com.darkempire.math.struct.number.Fraction;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 11.11.13
 * Time: 17:21
 * To change this template use File | Settings | File Templates.
 */
public class AnjiFractionValue extends AbstractAnjiValue<Fraction> implements com.darkempire.anjifx.beans.IAnjiAssignableValue<Fraction>, IAnjiNumberValue<Fraction> {
    private Fraction value;

    public AnjiFractionValue() {
        value = new Fraction();
    }

    public AnjiFractionValue(Fraction value) {
        this.value = value;
    }

    @Override
    protected void set(Fraction value) {
        this.value = value;
    }

    @Override
    public Fraction getValue() {
        return value;
    }

    @Override
    public BeanType getType() {
        return BeanType.FRACTION_TYPE;
    }
}
