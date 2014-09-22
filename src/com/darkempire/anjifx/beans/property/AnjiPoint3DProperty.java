package com.darkempire.anjifx.beans.property;

import com.darkempire.anjifx.beans.*;
import com.darkempire.math.struct.geometry.geomerty3d.Point3D;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 13.11.13
 * Time: 16:18
 * To change this template use File | Settings | File Templates.
 */
public class AnjiPoint3DProperty extends AbstractAnjiProperty<Point3D> implements com.darkempire.anjifx.beans.IAnjiLinearAssignableValue<Point3D> {
    private Point3D value;

    public AnjiPoint3DProperty(String name) {
        super(name);
        value = new Point3D();
    }

    public AnjiPoint3DProperty(Object bean, String name) {
        super(bean, name);
        value = new Point3D();
    }

    public AnjiPoint3DProperty(String name, Point3D value) {
        super(name);
        this.value = value;
    }

    public AnjiPoint3DProperty(Object bean, String name, Point3D value) {
        super(bean, name);
        this.value = value;
    }

    @Override
    protected void set(Point3D value) {
        this.value = value;
    }

    @Override
    public BeanType getType() {
        return BeanType.POINT3D_TYPE;
    }

    @Override
    public Point3D getValue() {
        return value;
    }

    public double getX() {
        return value.getX();
    }

    public double getY() {
        return value.getY();
    }

    public double getZ() {
        return value.getZ();
    }

    public void setX(double x) {
        value.setX(x);
        setValue(value);
    }

    public void setY(double y) {
        value.setY(y);
        setValue(value);
    }

    public void setZ(double z) {
        value.setZ(z);
        setValue(value);
    }
}
