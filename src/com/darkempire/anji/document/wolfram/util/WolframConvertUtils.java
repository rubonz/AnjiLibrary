package com.darkempire.anji.document.wolfram.util;

import com.darkempire.anji.annotation.AnjiRewrite;
import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.anji.log.Log;
import com.darkempire.math.struct.matrix.IDoubleMatrixProvider;
import com.darkempire.math.struct.set.DoubleInterval;
import com.darkempire.math.struct.set.SimpleDoubleInterval;
import com.darkempire.math.struct.vector.DoubleFixedVector;
import com.darkempire.math.struct.vector.DoubleResizeVector;
import com.darkempire.math.struct.vector.IDoubleVectorProvider;

import java.util.StringJoiner;

/**
 * Create in 0:47
 * Created by siredvin on 05.06.14.
 */
@AnjiUtil
public final class WolframConvertUtils {
    private WolframConvertUtils() {
    }

    public static String convertDoubleMatrix(IDoubleMatrixProvider matrix) {
        StringJoiner stringJoiner = new StringJoiner(",", "{", "}");
        int columnCount = matrix.getColumnCount();
        int rowCount = matrix.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            StringJoiner joiner = new StringJoiner(",", "{", "}");
            for (int j = 0; j < columnCount; j++) {
                joiner.add(String.valueOf(matrix.get(i, j)));
            }
            stringJoiner.add(joiner.toString());
        }
        return stringJoiner.toString();
    }

    public static String convertDoubleVector(IDoubleVectorProvider vector) {
        int size = vector.getSize();
        StringJoiner joiner = new StringJoiner(",", "{", "}");
        for (int i = 0; i < size; i++) {
            joiner.add(String.valueOf(vector.get(i)));
        }
        return joiner.toString();
    }

    public static DoubleFixedVector convertResultToDoubleFixedVector(String result) {
        result = result.replace("{", "").replace("}", "").replace("*^", "E");
        String[] numbers = result.split(",");
        DoubleFixedVector vector = new DoubleFixedVector(numbers.length);
        for (int i = 0; i < numbers.length; i++) {
            vector.set(i, Double.parseDouble(numbers[i]));
        }
        return vector;
    }

    public static DoubleResizeVector convertResultToDoubleResizeVector(String result) {
        result = result.replace("{", "").replace("}", "").replace("*^", "E");
        String[] numbers = result.split(",");
        DoubleResizeVector vector = new DoubleResizeVector(numbers.length);
        for (int i = 0; i < numbers.length; i++) {
            vector.set(i, Double.parseDouble(numbers[i]));
        }
        return vector;
    }

    //TODO:реалізувати підтримку поєднаних інтервалів
    @AnjiRewrite
    public static DoubleInterval fromWolframToDoubleInterval(String wolframInterval) {
        String[] splited = wolframInterval.split("<=");
        return new SimpleDoubleInterval(Double.parseDouble(splited[0].trim()), Double.parseDouble(splited[2].trim()));
    }



}
