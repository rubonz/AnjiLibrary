package com.darkempire.anjifx.beans.value;

import com.darkempire.anjifx.annotation.AnjiFXClassConstructor;
import com.darkempire.anjifx.beans.BeanType;

import java.util.Collection;
import java.util.TreeSet;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 11.11.13
 * Time: 17:20
 * To change this template use File | Settings | File Templates.
 */
@AnjiFXClassConstructor(isEmptyExist = true, constructorParams = {"@org.simpleframework.xml.ElementList(name=\"set\",inline=true)java.util.Collection<String> value"})
public class AnjiChooseStringValue extends AbstractAnjiValue<String> {
    private String currentValue;
    private Collection<String> set;

    public AnjiChooseStringValue() {
        set = new TreeSet<>();
        currentValue = null;
    }

    public AnjiChooseStringValue(Collection<String> set) {
        this.set = set;
        currentValue = set.iterator().next();
    }

    @Override
    protected void set(String value) {
        if (set.contains(value)) {
            this.currentValue = value;
        } else {
            currentValue = null;
        }
    }

    public boolean contains(String s) {
        return set.contains(s);
    }

    public Collection<String> getSet() {
        return set;
    }

    @Override
    public String getValue() {
        return currentValue;
    }

    @Override
    public BeanType getType() {
        return BeanType.CHOOSE_TYPE;
    }
}
