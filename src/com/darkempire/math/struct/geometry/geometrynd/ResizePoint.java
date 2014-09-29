package com.darkempire.math.struct.geometry.geometrynd;

import com.darkempire.math.struct.LinearCalcable;
import com.darkempire.math.utils.MathUtils;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 07.11.13
 * Time: 8:04
 * To change this template use File | Settings | File Templates.
 */
public class ResizePoint implements LinearCalcable<ResizePoint> {
    private double[] coords;
    private int hash = 0;

    //region Конструктори
    public ResizePoint(double[] coords) {
        this.coords = coords;
    }

    public ResizePoint(int dimention) {
        this.coords = new double[dimention];
        for (int i = 0; i < dimention; i++) {
            this.coords[i] = 0;
        }
    }
    //endregion

    //region Сеттери
    public void setPoint(ResizePoint point) {
        if (coords.length != point.coords.length)
            coords = point.coords.clone();
        else
            System.arraycopy(point.coords, 0, coords, 0, point.coords.length);
    }

    public void setPoint(double[] coords) {
        if (this.coords.length != coords.length)
            this.coords = coords.clone();
        else
            System.arraycopy(coords, 0, coords, 0, coords.length);
    }

    public void setCoord(int index, double value) {
        this.coords[index] = value;
    }
    //endregion

    //region Геттери
    public double getCoord(int index) {
        if (index >= coords.length)
            return 0;
        return coords[index];
    }

    public int getDimension() {
        return coords.length;
    }
    //endregion

    //region Арифметичні операції
    @Override
    public ResizePoint add(ResizePoint resizePoint) {
        int max = MathUtils.max(resizePoint.coords.length, coords.length);
        double[] newcoords = new double[max];
        for (int i = 0; i < max; i++) {
            newcoords[i] = getCoord(i) + resizePoint.getCoord(i);
        }
        return new ResizePoint(newcoords);
    }

    @Override
    public ResizePoint subtract(ResizePoint resizePoint) {
        int max = MathUtils.max(resizePoint.coords.length, coords.length);
        double[] newcoords = new double[max];
        for (int i = 0; i < max; i++) {
            newcoords[i] = getCoord(i) - resizePoint.getCoord(i);
        }
        return new ResizePoint(newcoords);
    }

    @Override
    public ResizePoint negate() {
        double[] newcoords = new double[coords.length];
        for (int i = 0; i < newcoords.length; i++) {
            newcoords[i] = -coords[i];
        }
        return new ResizePoint(newcoords);
    }
    //endregion

    //region Арифметичні операції з присвоєнням
    @Override
    public ResizePoint inegate() {
        for (int i = 0; i < coords.length; i++) {
            coords[i] = -coords[i];
        }
        return this;
    }

    @Override
    public ResizePoint iadd(ResizePoint resizePoint) {
        if (coords.length < resizePoint.getDimension()) {
            double[] newcoords = new double[resizePoint.getDimension()];
            System.arraycopy(coords, 0, newcoords, 0, coords.length);
            coords = newcoords;
        }
        for (int i = 0; i < coords.length; i++) {
            coords[i] += resizePoint.getCoord(i);
        }
        return this;
    }

    @Override
    public ResizePoint isubtract(ResizePoint resizePoint) {
        if (coords.length < resizePoint.getDimension()) {
            double[] newcoords = new double[resizePoint.getDimension()];
            System.arraycopy(coords, 0, newcoords, 0, coords.length);
            coords = newcoords;
        }
        for (int i = 0; i < coords.length; i++) {
            coords[i] -= resizePoint.getCoord(i);
        }
        return this;
    }
    //endregion

    //region Системні функції
    @Override
    public ResizePoint clone() {
        return new ResizePoint(coords.clone());
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
        if (o instanceof ResizePoint) {
            ResizePoint pointND = (ResizePoint) o;
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
    public ResizePoint deepcopy() {
        return clone();
    }
}
