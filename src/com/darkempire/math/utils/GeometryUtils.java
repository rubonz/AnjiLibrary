package com.darkempire.math.utils;

import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.math.struct.geometry.geometrynd.FixedLine;
import com.darkempire.math.struct.geometry.geometrynd.FixedPoint;
import com.darkempire.math.struct.set.SimpleDoubleInterval;
import com.darkempire.math.struct.vector.DoubleVector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 14.11.13
 * Time: 11:40
 * To change this template use File | Settings | File Templates.
 */
@AnjiUtil
public final class GeometryUtils {
    private GeometryUtils() {
    }

    /**
     * Поворот лінію за площиною через поворот її вершин
     *
     * @param line  лінія
     * @param i     індекс першої координати площини
     * @param j     індекс другої координати площини
     * @param angle кут повороту
     * @return змінена лінія
     */
    public static FixedLine rotateLine(FixedLine line, int i, int j, double angle) {
        rotatePoint(line.getStart(), i, j, angle);
        rotatePoint(line.getEnd(), i, j, angle);
        return line;
    }

    /**
     * Поворот точки за площиною
     *
     * @param point точка
     * @param i     індекс першої координати площини
     * @param j     індекс другої координати площини
     * @param angle кут повороту
     * @return змінена точка
     */
    public static FixedPoint rotatePoint(FixedPoint point, int i, int j, double angle) {
        final double xi = point.getCoord(i);
        final double xj = point.getCoord(j);
        point.setCoord(i, xi * Math.cos(angle) - xj * Math.sin(angle));
        point.setCoord(j, xi * Math.sin(angle) + xj * Math.cos(angle));
        return point;
    }

    /**
     * Поворот вектору за площиною
     *
     * @param vector вектор
     * @param i      індекс першої координати площини
     * @param j      індекс другої координати площини
     * @param angle  кут повороту
     * @return змінений вектор
     */
    public static DoubleVector rotateVector(DoubleVector vector, int i, int j, double angle) {
        final double xi = vector.get(i);
        final double xj = vector.get(j);
        vector.set(i, xi * Math.cos(angle) - xj * Math.sin(angle));
        vector.set(j, xi * Math.sin(angle) + xj * Math.cos(angle));
        return vector;
    }

    /**
     * Генерує купу інтервалів у вигляді [x_0,x_1],[x_1,x_2],...
     *
     * @param values [x_0,x_1,x_2,...]
     * @return інтервали
     */
    public static List<SimpleDoubleInterval> generateIntervals(double... values) {
        List<SimpleDoubleInterval> result = new ArrayList<>();
        int size = values.length - 1;
        for (int i = 0; i < size; i++) {
            result.add(new SimpleDoubleInterval(values[i], values[i + 1]));
        }
        return result;
    }
}
