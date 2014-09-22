package com.darkempire.anjifx.beans.value;

import com.darkempire.anjifx.annotation.AnjiFXClassConstructor;
import com.darkempire.anjifx.beans.BeanType;
import com.darkempire.math.struct.logic.FuzzyBool;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 11.11.13
 * Time: 17:18
 * To change this template use File | Settings | File Templates.
 */
@AnjiFXClassConstructor(isEmptyExist = false)
public class AnjiFuzzyBoolValue extends AbstractAnjiValue<FuzzyBool> {
    private FuzzyBool value;

    public AnjiFuzzyBoolValue(FuzzyBool value) {
        this.value = value;
    }

    @Override
    protected void set(FuzzyBool value) {
        this.value = value;
    }

    @Override
    public FuzzyBool getValue() {
        return value;
    }

    @Override
    public BeanType getType() {
        return BeanType.FUZZYBOOL_TYPE;
    }
}
