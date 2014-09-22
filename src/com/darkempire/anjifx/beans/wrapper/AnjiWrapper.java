package com.darkempire.anjifx.beans.wrapper;

import javafx.beans.property.Property;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 14.11.13
 * Time: 11:31
 * To change this template use File | Settings | File Templates.
 */
public class AnjiWrapper<T> {
    private Property<T> property;
    private T saveValue;

    private AnjiWrapper(Property<T> property) {
        this.property = property;
        saveValue = property.getValue();
    }

    public void backup() {
        if (property.isBound())
            property.unbind();
        property.setValue(saveValue);
    }

    public void free() {
        if (property.isBound())
            property.unbind();
    }

    public static <T> AnjiWrapper<T> wrap(Property<T> property) {
        return new AnjiWrapper<>(property);
    }
}