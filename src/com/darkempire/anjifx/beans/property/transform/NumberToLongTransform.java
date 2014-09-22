package com.darkempire.anjifx.beans.property.transform;

/**
 * Created by siredvin on 20.03.14.
 */
public class NumberToLongTransform implements ITransform<Number, Long> {
    @Override
    public Number value(Long value) {
        return value;
    }

    @Override
    public Long key(Number value) {
        return value.longValue();
    }
}
