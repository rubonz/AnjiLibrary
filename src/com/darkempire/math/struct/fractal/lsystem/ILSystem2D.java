package com.darkempire.math.struct.fractal.lsystem;

import com.darkempire.math.struct.geometry.geomerty2d.Point2D;
import com.darkempire.math.struct.geometry.geomerty2d.Vector2D;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 02.11.13
 * Time: 22:13
 * To change this template use File | Settings | File Templates.
 */
public interface ILSystem2D extends ILSystem {
    public Point2D getPosition();

    public Vector2D getVector();
}
