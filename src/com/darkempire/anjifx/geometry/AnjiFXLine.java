package com.darkempire.anjifx.geometry;

import com.darkempire.math.struct.geometry.geometrynd.FixedPoint;
import com.darkempire.math.utils.GeometryUtils;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 07.11.13
 * Time: 8:14
 * To change this template use File | Settings | File Templates.
 */
public class AnjiFXLine implements AnjiShape {
    private FixedPoint start;
    private FixedPoint end;
    private Line line;

    public AnjiFXLine(FixedPoint start, FixedPoint end) {
        this.start = start;
        this.end = end;
        line = new Line(start.getCoord(0), start.getCoord(1), end.getCoord(0), end.getCoord(1));
        line.startXProperty().bind(new DoubleBinding() {
            @Override
            protected double computeValue() {
                return AnjiFXLine.this.start.getCoord(0);
            }
        });
        line.startYProperty().bind(new DoubleBinding() {
            @Override
            protected double computeValue() {
                return AnjiFXLine.this.start.getCoord(1);
            }
        });
        line.endXProperty().bind(new DoubleBinding() {
            @Override
            protected double computeValue() {
                return AnjiFXLine.this.end.getCoord(0);
            }
        });
        line.endYProperty().bind(new DoubleBinding() {
            @Override
            protected double computeValue() {
                return AnjiFXLine.this.end.getCoord(1);
            }
        });
    }

    public void rotate(int i, int j, double angle) {
        GeometryUtils.rotatePoint(start, i, j, angle);
        GeometryUtils.rotatePoint(end, i, j, angle);
    }

    public ObjectProperty<Paint> strokeProperty() {
        return line.strokeProperty();
    }

    public ObjectProperty<Paint> fillProperty() {
        return line.fillProperty();
    }

    public DoubleProperty translateXProperty() {
        return line.translateXProperty();
    }

    public DoubleProperty translateYProperty() {
        return line.translateYProperty();
    }

    public DoubleProperty translateZProperty() {
        return line.translateZProperty();
    }

    public DoubleProperty scaleXProperty() {
        return line.scaleXProperty();
    }

    public DoubleProperty scaleYProperty() {
        return line.scaleYProperty();
    }

    public DoubleProperty scaleZProperty() {
        return line.scaleZProperty();
    }

    public void setOnMouseClicked(EventHandler<MouseEvent> event) {
        line.setOnMouseClicked(event);
    }

    public DoubleProperty strokeWidthProperty() {
        return line.strokeWidthProperty();
    }

    @Override
    public Shape projection() {
        return line;
    }

    @Override
    public int getDimension() {
        return start.getDimension();
    }
}
