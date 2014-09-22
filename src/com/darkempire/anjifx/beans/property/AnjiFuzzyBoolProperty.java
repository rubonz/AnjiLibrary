package com.darkempire.anjifx.beans.property;

import com.darkempire.anjifx.beans.BeanType;
import com.darkempire.math.struct.logic.FuzzyBool;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 11.11.13
 * Time: 17:18
 * To change this template use File | Settings | File Templates.
 */
public class AnjiFuzzyBoolProperty extends AbstractAnjiProperty<FuzzyBool> {
    private FuzzyBool value;

    public AnjiFuzzyBoolProperty(String name, FuzzyBool value) {
        super(name);
        this.value = value;
    }

    public AnjiFuzzyBoolProperty(Object bean, String name, FuzzyBool value) {
        super(bean, name);
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
