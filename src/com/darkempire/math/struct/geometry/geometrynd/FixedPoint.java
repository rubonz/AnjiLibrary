package com.darkempire.math.struct.geometry.geometrynd;

import com.darkempire.math.exception.SizeMissmatchException;
import com.darkempire.math.struct.LinearCalcable;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 07.11.13
 * Time: 8:04
 * To change this template use File | Settings | File Templates.
 */
public class FixedPoint implements LinearCalcable<FixedPoint> {
    private double[] coords;
    private int hash = 0;

    //region Конструктори
    public FixedPoint(double[] coords) {
        this.coords = coords;
    }

    public FixedPoint(int dimention) {
        this.coords = new double[dimention];
        for (int i = 0; i < dimention; i++) {
            this.coords[i] = 0;
        }
    }
    //endregion

    //region Сеттери
    public void setPoint(FixedPoint point) {
        if (point.getDimension() != getDimension())
            throw new SizeMissmatchException();
        for (int i = 0; i < coords.length; i++) {
            this.coords[i] = point.getCoord(i);
        }
    }

    public void setPoint(double[] coords) {
        if (getDimension() != coords.length)
            throw new SizeMissmatchException();
        System.arraycopy(coords, 0, this.coords, 0, getDimension());
    }

    public void setCoord(int index, double value) {
        this.coords[index] = value;
    }
    //endregion

    //region Геттери
    public double getCoord(int index) {
        return coords[index];
    }

    public int getDimension() {
        return coords.length;
    }
    //endregion

    //region Арифметичні операції
    @Override
    public FixedPoint add(FixedPoint fixedPoint) {
        if (coords.length != fixedPoint.getDimension())
            throw new SizeMissmatchException();
        double[] newcoords = coords.clone();
        for (int i = 0; i < coords.length; i++) {
            newcoords[i] += fixedPoint.getCoord(i);
        }
        return new FixedPoint(newcoords);
    }

    @Override
    public FixedPoint subtract(FixedPoint fixedPoint) {
        if (coords.length != fixedPoint.getDimension())
            throw new SizeMissmatchException();
        double[] newcoords = coords.clone();
        for (int i = 0; i < coords.length; i++) {
            newcoords[i] -= fixedPoint.getCoord(i);
        }
        return new FixedPoint(newcoords);
    }

    @Override
    public FixedPoint negate() {
        double[] newcoords = new double[coords.length];
        for (int i = 0; i < newcoords.length; i++) {
            newcoords[i] = -coords[i];
        }
        return new FixedPoint(newcoords);
    }
    //endregion

    //region Арифметичні операції з присвоєнням
    @Override
    public FixedPoint inegate() {
        for (int i = 0; i < coords.length; i++) {
            coords[i] = -coords[i];
        }
        return this;
    }

    @Override
    public FixedPoint iadd(FixedPoint fixedPoint) {
        if (coords.length != fixedPoint.getDimension())
            throw new SizeMissmatchException();
        for (int i = 0; i < coords.length; i++) {
            coords[i] += fixedPoint.getCoord(i);
        }
        return this;
    }

    @Override
    public FixedPoint isubtract(FixedPoint fixedPoint) {
        if (coords.length != fixedPoint.getDimension())
            throw new SizeMissmatchException();
        for (int i = 0; i < coords.length; i++) {
            coords[i] -= fixedPoint.getCoord(i);
        }
        return this;
    }
    //endregion

    //region Системні методи
    @Override
    public FixedPoint clone() {
        return new FixedPoint(coords.clone());
    }

    @Override
    public int hashCode() {
        if (hash == 0) {
            long bits = 7L;
            for (double c : coords) {
                bits = 31L * bits + Double.doubleToLongBits(c);
            }
            hash = (int) (bits ^ (bits >> 32));
        }
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o instanceof FixedPoint) {
            FixedPoint pointND = (FixedPoint) o;
            if (pointND.getDimension() != coords.length)
                return false;
            else {
                for (int i = 0; i < coords.length; i++) {
                    if (coords[i] != pointND.getCoord(i))
                        return false;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("(");
        for (double coord : coords) {
            builder.append(coord);
            builder.append(',');
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append(')');
        return builder.toString();
    }

    //endregion

    @Override
    public FixedPoint deepcopy() {
        return clone();
    }
}
