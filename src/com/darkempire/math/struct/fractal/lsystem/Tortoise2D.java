package com.darkempire.math.struct.fractal.lsystem;

import com.darkempire.math.struct.geometry.geomerty2d.Point2D;
import com.darkempire.math.struct.geometry.geomerty2d.Vector2D;

/**
 * Created by siredvin on 29.09.14.
 */
public class Tortoise2D {
    private Vector2D moveVector;
    private Point2D position;

    //region Конструктори
    public Tortoise2D(Vector2D moveVector, Point2D position) {
        this.moveVector = moveVector;
        this.position = position;
    }
    //endregion

    //region Геттери
    public Vector2D getMoveVector() {
        return moveVector;
    }

    public Point2D getPosition() {
        return position;
    }
    //endregion

    //region Сеттери
    public void setMoveVector(Vector2D moveVector) {
        this.moveVector = moveVector;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }
    //endregion

    //region Операції над черепашкою
    public void rotate(double angle) {
        moveVector.rotate(angle);
    }

    public void step() {
        position.iadd(moveVector.getX(), moveVector.getY());
    }
    //endregion
}
