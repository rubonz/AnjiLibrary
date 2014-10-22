package com.darkempire.math.struct.number;

import com.darkempire.anji.log.Log;
import com.darkempire.math.struct.*;
import com.darkempire.math.struct.Number;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by siredvin on 22.10.14.
 *
 * @author siredvin
 */
public class Decimal implements Number<Decimal> {
    private BigDecimal value;

    public Decimal(BigDecimal value) {
        this.value = value;
    }

    //region Методи для числа
    @Override
    public int intValue() {
        return value.intValue();
    }

    @Override
    public long longValue() {
        return value.longValue();
    }

    @Override
    public float floatValue() {
        return value.floatValue();
    }

    @Override
    public double doubleValue() {
        return value.doubleValue();
    }

    @Override
    public int compareTo(Decimal o) {
        return value.compareTo(o.value);
    }
    //endregion

    @Override
    public Decimal getZero() {
        return new Decimal(BigDecimal.ZERO);
    }

    @Override
    public Decimal getOne() {
        return new Decimal(BigDecimal.ONE);
    }

    //region Системні методи
    @Override
    public Decimal deepcopy() {
        return new Decimal(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Decimal)) return false;

        Decimal decimal = (Decimal) o;

        return value.equals(decimal.value);

    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value.toString();
    }

    //endregion

    //region Арифметичні операції з присвоєнням
    @Override
    public Decimal inegate() {
        value = value.negate();
        return this;
    }

    @Override
    public Decimal iadd(Decimal decimal) {
        value = value.add(decimal.value);
        return this;
    }

    @Override
    public Decimal isubtract(Decimal decimal) {
        value = value.subtract(decimal.value);
        return this;
    }

    @Override
    public Decimal imultiply(Decimal decimal) {
        value = value.multiply(decimal.value);
        return this;
    }

    @Override
    public Decimal idivide(Decimal decimal) {
        value = value.divide(decimal.value, RoundingMode.HALF_EVEN);
        return this;
    }

    @Override
    public Decimal iinverse() {
        value = BigDecimal.ONE.divide(value, RoundingMode.HALF_EVEN);
        return this;
    }
    //endregion

    //region Арифметичні операції
    @Override
    public Decimal multiply(Decimal decimal) {
        return new Decimal(decimal.value.multiply(value));
    }

    @Override
    public Decimal divide(Decimal decimal) {
        return new Decimal(value.divide(decimal.value, RoundingMode.HALF_EVEN));
    }

    @Override
    public Decimal inverse() {
        return new Decimal(BigDecimal.ONE.divide(value, RoundingMode.HALF_EVEN));
    }

    @Override
    public Decimal add(Decimal decimal) {
        return new Decimal(value.add(decimal.value));
    }

    @Override
    public Decimal subtract(Decimal decimal) {
        return new Decimal(value.subtract(decimal.value));
    }

    @Override
    public Decimal negate() {
        return new Decimal(value.negate());
    }
    //endregion


}
