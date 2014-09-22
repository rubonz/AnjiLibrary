package com.darkempire.anjifx.scene;

import com.darkempire.anjifx.beans.property.AnjiLongProperty;
import com.darkempire.anjifx.beans.property.transform.Transform;
import javafx.scene.control.Slider;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 27.11.13
 * Time: 17:22
 * To change this template use File | Settings | File Templates.
 */
public class LongSlider extends Slider {
    private AnjiLongProperty longProperty;

    public LongSlider() {
        this(0, 100, 50);
    }

    public LongSlider(double min, double max) {
        this(min, max, (min + max) / 2);
    }

    public LongSlider(double min, double max, double value) {
        super(min, max, value);
        this.setShowTickMarks(true);
        this.setMinorTickCount((int) (max - min) + 1);
        longProperty = new AnjiLongProperty(this, "longProperty", (long) value);
        longProperty.bind(Transform.controlTransformNumberToLong(valueProperty()));
    }

    public AnjiLongProperty longProperty() {
        return longProperty;
    }
}
