package com.darkempire.anjifx.scene;

import com.darkempire.anji.annotation.AnjiRewrite;
import com.darkempire.anjifx.beans.property.AnjiBoundedLongProperty;
import com.darkempire.anjifx.beans.property.AnjiLongProperty;
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
public class LongField extends TextField {
    private AnjiBoundedLongProperty valueProperty;
    private AnjiObjectProperty<NumberFormat> numberFormatProperty;

    //region Конструктори
    public LongField(long v) {
        super();
        setStyle(null);
        setFocusTraversable(true);
        getStyleClass().add("long-field");
        valueProperty = new AnjiBoundedLongProperty(this, "value", Long.MIN_VALUE, Long.MAX_VALUE, v);
        numberFormatProperty = new AnjiObjectProperty<>(this, "numberFormat", NumberFormat.getIntegerInstance());
        setText(getNumberFormat().format(getValue()));
        focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                parse();
            }
        });
        Bindings.bindBidirectional(textProperty(), valueProperty, new StringConverter<Long>() {
            @Override
            public String toString(Long object) {
                return getNumberFormat().format(object);
            }

            @Override
            public Long fromString(String string) {
                long result = valueProperty.get();
                try {
                    result = getNumberFormat().parse(string).longValue();
                } catch (ParseException e) {
                    setText(getNumberFormat().format(result));
                }
                return result;
            }
        });
    }


    public LongField() {
        this(0);
    }
    //endregion

    private void parse() {
        try {
            Long value = getNumberFormat().parse(getText()).longValue();
            setValue(value);
        } catch (ParseException | IllegalArgumentException e) {
            setText(getNumberFormat().format(getValue()));
            positionCaret(getText().length());
        }
    }

    //region Геттери
    public long getValue() {
        return valueProperty.get();
    }


    public NumberFormat getNumberFormat() {
        return numberFormatProperty.getValue();
    }

    public long getMinValue() {
        return valueProperty.getMinValue();
    }

    public long getMaxValue() {
        return valueProperty.getMaxValue();
    }
    //endregion

    //region Сеттери
    public void setValue(Long value) {
        valueProperty.setValue(value);
    }

    public void setNumberFormat(NumberFormat numberFormat) {
        numberFormatProperty.setValue(numberFormat);
    }

    public void setMaxValue(long max) {
        valueProperty.setMaxValue(max);
    }

    public void setMinValue(long min) {
        valueProperty.setMinValue(min);
    }
    //endregion

    //region Властивості
    public AnjiBoundedLongProperty valueProperty() {
        return valueProperty;
    }

    public AnjiObjectProperty<NumberFormat> numberFormatProperty() {
        return numberFormatProperty;
    }
    //endregion

    @Override
    protected String getUserAgentStylesheet() {
        return getClass().getResource("/com/darkempire/res/css/fields.css").toExternalForm();
    }
}