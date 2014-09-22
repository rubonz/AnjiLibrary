package com.darkempire.math.struct.vector;

import com.darkempire.anji.util.Util;
import com.darkempire.math.exception.MatrixSizeException;
import com.darkempire.math.struct.LinearCalcable;
import com.darkempire.math.struct.function.interfaces.FBooleanVectorIndexBoolean;
import com.darkempire.math.struct.function.interfaces.FIndexToBoolean;
import com.darkempire.math.struct.matrix.BooleanFixedMatrix;
import com.darkempire.math.struct.matrix.BooleanMatrix;

/**
 * Create in 10:42
 * Created by siredvin on 25.04.14.
 */
public abstract class BooleanVector implements LinearCalcable<BooleanVector>, IBooleanVectorProvider {
    //region Cеттери
    public abstract void set(int index, boolean value);
    //endregion

    //region Отримання частини вектора
    public abstract BooleanVector subvector(int length);

    public abstract BooleanVector subvector(int startIndex, int length);
    //endregion

    //region Геттери
    @Override
    public abstract boolean get(int index);

    @Override
    public abstract int getSize();
    //endregion

    public abstract boolean scalar(BooleanVector vector);

    //region Заповнювачі всього вектора
    public BooleanVector fill(boolean value) {
        int size = getSize();
        for (int i = 0; i < size; i++) {
            set(i, value);
        }
        return this;
    }

    public BooleanVector fill(FIndexToBoolean function) {
        int size = getSize();
        for (int i = 0; i < size; i++) {
            set(i, function.calc(i));
        }
        return this;
    }

    public BooleanVector fill(FBooleanVectorIndexBoolean function) {
        int size = getSize();
        for (int i = 0; i < size; i++) {
            set(i, function.calc(this, i));
        }
        return this;
    }
    //endregion

    //region Заповнення підвектора
    public BooleanVector fillSubvector(int start, int end, boolean value) {
        end++;
        for (int i = start; i < end; i++) {
            set(i, value);
        }
        return this;
    }

    public BooleanVector fillSubvector(int start, int end, FIndexToBoolean function) {
        end++;
        for (int i = start; i < end; i++) {
            set(i, function.calc(i));
        }
        return this;
    }

    public BooleanVector fillSubvector(int start, int end, FBooleanVectorIndexBoolean function) {
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
        if (o instanceof BooleanVector) {
            BooleanVector vector = (BooleanVector) o;
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
    public abstract BooleanVector clone();
    //endregion

    //region Арифметичні операції з присвоєнням
    public BooleanVector iprod(boolean lambda) {
        int size = getSize();
        for (int i = 0; i < size; i++) {
            set(i, get(i) && lambda);
        }
        return this;
    }

    //endregion
    public BooleanMatrix mult(BooleanVector vector) {
        int size = getSize();
        if (vector.getSize() != size)
            throw new MatrixSizeException(MatrixSizeException.MATRIX_SIZE_MULTY_MISMATCH);
        BooleanFixedMatrix result = BooleanFixedMatrix.createInstance(size, size);
        for (int rowIndex = 0; rowIndex < size; rowIndex++) {
            for (int columnIndex = 0; columnIndex < size; columnIndex++) {
                result.set(rowIndex, columnIndex, get(rowIndex) && vector.get(columnIndex));
            }
        }
        return result;
    }

    public abstract BooleanVector prod(boolean lambda);
}
