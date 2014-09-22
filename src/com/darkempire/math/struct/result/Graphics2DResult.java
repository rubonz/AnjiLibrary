package com.darkempire.math.struct.result;

import com.darkempire.math.struct.geometry.geomerty2d.Point2D;
import com.darkempire.math.exception.SizeMissmatchException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 07.11.13
 * Time: 16:43
 * To change this template use File | Settings | File Templates.
 */
public class Graphics2DResult implements Iterable<Point2D> {
    private List<Point2D> points;
    private String name;

    public Graphics2DResult(String name) {
        this.name = name;
        points = new ArrayList<>();
    }

    public Graphics2DResult(String name, List<Point2D> points) {
        this.name = name;
        this.points = points;
    }

    public Graphics2DResult(String name, double[] x, double[] y) {
        this(name);
        if (x.length != y.length)
            throw new SizeMissmatchException();
        for (int i = 0; i < x.length; i++) {
            points.add(new Point2D(x[i], y[i]));
        }
    }

    public int size() {
        return points.size();
    }

    public void addPoint(Point2D point2D) {
        points.add(point2D);
    }

    public void addPoint(double x, double y) {
        points.add(new Point2D(x, y));
    }

    public Point2D getPoint(int index) {
        return points.get(index);
    }

    public String getName() {
        return name;
    }

    @Override
    public Iterator<Point2D> iterator() {
        return points.iterator();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(name);
        builder.append("values:{");
        for (Point2D point2D : points) {
            builder.append(point2D);
            builder.append(',');
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append("}");
        return builder.toString();
    }
}
