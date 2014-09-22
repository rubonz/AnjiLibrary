package com.darkempire.math.struct.geometry.geometrynd;

import com.darkempire.math.exception.SizeMissmatchException;
import com.darkempire.math.utils.GeometryUtils;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 07.11.13
 * Time: 8:14
 * To change this template use File | Settings | File Templates.
 */
public class FixedLine {
    private FixedPoint start;
    private FixedPoint end;

    //region Конструктори
    public FixedLine(FixedPoint start, FixedPoint end) {
        if (start.getDimension() != end.getDimension())
            throw new SizeMissmatchException();
        this.start = start;
        this.end = end;
    }
    //endregion

    //region Геттери
    public FixedPoint getStart() {
        return start;
    }

    public FixedPoint getEnd() {
        return end;
    }

    public int getDimension() {
        return start.getDimension();
    }
    //endregion

    //region Сеттери
    public void setStart(FixedPoint pointND) {
        start.setPoint(pointND);
    }

    public void setEnd(FixedPoint pointND) {
        end.setPoint(pointND);
    }
    //endregion

    //region Повороти лінії
    public void rotate(int i, int j, double angle) {
        GeometryUtils.rotateLine(this, i, j, angle);
    }
    //endregion
}
