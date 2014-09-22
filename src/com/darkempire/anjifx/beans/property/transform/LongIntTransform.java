package com.darkempire.anjifx.beans.property.transform;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 27.11.13
 * Time: 21:03
 * To change this template use File | Settings | File Templates.
 */
public class LongIntTransform implements ITransform<Long, Integer> {
    @Override
    public Integer key(Long value) {
        return value.intValue();
    }

    @Override
    public Long value(Integer value) {
        return value.longValue();
    }
}
