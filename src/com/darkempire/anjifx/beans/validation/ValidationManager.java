package com.darkempire.anjifx.beans.validation;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 20.09.13
 * Time: 11:25
 * To change this template use File | Settings | File Templates.
 */
public abstract class ValidationManager {
    public static final String warningStyle = "-fx-background-color: yellow;";
    public static final String errorStyle = "-fx-background-color: red;\n" +
            "    -fx-text-fill: white;";
    public static final String sucssesStyle = "";

    public abstract ValidationType validate(String text);
}
