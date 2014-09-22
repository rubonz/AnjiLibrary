package com.darkempire.anjifx.beans.value;

import com.darkempire.anjifx.annotation.AnjiFXValue;
import com.darkempire.anjifx.beans.IAnjiValue;
import com.darkempire.anjifx.beans.ListenerHolder;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 23.09.13
 * Time: 16:24
 * To change this template use File | Settings | File Templates.
 */
@AnjiFXValue
public abstract class AbstractAnjiValue<T> implements IAnjiValue<T> {
    private ListenerHolder<T> holder;

    public AbstractAnjiValue() {
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

    @Override
    public void setValue(T value) {
        T temp = getValue();
        set(value);
        change(temp, getValue());
        invalidate();
    }

    protected abstract void set(T value);
}
