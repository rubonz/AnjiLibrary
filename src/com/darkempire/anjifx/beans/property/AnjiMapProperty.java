package com.darkempire.anjifx.beans.property;

import com.darkempire.anjifx.beans.BeanType;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 13.11.13
 * Time: 16:46
 * To change this template use File | Settings | File Templates.
 */
public class AnjiMapProperty<K, V> extends AbstractAnjiProperty<Map<K, V>> implements com.darkempire.anjifx.beans.IAnjiValue<Map<K, V>>, Map<K, V> {
    private Map<K, V> value;

    public AnjiMapProperty(String name, Map<K, V> value) {
        super(name);
        this.value = value;
    }

    public AnjiMapProperty(Object bean, String name, Map<K, V> value) {
        super(bean, name);
        this.value = value;
    }

    @Override
    protected void set(Map<K, V> value) {
        this.value = value;
    }

    @Override
    public BeanType getType() {
        return BeanType.MAP_TYPE;
    }

    @Override
    public int size() {
        return value.size();
    }

    @Override
    public boolean isEmpty() {
        return value.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return value.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.value.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return value.get(key);
    }

    @Override
    public V put(K key, V value) {
        V result = this.value.put(key, value);
        setValue(this.value);
        return result;
    }

    @Override
    public V remove(Object key) {
        V result = value.remove(key);
        setValue(value);
        return result;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        value.putAll(m);
        setValue(value);
    }

    @Override
    public void clear() {
        value.clear();
        setValue(value);
    }

    @Override
    public Set<K> keySet() {
        return value.keySet();
    }

    @Override
    public Collection<V> values() {
        return value.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return value.entrySet();
    }

    @Override
    public Map<K, V> getValue() {
        return value;
    }
}
