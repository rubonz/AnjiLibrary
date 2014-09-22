package com.darkempire.anjifx.beans.value;

import com.darkempire.anjifx.annotation.AnjiFXClassConstructor;
import com.darkempire.anjifx.beans.*;
import com.darkempire.math.struct.geometry.geometrynd.FixedPoint;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 13.11.13
 * Time: 16:23
 * To change this template use File | Settings | File Templates.
 */
@AnjiFXClassConstructor(isEmptyExist = false)
public class AnjiFixedPointValue extends AbstractAnjiValue<FixedPoint> implements com.darkempire.anjifx.beans.IAnjiLinearAssignableValue<FixedPoint> {
    private FixedPoint value;

    public AnjiFixedPointValue(FixedPoint value) {
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
