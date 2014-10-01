package com.darkempire.anjifx.beans.property;

import com.darkempire.anjifx.beans.BeanType;

import java.util.Collection;
import java.util.TreeSet;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 11.11.13
 * Time: 17:20
 * To change this template use File | Settings | File Templates.
 */
public class AnjiChooseStringProperty extends AbstractAnjiProperty<String> {
    //TODO:певно, варто огорнути Collection в ObservableCollection, а поки clear велосипед
    private String currentValue;
    private Collection<String> set;

    public AnjiChooseStringProperty(String name) {
        super(name);
        set = new TreeSet<>();
        currentValue = null;
    }

    public AnjiChooseStringProperty(Object bean, String name) {
        super(bean, name);
        set = new TreeSet<>();
        currentValue = null;
    }

    public AnjiChooseStringProperty(String name, Collection<String> set) {
        super(name);
        this.set = set;
        currentValue = set.iterator().next();
    }

    public AnjiChooseStringProperty(Object bean, String name, Collection<String> set) {
        super(bean, name);
        this.set = set;
        currentValue = set.iterator().next();
    }

    @Override
    protected void set(String value) {
        if (set.contains(value)) {
            this.currentValue = value;
        } else {
            currentValue = null;
        }
    }

    //TODO:видалити цей велосипед та зробити щось нормальне
    public void wipe() {
        currentValue = set.iterator().next();
    }

    public boolean contains(String s) {
        return set.contains(s);
    }

    public Collection<String> getSet() {
        return set;
    }

    @Override
    public String getValue() {
        return currentValue;
    }

    @Override
    public BeanType getType() {
        return BeanType.CHOOSE_TYPE;
    }
}
