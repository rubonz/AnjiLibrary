package com.darkempire.math.struct.geometry.geomerty3d;

import com.darkempire.math.struct.LinearCalcable;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 02.11.13
 * Time: 22:21
 * To change this template use File | Settings | File Templates.
 */
public class Point3D implements LinearCalcable<Point3D> {
    private double x;
    private double y;
    private double z;
    private int hash;

    //region Конструктори
    public Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        hash = 0;
    }

    public Point3D() {
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

    public void setPoint(Point3D value) {
        this.x = value.getX();
        this.y = value.getY();
        this.z = value.getZ();
    }
    //endregion

    //region Арифметичні операції з присвоєнням
    @Override
    public Point3D iadd(Point3D point3D) {
        return iadd(point3D.getX(), point3D.getY(), point3D.getZ());
    }

    @Override
    public Point3D isubtract(Point3D point3D) {
        return isubtract(point3D.getX(), point3D.getY(), point3D.getZ());
    }


    @Override
    public Point3D inegate() {
        x = -x;
        y = -y;
        z = -z;
        return this;
    }

    public Point3D iadd(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public Point3D isubtract(double x, double y, double z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }
    //endregion

    //region Арифметичні операції
    @Override
    public Point3D add(Point3D point3D) {
        return add(point3D.getX(), point3D.getY(), point3D.getZ());
    }

    @Override
    public Point3D subtract(Point3D point3D) {
        return subtract(point3D.getX(), point3D.getY(), point3D.getZ());
    }

    public Point3D add(double x, double y, double z) {
        return new Point3D(this.x + x, this.y + y, this.z + z);
    }

    public Point3D subtract(double x, double y, double z) {
        return new Point3D(this.x - x, this.y - y, this.z - z);
    }

    @Override
    public Point3D negate() {
        return new Point3D(-x, -y, -z);
    }
    //endregion

    //region Характеристики точки
    public double magnitude() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public double distance(double x, double y, double z) {
        double dy = this.y - y;
        double dx = this.x - x;
        double dz = this.z - z;
        return Math.sqrt(dy * dy + dx * dx + dz * dz);
    }

    public double distance(Point3D point3D) {
        return distance(point3D.getX(), point3D.getY(), point3D.getZ());
    }
    //endregion

    //region Системні функції
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof Point3D) {
            Point3D other = (Point3D) obj;
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
    public Point3D clone() {
        return new Point3D(x, y, z);
    }
    //endregion

    //region Розпізнання
    public static Point3D valueOf(String value) {
        String[] split = value.split(";");
        return new Point3D(Double.valueOf(split[0].substring(1)), Double.valueOf(split[1]), Double.valueOf(split[2].substring(0, split[2].length() - 2)));
    }
    //endregion

    @Override
    public Point3D deepcopy() {
        return clone();
    }
}
