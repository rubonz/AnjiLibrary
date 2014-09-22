package com.darkempire.math.struct.number;

import java.lang.Number;
import java.math.BigInteger;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 06.11.13
 * Time: 8:06
 * To change this template use File | Settings | File Templates.
 */
public class BigFraction extends Number implements com.darkempire.math.struct.Number<BigFraction> {

    private BigInteger denominator;
    private BigInteger numerator;

    //region Конструктори
    public BigFraction() {
        denominator = BigInteger.ONE;
        numerator = BigInteger.ZERO;
    }

    public BigFraction(BigInteger numerator) {
        denominator = BigInteger.ONE;
        this.numerator = numerator;
    }

    public BigFraction(BigInteger numerator, BigInteger denominator) {
        this.denominator = denominator;
        this.numerator = numerator;
        simplify();
    }
    //endregion

    //region Арифметичні операції з присвоєнням
    @Override
    public BigFraction isubtract(BigFraction f1) {
        BigInteger lcm = denominator.gcd(f1.denominator);
        BigInteger a1 = lcm.divide(denominator);
        BigInteger a2 = lcm.divide(f1.denominator);
        denominator = lcm;
        numerator = numerator.multiply(a1).subtract(f1.numerator.multiply(a2));
        simplify();
        return this;
    }

    @Override
    public BigFraction iadd(BigFraction f1) {
        BigInteger lcm = denominator.gcd(f1.denominator);
        BigInteger a1 = lcm.divide(denominator);
        BigInteger a2 = lcm.divide(f1.denominator);
        denominator = lcm;
        numerator = numerator.multiply(a1).add(f1.numerator.multiply(a2));
        simplify();
        return this;
    }

    @Override
    public BigFraction imultiply(BigFraction f1) {
        numerator = numerator.multiply(f1.numerator);
        denominator = denominator.multiply(f1.denominator);
        simplify();
        return this;
    }

    @Override
    public BigFraction idivide(BigFraction f1) {
        numerator = numerator.multiply(f1.denominator);
        denominator = denominator.multiply(f1.numerator);
        simplify();
        return this;
    }

    @Override
    public BigFraction inegate() {
        numerator = numerator.negate();
        return this;
    }

    @Override
    public BigFraction iinverse() {
        BigInteger t = numerator;
        numerator = denominator;
        denominator = t;
        simplify();
        return this;
    }
    //endregion

    //region Арифметичні операції
    @Override
    public BigFraction subtract(BigFraction f1) {
        BigInteger lcm = denominator.gcd(f1.denominator);
        BigInteger a1 = lcm.divide(denominator);
        BigInteger a2 = lcm.divide(f1.denominator);
        return new BigFraction(numerator.multiply(a1).subtract(f1.numerator.multiply(a2)), lcm);
    }

    @Override
    public BigFraction add(BigFraction f1) {
        BigInteger lcm = denominator.gcd(f1.denominator);
        BigInteger a1 = lcm.divide(denominator);
        BigInteger a2 = lcm.divide(f1.denominator);
        return new BigFraction(numerator.multiply(a1).add(f1.numerator.multiply(a2)), lcm);
    }

    @Override
    public BigFraction multiply(BigFraction f1) {
        return new BigFraction(numerator.multiply(f1.numerator), denominator.multiply(f1.denominator));
    }

    @Override
    public BigFraction divide(BigFraction f1) {
        numerator = numerator.multiply(f1.denominator);
        denominator = denominator.multiply(f1.numerator);
        return new BigFraction(numerator.multiply(f1.denominator), denominator.multiply(f1.numerator));
    }

    @Override
    public BigFraction negate() {
        numerator = numerator.negate();
        return this;
    }

    @Override
    public BigFraction inverse() {
        BigInteger t = numerator;
        numerator = denominator;
        denominator = t;
        return this;
    }
    //endregion

    //region Геттери
    public BigInteger getDenominator() {
        return denominator;
    }

    public BigInteger getNumerator() {
        return numerator;
    }

    @Override
    public BigFraction getZero() {
        return new BigFraction(BigInteger.ZERO);
    }

    @Override
    public BigFraction getOne() {
        return new BigFraction(BigInteger.ONE);
    }
    //endregion

    //region Отримання числових значень
    @Override
    public int intValue() {
        return (int) longValue();
    }

    @Override
    public long longValue() {
        return numerator.divide(denominator).longValue();
    }

    @Override
    public float floatValue() {
        return numerator.floatValue() / denominator.floatValue();
    }

    @Override
    public double doubleValue() {
        return numerator.doubleValue() / denominator.doubleValue();
    }
    //endregion

    @Override
    public BigFraction deepcopy() {
        return clone();
    }

    //region Системні функції
    @Override
    public String toString() {
        if (denominator.compareTo(BigInteger.ONE) == 0) {
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
        if (o instanceof BigFraction) {
            BigFraction temp = (BigFraction) o;
            return (temp.denominator.equals(denominator)) && (temp.numerator.equals(numerator));
        }
        return false;
    }

    @Override
    public BigFraction clone() {
        return new BigFraction(numerator.add(BigInteger.ZERO), denominator.add(BigInteger.ZERO));
    }
    //endregion

    @Override
    public int compareTo(BigFraction o) {
        if (o.equals(this))
            return 0;
        return o.doubleValue() > doubleValue() ? -1 : 1;
    }

    public void simplify() {
        BigInteger gcd = denominator.gcd(numerator);
        numerator = numerator.divide(gcd);
        denominator = denominator.divide(gcd);
        if (this.denominator.compareTo(BigInteger.ZERO) < 0) {
            this.denominator = this.denominator.negate();
            this.numerator = this.numerator.negate();
        }
    }

    //region Розбір
    public static BigFraction valueOf(String s) {
        String[] strings = s.split("/");
        if (strings.length > 2 || strings.length < 1)
            throw new IllegalArgumentException();
        BigFraction temp = null;
        switch (strings.length) {
            case 1:
                temp = new BigFraction(new BigInteger(strings[0]));
                break;
            case 2:
                temp = new BigFraction(new BigInteger(strings[0]), new BigInteger(strings[1]));
                break;
        }
        return temp;
    }
    //endregion
}
