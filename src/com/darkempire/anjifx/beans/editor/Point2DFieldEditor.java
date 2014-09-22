package com.darkempire.anjifx.beans.editor;

import com.darkempire.anjifx.beans.property.AnjiPoint2DProperty;
import com.darkempire.anjifx.scene.DoubleField;
import com.darkempire.math.struct.geometry.geomerty2d.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 28.10.13
 * Time: 11:36
 * To change this template use File | Settings | File Templates.
 */
public class Point2DFieldEditor extends HBox implements IAnjiPropertyEditor<Point2D> {
    private AnjiPoint2DProperty anjiPoint2DBean;

    public Point2DFieldEditor() {
        this(0, 0);
    }

    public Point2DFieldEditor(double x, double y) {
        DoubleField x1 = new DoubleField(x);
        DoubleField y1 = new DoubleField(y);
        this.setSpacing(5);
        Label xl = new Label("x=");
        Label yl = new Label("y=");
        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(xl, x1, yl, y1);
        anjiPoint2DBean = new AnjiPoint2DProperty(this, "value", new Point2D(x, y));
        x1.valueProperty().addListener((observable, oldValue, newValue) -> anjiPoint2DBean.setX(newValue));
        y1.valueProperty().addListener((observable, oldValue, newValue) -> anjiPoint2DBean.setY(newValue));
    }

    public Point2DFieldEditor(Point2D point2DBean) {
        this(point2DBean.getX(), point2DBean.getY());
    }

    @Override
    public AnjiPoint2DProperty valueProperty() {
        return anjiPoint2DBean;
    }
}
