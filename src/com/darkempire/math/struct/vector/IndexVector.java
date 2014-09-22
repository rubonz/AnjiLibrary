package com.darkempire.math.struct.vector;

import com.darkempire.anji.util.Util;
import com.darkempire.math.struct.LinearCalcable;
import com.darkempire.math.struct.function.interfaces.FIndexToIndex;
import com.darkempire.math.struct.function.interfaces.FIndexVectorAndIndexToIndex;

/**
 * Create in 10:42
 * Created by siredvin on 25.04.14.
 */
public abstract class IndexVector implements LinearCalcable<IndexVector>, IIndexVectorProvider {
    //region Сеттер
    public abstract void set(int index, int value);
    //endregion

    //region Геттери
    @Override
    public abstract int get(int index);

    @Override
    public abstract int getSize();
    //endregion

    public abstract int scalar(IndexVector vector);

    //region Заповнювачі
    public IndexVector fill(int value) {
        int size = getSize();
        for (int i = 0; i < size; i++) {
            set(i, value);
        }
        return this;
    }

    public IndexVector fill(FIndexToIndex function) {
        int size = getSize();
        for (int i = 0; i < size; i++) {
            set(i, function.calc(i));
        }
        return this;
    }

    public IndexVector fill(FIndexVectorAndIndexToIndex function) {
        int size = getSize();
        for (int i = 0; i < size; i++) {
            set(i, function.calc(this, i));
        }
        return this;
    }
    //endregion

    //region Заповнювачі підвекторів
    public IndexVector fillSubvector(int start, int end, int value) {
        end++;
        for (int i = start; i < end; i++) {
            set(i, value);
        }
        return this;
    }

    public IndexVector fillSubvector(int start, int end, FIndexToIndex function) {
        end++;
        for (int i = start; i < end; i++) {
            set(i, function.calc(i));
        }
        return this;
    }

    public IndexVector fillSubvector(int start, int end, FIndexVectorAndIndexToIndex function) {
        end++;
        for (int i = start; i < end; i++) {
            set(i, function.calc(this, i));
        }
        return this;
    }
    //endregion

    //region Системні функції
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o instanceof IndexVector) {
            IndexVector vector = (IndexVector) o;
            if (getSize() != vector.getSize())
                return false;
            int size = getSize();
            for (int i = 0; i < size; i++) {
                if (get(i) != vector.get(i))
                    return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int size = getSize();
        sb.append('{');
        for (int i = 0; i < size; i++) {
            sb.append(get(i));
            sb.append(',');
        }
        Util.removeLastChar(sb);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public abstract IndexVector clone();
    //endregion
}
