package com.darkempire.anjifx.beans;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.LinkedList;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 31.10.13
 * Time: 23:17
 * To change this template use File | Settings | File Templates.
 */
public class ListenerHolder<T> {
    private List<ChangeListener<? super T>> changeListeners;
    private List<InvalidationListener> invalidationListeners;

    public ListenerHolder() {
        changeListeners = new LinkedList<>();
        invalidationListeners = new LinkedList<>();
    }

    public void addListener(ChangeListener<? super T> listener) {
        changeListeners.add(listener);
    }

    public void addListener(InvalidationListener listener) {
        invalidationListeners.add(listener);
    }

    public void removeListener(ChangeListener<? super T> listener) {
        changeListeners.remove(listener);
    }

    public void removeListener(InvalidationListener listener) {
        invalidationListeners.remove(listener);
    }

    public void change(ObservableValue<T> o, T oldValue, T newValue) {
        for (ChangeListener<? super T> listener : changeListeners) {
            listener.changed(o, oldValue, newValue);
        }
    }

    public void invalidate(Observable o) {
        for (InvalidationListener listener : invalidationListeners) {
            listener.invalidated(o);
        }
    }
}
