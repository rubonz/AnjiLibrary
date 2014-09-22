package com.darkempire.anjifx.beans.validation;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 20.09.13
 * Time: 11:28
 * To change this template use File | Settings | File Templates.
 */
public class SimpleValidationManager extends ValidationManager {
    private static SimpleValidationManager ourInstance = new SimpleValidationManager();

    public static SimpleValidationManager getInstance() {
        return ourInstance;
    }

    private SimpleValidationManager() {
    }

    @Override
    public ValidationType validate(String text) {
        return ValidationType.sucsess;
    }
}
