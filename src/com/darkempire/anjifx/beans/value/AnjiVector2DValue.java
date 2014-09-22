package com.darkempire.anjifx.beans.value;

import com.darkempire.anjifx.annotation.AnjiFXValue;
import com.darkempire.anjifx.beans.*;
import com.darkempire.math.struct.geometry.geomerty2d.Vector2D;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 11.11.13
 * Time: 18:39
 * To change this template use File | Settings | File Templates.
 */
@AnjiFXValue
public class AnjiVector2DValue extends AbstractAnjiValue<Vector2D> implements com.darkempire.anjifx.beans.IAnjiLinearAssignableValue<Vector2D> {
    private Vector2D value;

    public AnjiVector2DValue() {
        value = new Vector2D();
    }

    public AnjiVector2DValue(Vector2D value) {
        this.value = value;
    }

    @Override
    protected void set(Vector2D value) {
        this.value = value;
    }

    public double getX() {
        return value.getX();
    }

    public double getY() {
        return value.getY();
    }

    public void setX(double x) {
        value.setX(x);
        setValue(value);
    }

    public void setY(double y) {
        value.setY(y);
        setValue(value);
    }

    public void irotate(double angle) {
        value.irotate(angle);
        setValue(value);
    }

    public void setValue(double x, double y) {
        Vector2D temp = new Vector2D(x, y);
        setValue(temp);
    }

    @Override
    public Vector2D getValue() {
        return value;
    }

    @Override
    public BeanType getType() {
        return BeanType.VECTOR2D_TYPE;
    }
}
