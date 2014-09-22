package com.darkempire.anjifx.beans.value;

import com.darkempire.anjifx.annotation.AnjiFXClassConstructor;
import com.darkempire.anjifx.beans.BeanType;

import java.time.LocalDate;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 11.11.13
 * Time: 17:26
 * To change this template use File | Settings | File Templates.
 */
@AnjiFXClassConstructor(isEmptyExist = false, constructorParams = {"@org.simpleframework.xml.Element(name = \"value\",inline = true) java.util.Date value"})
public class AnjiDateValue extends AbstractAnjiValue<LocalDate> {
    private LocalDate value;

    public AnjiDateValue() {
        value = LocalDate.now();
    }

    public AnjiDateValue(LocalDate value) {
        this.value = value;
    }

    @Override
    protected void set(LocalDate value) {
        this.value = value;
    }

    @Override
    public LocalDate getValue() {
        return value;
    }

    @Override
    public BeanType getType() {
        return BeanType.DATE_TYPE;
    }
}
