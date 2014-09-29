package com.darkempire.math.struct.geometry.geomerty2d;

import com.darkempire.math.struct.LinearCalcable;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 02.11.13
 * Time: 22:03
 * To change this template use File | Settings | File Templates.
 */
public class Vector2D implements LinearCalcable<Vector2D> {
    private double x;
    private double y;
    private int hash;

    //region Конструктори
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
        hash = 0;
    }

    public Vector2D() {
        this(0, 0);
    }
    //endregion

    //region Геттери
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    //endregion

    //region Сеттери
    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setVector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setVector(Vector2D value) {
        this.x = value.getX();
        this.y = value.getY();
    }
    //endregion

    //region Арифметичні операції
    @Override
    public Vector2D add(Vector2D vector2D) {
        return new Vector2D(x + vector2D.getX(), y + vector2D.getY());
    }

    @Override
    public Vector2D subtract(Vector2D vector2D) {
        return new Vector2D(x - vector2D.getX(), y - vector2D.getY());
    }

    public Vector2D add(double x, double y) {
        return new Vector2D(this.x + x, this.y + y);
    }

    public Vector2D subtract(double x, double y) {
        return new Vector2D(this.x - x, this.y - y);
    }

    @Override
    public Vector2D negate() {
        return new Vector2D(-x, -y);
    }

    public double multiply(Vector2D vector2D) {
        return multiply(vector2D.getX(), vector2D.getY());
    }

    public double multiply(double x, double y) {
        return x * getX() + y * getY();
    }

    public double scalarProduct(Vector2D vector2D) {
        return x * vector2D.x + y * vector2D.y;
    }
    //endregion

    //region Арифметичні операції з присвоєнням
    @Override
    public Vector2D inegate() {
        x = -x;
        y = -y;
        return this;
    }

    @Override
    public Vector2D iadd(Vector2D vector2D) {
        return iadd(vector2D.getX(), vector2D.getY());
    }

    @Override
    public Vector2D isubtract(Vector2D vector2D) {
        return isubtract(vector2D.getX(), vector2D.getY());
    }

    public Vector2D iadd(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public Vector2D isubtract(double x, double y) {
        this.x -= x;
        this.y -= y;
        return this;
    }
    //endregion

    //region Характеристики вектора
    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    public double distance(double x, double y) {
        double dy = this.y - y;
        double dx = this.x - x;
        return Math.sqrt(dy * dy + dx * dx);
    }

    public double distance(Vector2D vector2D) {
        return distance(vector2D.getX(), vector2D.getY());
    }

    public double angle(Vector2D vector2D) {
        return angle(vector2D.getX(), vector2D.getY());
    }

    public double angle(double x, double y) {
        final double ax = getX();
        final double ay = getY();

        final double delta = (ax * x + ay * y) / Math.sqrt(
                (ax * ax + ay * ay) * (x * x + y * y));

        if (delta > 1.0) {
            return 0.0;
        }
        if (delta < -1.0) {
            return 180.0;
        }

        return Math.acos(delta);
    }

    //endregion

    //region Системні функції
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof Vector2D) {
            Vector2D other = (Vector2D) obj;
            return getX() == other.getX() && getY() == other.getY();
        } else
            return false;
    }

    @Override
    public int hashCode() {
        if (hash == 0) {
            long bits = 7L;
            bits = 31L * bits + Double.doubleToLongBits(getX());
            bits = 31L * bits + Double.doubleToLongBits(getY());
            hash = (int) (bits ^ (bits >> 32));
        }
        return hash;
    }

    @Override
    public String toString() {
        return "(" + x + ";" + y + ")";
    }

    @Override
    public Vector2D clone() {
        return new Vector2D(x, y);
    }
    //endregion

    //region Поворот вектору
    public Vector2D irotate(double angle) {
        final double ax = getX();
        final double ay = getY();
        setX(ax * Math.cos(angle) - ay * Math.sin(angle));
        setY(ax * Math.sin(angle) + ay * Math.cos(angle));
        return this;
    }

    public Vector2D rotate(double angle) {
        final double ax = getX();
        final double ay = getY();
        return new Vector2D(ax * Math.cos(angle) - ay * Math.sin(angle), ax * Math.sin(angle) + ay * Math.cos(angle));
    }
    //endregion

    //region Розпізнання
    public static Vector2D valueOf(String value) {
        String[] split = value.split(";");
        return new Vector2D(Double.valueOf(split[0].substring(1)), Double.valueOf(split[1].substring(0, split[1].length() - 2)));
    }
    //endregion

    @Override
    public Vector2D deepcopy() {
        return clone();
    }
}
