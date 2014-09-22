package com.darkempire.anjifx.beans.value;

import com.darkempire.anjifx.annotation.AnjiFXClassConstructor;
import com.darkempire.anjifx.beans.BeanType;
import com.darkempire.math.struct.vector.DoubleVector;
import com.darkempire.math.struct.vector.DoubleFixedVector;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 13.11.13
 * Time: 16:42
 * To change this template use File | Settings | File Templates.
 */
@AnjiFXClassConstructor(isEmptyExist = false)
public class AnjiDoubleVectorValue extends AbstractAnjiValue<DoubleVector> implements com.darkempire.anjifx.beans.IAnjiLinearAssignableValue<DoubleVector> {
    private DoubleVector value;

    public AnjiDoubleVectorValue(DoubleFixedVector vector) {
        this.value = vector;
    }

    @Override
    protected void set(DoubleVector value) {
        this.value = value;
    }

    @Override
    public BeanType getType() {
        return BeanType.DOUBLEVECTOR_TYPE;
    }

    @Override
    public DoubleVector getValue() {
        return value;
    }

    public int getDimension() {
        return value.getSize();
    }

    public double getCoord(int i) {
        return value.get(i);
    }

    public void irotate(int i, int j, double angle) {
        value.irotate(i, j, angle);
        setValue(value);
    }
}
