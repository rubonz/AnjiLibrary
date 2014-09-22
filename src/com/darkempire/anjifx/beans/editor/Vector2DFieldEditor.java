package com.darkempire.anjifx.beans.editor;

import com.darkempire.anjifx.beans.property.AnjiVector2DProperty;
import com.darkempire.anjifx.scene.DoubleField;
import com.darkempire.math.struct.geometry.geomerty2d.Vector2D;
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
public class Vector2DFieldEditor extends HBox implements IAnjiPropertyEditor<Vector2D> {
    private AnjiVector2DProperty anjiVector2DBean;

    public Vector2DFieldEditor() {
        this(0, 0);
    }

    public Vector2DFieldEditor(double x, double y) {
        DoubleField x1 = new DoubleField(x);
        DoubleField y1 = new DoubleField(y);
        this.setSpacing(5);
        Label xl = new Label("x=");
        Label yl = new Label("y=");
        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(xl, x1, yl, y1);
        anjiVector2DBean = new AnjiVector2DProperty(this, "value", new Vector2D(x, y));
        x1.valueProperty().addListener((observable, oldValue, newValue) -> anjiVector2DBean.setX(newValue));
        y1.valueProperty().addListener((observable, oldValue, newValue) -> anjiVector2DBean.setY(newValue));
    }

    public Vector2DFieldEditor(Vector2D vector2DBean) {
        this(vector2DBean.getX(), vector2DBean.getY());
    }

    @Override
    public AnjiVector2DProperty valueProperty() {
        return anjiVector2DBean;
    }
}
