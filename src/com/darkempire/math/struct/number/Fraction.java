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
public class Fraction extends Number implements com.darkempire.math.struct.Number<Fraction> {
    private int denominator;
    private int numerator;

    //region Конструктори
    public Fraction() {
        denominator = 1;
        numerator = 0;
    }

    public Fraction(float value) {
        if (value == 0) {
            denominator = 1;
            numerator = 0;
        } else {
            int bits = Float.floatToIntBits(value);

            int sign = bits >>> 31;
            int exponent = ((bits >>> 23) ^ (sign << 8)) - 127;
            int fraction = bits << 9; // bits are "reversed" but that's not a problem
            if (exponent == 0) {
                numerator = Math.round(value);
                denominator = 1;
            } else {
                int a = 1;
                int b = 1;

                for (int i = 31; i >= 9; i--) {
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

    public Fraction(int numerator) {
        denominator = 1;
        this.numerator = numerator;
    }

    public Fraction(int numerator, int denominator) {
        this.denominator = denominator;
        this.numerator = numerator;
        simplify();
    }
    //endregion

    //region Арифметичні операції з присвоєннями
    @Override
    public Fraction isubtract(Fraction f1) {
        int lcm = MathUtils.lcm(denominator, f1.denominator);
        int a1 = lcm / denominator;
        int a2 = lcm / f1.denominator;
        denominator = lcm;
        numerator = numerator * a1 - f1.numerator * a2;
        simplify();
        return this;
    }

    @Override
    public Fraction iadd(Fraction f1) {
        int lcm = MathUtils.lcm(denominator, f1.denominator);
        int a1 = lcm / denominator;
        int a2 = lcm / f1.denominator;
        denominator = lcm;
        numerator = numerator * a1 + f1.numerator * a2;
        simplify();
        return this;
    }

    @Override
    public Fraction imultiply(Fraction f1) {
        numerator *= f1.numerator;
        denominator *= f1.denominator;
        simplify();
        return this;
    }

    @Override
    public Fraction idivide(Fraction f1) {
        numerator *= f1.denominator;
        denominator *= f1.numerator;
        simplify();
        return this;
    }

    @Override
    public Fraction inegate() {
        numerator = -numerator;
        return this;
    }

    @Override
    public Fraction iinverse() {
        int t = numerator;
        numerator = denominator;
        denominator = t;
        simplify();
        return this;
    }
    //endregion

    //region Арифметичні операції
    @Override
    public Fraction subtract(Fraction f1) {
        int lcm = MathUtils.lcm(denominator, f1.denominator);
        int a1 = lcm / denominator;
        int a2 = lcm / f1.denominator;
        return new Fraction(numerator * a1 - f1.numerator * a2, lcm);
    }

    @Override
    public Fraction add(Fraction f1) {
        int lcm = MathUtils.lcm(denominator, f1.denominator);
        int a1 = lcm / denominator;
        int a2 = lcm / f1.denominator;
        return new Fraction(numerator * a1 + f1.numerator * a2, lcm);
    }

    @Override
    public Fraction multiply(Fraction f1) {
        return new Fraction(numerator * f1.numerator, denominator * f1.denominator);
    }

    @Override
    public Fraction divide(Fraction f1) {
        return new Fraction(numerator * f1.denominator, denominator * f1.numerator);
    }

    @Override
    public Fraction negate() {
        return new Fraction(-numerator, denominator);
    }

    @Override
    public Fraction inverse() {
        return new Fraction(denominator, numerator);
    }
    //endregion

    //region Геттери
    public int getDenominator() {
        return denominator;
    }

    public int getNumerator() {
        return numerator;
    }

    @Override
    public Fraction getZero() {
        return new Fraction(0);
    }

    @Override
    public Fraction getOne() {
        return new Fraction(1);
    }
    //endregion

    //region Отримання числових значень
    @Override
    public int intValue() {
        return numerator / denominator;
    }

    @Override
    public long longValue() {
        return intValue();
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
    public Fraction deepcopy() {
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
        if (o instanceof Fraction) {
            Fraction temp = (Fraction) o;
            return (temp.denominator == denominator) && (temp.numerator == numerator);
        }
        return false;
    }


    @Override
    public Fraction clone() {
        return new Fraction(numerator, denominator);
    }
    //endregion

    @Override
    public int compareTo(Fraction o) {
        if (o.equals(this))
            return 0;
        return o.doubleValue() > doubleValue() ? -1 : 1;
    }

    public void simplify() {
        int gcd = MathUtils.gcd(numerator, denominator);
        numerator /= gcd;
        denominator /= gcd;
        if (this.denominator < 0) {
            this.denominator = -this.denominator;
            this.numerator = -this.numerator;
        }
    }

    //region Розбір
    public static Fraction valueOf(String s) {
        String[] strings = s.split("/");
        if (strings.length > 2 || strings.length < 1)
            throw new IllegalArgumentException();
        Fraction temp = null;
        switch (strings.length) {
            case 1:
                temp = new Fraction(Integer.valueOf(strings[0]));
                break;
            case 2:
                temp = new Fraction(Integer.valueOf(strings[0]), Integer.valueOf(strings[1]));
                break;
        }
        return temp;
    }
    //endregion
}
