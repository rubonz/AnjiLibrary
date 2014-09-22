package com.darkempire.math.struct.number;

import com.darkempire.math.utils.MathUtils;

import java.lang.Number;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 06.11.13
 * Time: 8:06
 * To change this template use File | Settings | File Templates.
 */
public class LongFraction extends Number implements com.darkempire.math.struct.Number<LongFraction> {
    private long denominator;
    private long numerator;

    //region Конструктори
    public LongFraction() {
        denominator = 1;
        numerator = 0;
    }

    public LongFraction(double value) {
        if (value == 0.0) {
            denominator = 1;
            numerator = 0;
        } else {
            long bits = Double.doubleToLongBits(value);

            long sign = bits >>> 63;
            long exponent = ((bits >>> 52) ^ (sign << 11)) - 1023;
            long fraction = bits << 12; // bits are "reversed" but that's not a problem
            if (exponent == 0) {
                numerator = Math.round(value);
                denominator = 1l;
            } else {
                long a = 1L;
                long b = 1L;

                for (int i = 63; i >= 12; i--) {
                    a = a * 2 + ((fraction >>> i) & 1);
                    b *= 2;
                }

                if (exponent > 0)
                    a *= 1 << exponent;
                else
                    b *= 1 << -exponent;

                if (sign == 1)
                    a *= -1;
                numerator = a;
                denominator = b;
                simplify();
            }
        }
    }

    public LongFraction(long numerator) {
        denominator = 1;
        this.numerator = numerator;
    }

    public LongFraction(long numerator, long denominator) {
        this.denominator = denominator;
        this.numerator = numerator;
        simplify();
    }
    //endregion

    //region Арифметичні операції з присвоєнням
    @Override
    public LongFraction isubtract(LongFraction f1) {
        long lcm = MathUtils.lcm(denominator, f1.denominator);
        long a1 = lcm / denominator;
        long a2 = lcm / f1.denominator;
        denominator = lcm;
        numerator = numerator * a1 - f1.numerator * a2;
        simplify();
        return this;
    }

    @Override
    public LongFraction iadd(LongFraction f1) {
        long lcm = MathUtils.lcm(denominator, f1.denominator);
        long a1 = lcm / denominator;
        long a2 = lcm / f1.denominator;
        denominator = lcm;
        numerator = numerator * a1 + f1.numerator * a2;
        simplify();
        return this;
    }

    @Override
    public LongFraction imultiply(LongFraction f1) {
        numerator *= f1.numerator;
        denominator *= f1.denominator;
        simplify();
        return this;
    }

    @Override
    public LongFraction idivide(LongFraction f1) {
        numerator *= f1.denominator;
        denominator *= f1.numerator;
        simplify();
        return this;
    }

    @Override
    public LongFraction inegate() {
        numerator = -numerator;
        return this;
    }

    @Override
    public LongFraction iinverse() {
        long t = numerator;
        numerator = denominator;
        denominator = t;
        simplify();
        return this;
    }
    //endregion

    //region Арифметичні операції
    @Override
    public LongFraction subtract(LongFraction f1) {
        long lcm = MathUtils.lcm(denominator, f1.denominator);
        long a1 = lcm / denominator;
        long a2 = lcm / f1.denominator;
        return new LongFraction(numerator * a1 - f1.numerator * a2, lcm);
    }

    @Override
    public LongFraction add(LongFraction f1) {
        long lcm = MathUtils.lcm(denominator, f1.denominator);
        long a1 = lcm / denominator;
        long a2 = lcm / f1.denominator;
        return new LongFraction(numerator * a1 + f1.numerator * a2, lcm);
    }

    @Override
    public LongFraction multiply(LongFraction f1) {
        return new LongFraction(numerator * f1.numerator, denominator * f1.denominator);
    }

    @Override
    public LongFraction divide(LongFraction f1) {
        return new LongFraction(numerator * f1.denominator, denominator * f1.numerator);
    }

    @Override
    public LongFraction negate() {
        return new LongFraction(-numerator, denominator);
    }

    @Override
    public LongFraction inverse() {
        return new LongFraction(denominator, numerator);
    }
    //endregion

    //region Геттери
    public long getDenominator() {
        return denominator;
    }

    public long getNumerator() {
        return numerator;
    }

    @Override
    public LongFraction getZero() {
        return new LongFraction(0);
    }

    @Override
    public LongFraction getOne() {
        return new LongFraction(1);
    }
    //endregion

    //region Отримання числових значень
    @Override
    public int intValue() {
        return (int) longValue();
    }

    @Override
    public long longValue() {
        return numerator / denominator;
    }

    @Override
    public float floatValue() {
        return (float) numerator / (float) denominator;
    }

    @Override
    public double doubleValue() {
        return (double) numerator / (double) denominator;
    }
    //endregion

    @Override
    public LongFraction deepcopy() {
        return clone();
    }

    //region Системні функції
    @Override
    public String toString() {
        if (denominator == 1) {
            return String.valueOf(numerator);
        }
        return numerator + "/" + denominator;
    }

    @Override
    public int hashCode() {
        return ((Double) doubleValue()).hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o instanceof LongFraction) {
            LongFraction temp = (LongFraction) o;
            return (temp.denominator == denominator) && (temp.numerator == numerator);
        }
        return false;
    }


    @Override
    public LongFraction clone() {
        return new LongFraction(numerator, denominator);
    }
    //endregion

    @Override
    public int compareTo(LongFraction o) {
        if (o.equals(this))
            return 0;
        return o.doubleValue() > doubleValue() ? -1 : 1;
    }


    public void simplify() {
        long gcd = MathUtils.gcd(numerator, denominator);
        numerator /= gcd;
        denominator /= gcd;
        if (this.denominator < 0) {
            this.denominator = -this.denominator;
            this.numerator = -this.numerator;
        }
    }

    //region Розбір
    public static LongFraction valueOf(String s) {
        String[] strings = s.split("/");
        if (strings.length > 2 || strings.length < 1)
            throw new IllegalArgumentException();
        LongFraction temp = null;
        switch (strings.length) {
            case 1:
                temp = new LongFraction(Long.valueOf(strings[0]));
                break;
            case 2:
                temp = new LongFraction(Long.valueOf(strings[0]), Long.valueOf(strings[1]));
                break;
        }
        return temp;
    }
    //endregion
}
