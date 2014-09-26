package com.darkempire.math.struct.set;

import com.darkempire.anji.util.Util;
import com.darkempire.math.MathMachine;
import com.darkempire.math.struct.function.interfaces.FIndexToDouble;
import com.darkempire.math.struct.vector.IDoubleVectorProvider;

import java.util.Arrays;

/**
 * Create in 15:50
 * Created by siredvin on 23.04.14.
 */
public class FuzzyNumberSet implements FuzzySetOperable<FuzzyNumberSet>, IDoubleVectorProvider {
    private double[] p;

    //region Конструктори
    public FuzzyNumberSet(int count) {
        p = new double[count];
    }

    public FuzzyNumberSet(double[] p) {
        this.p = p;
    }
    //endregion

    //region Геттери
    @Override
    public double get(int index) {
        if (index >= p.length)
            return 0;
        return p[index];
    }

    @Override
    public int getSize() {
        return p.length;
    }
    //endregion

    //region Сеттери
    public void set(int index, double value) {
        if (index >= p.length)
            return;
        p[index] = value;
    }
    //endregion

    //region Операції над множинами з присвоєнням
    @Override
    public FuzzyNumberSet iunion(FuzzyNumberSet fuzzyNumberSet) {
        if (fuzzyNumberSet.getSize() >= p.length) {
            double[] np = new double[fuzzyNumberSet.getSize()];
            for (int i = 0; i < p.length; i++) {
                np[i] = Math.max(get(i), fuzzyNumberSet.get(i));
            }
            this.p = np;
        } else {
            for (int i = 0; i < p.length; i++) {
                p[i] = Math.max(p[i], fuzzyNumberSet.get(i));
            }
        }
        return this;
    }

    @Override
    public FuzzyNumberSet iintersection(FuzzyNumberSet fuzzyNumberSet) {
        if (fuzzyNumberSet.getSize() >= p.length) {
            double[] np = new double[fuzzyNumberSet.getSize()];
            for (int i = 0; i < p.length; i++) {
                np[i] = Math.min(get(i), fuzzyNumberSet.get(i));
            }
            this.p = np;
        } else {
            for (int i = 0; i < p.length; i++) {
                p[i] = Math.min(get(i), fuzzyNumberSet.get(i));
            }
        }
        return this;
    }

    @Override
    public FuzzyNumberSet icomplement() {
        for (int i = 0; i < p.length; i++) {
            p[i] = 1 - p[i];
        }
        return this;
    }

    @Override
    public FuzzyNumberSet isetminus(FuzzyNumberSet fuzzyNumberSet) {
        if (fuzzyNumberSet.getSize() >= p.length) {
            double[] np = new double[fuzzyNumberSet.getSize()];
            for (int i = 0; i < p.length; i++) {
                np[i] = Math.max(get(i) - fuzzyNumberSet.get(i), 0);
            }
            this.p = np;
        } else {
            for (int i = 0; i < p.length; i++) {
                p[i] = Math.max(get(i) - fuzzyNumberSet.get(i), 0);
            }
        }
        return this;
    }
    //endregion

    //region Операції над множинами
    @Override
    public FuzzyNumberSet union(FuzzyNumberSet fuzzyNumberSet) {
        int size = Math.max(fuzzyNumberSet.getSize(), getSize());
        double[] p = new double[size];
        for (int i = 0; i < size; i++) {
            p[i] = Math.max(get(i), fuzzyNumberSet.get(i));
        }
        return new FuzzyNumberSet(p);
    }

    @Override
    public FuzzyNumberSet intersection(FuzzyNumberSet fuzzyNumberSet) {
        int size = Math.max(fuzzyNumberSet.getSize(), getSize());
        double[] p = new double[size];
        for (int i = 0; i < size; i++) {
            p[i] = Math.min(get(i), fuzzyNumberSet.get(i));
        }
        return new FuzzyNumberSet(p);
    }


    @Override
    public FuzzyNumberSet complement() {
        int size = getSize();
        double[] p = new double[size];
        for (int i = 0; i < size; i++) {
            p[i] = 1 - this.p[i];
        }
        return new FuzzyNumberSet(p);
    }

    @Override
    public FuzzyNumberSet setminus(FuzzyNumberSet fuzzyNumberSet) {
        int size = Math.max(fuzzyNumberSet.getSize(), getSize());
        double[] p = new double[size];
        for (int i = 0; i < size; i++) {
            p[i] = Math.max(get(i) - fuzzyNumberSet.get(i), 0);
        }
        return new FuzzyNumberSet(p);
    }
    //endregion


    public NumberSet levelSet(double alpha) {
        NumberSet numberSet = new NumberSet(p.length);
        for (int i = 0; i < p.length; i++) {
            numberSet.set(i, p[i] >= alpha);
        }
        return numberSet;
    }

    //region Логічні операції з присвоєнням
    @Override
    public FuzzyNumberSet iand(FuzzyNumberSet fuzzyNumberSet) {
        if (fuzzyNumberSet.getSize() >= p.length) {
            double[] np = new double[fuzzyNumberSet.getSize()];
            for (int i = 0; i < p.length; i++) {
                np[i] = get(i) * fuzzyNumberSet.get(i);
            }
            this.p = np;
        } else {
            for (int i = 0; i < p.length; i++) {
                p[i] = p[i] * fuzzyNumberSet.get(i);
            }
        }
        return this;
    }

    @Override
    public FuzzyNumberSet ior(FuzzyNumberSet fuzzyNumberSet) {
        if (fuzzyNumberSet.getSize() >= p.length) {
            double[] np = new double[fuzzyNumberSet.getSize()];
            for (int i = 0; i < p.length; i++) {
                np[i] = Math.min(get(i) + fuzzyNumberSet.get(i), 1);
            }
            this.p = np;
        } else {
            for (int i = 0; i < p.length; i++) {
                p[i] = Math.min(p[i] + fuzzyNumberSet.get(i), 1);
            }
        }
        return this;
    }

    @Override
    public FuzzyNumberSet ixor(FuzzyNumberSet fuzzyNumberSet) {
        if (fuzzyNumberSet.getSize() >= p.length) {
            double[] np = new double[fuzzyNumberSet.getSize()];
            for (int i = 0; i < p.length; i++) {
                double temp = get(i) * fuzzyNumberSet.get(i);
                np[i] = Math.min(get(i) + fuzzyNumberSet.get(i) - 2 * temp, 1);
            }
            this.p = np;
        } else {
            for (int i = 0; i < p.length; i++) {
                double temp = p[i] * fuzzyNumberSet.get(i);
                p[i] = Math.min(p[i] + fuzzyNumberSet.get(i) - 2 * temp, 1);
            }
        }
        return this;
    }
    //endregion

    //region Логічні операції
    @Override
    public FuzzyNumberSet and(FuzzyNumberSet fuzzyNumberSet) {
        int size = Math.max(fuzzyNumberSet.getSize(), getSize());
        double[] p = new double[size];
        for (int i = 0; i < size; i++) {
            p[i] = get(i) * fuzzyNumberSet.get(i);
        }
        return new FuzzyNumberSet(p);
    }

    @Override
    public FuzzyNumberSet or(FuzzyNumberSet fuzzyNumberSet) {
        int size = Math.max(fuzzyNumberSet.getSize(), getSize());
        double[] p = new double[size];
        for (int i = 0; i < size; i++) {
            p[i] = Math.min(get(i) + fuzzyNumberSet.get(i), 1);
        }
        return new FuzzyNumberSet(p);
    }

    @Override
    public FuzzyNumberSet xor(FuzzyNumberSet fuzzyNumberSet) {
        int size = Math.max(fuzzyNumberSet.getSize(), getSize());
        double[] p = new double[size];
        for (int i = 0; i < size; i++) {
            double temp = get(i) * fuzzyNumberSet.get(i);
            p[i] = Math.min(get(i) + fuzzyNumberSet.get(i) - 2 * temp, 1);
        }
        return new FuzzyNumberSet(p);
    }
    //endregion

    //region Системні функції
    @Override
    public Object clone() {
        return new FuzzyNumberSet(p.clone());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(p);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o instanceof FuzzyNumberSet) {
            FuzzyNumberSet fuzzyNumberSet = (FuzzyNumberSet) o;
            return Arrays.equals(p, fuzzyNumberSet.p);
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("{");
        for (int i = 0; i < p.length; i++) {
            /*s.append("(");
            s.append(i);
            s.append("|");*/
            s.append(MathMachine.numberFormat.format(p[i]));
            //s.append("),");
            s.append(';');
        }
        Util.removeLastChar(s);
        s.append("}");
        return s.toString();
    }
    //endregion

    //region Створення матриць
    public static FuzzyNumberSet create(double... p) {
        return new FuzzyNumberSet(p);
    }

    public static FuzzyNumberSet create(int size, double value) {
        double[] p = new double[size];
        Arrays.fill(p, value);
        return new FuzzyNumberSet(p);
    }


    public static FuzzyNumberSet create(int size, FIndexToDouble function) {
        double[] p = new double[size];
        for (int i = 0; i < size; i++) {
            p[i] = function.calc(i);
        }
        return new FuzzyNumberSet(p);
    }
    //endregion

    //region Статичні операції над множинами для багатьох множин відразу
    public static FuzzyNumberSet union(FuzzyNumberSet... sets) {
        int maxSize = 0;
        for (FuzzyNumberSet set : sets) {
            int size = set.p.length;
            if (size > maxSize)
                maxSize = size;
        }
        FuzzyNumberSet newSet = new FuzzyNumberSet(maxSize);
        for (int i = 0; i < maxSize; i++) {
            double temp = 0;
            for (FuzzyNumberSet set : sets) {
                temp = Math.max(temp, set.get(i));
            }
            newSet.set(i, temp);
        }
        return newSet;
    }

    public static FuzzyNumberSet intersection(FuzzyNumberSet... sets) {
        int maxSize = 0;
        for (FuzzyNumberSet set : sets) {
            int size = set.p.length;
            if (size > maxSize)
                maxSize = size;
        }
        FuzzyNumberSet newSet = new FuzzyNumberSet(maxSize);
        for (int i = 0; i < maxSize; i++) {
            double temp = 0;
            for (FuzzyNumberSet set : sets) {
                temp = Math.min(temp, set.get(i));
            }
            newSet.set(i, temp);
        }
        return newSet;
    }

    public static FuzzyNumberSet pUnion(FuzzyNumberSet... sets) {
        int maxSize = 0;
        for (FuzzyNumberSet set : sets) {
            int size = set.p.length;
            if (size > maxSize)
                maxSize = size;
        }
        FuzzyNumberSet newSet = new FuzzyNumberSet(maxSize);
        for (int i = 0; i < maxSize; i++) {
            double temp = 0;
            for (FuzzyNumberSet set : sets) {
                temp += set.get(i);
            }
            newSet.set(i, Math.min(temp, 1));
        }
        return newSet;
    }

    public static FuzzyNumberSet pIntersection(FuzzyNumberSet... sets) {
        int maxSize = 0;
        for (FuzzyNumberSet set : sets) {
            int size = set.p.length;
            if (size > maxSize)
                maxSize = size;
        }
        FuzzyNumberSet newSet = new FuzzyNumberSet(maxSize);
        for (int i = 0; i < maxSize; i++) {
            double temp = 0;
            for (FuzzyNumberSet set : sets) {
                temp *= set.get(i);
            }
            newSet.set(i, temp);
        }
        return newSet;
    }
    //endregion
}
