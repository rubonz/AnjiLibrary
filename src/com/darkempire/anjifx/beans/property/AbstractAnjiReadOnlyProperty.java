package com.darkempire.anjifx.beans.property;

import com.darkempire.anjifx.beans.ListenerHolder;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 23.09.13
 * Time: 16:24
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractAnjiReadOnlyProperty<T> implements ObservableValue<T>, ReadOnlyProperty<T> {
    private final static Object DEFAULT_BEAN = null;
    private final static String DEFAULT_NAME = "";
    private Object bean;
    private String name;
    private ListenerHolder<T> holder;

    public AbstractAnjiReadOnlyProperty() {
        super();
        this.bean = DEFAULT_BEAN;
        this.name = DEFAULT_NAME;
    }

    public AbstractAnjiReadOnlyProperty(Object bean) {
        this();
        this.bean = bean;
    }

    public AbstractAnjiReadOnlyProperty(String name) {
        this();
        this.name = name;
    }

    public AbstractAnjiReadOnlyProperty(Object bean, String name) {
        this();
        this.bean = bean;
        this.name = name;
    }

    @Override
    public void addListener(ChangeListener<? super T> changeListener) {
        if (holder == null)
            holder = new ListenerHolder<>();
        holder.addListener(changeListener);
    }

    @Override
    public void removeListener(ChangeListener<? super T> changeListener) {
        if (holder == null)
            holder = new ListenerHolder<>();
        holder.removeListener(changeListener);
    }

    @Override
    public void addListener(InvalidationListener invalidationListener) {
        if (holder == null)
            holder = new ListenerHolder<>();
        holder.addListener(invalidationListener);
    }

    @Override
    public void removeListener(InvalidationListener invalidationListener) {
        if (holder == null)
            holder = new ListenerHolder<>();
        holder.removeListener(invalidationListener);
    }

    protected void invalidate() {
        if (holder == null)
            holder = new ListenerHolder<>();
        holder.invalidate(this);
    }

    protected void change(T oldValue, T newValue) {
        if (holder == null)
            holder = new ListenerHolder<>();
        holder.change(this, oldValue, newValue);
    }

    public void handle(T oldValue) {
        change(oldValue, getValue());
        invalidate();
    }

    @Override
    public Object getBean() {
        return bean;
    }

    @Override
    public String getName() {
        return name;
    }

}
