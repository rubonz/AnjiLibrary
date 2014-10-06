package com.darkempire.anjifx.scene;

import com.darkempire.anji.annotation.AnjiRewrite;
import com.darkempire.anjifx.beans.property.AnjiFloatProperty;
import com.darkempire.anjifx.beans.property.AnjiObjectProperty;
import javafx.beans.binding.Bindings;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.text.NumberFormat;
import java.text.ParseException;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 22.11.13
 * Time: 19:04
 * To change this template use File | Settings | File Templates.
 */
@AnjiRewrite
public class FloatField extends TextField {
    private static final int DEFAULT_FRACTION_DIGITS = 14;
    private AnjiFloatProperty valueProperty;
    private AnjiObjectProperty<NumberFormat> numberFormatProperty;

    //region Конструктори
    public FloatField(float v) {
        super();
        setStyle(null);
        getStyleClass().add("double-field");
        valueProperty = new AnjiFloatProperty(this, "value", v);
        numberFormatProperty = new AnjiObjectProperty<>(this, "numberFormat", NumberFormat.getNumberInstance());
        numberFormatProperty.getValue().setMaximumFractionDigits(DEFAULT_FRACTION_DIGITS);
        focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                parse();
            }
        });
        Bindings.bindBidirectional(textProperty(), valueProperty, new StringConverter<Float>() {
            @Override
            public String toString(Float object) {
                return getNumberFormat().format(object);
            }

            @Override
            public Float fromString(String string) {
                float result = valueProperty.get();
                try {
                    result = getNumberFormat().parse(string).floatValue();
                } catch (ParseException e) {
                    setText(getNumberFormat().format(result));
                }
                return result;
            }
        });
    }

    public FloatField() {
        this(0);
    }
    //endregion

    //region Геттери
    public double getValue() {
        return valueProperty.get();
    }

    public NumberFormat getNumberFormat() {
        return numberFormatProperty.getValue();
    }
    //endregion

    //region Сеттери
    public void setValue(Float value) {
        valueProperty.setValue(value);
    }

    public void setNumberFormat(NumberFormat numberFormat) {
        numberFormatProperty.setValue(numberFormat);
    }
    //endregion

    //region Властивості
    public AnjiFloatProperty valueProperty() {
        return valueProperty;
    }

    public AnjiObjectProperty<NumberFormat> numberFormatProperty() {
        return numberFormatProperty;
    }
    //endregion

    private void parse() {
        try {
            Float value = getNumberFormat().parse(getText()).floatValue();
            setValue(value);
        } catch (ParseException | IllegalArgumentException e) {
            setText(getNumberFormat().format(getValue()));
            positionCaret(getText().length());
        }
    }
}