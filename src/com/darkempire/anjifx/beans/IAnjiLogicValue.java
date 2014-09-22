package com.darkempire.anjifx.beans;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 11.11.13
 * Time: 15:39
 * To change this template use File | Settings | File Templates.
 */
public interface IAnjiLogicValue<T> extends IAnjiValue<T> {
    public IAnjiLogicValue<T> ior(T t);

    public IAnjiLogicValue<T> iand(T t);

    public IAnjiLogicValue<T> ixor(T t);

    public IAnjiLogicValue<T> inot();

    public boolean get();
}
