package com.darkempire.anjifx.scene;

import com.darkempire.anji.annotation.AnjiRewrite;
import com.darkempire.anjifx.beans.property.AnjiBoundedLongProperty;
import com.darkempire.anjifx.beans.property.AnjiLongProperty;
import javafx.geometry.Orientation;
import javafx.scene.control.Control;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 27.11.13
 * Time: 9:19
 * To change this template use File | Settings | File Templates.
 */
@AnjiRewrite
public class LongSpinBox extends Control {
    protected LongField inheritedField;
    private Orientation orientation;

    public LongSpinBox() {
        super();
        inheritedField = new LongField();
        setStyle(null);
        getStyleClass().add("long-spin-box");
        orientation = Orientation.VERTICAL;
    }

    public LongSpinBox(Orientation orientation) {
        this();
        this.orientation = orientation;
    }

    public void setValue(Long value) {
        inheritedField.setValue(value);
    }

    public void setMinValue(long min) {
        inheritedField.setMinValue(min);
    }

    public void setMaxValue(long max) {
        inheritedField.setMaxValue(max);
    }

    public long getValue() {
        return inheritedField.getValue();
    }

    public long getMaxValue() {
        return inheritedField.getMaxValue();
    }

    public long getMinValue() {
        return inheritedField.getMinValue();
    }

    public AnjiBoundedLongProperty valueProperty() {
        return inheritedField.valueProperty();
    }

    @Deprecated
    public LongField impl_getField() {
        return inheritedField;
    }

    @Deprecated
    public Orientation impl_buttonOrientaion() {
        return orientation;
    }

    @Override
    protected String getUserAgentStylesheet() {
        return getClass().getResource("/com/darkempire/res/css/spinbox.css").toExternalForm();
    }
}
