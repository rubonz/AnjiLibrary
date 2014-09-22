package com.darkempire.anjifx.beans.property;

import com.darkempire.anjifx.beans.BeanType;

import java.time.LocalDate;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 11.11.13
 * Time: 17:26
 * To change this template use File | Settings | File Templates.
 */
public class AnjiDateProperty extends AbstractAnjiProperty<LocalDate> {
    private LocalDate value;

    public AnjiDateProperty(String name) {
        super(name);
        value = LocalDate.now();
    }

    public AnjiDateProperty(String name, LocalDate value) {
        super(name);
        this.value = value;
    }

    public AnjiDateProperty(Object bean, String name) {
        super(bean, name);
        value = LocalDate.now();
    }

    public AnjiDateProperty(Object bean, String name, LocalDate value) {
        super(bean, name);
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
