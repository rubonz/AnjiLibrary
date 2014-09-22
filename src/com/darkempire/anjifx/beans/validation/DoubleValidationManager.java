package com.darkempire.anjifx.beans.validation;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 20.09.13
 * Time: 11:32
 * To change this template use File | Settings | File Templates.
 */
public class DoubleValidationManager extends ValidationManager {
    private static DoubleValidationManager ourInstance = new DoubleValidationManager();

    public static DoubleValidationManager getInstance() {
        return ourInstance;
    }

    private DoubleValidationManager() {
    }

    @Override
    public ValidationType validate(String text) {
        try {
            Double.valueOf(text);
        } catch (Exception e) {
            return ValidationType.error;
        }
        return ValidationType.sucsess;
    }
}
