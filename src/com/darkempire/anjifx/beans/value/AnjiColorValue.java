package com.darkempire.anjifx.beans.value;

import com.darkempire.anjifx.beans.BeanType;
import com.darkempire.anjifx.beans.IAnjiValue;
import javafx.scene.paint.Color;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 27.11.13
 * Time: 11:55
 * To change this template use File | Settings | File Templates.
 */
public class AnjiColorValue extends AbstractAnjiValue<Color> implements IAnjiValue<Color> {
    private Color value;

    public AnjiColorValue(Color value) {
        this.value = value;
    }

    @Override
    protected void set(Color value) {
        this.value = value;
    }

    @Override
    public BeanType getType() {
        return BeanType.COLOR_TYPE;
    }

    public void setValue(double red, double green, double blue) {
        setValue(Color.color(red, green, blue));
    }

    public void setValue(double red, double green, double blue, double opacity) {
        setValue(Color.color(red, green, blue, opacity));
    }

    @Override
    public Color getValue() {
        return value;
    }

    public void setOpticaly(double value) {
        this.value = Color.color(this.value.getRed(), this.value.getGreen(), this.value.getBlue(), value);
        setValue(this.value);
    }
}
