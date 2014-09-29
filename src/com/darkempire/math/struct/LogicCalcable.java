package com.darkempire.math.struct;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 11.11.13
 * Time: 15:44
 * To change this template use File | Settings | File Templates.
 */
public interface LogicCalcable<T extends LogicCalcable<T>> extends LogicModifable<T>, LogicAssignable<T> {
    public T deepcopy();
}
