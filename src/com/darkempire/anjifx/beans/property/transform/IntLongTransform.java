package com.darkempire.anjifx.beans.property.transform;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 27.11.13
 * Time: 21:03
 * To change this template use File | Settings | File Templates.
 */
public class IntLongTransform implements ITransform<Integer, Long> {
    @Override
    public Integer value(Long value) {
        return value.intValue();
    }

    @Override
    public Long key(Integer value) {
        return value.longValue();
    }
}
