package com.darkempire.anjifx.beans.property;

import com.darkempire.anjifx.beans.*;
import com.darkempire.math.struct.geometry.geomerty3d.Vector3D;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 13.11.13
 * Time: 16:19
 * To change this template use File | Settings | File Templates.
 */
public class AnjiVector3DProperty extends AbstractAnjiProperty<Vector3D> implements com.darkempire.anjifx.beans.IAnjiLinearAssignableValue<Vector3D> {
    private Vector3D value;

    public AnjiVector3DProperty(String name) {
        super(name);
        value = new Vector3D();
    }

    public AnjiVector3DProperty(Object bean, String name) {
        super(bean, name);
        value = new Vector3D();
    }

    public AnjiVector3DProperty(String name, Vector3D value) {
        super(name);
        this.value = value;
    }

    public AnjiVector3DProperty(Object bean, String name, Vector3D value) {
        super(bean, name);
        this.value = value;
    }

    @Override
    protected void set(Vector3D value) {
        this.value = value;
    }

    @Override
    public BeanType getType() {
        return BeanType.VECTOR3D_TYPE;
    }

    @Override
    public Vector3D getValue() {
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

    public void irotateXY(double angle) {
        value.irotateXY(angle);
        setValue(value);
    }

    public void irotateXZ(double angle) {
        value.irotateXZ(angle);
        setValue(value);
    }

    public void irotateYZ(double angle) {
        value.irotateYZ(angle);
        setValue(value);
    }

    public void irotate(double alpha, double beta, double gamma) {
        value.irotate(alpha, beta, gamma);
        setValue(value);
    }
}
