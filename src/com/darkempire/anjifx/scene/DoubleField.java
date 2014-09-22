package com.darkempire.anjifx.scene;

import com.darkempire.anjifx.beans.property.AnjiDoubleProperty;
import com.darkempire.anjifx.beans.property.AnjiObjectProperty;
import javafx.scene.control.TextField;

import java.text.NumberFormat;
import java.text.ParseException;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 22.11.13
 * Time: 19:04
 * To change this template use File | Settings | File Templates.
 */
//TODO: зробити bidirectional binding в усіх цих дивних полях.
public class DoubleField extends TextField {
    private static final int DEFAULT_FRACTION_DIGITS = 14;
    private AnjiDoubleProperty valueProperty;
    private AnjiObjectProperty<NumberFormat> numberFormatProperty;

    public DoubleField(double v) {
        super();
        //setStyle(null);
        //getStyleClass().add("double-field");
        valueProperty = new AnjiDoubleProperty(this, "value", v);
        numberFormatProperty = new AnjiObjectProperty<>(this, "numberFormat", NumberFormat.getNumberInstance());
        numberFormatProperty.getValue().setMaximumFractionDigits(DEFAULT_FRACTION_DIGITS);
        valueProperty().addListener((observable, oldValue, newValue) -> {
            setText(getNumberFormat().format(newValue));
            positionCaret(getText().length());
        });
        setOnAction(event -> parse());
        focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                parse();
            }
        });
    }

    public DoubleField() {
        this(0);
    }

    /**
     * ************Getters**************
     */
    public double getValue() {
        return valueProperty.get();
    }

    public NumberFormat getNumberFormat() {
        return numberFormatProperty.getValue();
    }

    /**
     * ************Setters**************
     */
    public void setValue(Double value) {
        valueProperty.setValue(value);
    }

    public void setNumberFormat(NumberFormat numberFormat) {
        numberFormatProperty.setValue(numberFormat);
    }

    /**
     * ************Property**************
     */
    public AnjiDoubleProperty valueProperty() {
        return valueProperty;
    }

    public AnjiObjectProperty<NumberFormat> numberFormatProperty() {
        return numberFormatProperty;
    }


    private void parse() {
        try {
            Double value = getNumberFormat().parse(getText()).doubleValue();
            setValue(value);
        } catch (ParseException | IllegalArgumentException e) {
            setText(getNumberFormat().format(getValue()));
            positionCaret(getText().length());
        }
    }
}