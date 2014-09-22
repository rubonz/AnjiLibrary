package com.darkempire.anjifx.beans.property;

import com.darkempire.anjifx.beans.BeanType;

import java.sql.Date;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 11.11.13
 * Time: 17:26
 * To change this template use File | Settings | File Templates.
 */
public class AnjiSQLDateProperty extends AbstractAnjiProperty<Date> {
    private Date value;

    public AnjiSQLDateProperty(String name) {
        super(name);
        value = new Date(new java.util.Date().getTime());
    }

    public AnjiSQLDateProperty(String name, Date value) {
        super(name);
        this.value = value;
    }

    public AnjiSQLDateProperty(Object bean, String name) {
        super(bean, name);
        value = new Date(new java.util.Date().getTime());
    }

    public AnjiSQLDateProperty(Object bean, String name, Date value) {
        super(bean, name);
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

    public int getYear() {
        return value.getYear();
    }

    public int getMonth() {
        return value.getMonth();
    }

    public int getDay() {
        return value.getDay();
    }
}
