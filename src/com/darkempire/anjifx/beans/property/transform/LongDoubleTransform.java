package com.darkempire.anjifx.beans.property.transform;

/**
 * Created by siredvin on 20.03.14.
 */
public class LongDoubleTransform implements ITransform<Long, Double> {
    @Override
    public Long value(Double value) {
        return value.longValue();
    }

    @Override
    public Double key(Long value) {
        return value.doubleValue();
    }
}
