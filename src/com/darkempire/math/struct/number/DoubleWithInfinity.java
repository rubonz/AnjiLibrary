package com.darkempire.math.struct.number;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 13.11.13
 * Time: 15:49
 * To change this template use File | Settings | File Templates.
 */
public class DoubleWithInfinity implements com.darkempire.math.struct.Number<DoubleWithInfinity> {

    private double doublePart;
    private double mPart;
    private int hash;

    //region Конструктори
    public DoubleWithInfinity() {
        doublePart = mPart = 0;
        hash = 0;
    }

    public DoubleWithInfinity(double doublePart) {
        this.doublePart = doublePart;
        mPart = 0;
        hash = 0;
    }

    public DoubleWithInfinity(double doublePart, double mPart) {
        this.doublePart = doublePart;
        this.mPart = mPart;
        hash = 0;
    }
    //endregion

    //region Геттери
    public double getDoublePart() {
        return doublePart;
    }

    public double getM() {
        return mPart;
    }

    public double getAbs() {
        return mPart == 0 ? doublePart : Math.sqrt(doublePart * doublePart + mPart * mPart);
    }

    @Override
    public DoubleWithInfinity getZero() {
        return new DoubleWithInfinity(0);
    }

    @Override
    public DoubleWithInfinity getOne() {
        return new DoubleWithInfinity(1);
    }
    //endregion

    //region Арифметичні операції з присвоєнням
    @Override
    public DoubleWithInfinity imultiply(DoubleWithInfinity doubleWithInfinity) {
        final double nx = doublePart * doubleWithInfinity.doublePart;
        mPart = doublePart * doubleWithInfinity.mPart + mPart * doubleWithInfinity.doublePart + mPart * doubleWithInfinity.mPart;
        doublePart = nx;
        return this;
    }

    @Override
    public DoubleWithInfinity idivide(DoubleWithInfinity doubleWithInfinity) {
        if (mPart == 0) {
            if (doubleWithInfinity.mPart == 0) {
                doublePart = doublePart / doubleWithInfinity.doublePart;
                return this;
            } else {
                doublePart = 0;
                return this;
            }
        }
        /*
            (a1+b1 M)/(a2 + b2 M) = (a1/M + b1)/ (a2/M + b2) = b1 / b2
        */
        doublePart = mPart / doubleWithInfinity.mPart;
        mPart = 0;
        return this;
    }

    @Override
    public DoubleWithInfinity iinverse() {
        mPart = (-1 - doublePart / mPart) * doublePart;
        doublePart = 1 / doublePart;
        return this;
    }

    @Override
    public DoubleWithInfinity inegate() {
        doublePart = -doublePart;
        mPart = -mPart;
        return this;
    }

    @Override
    public DoubleWithInfinity iadd(DoubleWithInfinity doubleWithInfinity) {
        doublePart += doubleWithInfinity.doublePart;
        mPart += doubleWithInfinity.mPart;
        return this;
    }

    @Override
    public DoubleWithInfinity isubtract(DoubleWithInfinity doubleWithInfinity) {
        doublePart -= doubleWithInfinity.doublePart;
        mPart -= doubleWithInfinity.mPart;
        return this;
    }
    //endregion

    //region Арифметичні операції
    @Override
    public DoubleWithInfinity multiply(DoubleWithInfinity doubleWithInfinity) {
        return new DoubleWithInfinity(doublePart * doubleWithInfinity.doublePart, doublePart * doubleWithInfinity.mPart + mPart * doubleWithInfinity.doublePart + doubleWithInfinity.mPart * mPart);
    }

    @Override
    public DoubleWithInfinity divide(DoubleWithInfinity doubleWithInfinity) {
        if (mPart == 0) {
            if (doubleWithInfinity.mPart == 0) {
                return new DoubleWithInfinity(doublePart / doubleWithInfinity.doublePart);
            } else {
                return new DoubleWithInfinity(0);
            }
        }
        /*
        (a1+b1 M)/(a2 + b2 M) = (a1/M + b1)/ (a2/M + b2) = b1 / b2
         */
        return new DoubleWithInfinity(mPart / doubleWithInfinity.mPart);//Пов’язано з тим, що ми рахуємо M як майже нескінченність
    }

    @Override
    public DoubleWithInfinity inverse() {
        return new DoubleWithInfinity(1 / doublePart, (-1 - doublePart / mPart) * doublePart);
    }

    @Override
    public DoubleWithInfinity add(DoubleWithInfinity doubleWithInfinity) {
        return new DoubleWithInfinity(doublePart + doubleWithInfinity.doublePart, mPart + doubleWithInfinity.mPart);
    }

    @Override
    public DoubleWithInfinity subtract(DoubleWithInfinity doubleWithInfinity) {
        return new DoubleWithInfinity(doublePart - doubleWithInfinity.doublePart, mPart - doubleWithInfinity.mPart);
    }

    @Override
    public DoubleWithInfinity negate() {
        return new DoubleWithInfinity(-doublePart, -mPart);
    }
    //endregion

    //region Системні функції
    @Override
    public DoubleWithInfinity clone() {
        return new DoubleWithInfinity(doublePart, mPart);
    }

    @Override
    public int hashCode() {
        if (hash == 0) {
            long bits = 7L;
            bits = 31L * bits + Double.doubleToLongBits(doublePart);
            bits = 31L * bits + Double.doubleToLongBits(mPart);
            hash = (int) (bits ^ (bits >> 32));
        }
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o instanceof DoubleWithInfinity) {
            DoubleWithInfinity c = (DoubleWithInfinity) o;
            return c.doublePart == doublePart && c.mPart == mPart;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (doublePart != 0) {
            stringBuilder.append(doublePart);
        }
        if (mPart != 0) {
            stringBuilder.append(mPart > 0 ? '+' : '-');
            stringBuilder.append(Math.abs(mPart));
            stringBuilder.append('M');
        }
        if (stringBuilder.length() == 0)
            stringBuilder.append(0);
        return stringBuilder.toString();
    }
    //endregion

    //region Розбір
    public static DoubleWithInfinity valueOf(String v) {
        DoubleWithInfinity doubleWithInfinity = new DoubleWithInfinity();
        int sum = v.lastIndexOf('+');
        int sub = v.lastIndexOf('-');
        if (sum == sub) {
            if (v.contains("M")) {
                doubleWithInfinity.mPart = Double.valueOf(v.replace('M', 'd'));
            } else {
                doubleWithInfinity.doublePart = Double.valueOf(v);
            }
        } else {
            if (sub > 0) {
                String[] result = v.split("-");
                doubleWithInfinity.doublePart = Double.valueOf(result[0]);
                doubleWithInfinity.mPart = -Double.valueOf(result[1].replace('M', 'd'));
            } else {
                String[] result = v.split("\\+");
                doubleWithInfinity.doublePart = Double.valueOf(result[0]);
                doubleWithInfinity.mPart = Double.valueOf(result[1].replace('M', 'd'));
            }
        }
        return doubleWithInfinity;
    }
    //endregion

    //region Отримання числового значення
    @Override
    public int intValue() {
        return (int) doublePart;
    }

    @Override
    public long longValue() {
        return (long) doublePart;
    }

    @Override
    public float floatValue() {
        return (float) doublePart;
    }

    @Override
    public double doubleValue() {
        return doublePart;
    }
    //endregion

    @Override
    public DoubleWithInfinity deepcopy() {
        return clone();
    }

    @Override
    public int compareTo(DoubleWithInfinity o) {
        int firstResult = Double.compare(this.mPart, o.getM());
        if (firstResult == 0) {
            firstResult = Double.compare(this.doublePart, o.getDoublePart());
        }
        return firstResult;
    }
}
