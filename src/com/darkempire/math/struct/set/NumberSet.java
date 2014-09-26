package com.darkempire.math.struct.set;

import com.darkempire.anji.util.Util;
import com.darkempire.math.struct.vector.IDoubleVectorProvider;

import java.util.BitSet;

/**
 * Create in 21:42
 * Created by siredvin on 23.04.14.
 */
public class NumberSet implements SetOperable<NumberSet>, IDoubleVectorProvider {
    private BitSet set;
    private int size;

    //region Конструктори
    public NumberSet() {
        set = new BitSet();
        size = 0;
    }

    public NumberSet(int count) {
        set = new BitSet(count);
        size = count;
    }

    public NumberSet(BitSet set) {
        this.set = set;
        size = set.length();
    }

    public NumberSet(BitSet set, int size) {
        this.set = set;
        this.size = size;
    }
    //endregion

    //region Геттери
    public boolean is(int index) {
        return set.get(index);
    }

    @Override
    public double get(int index) {
        return is(index) ? 1 : 0;
    }

    @Override
    public int getSize() {
        return size;
    }
    //endregion

    //region Сеттери
    public void set(int index, boolean exists) {
        if (size < index)
            size = index;
        set.set(index, exists);
    }
    //endregion

    //region Операції над множинами з присвоєнням
    @Override
    public NumberSet iunion(NumberSet numberSet) {
        set.or(numberSet.set);
        size = Math.min(size, numberSet.getSize());
        return this;
    }

    @Override
    public NumberSet iintersection(NumberSet numberSet) {
        set.and(numberSet.set);
        size = Math.min(size, numberSet.getSize());
        return this;
    }

    @Override
    public NumberSet icomplement() {
        int size = set.length();
        for (int i = 0; i < size; i++) {
            set.set(i, !set.get(i));
        }
        return this;
    }

    @Override
    public NumberSet isetminus(NumberSet numberSet) {
        set.andNot(numberSet.set);
        size = Math.min(size, numberSet.getSize());
        return this;
    }
    //endregion

    //region Операції над множинами
    @Override
    public NumberSet union(NumberSet numberSet) {
        BitSet set = (BitSet) this.set.clone();
        set.or(numberSet.set);
        return new NumberSet(set, Math.max(size, numberSet.getSize()));
    }

    @Override
    public NumberSet intersection(NumberSet numberSet) {
        BitSet set = (BitSet) this.set.clone();
        set.and(numberSet.set);
        return new NumberSet(set, Math.max(size, numberSet.getSize()));
    }

    @Override
    public NumberSet complement() {
        BitSet set = (BitSet) this.set.clone();
        int size = set.length();
        for (int i = 0; i < size; i++) {
            set.set(i, !set.get(i));
        }
        return new NumberSet(set, size);
    }

    @Override
    public NumberSet setminus(NumberSet numberSet) {
        BitSet set = (BitSet) this.set.clone();
        set.andNot(numberSet.set);
        return new NumberSet(set, Math.max(size, numberSet.getSize()));
    }
    //endregion

    //region Системні функції
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("{");
        for (int i = 0; i < size; i++) {
            if (set.get(i)) {
                s.append(i);
                s.append(",");
            }
        }
        s = Util.removeLastChar(s);
        s.append("}");
        return s.toString();
    }

    @Override
    public int hashCode() {
        return set.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o instanceof NumberSet) {
            NumberSet ns = (NumberSet) o;
            return ns.set.equals(set);
        }
        return false;
    }
    //endregion

    //region Статичні операції над множинами для багатьох множин відразу
    public static NumberSet union(NumberSet... sets) {
        BitSet temp = new BitSet();
        int size = 0;
        for (NumberSet set : sets) {
            int temp_size = set.getSize();
            if (temp_size > size) {
                size = temp_size;
            }
            temp.or(set.set);
        }
        return new NumberSet(temp, size);
    }

    public static NumberSet intersection(NumberSet... sets) {
        BitSet temp = new BitSet();
        int size = 0;
        for (NumberSet set : sets) {
            int temp_size = set.getSize();
            if (temp_size > size) {
                size = temp_size;
            }
            temp.and(set.set);
        }
        return new NumberSet(temp, size);
    }
    //endregion
}
