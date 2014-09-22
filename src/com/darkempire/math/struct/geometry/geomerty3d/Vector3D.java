package com.darkempire.math.struct.geometry.geomerty3d;

import com.darkempire.math.struct.LinearCalcable;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 02.11.13
 * Time: 22:21
 * To change this template use File | Settings | File Templates.
 */
public class Vector3D implements LinearCalcable<Vector3D> {
    private double x;
    private double y;
    private double z;
    private int hash;

    //region Конструктори
    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        hash = 0;
    }

    public Vector3D() {
        this(0, 0, 0);
    }
    //endregion

    //region Геттери
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
    //endregion

    //region Сеттери
    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setPoint(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setPoint(Vector3D value) {
        this.x = value.getX();
        this.y = value.getY();
        this.z = value.getZ();
    }
    //endregion

    //region Арифметичні операції з присвоєнням
    @Override
    public Vector3D iadd(Vector3D vector3D) {
        return iadd(vector3D.getX(), vector3D.getY(), vector3D.getZ());
    }

    @Override
    public Vector3D isubtract(Vector3D vector3D) {
        return isubtract(vector3D.getX(), vector3D.getY(), vector3D.getZ());
    }

    public Vector3D iadd(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public Vector3D isubtract(double x, double y, double z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    @Override
    public Vector3D inegate() {
        x = -x;
        y = -y;
        z = -z;
        return this;
    }
    //endregion

    //region Арифметичні операції
    @Override
    public Vector3D add(Vector3D vector3D) {
        return add(vector3D.getX(), vector3D.getY(), vector3D.getZ());
    }

    @Override
    public Vector3D subtract(Vector3D vector3D) {
        return subtract(vector3D.getX(), vector3D.getY(), vector3D.getZ());
    }


    public Vector3D add(double x, double y, double z) {
        return new Vector3D(this.x + x, this.y + y, this.z + z);
    }

    public Vector3D subtract(double x, double y, double z) {
        return new Vector3D(this.x - x, this.y - y, this.z - z);
    }

    @Override
    public Vector3D negate() {
        return new Vector3D(-x, -y, -z);
    }

    public double scalarProduct(Vector3D vector3D) {
        return x * vector3D.x + y * vector3D.y + z * vector3D.z;
    }

    public Vector3D vectorProduct(Vector3D vector3D) {
        final double bx = vector3D.x;
        final double by = vector3D.y;
        final double bz = vector3D.z;
        return new Vector3D(y * bz - z * by, z * bz - x * bz, x * by - y * bx);
    }

    /**
     * Реалізує змішаний добуток векторів
     *
     * @param b - second Vector3D
     * @param c - third Vector3D
     * @return (this, b, c) = (this,[b,c])
     */
    public double tripleProduct(Vector3D b, Vector3D c) {
        return scalarProduct(b.vectorProduct(c));
    }

    //endregion

    //region Характеристики вектора
    public double magnitude() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public double distance(double x, double y, double z) {
        double dy = this.y - y;
        double dx = this.x - x;
        double dz = this.z - z;
        return Math.sqrt(dy * dy + dx * dx + dz * dz);
    }

    public double distance(Vector3D vector3D) {
        return distance(vector3D.getX(), vector3D.getY(), vector3D.getZ());
    }
    //endregion

    //region Системні функції
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof Vector3D) {
            Vector3D other = (Vector3D) obj;
            return getX() == other.getX() && getY() == other.getY() && getZ() == other.getZ();
        } else
            return false;
    }

    @Override
    public int hashCode() {
        if (hash == 0) {
            long bits = 7L;
            bits = 31L * bits + Double.doubleToLongBits(getX());
            bits = 31L * bits + Double.doubleToLongBits(getY());
            bits = 31L * bits + Double.doubleToLongBits(getZ());
            hash = (int) (bits ^ (bits >> 32));
        }
        return hash;
    }

    @Override
    public String toString() {
        return "(" + x + ";" + y + ";" + z + ")";
    }

    @Override
    public Vector3D clone() {
        return new Vector3D(x, y, z);
    }
    //endregion

    //region Поворот вектору
    public Vector3D irotateXY(double angle) {
        final double ax = getX();
        final double ay = getY();
        setX(ax * Math.cos(angle) - ay * Math.sin(angle));
        setY(ax * Math.sin(angle) + ay * Math.cos(angle));
        return this;
    }

    public Vector3D irotateXZ(double angle) {
        final double ax = getX();
        final double az = getZ();
        setX(ax * Math.cos(angle) - az * Math.sin(angle));
        setZ(ax * Math.sin(angle) + az * Math.cos(angle));
        return this;
    }

    public Vector3D irotateYZ(double angle) {
        final double ay = getY();
        final double az = getZ();
        setY(ay * Math.cos(angle) - az * Math.sin(angle));
        setZ(ay * Math.sin(angle) + az * Math.cos(angle));
        return this;
    }

    public Vector3D rotateXY(double angle) {
        final double ax = getX();
        final double ay = getY();
        return new Vector3D(ax * Math.cos(angle) - ay * Math.sin(angle), ax * Math.sin(angle) + ay * Math.cos(angle), z);
    }

    public Vector3D rotateXZ(double angle) {
        final double ax = getX();
        final double az = getZ();
        return new Vector3D(ax * Math.cos(angle) - az * Math.sin(angle), y, ax * Math.sin(angle) + az * Math.cos(angle));
    }

    public Vector3D rotateYZ(double angle) {
        final double ay = getY();
        final double az = getZ();
        return new Vector3D(x, ay * Math.cos(angle) - az * Math.sin(angle), ay * Math.sin(angle) + az * Math.cos(angle));
    }

    /**
     * Реалізує повертання вектору відразу по трьом площинам
     *
     * @param alpha - кут повороту по площині XY
     * @param beta  - кут повороту по площині XY
     * @param gamma - кут повороту по площині XY
     * @return цей саме вектор зі зміненими координатами
     */
    public Vector3D irotate(double alpha, double beta, double gamma) {
        return irotateXY(alpha).irotateYZ(beta).irotateXZ(gamma);
    }
    //endregion

    //region Розпізнання
    public static Vector3D valueOf(String value) {
        String[] split = value.split(";");
        return new Vector3D(Double.valueOf(split[0].substring(1)), Double.valueOf(split[1]), Double.valueOf(split[2].substring(0, split[2].length() - 2)));
    }
    //endregion
}
