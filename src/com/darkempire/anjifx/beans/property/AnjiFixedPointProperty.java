package com.darkempire.anjifx.beans.property;

import com.darkempire.anjifx.beans.BeanType;
import com.darkempire.math.struct.geometry.geometrynd.FixedPoint;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 13.11.13
 * Time: 16:23
 * To change this template use File | Settings | File Templates.
 */
public class AnjiFixedPointProperty extends AbstractAnjiProperty<FixedPoint> implements com.darkempire.anjifx.beans.IAnjiLinearAssignableValue<FixedPoint> {
    private FixedPoint value;

    public AnjiFixedPointProperty(String name, FixedPoint value) {
        super(name);
        this.value = value;
    }

    public AnjiFixedPointProperty(Object bean, String name, FixedPoint value) {
        super(bean, name);
        this.value = value;
    }

    @Override
    protected void set(FixedPoint value) {
        this.value = value;
    }

    @Override
    public BeanType getType() {
        return BeanType.FIXEDPOINT_TYPE;
    }

    @Override
    public FixedPoint getValue() {
        return value;
    }

    public int getDimension() {
        return value.getDimension();
    }

    public double getCoord(int i) {
        return value.getCoord(i);
    }
}
