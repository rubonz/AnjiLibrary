package com.darkempire.anjifx.beans.validation;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 23.09.13
 * Time: 21:45
 * To change this template use File | Settings | File Templates.
 */
public class BooleanValidatorManager extends ValidationManager {
    private static BooleanValidatorManager ourInstance = new BooleanValidatorManager();

    public static BooleanValidatorManager getInstance() {
        return ourInstance;
    }

    private BooleanValidatorManager() {
    }

    @Override
    public ValidationType validate(String text) {
        try {
            Boolean.valueOf(text);
        } catch (IllegalArgumentException e) {
            return ValidationType.error;
        }
        return ValidationType.sucsess;
    }
}
