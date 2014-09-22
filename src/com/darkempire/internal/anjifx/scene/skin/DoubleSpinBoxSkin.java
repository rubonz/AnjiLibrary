/*
 * Copyright (c) 2012, JFXtras
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *      * Redistributions of source code must retain the above copyright
 *        notice, this list of conditions and the following disclaimer.
 *      * Redistributions in binary form must reproduce the above copyright
 *        notice, this list of conditions and the following disclaimer in the
 *        documentation and/or other materials provided with the distribution.
 *      * Neither the name of the <organization> nor the
 *        names of its contributors may be used to endorse or promote products
 *        derived from this software without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 *  DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.darkempire.internal.anjifx.scene.skin;

import com.darkempire.anjifx.scene.DoubleField;
import com.darkempire.anjifx.scene.DoubleSpinBox;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.control.Control;
import javafx.scene.control.SkinBase;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 27.11.13
 * Time: 9:21
 * To change this template use File | Settings | File Templates.
 */
public class DoubleSpinBoxSkin extends SkinBase<DoubleSpinBox> {
    private DoubleSpinBox control;
    private DoubleField textField;
    private StackPane btnUp;
    private StackPane btnDown;
    private Orientation orientation;
    private static final double ARROW_SIZE = 4;
    private static final double ARROW_HEIGHT = 0.7;
    private static final double ARROW_WIDTH = 0.7;

    public DoubleSpinBoxSkin(DoubleSpinBox control) {
        super(control);
        this.control = control;
        createNodes();
        initFocusSimulation();
        getSkinnable().requestLayout();
    }

    private void createNodes() {
        textField = new DoubleField();
        control.impl_setField(textField);
        textField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
                case UP:
                    textField.setValue(textField.getValue() + control.getStep());
                    break;
                case DOWN:
                    textField.setValue(textField.getValue() - control.getStep());
                    break;
            }
        });
        //
        // The Buttons are StackPanes with a Path on top
        //
        orientation = control.impl_buttonOrientaion();
        switch (orientation) {
            case VERTICAL:
                createUpDownButtons();
                this.getChildren().addAll(textField, btnUp, btnDown);
                break;
            case HORIZONTAL:
                createLeftRightButtons();
                this.getChildren().addAll(textField, btnDown, btnUp);
                break;
        }


        //
        // Mouse Handler for buttons
        //
        btnUp.setOnMouseClicked(arg0 -> textField.setValue(textField.getValue() + control.getStep()));
        btnDown.setOnMouseClicked(arg0 -> textField.setValue(textField.getValue() - control.getStep()));

    }

    private void createLeftRightButtons() {
        // Button Up
        btnUp = new StackPane();
        btnUp.getStyleClass().add("arrow-button");
        Path arrowUp = new Path();
        arrowUp.getStyleClass().add("spinner-arrow");
        arrowUp.getElements().addAll(new MoveTo(0, -ARROW_SIZE), new LineTo(ARROW_SIZE * ARROW_WIDTH, 0),
                new LineTo(0, ARROW_SIZE));
        btnUp.getChildren().add(arrowUp);

        // Button Down
        btnDown = new StackPane();
        btnDown.getStyleClass().add("arrow-button");
        Path arrowDown = new Path();
        arrowDown.getStyleClass().add("spinner-arrow");
        arrowDown.getElements().addAll(new MoveTo(0, -ARROW_SIZE), new LineTo(-ARROW_SIZE * ARROW_WIDTH, 0),
                new LineTo(0, ARROW_SIZE));
        btnDown.getChildren().add(arrowDown);
    }

    private void createUpDownButtons() {
        // Button Up
        btnUp = new StackPane();
        btnUp.getStyleClass().add("arrow-button");
        Path arrowUp = new Path();
        arrowUp.getStyleClass().add("spinner-arrow");
        arrowUp.getElements().addAll(new MoveTo(-ARROW_SIZE, 0), new LineTo(0, -ARROW_SIZE * ARROW_HEIGHT),
                new LineTo(ARROW_SIZE, 0));
        btnUp.getChildren().add(arrowUp);

        // Button Down
        btnDown = new StackPane();
        btnDown.getStyleClass().add("arrow-button");
        Path arrowDown = new Path();
        arrowDown.getStyleClass().add("spinner-arrow");
        arrowDown.getElements().addAll(new MoveTo(-ARROW_SIZE, 0), new LineTo(0, ARROW_SIZE * ARROW_HEIGHT),
                new LineTo(ARROW_SIZE, 0));
        btnDown.getChildren().add(arrowDown);
    }

    @Override
    protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {
        super.layoutChildren(contentX, contentY, contentWidth, contentHeight); //To change body of generated methods, choose Tools | Templates.
        Insets insets = getSkinnable().getInsets();
        double x = insets.getLeft();
        double y = insets.getTop();
        double buttonWidth = textField.prefHeight(-1);
        Insets buttonInsets = btnDown.getInsets();
        double textfieldWidth = this.getSkinnable().getWidth() - insets.getLeft() - insets.getRight() - buttonWidth - buttonInsets.getLeft() - buttonInsets.getRight();
        layoutInArea(textField, x, y, textfieldWidth, contentHeight, Control.USE_PREF_SIZE, HPos.LEFT, VPos.TOP);
        switch (orientation) {
            case VERTICAL:
                layoutInArea(btnUp, x + textfieldWidth + buttonInsets.getLeft(), y, buttonWidth, contentHeight / 2, Control.USE_PREF_SIZE, HPos.LEFT, VPos.TOP);
                layoutInArea(btnDown, x + textfieldWidth + buttonInsets.getLeft(), y + contentHeight / 2, buttonWidth, contentHeight / 2, Control.USE_PREF_SIZE, HPos.LEFT, VPos.TOP);
                break;
            case HORIZONTAL:
                layoutInArea(btnUp, x + textfieldWidth + buttonInsets.getLeft() + buttonWidth / 2, y, buttonWidth / 2, contentHeight, Control.USE_PREF_SIZE, HPos.LEFT, VPos.TOP);
                layoutInArea(btnDown, x + textfieldWidth + buttonInsets.getLeft(), y, buttonWidth / 2, contentHeight, Control.USE_PREF_SIZE, HPos.LEFT, VPos.TOP);
                break;
        }
    }


    @Override
    protected double computePrefWidth(double PREF_WIDTH, double topInset, double rightInset, double bottomInset, double leftInset) {
        super.computePrefWidth(PREF_WIDTH, topInset, rightInset, bottomInset, leftInset);
        double prefWidth = getSkinnable().getInsets().getLeft()
                + textField.prefWidth(PREF_WIDTH)
                + textField.prefHeight(PREF_WIDTH)
                + getSkinnable().getInsets().getRight();
        return prefWidth;
    }

    @Override
    protected double computePrefHeight(double PREF_HEIGHT, double topInset, double rightInset, double bottomInset, double leftInset) {
        super.computePrefHeight(PREF_HEIGHT, topInset, rightInset, bottomInset, leftInset);
        double prefHeight = getSkinnable().getInsets().getTop()
                + textField.prefHeight(PREF_HEIGHT)
                + getSkinnable().getInsets().getBottom();
        return prefHeight;
    }

    private void initFocusSimulation() {
        control.focusedProperty().addListener((observable, wasFocused, isFocused) -> {
            if (isFocused) {
                textField.requestFocus();
            }
        });
        textField.focusedProperty().addListener((ov, wasFocused, isFocused) -> {
            if (isFocused) {
                control.getStyleClass().add("integer-spin-box-focused");
            } else {
                control.getStyleClass().remove("integer-spin-box-focused");
            }
        });
    }
}
