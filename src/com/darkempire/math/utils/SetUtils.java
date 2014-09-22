package com.darkempire.math.utils;

import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.math.exception.MatrixSizeException;
import com.darkempire.math.struct.matrix.FuzzyRelationMatrix;
import com.darkempire.math.struct.set.FuzzyNumberSet;
import com.darkempire.math.struct.set.NumberSet;

/**
 * Create in 22:30
 * Created by siredvin on 05.05.14.
 */
@AnjiUtil
public final class SetUtils {
    /**
     * Перевіряє, чи є одна множина підмножиною іншої
     *
     * @param set    головна множина
     * @param subset кандидант на підмножину
     * @return чи справді кандидант на піднмножину множина?
     */
    public static boolean isSubset(FuzzyNumberSet set, FuzzyNumberSet subset) {
        int size = Math.max(set.getSize(), subset.getSize());
        for (int i = 0; i < size; i++) {
            if (set.get(i) < subset.get(i))
                return false;
        }
        return true;
    }

    /**
     * Перевіряє, чи є одна множина підмножиною іншої
     *
     * @param set    головна множина
     * @param subset кандидант на підмножину
     * @return чи справді кандидант на піднмножину множина?
     */
    public static boolean isSubset(NumberSet set, NumberSet subset) {
        int size = Math.max(set.getSize(), subset.getSize());
        for (int i = 0; i < size; i++) {
            if (set.get(i) < subset.get(i))
                return false;
        }
        return true;
    }

    /**
     * Перевіряє, чи є одна множина підмножиною іншої
     *
     * @param set    головна множина
     * @param subset кандидант на підмножину
     * @return чи справді кандидант на піднмножину множина?
     */
    public static boolean isSubset(FuzzyRelationMatrix set, FuzzyRelationMatrix subset) {
        int columnCount = Math.max(set.getColumnCount(), subset.getColumnCount());
        int rowCount = Math.max(set.getRowCount(), subset.getRowCount());
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                if (set.get(i, j) < subset.get(i, j))
                    return false;
            }
        }
        return true;
    }

    public static FuzzyRelationMatrix buildStrong(FuzzyRelationMatrix fuzzyRelationMatrix) {
        if (fuzzyRelationMatrix.getRowCount() != fuzzyRelationMatrix.getColumnCount())
            throw new MatrixSizeException(MatrixSizeException.MATRIX_IS_NOT_SQUARE);
        FuzzyRelationMatrix result = FuzzyRelationMatrix.createInstance(fuzzyRelationMatrix.getRowCount(), fuzzyRelationMatrix.getColumnCount());
        result.fill((rowIndex, columnIndex) -> Math.max(fuzzyRelationMatrix.get(rowIndex, columnIndex) - fuzzyRelationMatrix.get(columnIndex, rowIndex), 0));
        return result;
    }

    public static FuzzyNumberSet buildNotDominate(FuzzyRelationMatrix fuzzyRelationMatrix) {
        if (fuzzyRelationMatrix.getRowCount() != fuzzyRelationMatrix.getColumnCount())
            throw new MatrixSizeException(MatrixSizeException.MATRIX_IS_NOT_SQUARE);
        int columnCount = fuzzyRelationMatrix.getColumnCount();
        return FuzzyNumberSet.create(fuzzyRelationMatrix.getColumnCount(), index -> {
            double r = 0;
            for (int i = 0; i < columnCount; i++) {
                double temp = fuzzyRelationMatrix.get(i, index);
                if (temp > r)
                    r = temp;
            }
            return 1 - r;
        });
    }
}