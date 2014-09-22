package com.darkempire.math.struct.geometry.geomerty2d;

import com.darkempire.math.struct.LinearCalcable;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 31.10.13
 * Time: 17:57
 * To change this template use File | Settings | File Templates.
 */
public class Point2D implements LinearCalcable<Point2D> {
    private double x;
    private double y;
    private int hash;

    //region Конструктори
    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
        hash = 0;
    }

    public Point2D() {
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

    public void setPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setPoint(Point2D value) {
        this.x = value.getX();
        this.y = value.getY();
    }
    //endregion

    //region Арифметичні операції
    @Override
    public Point2D add(Point2D point2D) {
        return new Point2D(x + point2D.getX(), y + point2D.getY());
    }

    @Override
    public Point2D subtract(Point2D point2D) {
        return new Point2D(x - point2D.getX(), y - point2D.getY());
    }

    public Point2D add(double x, double y) {
        return new Point2D(this.x + x, this.y + y);
    }

    public Point2D subtract(double x, double y) {
        return new Point2D(this.x - x, this.y - y);
    }

    @Override
    public Point2D negate() {
        return new Point2D(-x, -y);
    }
    //endregion

    //region Арифметичні операції з присвоєнням
    @Override
    public Point2D inegate() {
        x = -x;
        y = -y;
        return this;
    }

    @Override
    public Point2D iadd(Point2D point2D) {
        return iadd(point2D.getX(), point2D.getY());
    }

    @Override
    public Point2D isubtract(Point2D point2D) {
        return isubtract(point2D.getX(), point2D.getY());
    }


    public Point2D iadd(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public Point2D isubtract(double x, double y) {
        this.x -= x;
        this.y -= y;
        return this;
    }
    //endregion

    //region Характеристики точки
    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    public double distance(double x, double y) {
        double dy = this.y - y;
        double dx = this.x - x;
        return Math.sqrt(dy * dy + dx * dx);
    }

    public double distance(Point2D point2D) {
        return distance(point2D.getX(), point2D.getY());
    }
    //endregion

    //region Системні функції
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof Point2D) {
            Point2D other = (Point2D) obj;
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
    public Point2D clone() {
        return new Point2D(x, y);
    }
    //endregion

    //region Розпізнання
    public static Point2D valueOf(String value) {
        String[] split = value.split(";");
        return new Point2D(Double.parseDouble(split[0].substring(1)), Double.parseDouble(split[1].substring(0, split[1].length() - 2)));
    }
    //endregion
}
