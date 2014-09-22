package com.darkempire.anjifx.scene;

import com.darkempire.anjifx.beans.property.AnjiLongProperty;
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
//TODO:а ось тут потрібно дуже гарно подумати, як цю штуку переробити. Оскільки фокуси воно не ловить і взагалі нічого не робить. Це якась маячня!
public class LongField extends TextField {
    private AnjiLongProperty valueProperty;
    private AnjiObjectProperty<NumberFormat> numberFormatProperty;

    public LongField(long v) {
        super();
        //setStyle(null);
        //setFocusTraversable(true);
        //getStyleClass().add("long-field");
        valueProperty = new AnjiLongProperty(this, "value", v);
        numberFormatProperty = new AnjiObjectProperty<>(this, "numberFormat", NumberFormat.getIntegerInstance());
        setText(getNumberFormat().format(getValue()));
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

    private void parse() {
        try {
            Long value = (Long) getNumberFormat().parse(getText());
            setValue(value);
        } catch (ParseException | IllegalArgumentException e) {
            setText(getNumberFormat().format(getValue()));
            positionCaret(getText().length());
        }
    }

    public LongField() {
        this(0);
    }

    /**
     * ************Getters**************
     */
    public long getValue() {
        return valueProperty.get();
    }


    public NumberFormat getNumberFormat() {
        return numberFormatProperty.getValue();
    }

    /**
     * ************Setters**************
     */
    public void setValue(Long value) {
        valueProperty.setValue(value);
    }

    public void setNumberFormat(NumberFormat numberFormat) {
        numberFormatProperty.setValue(numberFormat);
    }

    /**
     * ************Property**************
     */
    public AnjiLongProperty valueProperty() {
        return valueProperty;
    }

    public AnjiObjectProperty<NumberFormat> numberFormatProperty() {
        return numberFormatProperty;
    }

    @Override
    protected String getUserAgentStylesheet() {
        return getClass().getResource("/com/com.darkempire/res/css/fields.css").toExternalForm();
    }
}