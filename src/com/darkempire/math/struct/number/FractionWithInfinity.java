package com.darkempire.math.struct.number;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 13.11.13
 * Time: 15:49
 * To change this template use File | Settings | File Templates.
 */
public class FractionWithInfinity implements com.darkempire.math.struct.Number<FractionWithInfinity> {
    private Fraction doublePart;
    private Fraction mPart;
    private int hash;

    //region Конструктори
    public FractionWithInfinity() {
        doublePart = new Fraction();
        mPart = new Fraction();
        hash = 0;
    }

    public FractionWithInfinity(Fraction doublePart) {
        this.doublePart = doublePart;
        mPart = new Fraction();
        hash = 0;
    }

    public FractionWithInfinity(Fraction doublePart, Fraction mPart) {
        this.doublePart = doublePart;
        this.mPart = mPart;
        hash = 0;
    }

    public FractionWithInfinity(float doublePart) {
        this.doublePart = new Fraction(doublePart);
        this.mPart = new Fraction();
        hash = 0;
    }

    public FractionWithInfinity(float doublePart, float mPart) {
        this.doublePart = new Fraction(doublePart);
        this.mPart = new Fraction(mPart);
        hash = 0;
    }
    //endregion

    //region Геттери
    public Fraction getDoublePart() {
        return doublePart;
    }

    public Fraction getM() {
        return mPart;
    }

    @Override
    public FractionWithInfinity getZero() {
        return new FractionWithInfinity(new Fraction(0));
    }

    @Override
    public FractionWithInfinity getOne() {
        return new FractionWithInfinity(new Fraction(1));
    }
    //endregion

    //region Арифметичні операції з присвоєнням
    @Override
    public FractionWithInfinity imultiply(FractionWithInfinity doubleWithInfinity) {
        Fraction nx = doublePart.multiply(doubleWithInfinity.doublePart);
        mPart = doublePart.multiply(doubleWithInfinity.mPart).iadd(mPart.multiply(doubleWithInfinity.doublePart)).iadd(mPart.multiply(doubleWithInfinity.mPart));
        doublePart = nx;
        return this;
    }

    @Override
    public FractionWithInfinity idivide(FractionWithInfinity doubleWithInfinity) {
        if (mPart.getNumerator() == 0) {
            if (doubleWithInfinity.mPart.getNumerator() == 0) {
                doublePart.idivide(doubleWithInfinity.doublePart);
                return this;
            } else {
                doublePart = new Fraction(0);
                return this;
            }
        }
        /*
            (a1+b1 M)/(a2 + b2 M) = (a1/M + b1)/ (a2/M + b2) = b1 / b2
        */
        doublePart = mPart.idivide(doubleWithInfinity.mPart);
        mPart = new Fraction(0);
        return this;
    }

    @Override
    public FractionWithInfinity iinverse() {
        mPart = (doublePart.divide(mPart).inegate().isubtract(new Fraction(1))).imultiply(doublePart);
        doublePart.iinverse();
        return this;
    }

    @Override
    public FractionWithInfinity inegate() {
        doublePart.inegate();
        mPart.inegate();
        return this;
    }

    @Override
    public FractionWithInfinity iadd(FractionWithInfinity doubleWithInfinity) {
        doublePart.iadd(doubleWithInfinity.doublePart);
        mPart.iadd(doubleWithInfinity.mPart);
        return this;
    }

    @Override
    public FractionWithInfinity isubtract(FractionWithInfinity doubleWithInfinity) {
        doublePart.isubtract(doubleWithInfinity.doublePart);
        mPart.isubtract(doubleWithInfinity.mPart);
        return this;
    }
    //endregion

    //region Арифметичні операції
    @Override
    public FractionWithInfinity multiply(FractionWithInfinity doubleWithInfinity) {
        return new FractionWithInfinity(doublePart.multiply(doubleWithInfinity.doublePart), doublePart.multiply(doubleWithInfinity.mPart).iadd(mPart.multiply(doubleWithInfinity.doublePart)).iadd(doubleWithInfinity.mPart.multiply(mPart)));
    }

    @Override
    public FractionWithInfinity divide(FractionWithInfinity doubleWithInfinity) {
        if (mPart.getNumerator() == 0) {
            if (doubleWithInfinity.mPart.getNumerator() == 0) {
                return new FractionWithInfinity(doublePart.divide(doubleWithInfinity.doublePart));
            } else {
                return new FractionWithInfinity(new Fraction(0));
            }
        }
        /*
        (a1+b1 M)/(a2 + b2 M) = (a1/M + b1)/ (a2/M + b2) = b1 / b2
         */
        return new FractionWithInfinity(mPart.divide(doubleWithInfinity.mPart));//Пов’язано з тим, що ми рахуємо M як майже нескінченність
    }

    @Override
    public FractionWithInfinity inverse() {
        return new FractionWithInfinity(doublePart.inverse(), (doublePart.divide(mPart).inegate().subtract(new Fraction(1))).imultiply(doublePart));
    }

    @Override
    public FractionWithInfinity add(FractionWithInfinity doubleWithInfinity) {
        return new FractionWithInfinity(doublePart.add(doubleWithInfinity.doublePart), mPart.add(doubleWithInfinity.mPart));
    }

    @Override
    public FractionWithInfinity subtract(FractionWithInfinity doubleWithInfinity) {
        return new FractionWithInfinity(doublePart.subtract(doubleWithInfinity.doublePart), mPart.subtract(doubleWithInfinity.mPart));
    }

    @Override
    public FractionWithInfinity negate() {
        return new FractionWithInfinity(doublePart.negate(), mPart.negate());
    }
    //endregion

    //region Системні функції
    @Override
    public FractionWithInfinity clone() {
        return new FractionWithInfinity(doublePart.deepcopy(), mPart.deepcopy());
    }

    @Override
    public int hashCode() {
        if (hash == 0) {
            long bits = 7L;
            bits = 31L * bits + Double.doubleToLongBits(doublePart.hashCode());
            bits = 31L * bits + Double.doubleToLongBits(mPart.hashCode());
            hash = (int) (bits ^ (bits >> 32));
        }
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o instanceof FractionWithInfinity) {
            FractionWithInfinity c = (FractionWithInfinity) o;
            return c.doublePart == doublePart && c.mPart == mPart;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (doublePart.getNumerator() != 0) {
            stringBuilder.append(doublePart);
        }
        if (mPart.getNumerator() != 0) {
            if (doublePart.getNumerator() != 0 && mPart.getNumerator() > 0) {
                stringBuilder.append('+');
            }
            stringBuilder.append(mPart);
            stringBuilder.append('M');
        }
        if (stringBuilder.length() == 0)
            stringBuilder.append(0);
        return stringBuilder.toString();
    }
    //endregion

    //region Розбір
    public static FractionWithInfinity valueOf(String v) {
        FractionWithInfinity doubleWithInfinity = new FractionWithInfinity();
        int sum = v.lastIndexOf('+');
        int sub = v.lastIndexOf('-');
        if (sum == sub) {
            if (v.contains("M")) {
                doubleWithInfinity.mPart = Fraction.valueOf(v.replace("M", ""));
            } else {
                doubleWithInfinity.doublePart = Fraction.valueOf(v);
            }
        } else {
            if (sub > 0) {
                String[] result = v.split("-");
                doubleWithInfinity.doublePart = Fraction.valueOf(result[0]);
                doubleWithInfinity.mPart = Fraction.valueOf(result[1].replace("M", "")).inegate();
            } else {
                String[] result = v.split("\\+");
                doubleWithInfinity.doublePart = Fraction.valueOf(result[0]);
                doubleWithInfinity.mPart = Fraction.valueOf(result[1].replace("M", ""));
            }
        }
        return doubleWithInfinity;
    }
    //endregion

    //region Отримання числових значень
    @Override
    public int intValue() {
        return doublePart.intValue();
    }

    @Override
    public long longValue() {
        return doublePart.longValue();
    }

    @Override
    public float floatValue() {
        return doublePart.floatValue();
    }

    @Override
    public double doubleValue() {
        return doublePart.doubleValue();
    }

    @Override
    public FractionWithInfinity deepcopy() {
        return clone();
    }
    //endregion

    //region Порівняння
    public boolean isLessThenZero() {
        return mPart.getNumerator() < 0 || (mPart.getNumerator() == 0 && doublePart.getNumerator() < 0);
    }

    public boolean isGreatThenZero() {
        return mPart.getNumerator() > 0 || (mPart.getNumerator() == 0 && doublePart.getNumerator() > 0);
    }

    @Override
    public int compareTo(FractionWithInfinity o) {
        int firstResult = this.mPart.compareTo(o.getM());
        if (firstResult == 0) {
            firstResult = this.doublePart.compareTo(o.getDoublePart());
        }
        return firstResult;
    }
    //endregion
}
