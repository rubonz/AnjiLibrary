package com.darkempire.math.struct.complex;

import com.darkempire.math.struct.Calcable;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 13.11.13
 * Time: 15:49
 * To change this template use File | Settings | File Templates.
 */
public class Complex implements Calcable<Complex> {
    private double x;
    private double y;
    private int hash;

    //region Конструктори
    public Complex() {
        x = y = 0;
        hash = 0;
    }

    public Complex(double x) {
        this.x = x;
        y = 0;
        hash = 0;
    }

    public Complex(double x, double y) {
        this.x = x;
        this.y = y;
        hash = 0;
    }
    //endregion

    //region Геттери
    public double getRe() {
        return x;
    }

    public double getIm() {
        return y;
    }

    public double getArg() {
        return Math.acos(x / getAbs());
    }

    public double getAbs() {
        return Math.sqrt(x * x + y * y);
    }
    //endregion

    //region Арифметичні операції з присвоєння
    public Complex iconjugation() {
        y = -y;
        return this;
    }


    @Override
    public Complex imultiply(Complex complex) {
        final double nx = x * complex.x - y * complex.y;
        y = x * complex.y + y * complex.x;
        x = nx;
        return this;
    }

    @Override
    public Complex idivide(Complex complex) {
        final double abs = complex.getAbs();
        final double nx = x * complex.x + y * complex.y;
        y = y * complex.x - x * complex.y;
        x = nx / abs;
        y /= abs;
        return this;
    }

    @Override
    public Complex iinverse() {
        final double abs = getAbs();
        x /= abs;
        y = -y / abs;
        return this;
    }

    @Override
    public Complex inegate() {
        x = -x;
        y = -y;
        return this;
    }

    @Override
    public Complex iadd(Complex complex) {
        x += complex.x;
        y += complex.y;
        return this;
    }

    @Override
    public Complex isubtract(Complex complex) {
        x -= complex.x;
        y -= complex.y;
        return this;
    }
    //endregion

    //region Арифметичні операції
    public Complex conjugation() {
        return new Complex(x, -y);
    }

    @Override
    public Complex multiply(Complex complex) {
        return new Complex(x * complex.x - y * complex.y, x * complex.y + y * complex.x);
    }

    @Override
    public Complex divide(Complex complex) {
        final double abs = complex.getAbs();
        return new Complex((x * complex.x + y * complex.y) / abs, (y * complex.x - x * complex.y) / abs);
    }

    @Override
    public Complex inverse() {
        final double abs = getAbs();
        return new Complex(x / abs, -y / abs);
    }

    @Override
    public Complex add(Complex complex) {
        return new Complex(x + complex.x, y + complex.y);
    }

    @Override
    public Complex subtract(Complex complex) {
        return new Complex(x - complex.x, y - complex.y);
    }

    @Override
    public Complex negate() {
        return new Complex(-x, -y);
    }
    //endregion

    //region Перевантаження звичайних методів
    @Override
    public Complex clone() {
        return new Complex(x, y);
    }

    @Override
    public int hashCode() {
        if (hash == 0) {
            long bits = 7L;
            bits = 31L * bits + Double.doubleToLongBits(x);
            bits = 31L * bits + Double.doubleToLongBits(y);
            hash = (int) (bits ^ (bits >> 32));
        }
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o instanceof Complex) {
            Complex c = (Complex) o;
            return c.x == x && c.y == y;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (x != 0) {
            stringBuilder.append(x);
        }
        if (y != 0) {
            stringBuilder.append(y > 0 ? '+' : '-');
            stringBuilder.append(Math.abs(y));
            stringBuilder.append('i');
        }
        if (stringBuilder.length() == 0)
            stringBuilder.append(0);
        return stringBuilder.toString();
    }
    //endregion

    //region Розпізнавання
    public static Complex valueOf(String v) {
        Complex complex = new Complex();
        int sum = v.lastIndexOf('+');
        int sub = v.lastIndexOf('-');
        if (sum == sub) {
            if (v.contains("i")) {
                complex.y = Double.valueOf(v.replace('i', 'd'));
            } else {
                complex.x = Double.valueOf(v);
            }
        } else {
            if (sub > 0) {
                String[] result = v.split("-");
                complex.x = Double.valueOf(result[0]);
                complex.y = -Double.valueOf(result[1].replace('i', 'd'));
            } else {
                String[] result = v.split("\\+");
                complex.x = Double.valueOf(result[0]);
                complex.y = Double.valueOf(result[1].replace('i', 'd'));
            }
        }
        return complex;
    }
    //endregion

    @Override
    public Complex deepcopy() {
        return clone();
    }
}
