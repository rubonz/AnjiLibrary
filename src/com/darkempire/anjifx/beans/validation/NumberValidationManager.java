package com.darkempire.anjifx.beans.validation;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 20.09.13
 * Time: 11:29
 * To change this template use File | Settings | File Templates.
 */
public class NumberValidationManager extends ValidationManager {
    private static NumberValidationManager ourInstance = new NumberValidationManager();

    public static NumberValidationManager getInstance() {
        return ourInstance;
    }

    private NumberValidationManager() {
    }

    @Override
    public ValidationType validate(String text) {
        try {
            Integer.valueOf(text);
        } catch (Exception e) {
            return ValidationType.error;
        }
        return ValidationType.sucsess;
    }
}
