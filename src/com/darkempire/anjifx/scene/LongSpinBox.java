package com.darkempire.anjifx.scene;

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
public class LongSpinBox extends Control {
    private LongField inheritedField;
    private Orientation orientation;

    public LongSpinBox() {
        super();
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

    public long getValue() {
        return inheritedField.getValue();
    }

    public AnjiLongProperty valueProperty() {
        return inheritedField.valueProperty();
    }

    @Deprecated
    public void impl_setField(LongField field) {
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
