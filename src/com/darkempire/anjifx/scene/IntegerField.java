package com.darkempire.anjifx.scene;

import com.darkempire.anji.annotation.AnjiRewrite;
import com.darkempire.anjifx.beans.property.AnjiIntegerProperty;
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
public class IntegerField extends TextField {
    private AnjiIntegerProperty valueProperty;
    private AnjiObjectProperty<NumberFormat> numberFormatProperty;

    //region Конструктори
    public IntegerField(int v) {
        super();
        setStyle(null);
        setFocusTraversable(true);
        getStyleClass().add("integer-field");
        valueProperty = new AnjiIntegerProperty(this, "value", v);
        numberFormatProperty = new AnjiObjectProperty<>(this, "numberFormat", NumberFormat.getIntegerInstance());
        setText(getNumberFormat().format(getValue()));
        focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                parse();
            }
        });
        Bindings.bindBidirectional(textProperty(), valueProperty, new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                return getNumberFormat().format(object);
            }

            @Override
            public Integer fromString(String string) {
                int result = valueProperty.get();
                try {
                    result = getNumberFormat().parse(string).intValue();
                } catch (ParseException e) {
                    setText(getNumberFormat().format(result));
                }
                return result;
            }
        });
    }

    public IntegerField() {
        this(0);
    }
    //endregion

    private void parse() {
        try {
            Integer value = getNumberFormat().parse(getText()).intValue();
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
    //endregion

    //region Сеттери
    public void setValue(Integer value) {
        valueProperty.setValue(value);
    }

    public void setNumberFormat(NumberFormat numberFormat) {
        numberFormatProperty.setValue(numberFormat);
    }
    //endregion

    //region Властивості
    public AnjiIntegerProperty valueProperty() {
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