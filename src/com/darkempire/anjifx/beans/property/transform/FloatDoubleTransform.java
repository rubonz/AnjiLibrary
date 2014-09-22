package com.darkempire.anjifx.beans.property.transform;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 27.11.13
 * Time: 21:03
 * To change this template use File | Settings | File Templates.
 */
public class FloatDoubleTransform implements ITransform<Float, Double> {
    @Override
    public Float value(Double value) {
        return value.floatValue();
    }

    @Override
    public Double key(Float value) {
        return value.doubleValue();
    }
}
