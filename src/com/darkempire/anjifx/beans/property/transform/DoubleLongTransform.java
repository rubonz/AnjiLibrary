package com.darkempire.anjifx.beans.property.transform;

/**
 * Created by siredvin on 20.03.14.
 */
public class DoubleLongTransform implements ITransform<Double, Long> {
    @Override
    public Double value(Long value) {
        return value.doubleValue();
    }

    @Override
    public Long key(Double value) {
        return value.longValue();
    }
}
