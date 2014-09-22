package com.darkempire.math.struct;

/**
 * Created by siredvin on 02.07.14.
 */
public enum SignType {
    GREAT_THEN, LOWER_THEN, GREAT_THEN_OR_EQL, LOWER_THEN_OR_EQL, EQL, NO_EQL;

    @Override
    public String toString() {
        String temp = null;
        switch (this) {
            case GREAT_THEN:
                temp = ">";
                break;
            case LOWER_THEN:
                temp = "<";
                break;
            case GREAT_THEN_OR_EQL:
                temp = ">=";
                break;
            case LOWER_THEN_OR_EQL:
                temp = "<=";
                break;
            case EQL:
                temp = "=";
                break;
            case NO_EQL:
                temp = "!=";
                break;
        }
        return temp;
    }

    public String toTeXString() {
        String temp = null;
        switch (this) {
            case GREAT_THEN:
                temp = ">";
                break;
            case LOWER_THEN:
                temp = "<";
                break;
            case GREAT_THEN_OR_EQL:
                temp = "\\geq";
                break;
            case LOWER_THEN_OR_EQL:
                temp = "\\leq";
                break;
            case EQL:
                temp = "=";
                break;
            case NO_EQL:
                temp = "\\neq";
                break;
        }
        return temp;
    }
}
