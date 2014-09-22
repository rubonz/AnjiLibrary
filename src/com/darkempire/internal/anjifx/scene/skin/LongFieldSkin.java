package com.darkempire.internal.anjifx.scene.skin;

import com.darkempire.anjifx.scene.LongField;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Control;
import javafx.scene.control.SkinBase;
import javafx.scene.control.TextField;

import java.text.ParseException;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 22.11.13
 * Time: 19:07
 * To change this template use File | Settings | File Templates.
 */
public class LongFieldSkin extends SkinBase<LongField> {
    private LongField control;
    private TextField textField;

    public LongFieldSkin(LongField control) {
        super(control);
        this.control = control;
        createNodes();
        createListeners();
        getSkinnable().requestLayout();
    }

    public LongField getControl() {
        return control;
    }

    private void createNodes() {
        textField = new TextField();
        textField.getStyleClass().add("text-field");
        textField.setText(control.getNumberFormat().format(control.getValue()));
        this.getChildren().addAll(textField);
    }

    private void createListeners() {
        control.focusedProperty().addListener((observableValue, wasFocused, isFocused) -> {
            if (isFocused) {
                textField.requestFocus();
            }
        });
        textField.focusedProperty().addListener((observableValue, wasFocused, isFocused) -> {
            if (isFocused) {
                control.getStyleClass().add("long-field-focused");
            } else {
                control.getStyleClass().remove("long-field-focused");
            }
        });
        control.valueProperty().addListener((observable, oldValue, newValue) -> {
            textField.setText(control.getNumberFormat().format(newValue));
            textField.positionCaret(textField.getText().length());
        });
        textField.setOnAction(event -> parse());
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                parse();
            }
        });
    }

    private void parse() {
        try {
            Long value = (Long) control.getNumberFormat().parse(textField.getText());
            control.setValue(value);
        } catch (ParseException | IllegalArgumentException e) {
            textField.setText(control.getNumberFormat().format(control.getValue()));
            textField.positionCaret(textField.getText().length());
        }
    }

    @Override
    protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {
        super.layoutChildren(contentX, contentY, contentWidth, contentHeight);
        Insets insets = getSkinnable().getInsets();
        double x = insets.getLeft();
        double y = insets.getTop();
        double textfieldWidth = this.getSkinnable().getWidth() - insets.getLeft() - insets.getRight();
        layoutInArea(textField, x, y, textfieldWidth, contentHeight, Control.USE_PREF_SIZE, HPos.LEFT, VPos.TOP);
    }

    @Override
    protected double computePrefWidth(double PREF_WIDTH, double topInset, double rightInset, double bottomInset, double leftInset) {
        super.computePrefWidth(PREF_WIDTH, topInset, rightInset, bottomInset, leftInset);
        return getSkinnable().getInsets().getLeft()
                + textField.prefWidth(PREF_WIDTH)
                + textField.prefHeight(PREF_WIDTH)
                + getSkinnable().getInsets().getRight();
    }

    @Override
    protected double computePrefHeight(double PREF_HEIGHT, double topInset, double rightInset, double bottomInset, double leftInset) {
        super.computePrefHeight(PREF_HEIGHT, topInset, rightInset, bottomInset, leftInset);
        return getSkinnable().getInsets().getTop()
                + textField.prefHeight(PREF_HEIGHT)
                + getSkinnable().getInsets().getBottom();
    }
}