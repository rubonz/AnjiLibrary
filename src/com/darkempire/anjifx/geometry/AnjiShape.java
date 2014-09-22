package com.darkempire.anjifx.geometry;

import javafx.scene.shape.Shape;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 07.11.13
 * Time: 15:53
 * To change this template use File | Settings | File Templates.
 */
public interface AnjiShape {
    public Shape projection();

    public int getDimension();
}
