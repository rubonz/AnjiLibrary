package com.darkempire.math.struct;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 14.10.13
 * Time: 15:37
 * To change this template use File | Settings | File Templates.
 */
public class TreeItem<T extends Serializable> implements Iterable<TreeItem<T>>, Serializable {
    private transient TreeItem<T> parent;
    private T value;
    private ArrayList<TreeItem<T>> list;

    public TreeItem(TreeItem<T> parent, T value) {
        this.value = value;
        this.parent = parent;
        list = new ArrayList<>();
    }

    public T getValue() {
        return value;
    }

    public TreeItem<T> getParent() {
        return parent;
    }

    public void setParent(TreeItem<T> parent) {
        this.parent = parent;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public ArrayList<TreeItem<T>> getChildrens() {
        return list;
    }

    @Override
    public Iterator<TreeItem<T>> iterator() {
        return list.iterator();
    }

    public void afterLoad() {
        for (TreeItem<T> item : list) {
            item.parent = this;
            item.afterLoad();
        }
    }

    public TreeItem<T> findValue(T value) {
        if (this.value.equals(value))
            return this;
        for (TreeItem<T> treeItem : list) {
            if (treeItem.getValue().equals(value))
                return treeItem;
        }
        for (TreeItem<T> treeItem : list) {
            TreeItem<T> temp = treeItem.findValue(value);
            if (temp != null)
                return temp;
        }
        return null;
    }
}
