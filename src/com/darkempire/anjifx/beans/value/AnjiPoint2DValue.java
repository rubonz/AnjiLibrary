package com.darkempire.anjifx.beans.value;

import com.darkempire.anjifx.beans.*;
import com.darkempire.math.struct.geometry.geomerty2d.Point2D;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 11.11.13
 * Time: 18:33
 * To change this template use File | Settings | File Templates.
 */
public class AnjiPoint2DValue extends AbstractAnjiValue<Point2D> implements com.darkempire.anjifx.beans.IAnjiLinearAssignableValue<Point2D> {
    private Point2D value;

    public AnjiPoint2DValue() {
        value = new Point2D();
    }

    public AnjiPoint2DValue(Point2D value) {
        this.value = value;
    }

    @Override
    protected void set(Point2D value) {
        this.value.setPoint(value);
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

    public void setValue(double x, double y) {
        Point2D temp = new Point2D(x, y);
        setValue(temp);
    }

    @Override
    public Point2D getValue() {
        return value;
    }

    @Override
    public BeanType getType() {
        return BeanType.POINT2D_TYPE;
    }
}
