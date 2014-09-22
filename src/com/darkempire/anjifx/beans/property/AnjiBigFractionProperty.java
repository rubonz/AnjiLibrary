package com.darkempire.anjifx.beans.property;

import com.darkempire.anjifx.beans.*;
import com.darkempire.math.struct.number.BigFraction;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 11.11.13
 * Time: 17:26
 * To change this template use File | Settings | File Templates.
 */
public class AnjiBigFractionProperty extends AbstractAnjiProperty<BigFraction> implements com.darkempire.anjifx.beans.IAnjiNumberValue<BigFraction> {
    private BigFraction value;

    public AnjiBigFractionProperty(String name) {
        super(name);
        value = new BigFraction();
    }

    public AnjiBigFractionProperty(Object bean, String name) {
        super(bean, name);
        value = new BigFraction();
    }

    public AnjiBigFractionProperty(String name, BigFraction value) {
        super(name);
        this.value = value;
    }

    public AnjiBigFractionProperty(Object bean, String name, BigFraction value) {
        super(bean, name);
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
