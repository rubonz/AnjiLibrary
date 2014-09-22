package com.darkempire.anjifx.scene;

import com.darkempire.anjifx.beans.property.AbstractAnjiReadOnlyProperty;
import javafx.scene.control.Slider;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 27.11.13
 * Time: 17:22
 * To change this template use File | Settings | File Templates.
 */
public class DoubleSlider extends Slider {
    private AbstractAnjiReadOnlyProperty<Double> doubleProperty = new AbstractAnjiReadOnlyProperty<Double>() {
        @Override
        public Double getValue() {
            return valueProperty().getValue();
        }
    };

    public DoubleSlider() {
        super();
    }

    public DoubleSlider(double min, double max) {
        this(min, max, (min + max) / 2);
    }

    public DoubleSlider(double min, double max, double value) {
        super(min, max, value);
        this.valueProperty().addListener((observableValue, oldValue, newValue) -> doubleProperty.handle(oldValue.doubleValue()));
    }

    public AbstractAnjiReadOnlyProperty<Double> doubleProperty() {
        return doubleProperty;
    }
}
