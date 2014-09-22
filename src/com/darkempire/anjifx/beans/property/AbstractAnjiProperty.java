package com.darkempire.anjifx.beans.property;

import com.darkempire.anjifx.annotation.AnjiFXProperty;
import com.darkempire.anjifx.beans.value.AbstractAnjiValue;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.beans.value.ObservableValue;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 23.09.13
 * Time: 16:24
 * To change this template use File | Settings | File Templates.
 */
@AnjiFXProperty
public abstract class AbstractAnjiProperty<T> extends AbstractAnjiValue<T> implements Property<T> {
    private final static Object DEFAULT_BEAN = null;
    private final static String DEFAULT_NAME = "";
    private ObservableValue<? extends T> observable;
    private Object bean;
    private String name;
    private InvalidationListener listener;

    public AbstractAnjiProperty() {
        super();
        this.bean = DEFAULT_BEAN;
        this.name = DEFAULT_NAME;
        listener = value -> super.setValue(observable.getValue());
    }

    public AbstractAnjiProperty(Object bean) {
        this();
        this.bean = bean;
    }

    public AbstractAnjiProperty(String name) {
        this();
        this.name = name;
    }

    public AbstractAnjiProperty(Object bean, String name) {
        this();
        this.bean = bean;
        this.name = name;
    }

    @Override
    public void bind(ObservableValue<? extends T> observable) {
        if (observable == null)
            throw new NullPointerException("Cannot bind to null");
        if (isBound())
            unbind();
        this.observable = observable;
        this.observable.addListener(listener);
    }

    @Override
    public void unbind() {
        observable.removeListener(listener);
        observable = null;
    }

    @Override
    public boolean isBound() {
        return observable != null;
    }

    @Override
    public void bindBidirectional(Property<T> other) {
        Bindings.bindBidirectional(this, other);
    }

    @Override
    public void unbindBidirectional(Property<T> other) {
        Bindings.unbindBidirectional(this, other);
    }

    @Override
    public Object getBean() {
        return bean;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setValue(T value) {
        if (isBound()) {
            throw new RuntimeException("A bound value cannot be set.");
        }
        super.setValue(value);
    }
}
