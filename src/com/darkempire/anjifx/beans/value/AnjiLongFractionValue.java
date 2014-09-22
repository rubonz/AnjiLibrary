package com.darkempire.anjifx.beans.value;

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
public class AnjiLongFractionValue extends AbstractAnjiValue<LongFraction> implements com.darkempire.anjifx.beans.IAnjiAssignableValue<LongFraction>, IAnjiNumberValue<LongFraction> {
    private LongFraction value;

    public AnjiLongFractionValue() {
        value = new LongFraction();
    }

    public AnjiLongFractionValue(LongFraction value) {
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
