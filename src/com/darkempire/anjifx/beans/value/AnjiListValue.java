package com.darkempire.anjifx.beans.value;

import com.darkempire.anjifx.annotation.AnjiFXClassConstructor;
import com.darkempire.anjifx.beans.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 13.11.13
 * Time: 16:25
 * To change this template use File | Settings | File Templates.
 */
@AnjiFXClassConstructor(isEmptyExist = false, constructorParams = {"@org.simpleframework.xml.ElementList(name = \"value\",inline = true) java.util.List<K> value"})
public class AnjiListValue<K> extends AbstractAnjiValue<List<K>> implements com.darkempire.anjifx.beans.IAnjiCollectionValue<List<K>, K> {
    private List<K> value;

    public AnjiListValue(List<K> value) {
        this.value = value;
    }

    @Override
    protected void set(List<K> value) {
        this.value = value;
    }


    @Override
    public BeanType getType() {
        return BeanType.LIST_TYPE;
    }

    @Override
    public List<K> getValue() {
        return value;
    }
}
