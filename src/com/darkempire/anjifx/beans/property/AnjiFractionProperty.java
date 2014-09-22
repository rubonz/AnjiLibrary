package com.darkempire.anjifx.beans.property;

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
public class AnjiFractionProperty extends AbstractAnjiProperty<Fraction> implements com.darkempire.anjifx.beans.IAnjiAssignableValue<Fraction>, IAnjiNumberValue<Fraction> {
    private Fraction value;

    public AnjiFractionProperty(String name) {
        super(name);
        value = new Fraction();
    }

    public AnjiFractionProperty(Object bean, String name) {
        super(bean, name);
        value = new Fraction();
    }

    public AnjiFractionProperty(String name, Fraction value) {
        super(name);
        this.value = value;
    }

    public AnjiFractionProperty(Object bean, String name, Fraction value) {
        super(bean, name);
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
