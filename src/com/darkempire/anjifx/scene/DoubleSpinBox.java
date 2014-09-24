package com.darkempire.anjifx.scene;

import com.darkempire.anjifx.beans.property.AnjiDoubleProperty;
import javafx.geometry.Orientation;
import javafx.scene.control.Control;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 27.11.13
 * Time: 9:19
 * To change this template use File | Settings | File Templates.
 */
public class DoubleSpinBox extends Control {
    private static final double DEFAULT_STEP = 0.01;
    private DoubleField inheritedField;
    private AnjiDoubleProperty stepProperty;
    private Orientation orientation;

    public DoubleSpinBox() {
        super();
        setStyle(null);
        getStyleClass().add("double-spin-box");
        orientation = Orientation.VERTICAL;
        stepProperty = new AnjiDoubleProperty(this, "step", DEFAULT_STEP);
    }

    public DoubleSpinBox(double step) {
        this();
        setStep(step);
    }

    public DoubleSpinBox(double step, Orientation orientation) {
        this();
        setStep(step);
        this.orientation = orientation;
    }

    public void setValue(Double value) {
        inheritedField.setValue(value);
    }

    public double getValue() {
        return inheritedField.getValue();
    }

    public AnjiDoubleProperty valueProperty() {
        return inheritedField.valueProperty();
    }

    public void setStep(Double stepProperty) {
        this.stepProperty.setValue(stepProperty);
    }

    public double getStep() {
        return stepProperty.get();
    }

    public AnjiDoubleProperty stepProperty() {
        return stepProperty;
    }

    @Deprecated
    public void impl_setField(DoubleField field) {
        inheritedField = field;
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
