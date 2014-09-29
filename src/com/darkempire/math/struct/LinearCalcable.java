package com.darkempire.math.struct;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 07.11.13
 * Time: 16:01
 * To change this template use File | Settings | File Templates.
 */
public interface LinearCalcable<T extends LinearCalcable<T>> extends LinearAssignable<T>, LinearModifable<T> {
    public T deepcopy();
}
