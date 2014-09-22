package com.darkempire.anjifx.beans.value;

import com.darkempire.anjifx.annotation.AnjiFXClassConstructor;
import com.darkempire.anjifx.beans.*;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 13.11.13
 * Time: 16:34
 * To change this template use File | Settings | File Templates.
 */
@AnjiFXClassConstructor(isEmptyExist = false, constructorParams = {"@org.simpleframework.xml.ElementList(name = \"value\",inline = true) java.util.Set<E> valuex"})
public class AnjiSetValue<E> extends AbstractAnjiValue<Set<E>> implements com.darkempire.anjifx.beans.IAnjiCollectionValue<Set<E>, E> {
    private Set<E> value;

    public AnjiSetValue(Set<E> value) {
        this.value = value;
    }

    @Override
    protected void set(Set<E> value) {
        this.value = value;
    }

    @Override
    public BeanType getType() {
        return BeanType.SET_TYPE;
    }

    @Override
    public Set<E> getValue() {
        return value;
    }
}
