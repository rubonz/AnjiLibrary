package com.darkempire.anjifx.beans.editor;

import javafx.beans.property.Property;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 04.11.13
 * Time: 8:44
 * To change this template use File | Settings | File Templates.
 */
public interface IAnjiPropertyEditor<T> {
    public Property<T> valueProperty();
}
