package com.darkempire.anjifx.scene;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;

/**
 * Create in 11:00
 * Created by siredvin on 18.04.14.
 */
public class BindingButton extends Button {
    private Control bean;

    public BindingButton(Control bean, String text) {
        super(text);
        this.bean = bean;
    }

    public BindingButton(Control bean, String text, Node graphics) {
        super(text, graphics);
        this.bean = bean;
    }

    public Control getBean() {
        return bean;
    }
}
