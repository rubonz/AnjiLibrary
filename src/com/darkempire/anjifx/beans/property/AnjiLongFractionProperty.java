package com.darkempire.anjifx.beans.property;

import com.darkempire.anjifx.beans.*;
import com.darkempire.anjifx.beans.IAnjiNumberValue;
import com.darkempire.math.struct.number.LongFraction;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 11.11.13
 * Time: 17:26
 * To change this template use File | Settings | File Templates.
 */
public class AnjiLongFractionProperty extends AbstractAnjiProperty<LongFraction> implements com.darkempire.anjifx.beans.IAnjiAssignableValue<LongFraction>, IAnjiNumberValue<LongFraction> {
    private LongFraction value;

    public AnjiLongFractionProperty(String name) {
        super(name);
        value = new LongFraction();
    }

    public AnjiLongFractionProperty(Object bean, String name) {
        super(bean, name);
        value = new LongFraction();
    }

    public AnjiLongFractionProperty(String name, LongFraction value) {
        super(name);
        this.value = value;
    }

    public AnjiLongFractionProperty(Object bean, String name, LongFraction value) {
        super(bean, name);
        this.value = value;
    }

    @Override
    protected void set(LongFraction value) {
        this.value = value;
    }

    @Override
    public LongFraction getValue() {
        return value;
    }

    @Override
    public BeanType getType() {
        return BeanType.LONGFRACTION_TYPE;
    }
}
