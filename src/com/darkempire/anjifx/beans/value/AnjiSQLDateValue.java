package com.darkempire.anjifx.beans.value;

import com.darkempire.anjifx.annotation.AnjiFXClassConstructor;
import com.darkempire.anjifx.beans.BeanType;

import java.sql.Date;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 11.11.13
 * Time: 17:26
 * To change this template use File | Settings | File Templates.
 */
@AnjiFXClassConstructor(isEmptyExist = false, constructorParams = {"@org.simpleframework.xml.Element(name = \"value\",inline = true) java.sql.Date value"})
public class AnjiSQLDateValue extends AbstractAnjiValue<Date> {
    private Date value;

    public AnjiSQLDateValue() {
        value = new Date(new java.util.Date().getTime());
    }

    public AnjiSQLDateValue(Date value) {
        this.value = value;
    }

    @Override
    protected void set(Date value) {
        this.value = value;
    }

    @Override
    public Date getValue() {
        return value;
    }

    @Override
    public BeanType getType() {
        return BeanType.SQLDATE_TYPE;
    }
}
